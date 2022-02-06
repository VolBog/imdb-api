package io.github.jimmydbe.imdb.domain;

import java.util.Set;

public class TvShowDetails {

    private final String name;
    private final double rating;
    private final int startYear;
    private final int endYear;
    //private final int duration;
    private final Set<String> genres;
    private final String plot;
    private final Set<String> creators;

    private TvShowDetails(Builder builder) {
        name = builder.name;
        rating = builder.rating;
        startYear = builder.startYear;
        endYear = builder.endYear;
        genres = builder.genres;
        plot = builder.plot;
        creators = builder.creators;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public String getPlot() {
        return plot;
    }

    public Set<String> getCreators() {
        return creators;
    }


    public static final class Builder {
        private String name;
        private double rating;
        private int startYear;
        private int endYear;
        private Set<String> genres;
        private String plot;
        private Set<String> creators;

        public Builder() {
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withRating(double val) {
            rating = val;
            return this;
        }

        public Builder withStartYear(int val) {
            startYear = val;
            return this;
        }

        public Builder withEndYear(int val) {
            endYear = val;
            return this;
        }

        public Builder withGenres(Set<String> val) {
            genres = val;
            return this;
        }

        public Builder withPlot(String val) {
            plot = val;
            return this;
        }

        public Builder withCreators(Set<String> val) {
            creators = val;
            return this;
        }

        public TvShowDetails build() {
            return new TvShowDetails(this);
        }
    }
}
