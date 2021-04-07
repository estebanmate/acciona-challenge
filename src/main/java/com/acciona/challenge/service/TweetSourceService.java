package com.acciona.challenge.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.util.List;
import java.util.Optional;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acciona.challenge.common.StatusProcessor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;

@Service
public class TweetSourceService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TweetSourceService.class);

  @Autowired private TwitterStream twitterStream;
  @Autowired private List<StatusProcessor> processors;

  @PreDestroy
  public void disconnect() {

    twitterStream.clearListeners();
    twitterStream.cleanUp();

    LOGGER.info("Twitter client disconnected");
  }

  @PostConstruct
  public void init() {
    processors.forEach(p -> p.processStatusses(twittsFlux()));
  }

  public Flux<Status> twittsFlux() {
    return Flux.from(new StatusPublisher(twitterStream)).subscribeOn(Schedulers.parallel());
  }

  private class StatusPublisher implements Publisher<Status> {

    private TwitterStream twitterStream;

    public StatusPublisher(TwitterStream twitterStream) {
      super();
      this.twitterStream = twitterStream;
    }

    @Override
    public void subscribe(Subscriber<? super Status> subscriber) {

      this.twitterStream.addListener(new TrendsStatusAdapter(subscriber));
      
    }
  }

  private class TrendsStatusAdapter extends StatusAdapter {

    private Subscriber<? super Status> subscriber;

    public TrendsStatusAdapter(Subscriber<? super Status> subscriber) {
      super();
      this.subscriber = subscriber;
    }

    @Override
    public void onStatus(Status status) {
      Optional.ofNullable(status)
          .filter(s -> this.subscriber != null)
          .ifPresent(s -> this.subscriber.onNext(s));
    }

    @Override
    public void onException(Exception ex) {
      Optional.ofNullable(ex)
          .filter(e -> this.subscriber != null)
          .ifPresent(
              e -> {
                e.printStackTrace();
                this.subscriber.onError(e);
              });
    }
  }

}
