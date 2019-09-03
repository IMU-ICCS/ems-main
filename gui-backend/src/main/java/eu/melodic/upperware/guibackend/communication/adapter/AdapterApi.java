package eu.melodic.upperware.guibackend.communication.adapter;

import eu.melodic.models.services.adapter.DifferenceRequestImpl;
import eu.melodic.models.services.adapter.DifferenceResponse;

public interface AdapterApi {

    DifferenceResponse getDifference(DifferenceRequestImpl differenceRequest, String token);
}
