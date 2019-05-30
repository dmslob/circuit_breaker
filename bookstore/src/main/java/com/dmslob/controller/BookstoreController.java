package com.dmslob.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookstoreController {

    @RequestMapping(value = "/recommended")
    public String readingList() {
        return "readingList";
    }
}
