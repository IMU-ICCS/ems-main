package de.cas.dcsresearch.melodic.activemqtorest.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.cas.dcsresearch.melodic.activemqtorest.objects.ActiveMqStatistics;

@Service
public class ActiveMqStatisticHolder {

	@Autowired
	private ActiveMqStatistics activeMqStatistics;


	public void increaseMsgCount() {
		activeMqStatistics.setMsqCount(activeMqStatistics.getMsqCount() + 1);
	}

	public void setHasError() {
		activeMqStatistics.setHasError(true);
	}

	public ActiveMqStatistics getActiveMqStatistics() {
		return this.activeMqStatistics;
	}

}
