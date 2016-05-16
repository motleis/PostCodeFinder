package tleis.postcodefinder;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 * @Author Mohamed Tleis
 * Main GUI class consists of a simple Interface for the PostCode Finder application
 */

public class Main extends JFrame {
    private Dimension window = new Dimension(500, 300);
    private Dimension textFieldDimension = new Dimension(250, 35);
    private Dimension buttonDimension = new Dimension(100, 35);
    final private JFileChooser chooser = new JFileChooser();
    protected JProgressBar progressBar;
    private Dimension progressBarDimension = new Dimension(450, 25);
    private JTextField fileServerTF = new JTextField();
    private JTextField extensionTF = new JTextField();
    private JTextField formatTF = new JTextField();
    
    /* User can specify the location of the file server to be searched */
    private JPanel fileServerLocation() {
        JPanel fileServerLocation = new JPanel();
        
        // This panel has three components; a label, a textfield and a browse button
        JLabel fileServerLabel = new JLabel("File Server Path:");
        fileServerTF = new JTextField("/home/mohamed");
        fileServerTF.setPreferredSize(textFieldDimension);
        JButton browse = new JButton("Browse");
   
        // Use lambda expression to call ActionPerformed from the functional interface
        browse.addActionListener(e -> {
            File directory = chooseDirectory();
            fileServerTF.setText(directory.getAbsolutePath());
        });
        
        browse.setPreferredSize(buttonDimension);
        
        // add the components to the JPanel
        fileServerLocation.add(fileServerLabel);
        fileServerLocation.add(fileServerTF);
        fileServerLocation.add(browse);
        
        return fileServerLocation;
    }
    
    /* Open Dialog to choose file server path 
     * If the requirement is to browse remote path, there is an option
     *  http://vfsjfilechooser.sourceforge.net/    */
    public File chooseDirectory() {
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select File Server location:");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    
        // disable the "All files" option.
        chooser.setAcceptAllFileFilterUsed(false);
          
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {         
            System.out.println("getCurrentDirectory(): " 
                +  chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : " 
                +  chooser.getSelectedFile());      
        }
        else {
            System.out.println("No Selection ");
        }
    
        return chooser.getSelectedFile();
  }
    
    /* User can specify the formats as regex expressions */     
    private JPanel postcodeFormats() {
        JPanel postCodeFormat = new JPanel();
        
        JLabel formatLabel = new JLabel("Post Code Format : ");
        // By Default use the Dutch PostCode Format 4 digits followed by a space 
        // then two Capital Letter
        formatTF = new JTextField("\\d{4}\\s[A-Z]{2}");
        formatTF.setPreferredSize(textFieldDimension);
        
        postCodeFormat.add(formatLabel);
        postCodeFormat.add(formatTF);
        
        return postCodeFormat;
    }
    
    /* Start the search process */
    private JPanel search() {
        JPanel searchPanel = new JPanel();
        
        JButton searchBT = new JButton("Search");
        searchBT.setPreferredSize(buttonDimension);
        searchBT.addActionListener(e2 -> {
            
            long time1 = System.currentTimeMillis();
            
            // Call the Search Class
            Task task = new Task();
            task.execute();
            
            long time2 = System.currentTimeMillis();
            System.out.println("Time Searching " + ((time2-time1)) + " milli-second!");
        });
        
        searchPanel.add(searchBT);
        
        return searchPanel;
    }
    
    /* Track the progess of the search process */
    private JPanel progress() {
        
        JPanel progressPanel = new JPanel();
        
        // prepare the progressBar
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(progressBarDimension);        
        
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
        progressPanel.add(progressBar);
        
        return progressPanel;
    }
   
    
    
class Task extends SwingWorker<Void, Void> 
{
    @Override
    public Void doInBackground() {

        setProgress(0);

        try{
            long time1 = System.currentTimeMillis();
          
          new SearchPostCodes(
                    fileServerTF.getText(),
                    formatTF.getText(),
                    extensionTF.getText(),
                    progressBar
                );
           
            long time2 = System.currentTimeMillis();
            System.out.println("Time Searching " + ((time2-time1)) + " milli-second!");
            
        }           
        catch(Exception ex)
        {
            ex.printStackTrace();                        
        }

        return null;                        
    }
    
     /* Executed in event dispatching thread */
    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
        progressBar.setValue(0);                        
    }

    public void publicSetProgress(int prog) {
        // try{Thread.sleep(1000);}catch(Exception x){x.printStackTrace();}
        int progress = prog;
        progressBar.setValue(progress);            
        setProgress(progress);
    }
}
    
    
    /* User can specify extensions of flat files */
    private JPanel fileExtensions() {
        JPanel fileExtensionPanel = new JPanel();
        
        JLabel extensionLabel = new JLabel("Flat File Extension: ");
        extensionTF = new JTextField("txt");
        extensionTF.setPreferredSize(textFieldDimension);
        
        fileExtensionPanel.add(extensionLabel);
        fileExtensionPanel.add(extensionTF);
        
        return fileExtensionPanel;
    }
    
    /* Prepare the GUI interface */
    private void prepareInterface() {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout( ) );
        
        // add Panels
        frame.add(fileServerLocation());
        frame.add(postcodeFormats());
        frame.add(fileExtensions());
        frame.add(search());
        frame.add(progress());

        
        Container con = getContentPane();
        frame.setVisible(true);
        frame.pack();
        frame.setTitle("Post-Code Finder");
        frame.setSize(window);
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );        
        frame.add(con);         
    }
    
    /* the main method */
    public static void main(String args[]) {
        Main app = new Main();
        app.prepareInterface();        
    }
}
