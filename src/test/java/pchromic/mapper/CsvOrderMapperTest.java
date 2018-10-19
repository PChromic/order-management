package pchromic.mapper;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pchromic.repository.OrderRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class CsvOrderMapperTest {


    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Autowired
    CsvOrderMapper mapper;
    @Autowired
    OrderRepository repository;


    @Test
    public void shouldMapFileToObject() throws IOException {
        // given
        final File tempFile = tempFolder.newFile("tempFile.csv");

        String[] header = {};
        String[] strings = {
                "Client_Id,Request_Id,Name,Quantity,Price",
                "1,1,Bułka,1,10.11\n",
                "1,1,Chleb,2,15.00\n",

                };

        for (int i = 0; i < strings.length-1 ; i++) {
            FileUtils.writeStringToFile(tempFile,strings[i],Charset.forName("UTF-8"));
        }

        String[] stringsFromFile = new String[strings.length];
        for (int i = 0; i < strings.length-1; i++) {
            stringsFromFile[i] = FileUtils.readFileToString(tempFile,Charset.forName("UTF-8"));
        }

        // when
        mapper.map(stringsFromFile);

        // then
        assertNotNull(repository);
        assertEquals(4,repository.count());

    }
}