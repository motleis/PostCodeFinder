package tleis.postcodefinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mohamed
 */
public class SearchFile {
    private String filePath, format;
    private boolean isFound = false;
    private String data = "";
    private File file;
    
    
    public SearchFile(){}
    
    public SearchFile(String filePath, String format) {
        this.filePath = filePath;
        this.format = format;
        file = new File(filePath);
        
        checkLines();
    }
   
    private void checkLines()
    {
        try {
            final Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                final String lineFromFile = scanner.nextLine();
                
                Pattern p = Pattern.compile(format);
                Matcher m = p.matcher(lineFromFile);
                
                if(m.find()) {
                    isFound = true;
                    System.out.println("FOUND!" + filePath);
                    data += filePath;
                    data += "\n\t" + lineFromFile + "\n";
                    // data += m.group(0) + "\t" + filePath;
                }
            }
        }
        catch(FileNotFoundException fe) {
            fe.printStackTrace();
        }
    }
    
    public boolean foundPostCode() {
        return isFound;
    }
    
    public String getData() {
        return data;
    }
    
    
    public static void main(String args[])
    {
       SearchFile sf = new SearchFile();
       String myString = "alahgahlagh2334ESAadg3423ABakl";
      
       Pattern p = Pattern.compile("\\d{4}[A-Z]{2}");
       Matcher m = p.matcher(myString);
                
       System.out.println(" FOUND? : " + m.find() );
               
       System.out.println(m.group(0) );
                
    }
}
