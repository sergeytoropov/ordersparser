package ru.sergeytoropov.orders.parser.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sergeytoropov.orders.parser.param.Param;
import ru.sergeytoropov.orders.parser.tasks.parsers.CSVParser;
import ru.sergeytoropov.orders.parser.dto.Result;
import ru.sergeytoropov.orders.parser.tasks.parsers.JSONParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author sergeytoropov
 * @since 04.05.18
 */
@Component
public class ParserTasks {
    private final List<Runnable> parserTasks = new ArrayList<>();

    @Autowired
    private BlockingQueue<Result> blockingQueue;

    @Autowired
    private Param param;

    public List<Runnable> getParserTasks() {
        if (parserTasks.size() == 0) {
            param.fileDescriptors.stream().forEach(desc -> {
                switch (desc.type) {
                    case CSV:
                        parserTasks.add(new CSVParser(blockingQueue, desc));
                        break;
                    case JSON:
                        parserTasks.add(new JSONParser(blockingQueue, desc));
                        break;
                }
            });
        }
        return parserTasks;
    }
}
