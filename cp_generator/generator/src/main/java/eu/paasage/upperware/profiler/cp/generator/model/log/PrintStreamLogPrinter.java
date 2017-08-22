package eu.paasage.upperware.profiler.cp.generator.model.log;

import java.io.PrintStream;

public class PrintStreamLogPrinter extends LogPrinterAbstact {

    private PrintStream printStream;

    public PrintStreamLogPrinter(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    protected void printLine(String str) {
        this.printStream.println(str);
    }
}
