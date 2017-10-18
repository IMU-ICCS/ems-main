package eu.melodic.upperware.dlm.monitor;

import org.springframework.boot.ExitCodeGenerator;

public class ExitException extends RuntimeException implements ExitCodeGenerator {

	@Override
	public int getExitCode() {
		return 0;
	}

}