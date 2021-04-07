package com.acciona.challenge.logging;

import java.util.function.Consumer;

import com.acciona.challenge.model.Tweet;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class PersistingSuccessHandler<T> implements Consumer<Mono<Tweet>> {

  private Class<T> clazz;

  public PersistingSuccessHandler(Class<T> clazz) {
    super();
    this.clazz = clazz;
  }

  @Override
  public void accept(Mono<Tweet> tweet) {
    tweet.subscribe(persistedTweet -> log.info("{} with id: {} persisted", clazz.getSimpleName(), persistedTweet.getId()));
  }
}
