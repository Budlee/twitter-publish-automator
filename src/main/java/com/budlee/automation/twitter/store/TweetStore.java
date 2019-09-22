package com.budlee.automation.twitter.store;

import com.budlee.automation.twitter.model.Tweet;
import reactor.core.publisher.Flux;

public interface TweetStore {
    Flux<Tweet> getTweets();
}
