package ru.sergeytoropov.orders.parser;

/**
 * @author sergeytoropov
 * @since 05.04.18
 */
public class OrdersParserException extends Exception {
    public OrdersParserException() {
        super();
    }

    public OrdersParserException(String message) {
        super(message);
    }

    public OrdersParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrdersParserException(Throwable cause) {
        super(cause);
    }
}
