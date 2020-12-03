package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.command.ResourceData;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.response.resource.ResourceDiscoveryResponse;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("resourceCommand")
public class ResourceDiscoveryCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceDiscoveryCommand.class);

    @Autowired
    public ResourceDiscoveryCommand(Cache cache, @Qualifier("restTemplate") RestTemplate restTemplate) {
        super(cache, restTemplate);
    }


    @Override
    public void execute(CommandData commandData) {
        HttpHeaders requestHeader = ModelUtil.getRequestHeader(commandData.getResponseData().getSessionToken(), HttpHeaderEnum.RESOURCE_DISCOVERY);
        HttpEntity<String> entity = ModelUtil.getEntityFromObject(null, requestHeader);
        String userAgentURL = commandData.getResponseData().getUserAgentURL();
        try {
            logInfoOnStart(commandData.getResponseData().getSessionId());
            ResourceDiscoveryResponse response = getRestTemplate().exchange(userAgentURL, HttpMethod.GET, entity, ResourceDiscoveryResponse.class).getBody();
            logInfoOnFinish(commandData.getResponseData().getSessionId());
            executeNext(getUpdatedCommandData(response, commandData));
        } catch (Exception e) {
            logError(e);
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public void setNextData(CommandData data) {
        setNextCommandData(data);
    }

    @Override
    public void setNext(Command command) {
        super.setNextCommand(command);
    }


    private CommandData getUpdatedCommandData(ResourceDiscoveryResponse response, CommandData commandData) {
        CommandData nextCommandData = getNextCommandData();
        CommandData data = new CommandData(nextCommandData.getName(), nextCommandData.getParent(), nextCommandData.getResponseData(), nextCommandData.getConfig());
        data.setResponseData(commandData.getResponseData());
        data.getResponseData().setResourceData(new ResourceData(response.getNotificationService().getWebsocket().getHref(),
                response.getNotificationService().getSse().getHref(),
                response.getClientManagement().getTerminateClient().getHref(),
                response.getSessionManagement().getHref(),
                response.getResources()));
        return data;
    }

    private void logInfoOnStart(String sessionId) {
        LOGGER.info("Resource discovery STARTED for session id " + sessionId);
    }

    private void logInfoOnFinish(String sessionId) {
        LOGGER.info("Resource discovery FINISHED for session id " + sessionId);
    }

    private void logError(Exception e) {
        LOGGER.error("Resource discovery ERROR. ERROR MESSAGE: " + e.getMessage(), e);
    }

}
