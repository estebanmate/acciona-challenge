package com.acciona.challenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.acciona.challenge.common.StatusProcessor;
import com.acciona.challenge.logging.PersistingErrorHandler;
import com.acciona.challenge.logging.PersistingSuccessHandler;
import com.acciona.challenge.model.Tweet;
import com.acciona.challenge.repository.TweetRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import twitter4j.Status;

@Slf4j
@Component
public class TweetPersistService implements StatusProcessor {
  
  @Value("#{'${locales.accepted}'.split(',')}")
  private List<String> acceptedLocales;
 
  @Value("${followers.min}")
  private int followersMin;
  
  private TweetRepository tweetRepository;

  @Autowired
  public TweetPersistService(
      TweetRepository tweetRepository) {
    super();
    this.tweetRepository = tweetRepository;
  }

  @Override
  public void processStatusses(Flux<Status> statusses) {
    log.info("Persisting tweets initiated");
    statusses
        .filter(status -> checkParameters(status))
        .map(status -> {return this.toTweetEntity(status);})
        .map(tweetRepository::save)
        .subscribe(
                new PersistingSuccessHandler<>(Tweet.class),
                new PersistingErrorHandler<>(Tweet.class));
      }

  private boolean checkParameters(Status status) {
	  if(status.getUser().getFollowersCount() > followersMin &&
			  acceptedLocales.contains(status.getLang()))
		  return true;
	  return false;
  }

  public Flux<Tweet> getAllTweets() {  
      return tweetRepository.findAll();  
  }
  
  private Tweet toTweetEntity(Status status) {
	  Tweet tweet = new Tweet(status.getId(),
	    		status.getUser().getName(),
	            status.getText(),
	            status.getLang(),
	            false);

	  log.info("Save Tweet: ["+tweet.toString()+"]");
	  
	  return tweet;
  }

}
