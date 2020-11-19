package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.commands.utils.CommandUtil;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.requests.startAudioCall.AudioCall;
import com.avaya.ecloud.model.response.startAudioCall.StartAudioCallResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("createNewCallCommand")
public class CreateNewCallCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateNewCallCommand.class);

    @Autowired
    public CreateNewCallCommand(ScenarioCache scenarioCache, ResponseCache responseCache, @Qualifier("restTemplate") RestTemplate restTemplate) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        String url = getResponseCache().getCallsUri(scenario);

        AudioCall request = CommandUtil.getAudioCallRequestFromFile((String) commandData.getConfig().get("config"));
        String sessionId = getResponseCache().getSessionIds(scenario).get(0);
        request.setSessionId(sessionId);

        String sessionToken = getResponseCache().getSessionToken(scenario);
        HttpHeaders requestHeader = CommandUtil.getRequestHeader(sessionToken, HttpHeaderEnum.CREATE_NEW_CALL);
        requestHeader.set("X-Forwarded-For", "10.133.1.1");

        HttpEntity<String> entity = CommandUtil.getEntityFromObject(request, requestHeader);

        try {
            StartAudioCallResponse response = getRestTemplate().postForObject(url, entity, StartAudioCallResponse.class);
            String callId = response.getCallId();
            System.out.println("CALL ID " + callId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
