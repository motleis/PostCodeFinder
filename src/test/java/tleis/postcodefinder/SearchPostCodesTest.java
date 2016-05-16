package tleis.postcodefinder;

import java.io.File;
import java.io.IOException;
import javax.swing.JProgressBar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author mohamed
 */
public class SearchPostCodesTest {
    private String path, format, extension;
    private JProgressBar bar = null;
    private File createdFolder;
    
    public SearchPostCodesTest() {
    }
  
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testUsingTempFolder() throws IOException {
        createdFolder = folder.newFolder("newfolder");
        File createdFile = folder.newFile("myfilefile.txt");
        assertTrue(createdFile.exists());
    }
    
    @Before
    public void setUp() {
        // path += createdFolder.getAbsolutePath();
        path = "/home";
        format = "\\d{4}\\s[A-Z]{2}";
        extension = "txt";        
    }
  
    @Test
    public void testListingFiles() {
        SearchPostCodes spc = new SearchPostCodes(path, format, extension, bar); 
        int count = spc.getFileCounter();
        int countFlat = spc.getFlatFileCounter();
        
        // boolean expResult = true;
        boolean result = false;
        
        if(count>0)
            result = true;
        
        assertTrue(result);
    }
    
}
