package ru.sergeytoropov.orders.parser.tasks.converts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sergeytoropov.orders.parser.OrdersParserException;
import ru.sergeytoropov.orders.parser.dto.NullResult;
import ru.sergeytoropov.orders.parser.dto.Result;
import ru.sergeytoropov.orders.parser.util.Util;

import java.util.concurrent.BlockingQueue;

/**
 * @author sergeytoropov
 * @since 08.05.18
 */
public class BaseConvert implements Runnable {
    private final static Logger log = LoggerFactory.getLogger(BaseConvert.class);

    protected final BlockingQueue<Result> blockingQueue;

    public BaseConvert(BlockingQueue<Result> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void read() throws OrdersParserException {
        try {
            while (true) {
                Result result = blockingQueue.take();
                if (result instanceof NullResult) {
                    break;
                }
                System.out.println(result);
            }
        } catch (InterruptedException ex) {
            Util.interruptedException(ex);
        }
    }

    @Override
    public void run() {
        try {
            read();
        } catch (OrdersParserException ex) {
            log.error(ex.toString());
        }
    }
}
