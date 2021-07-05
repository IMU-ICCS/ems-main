/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokerclient;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.google.gson.Gson;
import eu.melodic.event.brokerclient.event.EventGenerator;
import eu.melodic.event.brokerclient.event.EventMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;

import javax.jms.*;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
public class BrokerClientApp {

    private static boolean filterAMQMessages = true;
    private static boolean isRecording = false;
    private static File recordFile;
    private static Writer recordWriter;
    private static RECORD_FORMAT recordFormat;
    private static CSVPrinter csvPrinter;
    private static JsonGenerator jsonGenerator;
    private static long playbackInterval;
    private static long playbackDelay;

    private enum RECORD_FORMAT { CSV, JSON }

    public static void main(String args[]) throws java.io.IOException, JMSException, ScriptException {
        log.info("Broker Client for EMS, v.{}", BrokerClientApp.class.getPackage().getImplementationVersion());
        if (args.length==0) {
            usage();
            return;
        }

        int aa=0;
        String command = args[aa++];

        filterAMQMessages = args.length>aa && args[aa].startsWith("-Q") ? false : true;
        if (!filterAMQMessages) aa++;

        String username = args.length>aa && args[aa].startsWith("-U") ? args[aa++].substring(2) : null;
        String password = username!=null && args.length>aa && args[aa].startsWith("-P") ? args[aa++].substring(2) : null;
        if (StringUtils.isNotBlank(username) && password == null) {
            password = new String(System.console().readPassword("Enter broker password: "));
        }

        if ("record".equalsIgnoreCase(command)) {
            isRecording = true;
            command = "receive";
        }

        // list destinations
        if ("list".equalsIgnoreCase(command)) {
            String url = args[aa++];
            log.info("BrokerClientApp: Listing destinations:");
            BrokerClient client = BrokerClient.newClient(username, password);
            client.getDestinationNames(url).stream().forEach(d -> log.info("    {}", d));
        } else
        // send an event
        if ("publish".equalsIgnoreCase(command)) {
            String url = args[aa++];
            String topic = args[aa++];
            String value = args[aa++];
            String level = args[aa++];
            EventMap event = new EventMap(Double.parseDouble(value), Integer.parseInt(level), System.currentTimeMillis());
            log.info("BrokerClientApp: Publishing event: {}", event);
            BrokerClient client = BrokerClient.newClient(username, password);
            client.publishEvent(url, topic, event);
        } else
        if ("publish2".equalsIgnoreCase(command)) {
            String url = args[aa++];
            String topic = args[aa++];
            String payload = args[aa++];
            payload = payload
                    .replaceAll("%TIMESTAMP%|%TS%", ""+System.currentTimeMillis());
            EventMap event = new Gson().fromJson(payload, EventMap.class);
            log.info("BrokerClientApp: Publishing event: {}", event);
            BrokerClient client = BrokerClient.newClient(username, password);
            client.publishEvent(url, topic, event);
            log.info("BrokerClientApp: Event payload: {}", payload);
        } else
        // receive events from topic
        if ("receive".equalsIgnoreCase(command)) {
            String url = args[aa++];
            String topic = args[aa++];

            if (isRecording)
                initRecording(args, aa);

            log.info("BrokerClientApp: Subscribing to topic: {}", topic);
            BrokerClient client = BrokerClient.newClient(username, password);
            client.receiveEvents(url, topic, getMessageListener());
        } else
        // playback events
        if ("playback".equalsIgnoreCase(command)) {
            String url = args[aa++];
            initPlayback(args, aa);
            playbackEvents(url, username, password);
        } else
        // subscribe to topic
        if ("subscribe".equalsIgnoreCase(command)) {
            String url = args[aa++];
            String topic = args[aa++];
            log.info("BrokerClientApp: Subscribing to topic: {}", topic);
            BrokerClient client = BrokerClient.newClient(username, password);
            MessageListener listener = null;
            client.subscribe(url, topic, listener = getMessageListener());

            log.info("BrokerClientApp: Hit ENTER to exit");
            try {
                System.in.read();
            } catch (Exception e) {}
            log.info("BrokerClientApp: Closing connection...");

            client.unsubscribe(listener);
            client.closeConnection();
            log.info("BrokerClientApp: Exiting...");

        } else
        // start event generator
        if ("generator".equalsIgnoreCase(command)) {
            String url = args[aa++];
            String topic = args[aa++];
            long interval = Long.parseLong(args[aa++]);
            long howmany = Long.parseLong(args[aa++]);
            double lowerValue = Double.parseDouble(args[aa++]);
            double upperValue = Double.parseDouble(args[aa++]);
            int level = Integer.parseInt(args[aa++]);

            BrokerClient client = BrokerClient.newClient();
            client.openConnection(url, username, password, true);
            EventGenerator generator = new EventGenerator();
            generator.setClient(client);
            generator.setBrokerUrl(url);
            generator.setDestinationName(topic);
            generator.setInterval(interval);
            generator.setHowmany(howmany);
            generator.setLowerValue(lowerValue);
            generator.setUpperValue(upperValue);
            generator.setLevel(level);
            generator.run();
            client.closeConnection();
        } else
        // Run JS script
        if ("js".equalsIgnoreCase(command)) {
            ScriptEngineManager manager = new ScriptEngineManager();
            String engineName = "nashorn";
            if (aa<args.length && args[aa].startsWith("-E")) {
                String tmp = args[aa].substring(2).trim();
                if (StringUtils.isNotBlank(tmp)) engineName = tmp;
                else {
                    log.info("Available Script engines:");
                    manager.getEngineFactories().forEach(s->{
                        log.info("  Engine: {} {}, {}, Language: {} {}, Mime: {}, Ext: {}",
                            s.getEngineName(), s.getEngineVersion(), s.getNames(),
                            s.getLanguageName(), s.getLanguageVersion(),
                            s.getMimeTypes(), s.getExtensions());
                    });
                }
                aa++;
            }

            ScriptEngine engine = manager.getEngineByName(engineName);
            Bindings bindings = engine.createBindings();
            String scriptFile = args[aa++];

            ArrayList<String> jsArgs = new ArrayList<>();
            for (; aa<args.length; aa++) jsArgs.add(args[aa]);
            bindings.put("args", jsArgs);

            engine.eval(
                    "var BrokerClient = Java.type('eu.melodic.event.brokerclient.BrokerClient');\n" +
                    "var EventMap = Java.type('eu.melodic.event.brokerclient.event.EventMap');\n" +
                    "var System = Java.type('java.lang.System');\n" +
                    "load('"+scriptFile+"')",
                    bindings
            );

        } else
        // error
        {
            log.error("BrokerClientApp: Unknown command: {}", command);
            usage();
        }
    }

