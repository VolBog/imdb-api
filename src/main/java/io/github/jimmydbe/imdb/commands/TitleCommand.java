package io.github.jimmydbe.imdb.commands;

import java.io.UnsupportedEncodingException;

public class TitleCommand implements Command {

    private final String id;

    public TitleCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getUrlExtension() throws UnsupportedEncodingException {
        return "/title/" + id;
    }

}
