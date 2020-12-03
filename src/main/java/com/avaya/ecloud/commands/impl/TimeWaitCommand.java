package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.ResponseData;
import com.avaya.ecloud.model.command.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("timeWaitCommand")
public class TimeWaitCommand extends BaseCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeWaitCommand.class);

    @Autowired
    public TimeWaitCommand(Cache cache, RestTemplate restTemplate) {
        super(cache, restTemplate);
    }


    @Override
    public void execute(CommandData commandData) {
        Integer time = (Integer) commandData.getConfig().get("time");
        LOGGER.info("GOING_TO_SLEEP_FOR_" + time + "_MILLISECONDS");
        try {
            Thread.sleep(time);
            executeNext(updateNextCommandData(commandData.getResponseData()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private CommandData updateNextCommandData(ResponseData responseData) {
        CommandData nextCommandData = getNextCommandData();
        CommandData data = new CommandData(nextCommandData.getName(), nextCommandData.getParent(), nextCommandData.getResponseData(), nextCommandData.getConfig());
        data.setResponseData(responseData);
        return data;
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
