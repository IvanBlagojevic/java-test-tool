package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.utils.ModelUtil;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.response.resource.Resource;
import com.avaya.ecloud.model.response.resource.ResourceDiscoveryResponse;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component("resourceCommand")
public class ResourceDiscoveryCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceDiscoveryCommand.class);

    @Autowired
    public ResourceDiscoveryCommand(ScenarioCache scenarioCache, ResponseCache responseCache, @Qualifier("restTemplate") RestTemplate restTemplate) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        HttpEntity<String> entity = ModelUtil.getEntityFromObject(null, ModelUtil.getRequestHeader(getResponseCache().getSessionToken(scenario), HttpHeaderEnum.RESOURCE_DISCOVERY));
        try {
            logInfoOnStart();
            ResponseEntity<ResourceDiscoveryResponse> responseEntity = getRestTemplate().exchange(getResponseCache().getUserAgentURL(scenario), HttpMethod.GET, entity, ResourceDiscoveryResponse.class);
            cacheResponseData(scenario, responseEntity.getBody());
            logInfoOnFinish();
        } catch (Exception e) {
            logError(e);
        }
    }

    private void cacheResponseData(String scenario, ResourceDiscoveryResponse response) {
        ResponseCache responseCache = getResponseCache();
        responseCache.saveWebsocketUri(scenario, response.getNotificationService().getWebsocket().getHref());
        responseCache.saveSseUri(scenario, response.getNotificationService().getSse().getHref());
        responseCache.saveTerminateClientUri(scenario, response.getClientManagement().getTerminateClient().getHref());
        responseCache.saveDeleteSessionUri(scenario, response.getSessionManagement().getHref());

        List<Resource> resources = response.getResources();

        for (Resource resource : resources) {
            String name = resource.getName();
            String href = resource.getServiceReference().getHref();

            switch (name) {
                case "calls":
                    responseCache.saveCallsUri(scenario, href);
                    break;
                case "mpaasevents":
                    responseCache.saveEventsUri(scenario, href);
                    break;
                case "services":
                    responseCache.saveServicesUri(scenario, href);
                    break;
            }
        }
    }

    private void logInfoOnStart() {
        LOGGER.info("Resource discovery STARTED");
    }

    private void logInfoOnFinish() {
        LOGGER.info("Resource discovery FINISHED");
    }

    private void logError(Exception e) {
        LOGGER.error("Resource discovery ERROR. ERROR MESSAGE: " + e.getMessage(), e);
    }


}
