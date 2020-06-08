package com.neeraj.poc.springbootdockerpoc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

/**
 * Created on:  Jun 06, 2020
 */

@RestController
@Slf4j
public class HelloController {

    @Autowired
    Environment environment;

    private String port;
    private String hostname;

    @GetMapping
    public ResponseEntity<String> sayHello() throws UnknownHostException {
        String body = getServerUrlPrefi() + "\t" + LocalDateTime.now() + "\t Hello From Java.";
        log.info("Hello endpoint called. Sending the response : {}", body);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public String getPort() {
        if (port == null) port = environment.getProperty("local.server.port");
        return port;
    }

    public String getHostname() throws UnknownHostException {
        if (hostname == null) hostname = InetAddress.getLocalHost().getHostAddress();
        return hostname;
    }

    public String getServerUrlPrefi() throws UnknownHostException {
        return "http://" + getHostname() + ":" + getPort();
    }
}
