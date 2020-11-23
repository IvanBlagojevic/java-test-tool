package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.utils.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("eventSubscriptionCommand")
public class EventSubscriptionCommand extends BaseCommand implements Command {

    @Autowired
    public EventSubscriptionCommand(ScenarioCache scenarioCache, ResponseCache responseCache, RestTemplate restTemplate) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {

        String scenario = commandData.getParent();

        HttpHeaders headers = ModelUtil.getRequestHeader(getResponseCache().getAuthToken(scenario), HttpHeaderEnum.EVENT_SUBSCRIPTION);


    }
}
