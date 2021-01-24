package io.github.jimmydbe.imdb;

import io.github.jimmydbe.imdb.commands.SearchTitleCommand;
import io.github.jimmydbe.imdb.commands.TitleCommand;
import io.github.jimmydbe.imdb.commands.TitleDetailsCommand;
import io.github.jimmydbe.imdb.domain.MovieDetails;
import io.github.jimmydbe.imdb.domain.SearchResult;
import io.github.jimmydbe.imdb.domain.TvEpisodeDetails;
import io.github.jimmydbe.imdb.domain.TvShowDetails;
import io.github.jimmydbe.imdb.exceptions.*;
import io.github.jimmydbe.imdb.filter.Predicate;
import io.github.jimmydbe.imdb.parsers.MovieDetailsPageParser;
import io.github.jimmydbe.imdb.parsers.SearchedMoviesParser;
import io.github.jimmydbe.imdb.parsers.TvEpisodeDetailsPageParser;
import io.github.jimmydbe.imdb.parsers.TvShowDetailsPageParser;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class IMDB {

    private final DocumentBuilder documentBuilder;
    private final SearchedMoviesParser searchedMoviesParser;
    private final MovieDetailsPageParser movieDetailsPageParser;
    private final TvShowDetailsPageParser tvShowDetailsPageParser;
    private final TvEpisodeDetailsPageParser tvEpisodeDetailsPageParser;

    public IMDB(DocumentBuilder documentBuilder, SearchedMoviesParser searchedMoviesParser, MovieDetailsPageParser movieDetailsPageParser, TvShowDetailsPageParser tvShowDetailsPageParser, TvEpisodeDetailsPageParser tvEpisodeDetailsPageParser) {
        this.documentBuilder = documentBuilder;
        this.searchedMoviesParser = searchedMoviesParser;
        this.movieDetailsPageParser = movieDetailsPageParser;
        this.tvShowDetailsPageParser = tvShowDetailsPageParser;
        this.tvEpisodeDetailsPageParser = tvEpisodeDetailsPageParser;
    }

    public List<SearchResult> search(String query) throws IMDBException {
        try {
            Document doc = documentBuilder.buildDocument(new SearchTitleCommand(query));
            return searchedMoviesParser.parse(doc, Optional.empty());
        } catch (IOException e) {
            throw new IMDBException("Could not find movies for name '" + query + "'", e);
        } catch (ParseException e) {
            throw new IMDBException("A parse exception occurred while searching movies for name '" + query + "'", e);
        }
    }

    public List<SearchResult> search(String movieName, final Predicate<SearchResult> searchResultFilter) throws IMDBException {
        List<SearchResult> searchResults = search(movieName);
        return searchResults.stream()
                .filter(searchResultFilter::accepts)
                .collect(Collectors.toList());
    }

    public MovieDetails getMovieDetails(String movieId) throws IMDBException {
        try {
            Document doc = documentBuilder.buildDocument(new TitleCommand(movieId));
            Document detailsDoc = documentBuilder.buildDocument(new TitleDetailsCommand(movieId));
            return movieDetailsPageParser.parse(doc, Optional.of(detailsDoc));
        } catch (Exception e) {
            throw new MovieDetailsException("Could not find movie details for id: '" + movieId + "'", e);
        }
    }

    public TvEpisodeDetails getTvEpisodeDetails(String episodeId) throws IMDBException {
        try {
            Document doc = documentBuilder.buildDocument(new TitleCommand(episodeId));
            Document detailsDoc = documentBuilder.buildDocument(new TitleDetailsCommand(episodeId));
            return tvEpisodeDetailsPageParser.parse(doc, Optional.of(detailsDoc));
        } catch (Exception e) {
            throw new TvEpisodeDetailsException("Could not find episode details for id: '" + episodeId + "'", e);
        }
    }

    public TvShowDetails getTvShowDetails(String showId) throws IMDBException {
        try {
            Document doc = documentBuilder.buildDocument(new TitleCommand(showId));
            Document detailsDoc = documentBuilder.buildDocument(new TitleDetailsCommand(showId));
            return tvShowDetailsPageParser.parse(doc, Optional.of(detailsDoc));
        } catch (Exception e) {
            throw new TvShowDetailsException("Could not find show details for id: '" + showId + "'", e);
        }
    }
}