package eu.melodic.upperware.activemqtorest.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.melodic.upperware.activemqtorest.entry.ActiveMqStatistics;

@Service
public class ActiveMqStatisticHolder {

	@Autowired
	private ActiveMqStatistics activeMqStatistics;


	public void increaseMsgCount() {
		activeMqStatistics.setMsqCount(activeMqStatistics.getMsqCount() + 1);
	}

	public void increaseErrorCount() {
		activeMqStatistics.setErrorCount(activeMqStatistics.getErrorCount() + 1);
	}

	public ActiveMqStatistics getActiveMqStatistics() {
		return this.activeMqStatistics;
	}

}
