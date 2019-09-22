package com.budlee.automation.twitter;

import com.budlee.automation.twitter.scheduler.DelayedTwitterStoreConverter;
import com.budlee.automation.twitter.service.TweetSevice;
import com.budlee.automation.twitter.store.TweetStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class TwitterApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TwitterApplication.class, args);
    }

    @Autowired
    TweetSevice tweetSevice;

    @Autowired
    TweetStore tweetStore;

    @Autowired
    DelayedTwitterStoreConverter delayedTwitterStoreConverter;

    @Override
    public void run(String... args) throws Exception {
        final var delayedTweetStore = delayedTwitterStoreConverter.convert(tweetStore.getTweets());
        delayedTweetStore.log()
                .map(tweet -> {
                    tweetSevice.postTweet(tweet);
                    return Mono.empty();
                })
                .subscribe();
    }

}
