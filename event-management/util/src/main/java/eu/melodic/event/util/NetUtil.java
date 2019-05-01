/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Network Utility
 */
@Slf4j
public class NetUtil {

    public final static String[] addressFilter = {
            "127.",
            /*"192.168.", "10.", "172.16.", "172.31.", "169.254.",*/
            "224.", "239.", "255.255.255.255"
    };

    public final static String DATAGRAM_ADDRESS = "8.8.8.8";

    public final static String[][] SERVICES = {
            { "AWS", "http://checkip.amazonaws.com" },
            { "Ipify", "https://api.ipify.org/?format=text" },
            { "WhatIsMyIpAddress", "http://bot.whatismyipaddress.com/" }
    };

    // ------------------------------------------------------------------------

    protected static boolean cacheAddresses = true;

    public static boolean isCacheAddresses() { return cacheAddresses; }
    public static void setCacheAddresses(boolean b) { cacheAddresses = b; }

    public static void clearCaches() {
        ipAddresses = null;
        publicIpAddress = null;
        defaultIpAddress = null;
    }

    // ------------------------------------------------------------------------

    private static List<InetAddress> ipAddresses = null;

    public static List<InetAddress> getIpAddresses() throws SocketException {
        if (cacheAddresses && ipAddresses!=null) {
            log.debug("NetUtil.getIpAddresses(): Returning cached IP addresses: {}", ipAddresses);
            return ipAddresses;
        }

        Vector<InetAddress> list = new Vector<>();
        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface ni = en.nextElement();
            for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                InetAddress inet = ia.getAddress();
                if (inet instanceof java.net.Inet4Address) {
                    String addr = inet.getHostAddress();
                    if (!inet.isLoopbackAddress() && !inet.isMulticastAddress() && inet.isSiteLocalAddress()) {
                        boolean ok = Arrays.stream(addressFilter)
                                .noneMatch(addr::startsWith);
                        if (ok) {
                            log.debug("{}", addr);
                            list.add(inet);
                        }
                    }
                }
            }
        }
        if (cacheAddresses) ipAddresses = Collections.unmodifiableList(list);
        return list;
    }

    public static String getIpAddress() {
        try {
            List<InetAddress> list = getIpAddresses();
            if (list.size() == 0) {
                log.debug("NetUtil.getIpAddress(): Returning 'null' because getIpAddresses() returned an empty list");
                return null;
            }
            return list.get(0).getHostAddress();
        } catch (SocketException se) {
            log.debug("NetUtil.getIpAddress(): Returning 'null' due to exception: ", se);
            return null;
        }
    }

    // ------------------------------------------------------------------------

    private static String publicIpAddress = null;

    public static String getPublicIpAddress() {
        if (cacheAddresses && publicIpAddress!=null) {
            log.debug("NetUtil.getPublicIpAddress(): Returning cached Public IP address: {}", publicIpAddress);
            return publicIpAddress;
        }

        for (String[] service : SERVICES) {
            log.debug("NetUtil.getPublicIpAddress(): Contacting service {}", service[0]);
            String ip = getIpAddressUsingService(service[1]);
            if (StringUtils.isNotBlank(ip)) {
                String addr = ip.trim();
                if (cacheAddresses) publicIpAddress = addr;
                log.debug("NetUtil.getPublicIpAddress(): Public IP address: {}", addr);
                return addr;
            }
        }
        if (cacheAddresses) publicIpAddress = "";

        log.warn("NetUtil.getPublicIpAddress(): No Public IP address or connectivity problems exist");
        return null;
    }

    private static String getIpAddressUsingService(String url) {
        try {
            log.debug("NetUtil.getIpAddressUsingService(): Service URL: {}", url);
            String response = queryService(url);
            log.debug("NetUtil.getIpAddressUsingService(): Service response: {}", response);
            if (StringUtils.isNotBlank(response)) {
                return response;
            }
        } catch (Exception ex) {
            log.warn("NetUtil.getIpAddressUsingService(): Contacting service FAILED: url={}, EXCEPTION={}", url, ex.toString());
            log.trace("NetUtil.getIpAddressUsingService(): Exception stack trace: ", ex);
        }

        log.debug("NetUtil.getIpAddressUsingService(): Response is null or blank");
        return null;
    }

    private static String queryService(String url) throws MalformedURLException, IOException {
        try (Scanner s = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A")) {
            return s.next().trim();
        }
    }

    // ------------------------------------------------------------------------

    private static String defaultIpAddress = null;

    public static String getDefaultIpAddress() {
        if (cacheAddresses && defaultIpAddress!=null) {
            log.debug("NetUtil.getDefaultIpAddress(): Returning cached Default IP address: {}", defaultIpAddress);
            return defaultIpAddress;
        }

        try {
            log.debug("NetUtil.getDefaultIpAddress(): Datagram address: {}", DATAGRAM_ADDRESS);
            String addr = getIpAddressWithDatagram(DATAGRAM_ADDRESS);
            if (cacheAddresses) defaultIpAddress = addr;
            log.debug("NetUtil.getDefaultIpAddress(): Response: {}", addr);
            if (StringUtils.isNotBlank(defaultIpAddress)) return addr;
        } catch (Exception ex) {
            log.warn("NetUtil.getDefaultIpAddress(): Datagram method failed: outgoing-ip-address={}, exception=", DATAGRAM_ADDRESS, ex);
            if (cacheAddresses) defaultIpAddress = "";
        }

        log.warn("NetUtil.getDefaultIpAddress(): Address is null or blank");
        return null;
    }

    public static String getIpAddressWithDatagram(String address) throws SocketException, UnknownHostException {
        try(final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName(address), 10002);
            return socket.getLocalAddress().getHostAddress();
        }
    }
}
