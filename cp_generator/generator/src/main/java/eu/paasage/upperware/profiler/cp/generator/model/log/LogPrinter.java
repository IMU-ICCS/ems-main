package eu.paasage.upperware.profiler.cp.generator.model.log;

import org.apache.commons.lang3.tuple.Pair;

public interface LogPrinter {

    void printMessage(Pair<String, String>... pairs);

    void printMessage(boolean withEndingLine, Pair<String, String>... pairs);

    void printMessage(String header, boolean withEndingLine, Pair<String, String>... pairs);

    void printHeader(String header);

    void printEndingLine();

    void printPair(Pair<String, String> pair);

}
