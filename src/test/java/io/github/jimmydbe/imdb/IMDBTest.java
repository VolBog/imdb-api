package io.github.jimmydbe.imdb;

import com.google.common.collect.Lists;
import io.github.jimmydbe.imdb.commands.Command;
import io.github.jimmydbe.imdb.commands.SearchTitleCommand;
import io.github.jimmydbe.imdb.commands.TitleDetailsCommand;
import io.github.jimmydbe.imdb.domain.MovieDetails;
import io.github.jimmydbe.imdb.domain.SearchResult;
import io.github.jimmydbe.imdb.exceptions.IMDBException;
import io.github.jimmydbe.imdb.exceptions.ParseException;
import io.github.jimmydbe.imdb.filter.Predicate;
import io.github.jimmydbe.imdb.parsers.MovieDetailsPageParser;
import io.github.jimmydbe.imdb.parsers.SearchedMoviesParser;
import io.github.jimmydbe.imdb.parsers.TvEpisodeDetailsPageParser;
import io.github.jimmydbe.imdb.parsers.TvShowDetailsPageParser;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

public class IMDBTest {

    private IMDB imdb;

    @Mock
    private DocumentBuilder documentBuilder;

    @Mock
    private SearchedMoviesParser searchedMoviesParser;

    @Mock
    private MovieDetailsPageParser movieDetailsPageParser;

    @Mock
    private TvShowDetailsPageParser tvShowDetailsPageParser;

    @Mock
    private TvEpisodeDetailsPageParser tvEpisodeDetailsPageParser;

    @Mock
    private Document document;

    @Mock
    private Document detailsDocument;

    @Captor
    private ArgumentCaptor<Command> commandCaptor;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        imdb = new IMDB(documentBuilder, searchedMoviesParser, movieDetailsPageParser, tvShowDetailsPageParser, tvEpisodeDetailsPageParser);
    }

    @org.junit.Test
    public void testSearch() throws IMDBException, IOException, ParseException {
        List<SearchResult> answer = Lists.newArrayList(mock(SearchResult.class), mock(SearchResult.class), mock(SearchResult.class));
        when(documentBuilder.buildDocument(isA(Command.class))).thenReturn(document);
        when(searchedMoviesParser.parse(document, Optional.empty())).thenReturn(answer);
        List<SearchResult> result = imdb.search("someQuery");

        verify(documentBuilder).buildDocument(commandCaptor.capture());

        assertThat(commandCaptor.getValue() instanceof SearchTitleCommand, is(true));
        assertThat(result, is(answer));
    }

    @org.junit.Test
    public void testSearchWithFilter() throws IMDBException, IOException, ParseException {
        SearchResult first = mock(SearchResult.class);
        SearchResult second = mock(SearchResult.class);

        @SuppressWarnings("unchecked")
        Predicate<SearchResult> predicate = mock(Predicate.class);
        when(predicate.accepts(first)).thenReturn(true);
        when(predicate.accepts(second)).thenReturn(false);

        List<SearchResult> answer = Lists.newArrayList(first, second);
        when(documentBuilder.buildDocument(isA(Command.class))).thenReturn(document);
        when(searchedMoviesParser.parse(document, Optional.empty())).thenReturn(answer);

        List<SearchResult> result = imdb.search("someQuery", predicate);

        verify(documentBuilder).buildDocument(commandCaptor.capture());
        assertThat(commandCaptor.getValue() instanceof SearchTitleCommand, is(true));
        assertThat(result, is((List<SearchResult>) Lists.newArrayList(first)));
    }

    @Test
    public void testSearchWithIOException() throws IOException {
        doThrow(IOException.class).when(documentBuilder).buildDocument(isA(Command.class));

        try {
            imdb.search("someQuery");
            fail();
        } catch (IMDBException e) {
            assertThat(e.getCause() instanceof IOException, is(true));
            assertThat(e.getMessage(), is("Could not find movies for name 'someQuery'"));
        }
    }

    @Test
    public void testSearchWithParseException() throws IOException {
        given(documentBuilder.buildDocument(isA(Command.class))).willAnswer(invocation -> {
            throw new ParseException("");
        });

        try {
            imdb.search("someQuery");
            fail();
        } catch (IMDBException e) {
            assertThat(e.getCause() instanceof ParseException, is(true));
            assertThat(e.getMessage(), is("A parse exception occurred while searching movies for name 'someQuery'"));
        }
    }

    @Test
    public void testGetMovieDetails() throws IOException, IMDBException {
        MovieDetails answer = mock(MovieDetails.class);
        when(documentBuilder.buildDocument(isA(Command.class))).thenReturn(document);
        when(documentBuilder.buildDocument(isA(TitleDetailsCommand.class))).thenReturn(detailsDocument);

        when(movieDetailsPageParser.parse(document, Optional.of(detailsDocument))).thenReturn(answer);

        MovieDetails result = imdb.getMovieDetails("someId");

        verify(documentBuilder, times(2)).buildDocument(commandCaptor.capture());

        assertThat(commandCaptor.getValue() instanceof TitleDetailsCommand, is(true));
        assertThat(((TitleDetailsCommand) commandCaptor.getValue()).getId(), is("someId"));
        assertThat(result, is(answer));

    }
}
