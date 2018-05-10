package ru.sergeytoropov.orders.parser.util;

import ru.sergeytoropov.orders.parser.OrdersParserException;
import ru.sergeytoropov.orders.parser.dto.Order;

/**
 * @author sergeytoropov
 * @since 02.05.18
 */
public class Util {
    public static String createAttribute(String name, String value, boolean hasNext) {
        String attr = "\"" + name + "\": " + "\"" + value + "\"";
        return hasNext == true ? attr + ", " : attr;
    }

    public static String createAttribute(String name, String value) {
        return createAttribute(name, value, true);
    }

    public static String createAttribute(String name, long value, boolean hasNext) {
        String attr = "\"" + name + "\": " +  value;
        return hasNext == true ? attr + ", " : attr;
    }

    public static String createAttribute(String name, long value) {
        return createAttribute(name, value, true);
    }

    public static Order createNullOrder() {
        return new Order(0, 0, "", "");
    }

    public static void interruptedException(InterruptedException ex) throws OrdersParserException {
        throw new OrdersParserException("Получен сигнал о досрочном завершии нити.", ex);
    }
}
