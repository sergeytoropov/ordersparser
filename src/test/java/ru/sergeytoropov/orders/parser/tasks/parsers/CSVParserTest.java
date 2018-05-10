package ru.sergeytoropov.orders.parser.tasks.parsers;

import org.junit.Before;
import org.junit.Test;
import ru.sergeytoropov.orders.parser.OrdersParserException;

import java.util.concurrent.LinkedBlockingQueue;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static ru.sergeytoropov.orders.parser.util.InputOutputData.*;
import static org.junit.Assert.*;

/**
 * @author sergeytoropov
 * @since 10.05.18
 */
public class CSVParserTest {
    private CSVParser parser;

    @Before
    public void init() {
        parser = new CSVParser(new LinkedBlockingQueue<>(), ordersCSVDescriptor);
    }

    @Test
    public void toLong() throws Exception {
        assertThat(parser.toLong("100", "message"), is(100l));
    }

    @Test(expected = OrdersParserException.class)
    public void toLongException() throws Exception {
        assertThat(parser.toLong("100l", "message"), is(100l));
    }

    @Test
    public void checkId() throws Exception {
        assertThat(parser.checkId("100"), is(100l));
    }

    @Test(expected = OrdersParserException.class)
    public void checkIdException() throws Exception {
        assertThat(parser.checkId("100l"), is(100l));
    }

    @Test
    public void checkAmount() throws Exception {
        assertThat(parser.checkAmount("100"), is(100l));
    }

    @Test(expected = OrdersParserException.class)
    public void checkAmountException() throws Exception {
        assertThat(parser.checkAmount("100l"), is(100l));
    }

    @Test
    public void checkCurrency() throws Exception {
        assertThat(parser.checkCurrency("RUS"), equalToIgnoringCase("RUS"));
        assertThat(parser.checkCurrency("Rus"), equalToIgnoringCase("RUS"));
        assertThat(parser.checkCurrency("USD"), equalToIgnoringCase("USD"));
        assertThat(parser.checkCurrency("usd"), equalToIgnoringCase("USD"));
        assertThat(parser.checkCurrency("EUR"), equalToIgnoringCase("EUR"));
        assertThat(parser.checkCurrency("euR"), equalToIgnoringCase("EUR"));
    }

    @Test(expected = OrdersParserException.class)
    public void checkCurrencyException() throws Exception {
        assertThat(parser.checkCurrency("GER"), equalToIgnoringCase("GER"));
    }
}