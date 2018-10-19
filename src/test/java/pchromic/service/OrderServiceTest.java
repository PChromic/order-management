package pchromic.service;

import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import pchromic.OrderManagementApplication;
import pchromic.repository.OrderRepository;
import pchromic.service.Impl.OrderServiceImpl;
import sun.nio.cs.UTF_32;
import sun.text.normalizer.UTF16;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional

public class OrderServiceTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Autowired
    OrderServiceImpl service;

    @Autowired
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
        repository.findAll();
        assertTrue(repository.findAll().isEmpty());

    }

    @Test
    public void shouldGetTotalAmountOfOrdersForCustomer () {
   }

    @Test
    public void shouldGetTotalOrdersValue() {
    }

    @Test
    public void shouldGetTotalOrdersValueForCustomter() {
    }

    @Test
    public void shouldGetAllOrders() {
    }

    @Test
    public void shouldGetAllOrdersForCustomer() {

    }

    @Test
    public void shouldGetAverageValueOfOrder() {

    }

    @Test
    public void shouldGetAverageValueOfOrderForCustomer() {
    }



}
