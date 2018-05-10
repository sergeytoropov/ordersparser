package ru.sergeytoropov.orders.parser;

import org.junit.Before;
import org.junit.Test;
import ru.sergeytoropov.orders.parser.param.Args;
import ru.sergeytoropov.orders.parser.param.FileDescriptor;
import ru.sergeytoropov.orders.parser.param.FileType;
import ru.sergeytoropov.orders.parser.util.InputOutputData;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static ru.sergeytoropov.orders.parser.param.FileType.CSV;
import static ru.sergeytoropov.orders.parser.param.FileType.JSON;

/**
 * @author sergeytoropov
 * @since 01.05.18
 */
public class ArgsTest {
    private final String ordersCSV = InputOutputData.ordersCSV;
    private final String ordersJSON = InputOutputData.ordersJSON;
    private final String ordersXML = InputOutputData.ordersXML;
    private final Path ordersCSVPath = Paths.get(ordersCSV);
    private final Path ordersJSONPath = Paths.get(ordersJSON);

    private Args args;
    private Args badArgs;

    public FileDescriptor getOrders(FileType type) {
        switch (type) {
            case CSV:
                return new FileDescriptor(ordersCSVPath, type);
            case JSON:
                return new FileDescriptor(ordersJSONPath, type);
        }
        return null;
    }

    public FileDescriptor getOrdersCSV() {
        return getOrders(CSV);
    }

    public FileDescriptor getOrdersJSON() {
        return getOrders(JSON);
    }

    @Before
    public void init() {
        args = new Args(new String[] {ordersCSV, ordersJSON});
        badArgs = new Args(new String[] {ordersXML, ordersCSV, ordersJSON});
    }

    @Test
    public void checkExtension() throws Exception {
        assertThat(args.checkExtension(ordersCSV, CSV), is(true));
        assertThat(args.checkExtension(ordersJSON, JSON), is(true));
        assertThat(args.checkExtension(ordersXML, CSV), is(false));
        assertThat(args.checkExtension(ordersXML, JSON), is(false));
    }

    @Test
    public void createFileDescriptor() throws Exception {
        assertThat(args.createFileDescriptor(ordersCSV, CSV), is(getOrdersCSV()));
    }

    @Test
    public void getFileDescriptors() throws Exception {
        assertThat(args.getFileDescriptors(), contains(getOrdersCSV(), getOrdersJSON()));
    }

    @Test(expected = OrdersParserException.class)
    public void getFileDescriptorsBad() throws Exception {
        assertThat(badArgs.getFileDescriptors(), contains(getOrdersCSV(), getOrdersJSON()));
    }

    @Test
    public void createPath() throws Exception {
        assertThat(args.createPath(ordersCSV), is(ordersCSVPath));
        assertThat(args.createPath(ordersJSON), is(ordersJSONPath));
    }

    @Test(expected = OrdersParserException.class)
    public void createPathException() throws Exception {
        args.createPath(ordersXML);
    }
}