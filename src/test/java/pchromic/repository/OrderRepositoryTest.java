package pchromic.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        assertTrue(ordersForCustomer.get(0).getClientId().equals("1"));
        assertTrue(ordersForCustomer.get(1).getClientId().equals("1"));
        assertTrue(ordersForCustomer.get(2).getClientId().equals("1"));
    }

    @Test
    public void shouldGetTotalAmountOfOrders() {
        // when
        Integer totalAmountOfOrders = repository.getTotalAmountOfOrders();
        // then
        assertEquals((Integer)4,totalAmountOfOrders);
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
}