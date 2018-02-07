/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.discovery.client;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.config.hosts.HostConfigEntryResolver;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.config.keys.impl.RSAPublicKeyDecoder;
import org.apache.sshd.common.keyprovider.KeyPairProvider;
//import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
//import org.apache.sshd.client.keyverifier.RequiredServerKeyVerifier;
import org.apache.sshd.client.keyverifier.ServerKeyVerifier;
import org.apache.sshd.client.simple.SimpleClient;
import org.apache.sshd.common.util.io.NoCloseInputStream;
import org.apache.sshd.common.util.io.NoCloseOutputStream;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.SocketAddress;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


/**
 * Custom SSH client
 *
 */
@Slf4j
public class Sshc
{
	private Properties config;
	private SshClient client;
	private SimpleClient simple;
	private ClientSession session;
	private ClientChannel channel;
	private boolean started = false;
	
	private InputStream in;
	private PrintStream out;
	//private PrintStream err;
	private String clientId;
	
	public Sshc(Properties config) throws IOException {
		this.config = config;
		log.debug("OS detected: {}", getOsName());
	}
	
	public synchronized void start(boolean retry) throws IOException {
		if (retry) {
			log.trace("Starting client in retry mode");
			long retryPeriod = Long.parseLong(config.getProperty("retry.period","60000"));
			while (!started) {
				log.debug("(Re-)trying to start client....");
				try { start(); } catch (Exception ex) { log.warn("{}", ex.getMessage()); }	//{ log.warn("(Re-)trying to start client: {}", ex.getMessage()); }
				if (started) break;
				log.trace("Failed to start. Sleeping for {}ms...", retryPeriod);
				try { Thread.sleep(retryPeriod); } catch (InterruptedException ex) { log.debug("Sleep: {}", ex); }
			}
			log.trace("Client started");
		} else {
			start();
		}
	}
	
	public synchronized void start() throws IOException {
		if (started) return;
		log.info( "Connecting to server..." );
		
		String host = config.getProperty("host");
		int port = Integer.parseInt(config.getProperty("port","22"));
		String serverPubKey = config.getProperty("pubkey");
		String serverFingerprint = config.getProperty("fingerprint");
		String username = config.getProperty("username");
		String password = config.getProperty("password");;
		long authTimeout = Long.parseLong(config.getProperty("auth.timeout","60000"));
		
		// Starting client and connecting to server
		this.client = SshClient.setUpDefaultClient();
		client.setHostConfigEntryResolver(HostConfigEntryResolver.EMPTY);
		client.setKeyPairProvider(KeyPairProvider.EMPTY_KEYPAIR_PROVIDER);
		
		//client.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);
		//client.setServerKeyVerifier(new RequiredServerKeyVerifier(....));
		client.setServerKeyVerifier(new ServerKeyVerifier() {
				private String serverFingerprint;
				private String serverPubKey;
				
				public boolean verifyServerKey(ClientSession sshClientSession, SocketAddress remoteAddress, PublicKey serverKey) {
					
					// Print server address info
					log.info("verifyServerKey(): remoteAddress: {}", remoteAddress.toString());
					/*log.info("verifyServerKey(): remoteAddress: {}: {}",
						remoteAddress.getClass().getName(),	//java.net.InetSocketAddress
						remoteAddress.toString()
					);*/
					
					// Check that server public key fingerprint matches with the one in configuration
					String fingerprint = KeyUtils.getFingerPrint(serverKey);
					log.info("verifyServerKey(): serverKey: fingerprint: {}", fingerprint);
					//if ( fingerprint!=null && KeyUtils.checkFingerPrint(serverFingerprint, serverKey).getFirst() ) log.info("verifyServerKey(): serverKey: fingerprint: MATCH");
					//else log.warn("verifyServerKey(): serverKey: fingerprint: NO MATCH");
					
					// Check that server public key matches with the one in configuration
					try {
						log.debug("verifyServerKey(): serverKey: decoder: {}", KeyUtils.getPublicKeyEntryDecoder(serverKey).getClass() );
						java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
						((RSAPublicKeyDecoder)KeyUtils.getPublicKeyEntryDecoder(serverKey)).encodePublicKey(baos, (RSAPublicKey)serverKey);
						String keyStr = new String(java.util.Base64.getEncoder().encode(baos.toByteArray()));
						log.debug("verifyServerKey(): serverKey: server public key: \n{}", keyStr);
						
						return keyStr.equalsIgnoreCase(serverPubKey);
						
					} catch (Exception ex) {
						log.error("verifyServerKey(): serverKey: EXCEPTION: {}", ex);
						return false;
					}
				}
				
				public ServerKeyVerifier setServerPubKey(String pubkey, String fingerprint) {
					this.serverFingerprint = fingerprint;
					this.serverPubKey = pubkey;
					return this;
				}
			}
			.setServerPubKey(serverPubKey, serverFingerprint)
		);
		
		this.simple = SshClient.wrapAsSimpleClient(client);
		//simple.setConnectTimeout(...CONNECT_TIMEOUT...);
		//simple.setAuthenticationTimeout(...AUTH_TIMEOUT...);
		
		client.start();
		
		// Authenticate and start session
		this.session = client.connect(username, host, port).verify().getSession();
		session.addPasswordIdentity(password);
		session.auth().verify( authTimeout );
		
		// Open command shell channel
		this.channel = session.createChannel(ClientChannel.CHANNEL_SHELL);
		PipedInputStream pIn = new PipedInputStream();
		PipedOutputStream pOut = new PipedOutputStream();
		//PipedOutputStream pErr = new PipedOutputStream();
		this.in  = new BufferedInputStream(pIn);
		this.out = new PrintStream(pOut, true);
		//this.err = new PrintStream(pErr, true);
		
		channel.setIn(new NoCloseInputStream( new PipedInputStream( pOut ) ));
		channel.setOut(new NoCloseOutputStream( new PipedOutputStream( pIn ) ));
		//channel.setErr(new NoCloseOutputStream( new PipedOutputStream( pErr ) ));
		
		channel.open();
		
		// Create a client id
		this.clientId = "CLIENT-"+java.util.UUID.randomUUID().toString();
		
		log.info( "SSH client is ready" );
		this.started = true;
	}
	
