package io.github.jimmydbe.imdb.exceptions;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.mockito.Mockito.mock;

public class TvShowDetailsExceptionTest {

    @Test
    public void testConstructor() {
        Throwable throwable = mock(Throwable.class);
        TvShowDetailsException tvShowDetailsException = new TvShowDetailsException("Message", throwable);
        Assertions.assertEquals("Message", tvShowDetailsException.getMessage());
        Assertions.assertEquals(throwable, tvShowDetailsException.getCause());
    }
}
