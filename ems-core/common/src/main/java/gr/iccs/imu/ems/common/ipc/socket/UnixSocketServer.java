package gr.iccs.imu.ems.common.ipc.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

@Slf4j
public class UnixSocketServer implements Runnable {
    private final long restartDelay;
    private final Path socketPath;
    private final ExecutorService workerPool;
    private final boolean exitAllowed;
    private final Function<String[],String[]> requestHandler;
    private final Runnable exitHandler;
    private final AtomicBoolean keepRunning = new AtomicBoolean(false);
    private boolean error = false;
    private ServerSocketChannel server;
    private Selector selector;

    public UnixSocketServer(String socketPath, Function<String[],String[]> requestHandler) {
        this(socketPath, 8, 1000L, true, requestHandler, null);
    }

    public UnixSocketServer(String socketPath, int workers, long restartDelay, boolean exitAllowed, Function<String[],String[]> requestHandler, Runnable exitHandler) {
        this.restartDelay = restartDelay;
        this.socketPath = Path.of(socketPath);
        this.workerPool = Executors.newFixedThreadPool(workers);
        this.exitAllowed = exitAllowed;
        this.requestHandler = requestHandler;
        this.exitHandler = exitHandler;
    }

    public boolean isRunning() {
        return keepRunning.get();
    }

    public boolean hasError() {
        return error;
    }

