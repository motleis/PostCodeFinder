package tleis.postcodefinder;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mohamed
 */
public class SearchFileTest {
    
    public String filePath;
    public String file2Path;
    public String format;
    
    public SearchFileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        File f = new File("/home/mohamed/testFile1");
        File f2 = new File("/home/mohamed/testFile2");
        filePath = f.getAbsolutePath();
        file2Path = f2.getAbsolutePath();
        
        format = "\\d{4}\\s[A-Z]{2}";
        // SearchFile sf = new SearchFile(f.getAbsolutePath(), format);
        
        assertNotNull(f);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testFile1ShouldHaveNoPostCodes() {
        SearchFile instance = new SearchFile(filePath, format);
        boolean expResult = false;
        boolean result = instance.foundPostCode();        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testFile2ShouldHavePostCodes() {
        SearchFile instance = new SearchFile(file2Path, format);
        boolean expResult = true;
        boolean result = instance.foundPostCode();        
        assertEquals(expResult, result);
        System.out.println("found Post Code");
    }

    /**
     * Test of getData method, of class SearchFile.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        SearchFile instance = new SearchFile(filePath, format);
        String expResult = "";
        String result = instance.getData();
        assertEquals(expResult, result);        
    }

    @Test
    public void test2GetData() {
        System.out.println("getData");
        SearchFile instance = new SearchFile(file2Path, format);
        
        String pattern = "2334 ES";
        String data = instance.getData();
        boolean result = data.contains(pattern);
        boolean expResult = true;
        System.out.println(instance.getData());
        
        assertEquals(expResult, result);        
    }
}
