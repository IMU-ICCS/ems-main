/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.discovery.server;

import org.apache.sshd.server.Command;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.SessionAware;
import org.apache.sshd.server.session.ServerSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
//import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientShellCommand implements Command, Runnable, SessionAware {
	
	protected static Object LOCK = new Object();
	protected static long counter;
	protected static HashSet<ClientShellCommand> activeCmdList = new HashSet<>();
	
	public static Set<ClientShellCommand> getActive() {
		return (Set<ClientShellCommand>)activeCmdList.clone();
	}
	
	private InputStream in;
	private PrintStream out;
	private PrintStream err;
	private ExitCallback callback;
	private boolean callbackCalled;
	
	@Getter @Setter
	private String id;
	@Getter @Setter
	private boolean echoOn = false;
	
	private ServerCoordinator coordinator;
	private ServerSession session;
	//private boolean isAdmin;
	
	public ClientShellCommand(ServerCoordinator coordinator) {
		synchronized (LOCK) {
			id = String.format("#%05d", counter++);
		}
		this.coordinator = coordinator;
	}
	
	public String getId() { return id; }
	
	public void setSession(ServerSession session) {
		log.info("{}--> Got session : {}", id, session);
		this.session = session;
		
		/*try {
			String clientIpAddr = ((InetSocketAddress)session.getIoSession().getRemoteAddress()).getAddress().getHostAddress();
			int clientPort = ((InetSocketAddress)session.getIoSession().getRemoteAddress()).getPort();
			log.info("{}--> Client connection : {}:{}", id, clientIpAddr, clientPort);
		} catch (Exception ex) {}*/
		
		if (session!=null) {
			String username = session.getUsername();
			if (username!=null && !(username=username.trim()).isEmpty()) {
				//this.isAdmin = coordinator.isAdmin(username);
			}
		}
	}
	
	public ServerSession getSession() { return session; }
	
	public void setInputStream(InputStream in) { this.in = in; }
	public void setOutputStream(OutputStream out) { this.out = new PrintStream(out, true); }
	public void setErrorStream(OutputStream err) { this.err = new PrintStream(err, true); }
	public void setExitCallback(ExitCallback callback) { this.callback = callback; }
	
	public void start(Environment env) { new Thread(this).start(); }
	public void destroy() { }
	
	public void run() {
		synchronized (activeCmdList) {
			activeCmdList.add(this);
		}
		
		try {
			log.info( "{}==> Thread started", id );
			out.printf("CLIENT (%s) : START\n", id);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line=reader.readLine())!=null) {
				line = line.trim();
				log.info("{}--> {}", id, line);
				
				//if (echoOn) out.printf("CLIENT (%s) : ECHO : %s\n", id, line);
				if (echoOn) out.printf("ECHO %s\n", line);
				//if (line.equalsIgnoreCase("exit")) break;
				
				if (line.startsWith("-HELLO FROM")) {
					coordinator.register(this);
				} else
				if (line.equalsIgnoreCase("READY")) {
					if (coordinator.getPhase()==2) {
						coordinator.brokerReady(this);
					} else {
						coordinator.clientReady(this);
					}
				}
			}
			
			log.info( "{}==> Signaling client to exit", id );
			out.println("EXIT");
			
		} catch (IOException ex) {
			log.warn( "{}==> EXCEPTION : {}", id, ex );
			out.printf("EXCEPTION %s\n", ex); 
		} finally {
			synchronized (activeCmdList) {
				activeCmdList.remove(this);
			}
			log.info( "{}--> Thread stops", id );
			if (!callbackCalled) { callbackCalled=true; callback.onExit(0); }
		}
	}
	
	public void sendToClient(String msg) {
		if (msg==null || (msg=msg.trim()).isEmpty()) return;
		log.info("{}==> PUSH : {}", id, msg);
		out.println(msg);
	}
	
	public void stop(String msg) {
		log.info("{}==> STOP : {}", id, msg);
		out.println("EXIT "+msg);
		if (!callbackCalled) { callbackCalled=true; callback.onExit(1); }
	}
}
