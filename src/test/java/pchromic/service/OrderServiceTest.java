package pchromic.service;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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
    public void shouldMapCsvFile() {


    }

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
        assertEquals(2,repository.count());
    }

    @Test
    public void shouldGetTotalAmountOfOrders() {
        // when
        repository.findAll();
        // then
        assertFalse(repository.findAll().isEmpty());
        assertEquals(4,repository.findAll().size());
    }

    @Test
    public void shouldGetTotalAmountOfOrdersForClient() {

   }

    @Test
    public void shouldGetTotalOrdersValue() {
    }

    @Test
    public void shouldGetTotalOrdersValueForClient() {
    }

    @Test
    public void shouldGetAllOrders() {
    }

    @Test
    public void shouldGetAllOrdersForClient() {

    }

    @Test
    public void shouldGetAverageValueOfOrder() {

    }

    @Test
    public void shouldGetAverageValueOfOrderForClient() {
    }



}
