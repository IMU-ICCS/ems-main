package de.cas.dcsresearch.melodic.activemqtorest.objects;

import org.springframework.stereotype.Component;

@Component
public class ActiveMqStatistics {

	private long msqCount = 0;
	private Boolean hasError = false;

	public long getMsqCount() {
		return msqCount;
	}

	public void setMsqCount(long msqCount) {
		this.msqCount = msqCount;
	}

	public Boolean getHasError() {
		return hasError;
	}

	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}

	@Override
	public String toString() {
		return "ActiveMqStatistics{" +
				"msqCount=" + msqCount +
				", hasError=" + hasError +
				'}';
	}

}
