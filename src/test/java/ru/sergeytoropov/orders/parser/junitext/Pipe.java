package ru.sergeytoropov.orders.parser.junitext;

import org.junit.rules.ExternalResource;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sergeytoropov
 * @since 09.05.18
 */
public class Pipe extends ExternalResource {
    private InputStream in = null;
    private PrintStream out = null;
    private OutputStream resultStream = null;
    private boolean isEnabled = false;

    @Override
    protected void before() throws Throwable {
        super.before();
        enable();
    }

    @Override
    protected void after() {
        super.after();
        disable();
    }

    public void enable() {
        if (!isEnabled) {
            in = System.in;
            System.setIn(new ByteArrayInputStream("".getBytes()));
            out = System.out;
            resultStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(resultStream));
            isEnabled = true;
        }
    }

    private void closeSystemIn() {
        try {
            System.in.close();
        } catch (IOException ioe) {}
    }

    public void disable() {
        if (isEnabled) {
            isEnabled = false;
            System.out.close();
            System.setOut(out);
            closeSystemIn();
            System.setIn(in);
        }
    }

    public void prepare(List<String> inputData) {
        if (!isEnabled) {
            throw new IllegalStateException();
        }
        closeSystemIn();
        System.setIn(new ByteArrayInputStream(inputData.stream()
                .reduce("", (acc, element) -> acc += element + "\n").getBytes()));
    }

    public void prepare(String[] inputData) {
        prepare(Arrays.stream(inputData).collect(Collectors.toList()));
    }

    public List<String> getOutput() throws IOException {
        if (!isEnabled) {
            throw new IllegalStateException();
        }
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(resultStream.toString()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        }
        return result;
    }
}
