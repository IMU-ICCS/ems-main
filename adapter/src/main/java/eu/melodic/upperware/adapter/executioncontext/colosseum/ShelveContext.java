package eu.melodic.upperware.adapter.executioncontext.colosseum;

import eu.melodic.upperware.adapter.executioncontext.ContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@Service
public class ShelveContext extends ContextUtils {

    private List<ShelveJob> shelveJobs = synchronizedList();
    private List<ShelveSchedule> shelveSchedules = synchronizedList();


    public void addShelveJob(ShelveJob shelveJob){
        shelveJobs.add(shelveJob);
    }

    public Optional<ShelveJob> getShelveJobByName(String name){
        return getShelveJob(shelveJob -> name.equals(shelveJob.getJobName()));
    }

    private Optional<ShelveJob> getShelveJob(Predicate<ShelveJob> predicate) {
        return shelveJobs
                .stream()
                .filter(predicate)
                .findFirst();
    }

    public void addShelveSchedule(ShelveSchedule shelveSchedule){
        shelveSchedules.add(shelveSchedule);
    }

    public Optional<ShelveSchedule> getShelveScheduleByJobId(String jobId){
        return getShelveSchedule(shelveSchedule -> jobId.equals(shelveSchedule.getJobId()));
    }

    private Optional<ShelveSchedule> getShelveSchedule(Predicate<ShelveSchedule> predicate) {
        return shelveSchedules
                .stream()
                .filter(predicate)
                .findFirst();
    }
}
