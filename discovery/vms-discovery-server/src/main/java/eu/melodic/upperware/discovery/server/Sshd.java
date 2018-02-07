/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.discovery.server;

import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
//import org.apache.sshd.common.file.nativefs.NativeFileSystemFactory;
//import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;

import org.apache.sshd.common.Factory;
//import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.server.Command;
//import org.apache.sshd.server.CommandFactory;
//import org.apache.sshd.server.ExitCallback;
//import org.apache.sshd.server.Environment;


//import org.apache.sshd.server.shell.ProcessShellFactory;
//import org.apache.sshd.server.scp.ScpCommandFactory;
//import org.apache.sshd.server.subsystem.sftp.SftpSubsystem;
//import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;

import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.pubkey.PublickeyAuthenticator;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintStream;
//import java.nio.file.Paths;
//import java.security.PublicKey;
//import java.util.Collections;
import java.util.Properties;

/**
 * Custom SSH server
 *
 */
@Slf4j
public class Sshd
{
	private ServerCoordinator coordinator;
	private Properties configuration;
	private Properties credentials;
	private SshServer sshd;
	
	private boolean heartbeatOn;
	private long heartbeatPeriod;
	
	public void start(Properties configuration, ServerCoordinator coordinator, Properties credentials) throws IOException {
		log.info( "** SSH server **" );
		this.coordinator = coordinator;
		this.configuration = configuration;
		this.credentials = credentials;
		
		int port = Integer.parseInt(configuration.getProperty("server.port", "22").trim());
		//String homeDir = configuration.getProperty("server.home.dir", "").trim();
		String serverKeyFilePath = configuration.getProperty("server.key.file", "").trim();
		
		// Configure SSH server
		log.info( "Starting SSH server on port {}", port );
		sshd = SshServer.setUpDefaultServer();
		sshd.setPort( port );
		sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider( new File(serverKeyFilePath) ));
		
		//sshd.setFileSystemFactory( new NativeFileSystemFactory() );
		//sshd.setFileSystemFactory( new VirtualFileSystemFactory( Paths.get(homeDir) ) );
		//sshd.setSubsystemFactories( Collections.<NamedFactory<Command>>singletonList( new SftpSubsystemFactory() ) );
		
		//sshd.setShellFactory(new ProcessShellFactory(new String[] { "/bin/sh", "-i", "-l" }));
		//sshd.setShellFactory(new ProcessShellFactory(new String[] { "C:\\WINDOWS\\system32\\cmd.exe", "/K" }));
		sshd.setShellFactory(
			new Factory<Command>() {
				private ServerCoordinator coordinator;
				public Command create() {
					ClientShellCommand msc = new ClientShellCommand(this.coordinator);
					//msc.setId( "#-"+System.currentTimeMillis() );
					log.debug("SSH server: Shell Factory: create invoked : New ClientShellCommand id: {}", msc.getId());
					return msc;
				}
				public Command get() {
					log.debug("SSH server: Shell Factory: get invoked");
					return null;
				}
				public Factory setCoordinator(ServerCoordinator coordinator) {
					this.coordinator = coordinator;
					return this;
				}
			}
			.setCoordinator(coordinator)
		);
		//sshd.setCommandFactory(new ScpCommandFactory());
		/*sshd.setCommandFactory(new CommandFactory() {
			public Command createCommand(String command) {
				System.out.println("COMMAND RECEIVED: " + command);
				//return new ProcessShellFactory(command.split(" ")).create();
				return new MyCommand(command);
			}
		});*/
		
		sshd.setPasswordAuthenticator(
			new PasswordAuthenticator() {
				private Properties credentials;
				public boolean authenticate(String username, String password, ServerSession session) {
					String pwd = credentials.getProperty(username,"").trim();
					return pwd.equals(password);
					//return username.trim().equals("aa") && password.trim().equals("xx");
				}
				public PasswordAuthenticator setCredentials(Properties credentials) {
					this.credentials = credentials;
					return this;
				}
			}
			.setCredentials(credentials)
		);
		//sshd.setPublickeyAuthenticator( new PublickeyAuthenticator() { public boolean authenticate(String username, PublicKey key, ServerSession session) { return true; } } );
		
		
		// Start SSH server and accept connections
		sshd.start();
		log.info( "SSH server is ready" );
		
		// Start heartbeat service
		String heartbeatOn = configuration.getProperty("heartbeat", "off").trim().toLowerCase();
		if (heartbeatOn.equals("true") || heartbeatOn.equals("yes") || heartbeatOn.equals("on")) {
			long heartbeatPeriod = Long.parseLong(configuration.getProperty("heartbeat.period", "60000").trim());
			startHeartbeat(heartbeatPeriod);
		}
		
		// Start coordinator
		coordinator.start();
	}
	
	public void stop() throws IOException {
		// Stop coordinator
		coordinator.stop();
		
		// Don't accept new connections
		log.info( "Stopping SSH server..." );
		sshd.setShellFactory(null);
		
		// Signal heartbeat service to stop
		stopHeartbeat();
		
		// Close active client connections
		for (ClientShellCommand csc : ClientShellCommand.getActive()) {
			csc.stop("Server exits");
		}
		
		sshd.stop();
		log.info( "SSH server stopped" );
	}
	
	public void startHeartbeat(long period) {
		heartbeatOn = true;
		Thread heartbeat = new Thread(
			new Runnable() {
				private long period;
				public void run() {
					log.info("--> Heartbeat: Started: period={}ms", period);
					while (heartbeatOn) {
						try { Thread.sleep(period); } catch (Exception ex) {}
						String msg = String.format("Heartbeat %d", System.currentTimeMillis());
						log.debug("--> Heartbeat: {}", msg);
						for (ClientShellCommand csc : ClientShellCommand.getActive()) {
							csc.sendToClient(msg);
						}
					}
					log.info("--> Heartbeat: Stopped");
				}
				
				public Runnable setPeriod(long period) {
					this.period = period;
					return this;
				}
			}
			.setPeriod(period)
		);
		heartbeat.setDaemon(true);
		heartbeat.start();
	}
	
	public void stopHeartbeat() {
		heartbeatOn = false;
	}
	
	protected void broadcastToClients(String msg) {
		for (ClientShellCommand csc : ClientShellCommand.getActive()) {
			System.out.println("Sending to "+csc.getId()+" : "+msg);
			csc.sendToClient(msg);
		}
	}
	
	/*protected static class MyCommand implements Command, Runnable {
		private InputStream in;
		private PrintStream out;
		private PrintStream err;
		private ExitCallback callback;
		private String cmd;
		
		public MyCommand(String cmd) {
			setCmd(cmd);
		}
		
		public void setInputStream(InputStream in) { this.in = in; }
		public void setOutputStream(OutputStream out) { this.out = new PrintStream(out, true); }
		public void setErrorStream(OutputStream err) { this.err = new PrintStream(err, true); }
		public void setExitCallback(ExitCallback callback) { this.callback = callback; }
		
		public void start(Environment env) { new Thread(this).start(); }
		public void destroy() { }
		
		public void run() {
			out.println("COMMAND EXECUTION STARTED: "+cmd); 
			try { Thread.sleep(5000); } catch (Exception ex) {}
			
			out.println("COMMAND EXECUTION ENDED: "+cmd); 
			callback.onExit(0, "OK: COMMAND: "+cmd);
		}
		
		public String getCmd() { return cmd; }
		public void setCmd(String cmd) { this.cmd = cmd; }
	}*/
}
