package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Schedule extends Feature {
    private Date start;
    private Date end;
    private String timeUnit;
    private long interval;
    private int repetitions;

    public long getIntervalInMillis() {
        if (timeUnit == null) return interval;
        return TimeUnit.MILLISECONDS.convert(interval, TimeUnit.valueOf(timeUnit.toUpperCase()));
    }
}
