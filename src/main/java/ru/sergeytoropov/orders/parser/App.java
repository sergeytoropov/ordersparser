package ru.sergeytoropov.orders.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sergeytoropov.orders.parser.param.Args;
import ru.sergeytoropov.orders.parser.param.Param;

/**
 * @author sergeytoropov
 * @since 04.05.18
 */
public class App {
    private final static Logger log = LoggerFactory.getLogger(App.class);

    private static Param param = null;

    public static Param getParam() throws OrdersParserException {
        if (param == null) {
            throw new OrdersParserException("Не заданы входные параметры.");
        }
        return param;
    }

    public static Param init(String[] args) throws OrdersParserException {
        try {
            Args arguments = new Args(args);
            return new Param(arguments.getFileDescriptors());
        } catch (OrdersParserException ex) {
            Args.usage();
            throw ex;
        }
    }

    public static void run() throws OrdersParserException {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring-app.xml")) {
            OrdersParser ordersParser = appCtx.getBean(OrdersParser.class);
            ordersParser.run();
        } catch (BeanCreationException ex) {
            throw new OrdersParserException(ex);
        }
    }

    public static void main(String[] args) {
        try {
            param = init(args);
            run();
        } catch (OrdersParserException ex) {
            log.error(ex.getMessage());
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.toString());
            System.out.println(ex.toString());
        }
    }
}
