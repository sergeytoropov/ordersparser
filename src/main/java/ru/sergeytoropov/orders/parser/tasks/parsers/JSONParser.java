package ru.sergeytoropov.orders.parser.tasks.parsers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sergeytoropov.orders.parser.OrdersParserException;
import ru.sergeytoropov.orders.parser.dto.Order;
import ru.sergeytoropov.orders.parser.dto.Result;
import ru.sergeytoropov.orders.parser.param.FileDescriptor;
import ru.sergeytoropov.orders.parser.util.Util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * @author sergeytoropov
 * @since 30.04.18
 */
public class JSONParser extends AbstractParser {
    private final static Logger log = LoggerFactory.getLogger(CSVParser.class);

    public JSONParser(BlockingQueue<Result> blockingQueue, FileDescriptor descriptor) {
        super(blockingQueue, descriptor);
    }

    // TODO Добавить грамотную обработку ошибок при парсинге json
    public void parse(ObjectMapper objectMapper, String line, long recordNumber) throws InterruptedException {
        try {
            blockingQueue.put(new Result(objectMapper.readValue(line.getBytes(), Order.class), descriptor.name.toString(), recordNumber));
        } catch (JsonParseException ex) {
            Result result = new Result(descriptor.name.toString(), recordNumber, ex.getMessage());
            log.error(result.toString());
            //blockingQueue.put(result);
        } catch (JsonMappingException ex) {
            Result result = new Result(descriptor.name.toString(), recordNumber, ex.getMessage());
            log.error(result.toString());
            //blockingQueue.put(result);
        } catch (IOException ex) {
            Result result = new Result(descriptor.name.toString(), recordNumber, ex.getMessage());
            log.error(result.toString());
            //blockingQueue.put(result);
        }
    }

    public void read() throws OrdersParserException {
        try (BufferedReader reader = new BufferedReader(new FileReader(descriptor.name.toFile()))) {
            String line;
            long recordNumber = 0;
            ObjectMapper objectMapper = new ObjectMapper();
            while ((line = reader.readLine()) != null) {
                parse(objectMapper, line, ++recordNumber);
            }
        } catch (FileNotFoundException ex) {
            throw new OrdersParserException(ex);
        } catch (IOException ex) {
            throw new OrdersParserException(ex);
        } catch (InterruptedException ex) {
            Util.interruptedException(ex);
        }
    }
}
