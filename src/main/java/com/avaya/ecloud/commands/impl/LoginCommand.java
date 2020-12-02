package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.Cache;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.enums.ApiUrlEnum;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.requests.LoginRequest;
import com.avaya.ecloud.model.response.LoginResponse;
import com.avaya.ecloud.utils.ModelUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("loginCommand")
@Scope("prototype")
public class LoginCommand extends BaseCommand implements Command {

    private Command subscriptionCommand;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);

    @Autowired
    public LoginCommand(@Qualifier("restTemplate") RestTemplate restTemplate, Cache cache, @Qualifier("subscriptionCommand") Command subscriptionCommand) {
        super(cache, restTemplate);
        this.subscriptionCommand = subscriptionCommand;
    }

    private String getAccountId(CommandData commandData) {
        String name = commandData.getName();
        if (name.equalsIgnoreCase("login")) {
            return getCache().getAccountId(commandData.getParent());
        } else if (name.equalsIgnoreCase("refreshToken")) {
            return (String) commandData.getConfig().get("accountId");
        } else {
            throw new RuntimeException("Invalid command name for Login command. Expected login or refreshToken, but got " + commandData.getName());
        }
    }

    private String getAccountSecret(CommandData commandData) {
        String name = commandData.getName();
        if (name.equalsIgnoreCase("login")) {
            return getCache().getAccountSecret(commandData.getParent());
        } else if (name.equalsIgnoreCase("refreshToken")) {
            return (String) commandData.getConfig().get("accountSecret");
        } else {
            throw new RuntimeException("Invalid command name for Login command. Expected login or refreshToken, but got " + commandData.getName());
        }
    }

    @Override
    public void execute(CommandData commandData) {
        String scenario = commandData.getParent();
        String accessToken = getCache().getAuthToken(scenario);

        if (commandData.getName().equals("refreshToken") || StringUtils.isEmpty(accessToken)) {
            String accountId = getAccountId(commandData);
            String accountSecret = getAccountSecret(commandData);

            String baseUrl = getCache().getBaseUrl(scenario);

            LoginRequest loginRequest = new LoginRequest(accountId, accountSecret);

            HttpEntity<String> entity = ModelUtil.getEntityFromObject(loginRequest, ModelUtil.getRequestHeader(null, HttpHeaderEnum.LOGIN));

            try {
                LoginResponse response = getRestTemplate().postForObject(getLoginUrl(baseUrl), entity, LoginResponse.class);
                accessToken = response.getAccessToken();
                getCache().saveAuthToken(accessToken);
                logInfo(commandData.getName(), loginRequest.getAccountId(), accessToken);
                createSubscription(scenario, accessToken, accountId);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            }

        }

        if (!commandData.getName().equals("refreshToken")){
            executeNext(updateNextCommandData(accessToken));
        }

    }

    private CommandData updateNextCommandData(String authToken) {
        CommandData nextCommandData = getNextCommandData();
        CommandData data = new CommandData(nextCommandData.getName(), nextCommandData.getParent(), nextCommandData.getResponseData(), nextCommandData.getConfig());
        data.getResponseData().setAuthToken(authToken);
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

    private void createSubscription(String scenario, String authToken, String accountId) {
        CommandData commandData = new CommandData("createSubscription", scenario);
        commandData.getResponseData().setRequestBody("subscribe.json");
        commandData.getResponseData().setAuthToken(authToken);
        subscriptionCommand.execute(commandData);
    }

    private String getLoginUrl(String baseUrl) {
        return baseUrl + ApiUrlEnum.LOGIN.getValue();
    }

    private void logInfo(String taskName, String accountId, String token) {
        StringBuilder builder = new StringBuilder();

        if (taskName.equalsIgnoreCase("login")) {
            builder.append("Login SUCCESSFUL for user with account id ").append(accountId).append(". ACCESS TOKEN: ").append(token);
        } else if (taskName.equalsIgnoreCase("refreshToken")) {
            builder.append("Token refreshed SUCCESSFUL for user with account id ").append(accountId).append(". NEW TOKEN: ").append(token);
        }

        LOGGER.info(builder.toString());
    }


}
