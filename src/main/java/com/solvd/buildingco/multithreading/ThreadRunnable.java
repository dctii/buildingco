package com.solvd.buildingco.multithreading;

import com.solvd.buildingco.MultithreadingProof;
import com.solvd.buildingco.utilities.AnsiCodes;
import com.solvd.buildingco.utilities.StringFormatters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadRunnable implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ThreadRunnable.class);
    private ConnectionPool pool;

    public ThreadRunnable(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        Connection connection = pool.getConnection();
        String currentThreadName = StringFormatters.nestInSingleQuotations(
                Thread.currentThread().getName()
        );

        if (connection != null) {
            try {
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
        } else {
            LOGGER.info(
                    "{}{} could not acquire a connection.{}",
                    AnsiCodes.RED,
                    currentThreadName,
                    AnsiCodes.RESET_ALL

            );
        }
    }

    public ConnectionPool getPool() {
        return pool;
    }

    public void setPool(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public String toString() {
        Class<?> currClass = ThreadRunnable.class;
        String[] fieldNames = {
                "pool"
        };

        String fieldsString =
                StringFormatters.buildFieldsString(this, fieldNames);

        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
