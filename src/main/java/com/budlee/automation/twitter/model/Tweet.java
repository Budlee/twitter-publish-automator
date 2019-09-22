package com.budlee.automation.twitter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tweet {

    public static final int MAX_CHARACTER_LENGTH = 240;
    private String status;
    private LocalDateTime dateTime;
}
