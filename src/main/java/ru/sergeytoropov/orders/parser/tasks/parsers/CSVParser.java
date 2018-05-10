package ru.sergeytoropov.orders.parser.tasks.parsers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sergeytoropov.orders.parser.OrdersParserException;
import ru.sergeytoropov.orders.parser.dto.Order;
import ru.sergeytoropov.orders.parser.dto.Result;
import ru.sergeytoropov.orders.parser.param.FileDescriptor;
import ru.sergeytoropov.orders.parser.util.Util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.BlockingQueue;

/**
 * @author sergeytoropov
 * @since 30.04.18
 */
public class CSVParser extends AbstractParser {
    private final static Logger log = LoggerFactory.getLogger(CSVParser.class);

    public CSVParser(BlockingQueue<Result> blockingQueue, FileDescriptor descriptor) {
        super(blockingQueue, descriptor);
    }

    public long toLong(String value, String message) throws OrdersParserException {
        try {
            return Long.valueOf(value);
        } catch (RuntimeException ex) {
            throw new OrdersParserException(message, ex);
        }
    }

    public long checkId(String value) throws OrdersParserException {
        return toLong(value, "Не валидный идентификатор id [" + value + "]");
    }

    public long checkAmount(String value) throws OrdersParserException {
        return toLong(value, "Не валидна сумма платежа (только целые значения) amount [" + value + "]");
    }

    public String checkCurrency(String value) throws OrdersParserException {
        String currency = value.toUpperCase();
        if ("USD".equals(currency) || "RUS".equals(currency) || "EUR".equals(currency)) {
            return value;
        }
        throw new OrdersParserException("Не валидна валюта (допустимые значения USD|RUS|EUR) currency = [" + value + "]");
    }

    public Order createOrder(CSVRecord record) throws OrdersParserException {
        return new Order(
                checkId(record.get(0)),
                checkAmount(record.get(1)),
                checkCurrency(record.get(2)),
                record.get(3));
    }

    public void parse(CSVRecord record) throws InterruptedException {
        try {
            blockingQueue.put(new Result(createOrder(record), descriptor.name.toString(), record.getRecordNumber()));
        } catch (OrdersParserException ex) {
            Result result = new Result(descriptor.name.toString(), record.getRecordNumber(), ex.getMessage());
            log.error(result.toString());
            blockingQueue.put(result);
        }
    }

    @Override
    public void read() throws OrdersParserException {
        try (Reader in = new FileReader(descriptor.name.toFile())) {
            Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
            for (CSVRecord record : records) {
                parse(record);
            }
        } catch (IOException ex) {
            throw new OrdersParserException(ex);
        } catch (InterruptedException ex) {
            Util.interruptedException(ex);
        }
    }
}
