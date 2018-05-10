package ru.sergeytoropov.orders.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sergeytoropov.orders.parser.dto.NullResult;
import ru.sergeytoropov.orders.parser.dto.Result;
import ru.sergeytoropov.orders.parser.tasks.ConvertTasks;
import ru.sergeytoropov.orders.parser.tasks.ParserTasks;
import ru.sergeytoropov.orders.parser.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author sergeytoropov
 * @since 01.05.18
 */
@Component
public class OrdersParser {

    @Autowired
    private BlockingQueue<Result> blockingQueue;

    @Autowired
    private ParserTasks parserTasks;

    @Autowired
    private ConvertTasks convertTasks;

    private ExecutorService serviceParserTasks = Executors.newFixedThreadPool(3);
    private List<Future> listParserTasks = new ArrayList<>();

    private ExecutorService serviceConvertTasks = Executors.newFixedThreadPool(2);
    private List<Future> listConvertTasks = new ArrayList<>();

    public void init() {
        listParserTasks.clear();
        parserTasks.getParserTasks().stream().forEach(runnable -> {
            Future future = serviceParserTasks.submit(runnable);
            listParserTasks.add(future);
        });

        listConvertTasks.clear();
        convertTasks.getConvertTasks().stream().forEach(runnable -> {
            Future future = serviceConvertTasks.submit(runnable);
            listConvertTasks.add(future);
        });
    }

    private void repeat(List<Future> tasks, Runnable runnable) throws OrdersParserException {
        try {
            while (true) {
                boolean stop = true;
                for (Future future : tasks) {
                    if (!future.isDone()) {
                        stop = false;
                        break;
                    }
                }
                if (stop) {
                    break;
                }
                runnable.run();
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            Util.interruptedException(ex);
        }

    }

    public void waiting() throws OrdersParserException {
        repeat(listParserTasks, () -> {});
        serviceParserTasks.shutdown();
    }

    public void stop() throws OrdersParserException{
        repeat(listConvertTasks, () -> {
            try {
                blockingQueue.put(new NullResult());
            } catch (InterruptedException ex) {}
        });
        serviceConvertTasks.shutdown();
    }

    public void run() throws OrdersParserException {
        init();
        waiting();
        stop();
    }
}
