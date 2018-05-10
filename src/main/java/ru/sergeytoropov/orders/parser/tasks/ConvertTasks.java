package ru.sergeytoropov.orders.parser.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sergeytoropov.orders.parser.dto.Result;
import ru.sergeytoropov.orders.parser.tasks.converts.BaseConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author sergeytoropov
 * @since 08.05.18
 */
@Component
public class ConvertTasks {
    private final List<Runnable> convertTasks = new ArrayList<>();

    @Autowired
    private BlockingQueue<Result> blockingQueue;

    public List<Runnable> getConvertTasks() {
        if (convertTasks.size() == 0) {
            convertTasks.add(new BaseConvert(blockingQueue));
            convertTasks.add(new BaseConvert(blockingQueue));
        }
        return convertTasks;
    }
}
