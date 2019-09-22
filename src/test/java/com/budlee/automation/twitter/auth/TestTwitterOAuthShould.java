//package com.budlee.automation.twitter.auth;
//
//import lombok.val;
//import org.apache.logging.log4j.util.Base64Util;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpMethod;
//
//import java.time.Clock;
//import java.time.Instant;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class TestTwitterOAuthShould {
//
////    @Test
////    void getSignature() {
////        assertAll(() -> {
////
////            assertEquals(
////                    "tnnArxj06cWHq44gCs1OSKk%2FjLY%3D",
////                    new TwitterOAuth("abc", "xvz1evFS4wEEPTGEFPHBog", "370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb").getSignature()
////            );
//////            assertEquals(
//////                    "tnnArxj06cWHq44gCs1OSKk%2FjLY%3D",
//////                    new TwitterOAuth("writesecret", "readsecret").getSignature()
//////            );
////
////        });
////
////    }
//
//    @Test
//    public void generateCorrectSignature() {
//        NonceGenerator mockNonce = mock(NonceGenerator.class);
//        when(mockNonce.nextNonce()).thenReturn("mockNonce");
//        Clock mockClock = mock(Clock.class);
//        Instant returnedTime = Instant.ofEpochMilli(1191242096);
//        when(mockClock.instant()).thenReturn(returnedTime);
//
//        String consumerSecret = "writesecret";
//        String token = "readsecret";
//        final var twitterOAuth = new TwitterOAuth(consumerSecret, token, mockNonce, mockClock);
////        GET&http%3A%2F%2Fphotos.example.net%2Fphotos%26file%3Dvacation.jpg%26oauth_consumer_key%3Dreadsecret%26oauth_nonce%3DmockNonce%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1191242%26oauth_token%3Dwritesecret%26oauth_version%3D1.0%26size%3Doriginal
////        GET&http%3A%2F%2Fphotos.example.net%2Fphotos%26file%3Dvacation.jpg%26oauth_consumer_key%3Dwritekey%26oauth_nonce%3Dkllo9940pd9333jh%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1191242096%26oauth_token%3Dreadkey%26oauth_version%3D1.0%26size%3Doriginal
//        Map<String, String> parameters = new LinkedHashMap<>();
//        parameters.put("size", "original");
////        final var returnedSignature = twitterOAuth.generateSignature(HttpMethod.GET, "http://photos.example.net/photos&file=vacation.jpg", parameters);
//        String expectedSignature = "Dq+QxkRpmASNSiUrwhCBbQYZuBo=";
//        final var decodedSignatureBytes = Base64.getDecoder().decode(expectedSignature);
////        System.out.println(returnedSignature);
////        assertEquals(expectedSignature, returnedSignature);
//
////        val bytes = Base64Util.decode(expectedSignature)
////        val encoded = UrlCodec.encode(signature)
//    }
//    @Test
//    public void generateCorrectSignature2() {
//        NonceGenerator mockNonce = mock(NonceGenerator.class);
//        when(mockNonce.nextNonce()).thenReturn("kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg");
//        Clock mockClock = mock(Clock.class);
//        Instant returnedTime = Instant.ofEpochMilli(1191242096);
//        when(mockClock.instant()).thenReturn(returnedTime);
//
//        String consumerSecret = "xvz1evFS4wEEPTGEFPHBog";
//        String token = "readsecret";
//        final var twitterOAuth = new TwitterOAuth(consumerSecret, token, mockNonce, mockClock);
////        GET&http%3A%2F%2Fphotos.example.net%2Fphotos%26file%3Dvacation.jpg%26oauth_consumer_key%3Dreadsecret%26oauth_nonce%3DmockNonce%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1191242%26oauth_token%3Dwritesecret%26oauth_version%3D1.0%26size%3Doriginal
////        GET&http%3A%2F%2Fphotos.example.net%2Fphotos%26file%3Dvacation.jpg%26oauth_consumer_key%3Dwritekey%26oauth_nonce%3Dkllo9940pd9333jh%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1191242096%26oauth_token%3Dreadkey%26oauth_version%3D1.0%26size%3Doriginal
//        Map<String, String> parameters = new LinkedHashMap<>();
//        parameters.put("size", "original");
////        final var returnedSignature = twitterOAuth.generateSignature(HttpMethod.GET, "http://photos.example.net/photos&file=vacation.jpg", parameters);
//        String expectedSignature = "Dq+QxkRpmASNSiUrwhCBbQYZuBo=";
//        final var decodedSignatureBytes = Base64.getDecoder().decode(expectedSignature);
////        System.out.println(returnedSignature);
////        assertEquals(expectedSignature, returnedSignature);
//
////        val bytes = Base64Util.decode(expectedSignature)
////        val encoded = UrlCodec.encode(signature)
//    }
//
//}