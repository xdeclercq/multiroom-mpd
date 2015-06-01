package com.autelhome.multiroom.errors;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ErrorCodeTest {

    @Test
    public void valueOf() throws Exception {
        assertThat(ErrorCode.valueOf("INVALID_RESOURCE")).isEqualTo(ErrorCode.INVALID_RESOURCE);
    }

}