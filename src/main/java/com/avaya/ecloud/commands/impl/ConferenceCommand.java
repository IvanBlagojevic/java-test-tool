package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
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

import java.util.ArrayList;
import java.util.List;


@Component("conferenceCommand")
public class ConferenceCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceCommand.class);

    @Autowired
    public ConferenceCommand(RestTemplate restTemplate, Cache cache) {
        super(cache, restTemplate);
    }

    private String createConferences(HttpEntity<String> entity, String roomName, String scenario) {
        try {
            logInfoOnStart(roomName);
            ConferenceResponse response = getRestTemplate().postForObject(getCreateConferenceUrl(scenario), entity, ConferenceResponse.class);
            logInfoOnFinish(roomName, response.getId());
            return response.getId();
        } catch (Exception e) {
            logError(roomName, e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getCreateConferenceUrl(String scenario) {
        return getCache().getBaseUrl(scenario) + ApiUrlEnum.CREATE_CONFERENCE.getValue();
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

        if (CollectionUtils.isEmpty(getCache().getConferenceIds(scenario))) {
            int conferenceCounter = getCache().getConferenceCounter(scenario);
            String accountId = getCache().getAccountId(scenario);

            CreateConferenceRequest conferenceRequest = ModelUtil.getCreateConferenceRequestFromFile((String) commandData.getConfig().get("config"));
            List<String> confIds = new ArrayList<>();
            for (int i = 0; i < conferenceCounter; i++) {
                StringBuilder builder = new StringBuilder();
                conferenceRequest.setRoomName(builder.append("conference-").append(accountId).append("-").append(i).toString());

                String authToken = getCache().getAuthToken(scenario);
                String roomName = conferenceRequest.getRoomName();

                HttpEntity<String> entity = ModelUtil.getEntityFromObject(conferenceRequest, ModelUtil.getRequestHeader(authToken, HttpHeaderEnum.CREATE_CONFERENCE));
                confIds.add(createConferences(entity, roomName, scenario));
            }
            getCache().saveConferenceId(confIds);
        }
        executeNext(getNextCommandData());

    }

    @Override
    public void setNextData(CommandData data) {
        setNextCommandData(data);
    }


    @Override
    public void setNext(Command command) {
        super.setNextCommand(command);
    }
}
