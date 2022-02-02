package io.github.jimmydbe.imdb.it;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.github.jimmydbe.imdb.IMDB;
import io.github.jimmydbe.imdb.IMDBFactory;
import io.github.jimmydbe.imdb.domain.MovieDetails;
import io.github.jimmydbe.imdb.domain.SearchResult;
import io.github.jimmydbe.imdb.domain.TvEpisodeDetails;
import io.github.jimmydbe.imdb.domain.TvShowDetails;
import io.github.jimmydbe.imdb.exceptions.IMDBException;
import io.github.jimmydbe.imdb.exceptions.MovieDetailsException;
import io.github.jimmydbe.imdb.filter.MovieTypeFilter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import static junit.framework.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

@Category(IntegrationTest.class)
public class IMDBITTest {

    private IMDB imdb;

    @Before
    public void before() {
        Properties properties = new Properties();

        try {
            InputStream inputStream = getClass().getResourceAsStream("/io/github/jimmydbe/imdb/parsing.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            fail();
        }

        imdb = new IMDBFactory().createInstance(properties);
    }

    @Test
    public void testSearch() throws IMDBException {

        List<SearchResult> resultList = imdb.search("inglourious basterds");

        assertEquals(28, resultList.size());
        assertTrue(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterds", 2009, "Movie", "https://m.media-amazon.com/images/M/MV5BOTJiNDEzOWYtMTVjOC00ZjlmLWE0NGMtZmE1OWVmZDQ2OWJhXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_UX32_CR0,0,32,44_AL_.jpg")));
        assertFalse(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterds", 2009, "Abc", "")));
        assertFalse(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterds", 2008, "Movie", "")));
        assertFalse(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterd", 2009, "Movie", "")));
        assertTrue(resultList.contains(new SearchResult("tt1515156", "Inglourious Basterds: Movie Special", 2009, "TV Movie", "https://m.media-amazon.com/images/S/sash/85lhIiFCmSScRzu.png")));
        assertFalse(resultList.contains(new SearchResult("tt0000000", "Nijntje", 0, "Abc", "")));
    }

    @Test
    public void testSearchWithFilter() throws IMDBException {

        List<SearchResult> resultList = imdb.search("inglourious basterds", new MovieTypeFilter());

        assertEquals(3, resultList.size());
        assertTrue(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterds", 2009, "Movie", "https://m.media-amazon.com/images/M/MV5BOTJiNDEzOWYtMTVjOC00ZjlmLWE0NGMtZmE1OWVmZDQ2OWJhXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_UX32_CR0,0,32,44_AL_.jpg")));
        assertFalse(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterds", 2009, "Abc", "")));
        assertFalse(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterds", 2008, "Movie", "")));
        assertFalse(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterd", 2009, "Movie", "")));
        assertFalse(resultList.contains(new SearchResult("tt0000000", "Nijntje", 0, "Abc", "")));
    }

    @Test
    public void testSearch2() throws IMDBException {

        imdb.search("inglourious basterds");
    }

    @Test
    public void testSearch3() throws IMDBException {

        imdb.search("drift");
    }

    @Test
    public void testSearch4() throws IMDBException {

        List<SearchResult> resultList = imdb.search("drift");
        assertTrue(resultList.contains(new SearchResult("tt1714833", "Drift", 2013, "Movie", "https://m.media-amazon.com/images/M/MV5BMTkwNjgxMTQ3OF5BMl5BanBnXkFtZTcwMTU0NTY2OQ@@._V1_UX32_CR0,0,32,44_AL_.jpg")));
    }

    @Test
    public void testSearch5() throws IMDBException {

        final List<SearchResult> resultList = imdb.search("tenet");
        assertTrue(resultList.contains(new SearchResult("tt6723592", "Tenet", 2020, "Movie", "https://m.media-amazon.com/images/M/MV5BYzg0NGM2NjAtNmIxOC00MDJmLTg5ZmYtYzM0MTE4NWE2NzlhXkEyXkFqcGdeQXVyMTA4NjE0NjEy._V1_UX32_CR0,0,32,44_AL_.jpg")));
    }


    @Test
    public void testFetch() throws IMDBException {
        MovieDetails movieDetails = imdb.getMovieDetails("tt0361748");

        assertEquals("Inglourious Basterds", movieDetails.getMovieName());
        assertEquals(2009, movieDetails.getYear());
        assertEquals(153, movieDetails.getDuration());
        assertEquals(8.3, movieDetails.getRating());
        assertEquals("In Nazi-occupied France during World War II, a plan to assassinate Nazi leaders by a group of Jewish U.S. soldiers coincides with a theatre owner's vengeful plans for the same.", movieDetails.getDescription());
        assertEquals(Lists.newArrayList("Quentin Tarantino"), movieDetails.getDirectors());
        assertEquals(Lists.newArrayList("Quentin Tarantino"), movieDetails.getWriters());
        assertTrue(movieDetails.getStars().contains("Brad Pitt"));
        assertEquals(Lists.newArrayList("Adventure", "Drama", "War"), movieDetails.getCategories());
        assertEquals("https://m.media-amazon.com/images/M/MV5BOTJiNDEzOWYtMTVjOC00ZjlmLWE0NGMtZmE1OWVmZDQ2OWJhXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_QL75_UX190_CR0,0,190,281_.jpg", movieDetails.getImage());
    }

    @Test
    public void testFetchAlternative() throws IMDBException {

        MovieDetails movieDetails = imdb.getMovieDetails("tt0477348");

        assertTrue(movieDetails.getMovieName().equals("No Country for Old Men") || movieDetails.getMovieOriginalName().equals("No Country for Old Men"));
        assertEquals(2007, movieDetails.getYear());
        assertEquals(122, movieDetails.getDuration());
        assertEquals(8.1, movieDetails.getRating());
        assertEquals("Violence and mayhem ensue after a hunter stumbles upon a drug deal gone wrong and more than two million dollars in cash near the Rio Grande.", movieDetails.getDescription());
        assertEquals(Lists.newArrayList("Ethan Coen", "Joel Coen"), movieDetails.getDirectors());
        assertEquals(Lists.newArrayList("Joel Coen", "Ethan Coen", "Cormac McCarthy"), movieDetails.getWriters());
        assertEquals(52, movieDetails.getStars().size());
        assertTrue(movieDetails.getStars().contains("Tommy Lee Jones"));
        assertEquals(Lists.newArrayList("Crime", "Drama", "Thriller"), movieDetails.getCategories());
    }

    @Test
    public void testFetchAlternative2() throws IMDBException {

        MovieDetails movieDetails = imdb.getMovieDetails("tt1392214");

        assertEquals(153, movieDetails.getDuration());
        assertEquals("Prisoners", movieDetails.getMovieName());
        assertEquals(2013, movieDetails.getYear());
        assertEquals(8.1, movieDetails.getRating());
        assertEquals("When Keller Dover's daughter and her friend go missing, he takes matters into his own hands as the police pursue multiple leads and the pressure mounts.", movieDetails.getDescription());
        assertEquals(Lists.newArrayList("Denis Villeneuve"), movieDetails.getDirectors());
        assertEquals(Lists.newArrayList("Aaron Guzikowski"), movieDetails.getWriters());

        assertEquals(73, movieDetails.getStars().size());
        assertTrue(movieDetails.getStars().contains("Hugh Jackman"));
    }

    @Test
    public void testFetchAlternative3() throws IMDBException {

        MovieDetails movieDetails = imdb.getMovieDetails("tt2310332");
        assertTrue(movieDetails.getMovieName().equals("The Hobbit: The Battle of the Five Armies") ||
                movieDetails.getMovieOriginalName().equals("The Hobbit: The Battle of the Five Armies"));
    }

    @Test
    public void testFetchAlternative4() throws IMDBException {

        imdb.getMovieDetails("tt1535438");
    }

    @Test
    public void testFetchAlternative4a() throws IMDBException {

        imdb.getTvShowDetails("tt1615919");
    }

    @Test(expected = MovieDetailsException.class)
    public void testFetchAlternative5() throws IMDBException {

        imdb.getMovieDetails("tt1871715");
    }

    @Test
    public void testFetchTvEpisodeInformation() throws IMDBException {

        TvEpisodeDetails tvEpisodeDetails = imdb.getTvEpisodeDetails("tt1871715");
        assertEquals(LocalDate.of(2010, 9, 29), tvEpisodeDetails.getAirDate());
        assertEquals(2L, tvEpisodeDetails.getSeasonNumber());
        assertEquals(4L, tvEpisodeDetails.getEpisodeNumber());
        assertEquals("Chris Hölsken/Henk Bleker/Bryan Rookhuijzen", tvEpisodeDetails.getEpisodeName());
        assertEquals("Moraalridders", tvEpisodeDetails.getShowName());
        assertEquals(Lists.newArrayList("Talk-Show"), tvEpisodeDetails.getGenres());
    }

    @Ignore("Date is country specific")
    @Test
    public void testFetchTvEpisodeInformation2() throws IMDBException {

        TvEpisodeDetails tvEpisodeDetails = imdb.getTvEpisodeDetails("tt2178802");
        assertEquals(LocalDate.of(2013, 4, 14), tvEpisodeDetails.getAirDate());
        assertEquals(3L, tvEpisodeDetails.getSeasonNumber());
        assertEquals(3L, tvEpisodeDetails.getEpisodeNumber());
        assertEquals("Walk of Punishment", tvEpisodeDetails.getEpisodeName());
        assertEquals("Game of Thrones", tvEpisodeDetails.getShowName());
        assertEquals(Lists.newArrayList("Action", "Drama", "Adventure"), tvEpisodeDetails.getGenres());
    }

    @Test
    public void testFetchTvShowInformation() throws IMDBException {

        TvShowDetails tvShowDetails = imdb.getTvShowDetails("tt0944947");

        assertEquals("Game of Thrones", tvShowDetails.getName());
        assertEquals(2011, tvShowDetails.getStartYear());
        assertEquals(2019, tvShowDetails.getEndYear());
        assertEquals("Nine noble families fight for control over the lands of Westeros, while an ancient enemy returns after being dormant for millennia.", tvShowDetails.getPlot());
        assertEquals(9.2, tvShowDetails.getRating());
        assertEquals(Sets.newHashSet("Action", "Drama", "Adventure"), tvShowDetails.getGenres());
        assertTrue(tvShowDetails.getCreators().contains("David Benioff"));
        assertTrue(tvShowDetails.getCreators().contains("D.B. Weiss"));
    }

    @Test
    public void testFetchTvShowInformation2() throws IMDBException {

        TvShowDetails tvShowDetails = imdb.getTvShowDetails("tt0455275");

        assertEquals("Prison Break", tvShowDetails.getName());
        assertEquals(2005, tvShowDetails.getStartYear());
        assertEquals(2017, tvShowDetails.getEndYear());
        assertEquals("Due to a political conspiracy, an innocent man is sent to death row and his only hope is his brother, who makes it his mission to deliberately get himself sent to the same prison in order to break the both of them out, from the inside.", tvShowDetails.getPlot());
        assertEquals(8.3, tvShowDetails.getRating());
        assertEquals(Sets.newHashSet("Crime", "Action", "Drama"), tvShowDetails.getGenres());

    }


}
