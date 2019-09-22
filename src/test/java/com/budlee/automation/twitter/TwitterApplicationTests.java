package com.budlee.automation.twitter;

import com.budlee.automation.twitter.model.Tweet;
import com.budlee.automation.twitter.scheduler.DelayedTwitterStoreConverter;
import com.budlee.automation.twitter.service.TweetSevice;
import com.budlee.automation.twitter.store.TweetStore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(
        classes = TwitterApplicationTests.DefaultConfig.class
)
@DirtiesContext
class TwitterApplicationTests {

    //    The time for this is Friday, 20 September 2019 02:44:27
    private static final ZoneId ZONE_UTC = ZoneId.of("UTC");
    private static final long epochTime = 1568947482;
    private static final Clock fixedClock = Clock.fixed(Instant.ofEpochSecond(epochTime), ZONE_UTC);


    @Test
    void contextLoads() {
    }

    @MockBean
    TweetStore tweetStore;

    @MockBean
    TweetSevice tweetSevice;

    @Autowired
    DelayedTwitterStoreConverter mockDelayedTwitterStoreConverter;


    @Test
    @Disabled("Needs more configuration")
    public void push_tweets_in_the_store_to_the_tweet_service() {
        final var message1 = new Tweet("Message1", LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime + 1), ZONE_UTC));
        final var message2 = new Tweet("Message2", LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime + 2), ZONE_UTC));


        Flux<Tweet> storeTweets = Flux.just(message1, message2);
        when(tweetStore.getTweets())
                .thenReturn(storeTweets);
        when(mockDelayedTwitterStoreConverter.convert(storeTweets))
                .thenReturn(storeTweets);
        StepVerifier.create(storeTweets)
                .expectNext(message1, message2)
                .expectComplete();

        verify(tweetSevice).postTweet(message1);
        verify(tweetSevice).postTweet(message2);
    }

    @Configuration
    public static class DefaultConfig {
        @Bean
        DelayedTwitterStoreConverter delayedTwitterStoreConverter(){
            return new DelayedTwitterStoreConverter(fixedClock);
        }
    }
}