	public synchronized void stop() throws IOException {
		if (!started) return;
		this.started = false;
		log.info( "Stopping SSH client..." );
		
		// Signal command line runner to stop
		//runCli.interrupt(); 
		
		channel.close(false).await();
		session.close(false);
		simple.close();
		client.stop();
		
		log.info( "SSH client stopped" );
	}
	
	public void run() throws IOException {
		//channel.waitFor( Collections.singletonList( ClientChannelEvent.CLOSED ), 0);
		
		// Start command line runner
		/*Thread runCli = new Thread(new CliRunnable(out));
		runCli.setDaemon(true);
		runCli.start();*/
		
		// Start communication protocol with Server
		// Execution waits here until connection is closed
		log.trace("run(): Calling communicateWithServer()...");
		communicateWithServer(in, out, null);
	}
	
	protected void communicateWithServer(InputStream in, PrintStream out, PrintStream err) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		out.println( String.format("-HELLO FROM '%s'", clientId) );
		String line;
		while ((line=reader.readLine())!=null) {
			line = line.trim();
			log.info(line);
			try {
				boolean exit = execCmd(line.split("[ \t]+"), reader, out, err);
				if (exit) break;
			} catch (Exception ex) {
				log.error("{}", ex);
				//ex.printStackTrace(System.err);
				// Report exception back to server
				out.println(ex);
				ex.printStackTrace(out);
				out.flush();
			}
		}
		out.println( String.format("-BYE FROM '%s'", clientId) );
		
