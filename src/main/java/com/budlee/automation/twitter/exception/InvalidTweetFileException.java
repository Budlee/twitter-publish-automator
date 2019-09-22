package com.budlee.automation.twitter.exception;

public class InvalidTweetFileException extends RuntimeException {
    public InvalidTweetFileException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidTweetFileException(String errorMessage, Exception exception) {
        super(errorMessage, exception);
    }
}
