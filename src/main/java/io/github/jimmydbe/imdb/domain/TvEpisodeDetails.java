package io.github.jimmydbe.imdb.domain;

import java.time.LocalDate;
import java.util.List;


public class TvEpisodeDetails {

    private final String showName;
    private final String episodeName;
    private final long seasonNumber;
    private final long episodeNumber;
    private final List<String> genres;
    private final LocalDate airDate;

    private TvEpisodeDetails(Builder builder) {
        showName = builder.showName;
        episodeName = builder.episodeName;
        seasonNumber = builder.seasonNumber;
        episodeNumber = builder.episodeNumber;
        genres = builder.genres;
        airDate = builder.airDate;
    }

    public String getShowName() {
        return showName;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public long getSeasonNumber() {
        return seasonNumber;
    }

    public long getEpisodeNumber() {
        return episodeNumber;
    }

    public List<String> getGenres() {
        return genres;
    }

    public LocalDate getAirDate() {
        return airDate;
    }


    public static final class Builder {
        private String showName;
        private String episodeName;
        private long seasonNumber;
        private long episodeNumber;
        private List<String> genres;
        private LocalDate airDate;

        public Builder() {
        }

        public Builder withShowName(String val) {
            showName = val;
            return this;
        }

        public Builder withEpisodeName(String val) {
            episodeName = val;
            return this;
        }

        public Builder withSeasonNumber(long val) {
            seasonNumber = val;
            return this;
        }

        public Builder withEpisodeNumber(long val) {
            episodeNumber = val;
            return this;
        }

        public Builder withGenres(List<String> val) {
            genres = val;
            return this;
        }

        public Builder withAirDate(LocalDate val) {
            airDate = val;
            return this;
        }

        public TvEpisodeDetails build() {
            return new TvEpisodeDetails(this);
        }
    }
}
