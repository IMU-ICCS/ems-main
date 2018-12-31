/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 * Event Management Server
 */
@Slf4j
public class NetUtil {
    protected final static String[] addressFilter = {"127.", /*"192.168.", "10.", "172.16.", "172.31.", "169.254.",*/ "224.", "239.", "255.255.255.255"};

    public static List<InetAddress> getIpAddresses() throws SocketException {
        Vector<InetAddress> list = new Vector<>();
        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface ni = en.nextElement();
			/*log.info("NI > {}", ni);
			if (ni.isUp() && !ni.isLoopback() && !ni.isVirtual() && !ni.isPointToPoint()) {
				log.info("    --> ok");
			}*/
            for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                InetAddress inet = ia.getAddress();
                if (inet instanceof java.net.Inet4Address) {
                    String addr = inet.getHostAddress();
                    if (!inet.isLoopbackAddress() && !inet.isMulticastAddress() && inet.isSiteLocalAddress()) {
                        boolean ok = true;
                        for (int i = 0, n = addressFilter.length; i < n; i++) {
                            if (addr.startsWith(addressFilter[i])) ok = false;
                        }
                        if (ok) {
                            //log.info("{}  {}  {}  {}  {}", addr, inet.isLoopbackAddress(), inet.isMulticastAddress(), inet.isSiteLocalAddress(), inet.isLinkLocalAddress());
                            log.debug("{}", addr);
                            list.add(inet);
                        }
                    }
                }
            }
        }
        return list;
    }

    public static String getIpAddress() {
        try {
            List<InetAddress> list = getIpAddresses();
            if (list.size() == 0) return null;
            return list.get(0).getHostAddress();
        } catch (SocketException se) {
            return null;
        }
    }
}
