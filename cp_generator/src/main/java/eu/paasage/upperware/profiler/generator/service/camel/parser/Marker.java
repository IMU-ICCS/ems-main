package eu.paasage.upperware.profiler.generator.service.camel.parser;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mariuszgromada.math.mxparser.mXparser;
import org.mariuszgromada.math.mxparser.parsertokens.Token;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Marker {
    List<Token> tokens;

    @Override
    public String toString() {
        mXparser.consolePrintTokens(tokens);
        return "size: " + tokens.size();
    }

    public int getTokenLevel(){
        return tokens.get(0).tokenLevel;
    }

    public static Marker copyOf(Marker marker) {
        return new Marker(new ArrayList<>(marker.getTokens()));
    }

}