package ru.sergeytoropov.orders.parser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sergeytoropov.orders.parser.*;
import ru.sergeytoropov.orders.parser.param.Param;
import ru.sergeytoropov.orders.parser.dto.Result;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author sergeytoropov
 * @since 04.05.18
 */
@Configuration
public class ContextConfig {

    @Bean
    public BlockingQueue<Result> blockingQueue() {
        return new LinkedBlockingQueue<>();
    }

    @Bean
    public Param param() throws OrdersParserException {
        return App.getParam();
    }
}
