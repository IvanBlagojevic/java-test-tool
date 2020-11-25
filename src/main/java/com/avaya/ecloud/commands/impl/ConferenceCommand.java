package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.ApiUrlEnum;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.requests.conference.CreateConferenceRequest;
import com.avaya.ecloud.model.response.ConferenceResponse;
import com.avaya.ecloud.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;


@Component("conferenceCommand")
public class ConferenceCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceCommand.class);

    @Autowired
    public ConferenceCommand(RestTemplate restTemplate, ResponseCache responseCache, ScenarioCache scenarioCache) {
        super(scenarioCache, responseCache, restTemplate);
    }

    private void createConferences(HttpEntity<String> entity, String roomName, String scenario) {
        try {
            logInfoOnStart(roomName);
            ConferenceResponse response = getRestTemplate().postForObject(getCreateConferenceUrl(scenario), entity, ConferenceResponse.class);
            getResponseCache().saveConferenceId(scenario, response.getId());
            logInfoOnFinish(roomName, response.getId());
        } catch (Exception e) {
            logError(roomName, e);
        }
    }

    private String getCreateConferenceUrl(String scenario) {
        return getScenarioCache().getBaseUrl(scenario) + ApiUrlEnum.CREATE_CONFERENCE.getValue();
    }

    private void logInfoOnStart(String roomName) {
        LOGGER.info("Creating conference STARTED for conference room " + roomName);
    }

    private void logError(String roomName, Exception e) {
        LOGGER.error("Creating conference ERROR for conference room: " + roomName + ". ERROR MESSAGE: " + e.getMessage(), e);
    }

    private void logInfoOnFinish(String roomName, String id) {
        LOGGER.info("Creating conference FINISHED for conference room " + roomName + ". CONFERENCE_ID: " + id);
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();

        if (CollectionUtils.isEmpty(getResponseCache().getConferenceIds(scenario))) {
            int conferenceCounter = getScenarioCache().getConferenceCounter(scenario);
            String accountId = getScenarioCache().getAccountId(scenario);

            CreateConferenceRequest conferenceRequest = ModelUtil.getCreateConferenceRequestFromFile((String) commandData.getConfig().get("config"));

            for (int i = 0; i < conferenceCounter; i++) {
                StringBuilder builder = new StringBuilder();
                conferenceRequest.setRoomName(builder.append("conference-").append(accountId).append("-").append(i).toString());

                String authToken = getResponseCache().getAuthToken(scenario);
                String roomName = conferenceRequest.getRoomName();

                HttpEntity<String> entity = ModelUtil.getEntityFromObject(conferenceRequest, ModelUtil.getRequestHeader(authToken, HttpHeaderEnum.CREATE_CONFERENCE));
                createConferences(entity, roomName, scenario);
            }
        }
    }
}
