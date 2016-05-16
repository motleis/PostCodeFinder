package tleis.postcodefinder;

import java.io.File;
import java.util.concurrent.Semaphore;
import javax.swing.JProgressBar;

/**
 *
 * @author mohamed
 */
public class SearchPostCodes {
    private String path, format, extension;
    private int fileCounter;
    private int flatFileCounter;
    final private int numberOfThreads = 1024;
    final private int filesWriting = 1;
    private long time0;
    private long time1;
    private Semaphore writeFile = new Semaphore(filesWriting);
    private FileWrite fw;
    private JProgressBar progressBar;
    private int runningThreads = 0;
    
    public SearchPostCodes(String path, String format, String extension, JProgressBar progressBar) {
        /* We search in location 'path' within all files with extension 'extension'
         * for a post code of format 'format' */
        this.path = path;
        this.extension = extension;
        this.format = format;
        this.progressBar = progressBar;
        
        System.out.println("FileWrite Class...");
        File filePath = new File(path);
        
        // File Write is a singleton class
        if(fw == null)
            fw = new FileWrite(filePath.getAbsolutePath() + File.separator + "foundCodes");
        

        time0 = System.currentTimeMillis();
        
        System.out.println("path: " + path);
        // initiateSearch();
        fileCounter = 0;
        flatFileCounter = 0;
        listFiles(path);        
        
        try{
            // Wait until all threads are executed before closing writing file
            while(runningThreads != 0) {
                System.out.println(runningThreads + " are still executing ...");
                Thread.sleep(100);                
            }
            fw.close();
            
            System.out.println("Files: " + fileCounter);
            System.out.println("Flat Files: " +flatFileCounter);
        }catch(Exception x ){x.printStackTrace();}
    }
    
    // private File[] listFiles(String directoryName) {
    private void listFiles(String directoryName) {
         try {  
            // list file
            File directory = new File(directoryName);

            // get all the files from a directory
            // If speed is required, there are faster implmentations available, 
            // iteration instead of listing files is an option to consider as well.
            File[] fList = directory.listFiles();
            
            double progressComplete = 0;
            Semaphore thread = new Semaphore(numberOfThreads);
            for (File file : fList) {
                if (file.isFile()) {
                    // System.out.println(file.getAbsolutePath());
                    fileCounter++;
                    
                    // In case the file is a file of specified extension
                    if(file.getName().endsWith(extension)) {
                        flatFileCounter++;
                        time1 = System.currentTimeMillis();
                        try {
                            thread.acquire();
                            addNumOfThreads();
                            final String fileName = file.getName();
                            final String filePath = file.getAbsolutePath();
                            
                            time0 = System.nanoTime();
                            new Thread() {
                                public void run() {
                                    try {
//                                        System.out.println(fileName + " in a new thread" );
                                        searchFile(filePath);
                                        } 
                                    catch(Exception x){ 
                                        x.printStackTrace();                                     
                                    }
                                    finally {
                                        thread.release();
                                        decrementNumOfThreads();
                                        time1 = System.nanoTime();
//                                        System.out.println( fileName + " Released ; in : " 
//                                            + ((time1-time0)/1000) + " micro-seconds");
                                    }
                                }
                            }.start();                            
                        }
                        catch(InterruptedException ie) {
                            ie.printStackTrace();
                            // fw.close();
                        }
                    }
                    // System.out.println("flat file: " + file.getName());
                }
                // In case file is a directory, recursively call listFiles method
                else if (file.isDirectory()) {
                    listFiles(file.getAbsolutePath());
                }
                progressComplete += (double)1/(double)fList.length;
                progressBar.setValue((int)(progressComplete*100));
            }
            
         
        }
        catch(NullPointerException x){
            // System.out.println("WARNING: Couldn't list files in " + directoryName );
            // System.out.print("Counter : " + fileCounter);
        }
        catch(Exception y){
            System.out.println("Listing of files failed!");
            y.printStackTrace();
        }
    }
    
    /* Call SearchFile Class to search the given file, for the given postcode format*/
    private void searchFile(String filePath) {
        SearchFile sf = new SearchFile(filePath, format);
        if(sf.foundPostCode() == true) {
            String data = sf.getData();
            try{
                writeFile.acquire();
                fw.out(data);
            }
            catch(Exception x) {
                x.printStackTrace();
            }
            finally { 
                writeFile.release();
            }                                        
        }
    }
    
    
  public synchronized void addNumOfThreads(){
      runningThreads++;
  }
  
  public synchronized void decrementNumOfThreads(){
      runningThreads--;
  }
   
  public int getFileCounter() { 
      return fileCounter;
  }
  
  public int getFlatFileCounter() { 
      return flatFileCounter;
  }
}
