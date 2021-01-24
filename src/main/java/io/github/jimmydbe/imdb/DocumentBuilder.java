package io.github.jimmydbe.imdb;

import io.github.jimmydbe.imdb.commands.Command;
import io.github.jimmydbe.imdb.constants.IMDBConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DocumentBuilder {

    public Document buildDocument(Command command) throws IOException {
        String string = IMDBConstants.ROOT_URL + command.getUrlExtension();
        return Jsoup.connect(string)
                .cookie("beta-control", "tmd=in").timeout(30000).get();
    }

}
