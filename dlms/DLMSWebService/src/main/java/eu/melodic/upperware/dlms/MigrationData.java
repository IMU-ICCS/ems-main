package eu.melodic.upperware.dlms;


/**
 * Wraps migration information such as Source and destination path or datasource id to be migrated.
 */
public class MigrationData {

    private long id;
    private String pathFrom;
    private String pathTo;

    public MigrationData() {
    }

    public MigrationData(String pathFrom, String pathTo) {
        this.pathFrom = pathFrom;
        this.pathTo = pathTo;
    }

    public MigrationData(long id, String pathFrom, String pathTo) {
        this.id = id;
        this.pathFrom = pathFrom;
        this.pathTo = pathTo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPathFrom() {
        return pathFrom;
    }

    public void setPathFrom(String pathFrom) {
        this.pathFrom = pathFrom;
    }

    public String getPathTo() {
        return pathTo;
    }

    public void setPathTo(String pathTo) {
        this.pathTo = pathTo;
    }
}
