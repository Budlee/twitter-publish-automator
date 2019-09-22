package com.budlee.automation.twitter.service.exception;

import twitter4j.TwitterException;

public class TwitterStatusException extends RuntimeException {
    public TwitterStatusException(TwitterException e) {
        super(e);
    }
}
