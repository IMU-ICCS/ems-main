package eu.paasage.upperware.profiler.cp.generator.model.log;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;

public abstract class LogPrinterAbstact implements LogPrinter{

    private static final String LINE = "*************************************************";
    private static final String VALUE_PREFIX = "    -> ";

    @Override
    public void printMessage(Pair<String, String>[] pairs) {
        printMessage(false, pairs);
    }

    @Override
    public void printMessage(boolean withEndingLine, Pair<String, String>[] pairs) {
        printMessage(null, withEndingLine, pairs);
    }

    @Override
    public void printMessage(String header, boolean withEndingLine, Pair<String, String>[] pairs) {
        if (StringUtils.isNotBlank(header)) {
            printHeader(header);
        }

        Arrays.stream(pairs).forEach(this::printPair);

        if (withEndingLine) {
            printEndingLine();
        }
    }

    @Override
    public void printHeader(String header) {
        printLine();
        printLine(LINE);
        printLine(header);
        printLine(LINE);
        printLine();
    }

    @Override
    public void printEndingLine() {
        printLine();
        printLine(LINE);
    }

    @Override
    public void printPair(Pair<String, String> pair) {
        if (StringUtils.isNotBlank(pair.getKey())){
            printLine(pair.getKey());
        }
        if (StringUtils.isNotBlank(pair.getValue())){
            printLine(VALUE_PREFIX + pair.getValue());
        }
    }

    private void printLine(){
        printLine("");
    }

    protected abstract void printLine(String str);
}
