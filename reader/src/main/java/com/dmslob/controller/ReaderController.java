package com.dmslob.controller;

import com.dmslob.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReaderController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/to-read")
    public String toRead() {
        return bookService.readingList();
    }
}
