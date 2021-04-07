package com.acciona.challenge;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;

import com.acciona.challenge.repository.TweetRepository;


@SpringBootApplication
public class AccionaChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccionaChallengeApplication.class, args);
	}

	
    @Bean
    ApplicationRunner init(TweetRepository repository, DatabaseClient client) {
        return args -> {
            client.sql("create table IF NOT EXISTS TWEET" +
                    "(id BIGINT NOT NULL PRIMARY KEY, username varchar(255) not null, text varchar (255) not null, locale varchar(10), validated boolean default false);").fetch().first().subscribe();
        };
    }	
}
