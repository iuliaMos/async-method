package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class GitHubLookupService {

    private Logger log = LoggerFactory.getLogger(GitHubLookupService.class);

    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Async
    public CompletableFuture<User> findUser(String userName) throws InterruptedException {
        log.info("Looking up {}", userName);
        String url = String.format("https://api.github.com/users/%s", userName);
        User results = restTemplate.getForObject(url, User.class);

        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }
}
