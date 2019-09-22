package com.budlee.automation.twitter.scheduler;

import com.budlee.automation.twitter.service.TweetSevice;
import com.budlee.automation.twitter.store.TweetStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Clock;

@Component
public class TwitterPostScheduler implements CommandLineRunner {

    @Autowired
    TweetSevice tweetSevice;

    @Autowired
    TweetStore tweetStore;

    @Override
    public void run(String... args) throws Exception {
        final var delayedTwitterStoreConverter = new DelayedTwitterStoreConverter(Clock.systemDefaultZone());
        final var delayedTweetStore = delayedTwitterStoreConverter.convert(tweetStore.getTweets());
        delayedTweetStore.log()
                .map(tweet -> {
                    tweetSevice.postTweet(tweet);
                    return Mono.empty();
                })
                .subscribe();
    }
}
