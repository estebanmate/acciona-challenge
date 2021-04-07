package com.acciona.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acciona.challenge.model.Tweet;
import com.acciona.challenge.service.TweetPersistService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/acciona/api/v1")
public class AccionaChallengeController {

  @Autowired private TweetPersistService tweetPersistService;
  
  @GetMapping("tweets")
  public Flux<Tweet> getTweets() {
    log.info("Getting tweets");
    return tweetPersistService.getAllTweets();
  }
  
}
