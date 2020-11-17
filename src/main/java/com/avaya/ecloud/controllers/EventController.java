package com.avaya.ecloud.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @PostMapping("/subscribeEvent")
    public void receiveEvent(@RequestBody String event) {
        //TODO some logic
    }
}
