package nl.stil4m.imdb.parsers;

import nl.stil4m.imdb.exceptions.ParseException;

import org.jsoup.nodes.Element;

import java.util.Optional;

public interface Parser<T> {

    T parse(Element document, Optional<Element> detailsDocument) throws ParseException;

}