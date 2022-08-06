package io.github.jimmydbe.imdb.parsers;

import io.github.jimmydbe.imdb.domain.MovieDetails;
import io.github.jimmydbe.imdb.util.ElementUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

public class MovieDetailsPageParser implements Parser<MovieDetails> {

    private static final String IMAGE = "MovieDetailsPageParser.image";
    private static final String CATEGORIES = "MovieDetailsPageParser.categories";
    private static final String DESCRIPTION = "MovieDetailsPageParser.description";
    private static final String RATING = "MovieDetailsPageParser.rating";
    private static final String WRITERS = "MovieDetailsPageParser.writers";
    private static final String DIRECTORS = "MovieDetailsPageParser.directors";
    private static final String YEAR = "MovieDetailsPageParser.year";
    private static final String NAME_TITLE_HEADER = "MovieDetailsPageParser.nameTitleHeader";
    private static final String NAME_TITLE_EXTRA = "MovieDetailsPageParser.nameTitleExtra";
    private static final String NAME_TITLE_NORMAL = "MovieDetailsPageParser.nameTitleNormal";
    private static final String NAME_TITLE_ORIGINAL = "MovieDetailsPageParser.nameTitleOriginal";
    private static final String STARS = "MovieDetailsPageParser.stars";
    private static final String DURATION = "MovieDetailsPageParser.duration";

    private final ElementUtil elementUtil;
    private final Properties properties;


    public MovieDetailsPageParser(ElementUtil elementUtil, Properties properties) {
        this.elementUtil = elementUtil;
        this.properties = properties;
    }

    @Override
    public MovieDetails parse(Element document, Optional<Element> detailsDocument) {
        String movieName = parseMovieName(document);
        String movieOriginalName = parseMovieOriginalName(document);
        Integer year = parseMovieYear(document);
        String description = parseDescription(document);
        Double rating = parseRating(document);
        List<String> directors = detailsDocument.map(this::parseDirectors).orElse(new ArrayList<>());
        List<String> writers = detailsDocument.map(this::parseWriters).orElse(new ArrayList<>());
        List<String> stars = detailsDocument.map(this::parseStars).orElse(new ArrayList<>());
        List<String> categories = parseCategories(document);
        String image = parseImage(document);
        Integer duration = parseDuration(document);

        return new MovieDetails.Builder()
                .withMovieName(movieName)
                .withMovieOriginalName(movieOriginalName)
                .withYear(year)
                .withDescription(description)
                .withRating(rating)
                .withDirectors(directors)
                .withWriters(writers)
                .withStars(stars)
                .withCategories(categories)
                .withImage(image)
                .withDuration(duration)
                .build();
    }

    private String parseMovieOriginalName(Element document) {
        return document.select(properties.get(NAME_TITLE_ORIGINAL).toString()).text().replace("Original title: ", "");
    }

    private Integer parseDuration(Element document) {
        String durationString = document.select(properties.get(DURATION).toString()).text();

        durationString = durationString.replace("Runtime ", "");

        int duration = 0;

        if (durationString.contains("hours") || durationString.contains("hour")) {
            final String hours = durationString.substring(0, durationString.indexOf("h")).trim();
            duration += Integer.parseInt(hours) * 60;
            durationString = durationString.substring(durationString.indexOf("h") + 5).trim();
        } else if (durationString.contains("h")) {
            final String hours = durationString.substring(0, durationString.indexOf("h")).trim();
            duration += Integer.parseInt(hours) * 60;
            durationString = durationString.substring(durationString.indexOf("h")).trim();
        }

        if (durationString.contains("minutes")) {
            final String minutes = durationString.replace("minutes", "").trim();
            duration += Integer.parseInt(minutes);
        } else if (durationString.contains("minute")) {
            final String minutes = durationString.replace("minute", "").trim();
            duration += Integer.parseInt(minutes);
        } else if (durationString.contains("min")) {
            int startPosition = durationString.indexOf(" ") != -1 ? durationString.indexOf(" ") : 0;

            duration += Integer.parseInt(durationString.substring(startPosition, durationString.length() - 3).trim());
        }

        return duration;
    }

    private String parseImage(Element document) {
        return document.select(properties.get(IMAGE).toString()).select("img").attr("src");
    }

    private List<String> parseCategories(Element document) {
        List<String> answer = new ArrayList<>();
        Collections.addAll(answer, document.select(properties.get(CATEGORIES).toString()).text().split("(?=\\p{Upper})"));
        return answer.stream().map(String::trim).collect(Collectors.toList());
    }

    private String parseDescription(Element document) {
        return document.select(properties.get(DESCRIPTION).toString()).text();
    }

    private Double parseRating(Element document) {
        Elements elements = document.select(properties.get(RATING).toString());
        //Elements elements = document.select(".AggregateRatingButton__ContentWrap-sc-1ll29m0-0");
        String value = elements.get(0).text().trim();
        if (value.length() > 0) {
            return Double.parseDouble(value);
        } else {
            return null;
        }
    }

    private List<String> parseWriters(Element document) {
        List<String> writers = new ArrayList<>();
        document.select(properties.get(WRITERS).toString()).next().select("tbody").forEach(element ->
                element.children().select("td.name").forEach(element1 -> writers.add(element1.text())));

        return writers;
    }

    private List<String> parseDirectors(Element document) {
        List<String> directors = new ArrayList<>();

        document.select(properties.get(DIRECTORS).toString()).next().select("tbody").forEach(element -> {
            element.children().select("td.name").forEach(element1 -> directors.add(element1.text()));
        });

        return directors;
    }

    private List<String> parseStars(Element document) {
        List<String> stars = new ArrayList<>();

        document.select(properties.get(STARS).toString()).next().select("tbody").forEach(element -> {
            element.children().select("td").not("td.character").not("td.ellipsis").forEach(element1 -> stars.add(element1.text()));
        });

        return stars.stream().map(String::trim).filter(n -> !n.isEmpty()).collect(Collectors.toList());
    }

    private String parseMovieName(Element document) {
        Elements header = document.select(properties.get(NAME_TITLE_HEADER).toString());
        if (header.select(properties.get(NAME_TITLE_EXTRA).toString()).size() > 0) {
            String text = header.select(properties.get(NAME_TITLE_EXTRA).toString()).get(0).ownText();
            if (text.startsWith("\"")) {
                text = text.substring(1, text.length() - 1);
            }
            return text;
        }
        String title = document.select(properties.get(NAME_TITLE_NORMAL).toString()).text();

        return title.contains("(") ? title.substring(0, title.indexOf("(")).trim() : title.trim();
    }

    private Integer parseMovieYear(Element document) {
        String yearString = document.select(properties.get(YEAR).toString()).text().trim();
        return Integer.parseInt(yearString);
    }

}
