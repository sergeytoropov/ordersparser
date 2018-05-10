package ru.sergeytoropov.orders.parser.param;

/**
 * @author sergeytoropov
 * @since 01.05.18
 */
public enum FileType {
    CSV(".csv"), JSON(".json");

    private String name;

    FileType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
