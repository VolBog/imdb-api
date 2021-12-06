package io.github.jimmydbe.imdb.exceptions;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TvEpisodeDetailsExceptionTest {

    @Test
    public void testConstructor() {
        Throwable throwable = mock(Throwable.class);
        TvEpisodeDetailsException tvEpisodeDetailsException = new TvEpisodeDetailsException("Message", throwable);
        assertEquals("Message", tvEpisodeDetailsException.getMessage());
        assertEquals(throwable, tvEpisodeDetailsException.getCause());
    }
}
