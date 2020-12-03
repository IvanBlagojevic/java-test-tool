package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.ResponseData;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component("terminateClientCommand")
public class TerminateClientCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminateClientCommand.class);

    @Autowired
    public TerminateClientCommand(Cache cache, RestTemplate restTemplate) {
        super(cache, restTemplate);
    }

    @Override
    public void setNextData(CommandData data) {
        setNextCommandData(data);
    }

    @Override
    public void setNext(Command command) {
        super.setNextCommand(command);
    }

    @Override
    public void execute(CommandData commandData) {
        ResponseData responseData = commandData.getResponseData();
        String terminateClientUri = responseData.getResourceData().getTerminateClientUri();
        HttpHeaders headers = ModelUtil.getRequestHeader(responseData.getSessionToken(), HttpHeaderEnum.TERMINATE_CLIENT);
        HttpEntity<?> request = new HttpEntity<>(headers);

        try {
            getRestTemplate().exchange(terminateClientUri, HttpMethod.DELETE, request, String.class);
            logInfoOnFinish(terminateClientUri);
            executeNext(updateNextCommandData(responseData));
        } catch (Exception e) {
            logError(terminateClientUri, e);
            //throw new RuntimeException(e.getMessage());
        }


    }

    private CommandData updateNextCommandData(ResponseData responseData) {
        CommandData nextCommandData = getNextCommandData();
        CommandData data = new CommandData(nextCommandData.getName(), nextCommandData.getParent(), nextCommandData.getResponseData(), nextCommandData.getConfig());
        data.setResponseData(responseData);
        return data;
    }


    private void logInfoOnFinish(String terminateClientUri) {
        LOGGER.info("Terminate client FINISHED for client uri " + terminateClientUri);
    }

    private void logError(String sessionId, Exception e) {

    }


}