    private static MessageListener getMessageListener() {
        return message -> {
            try {
                String destinationName = getDestinationName(message);
                if (filterAMQMessages && StringUtils.startsWithIgnoreCase(destinationName, "ActiveMQ.")) {
                    log.trace("BrokerClientApp:  - {}: ActiveMQ message filtered out: {}", destinationName, message);
                    log.debug("AMQ: {}:\n{}", destinationName, message);
                    return;
                }
                if (message instanceof ObjectMessage) {
                    ObjectMessage objMessage = (ObjectMessage) message;
                    Object obj = objMessage.getObject();
                    log.trace("BrokerClientApp:  - {}: Received object message: {}", destinationName, obj);
                    log.info("OBJ: {}:\n{}", destinationName, obj);
                } else if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    log.trace("BrokerClientApp:  - {}: Received text message: {}", destinationName, text);
                    log.info("TXT: {}:\n{}", destinationName, text);
                } else {
                    log.trace("BrokerClientApp:  - {}: Received message: {}", destinationName, message);
                    log.info("MSG: {}:\n{}", destinationName, message);
                }

                recordEvent(message);

            } catch (JMSException je) {
                log.warn("BrokerClientApp: onMessage: EXCEPTION: ", je);
            }
        };
    }

    private static int initRecording(String[] args, int aa) throws IOException {
        // Process recording command line arguments
        String format = null;
        if (args[aa].startsWith("-M"))
            format = args[aa++].substring(2).toLowerCase();
        String fileName = args[aa++];
        File file = Paths.get(fileName).toFile();
        String ext = StringUtils.substringAfterLast(file.getName(), ".");
        if (StringUtils.isNotBlank(format)) {
            if (!("csv".equalsIgnoreCase(format) || "json".equalsIgnoreCase(format)))
                throw new IllegalArgumentException("Unsupported recording format: "+format);
            else if ("csv".equalsIgnoreCase(format)) recordFormat = RECORD_FORMAT.CSV;
            else if ("json".equalsIgnoreCase(format)) recordFormat = RECORD_FORMAT.JSON;
        }
        else if ("csv".equalsIgnoreCase(ext)) recordFormat = RECORD_FORMAT.CSV;
        else if ("txt".equalsIgnoreCase(ext)) recordFormat = RECORD_FORMAT.CSV;
        else if ("json".equalsIgnoreCase(ext)) recordFormat = RECORD_FORMAT.JSON;
        else {
            log.warn("Unknown file extension. Assuming CSV");
            recordFormat = RECORD_FORMAT.CSV;
        }
        recordFile = file;

        // Initialize recording
        log.info("Record format: {}", recordFormat);
        log.info("Record file:   {}", recordFile);
        log.info("Start recording...");

        recordWriter = new BufferedWriter(new FileWriter(file));
        if (recordFormat==RECORD_FORMAT.CSV) {
            csvPrinter = new CSVPrinter(recordWriter, CSVFormat.DEFAULT
                    .withHeader("Timestamp", "Destination", "Mime", "Contents", "Properties"));

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try { csvPrinter.close(true); recordWriter.close(); } catch (IOException e) { log.error("BrokerClientApp: EXCEPTION while closing record file: ", e); }
                log.info("Recording stopped");
            }));
        } else
        if (recordFormat==RECORD_FORMAT.JSON) {
            jsonGenerator = new JsonFactory()
                    .createGenerator(recordWriter)
                    .setPrettyPrinter(new DefaultPrettyPrinter());
            jsonGenerator.writeStartArray();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try { jsonGenerator.writeEndArray(); jsonGenerator.close(); recordWriter.close(); } catch (IOException e) { log.error("BrokerClientApp: EXCEPTION while closing record file: ", e); }
                log.info("Recording stopped");
            }));
        } else
            throw new IllegalArgumentException("Unsupported recording format: "+recordFormat);

        return aa;
    }

    private static void recordEvent(Message message) {
        if (!isRecording) return;

        try {
            ActiveMQMessage amqMessage = (ActiveMQMessage) message;
            long timestamp = message.getJMSTimestamp();
            String destinationName = getDestinationName(message);
            String mime = amqMessage.getJMSXMimeType();

            String content;
            if (amqMessage instanceof ActiveMQTextMessage) {
                content = ((ActiveMQTextMessage)amqMessage).getText();
            } else
            if (amqMessage instanceof ActiveMQMapMessage) {
                content = ((ActiveMQMapMessage)amqMessage).getContentMap()
                        .entrySet().stream()
                        .map(x -> x.getKey() + "=" + x.getValue())
                        .collect(Collectors.joining(",", "{", "}"));
            } else
            if (amqMessage instanceof ActiveMQObjectMessage) {
                content = ((ActiveMQObjectMessage)amqMessage).getObject().toString();
            } else
                throw new IllegalArgumentException("Unsupported Message type: "+amqMessage.getClass().getName());

            String properties = amqMessage.getProperties()
                    .entrySet().stream()
                    .map(x -> x.getKey() + "=" + x.getValue())
                    .collect(Collectors.joining(",", "{", "}"));

            log.trace("REC> timestamp={}, topic={}, mime={}, contents={}, properties={}", timestamp, destinationName, mime, content, properties);
            if (recordFormat==RECORD_FORMAT.CSV) {
                csvPrinter.printRecord(timestamp, destinationName, mime, content, properties);
                csvPrinter.flush();
            } else
            if (recordFormat==RECORD_FORMAT.JSON) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("timestamp", timestamp);
                jsonGenerator.writeStringField("destination", destinationName);
                jsonGenerator.writeStringField("mime", mime);
                jsonGenerator.writeStringField("content", content);
                jsonGenerator.writeStringField("properties", properties);
                jsonGenerator.writeEndObject();
                jsonGenerator.flush();
            }

        } catch (Exception e) {
            log.error("BrokerClientApp: EXCEPTION during RECORDING: ", e);
        }
    }

    private static int initPlayback(String[] args, int aa) throws IOException {
        // Process recording command line arguments
        playbackInterval = -1L;
        playbackDelay = -1L;
        String str2 = null;
        if (args[aa].startsWith("-I"))
            playbackInterval = Long.parseLong(args[aa++].substring(2).toLowerCase());
        if (args[aa].startsWith("-D"))
            playbackDelay = Long.parseLong(args[aa++].substring(2).toLowerCase());
        if (playbackInterval>0 && playbackDelay>0)
            throw new IllegalArgumentException("You can use either -I to specify or -D switch but not both");

        String format = null;
        if (args[aa].startsWith("-M"))
            format = args[aa++].substring(2).toLowerCase();
        String fileName = args[aa++];
        File file = Paths.get(fileName).toFile();
        String ext = StringUtils.substringAfterLast(file.getName(), ".");
        if (StringUtils.isNotBlank(format)) {
            if (!("csv".equalsIgnoreCase(format) || "json".equalsIgnoreCase(format)))
                throw new IllegalArgumentException("Unsupported recording format: "+format);
            else if ("csv".equalsIgnoreCase(format)) recordFormat = RECORD_FORMAT.CSV;
            else if ("json".equalsIgnoreCase(format)) recordFormat = RECORD_FORMAT.JSON;
        }
        else if ("csv".equalsIgnoreCase(ext)) recordFormat = RECORD_FORMAT.CSV;
        else if ("txt".equalsIgnoreCase(ext)) recordFormat = RECORD_FORMAT.CSV;
        else if ("json".equalsIgnoreCase(ext)) recordFormat = RECORD_FORMAT.JSON;
        else {
            log.warn("Unknown file extension. Assuming CSV");
            recordFormat = RECORD_FORMAT.CSV;
        }
        recordFile = file;

        // Initialize recording
        log.info("Playback format: {}", recordFormat);
        log.info("Playback file:   {}", recordFile);

        return aa;
    }

    private static long playbackEvents(String url, String username, String password) throws IOException, JMSException {
        AtomicLong countSuccess = new AtomicLong();
        AtomicLong countFail = new AtomicLong();

        BrokerClient client = BrokerClient.newClient();
        client.openConnection(url, username, password, true);

        boolean useInterval = (playbackInterval>0);
        boolean useDelay = (playbackDelay>0);

        log.info("Start playback...");
        long startTm = System.currentTimeMillis();
        final long[] prevValues = {-1L, -1L, -1L};   // Previous Event Timestamp, Previous System time, Last sleep time

        if (recordFormat==RECORD_FORMAT.CSV)
            playbackEventsFromCsv(client, prevValues, useInterval, useDelay, countSuccess, countFail, url);
        else if (recordFormat==RECORD_FORMAT.JSON)
            playbackEventsFromJson(client, prevValues, useInterval, useDelay, countSuccess, countFail, url);
        else
            throw new IllegalArgumentException("Unsupported or missing recording format: "+recordFormat);

        long endTm = System.currentTimeMillis();
        long count = countSuccess.get() + countFail.get();

        client.closeConnection();

        printPlaybackStatistics(endTm - startTm, countSuccess, countFail);

        return count;
    }

    private static void playbackEventsFromCsv(BrokerClient client, long[] prevValues, boolean useInterval, boolean useDelay,
                                              AtomicLong countSuccess, AtomicLong countFail, String url)
            throws IOException, JMSException
    {
        CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(new BufferedReader(new FileReader(recordFile)))
                .forEach(rec -> {
                    // read event data
                    long timestamp = Long.parseLong(rec.get("Timestamp"));
                    String destinationName = rec.get("Destination");
                    //String mime = rec.get("Mime");
                    String contents = rec.get("Contents");
                    String properties = rec.get("Properties");

                    Map<String, String> propertiesMap = Arrays.stream(properties.split(","))
                            .map(p -> p.split("=",2))
                            .collect(Collectors.toMap(p->p[0], p->p[1]));

                    waitAndSend(client, prevValues, useInterval, useDelay, url,
                            timestamp, destinationName, contents, propertiesMap, countSuccess, countFail);
                });
    }

    private static void playbackEventsFromJson(BrokerClient client, long[] prevValues, boolean useInterval, boolean useDelay,
                                               AtomicLong countSuccess, AtomicLong countFail, String url)
            throws JMSException, IOException
    {
        Reader playbackReader = new BufferedReader(new FileReader(recordFile));
        JsonParser jsonParser = new JsonFactory().createParser(playbackReader);

        if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
            while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                // read event data
                long timestamp = -1L;
                String destinationName = null;
                String mime = null;
                String contents = null;
                String properties = "";

                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    String fieldName = jsonParser.getCurrentName();
                    jsonParser.nextToken();
                    if ("timestamp".equals(fieldName)) timestamp = jsonParser.getLongValue();
                    else if ("destination".equals(fieldName)) destinationName = jsonParser.getText();
                    else if ("mime".equals(fieldName)) mime = jsonParser.getText();
                    else if ("content".equals(fieldName)) contents = jsonParser.getText();
                    else if ("properties".equals(fieldName)) properties = jsonParser.getText();
                    else
                        log.warn("REPLAY> UNKNOWN JSON field at event #{}: {}", countSuccess.get()+countFail.get()+1, fieldName);
                }

                log.trace("REPLAY> Event data: timestamp={}, destination={}, mime={}, content={}, properties={}",
                        timestamp, destinationName, mime, contents, properties);

                Map<String, String> propertiesMap = Arrays.stream(properties.split(","))
                        .map(p -> p.split("=",2))
                        .collect(Collectors.toMap(p->p[0], p->p[1]));

                waitAndSend(client, prevValues, useInterval, useDelay, url,
                        timestamp, destinationName, contents, propertiesMap, countSuccess, countFail);
            }
        }

        jsonParser.close();
        playbackReader.close();
    }

    private static long waitAndSend(BrokerClient client, long[] prevValues, boolean useInterval, boolean useDelay, String url,
                               long timestamp, String destinationName, String contents, Map<String,String> propertiesMap,
                               AtomicLong countSuccess, AtomicLong countFail)
    {
        if (prevValues[1]>0) {
            // calculate wait time
            long sleepTime = 0;
            long now = System.currentTimeMillis();
            if (useInterval) {
                log.trace("REPLAY> Interval: now={}, prev={}, playback={}", now, prevValues[1], playbackInterval);
                prevValues[1] = prevValues[1] + playbackInterval;
                sleepTime = prevValues[1] - now;
                log.trace("REPLAY>         : sleep={}, new-prev={}", sleepTime, prevValues[1]);
            } else if (useDelay) {
                log.trace("REPLAY> Delay: now={}, playback={}", now, playbackDelay);
                sleepTime = playbackDelay;
            } else {
                long diff = timestamp - prevValues[0];
                log.trace("REPLAY> Recorded: diff={}, now={}, prev={}", diff, now, prevValues[1]);
                prevValues[0] = timestamp;
                prevValues[1] += diff;
                sleepTime = prevValues[1] - now;
                log.trace("REPLAY>         : sleep={}, new-prev={}", sleepTime, prevValues[1]);
            }
            prevValues[2] = sleepTime;
            // wait to send
            try {
                log.debug("REPLAY>  sleep={}", sleepTime);
                if (sleepTime > 1)
                    Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException("Playback interrupted");
            }
        } else {
            prevValues[0] = timestamp;
            prevValues[1] = System.currentTimeMillis();
        }

        // send event
        long counter = countSuccess.get()+countFail.get()+1;
        try {
            log.info("BrokerClientApp: Replay event #{}", counter);
            log.trace("BrokerClientApp: Publishing event: {}", contents);
            client.publishEvent(url, destinationName, contents, propertiesMap);
            log.info("BrokerClientApp: Event payload: {}", contents);
            countSuccess.getAndIncrement();
        } catch (Exception e) {
            log.error("BrokerClientApp: EXCEPTION while playing back event #{}: ", counter, e);
            countFail.getAndIncrement();
        }
        return counter;
    }

    private static void printPlaybackStatistics(long duration, AtomicLong countSuccess, AtomicLong countFail) {
        long count = countSuccess.get() + countFail.get();
        log.info("Playback completed in {}ms", duration);
        log.info("        Sent: {}", countSuccess.get());
        log.info("      Failed: {}", countFail.get());
        log.info("       Total: {}", count);
        log.info("   Send Rate: {}e/s", 1000d * count / (duration));
        log.info("  Mean Delay: {}s", count<=1 ? "N/A" : (duration) / 1000d / (count-1) );
    }

    private static String getDestinationName(Message message) throws JMSException {
        Destination d = message.getJMSDestination();
        if (d instanceof Topic) {
            return ((Topic)d).getTopicName();
        } else
        if (d instanceof Queue) {
            return ((Queue)d).getQueueName();
        } else
            throw new IllegalArgumentException("Argument is not a JMS destination: "+d);
    }

    protected static void usage() {
        log.info("BrokerClientApp: Usage: ");
        log.info("BrokerClientApp: client list [-U<USERNAME> [-P<PASSWORD]] <URL> ");
        log.info("BrokerClientApp: client publish [-U<USERNAME> [-P<PASSWORD]] <URL> <TOPIC> <VALUE> <LEVEL> ");
        log.info("BrokerClientApp: client publish2 [-U<USERNAME> [-P<PASSWORD]] <URL> <TOPIC> <PAYLOAD> ");
        log.info("BrokerClientApp: client receive [-U<USERNAME> [-P<PASSWORD]] <URL> <TOPIC> ");
        log.info("BrokerClientApp: client subscribe [-U<USERNAME> [-P<PASSWORD]] <URL> <TOPIC> ");
        log.info("BrokerClientApp: client generator [-U<USERNAME> [-P<PASSWORD]] <URL> <TOPIC> <INTERVAL> <HOWMANY> <LOWER-VALUE> <UPPER-VALUE> <LEVEL> ");
        log.info("BrokerClientApp: client record [-U<USERNAME> [-P<PASSWORD]] <URL> <TOPIC> [-Mcsv|-Mjson] <REC-FILE> ");
        log.info("BrokerClientApp: client playback [-U<USERNAME> [-P<PASSWORD]] <URL> [-Innn|-Dnnn] [-Mcsv|-Mjson] <REC-FILE> ");
        log.info("BrokerClientApp: client js [-E<engine-name>] <JS-file> ");
    }
}