package com.avaya.ecloud.commands.impl;

import com.avaya.ecloud.cache.ResponseCache;
import com.avaya.ecloud.cache.ScenarioCache;
import com.avaya.ecloud.model.cache.ResponseDetails;
import com.avaya.ecloud.model.enums.ApiUrlEnum;
import com.avaya.ecloud.model.enums.HttpHeaderEnum;
import com.avaya.ecloud.model.response.LoginResponse;
import com.avaya.ecloud.commands.Command;
import com.avaya.ecloud.utils.ModelUtil;
import com.avaya.ecloud.model.command.CommandData;
import com.avaya.ecloud.model.requests.LoginRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component("loginCommand")
public class LoginCommand extends BaseCommand implements Command {

    private Command subscriptionCommand;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);

    @Autowired
    public LoginCommand(@Qualifier("restTemplate") RestTemplate restTemplate, ScenarioCache scenarioCache, ResponseCache responseCache, @Qualifier("subscriptionCommand") Command subscriptionCommand) {
        super(scenarioCache, responseCache, restTemplate);
        this.subscriptionCommand = subscriptionCommand;
    }

    private String getAccountId(CommandData commandData) {
        String name = commandData.getName();
        if (name.equalsIgnoreCase("login")) {
            return getScenarioCache().getAccountId(commandData.getParent());
        } else if (name.equalsIgnoreCase("refreshToken")) {
            return (String) commandData.getConfig().get("accountId");
        } else {
            throw new RuntimeException("Invalid command name for Login command. Expected login or refreshToken, but got " + commandData.getName());
        }
    }

    private String getAccountSecret(CommandData commandData) {
        String name = commandData.getName();
        if (name.equalsIgnoreCase("login")) {
            return getScenarioCache().getAccountSecret(commandData.getParent());
        } else if (name.equalsIgnoreCase("refreshToken")) {
            return (String) commandData.getConfig().get("accountSecret");
        } else {
            throw new RuntimeException("Invalid command name for Login command. Expected login or refreshToken, but got " + commandData.getName());
        }
    }

    @Override
    public void execute(CommandData commandData) {

        String scenario = commandData.getParent();
        String authToken = getResponseCache().getAuthToken(scenario);
        if (StringUtils.isEmpty(authToken)) {
            String accountId = getAccountId(commandData);
            String accountSecret = getAccountSecret(commandData);

            LoginRequest loginRequest = new LoginRequest(accountId, accountSecret);

            HttpEntity<String> entity = ModelUtil.getEntityFromObject(loginRequest, ModelUtil.getRequestHeader(null, HttpHeaderEnum.LOGIN));

            try {
                LoginResponse response = getRestTemplate().postForObject(getLoginUrl(scenario), entity, LoginResponse.class);
                getResponseCache().put(scenario, new ResponseDetails(accountId, accountSecret, response.getAccessToken()));
                logInfo(commandData.getName(), loginRequest.getAccountId(), response.getAccessToken());
                createSubscription(scenario, response.getAccessToken(), accountId);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    private void createSubscription(String scenario, String authToken, String accountId) {
        Map<String, Object> map = new HashMap<>();
        map.put("config", "subscribe.json");
        map.put("authToken", authToken);
        map.put("accountId", accountId);
        subscriptionCommand.execute(new CommandData("createSubscription", scenario, map));
    }

    private String getLoginUrl(String scenario) {
        return getScenarioCache().getBaseUrl(scenario) + ApiUrlEnum.LOGIN.getValue();
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
