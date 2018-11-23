package eu.melodic.dlms.algorithms.affinity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
public class AppComDataSrc {
	private long appCompId; // application component
	private long dataSrcId; // data source
	private long expDataTransfer; // expected transfer
	private double normExpDataTransfer; // normalized expected transfer
	private long numTransfer; // number transfer
	private double normNumTransfer; // normalized transfer

	private double coupletValue; // final couple value for each application component and data source

	public AppComDataSrc(long appCompId, long dataSrcId, long expDataTransfer, long numTransfer) {
		this.appCompId = appCompId;
		this.dataSrcId = dataSrcId;
		this.expDataTransfer = expDataTransfer;
		this.numTransfer = numTransfer;
	}

}
