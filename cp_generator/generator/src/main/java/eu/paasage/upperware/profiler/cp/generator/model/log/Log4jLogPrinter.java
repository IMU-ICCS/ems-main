package eu.paasage.upperware.profiler.cp.generator.model.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4jLogPrinter extends LogPrinterAbstact {

    private Logger logger;
    private Level level;

    public Log4jLogPrinter(Logger logger){
        this(logger, Level.INFO);
    }

    public Log4jLogPrinter(Logger logger, Level level) {
        this.logger = logger;
        this.level = level;
    }

    @Override
    protected void printLine(String str) {
        if (Level.ERROR.equals(level)){
            logger.error(str);
        } else if (Level.INFO.equals(level)){
            logger.info(str);
        } else if (Level.DEBUG.equals(level)){
            logger.debug(str);
        } else if (Level.TRACE.equals(level)){
            logger.trace(str);
        } else if (Level.FATAL.equals(level)){
            logger.warn(str);
        } else {
            logger.info(str);
        }
    }
}
