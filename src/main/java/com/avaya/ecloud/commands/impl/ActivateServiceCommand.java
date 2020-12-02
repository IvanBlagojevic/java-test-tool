package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.requests.activateService.ActivateService;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("activateServiceCommand")
public class ActivateServiceCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivateServiceCommand.class);

    @Autowired
    public ActivateServiceCommand(Cache cache, RestTemplate restTemplate) {
        super(cache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        String url = commandData.getResponseData().getServicesUri();
        String sessionToken = commandData.getResponseData().getSessionToken();

        //TODO what about basic auth header?
        HttpHeaders requestHeader = ModelUtil.getRequestHeader(sessionToken, HttpHeaderEnum.ACTIVATE_SERVICE);
        ActivateService request = ModelUtil.getActivateServiceRequestFromFile("activateCalls.json");

        HttpEntity<String> entity = ModelUtil.getEntityFromObject(request, requestHeader);

        try {
            getRestTemplate().postForEntity(url, entity, String.class);
            executeNext(updateNextCommandData(commandData));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }


    private CommandData updateNextCommandData(CommandData commandData) {
        CommandData nextCommandData = getNextCommandData();
        CommandData data = new CommandData(nextCommandData.getName(), nextCommandData.getParent(), nextCommandData.getResponseData(), nextCommandData.getConfig());
        data.setResponseData(commandData.getResponseData());
        return data;
    }

    @Override
    public void setNext(Command command) {
        super.setNextCommand(command);
    }

    @Override
    public void setNextData(CommandData data) {
        setNextCommandData(data);
    }

}
