package com.solvd.buildingco;

import com.solvd.buildingco.exception.ClientTypeException;
import com.solvd.buildingco.multithreading.AsyncClient;
import com.solvd.buildingco.multithreading.ConnectionPool;
import com.solvd.buildingco.multithreading.ThreadRunnable;
import com.solvd.buildingco.utilities.AnsiCodes;
import com.solvd.buildingco.utilities.StringConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class MultithreadingProof {
    private static final Logger LOGGER = LogManager.getLogger(MultithreadingProof.class);
    private static final int ONE_SECOND = 1000;
    private static final int TIME_LIMIT = 20 * ONE_SECOND;
    private static final int THREAD_START_DELAY_TIME = ONE_SECOND;
    public static final int CLIENT_CONNECTION_HOLD_TIME = 6 * ONE_SECOND;


    public static void main(String[] args) throws InterruptedException {
        LOGGER.info(StringConstants.NEWLINE);

        ConnectionPool pool = ConnectionPool.getInstance();


        int choice = getChoice();

        int timer = 0;
        int threadIndex = 0;

        while (timer <= TIME_LIMIT) {
            String threadName =
                    StringConstants.SCREAMING_THREAD_STRING + StringConstants.DASH_STRING + (threadIndex + 1);
            threadIndex++;
            timer += ONE_SECOND;

            if (choice == 1) {
                Runnable task = new ThreadRunnable(pool);
                Thread clientThread = new Thread(
                        task,
                        threadName
                );

                Thread.sleep(THREAD_START_DELAY_TIME);

                clientThread.start();
            } else if (choice == 2) {
                AsyncClient asyncClient = new AsyncClient(pool);
                asyncClient.runAsync();
            } else {
                final String CLIENT_TYPE_EXCEPTION_MESSAGE =
                        "Client type unavailable";
                LOGGER.warn(CLIENT_TYPE_EXCEPTION_MESSAGE);
                throw new ClientTypeException(CLIENT_TYPE_EXCEPTION_MESSAGE);
            }
        }

    }

    private static int getChoice() {
        Scanner scanner = new Scanner(System.in);
        LOGGER.info(
                "How would you like to emulate multithreading? Input '1', '2', or '3':"
        );
        LOGGER.info("1. Start a {}java.lang.Thread{} with a {}Runnable{} passed into it. ",
                AnsiCodes.BOLD, AnsiCodes.RESET_ALL, AnsiCodes.BOLD, AnsiCodes.RESET_ALL
        );
        LOGGER.info(
                "2. Start a custom class that uses {}CompletableFuture<Connection>{} with a " +
                        "custom {}Executor{}",
                AnsiCodes.BOLD, AnsiCodes.RESET_ALL, AnsiCodes.BOLD, AnsiCodes.RESET_ALL
        );
        int choice = scanner.nextInt();
        scanner.close();
        return choice;
    }



}
