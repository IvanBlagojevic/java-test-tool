package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component("deleteHttpSessionCommand")
public class DeleteHttpSessionCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteHttpSessionCommand.class);

    public DeleteHttpSessionCommand(ScenarioCache scenarioCache, ResponseCache responseCache, RestTemplate restTemplate) {
        super(scenarioCache, responseCache, restTemplate);
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        String deleteSessionUri = getResponseCache().getDeleteSessionUri(scenario);

        try {
            getRestTemplate().delete(deleteSessionUri);
            logInfoOnFinish(deleteSessionUri);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void logInfoOnFinish(String deleteSessionUri) {
        LOGGER.info("Delete session FINISHED for session uri " + deleteSessionUri);
    }
}
