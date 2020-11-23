package com.avaya.ecloud.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    @PostMapping("/subscribeEvent")
    public void receiveEvent(@RequestBody String event) {
        LOGGER.info(event);
        //TODO some logic
    }
}
