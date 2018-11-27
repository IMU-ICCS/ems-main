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

    private List<ShelveNode> shelveNodes = synchronizedList();
    private List<ShelveJob> shelveJobs = synchronizedList();
    private List<ShelveSchedule> shelveSchedules = synchronizedList();

    public void addShelveNode(ShelveNode shelveNode){
        shelveNodes.add(shelveNode);
    }

    public Optional<ShelveNode> getShelveNodeById(String id){
      return getShelveNode(shelveNode -> id.equals(shelveNode.getId()));
    }

    public Optional<ShelveNode> getShelveNodeByName(String name){
        return getShelveNode(shelveNode -> shelveNode.getNodeName().endsWith(name));
    }

    private Optional<ShelveNode> getShelveNode(Predicate<ShelveNode> predicate) {
        return shelveNodes
                .stream()
                .filter(predicate)
                .findFirst();
    }

    public void addShelveJob(ShelveJob shelveJob){
        shelveJobs.add(shelveJob);
    }

    public Optional<ShelveJob> getShelveJobById(String id){
        return getShelveJob(shelveJob -> id.equals(shelveJob.getId()));
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

    public Optional<ShelveSchedule> getShelveScheduleById(String id){
        return getShelveSchedule(shelveSchedule -> id.equals(shelveSchedule.getId()));
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
