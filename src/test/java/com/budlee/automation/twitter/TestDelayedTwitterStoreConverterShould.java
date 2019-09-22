package com.budlee.automation.twitter;

import com.budlee.automation.twitter.model.Tweet;
import com.budlee.automation.twitter.scheduler.DelayedTwitterStoreConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TestDelayedTwitterStoreConverterShould {

    //    The time for this is Friday, 20 September 2019 02:44:27
    private final ZoneId ZONE_UTC = ZoneId.of("UTC");
    private final long epochTime = 1568947482;

    Clock fixedClock = Clock.fixed(Instant.ofEpochSecond(epochTime), ZONE_UTC);

    private DelayedTwitterStoreConverter twitterConverter;

    @BeforeEach
    public void setUp() {
        this.twitterConverter = new DelayedTwitterStoreConverter(fixedClock);
    }

    @Test
    public void return_stored_events_in_date_time_order() {
        final var message1 = new Tweet("Message1", LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime + 1), ZONE_UTC));
        final var message2 = new Tweet("Message2", LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime + 2), ZONE_UTC));
        final var message3 = new Tweet("Message3", LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime + 3), ZONE_UTC));
        final var message4 = new Tweet("Message4", LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime + 4), ZONE_UTC));

        final var results = twitterConverter.convert(Flux.just(
                message3,
                message1,
                message4,
                message2
        ));

        StepVerifier.create(results)
                .expectNext(message1)
                .thenAwait()
                .expectNext(message2)
                .thenAwait()
                .expectNext(message3)
                .thenAwait()
                .expectNext(message4)
                .expectComplete()
                .verify();
    }

    @Test
    @Tag("Need to verify")
    public void return_stored_events_with_correct_delayed_time() {
        final var message1 = new Tweet("Message1", LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime + 1), ZONE_UTC));
        final var message2 = new Tweet("Message2", LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime + 2), ZONE_UTC));
        final var message3 = new Tweet("Message3", LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime + 3), ZONE_UTC));
        final var message4 = new Tweet("Message4", LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime + 4), ZONE_UTC));

        final var results = twitterConverter.convert(Flux.just(
                message3,
                message1,
                message2,
                message4
        ));
//        Supplier<? extends Publisher<?>> pub = () -> results;
//        StepVerifier.withVirtualTime(pub)
        StepVerifier.create(results)
                .expectNext(message1)
                .thenAwait()
                .expectNext(message2)
                .thenAwait()
                .expectNext(message3)
                .thenAwait()
                .expectNext(message4)
                .expectComplete()
                .verify();
    }

    @Test
    public void return_an_empty_flux_when_all_events_in_the_past() {
        final var results = twitterConverter.convert(Flux.just(
                new Tweet("Message1", LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime - 1), ZONE_UTC)),
                new Tweet("Message2", LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime - 2), ZONE_UTC))
        ));
        StepVerifier.create(results)
                .expectComplete()
                .verify();
    }

    @Test
    public void filter_items_in_the_past_and_future() {
        final var pastEvent = new Tweet("Message1", LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime - 1), ZONE_UTC));
        final var futureTweet = new Tweet("Message2", LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTime + 2), ZONE_UTC));
        final var results = twitterConverter.convert(Flux.just(
                futureTweet,
                pastEvent
        ));
        StepVerifier.create(results)
                .expectNext(futureTweet)
                .expectComplete()
                .verify();
    }

    @Test
    public void throw_an_illegal_argument_exception_when_tweets_are_null() {
        assertThrows(
                IllegalArgumentException.class,
                () -> twitterConverter.convert(null)
        );
    }

    @Test
    public void return_an_empty_flux_if_passed_in_tweets_are_empty() {
        final var results = twitterConverter.convert(Flux.empty());
        StepVerifier.create(results)
                .expectComplete()
                .verify();

    }
}