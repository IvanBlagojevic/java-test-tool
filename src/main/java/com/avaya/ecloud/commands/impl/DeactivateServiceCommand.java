package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.ResponseData;
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


@Component("deactivateServiceCommand")
public class DeactivateServiceCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeactivateServiceCommand.class);

    @Autowired
    public DeactivateServiceCommand(Cache cache, RestTemplate restTemplate) {
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
        ResponseData responseData = commandData.getResponseData();

        // TODO We should refactor (merge) ActiveServiceCommand and DeactiveServiceCommand
        // TODO Since these are using same logic, but action is just different. We will need
        // TODO To refactor Java POJO name for this use case (this can be ACTIVATE or DEACTIVATE)
        // TODO HttpHeaderEnum should be also refactored
        HttpHeaders requestHeader = ModelUtil.getRequestHeader(responseData.getSessionToken(), HttpHeaderEnum.ACTIVATE_SERVICE);
        ActivateService request = ModelUtil.getActivateServiceRequestFromFile((String) commandData.getConfig().get("config"));

        HttpEntity<String> entity = ModelUtil.getEntityFromObject(request, requestHeader);

        try {
            getRestTemplate().postForEntity(responseData.getResourceData().getServicesUri(), entity, String.class);
            executeNext(updateNextCommandData(responseData));
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
}
