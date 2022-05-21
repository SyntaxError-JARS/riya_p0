package com.revature.banking.util.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.time.LocalDateTime;

public class Logger {

    private static Logger logger;
    private final boolean printToConsole;
//the difference between lazy and eager they're both singleton's because they're privatizing the only have one instance one's just being lazy
// in it's only ever going to make that instance if it's being requested, otherwise it just won't exist in memory.
    private Logger(boolean printToConsole) {//So the singleton is because we're privatizing its constructor and there's only ever going to be a single instance of the logger.
        this.printToConsole = printToConsole;
    }

    public static Logger getLogger(boolean printToConsole) {
        // logger is being lazily instantiated
        if (logger == null) {
            logger = new Logger(printToConsole);
        }

        return logger;
    }

    public static Logger getLogger() {
        // logger is being lazily instantiated
        if (logger == null) {
            logger = new Logger(true);
        }

        return logger;
    }

    public void log(String message) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL file = loader.getResource("bankingapp.log");

        try (Writer logWriter = new FileWriter(String.valueOf(file).split(":")[1], true);) {
            logWriter.write(LocalDateTime.now().toString() + " LOG: " + message + "\n");

            if (printToConsole) {
                System.out.println(LocalDateTime.now().toString() + " LOG: " + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void info(String message){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL file = loader.getResource("bankingapp.log");
        System.out.println(String.valueOf(file).split(":")[1]);
        try (Writer logWriter = new FileWriter(String.valueOf(file).split(":")[1], true);){
            logWriter.write(LocalDateTime.now().toString() + " INFO: " + message + "\n");

            if(printToConsole){
                System.out.println(LocalDateTime.now().toString() + " INFO: " + message);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void debug(String message){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL file = loader.getResource("bankingapp.log");

        try (Writer logWriter = new FileWriter(String.valueOf(file).split(":")[1], true);){
            logWriter.write(LocalDateTime.now().toString() + " DEBUG: " + message + "\n");

            if(printToConsole){
                System.out.println(LocalDateTime.now().toString() + " DEBUG: " + message);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void warn(String message){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL file = loader.getResource("bankingapp.log");

        try (Writer logWriter = new FileWriter(String.valueOf(file).split(":")[1], true);){
            logWriter.write(LocalDateTime.now().toString() + " WARN: " + message + "\n");

            if(printToConsole){
                System.out.println(LocalDateTime.now().toString() + " WARN: " + message);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

}

