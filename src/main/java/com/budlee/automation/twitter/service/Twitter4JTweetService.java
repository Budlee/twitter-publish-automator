package com.budlee.automation.twitter.service;

import com.budlee.automation.twitter.service.exception.TwitterStatusException;
import com.budlee.automation.twitter.model.Tweet;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Service
@Log
public class Twitter4JTweetService implements TweetSevice {

    private final Twitter twitter;

    public Twitter4JTweetService(
            @Value("${twitter.key.consumer.key}") String consumerKey,
            @Value("${twitter.key.consumer.secret}") String consumerSecret,
            @Value("${twitter.key.access.token}") String token,
            @Value("${twitter.key.access.secret}") String tokenSecret) {
        final var configuration = new ConfigurationBuilder()
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(token)
                .setOAuthAccessTokenSecret(tokenSecret)
                .build();
        this.twitter = new TwitterFactory(configuration).getInstance();
    }

    @Override
    public void postTweet(Tweet tweet) throws TwitterStatusException {
        try {
            log.info(String.format("Pushing up tweet [%s]", tweet));
            twitter.updateStatus(tweet.getStatus());
        } catch (TwitterException e) {
            throw new TwitterStatusException(e);
        }
    }

    public Twitter getTwitter() {
        return twitter;
    }
}
