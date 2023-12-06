package com.solvd.buildingco.multithreading;

import com.solvd.buildingco.MultithreadingProof;
import com.solvd.buildingco.utilities.AnsiCodes;
import com.solvd.buildingco.utilities.StringConstants;
import com.solvd.buildingco.utilities.StringFormatters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncClient {
    private static final Logger LOGGER = LogManager.getLogger(AsyncClient.class);

    private final ConnectionPool pool;

    /*
        "Practical uses for AtomicInteger"
        https://stackoverflow.com/questions/4818699/practical-uses-for-atomicinteger
    */
    private static final AtomicInteger threadNumber = new AtomicInteger(1);

    private static final Executor executor = Executors.newCachedThreadPool(
            threadFactory -> {
                final String CURR_CLASS_NAME = "AsyncClient";
                String threadName =
                        CURR_CLASS_NAME
                                + StringConstants.DASH_STRING
                                + StringConstants.SCREAMING_THREAD_STRING
                                + StringConstants.DASH_STRING
                                + threadNumber.getAndIncrement();
                return new Thread(threadFactory, threadName);
            });

    public AsyncClient(ConnectionPool pool) {
        this.pool = pool;
    }

    public void runAsync() {
        CompletableFuture<Connection> futureConnection =
                CompletableFuture.supplyAsync(pool::getConnection, executor);

        futureConnection.thenAccept(connection -> {
            try {

                String currentThreadName = StringFormatters.nestInSingleQuotations(
                        Thread.currentThread().getName()
                );
                String connectionName = StringFormatters.nestInSingleQuotations(
                        connection.getName()
                );

                LOGGER.info(
                        "{}{} claimed {}{}",
                        AnsiCodes.GREEN,
                        currentThreadName,
                        connectionName,
                        AnsiCodes.RESET_ALL
                );

                Thread.sleep(MultithreadingProof.CLIENT_CONNECTION_HOLD_TIME);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                pool.releaseConnection(connection);
            }
        }).exceptionally(e -> {
            LOGGER.error("{}An error occurred: {}{}",
                    AnsiCodes.RED,
                    e,
                    AnsiCodes.RESET_ALL);

            return null;
        });
    }

    @Override
    public String toString() {
        Class<?> currClass = AsyncClient.class;
        String[] fieldNames = {
                "pool"
        };

        String fieldsString =
                StringFormatters.buildFieldsString(this, fieldNames);

        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
