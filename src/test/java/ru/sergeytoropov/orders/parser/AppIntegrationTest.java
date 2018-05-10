package ru.sergeytoropov.orders.parser;

import org.junit.Rule;
import org.junit.Test;
import ru.sergeytoropov.orders.parser.junitext.Pipe;

import static org.hamcrest.Matchers.*;
import static ru.sergeytoropov.orders.parser.util.InputOutputData.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author sergeytoropov
 * @since 09.05.18
 */
public class AppIntegrationTest {
    @Rule
    public Pipe pipe = new Pipe();

    public List<String> readFile(Path path) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            return reader.lines().collect(Collectors.toList());
        }
    }

    @Test
    public void pass1() throws Exception {
        App.main(new String[] {ordersCSV, ordersJSON});
        assertThat(pipe.getOutput(), containsInAnyOrder(readFile(Paths.get(ordersResult)).toArray()));
    }

    @Test
    public void pass2() throws Exception {
        App.main(new String[] {ordersErrCSV});
        assertThat(pipe.getOutput(), containsInAnyOrder(readFile(Paths.get(ordersErrResult)).toArray()));
    }
}