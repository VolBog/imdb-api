package io.github.jimmydbe.imdb.domain;

import java.util.Objects;

public class SearchResult {

    private final String id;
    private final String name;
    private final int year;
    private final String type;
    private final String thumbnail;

    public SearchResult(String id, String name, int year, String type, String thumbnail) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.type = type;
        this.thumbnail = thumbnail;
    }

    private SearchResult(Builder builder) {
        id = builder.id;
        name = builder.name;
        year = builder.year;
        type = builder.type;
        thumbnail = builder.thumbnail;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchResult that = (SearchResult) o;
        return year == that.year && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(thumbnail, that.thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, type, thumbnail);
    }

    public static final class Builder {
        private String id;
        private String name;
        private int year;
        private String type;
        private String thumbnail;

        public Builder() {
        }

        public Builder withId(String val) {
            id = val;
            return this;
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withYear(int val) {
            year = val;
            return this;
        }

        public Builder withType(String val) {
            type = val;
            return this;
        }

        public Builder withThumbnail(String val) {
            thumbnail = val;
            return this;
        }

        public SearchResult build() {
            return new SearchResult(this);
        }
    }
}
