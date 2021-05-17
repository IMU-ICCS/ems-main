package eu.melodic.upperware.guibackend.communication.proactive;

import cloud.morphemic.connectors.proactive.IProactiveClientServiceConnector;

public interface ProactiveClientServiceForGUI extends IProactiveClientServiceConnector {

    int getNumberOfCurrentOffers();
}
