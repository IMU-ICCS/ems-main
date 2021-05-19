package eu.melodic.upperware.guibackend.communication.proactive;

import cloud.morphemic.connectors.proactive.IProactiveClientServiceConnector;

public interface ProactiveClientServiceGUI extends IProactiveClientServiceConnector {

    int getNumberOfCurrentOffers();
}
