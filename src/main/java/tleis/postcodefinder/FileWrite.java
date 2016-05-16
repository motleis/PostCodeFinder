package tleis.postcodefinder;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;

public class FileWrite 
{
    private String path;
    private FileWriter fstream;
    private BufferedWriter out;
    
    public FileWrite()    {
        // super();
    }

    public FileWrite(String path)    {
        // super();
        this.path = path;
        System.out.println(path);
        File file = new File(path);
        try {
            if (file.exists() )  {
                FileOutputStream fos = new FileOutputStream(file);
                fos.close();                
                System.gc();

                file.delete();
                file.createNewFile();

                fstream = new FileWriter(path, true);
                out = new BufferedWriter(fstream);
            }
            else
                file.createNewFile();
        }
        catch(Exception x){x.printStackTrace();}
    }
  
    public void out(String value)
    {
       try{
           if (value.matches("newLine")) {
               // out = new BufferedWriter(fstream);
               out.newLine();               
              // out.close();
           }

           else {
               out.write(value);              
               System.out.println("data saved to file: " + value);
               // out.close();
           }
       }catch (Exception e){//Catch exception if any
           System.err.println("Error: " + e.getMessage());
       }
     }
 
    public void close() throws Exception
    {
       //Close the output stream
       out.close();
    }
 
 
}
