package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.command.ResponseData;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


abstract class BaseCommand {

    private Cache cache;

    private RestTemplate restTemplate;

    private Command nextCommand;

    private CommandData nextCommandData;

    public Cache getCache() {
        return cache;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void executeNext(CommandData commandData) {
        if (!Objects.isNull(getNextCommand())) {
            getNextCommand().execute(commandData);
        }
    }

    public void updateNextCommandData(String key, Object value) {
        if (!Objects.isNull(nextCommandData)) {
            nextCommandData.getConfig().put(key, value);
        }
    }

    public Command getNextCommand() {
        return nextCommand;
    }

    public CommandData getNextCommandData() {
        return nextCommandData;
    }

    public CommandData getUpdatedCommandData(CommandData commandData) {
        CommandData nextCommandData = getNextCommandData();
        CommandData data;

        if (Objects.isNull(nextCommandData)) {
            data = new CommandData(new ResponseData());
        } else {
            data = new CommandData(nextCommandData.getName(), nextCommandData.getParent(), nextCommandData.getResponseData(), nextCommandData.getConfig());
        }

        data.setResponseData(commandData.getResponseData());

        return data;
    }

    public void setNextCommandData(CommandData nextCommandData) {
        this.nextCommandData = nextCommandData;
    }

    public void setNextCommand(Command nextCommand) {
        this.nextCommand = nextCommand;
    }

    public BaseCommand(Cache cache, RestTemplate restTemplate) {
        this.cache = cache;
        this.restTemplate = restTemplate;
    }
}
