package pchromic.mapper;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class XmlOrderMapperTest {


    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();


    @Before
    public void setUp() throws Exception {

   /*     final File tempFile = tempFolder.newFile("tempFile.txt");

        // Write something to it.
        for (int i = 0; i < ; i++) {

        }
        FileUtils.writeStringToFile(tempFile,"Blabla",Charset.forName("UTF-8"));
        // Read it from temp file
        final String s = FileUtils.readFileToString(tempFile,Charset.forName("UTF-8"));

        // Verify the content
        Assert.assertEquals("Blabla", s);

        //Note: File is guaranteed to be deleted after the test finishes.
        */
    }



}