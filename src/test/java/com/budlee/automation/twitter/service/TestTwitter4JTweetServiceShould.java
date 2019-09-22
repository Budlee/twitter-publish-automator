package com.budlee.automation.twitter.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        properties = {
                "twitter.key.consumer.key=consumer_key",
                "twitter.key.consumer.secret=consumer_secret",
                "twitter.key.access.token=access_token",
                "twitter.key.access.secret=access_secret"
        }
)
@DirtiesContext
class TestTwitter4JTweetServiceShould {

    private static final String CONSUMER_KEY = "consumer_key";
    private static final String CONSUMER_SECRET = "consumer_secret";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String ACCESS_SECRET = "access_secret";

    @Autowired
    Twitter4JTweetService twitter4JTweetService;

    @Test
    public void use_values_passed_in_to_configure_twitter_service() {
        final var configOftwitterInstance = twitter4JTweetService.getTwitter().getConfiguration();
        assertAll(
                () -> assertEquals(CONSUMER_KEY, configOftwitterInstance.getOAuthConsumerKey()),
                () -> assertEquals(CONSUMER_SECRET, configOftwitterInstance.getOAuthConsumerSecret()),
                () -> assertEquals(ACCESS_TOKEN, configOftwitterInstance.getOAuthAccessToken()),
                () -> assertEquals(ACCESS_SECRET, configOftwitterInstance.getOAuthAccessTokenSecret())
        );
    }

}