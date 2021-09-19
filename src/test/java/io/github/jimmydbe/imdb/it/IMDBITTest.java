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
import java.util.Set;

import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

        assertThat(resultList.size(), is(21));
        assertThat(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterds", 2009, "Movie", "https://m.media-amazon.com/images/M/MV5BOTJiNDEzOWYtMTVjOC00ZjlmLWE0NGMtZmE1OWVmZDQ2OWJhXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_UX32_CR0,0,32,44_AL_.jpg")), is(true));
        assertThat(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterds", 2009, "Abc", "")), is(false));
        assertThat(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterds", 2008, "Movie", "")), is(false));
        assertThat(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterd", 2009, "Movie", "")), is(false));
        assertThat(resultList.contains(new SearchResult("tt1515156", "Inglourious Basterds: Movie Special", 2009, "TV Movie", "https://m.media-amazon.com/images/S/sash/85lhIiFCmSScRzu.png")), is(true));
        assertThat(resultList.contains(new SearchResult("tt0000000", "Nijntje", 0, "Abc", "")), is(false));
    }

    @Test
    public void testSearchWithFilter() throws IMDBException {

        List<SearchResult> resultList = imdb.search("inglourious basterds", new MovieTypeFilter());

        assertThat(resultList.size(), is(3));
        assertThat(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterds", 2009, "Movie", "https://m.media-amazon.com/images/M/MV5BOTJiNDEzOWYtMTVjOC00ZjlmLWE0NGMtZmE1OWVmZDQ2OWJhXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_UX32_CR0,0,32,44_AL_.jpg")), is(true));
        assertThat(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterds", 2009, "Abc", "")), is(false));
        assertThat(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterds", 2008, "Movie", "")), is(false));
        assertThat(resultList.contains(new SearchResult("tt0361748", "Inglourious Basterd", 2009, "Movie", "")), is(false));
        assertThat(resultList.contains(new SearchResult("tt0000000", "Nijntje", 0, "Abc", "")), is(false));
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
        assertThat(resultList.contains(new SearchResult("tt1714833", "Drift", 2013, "Movie", "https://m.media-amazon.com/images/M/MV5BMTkwNjgxMTQ3OF5BMl5BanBnXkFtZTcwMTU0NTY2OQ@@._V1_UX32_CR0,0,32,44_AL_.jpg")), is(true));
    }

    @Test
    public void testSearch5() throws IMDBException {

        final List<SearchResult> resultList = imdb.search("tenet");
        assertThat(resultList.contains(new SearchResult("tt6723592", "Tenet", 2020, "Movie", "https://m.media-amazon.com/images/M/MV5BYzg0NGM2NjAtNmIxOC00MDJmLTg5ZmYtYzM0MTE4NWE2NzlhXkEyXkFqcGdeQXVyMTA4NjE0NjEy._V1_UX32_CR0,0,32,44_AL_.jpg")), is(true));
    }


    @Test
    public void testFetch() throws IMDBException {
        MovieDetails movieDetails = imdb.getMovieDetails("tt0361748");

        assertThat(movieDetails.getMovieName(), is("Inglourious Basterds"));
        assertThat(movieDetails.getYear(), is(2009));
        assertThat(movieDetails.getDuration(), is(153));
        assertThat(movieDetails.getRating(), is(8.3));
        assertThat(movieDetails.getDescription(), is("In Nazi-occupied France during World War II, a plan to assassinate Nazi leaders by a group of Jewish U.S. soldiers coincides with a theatre owner's vengeful plans for the same."));
        assertThat(movieDetails.getDirectors(), is((List<String>) Lists.newArrayList("Quentin Tarantino")));
        assertThat(movieDetails.getWriters(), is((List<String>) Lists.newArrayList("Quentin Tarantino")));
        assertThat(movieDetails.getStars().contains("Brad Pitt"), is(true));
        assertThat(movieDetails.getCategories(), is((List<String>) Lists.newArrayList("Adventure", "Drama", "War")));
        assertThat(movieDetails.getImage(), is("https://m.media-amazon.com/images/M/MV5BOTJiNDEzOWYtMTVjOC00ZjlmLWE0NGMtZmE1OWVmZDQ2OWJhXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_QL75_UX190_CR0,0,190,281_.jpg"));
    }

    @Test
    public void testFetchAlternative() throws IMDBException {

        MovieDetails movieDetails = imdb.getMovieDetails("tt0477348");

        assertTrue(movieDetails.getMovieName().equals("No Country for Old Men") || movieDetails.getMovieOriginalName().equals("No Country for Old Men"));
        assertThat(movieDetails.getYear(), is(2007));
        assertThat(movieDetails.getDuration(), is(122));
        assertThat(movieDetails.getRating(), is(8.1));
        assertThat(movieDetails.getDescription(), is("Violence and mayhem ensue after a hunter stumbles upon a drug deal gone wrong and more than two million dollars in cash near the Rio Grande."));
        assertThat(movieDetails.getDirectors(), is((List<String>) Lists.newArrayList("Ethan Coen", "Joel Coen")));
        assertThat(movieDetails.getWriters(), is((List<String>) Lists.newArrayList("Joel Coen", "Ethan Coen", "Cormac McCarthy")));
        assertThat(movieDetails.getStars().size(), is(52));
        assertThat(movieDetails.getStars().contains("Tommy Lee Jones"), is(true));
        assertThat(movieDetails.getCategories(), is((List<String>) Lists.newArrayList("Crime", "Drama", "Thriller")));
    }

    @Test
    public void testFetchAlternative2() throws IMDBException {

        MovieDetails movieDetails = imdb.getMovieDetails("tt1392214");

        assertThat(movieDetails.getDuration(), is(153));
        assertThat(movieDetails.getMovieName(), is("Prisoners"));
        assertThat(movieDetails.getYear(), is(2013));
        assertThat(movieDetails.getRating(), is(8.1));
        assertThat(movieDetails.getDescription(), is("When Keller Dover's daughter and her friend go missing, he takes matters into his own hands as the police pursue multiple leads and the pressure mounts."));
        assertThat(movieDetails.getDirectors(), is((List<String>) Lists.newArrayList("Denis Villeneuve")));
        assertThat(movieDetails.getWriters(), is((List<String>) Lists.newArrayList("Aaron Guzikowski")));

        assertThat(movieDetails.getStars().size(), is(73));
        assertThat(movieDetails.getStars().contains("Hugh Jackman"), is(true));
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
        assertThat(tvEpisodeDetails.getAirDate(), is(LocalDate.of(2010, 9, 29)));
        assertThat(tvEpisodeDetails.getSeasonNumber(), is(2L));
        assertThat(tvEpisodeDetails.getEpisodeNumber(), is(4L));
        assertThat(tvEpisodeDetails.getEpisodeName(), is("Chris Hölsken/Henk Bleker/Bryan Rookhuijzen"));
        assertThat(tvEpisodeDetails.getShowName(), is("Moraalridders"));
        assertThat(tvEpisodeDetails.getGenres(), is((List<String>) Lists.newArrayList("Talk-Show")));
    }

    @Ignore("Date is country specific")
    @Test
    public void testFetchTvEpisodeInformation2() throws IMDBException {

        TvEpisodeDetails tvEpisodeDetails = imdb.getTvEpisodeDetails("tt2178802");
        assertThat(tvEpisodeDetails.getAirDate(), is(LocalDate.of(2013, 4, 14)));
        assertThat(tvEpisodeDetails.getSeasonNumber(), is(3L));
        assertThat(tvEpisodeDetails.getEpisodeNumber(), is(3L));
        assertThat(tvEpisodeDetails.getEpisodeName(), is("Walk of Punishment"));
        assertThat(tvEpisodeDetails.getShowName(), is("Game of Thrones"));
        assertThat(tvEpisodeDetails.getGenres(), is((List<String>) Lists.newArrayList("Action", "Drama", "Adventure")));
    }

    @Test
    public void testFetchTvShowInformation() throws IMDBException {

        TvShowDetails tvShowDetails = imdb.getTvShowDetails("tt0944947");

        assertThat(tvShowDetails.getName(), is("Game of Thrones"));
        assertThat(tvShowDetails.getStartYear(), is(2011));
        assertThat(tvShowDetails.getEndYear(), is(2019));
        assertThat(tvShowDetails.getPlot(), is("Nine noble families fight for control over the lands of Westeros, while an ancient enemy returns after being dormant for millennia."));
        assertThat(tvShowDetails.getRating(), is(9.2));
        assertThat(tvShowDetails.getGenres(), is((Set<String>) Sets.newHashSet("Action", "Drama", "Adventure")));
        assertTrue(tvShowDetails.getCreators().contains("David Benioff"));
        assertTrue(tvShowDetails.getCreators().contains("D.B. Weiss"));
    }

    @Test
    public void testFetchTvShowInformation2() throws IMDBException {

        TvShowDetails tvShowDetails = imdb.getTvShowDetails("tt0455275");

        assertThat(tvShowDetails.getName(), is("Prison Break"));
        assertThat(tvShowDetails.getStartYear(), is(2005));
        assertThat(tvShowDetails.getEndYear(), is(2017));
        assertThat(tvShowDetails.getPlot(), is("Due to a political conspiracy, an innocent man is sent to death row and his only hope is his brother, who makes it his mission to deliberately get himself sent to the same prison in order to break the both of them out, from the inside."));
        assertThat(tvShowDetails.getRating(), is(8.3));
        assertThat(tvShowDetails.getGenres(), is((Set<String>) Sets.newHashSet("Crime", "Action", "Drama")));

    }


}
