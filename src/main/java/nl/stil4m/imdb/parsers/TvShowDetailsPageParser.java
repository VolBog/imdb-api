package nl.stil4m.imdb.parsers;

import nl.stil4m.imdb.domain.TvShowDetails;
import nl.stil4m.imdb.util.ElementUtil;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TvShowDetailsPageParser implements Parser<TvShowDetails> {

    private static final String CREATORS = "TvShowDetailsPageParser.creators";
    private static final String NAME = "TvShowDetailsPageParser.name";
    private static final String PLOT = "TvShowDetailsPageParser.plot";
    private static final String DURATION = "TvShowDetailsPageParser.duration";
    private static final String GENRES = "TvShowDetailsPageParser.genres";
    private static final String YEAR = "TvShowDetailsPageParser.year";
    private static final String RATING = "TvShowDetailsPageParser.rating";

    private static final char separatorChar = 8211;
    private static final Pattern YEAR_PATTERN = Pattern.compile("\\d{4}");

    private final Properties properties;
    private final ElementUtil elementUtil;

    public TvShowDetailsPageParser(Properties properties, ElementUtil elementUtil) {
        this.properties = properties;
        this.elementUtil = elementUtil;
    }

    @Override
    public TvShowDetails parse(Element document, Optional<Element> detailsDocument) {
        String plot = getPlot(document);
        String name = getName(document);
        Double rating = getRating(document);
        Integer startYear = getStartYear(document);
        Integer endYear = getEndYear(document);
        //Integer duration = getDuration(document);
        Set<String> genres = getGenres(document);
        Set<String> creators = getCreators(detailsDocument.get());
        return new TvShowDetails(name, rating, startYear, endYear, genres, plot, creators);
    }

    private Set<String> getCreators(Element document) {
        Set<String> writers = new HashSet<>();
        document.select(properties.get(CREATORS).toString()).next().select("tbody").forEach(element -> {
            element.children().select("td.name").forEach(element1 ->  writers.add(element1.text()));
        });

        return writers;
    }

    private Set<String> getGenres(Element document) {
        List<String> answer = new ArrayList<>();
        Collections.addAll(answer, document.select(properties.get(GENRES).toString()).text().split("(?=\\p{Upper})"));
        return answer.stream().map(n -> n.trim()).collect(Collectors.toSet());
    }

    private Integer getEndYear(Element document) {
        String yearString = document.select(properties.get(YEAR).toString()).text();
        Matcher matcher = YEAR_PATTERN.matcher(yearString);
        matcher.find();
        matcher.find();

        String endYear = matcher.group();
        if (endYear.equals(" ")) {
            return -1;
        }
        return Integer.parseInt(endYear);
    }

    private Integer getStartYear(Element document) {
        final String yearString = document.select(properties.get(YEAR).toString()).text();

        Matcher matcher = YEAR_PATTERN.matcher(yearString);
        matcher.find();
        return Integer.parseInt(matcher.group());
    }

    private Double getRating(Element document) {
        return Double.parseDouble(document.select(properties.get(RATING).toString()).text());
    }

    private String getName(Element document) {
        return document.select(properties.get(NAME).toString()).text();
    }

    private String getPlot(Element document) {
        return document.select(properties.get(PLOT).toString()).text();
    }

}
