package ru.sergeytoropov.orders.parser.dto;

/**
 * @author sergeytoropov
 * @since 08.05.18
 */
public class NullResult extends Result {
    public NullResult() {
        super(null, "", 0, true, "");
    }

    @Override
    public String toString() {
        return "NullResult{}";
    }
}
