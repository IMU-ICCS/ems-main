package eu.passage.upperware.commons.proactive.client;

import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.PAGateway;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class ProactiveClientServiceConnector implements IProactiveClientServiceConnector {

    private final PAGateway paGateway;
    private final AtomicInteger connectionState = new AtomicInteger(0);
    private final ProtectionUtils protectionUtils;
    private final String restUrl;
    private final String login;
    private final String password;

    protected ProactiveClientServiceConnector(final String restUrl, final String login, final String password, final String encryptorPassword) {
        this.restUrl = restUrl;
        this.login = login;
        this.password = password;
        this.protectionUtils = new ProtectionUtils(encryptorPassword);

        try {
            paGateway = new PAGateway(restUrl);
            log.info("ProactiveClientServiceConnector->constructor: PAGateway created");
        } catch (RuntimeException e) {
            log.error("ProactiveClientServiceConnector->constructor: Exception caught during creation of Proactive Client (PAGateway) object, error: {}", e.getMessage());
            throw new ProactiveClientException("Exception caught during creation of Proactive Client (PAGateway) object", e);
        }

        connectToProactiveServer();
    }

    private void connectToProactiveServer() {
        if (!this.connectionState.compareAndSet(0, 1)) { // trying to connect (1)
            log.debug("ProactiveClientServiceConnector->connectToProactiveServer: current connectionState= {}", getConnectionState());
            return;
        }

        try {
            log.info("ProactiveClientServiceConnector->connectToProactiveServer: Trying to connect to Proactive server using: url: {}, encrypted login: {}, encrypted password: {}",
                    restUrl,
                    login,
                    password);

            try {
                log.debug("ProactiveClientServiceConnector->connectToProactiveServer: decrypted login: {}", protectionUtils.decrypt(login));
                log.debug("ProactiveClientServiceConnector->connectToProactiveServer: decrypted password: {}", protectionUtils.decrypt(password));
            } catch (RuntimeException e) {
                log.debug("ProactiveClientServiceConnector->connectToProactiveServer: Exception caught while decrypting Proactive server credentials, message: {}", e.getMessage());
                throw e;
            }

            paGateway.connect(protectionUtils.decrypt(login),
                    protectionUtils.decrypt(password));
            log.info("ProactiveClientServiceConnector->connectToProactiveServer: Connected to Proactive server");
            this.connectionState.set(2); // connected (2)
        } catch (Exception e) {
            log.error("ProactiveClientServiceConnector->connectToProactiveServer: Exception of type {} caught while connecting to Proactive server, error: {}\nStackTrace: {}", e.getClass().getSimpleName(), e.getMessage(), ExceptionUtils.getStackTrace(e));
            this.connectionState.set(0); // disconnected (0)
        }
    }

    protected void disconnectFromProactiveServer() {
        if (!this.connectionState.compareAndSet(2, 3)) { // trying to disconnect (3)
            log.debug("ProactiveClientServiceConnector->disconnectFromProactiveServer: current connectionState= {}", getConnectionState());
            return;
        }

        try {
            log.info("ProactiveClientServiceConnector->disconnectFromProactiveServer: Trying to disconnect from Proactive server");
            paGateway.disconnect();
            log.info("ProactiveClientServiceConnector->disconnectFromProactiveServer: Disconnected from Proactive server");
            this.connectionState.set(0); // disconnected (0)
        } catch (Exception e) {
            log.error("ProactiveClientServiceConnector->disconnectFromProactiveServer: Exception of type {} caught while disconnecting from Proactive server, error: {}", e.getClass().getSimpleName(), e.getMessage());
            this.connectionState.set(2); // connected (2)
        }
    }

    protected Optional<PAGateway> getPAGateway() {
        connectToProactiveServer();
        if(getConnectionState().equals(ProactiveConnectionState.CONNECTED)) {
            return Optional.of(this.paGateway);
        }
        return Optional.empty();
    }

    @Override
    public ProactiveConnectionState getConnectionState() {
        return ProactiveConnectionState.findByCode(this.connectionState.get());
    }
}
