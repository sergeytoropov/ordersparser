package ru.sergeytoropov.orders.parser.util;

import ru.sergeytoropov.orders.parser.param.FileDescriptor;

import java.nio.file.Paths;

import static ru.sergeytoropov.orders.parser.param.FileType.*;

/**
 * @author sergeytoropov
 * @since 09.05.18
 */
public class InputOutputData {
    public static final String path = "src/test/java/ru/sergeytoropov/orders/parser/data/";
    public static final String ordersCSV = path + "orders.csv";
    public static final String ordersErrCSV = path + "orders.err.csv";
    public static final String ordersJSON = path + "orders.json";
    public static final String ordersXML = path + "orders.xml";
    public static final String ordersResult = path + "orders.result";
    public static final String ordersErrResult = path + "orders.err.result";

    public static final FileDescriptor ordersCSVDescriptor = new FileDescriptor(Paths.get(ordersCSV), CSV);
}
