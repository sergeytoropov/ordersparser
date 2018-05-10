package ru.sergeytoropov.orders.parser.dto;

import static ru.sergeytoropov.orders.parser.util.Util.*;

/**
 * @author sergeytoropov
 * @since 02.05.18
 */
public class Result {
    public final Order order;
    public final String fileName;
    public final long line;
    public final boolean result;
    public final String message;

    protected Result(Order order, String fileName, long line, boolean result, String message) {
        this.order = order;
        this.fileName = fileName;
        this.line = line;
        this.result = result;
        this.message = message;
    }

    public Result(String fileName, long line, String errorMessage) {
        this(createNullOrder(), fileName, line, false, errorMessage);
    }

    public Result(Order order, String fileName, long line) {
        this(order, fileName, line, true, "OK");
    }

    @Override
    public String toString() {
        return "{" + (result == true ? order.toString() + ", " : "") +
                createAttribute("filename", fileName) +
                createAttribute("line", line) +
                createAttribute("result", message, false) + "}";
    }
}
