package com.acciona.challenge.repository;

import org.springframework.stereotype.Component;

import com.acciona.challenge.model.Tweet;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;  

@Component
public interface TweetRepository extends ReactiveCrudRepository<Tweet, Long> {


}
