package org.ow2.paasage.camel.srl.adapter.execution;

import com.github.rholder.retry.*;
import eu.paasage.mddb.cdo.client.CDOClient;
import org.eclipse.net4j.connector.ConnectorException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 07.06.16.
 */
public class RetryingCDOClientFactory {

    private static final int CONNECTION_RETRIES = 10;
    private static final long EXPONENTIAL_MULTIPLIER = 1000;
    private static final long MAXIMUM_TIME = 1000;

    private RetryingCDOClientFactory() {

    }

    public static CDOClient client(String user, String password) {

        Retryer<CDOClient> remoteConnectionRetryer =
            RetryerBuilder.<CDOClient>newBuilder().retryIfRuntimeException()
                .retryIfException(throwable -> throwable instanceof ConnectorException)
                .withStopStrategy(StopStrategies.stopAfterAttempt(CONNECTION_RETRIES))
                .withWaitStrategy(WaitStrategies
                    .exponentialWait(EXPONENTIAL_MULTIPLIER, MAXIMUM_TIME, TimeUnit.SECONDS))
                .build();

        try {
            return remoteConnectionRetryer.call(() -> new CDOClient(user, password));
        } catch (ExecutionException | RetryException e) {
            throw new RuntimeException(e);
        }

    }

}
