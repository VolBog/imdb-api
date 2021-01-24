package io.github.jimmydbe.imdb.filter;

import io.github.jimmydbe.imdb.domain.SearchResult;

public class MovieTypeFilter implements Predicate<SearchResult> {

    @Override
    public boolean accepts(SearchResult searchResult) {
        return searchResult.getType() != null && (
                "movie".equals(searchResult.getType().toLowerCase()) ||
                        "short".equals(searchResult.getType().toLowerCase())
        );
    }

}