    public boolean waitToStart() {
        while (! isRunning() && ! hasError()) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException ignored) {}
        }
        return isRunning();
    }

    @Override
    public synchronized void run() {
        if (keepRunning.get()) {
            log.warn("Server is already running");
            return;
        }

        // Check if socket file exists
        if (Files.exists(socketPath)) {
            try {
                log.debug("Deleting existing socket file: {}", socketPath);
                deleteSocketFile();
            } catch (Exception e) {
                throw new IllegalStateException("Socket file already exists: " + socketPath, e);
            }
        }

        // Register shutdown hook to remove socket file
        Thread shutdownHook;
        Runtime.getRuntime().addShutdownHook(shutdownHook = new Thread(() -> {
            try {
                stopServer();

                Files.deleteIfExists(socketPath);
                log.info("Socket file removed on shutdown.");
            } catch (IOException e) {
                log.error("Error while deleting socket file on shutdown.", e);
            }
        }));

        UnixDomainSocketAddress address = UnixDomainSocketAddress.of(socketPath);

        keepRunning.set(true);

        while (keepRunning.get()) {
            try {
                // Cleanup UNIX socket file before binding
                deleteSocketFile();

                // Bind to new UNIX socket
                try (ServerSocketChannel server = ServerSocketChannel.open(StandardProtocolFamily.UNIX);
                     Selector selector = Selector.open())
                {
                    server.configureBlocking(false); // non-blocking
                    server.bind(UnixDomainSocketAddress.of(socketPath));
                    server.register(selector, SelectionKey.OP_ACCEPT);
                    this.server = server;
                    this.selector = selector;
                    log.info("Server listening on {}", socketPath);

                    // Wait for clients to connect
                    while (keepRunning.get()) {
                        selector.select(); // blocks, but can be interrupted with wakeup()

                        Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                        while (keys.hasNext()) {
                            SelectionKey key = keys.next();
                            keys.remove();

                            if (key.isAcceptable()) {
                                SocketChannel client = server.accept(); // non-blocking accept
                                if (client != null) {
                                    log.info("Client connected.");

                                    // Handle client in a virtual thread
                                    Thread.ofVirtual().start(() -> handleClient(client));
                                }
                            }
                        }
                    }
                    log.info("Server exiting");
                }
            } catch (Exception e) {
                log.error("Server socket failed: {}", e, e);

                // Prevent rapid restart loops
                try { Thread.sleep(restartDelay); } catch (InterruptedException ignored) {}
            } finally {
                this.server = null;
                this.selector = null;
                deleteSocketFile();
            }
        }

        // Unregister shutdown hook
        try {
            Runtime.getRuntime().removeShutdownHook(shutdownHook);
        } catch (IllegalStateException ignored) {}

        // Call exit handler if provided
        if (exitHandler!=null) {
            Thread.ofVirtual().start(exitHandler);
        }

        log.info("Server exited");
    }

    private void deleteSocketFile() {
        try {
            if (Files.deleteIfExists(socketPath))
                log.info("Socket file removed");
        } catch (IOException e) {
            log.error("Could not delete socket file", e);
            throw new RuntimeException(e);
        }
    }

    private void stopServer() throws IOException {
        keepRunning.set(false);
        try {
            if (selector!=null)
                selector.wakeup();
            if (server!=null)
                server.close();
        } catch (IOException e) {
            log.error("Cannot close server socket", e);
            throw e;
        }
    }

    private void handleClient(SocketChannel client) {
        String clientThread = Thread.currentThread().getName()+"<"+Thread.currentThread().threadId()+">";
        log.info("[{}] Client connected", clientThread);
        try (client;
             BufferedReader reader = new BufferedReader(new InputStreamReader(Channels.newInputStream(client)));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Channels.newOutputStream(client))) )
        {
            String request;
            while ((request = reader.readLine()) != null) {
                log.info("[{}] Received: {}", clientThread, request);

                // Dispatch to worker pool
                final String request_1 = request;
                Future<?> f = workerPool.submit(() -> {
                    String response = handleRequest(request_1);
                    writeToClient(writer, response, clientThread);
                });

                // Wait for job to complete
                try {
                    f.get();
                    if (! keepRunning.get())
                        return;
                } catch (ExecutionException e) {
                    log.error("[{}] request handler failed: ", clientThread, e);
                    writeErrorToClient(request, writer, clientThread, "Request handler failed", e);
                } catch (InterruptedException e) {
                    log.error("[{}] request handler interrupted: ", clientThread, e);
                    writeErrorToClient(request, writer, clientThread, "Request handler interrupted", e);
                }
            }

        } catch (Exception e) {
            log.error("[{}] Client handler error: ", clientThread, e);
        }

        log.info("[{}] Client disconnected.", clientThread);
    }

    private static void writeToClient(BufferedWriter writer, String response, String clientThread) {
        try {
            writer.write(response);
            writer.write("\n"); // newline-delimited
            writer.flush();
            log.info("[{}] Sent: {}", clientThread, response);
        } catch (IOException e) {
            log.error("[{}] Error writing response: ", clientThread, e);
        }
    }

    private static void writeErrorToClient(String request, BufferedWriter writer, String clientThread, String message, Exception e) {
        String correlationId = request.split("\\|", 2)[0];
        String response = correlationId+"|ERROR|"+message+"|"+ e.getClass().getName()+": "+ e.getMessage();
        writeToClient(writer, response, clientThread);
    }

    private String handleRequest(String req) {
        // Extract request data
        String[] part = req.split("\\|");
        String correlationId = part[0];
        if (part.length < 2) {
            return part[0] + "|ERROR|Invalid number of arguments in request";
        }

        // Check for Server command
        if ("SERVER".equalsIgnoreCase(part[1])) {
            if (part.length < 3) {
                return correlationId + "|ERROR|Invalid number of arguments in Server command request";
            }
            if ("NOOP".equalsIgnoreCase(part[2])) {
                return correlationId + "|SERVER|" + part[2];
            } else
            if ("EXIT".equalsIgnoreCase(part[2])) {
                if (! exitAllowed) {
                    return correlationId + "|SERVER|EXIT NOT ALLOWED";
                }
                try {
                    stopServer();
                } catch (IOException e) {
                    return correlationId + "|SERVER|EXCEPTION: " + e.getMessage();
                }
                return correlationId + "|SERVER|" + part[2];
            } else {
                return correlationId + "|ERROR|Unknown Server command: "+part[2];
            }
        }

        // Call request handler
        String[] response = requestHandler.apply(Arrays.copyOfRange(part, 1, part.length));
        log.info("  Result: {}", Arrays.toString(response));
        if (response == null)
            return correlationId + "|ERROR|Null response";

        return correlationId + "|" + String.join("|", response);
    }
}