package io.github.jimmydbe.imdb.parsers;

import io.github.jimmydbe.imdb.exceptions.ParseException;
import org.jsoup.nodes.Element;

import java.util.Optional;

public interface Parser<T> {

    T parse(Element document, Optional<Element> detailsDocument) throws ParseException;

}