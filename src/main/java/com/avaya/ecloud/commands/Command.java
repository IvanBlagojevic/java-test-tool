package com.avaya.ecloud.commands;

import com.avaya.ecloud.model.command.CommandData;

public interface Command {

    void execute(CommandData commandData);

    void setNext(Command command);

    void setNextData(CommandData data);
}
