package pchromic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pchromic.controller.Controller;
import pchromic.domain.Order;
import pchromic.exception.WrongOrderFormatException;
import pchromic.repository.OrderRepository;
import pchromic.service.OrderService;

import javax.transaction.Transactional;

import java.io.*;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private
    OrderService service;

    @Autowired
    private
    OrderRepository repository;

    @Autowired
    Controller controller;

 /*   @Test
    public void shouldGetAllOrders() throws Exception {
        // given

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File("test.csv"))));


        service.mapCsv(bufferedReader);

        // when
        List<Order> foundOrders = repository.findAll();
        // then
        assertFalse(foundOrders.isEmpty());
        assertEquals(4, foundOrders.size());

    }


    @Test(expected = WrongOrderFormatException.class)
    public void shouldThrowExceptionIfOrderHasWrongFormat() throws IOException, WrongOrderFormatException {
        // given

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File("testWrong.csv"))));

        // when
        service.mapCsv(bufferedReader);

    }
*/
}