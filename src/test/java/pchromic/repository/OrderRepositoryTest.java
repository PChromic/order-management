package pchromic.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import pchromic.domain.Order;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional

public class OrderRepositoryTest {

    @Autowired
    private
    OrderRepository repository;

    @Test
    public void shouldGetOrdersForClient() {
        // when
        List<Order> ordersForCustomer = repository.getOrdersForClient("1");

        // then
        assertFalse(ordersForCustomer.isEmpty());
        assertEquals(3,ordersForCustomer.size());
        assertEquals("1", ordersForCustomer.get(0).getClientId());
        assertEquals("1", ordersForCustomer.get(1).getClientId());
        assertEquals("1", ordersForCustomer.get(2).getClientId());
    }

    @Test
    public void shouldGetTotalAmountOfOrders() {
        // when
        Long totalAmountOfOrders = repository.getTotalAmountOfOrders();
        // then
        assertEquals((Long)4L,totalAmountOfOrders);
    }

    @Test
    public void shouldGetTotalOrdersValue() {
        // when
        Double totalOrdersValue = repository.getTotalOrdersValue();

        // then
        assertEquals((Double) 50.22,totalOrdersValue);
    }

    @Test
    public void shouldGetTotalOrdersValueForClient() {
        // when
        Double totalOrdersValueForCustomer = repository.getTotalOrdersValueForClient("1");

        // then
        assertEquals((Double) 40.22, totalOrdersValueForCustomer);
    }


    @Test
    public void shouldGetAverageValueOfOrder() {
        // when
        Double averageValueOfOrder = repository.getAverageValueOfOrder();

        // then
        assertEquals((Double) 12.56, averageValueOfOrder);
    }

    @Test
    public void shouldGetAverageValueOfOrderForClient() {
        // when
        Double averageValueOfOrderForCustomer = repository.getAverageValueOfOrderForClient("1");

        // then
        assertEquals((Double)13.41,averageValueOfOrderForCustomer);
    }

    @Test
    public void shouldGetAllOrders(){
        // when
        List<Order> allOrders = repository.getAllOrders();
        // then
        assertEquals(4,allOrders.size());
    }
}