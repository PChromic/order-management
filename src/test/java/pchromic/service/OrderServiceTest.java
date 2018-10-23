package pchromic.service;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pchromic.domain.Order;
import pchromic.repository.OrderRepository;
import pchromic.service.Impl.OrderServiceImpl;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {


    @Rule
    public final TemporaryFolder tempFolder = new TemporaryFolder();

    @Autowired
    private
    OrderServiceImpl service;

    @Autowired
    private
    OrderRepository repository;


    @Test
    public void shouldMapCsvFileToStringArray() throws IOException {
        // given
        final File tempFile = tempFolder.newFile("tempFile.csv");

        List<String> strings = new ArrayList<>(Arrays.asList(
                "Client_Id,Request_Id,Name,Quantity,Price",
                "1,1,Bułka,1,10.11",
                "2,1,Chleb,1,10.00"
        ));

        FileUtils.writeLines(tempFile,strings);

        // when
        service.mapCsv(tempFile);

        // then
        assertNotNull(repository);
        assertEquals(6,repository.count());
      }

    @Test
    public void shouldGetTotalAmountOfOrders() {
        // when
        List<Order> allOrders = service.getAllOrders();
        // then
        assertFalse(allOrders.isEmpty());
        assertEquals(4,allOrders.size());
    }

    @Test
    public void shouldGetTotalAmountOfOrdersForClient() {
        // when
        Integer totalAmountOfOrdersForClient = service.getTotalAmountOfOrdersForClient("2");

        // then
        assertEquals((Integer)1,totalAmountOfOrdersForClient);

    }

    @Test
    public void shouldGetTotalOrdersValue() {
        // when
        Double totalOrdersValue = service.getTotalOrdersValue();
        // then
        assertEquals((Double)50.22,totalOrdersValue);
    }

    @Test
    public void shouldGetTotalOrdersValueForClient() {
        // when
        Double totalAmountOfOrdersForClient = service.getTotalOrdersValueForClient("1");
        // then
        assertEquals((Double) 40.22, totalAmountOfOrdersForClient);
    }

    @Test
    public void shouldGetAllOrders() {
        // when
        List<Order> allOrders = service.getAllOrders();
        // then
        assertFalse(allOrders.isEmpty());
        assertEquals(4,allOrders.size());
    }

    @Test
    public void shouldGetAllOrdersForClient() {
        // when
        List<Order> allOrdersForClient = service.getAllOrdersForClient("1");
        // then
        assertFalse(allOrdersForClient.isEmpty());
        assertEquals(3,allOrdersForClient.size());
        assertEquals("1",allOrdersForClient.get(0).getClientId());
        assertEquals("1",allOrdersForClient.get(1).getClientId());
        assertEquals("1",allOrdersForClient.get(2).getClientId());
    }


    @Test
    public void shouldGetAverageValueOfOrder() {
        // when
        Double averageValueOfOrder = service.getAverageValueOfOrder();
        // then
        assertEquals((Double)12.56,averageValueOfOrder);
    }

    @Test
    public void shouldGetAverageValueOfOrderForClient() {
        // when
        Double averageValueOfOrderForClient = service.getAverageValueOfOrderForClient("1");
        // then
        assertEquals((Double) 13.41, averageValueOfOrderForClient);
    }



}
