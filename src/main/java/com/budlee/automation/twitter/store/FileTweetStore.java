package com.budlee.automation.twitter.store;

import com.budlee.automation.twitter.exception.InvalidTweetFileException;
import com.budlee.automation.twitter.model.Tweet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Collectors;

@Component
public class FileTweetStore implements TweetStore {
    private final Flux<Tweet> tweetStoreFlux;

    public FileTweetStore(@Value("${tweet.store.file}") String file) throws FileNotFoundException {
        if (!Files.exists(Paths.get(file))) {
            throw new FileNotFoundException(String.format("The tweet store file [%s] does not exist", file));
        }

        try {
            final var tweetStore = Files.readAllLines(Paths.get(file))
                    .stream()
                    .map(this::splitCsvElements)
                    .map(this::elementsToTweet)
                    .collect(Collectors.toList());
            if (tweetStore.size() <= 0) {
                throw new InvalidTweetFileException(String.format("No Tweets found in file [%s]", file));
            }
            this.tweetStoreFlux = Flux.fromIterable(tweetStore);
        } catch (IOException e) {
            throw new InvalidTweetFileException("Tweet file is invalid", e);
        }

    }

    private Tweet elementsToTweet(TweetCsvElements tweetCsvElements) {
        final var tweetStatus = tweetCsvElements.getMessage();
        if (tweetStatus.length() > Tweet.MAX_CHARACTER_LENGTH) {
            throw new InvalidTweetFileException(
                    String.format("Tweet status length is [%s]. This is greater than the allowed [%s] max length [%s]",
                            tweetStatus.length(),
                            Tweet.MAX_CHARACTER_LENGTH,
                            tweetStatus)
            );
        }
        final var epochLong = Long.parseLong(tweetCsvElements.getStrEpochLong());
        final var parsedInstant = Instant.ofEpochSecond(epochLong);
        return new Tweet(
                tweetStatus,
                LocalDateTime.ofInstant(parsedInstant, ZoneId.of("UTC"))
        );
    }

    private TweetCsvElements splitCsvElements(String csvLine) {
        final var csvSplit = csvLine.split(",");
        if (csvSplit.length != 2) {
            throw new InvalidTweetFileException(
                    String.format("The Tweet has too many commas [%s]", csvLine)
            );
        }
        return new TweetCsvElements(csvSplit[0], csvSplit[1]);
    }

    @Override
    public Flux<Tweet> getTweets() {
        return tweetStoreFlux;
    }

    @lombok.Value
    private class TweetCsvElements {
        private final String message;
        private final String strEpochLong;
    }
}
