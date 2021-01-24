package io.github.jimmydbe.imdb.parsers;

import io.github.jimmydbe.imdb.domain.SearchResult;
import io.github.jimmydbe.imdb.exceptions.ParseException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SearchedMoviesParser implements Parser<List<SearchResult>> {

    private final MovieFindResultParser movieFindResultParser;

    public SearchedMoviesParser(MovieFindResultParser movieFindResultParser) {
        this.movieFindResultParser = movieFindResultParser;
    }

    @Override
    public List<SearchResult> parse(Element document, Optional<Element> detailsDocument) throws ParseException {
        List<SearchResult> searchResultList = new ArrayList<>();

        Element element = getSearchResultsFromDocument(document);
        if (element == null) {
            return searchResultList;
        }

        Elements results = getSearchResults(element);
        try {
            for (Element result : results) {
                SearchResult searchResult = movieFindResultParser.parse(result, Optional.empty());
                searchResultList.add(searchResult);
            }
        } catch (ParseException e) {
            throw new ParseException("Could not parse search results", e);
        }
        return searchResultList;
    }

    private Elements getSearchResults(Element element) {
        return element.select(".findList .findResult");
    }

    private Element getSearchResultsFromDocument(Element document) {
        Elements findSections = document.select("#main .article .findSection");

        if (findSections.size() != 1) {
            //This happens when there are no search results
            return null;
        }
        return findSections.get(0);
    }

}