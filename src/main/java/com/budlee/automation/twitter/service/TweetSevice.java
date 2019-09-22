package com.budlee.automation.twitter.service;

import com.budlee.automation.twitter.service.exception.TwitterStatusException;
import com.budlee.automation.twitter.model.Tweet;

public interface TweetSevice {
    void postTweet(Tweet tweet) throws TwitterStatusException;
}
