package com.example.zuul.simpleController.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @Value("${simple.controller.version:0.0}")
    String version;

    @GetMapping("/version")
    public ResponseEntity<String> version() {
        String retVal = "Our version is " + version;

        HttpHeaders headers = new HttpHeaders();
        headers.add("VersionHeader", version);
        headers.add("Set-Cookie", "controller-version="+version);

        return ResponseEntity.ok().headers(headers).body(retVal);
    }


}
