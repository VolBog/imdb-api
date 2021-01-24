package io.github.jimmydbe.imdb.exceptions;

public class ParseException extends Throwable {

    public ParseException(String reason) {
        super(reason);
    }

    public ParseException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
