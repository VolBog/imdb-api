package io.github.jimmydbe.imdb.domain;

import java.util.List;


public class MovieDetails {

    private final String movieName;
    private final String movieOriginalName;
    private final Integer year;
    private final String description;
    private final Double rating;
    private final List<String> directors;
    private final List<String> writers;
    private final List<String> stars;
    private final List<String> categories;
    private final String image;
    private final Integer duration;

    private MovieDetails(Builder builder) {
        movieName = builder.movieName;
        movieOriginalName = builder.movieOriginalName;
        year = builder.year;
        description = builder.description;
        rating = builder.rating;
        directors = builder.directors;
        writers = builder.writers;
        stars = builder.stars;
        categories = builder.categories;
        image = builder.image;
        duration = builder.duration;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getMovieOriginalName() {
        return movieOriginalName;
    }

    public Integer getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public Double getRating() {
        return rating;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public List<String> getStars() {
        return stars;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getImage() {
        return image;
    }

    public Integer getDuration() {
        return duration;
    }

    public static final class Builder {
        private String movieName;
        private String movieOriginalName;
        private Integer year;
        private String description;
        private Double rating;
        private List<String> directors;
        private List<String> writers;
        private List<String> stars;
        private List<String> categories;
        private String image;
        private Integer duration;

        public Builder() {
        }

        public Builder withMovieName(String val) {
            movieName = val;
            return this;
        }

        public Builder withMovieOriginalName(String val) {
            movieOriginalName = val;
            return this;
        }

        public Builder withYear(Integer val) {
            year = val;
            return this;
        }

        public Builder withDescription(String val) {
            description = val;
            return this;
        }

        public Builder withRating(Double val) {
            rating = val;
            return this;
        }

        public Builder withDirectors(List<String> val) {
            directors = val;
            return this;
        }

        public Builder withWriters(List<String> val) {
            writers = val;
            return this;
        }

        public Builder withStars(List<String> val) {
            stars = val;
            return this;
        }

        public Builder withCategories(List<String> val) {
            categories = val;
            return this;
        }

        public Builder withImage(String val) {
            image = val;
            return this;
        }

        public Builder withDuration(Integer val) {
            duration = val;
            return this;
        }

        public MovieDetails build() {
            return new MovieDetails(this);
        }
    }
}
