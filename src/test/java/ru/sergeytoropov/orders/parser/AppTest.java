package ru.sergeytoropov.orders.parser;

import org.junit.Before;
import org.junit.Test;
import ru.sergeytoropov.orders.parser.param.FileDescriptor;
import ru.sergeytoropov.orders.parser.param.Param;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static ru.sergeytoropov.orders.parser.param.FileType.*;
import static ru.sergeytoropov.orders.parser.util.InputOutputData.*;

import static org.junit.Assert.*;

/**
 * @author sergeytoropov
 * @since 09.05.18
 */
public class AppTest {
    private Param param;

    @Before
    public void create() {
        List<FileDescriptor> descriptors = new ArrayList<>();
        descriptors.add(new FileDescriptor(Paths.get(ordersCSV), CSV));
        descriptors.add(new FileDescriptor(Paths.get(ordersJSON), JSON));
        param = new Param(descriptors);
    }

    @Test
    public void init() throws Exception {
        assertThat(App.init(new String[] {ordersCSV, ordersJSON}), is(param));
    }

    @Test
    public void main() {
        App.main(new String[] {ordersErrCSV});
    }
}