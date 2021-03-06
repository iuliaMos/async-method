package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class AppRunner implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(AppRunner.class);

    private final GitHubLookupService service;

    public AppRunner(GitHubLookupService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        long start = System.currentTimeMillis();

        CompletableFuture<User> page1 = service.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = service.findUser("CloudFoundry");
        CompletableFuture<User> page3 = service.findUser("Spring-Projects");

        CompletableFuture.allOf(page1, page2, page3).join();

        log.info("Elappsed time: " + (System.currentTimeMillis() - start));
        log.info("--> " + page1.get());
        log.info("--> " + page2.get());
        log.info("--> " + page3.get());
    }
}
