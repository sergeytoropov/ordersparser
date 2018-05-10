package ru.sergeytoropov.orders.parser.tasks.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sergeytoropov.orders.parser.OrdersParserException;
import ru.sergeytoropov.orders.parser.dto.Result;
import ru.sergeytoropov.orders.parser.param.FileDescriptor;

import java.util.concurrent.BlockingQueue;

/**
 * @author sergeytoropov
 * @since 02.05.18
 */
public abstract class AbstractParser implements Runnable  {
    private final static Logger log = LoggerFactory.getLogger(AbstractParser.class);

    protected final BlockingQueue<Result> blockingQueue;

    protected final FileDescriptor descriptor;

    protected AbstractParser(BlockingQueue<Result> blockingQueue, FileDescriptor descriptor) {
        this.blockingQueue = blockingQueue;
        this.descriptor = descriptor;
    }

    protected abstract void read() throws OrdersParserException;

    @Override
    public void run() {
        try {
            read();
        } catch (OrdersParserException ex) {
            log.error(ex.toString());
        }
    }

    public BlockingQueue<Result> getBlockingQueue() {
        return blockingQueue;
    }

    public FileDescriptor getDescriptor() {
        return descriptor;
    }
}
