package eu.paasage.upperware.profiler.generator.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CpGenerationResult {

    private CpGenerationStatus status;
    private String cpModelPath;
    private String msg;

    public static CpGenerationResult succes(String cpModelPath){
        CpGenerationResult result = new CpGenerationResult();
        result.setStatus(CpGenerationStatus.SUCCESS);
        result.setCpModelPath(cpModelPath);
        return result;
    }

    public static CpGenerationResult info(String msg){
        CpGenerationResult result = new CpGenerationResult();
        result.setStatus(CpGenerationStatus.INFO);
        result.setMsg(msg);
        return result;
    }

    public static CpGenerationResult error(String msg){
        CpGenerationResult result = new CpGenerationResult();
        result.setStatus(CpGenerationStatus.ERROR);
        result.setMsg(msg);
        return result;
    }

    public enum CpGenerationStatus{
        SUCCESS, INFO, ERROR
    }
}