		/*System.out.println( "\nPress enter to exit" );
		try { System.in.read(); } catch (Exception ex) {}*/
	}
	
	protected boolean execCmd(String args[], BufferedReader in, PrintStream out, PrintStream err) throws IOException, InterruptedException {
		if (args==null || args.length==0) return false;
		String cmd = args[0].toUpperCase();
		args[0] = "";
		
		if ("EXIT".equals(cmd)) {
			return true;
		} else
		if ("CLIENT".equals(cmd)) {
			// Information from server. Don't do anything
		} else
		if ("ECHO".equals(cmd)) {
			// Server echoes back client command. Don't do anything
		} else
		if ("HEARTBEAT".equals(cmd)) {
			// Respond to server with OK
			out.println("OK");
		} else
		if ("EXEC".equals(cmd)) {
			String execCmd = String.join(" ",args);
			log.info("EXEC COMMAND: {}", execCmd);
			// Execute command
			execCmdExec( execCmd, in, out, err);
		} else
		if ("ROLE".equals(cmd)) {
			if (args.length<2) return false;
			String role = args[1].trim();
			log.info("ASSUMING ROLE: {}", role);
			// Execute command
			assumeRole( role, in, out, err);
		} else
		if ("SET-PARAM".equals(cmd)) {
			if (args.length<5) return false;
			String tplFile = args[1].trim();
			String placeholder = args[2].trim();
			String value = args[3].trim();
			String outFile = args[4].trim();
			log.info("SETTING PARAM: '{}' to '{}' in file: {}", placeholder, value, outFile);
			// Execute command
			setFileParam( placeholder, value, tplFile, outFile, out );
		} else
		{
			args[0] = cmd;
			log.warn("UNKNOWN COMMAND: "+String.join(" ",args));
		}
		return false;
	}
	
	protected void assumeRole(String role, BufferedReader in, PrintStream out, PrintStream err) throws IOException, InterruptedException {
		// Execute role preparation command
		role = role.toLowerCase();
		String cmdProp = role+".command."+getOsName();
		String command = config.getProperty(cmdProp);
		log.debug("Command: {}", command);
		if (command!=null && !(command=command.trim()).isEmpty()) {
			// Executing command in a separate process
			log.info("Executing command: {}", command);
			Process process = Runtime.getRuntime().exec(command);
			StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), process.getErrorStream(), System.out::println);
			//Executors.newSingleThreadExecutor().submit(streamGobbler);
			long timeout = 0;
			try { timeout = Long.parseLong(config.getProperty("exec.timeout")); } catch (Exception ex) {}
			log.info("Timeout: "+timeout);
			int exitCode = -1;
			if (timeout>0) {
				log.warn("Wait for: {}", timeout);
				if (process.waitFor(timeout, java.util.concurrent.TimeUnit.MILLISECONDS)) {
					log.info(">> FINISHED");
					exitCode = process.exitValue();
				} else
					log.warn(">> TIMEOUT");
			} else {
				log.warn("Wait forever");
				exitCode = process.waitFor();
				log.info(">> FINISHED");
			}
			log.info("Exit code: "+exitCode);
			
			// Signaling server when ready
			if (exitCode==0) out.println("READY");
			else out.println("ERROR");
			return;
		}
		// Signaling server for error
		out.println("ERROR: Missing property"+cmdProp);
	}
	
	protected void execCmdExec(String command, BufferedReader in, PrintStream out, PrintStream err) throws IOException, InterruptedException {
		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
		if (isWindows) {
			command = String.format("cmd.exe /c %s", command);
		} else {
			command = String.format("/bin/sh -c %s", command);
		}		
		Process process = Runtime.getRuntime().exec(command);
		
		StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), process.getErrorStream(), out::println);
		//Executors.newSingleThreadExecutor().submit(streamGobbler);
		int exitCode = process.waitFor();
		out.println("Command exited with code: "+exitCode);
	}
	
	protected void setFileParam(String placeholder, String value, String tplFile, String outFile, PrintStream out) {
		// Read template file contents
		String contents = null;
		try (Scanner scanner = new Scanner(new File(tplFile))) { contents = scanner.useDelimiter("\\A").next(); }
		catch (FileNotFoundException ex) {
			log.info("SET-PARAM: EXCEPTION: {}", ex);
			out.println("ERROR Template file not found: "+ex);
			return;
		} catch (IOException ex) {
			log.info("SET-PARAM: EXCEPTION: {}", ex);
			out.println("ERROR While reading from template file: "+ex);
			return;
		}
		
		// Replace placeholders with value
		contents = contents.replace(placeholder, value);
		
		// Write new contents to output file
		try(PrintWriter writer = new PrintWriter( outFile )  ){ writer.println( contents ); }
		catch (FileNotFoundException ex) {
			log.error("SET-PARAM: EXCEPTION: {}", ex);
			out.println("ERROR Template file not found: "+ex);
			return;
		} catch (IOException ex) {
			log.error("SET-PARAM: EXCEPTION: {}", ex);
			out.println("ERROR While writing to output file: "+ex);
			return;
		}
		
		out.println("PARAM SET");
	}
	
	private static class StreamGobbler implements Runnable {
		private InputStream inputStream1;
		private InputStream inputStream2;
		private Consumer<String> consumer;

		public StreamGobbler(InputStream inputStream1, InputStream inputStream2, Consumer<String> consumer) {
			this.inputStream1 = inputStream1;
			this.inputStream2 = inputStream2;
			this.consumer = consumer;
		}

		@Override
		public void run() {
			new BufferedReader(new InputStreamReader(inputStream1)).lines().forEach(consumer);
			new BufferedReader(new InputStreamReader(inputStream2)).lines().forEach(consumer);
		}
	}
	
	/*protected static class CliRunnable implements Runnable {
		private PrintStream out;
		
		public CliRunnable(PrintStream out) {
			this.out = out;
		}
		
		public void run() {
			try {
				Scanner scanner = new Scanner(System.in).useDelimiter(System.getProperty("line.separator"));
				String line;
				while (scanner.hasNextLine()) {
					System.out.print("CLI> ");
					System.out.flush();
					line = scanner.nextLine().trim();
					out.println(line);
				}
			} catch (Exception ex) {
				System.err.println(ex);
				ex.printStackTrace(System.err);
			}
		}
	}*/
	
	public static String getOsName() {
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("win") >= 0) {
			return "windows";
		} else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 || OS.indexOf("sunos") >= 0) {
			return "linux";
		} else if (OS.indexOf("mac") >= 0) {
			return "mac";
		} else {
			return "other";
		}
	}
}
