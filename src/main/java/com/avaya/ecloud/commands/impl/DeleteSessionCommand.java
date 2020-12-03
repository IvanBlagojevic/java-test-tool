package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.response.ResponseData;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.ApiUrlEnum;
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

import java.util.Objects;


@Component("deleteSessionCommand")
public class DeleteSessionCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteSessionCommand.class);

    @Autowired
    public DeleteSessionCommand(Cache cache, RestTemplate restTemplate) {
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
        String scenario = commandData.getParent();
        String sessionEndpoint = getCache().getBaseUrl(scenario) + ApiUrlEnum.CREATE_SESSION.getValue();

        String sessionId = commandData.getResponseData().getSessionId();

        HttpHeaders headers = ModelUtil.getRequestHeader(getCache().getAuthToken(scenario), HttpHeaderEnum.DELETE_SESSION);
        HttpEntity<?> request = new HttpEntity<>(headers);

        StringBuilder builder = new StringBuilder(sessionEndpoint);
        builder.append(sessionId);

        try {
            getRestTemplate().exchange(builder.toString(), HttpMethod.DELETE, request, String.class);
            logInfoOnFinish(sessionId);
            executeNext(updateNextCommandData(commandData.getResponseData()));
        } catch (Exception e) {
            logError(sessionId, e);
            throw new RuntimeException(e.getMessage());
        }

    }

    private CommandData updateNextCommandData(ResponseData responseData) {
        CommandData nextCommandData = getNextCommandData();
        CommandData data;

        if (Objects.isNull(nextCommandData)) {
            data = new CommandData();
        } else {
            data = new CommandData(nextCommandData.getName(), nextCommandData.getParent(), nextCommandData.getResponseData(), nextCommandData.getConfig());
        }

        data.setResponseData(responseData);
        return data;
    }

    private void logInfoOnFinish(String sessionId) {
        LOGGER.info("Event unsubscribe FINISHED for session id " + sessionId);
    }

    private void logError(String sessionId, Exception e) {

    }
}
