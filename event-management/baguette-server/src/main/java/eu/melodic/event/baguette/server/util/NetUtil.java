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

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
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

    // ------------------------------------------------------------------------

    public final static String[][] SERVICES = {
            { "AWS", "http://checkip.amazonaws.com" },
            { "Ipify", "https://api.ipify.org/?format=text" },
            { "WhatIsMyIpAddress", "http://bot.whatismyipaddress.com/" }
    };

    public static String getPublicIpAddress() {
        for (String[] service : SERVICES) {
            log.debug("NetUtil.getPublicIpAddress(): Contacting service {}", service[0]);
            String ip = getIpAddressUsingService(service[1]);
            if (ip!=null && ip.length()>0) return ip.trim();
        }
        return null;
    }

    private static String getIpAddressUsingService(String url) {
        try {
            log.debug("NetUtil.getIpAddressUsingService(): Service URL: {}", url);
            String response = queryService(url);
            log.debug("NetUtil.getIpAddressUsingService(): Service response: {}", response);
            if (response!=null && !response.trim().isEmpty()) return response;
            log.debug("NetUtil.getIpAddressUsingService(): Response is null or blank");
        } catch (Exception ex) {
            log.debug("NetUtil.getIpAddressUsingService(): Contacting service failed: url={}, exception={}", url, ex);
        }
        return null;
    }

    private static String queryService(String url) throws MalformedURLException, IOException {
        try (Scanner s = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A")) {
            return s.next().trim();
        }
    }

    // ------------------------------------------------------------------------

    public final static String DATAGRAM_ADDRESS = "8.8.8.8";

    public static String getDefaultIpAddress() {
        try {
            log.debug("NetUtil.getDefaultIpAddress(): Datagram address: {}", DATAGRAM_ADDRESS);
            String address = getIpAddressWithDatagram(DATAGRAM_ADDRESS);
            log.debug("NetUtil.getDefaultIpAddress(): Response: {}", address);
            if (address!=null && !address.trim().isEmpty()) return address;
            log.debug("NetUtil.getDefaultIpAddress(): Address is null or blank");
        } catch (Exception ex) {
            log.debug("NetUtil.getDefaultIpAddress(): Datagram method failed: outgoing-ip-address={}, exception={}", DATAGRAM_ADDRESS, ex);
        }
        return null;
    }

    public static String getIpAddressWithDatagram(String address) throws SocketException, UnknownHostException {
        try(final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName(address), 10002);
            return socket.getLocalAddress().getHostAddress();
        }
    }
}
