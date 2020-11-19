package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.commands.utils.CommandUtil;
import com.avaya.ecloud.model.requests.activateService.ActivateService;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
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
    public ActivateServiceCommand(ScenarioCache scenarioCache, ResponseCache responseCache, RestTemplate restTemplate) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        String url = getResponseCache().getServicesUri(scenario);
        String sessionToken = getResponseCache().getSessionToken(scenario);

        //TODO what about basic auth header?

        HttpHeaders requestHeader = CommandUtil.getRequestHeader(sessionToken, HttpHeaderEnum.ACTIVATE_SERVICE);
        ActivateService request = CommandUtil.getActivateServiceRequestFromFile((String) commandData.getConfig().get("config"));

        HttpEntity<String> entity = CommandUtil.getEntityFromObject(request, requestHeader);

        try {
            getRestTemplate().postForEntity(url, entity, String.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
