package io.github.jimmydbe.imdb.exceptions;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class ParseExceptionTest {

    @Test
    public void testReasonConstructor() {
        ParseException parseException = new ParseException("Some Reason");
        assertNull(parseException.getCause());
        assertEquals("Some Reason", parseException.getMessage());
    }

    @Test
    public void testReasonAndCauseConstructor() {
        Throwable throwable = mock(Throwable.class);
        ParseException parseException = new ParseException("Some Reason", throwable);
        assertEquals(throwable, parseException.getCause());
        assertEquals("Some Reason", parseException.getMessage());
    }
}
