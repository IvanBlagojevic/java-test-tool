package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.requests.activateService.ActivateService;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component("deactivateServiceCommand")
public class DeactiveServiceCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeactiveServiceCommand.class);

    public DeactiveServiceCommand(ScenarioCache scenarioCache, ResponseCache responseCache, RestTemplate restTemplate) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        String url = getResponseCache().getServicesUri(scenario);
        String sessionToken = getResponseCache().getSessionToken(scenario);

        // TODO We should refactor (merge) ActiveServiceCommand and DeactiveServiceCommand
        // TODO Since these are using same logic, but action is just different. We will need
        // TODO To refactor Java POJO name for this use case (this can be ACTIVATE or DEACTIVATE)
        // TODO HttpHeaderEnum should be also refactored
        HttpHeaders requestHeader = ModelUtil.getRequestHeader(sessionToken, HttpHeaderEnum.ACTIVATE_SERVICE);
        ActivateService request = ModelUtil.getActivateServiceRequestFromFile((String) commandData.getConfig().get("config"));

        HttpEntity<String> entity = ModelUtil.getEntityFromObject(request, requestHeader);

        try {
            getRestTemplate().postForEntity(url, entity, String.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
