package com.budlee.automation.twitter.scheduler;

import com.budlee.automation.twitter.model.Tweet;
import com.google.common.base.Preconditions;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

@Component
@Log
public class DelayedTwitterStoreConverter {

    private Clock clock;

    public DelayedTwitterStoreConverter() {
        this(Clock.systemUTC());
    }

    public DelayedTwitterStoreConverter(Clock clock) {
        this.clock = clock;
    }

    public Flux<Tweet> convert(@NotNull Flux<Tweet> tweets) {
        Preconditions.checkArgument(Objects.nonNull(tweets));
        return tweets
                .filter(this::tweetIsAfterNow)
                .sort(Comparator.comparing(Tweet::getDateTime))
                .flatMap(this::mapToDelayedFlux);
    }

    private boolean tweetIsAfterNow(Tweet tweet) {
        return tweet.getDateTime().isAfter(LocalDateTime.now(clock));
    }

    private Flux<Tweet> mapToDelayedFlux(Tweet tweet) {
        final var between = Duration.between(LocalDateTime.now(clock), tweet.getDateTime());
        log.info(
                String.format("Tweet will be in [%s] seconds with message [%s]",
                        between.getSeconds(),
                        tweet.getStatus())
        );
        return Flux.just(tweet).delayElements(between);
    }
}
