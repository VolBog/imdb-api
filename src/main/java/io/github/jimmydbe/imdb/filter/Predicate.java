package io.github.jimmydbe.imdb.filter;

public interface Predicate<T> {

    boolean accepts(T input);
}
