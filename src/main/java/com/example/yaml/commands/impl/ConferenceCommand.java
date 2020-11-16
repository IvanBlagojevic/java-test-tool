package com.example.yaml.commands.impl;

import com.example.yaml.cache.ResponseCache;
import com.example.yaml.cache.ScenarioCache;
import com.example.yaml.commands.Command;
import com.example.yaml.commands.utils.CommandUtil;
import com.example.yaml.model.command.CommandData;
import com.example.yaml.model.enums.ApiUrlEnum;
import com.example.yaml.model.enums.HttpHeaderEnum;
import com.example.yaml.model.requests.CreateConferenceRequest;
import com.example.yaml.model.response.ConferenceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
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
        int conferenceCounter = getScenarioCache().getConferenceCounter(scenario);
        String accountId = getScenarioCache().getAccountId(scenario);

        CreateConferenceRequest conferenceRequest = CommandUtil.getCreateConferenceRequestFromFile((String) commandData.getConfig().get("config"));

        for (int i = 0; i <= conferenceCounter; i++) {
            StringBuilder builder = new StringBuilder();
            conferenceRequest.setRoomName(builder.append("conference-").append(accountId).append("-").append(i).toString());

            String authToken = getResponseCache().getAuthToken(scenario);
            String roomName = conferenceRequest.getRoomName();

            HttpEntity<String> entity = CommandUtil.getEntityFromObject(conferenceRequest, CommandUtil.getRequestHeader(authToken, HttpHeaderEnum.CREATE_CONFERENCE));
            createConferences(entity, roomName, scenario);
        }
    }
}
