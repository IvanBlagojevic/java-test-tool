package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.command.ResponseData;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component("deleteHttpSessionCommand")
public class DeleteHttpSessionCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteHttpSessionCommand.class);

    @Autowired
    public DeleteHttpSessionCommand(Cache cache, RestTemplate restTemplate) {
        super(cache, restTemplate);
    }

    @Override
    public void setNext(Command command) {
        super.setNextCommand(command);
    }

    @Override
    public void setNextData(CommandData data) {
        setNextCommandData(data);
    }

    @Override
    public void execute(CommandData commandData) {
        String deleteSessionUri = commandData.getResponseData().getResourceData().getDeleteSessionUri();
        HttpHeaders headers = ModelUtil.getRequestHeader(commandData.getResponseData().getSessionToken(), HttpHeaderEnum.DELETE_HTTP_SESSION);
        HttpEntity<?> request = new HttpEntity<>(headers);
        try {
            getRestTemplate().exchange(deleteSessionUri, HttpMethod.DELETE, request, String.class);
            logInfoOnFinish(deleteSessionUri);
            executeNext(updateNextCommandData(commandData.getResponseData()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private CommandData updateNextCommandData(ResponseData responseData) {
        CommandData nextCommandData = getNextCommandData();
        CommandData data = new CommandData(nextCommandData.getName(), nextCommandData.getParent(), nextCommandData.getResponseData(), nextCommandData.getConfig());
        data.setResponseData(responseData);
        return data;
    }

    private void logInfoOnFinish(String deleteSessionUri) {
        LOGGER.info("Delete session FINISHED for session uri " + deleteSessionUri);
    }
}
