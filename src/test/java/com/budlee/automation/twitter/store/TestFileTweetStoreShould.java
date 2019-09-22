package com.budlee.automation.twitter.store;

import com.budlee.automation.twitter.exception.InvalidTweetFileException;
import com.budlee.automation.twitter.model.Tweet;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TestFileTweetStoreShould {
    //first message,1568947482
    //third message,1568947484
    //second message,1568947483
    private static final Tweet TWEET_1 = new Tweet("first message", LocalDateTime.ofInstant(Instant.ofEpochSecond(1568947482), ZoneId.of("UTC")));
    private static final Tweet TWEET_3 = new Tweet("third message", LocalDateTime.ofInstant(Instant.ofEpochSecond(1568947484), ZoneId.of("UTC")));
    private static final Tweet TWEET_2 = new Tweet("second message", LocalDateTime.ofInstant(Instant.ofEpochSecond(1568947483), ZoneId.of("UTC")));

    @Test
    public void throw_file_not_found_exception_if_file_does_not_exist() {
        assertThrows(FileNotFoundException.class, () -> new FileTweetStore("does_not_exist.csv"));
    }

    @Test
    public void throw_illegal_exception_if_file_is_not_a_valid_csv() {
        assertThrows(InvalidTweetFileException.class, () -> new FileTweetStore("src/test/resources/invalid_file_format.csv"));
    }

    @Test
    public void throw_exception_if_file_is_empty() {
        assertThrows(InvalidTweetFileException.class, () -> new FileTweetStore("src/test/resources/empty_file.csv"));
    }

    @Test
    public void throw_exception_if_file_contains_illegal_date() {
        assertThrows(InvalidTweetFileException.class, () -> new FileTweetStore("src/test/resources/illegal_date.csv"));
    }

    @Test
    @Tag("Should be updated to whatever the new character size is")
    public void throw_exception_if_tweet_message_is_longer_than_240_characters() {
        assertThrows(InvalidTweetFileException.class, () -> new FileTweetStore("src/test/resources/tweet_length_too_long.csv"));
    }

    @Test
    public void read_in_all_tweets_from_a_file() throws FileNotFoundException {
        final var fileTweetStore = new FileTweetStore("src/test/resources/valid.csv");
        final var tweets = fileTweetStore.getTweets();
        StepVerifier.create(tweets)
                .expectNext(TWEET_1, TWEET_3, TWEET_2)
                .verifyComplete();
    }
}