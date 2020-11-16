package com.loki.iam;

import com.google.common.truth.Truth;
import org.junit.jupiter.api.Test;


public class PathTrieTest {

    @Test
    public void testGetMatchingPrefix_CompleteMatch() {
        PathTrie dict = new PathTrie();
        dict.insert("/v1/*/login");
        dict.insert("/v1/**");
        dict.insert("/v1/user/login");

        // Give
        String input = "/v1/user/login";
        // When
        String actual = dict.getMatchingPrefix(input);
        // Then
        Truth.assertThat(actual).isEqualTo("/v1/user/login");
    }

    @Test
    public void testGetMatchingPrefix_OneAsteriskMatch() {
        PathTrie dict = new PathTrie();
        dict.insert("/v1/*/login");
        dict.insert("/v1/**");

        // Give
        String input = "/v1/user/login";
        // When
        String actual = dict.getMatchingPrefix(input);
        // Then
        Truth.assertThat(actual).isEqualTo("/v1/*/login");
    }

    @Test
    public void testGetMatchingPrefix_TwoAsteriskMatch() {
        PathTrie dict = new PathTrie();
        dict.insert("/v1/**");

        // Give
        String input = "/v1/user/login";
        // When
        String actual = dict.getMatchingPrefix(input);
        // Then
        Truth.assertThat(actual).isEqualTo("/v1/**");
    }
}