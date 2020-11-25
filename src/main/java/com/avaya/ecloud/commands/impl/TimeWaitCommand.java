package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("timeWaitCommand")
public class TimeWaitCommand implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeWaitCommand.class);


    @Override
    public void execute(CommandData commandData) {
        Integer time = (Integer) commandData.getConfig().get("time");
        LOGGER.info("GOING TO SLEEP FOR " + time + " MILLISECONDS");
        try {

            Thread.currentThread().sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
