package gr.iccs.imu.ems.common.ipc.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
public class UnixSocketClient implements Closeable {
    private static final long DEFAULT_READ_TIMEOUT_MILLIS = 30000;
    private static final long DEFAULT_WRITE_TIMEOUT_MILLIS = 30000;
    private final SocketChannel channel;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final ExecutorService executor;

    public UnixSocketClient(Path socketPath) throws IOException {
        UnixDomainSocketAddress address = UnixDomainSocketAddress.of(socketPath);
        this.channel = SocketChannel.open(StandardProtocolFamily.UNIX);
        this.channel.connect(address);

        this.reader = new BufferedReader(new InputStreamReader(Channels.newInputStream(channel)));
        this.writer = new BufferedWriter(new OutputStreamWriter(Channels.newOutputStream(channel)));

        this.executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Sends a single request and waits for a response.
     *
     * @param request The request string (no newline needed)
     * @param writeTimeoutMillis Maximum time to wait for writing the request in milliseconds
     * @param readTimeoutMillis Maximum time to wait for the response in milliseconds
     * @return The response string (without newline)
     * @throws IOException On I/O error or timeout
     */
    public String sendRequest(String request, long writeTimeoutMillis, long readTimeoutMillis) throws IOException {
        // Send request
        writeLineWithTimeout(writer, request, writeTimeoutMillis);
        /*writer.write(request);
        writer.write("\n"); // newline-delimited framing
        writer.flush();*/

        // Read response
        String response = readLineWithTimeout(reader, readTimeoutMillis);
        /*String response = reader.readLine();*/
        if (response == null) {
            throw new IOException("Server closed connection unexpectedly");
        }
        return response;
    }

    public String sendRequest(String request) throws IOException {
        return sendRequest(request, DEFAULT_WRITE_TIMEOUT_MILLIS, DEFAULT_READ_TIMEOUT_MILLIS);
    }

    /**
     * Writes a line (with newline) to a BufferedWriter with a timeout.
     *
     * @param writer        The BufferedWriter to write to
     * @param line          The line to write (without newline)
     * @param timeoutMillis Maximum time to wait in milliseconds
     * @throws IOException If the write fails or timeout is exceeded
     */
    public void writeLineWithTimeout(BufferedWriter writer, String line, long timeoutMillis) throws IOException {
        Future<Void> future = this.executor.submit(() -> {
            writer.write(line);
            writer.write("\n"); // newline-delimited framing
            writer.flush();
            return null;
        });

        try {
            future.get(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true); // interrupt the write if possible
            throw new IOException("Write timeout exceeded");
        } catch (ExecutionException e) {
            throw new IOException("Error writing to socket", e.getCause());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Write interrupted", e);
        }
    }

    /**
     * Reads a line from the BufferedReader with a timeout.
     *
     * @param reader        BufferedReader to read from
     * @param timeoutMillis Timeout in milliseconds
     * @return The line read, or null if the stream is closed
     * @throws IOException If an I/O error occurs or the timeout expires
     */
    public String readLineWithTimeout(BufferedReader reader, long timeoutMillis) throws IOException {
        Future<String> future = this.executor.submit(reader::readLine);
        try {
            return future.get(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true); // interrupt the read
            throw new IOException("Read timeout exceeded");
        } catch (ExecutionException e) {
            throw new IOException("Error reading from socket", e.getCause());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Read interrupted", e);
        }
    }

    /**
     * Close the client and release resources
     */
    @Override
    public void close() throws IOException {
        try {
            executor.shutdownNow();
        } catch (Exception ignored) {}
        try {
            reader.close();
        } catch (IOException ignored) {}
        try {
            writer.close();
        } catch (IOException ignored) {}
        channel.close();
    }

    public static void main(String[] args) throws IOException {
        try (UnixSocketClient client = new UnixSocketClient(Paths.get(args[0]))) {
            String request = UUID.randomUUID() + "|" + String.join("|", Arrays.copyOfRange(args, 1, args.length));
            log.info("Request: {}", request);
            String response = client.sendRequest(request);
            log.info("Response: {}", response);
        }
    }
}