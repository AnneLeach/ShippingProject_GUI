package shippingstore;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import java.util.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.logging.*;
import java.io.IOException;

/**
* Main access point
*/
public class MainApp {
    
    ShippingStore ss;
    private final Scanner sc; // Used to read from System's standard input
    
    /**
     * Constructor
     */
    public MainApp() { 
        ss = ShippingStore.readDatabase();
        this.sc = new Scanner(System.in);
    }
    
    /**
    * This method servers as the main interface between the program and the user.
    * The method interacts with the user by printing out a set of options, and
    * asking the user to select one.
    */
   public void runSoftware() throws IOException{

//************************************************************************************************************

        // Borders setup.
        TitledBorder packageBorder = BorderFactory.createTitledBorder("Packages");
        TitledBorder userBorder = BorderFactory.createTitledBorder("Users");  
        TitledBorder innerBorder = BorderFactory.createTitledBorder("Main Menu");
        // Broadly used variables
        final Dimension mainButton = new Dimension(180, 30);
        final Dimension smallButton = new Dimension(130, 30);
        final Dimension eastDim = new Dimension(280, 30);
        final Dimension tArea = new Dimension(700, 300);
        // Logger
        final Logger logger = Logger.getLogger("program.runsoftware");
        final FileHandler fh = new FileHandler("ProgramLog.txt");
        logger.addHandler(fh);
        logger.setLevel(Level.ALL);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

        //*************************************************************
        // MAIN FRAME
        //*************************************************************
        JFrame mainFrame = new JFrame("Firefly Freight Services");
        mainFrame.setSize(500, 400);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Save option upon closing
        mainFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                // Confirm changes
                int n = JOptionPane.showConfirmDialog(mainFrame, "Would you like to save your changes?",
                        "Exiting Program", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION){
                    logger.log(Level.INFO, "User saved work.");
                    ss.writeDatabase();
                }
                else{
                    logger.log(Level.INFO, "User did not save work.");
                }
            }
        });
        
        // Padding
        JPanel horizontalPad = new JPanel();
        horizontalPad.setPreferredSize(new Dimension(30, 400));
        JPanel horizontalPad2 = new JPanel();
        horizontalPad2.setPreferredSize(new Dimension(30, 400));        
        JPanel verticalPad = new JPanel();
        verticalPad.setPreferredSize(new Dimension(500, 30));
        JPanel verticalPad2 = new JPanel();
        verticalPad2.setPreferredSize(new Dimension(500, 30));        
        mainFrame.add(horizontalPad, BorderLayout.EAST);
        mainFrame.add(horizontalPad2, BorderLayout.WEST);
        mainFrame.add(verticalPad, BorderLayout.SOUTH);
        mainFrame.add(verticalPad2, BorderLayout.NORTH);        
        
        //*************************************************************
        // INNER FRAME
        //*************************************************************
        JPanel innerFrame = new JPanel();
        // Layout
        innerFrame.setLayout(new BorderLayout());
        // Border
        innerFrame.setBorder(innerBorder);
        // Add to mainFrame.
        mainFrame.add(innerFrame, BorderLayout.CENTER);        

        //*************************************************************
        // PANEL 1
        //*************************************************************
        JPanel panel1 = new JPanel();
        // Size
        panel1.setPreferredSize(new Dimension(350, 20));
        // Layout
        panel1.setLayout(new BorderLayout());
        // Border
        // Add to inner Frame.
        innerFrame.add(panel1, BorderLayout.NORTH);

        //*************************************************************
        // PANEL 2
        //*************************************************************
        JPanel panel2 = new JPanel();          
        // Size
        // Layout
        panel2.setLayout(new FlowLayout());
        // Border    
        // Add to inner frame.
        innerFrame.add(panel2, BorderLayout.CENTER);
        
        // Create subpanel 2.1
        JPanel panel2_1 = new JPanel();
        panel2_1.setPreferredSize(new Dimension(200, 250));
        panel2_1.setBorder(packageBorder);
        panel2.add(panel2_1);
        
        // Add buttons to subpanel 2.1
        JButton showBtn = new JButton("Show All Packages");
        JButton addBtn = new JButton("Add New Package");
        JButton deleteBtn = new JButton("Delete a Package");
        JButton searchBtn = new JButton("Search for Package");
        JButton deliverBtn = new JButton("Deliver a Package");
        JButton showTransBtn = new JButton("Show Transaction List");
        showBtn.setPreferredSize(mainButton);
        addBtn.setPreferredSize(mainButton);
        deleteBtn.setPreferredSize(mainButton);
        searchBtn.setPreferredSize(mainButton);
        deliverBtn.setPreferredSize(mainButton);
        showTransBtn.setPreferredSize(mainButton);       
        panel2_1.add(showBtn);
        panel2_1.add(addBtn);
        panel2_1.add(deleteBtn);
        panel2_1.add(searchBtn);
        panel2_1.add(deliverBtn);
        panel2_1.add(showTransBtn);
        
        // Create sub panel 2.2
        JPanel panel2_2 = new JPanel();
        panel2_2.setPreferredSize(new Dimension(200, 250));
        panel2_2.setBorder(userBorder);
        panel2.add(panel2_2); 
        
        // Add buttons to subpanel 2.2
        JButton showUsersBtn = new JButton("Show All Users");
        JButton addUserBtn = new JButton("Add New User");
        JButton updateBtn = new JButton("Update User");
        showUsersBtn.setPreferredSize(mainButton);
        addUserBtn.setPreferredSize(mainButton);
        updateBtn.setPreferredSize(mainButton);
        panel2_2.add(showUsersBtn);
        panel2_2.add(addUserBtn);
        panel2_2.add(updateBtn);
        
        //*************************************************************
        // Button Commands
        //*************************************************************
        //*** Button Command:  [Show All Packages]
        showBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    logger.log(Level.INFO, "User viewed all packages.");
                    // Create new frame
                    JFrame frame = new JFrame("Show All Packages");
                    // Create a component
                    JTextArea textArea = new JTextArea();
                    textArea.setEditable(false);
                    // Create a container for component
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    textArea.setPreferredSize(tArea);
                    textArea.append(ss.getAllPackagesFormatted());
                    frame.add(scrollPane);
                    frame.pack();
                    frame.setVisible(true);
                }
        });
        
        //*** Button Command: [Add New Package]
        addBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    logger.log(Level.INFO, "User opened Add New Package window.");
                    
                    // Broadscope Variables
                    JLabel infoLabel1 = new JLabel("             Additional Info:");                    
                    JTextField infoField1 = new JTextField("<Select package type>");
                    JLabel infoLabel2 = new JLabel("             Additional Info:");                    
                    JTextField infoField2 = new JTextField("<Select package type>");  
                    JTextField trackingField = new JTextField("");
                    
                    //*** Create new main frame
                    JFrame pkgFrame = new JFrame("Add New Package");
                    pkgFrame.setLayout(new GridLayout(0, 1, 5, 10));
                  
                    // ********************************************* Panel Row 1:  Padding
                    JPanel padding = new JPanel();
                    pkgFrame.add(padding);
                        
                    // ********************************************* Panel Row 2:  Package Type
                    // Create panel
                    JPanel addPanel = new JPanel();
                    addPanel.setLayout(new BorderLayout());
                    pkgFrame.add(addPanel);
                    
                    // Create Label
                    JLabel newType = new JLabel("             Select package type:                       ");
                    addPanel.add(newType, BorderLayout.WEST);
                    
                    // Create CBox
                    String[] typeOptions = {"<package type>", "Envelope", "Box", "Crate", "Drum"};
                    JComboBox typeCombo = new JComboBox(typeOptions);
                    typeCombo.setPreferredSize(eastDim);
                    addPanel.add(typeCombo, BorderLayout.EAST);      
                    
                    // Event Listener for Type Combo Box
                    typeCombo.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            JComboBox<String> combo = (JComboBox<String>) event.getSource();
                            String s = (String) combo.getSelectedItem();

                            if (s.equals("Envelope")) {
                                infoLabel1.setText("             Enter height (in):");
                                infoLabel2.setText("             Enter width (in):");
                                infoField1.setText("");
                                infoField2.setText("");                                
                                infoLabel1.setForeground(Color.black);
                                infoLabel2.setForeground(Color.black);
                                infoField1.setForeground(Color.black);
                                infoField2.setForeground(Color.black);
                                infoField1.setEditable(true);
                                infoField2.setEditable(true);       
                            } else if (s.equals("Box")) {
                                infoLabel1.setText("             Enter largest dimension (in):");
                                infoLabel2.setText("             Enter volume (cubic in):");
                                infoField1.setText("");
                                infoField2.setText("");                                
                                infoLabel1.setForeground(Color.black);
                                infoLabel2.setForeground(Color.black);
                                infoField1.setForeground(Color.black);
                                infoField2.setForeground(Color.black);                                
                                infoField1.setEditable(true);
                                infoField2.setEditable(true);      
                            } else if (s.equals("Crate")){
                                infoLabel1.setText("             Enter weight (oz):");
                                infoLabel2.setText("             Enter contents:");
                                infoField1.setText("");
                                infoField2.setText("");                              
                                infoLabel1.setForeground(Color.black);
                                infoLabel2.setForeground(Color.black);
                                infoField1.setForeground(Color.black);
                                infoField2.setForeground(Color.black);                                
                                infoField1.setEditable(true);
                                infoField2.setEditable(true);                                      
                            } else if (s.equals("Drum")){
                                infoLabel1.setText("             Enter plastic/fiber:");
                                infoLabel2.setText("             Enter diameter (in):");
                                infoField1.setText("");
                                infoField2.setText("");                                
                                infoLabel1.setForeground(Color.black);
                                infoLabel2.setForeground(Color.black);
                                infoField1.setForeground(Color.black);
                                infoField2.setForeground(Color.black);                                
                                infoField1.setEditable(true);
                                infoField2.setEditable(true);                                      
                            } else if (s.equals("<package type>")){
                                infoLabel1.setText("             Additional Info:");      
                                infoLabel2.setText("             Additional Info:");    
                                infoLabel1.setForeground(Color.gray);
                                infoLabel2.setForeground(Color.gray);
                                infoField1.setForeground(Color.gray);
                                infoField2.setForeground(Color.gray);                                
                                infoField1.setText("<Select package type>");                
                                infoField2.setText("<Select package type>");            
                                infoField1.setEditable(false);
                                infoField2.setEditable(false);
                            }
                        }
                    });                    
                    
                    // ********************************************* Panel 3 Row:  Additional Info
                    // Create Panel
                    JPanel infoPanel1 = new JPanel();
                    infoPanel1.setLayout(new BorderLayout());
                    pkgFrame.add(infoPanel1);                
                    
                    // Modify infoLabel1
                    infoPanel1.add(infoLabel1, BorderLayout.WEST);
                    infoLabel1.setForeground(Color.GRAY);
                    
                    // Modify infoField1     
                    infoField1.setPreferredSize(eastDim);                    
                    infoField1.setForeground(Color.gray);
                    infoField1.setEditable(false);
                    infoPanel1.add(infoField1, BorderLayout.EAST);       
                    
                    // ********************************************* Panel 4 Row:  Additional Info
                    // Create Panel
                    JPanel infoPanel2 = new JPanel();
                    infoPanel2.setLayout(new BorderLayout());
                    pkgFrame.add(infoPanel2);                
                    
                    // Modify infoLabel2
                    infoLabel2.setForeground(Color.GRAY);                    
                    infoPanel2.add(infoLabel2, BorderLayout.WEST);   
                    
                    // Modify infoField2
                    infoField2.setPreferredSize(eastDim);                    
                    infoField2.setForeground(Color.gray);
                    infoField2.setEditable(false);
                    infoPanel2.add(infoField2, BorderLayout.EAST);       
                    
                    // ********************************************* Panel 5 Row:  Tracking Number
                    // Create Panel
                    JPanel trackingPanel = new JPanel();
                    trackingPanel.setLayout(new BorderLayout());
                    pkgFrame.add(trackingPanel);
                    
                    // Create Label
                    JLabel newTracking = new JLabel("             Enter tracking number:");
                    trackingPanel.add(newTracking, BorderLayout.WEST);
                    
                    // Create Textfield
                    trackingField.setPreferredSize(eastDim);
                    trackingPanel.add(trackingField, BorderLayout.EAST);

                    // ********************************************* Panel 6 Row: Specification      
                    // Create Panel
                    JPanel specPanel = new JPanel();
                    specPanel.setLayout(new BorderLayout());
                    pkgFrame.add(specPanel);
                    
                    // Create Label
                    JLabel specLabel = new JLabel("             Select specification:");
                    specPanel.add(specLabel, BorderLayout.WEST);          
                    
                    // Create CBox
                    String[] specOptions = {"<specification>", "Fragile", "Books", "Catalogs", "Do-not-bend", "N/A"};
                    JComboBox specCombo = new JComboBox(specOptions);
                    specCombo.setPreferredSize(eastDim);
                    specPanel.add(specCombo, BorderLayout.EAST);    

                    // ********************************************* Panel 7 Row:  Mailing Class
                    // Create Panel
                    JPanel classPanel = new JPanel();
                    classPanel.setLayout(new BorderLayout());
                    pkgFrame.add(classPanel);
                    // Create Label
                    JLabel newClass = new JLabel("             Select mailing class:");
                    classPanel.add(newClass, BorderLayout.WEST);
                    // Create CBOX
                    String[] classOptions = {"<mailing class>", "First-Class", "Priority", "Retail", "Ground", "Metro"};
                    JComboBox classCombo = new JComboBox(classOptions);
                    classCombo.setPreferredSize(eastDim);
                    classPanel.add(classCombo, BorderLayout.EAST);

                    
                     //*************************************************  Panel 8 Row: Save Button
                     // Create panel
                     JPanel savePanel = new JPanel();
                     savePanel.setLayout(new BorderLayout());
                     pkgFrame.add(savePanel);
                     
                     // Create save button
                     JButton saveButton = new JButton("Add");
                     saveButton.setPreferredSize(smallButton);
                     savePanel.add(saveButton, BorderLayout.EAST);
                     
                     // [Save Package] Action Listener
                    saveButton.addActionListener((ActionEvent ae1) -> {
                        logger.log(Level.INFO, "User attempted to save Add New Package form.");
                        
                        // Validation Variables
                        String pkgType = (String)typeCombo.getSelectedItem();
                        String trackNum = trackingField.getText();
                        String spec = (String)specCombo.getSelectedItem();
                        String mailClass = (String)classCombo.getSelectedItem();
                        String tempHeight = null;
                        Integer height = null;
                        String tempWidth = null;
                        Integer width = null;
                        String tempLrgDim = null;
                        Integer lrgDim = null;
                        String tempVolume = null;
                        Integer volume = null;
                        String tempWeight = null;
                        Float weight = null;
                        String contents = null;
                        String drumType = null;
                        String tempDiameter = null;
                        Float diameter = null;
                        
                        // Validate all combo boxes.
                        if (pkgType.equals("<package type>")){
                            logger.log(Level.INFO, "Save failed.  No <package type> selection.");
                            JOptionPane.showMessageDialog(null, "Please select a package type.",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);
                        }
                        else if (spec.equals("<specification>")){
                            logger.log(Level.INFO, "Save failed.  No <specification> selection.");
                            JOptionPane.showMessageDialog(null, "Please select a specification.",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);
                        }
                        else if (mailClass.equals("<mailing class>")){
                            logger.log(Level.INFO, "Save failed.  No <mailing class> selection.");
                            JOptionPane.showMessageDialog(null, "Please select a mailing class.",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);
                        }
                        // Validate tracking num
                        else if(trackNum.isEmpty()){ 
                            logger.log(Level.INFO, "Save failed.  No tracking number entry.");
                            JOptionPane.showMessageDialog(null, "Please enter a tracking number.",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);
                        }
                        else if (trackNum.length() > 5){
                            logger.log(Level.INFO, "Save failed.  Tracking number entry incorrect length.");
                            JOptionPane.showMessageDialog(null, "Tracking number must be 5 or fewer digits.",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);
                        }
                        else if (ss.packageExists(trackNum)){
                            logger.log(Level.INFO, "Save failed.  Attempted to save existing tracking number.");
                            JOptionPane.showMessageDialog(null, "Tracking number already exists.\nPlease try again.",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);
                        }
                        // Validate Envelope Variables
                        else if ("Envelope" == typeCombo.getSelectedItem()){
                            logger.log(Level.INFO, "User selected Envelope type");
                            
                            // Validate height and width
                            tempHeight = infoField1.getText();
                            tempWidth = infoField2.getText();
                            
                            if (tempHeight.isEmpty()){
                                logger.log(Level.INFO, "Save failed.  No height entry.");
                                JOptionPane.showMessageDialog(null, "Please enter envelope height.",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);                                  
                            }
                            else if (!isInteger(tempHeight)){
                                logger.log(Level.INFO, "Save failed.  Height entry not an integer.");
                                JOptionPane.showMessageDialog(null, "Height entry is not an integer."
                                        + "Ex)  14",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);  
                            }
                            else if(Integer.parseInt(tempHeight) < 0){
                                logger.log(Level.INFO, "Save failed.  Negative height entry.");
                                JOptionPane.showMessageDialog(null, "Envelope width cannot be a negative number of inches.",
                                        "Error Message", JOptionPane.ERROR_MESSAGE);                                  
                            } 
                            else if (tempWidth.isEmpty()){
                                logger.log(Level.INFO, "Save failed.  No width entry.");
                                JOptionPane.showMessageDialog(null, "Please enter envelope width.",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);                                  
                            }
                            else if (!isInteger(tempWidth)){
                                logger.log(Level.INFO, "Save failed.  Width entry not an integer.");
                                JOptionPane.showMessageDialog(null, "Width entry is not an integer."
                                        + "Ex)  14",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);  
                            }
                            else if(Integer.parseInt(tempWidth) < 0){
                                logger.log(Level.INFO, "Save failed.  Negative width entry.");
                                JOptionPane.showMessageDialog(null, "Envelope width cannot be a negative number of inches.",
                                        "Error Message", JOptionPane.ERROR_MESSAGE);                                  
                            }        
                            else{
                                // Convert
                                height = Integer.parseInt(tempHeight);
                                width = Integer.parseInt(tempWidth);
                                // Add new package
                                ss.addEnvelope(trackNum, spec, mailClass, height, width);
                                // Inform success
                                JOptionPane.showMessageDialog(null, "Your package was successfully added.",
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
                                logger.log(Level.INFO, "Add New Package save succeeded.");
                                // Clear add package frame
                                pkgFrame.dispose();
                            }
                            
                        }
                        // Validate Box Variables
                        else if ("Crate" == typeCombo.getSelectedItem()){
                            
                            // Validate weight & contents
                            tempWeight = infoField1.getText();
                            contents = infoField2.getText();
                            
                            if (tempWeight.isEmpty()){
                                logger.log(Level.INFO, "Save failed.  No weight entry.");
                                JOptionPane.showMessageDialog(null, "Please enter crate weight.",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);                                          
                            }
                            else if (!isFloat(tempWeight)){
                                logger.log(Level.INFO, "Save failed.  Crate eight entry not a float.");
                                JOptionPane.showMessageDialog(null, "Please enter a real number."
                                        + "\nEx)  12.45",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);                                   
                            }
                            else if (Float.parseFloat(tempWeight) < 0){
                                logger.log(Level.INFO, "Save failed.  Negative weight entry.");
                                JOptionPane.showMessageDialog(null, "Crate weight cannot be negative."
                                        + "\nEx)  12.45",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);                                
                            }
                            else if (contents.isEmpty()){
                                logger.log(Level.INFO, "Save failed.  No contents entry.");                                
                                JOptionPane.showMessageDialog(null, "Please enter crate contents."
                                        + "\nEx)  battleaxes",
                                        "Error Message", JOptionPane.ERROR_MESSAGE);         
                            }
                            else{
                                // Convert
                                weight = Float.parseFloat(tempWeight);
                                ss.addCrate(trackNum, spec, mailClass, weight, contents);
                                // Inform success
                                JOptionPane.showMessageDialog(null, "Your package was successfully added.",
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
                                logger.log(Level.INFO, "Add New Package save succeeded.."); 
                                // Clear add package frame
                                pkgFrame.dispose();
                            }
                        }
                        // Validate box variables
                        else if ("Box" == typeCombo.getSelectedItem()){
                            logger.log(Level.INFO, "User selected Box type.");
                            // Validate largest dimension and volume.
                            tempLrgDim = infoField1.getText();
                            tempVolume = infoField2.getText();
                            
                            if (tempLrgDim.isEmpty()){
                                logger.log(Level.INFO, "Save failed.  No largest dimension entry.");
                                JOptionPane.showMessageDialog(null, "Please enter box largest dimension."
                                        + "\nEx)  16",
                                        "Error Message", JOptionPane.ERROR_MESSAGE);      
                            }
                            else if (!isInteger(tempLrgDim)){
                                logger.log(Level.INFO, "Save failed.  Largest dimension entry not integer.");
                                JOptionPane.showMessageDialog(null, "Largest dimension must be an integer."
                                        + "\nEx)  16",
                                        "Error Message", JOptionPane.ERROR_MESSAGE);                                     
                            }
                            else if(Integer.parseInt(tempLrgDim) < 0){
                                logger.log(Level.INFO, "Save failed.  Largest dimension entry negative.");
                                JOptionPane.showMessageDialog(null, "Largest dimension cannot be negative."
                                        + "\nEx)  16",
                                        "Error Message", JOptionPane.ERROR_MESSAGE);                                     
                            }
                            else if (tempVolume.isEmpty()){
                                logger.log(Level.INFO, "Save failed.  No volume entry.");
                                JOptionPane.showMessageDialog(null, "Please enter volume."
                                        + "\nEx)  16",
                                        "Error Message", JOptionPane.ERROR_MESSAGE);      
                            }
                            else if (!isInteger(tempVolume)){
                                logger.log(Level.INFO, "Save failed.  Volume entry not integer.");
                                JOptionPane.showMessageDialog(null, "Volume must be an integer."
                                        + "\nEx)  16",
                                        "Error Message", JOptionPane.ERROR_MESSAGE);                                     
                            }
                            else if(Integer.parseInt(tempVolume) < 0){
                                logger.log(Level.INFO, "Save failed.  Volume entry negative.");
                                JOptionPane.showMessageDialog(null, "Volume cannot be negative."
                                        + "\nEx)  16",
                                        "Error Message", JOptionPane.ERROR_MESSAGE);                                     
                            }
                            else{
                                // Convert
                                lrgDim = Integer.parseInt(tempLrgDim);
                                volume = Integer.parseInt(tempVolume);
                                ss.addBox(trackNum, spec, mailClass, lrgDim, volume);
                                // Inform success
                                JOptionPane.showMessageDialog(null, "Your package was successfully added.",
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
                                // Clear add package frame
                                pkgFrame.dispose();
                            }
                        }
                        // Validate drum variables
                        else if ("Drum" == typeCombo.getSelectedItem()){
                            logger.log(Level.INFO, "User selected Drum type.");
                            
                            // Validate material and diamater
                            drumType = (String)infoField1.getText();
                            tempDiameter = infoField2.getText();
                            
                            if (drumType.isEmpty()){
                                logger.log(Level.INFO, "Save failed.  No drumtype entry.");
                                JOptionPane.showMessageDialog(null, "Please enter drum type: plastic or fiber.",
                                        "Error Message", JOptionPane.ERROR_MESSAGE);                                
                            }
                            else if (!drumType.equalsIgnoreCase("plastic") && !drumType.equalsIgnoreCase("fiber")){
                                logger.log(Level.INFO, "Save failed.  Improper entry.");
                                JOptionPane.showMessageDialog(null, "Please enter drum type: plastic or fiber.",
                                        "Error Message", JOptionPane.ERROR_MESSAGE);                                  
                            }
                            else if (!isFloat(tempDiameter)){
                                logger.log(Level.INFO, "Save failed.  Drumtype entry not a float.");
                                JOptionPane.showMessageDialog(null, "In drum type, please enter a real, nonnegative number."
                                        + "\nEx)  12.45",
                                        "Error Message", JOptionPane.ERROR_MESSAGE);                                  
                            }
                            else if (Float.parseFloat(tempDiameter) < 0){
                                logger.log(Level.INFO, "Save failed.  Drumtype entry negative.");
                                JOptionPane.showMessageDialog(null, "In drum type, please enter a real, nonnegative number."
                                        + "\nEx)  12.45",
                                        "Error Message", JOptionPane.ERROR_MESSAGE);                                     
                            }
                            else {
                                // Convert
                                diameter = Float.parseFloat(tempDiameter);
                                ss.addDrum(trackNum, spec, mailClass, drumType, diameter);
                                // Inform success
                                JOptionPane.showMessageDialog(null, "Your package was successfully added.",
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
                                logger.log(Level.INFO, "Add New Package save succeeded.");
                                // Clear add package frame
                                pkgFrame.dispose();
                            }
                        }
                    });
                     
                    //*************************************************  Panel LAST: Padding
                    // Create panel
                    JPanel paddingPanel = new JPanel();
                    pkgFrame.add(paddingPanel);
                     
     
                    pkgFrame.pack();
                    pkgFrame.setVisible(true);
                }
        });
        
        //*** Button Command:  [Delete a Package]
        deleteBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                                
                // Main Frame
                JFrame delFrame = new JFrame("Delete");
                delFrame.setLayout(new GridLayout(0, 1, 5, 10));
                
                // *************************************** Panel 1 Row: padding
                JPanel padding = new JPanel();
                delFrame.add(padding);
                
                // *************************************** Panel 2 Row: info
                JPanel delPanel = new JPanel();
                delPanel.setLayout(new BorderLayout());
                delFrame.add(delPanel);
                
                // Label
                JLabel delLabel = new JLabel("      Enter the tracking number of the package to delete:    ");
                delPanel.add(delLabel, BorderLayout.WEST);
                
                // Textfield
                JTextField delField = new JTextField("<tracking number>");
                delField.setPreferredSize(eastDim);
                delPanel.add(delField, BorderLayout.EAST);      
                
                // *************************************** Panel 3 Row: [delete]
                
                // Create panel
                JPanel btnPanel = new JPanel();
                btnPanel.setLayout(new BorderLayout());
                delFrame.add(btnPanel);
                
                 // Create save button
                 JButton delButton = new JButton("Delete Package");
                 delButton.setPreferredSize(new Dimension(130, 30));
                 btnPanel.add(delButton, BorderLayout.EAST);
                 
                 delButton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae){
                        
                        String trackNum = (String)delField.getText();
                        
                        if (trackNum.equals("<tracking number>")){
                            JOptionPane.showMessageDialog(null, "Please enter a tracking number.", 
                                "Error Message", JOptionPane.ERROR_MESSAGE);   
                        }
                        else if (ss.deletePackage(trackNum)){
                            JOptionPane.showMessageDialog(null, "Package " + trackNum + " was successfully deleted.", 
                                "Delete Successful", JOptionPane.INFORMATION_MESSAGE);  
                            delFrame.dispose();
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Package " + trackNum + " does not exist.", 
                                "Error Message", JOptionPane.ERROR_MESSAGE);  
                        }
                    } 
                     
                 });
                
                // *************************************** Panel 4 Row: padding
                
                JPanel padding2 = new JPanel();
                delFrame.add(padding2);
                delFrame.pack();
                delFrame.setVisible(true);     
            }
        });
        
        //*** Button Command:  [Search for Package]
        searchBtn.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent ae){
            
                // Main Frame
                JFrame srhFrame = new JFrame("Search for a Package");
                srhFrame.setLayout(new GridLayout(0, 1, 5, 10));
                
                // *************************************** Panel 1 Row: padding
                JPanel padding = new JPanel();
                srhFrame.add(padding);
                
                // *************************************** Panel 2 Row: info
                JPanel srhPanel = new JPanel();
                srhPanel.setLayout(new BorderLayout());
                srhFrame.add(srhPanel);
                
                // Label
                JLabel srhLabel = new JLabel("      Enter the tracking number of the package to locate:    ");
                srhPanel.add(srhLabel, BorderLayout.WEST);
                
                // Textfield
                JTextField srhField = new JTextField("<tracking number>");
                srhField.setPreferredSize(eastDim);
                srhPanel.add(srhField, BorderLayout.EAST);      
                
                // *************************************** Panel 3 Row: [delete]
                
                // Create panel
                JPanel btnPanel = new JPanel();
                btnPanel.setLayout(new BorderLayout());
                srhFrame.add(btnPanel);
                
                 // Create save button
                 JButton srhButton = new JButton("Search");
                 srhButton.setPreferredSize(new Dimension(130, 30));
                 btnPanel.add(srhButton, BorderLayout.EAST);
                 
                 srhButton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae){
                        
                        String trackNum = (String)srhField.getText();
                        
                        if (trackNum.equals("<tracking number>")){
                            JOptionPane.showMessageDialog(null, "Please enter a tracking number.", 
                                "Error Message", JOptionPane.ERROR_MESSAGE);   
                        }
                        else if (ss.packageExists(trackNum)){
                            JOptionPane.showMessageDialog(null, ss.getPackageFormatted(trackNum), 
                                "Search Successful", JOptionPane.INFORMATION_MESSAGE);  
                            srhFrame.dispose();
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Package " + trackNum + " does not exist.", 
                                "Error Message", JOptionPane.ERROR_MESSAGE);  
                        }
                    } 
                 });          
                
                // *************************************** Panel 4 Row: padding
                
                JPanel padding2 = new JPanel();
                srhFrame.add(padding2);
                srhFrame.pack();
                srhFrame.setVisible(true);     
           } 
        });
        
        //*** Button Command:  [Deliver Package]
        deliverBtn.addActionListener(new ActionListener(){
            @Override
           public void actionPerformed(ActionEvent ae){
               
                // Broadscope Variables


                //*** Create new main frame
                JFrame dlrFrame = new JFrame("Add New Package");
                dlrFrame.setLayout(new GridLayout(0, 1, 5, 10));

                // ********************************************* Panel Row 1:  Padding
                JPanel padding = new JPanel();
                dlrFrame.add(padding);

                // ********************************************* Panel Row 2:  Get Customer ID
                // Create panel
                JPanel cidPanel = new JPanel();
                cidPanel.setLayout(new BorderLayout());
                dlrFrame.add(cidPanel);

                // Create Label
                JLabel cidLabel = new JLabel("             Enter customer ID:                       ");
                cidPanel.add(cidLabel, BorderLayout.WEST);

                // Create TextField
                JTextField cidField = new JTextField("");
                cidField.setPreferredSize(eastDim);
                cidPanel.add(cidField, BorderLayout.EAST);
                
                // ********************************************* Panel Row 3:  Get Employee ID
                // Create panel
                JPanel eidPanel = new JPanel();
                eidPanel.setLayout(new BorderLayout());
                dlrFrame.add(eidPanel);

                // Create Label
                JLabel eidLabel = new JLabel("             Enter employee ID:");
                eidPanel.add(eidLabel, BorderLayout.WEST);

                // Create TextField
                JTextField eidField = new JTextField("");
                eidField.setPreferredSize(eastDim);
                eidPanel.add(eidField, BorderLayout.EAST);                
               
                // ********************************************* Panel Row 4:  Get Tracking Number
                // Create panel
                JPanel trkPanel = new JPanel();
                trkPanel.setLayout(new BorderLayout());
                dlrFrame.add(trkPanel);

                // Create Label
                JLabel trkLabel = new JLabel("             Enter tracking number:");
                trkPanel.add(trkLabel, BorderLayout.WEST);

                // Create TextField
                JTextField trkField = new JTextField("");
                trkField.setPreferredSize(eastDim);
                trkPanel.add(trkField, BorderLayout.EAST);     
                
                // ********************************************* Panel Row 5:  Get Price
                // Create panel
                JPanel prcPanel = new JPanel();
                prcPanel.setLayout(new BorderLayout());
                dlrFrame.add(prcPanel);

                // Create Label
                JLabel prcLabel = new JLabel("             Enter price:");
                prcPanel.add(prcLabel, BorderLayout.WEST);

                // Create TextField
                JTextField prcField = new JTextField("");
                prcField.setPreferredSize(eastDim);
                prcPanel.add(prcField, BorderLayout.EAST);  
                
                // ********************************************* Panel Row 6: [Deliver] Button
                // Create panel
                JPanel btnPanel = new JPanel();
                btnPanel.setLayout(new BorderLayout());
                dlrFrame.add(btnPanel);
                
                // Create [Deliver] Button
                JButton dlrButton = new JButton("Deliver");
                btnPanel.add(dlrButton, BorderLayout.EAST);
                
                // Create [Deliver] Action Listener
                dlrButton.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent ae){
                       // Prep Work
                       String tempCustID = cidField.getText();
                       Integer custID = null;
                       String tempEmpID = eidField.getText();
                       Integer empID = null;
                       String trackNum = trkField.getText();
                       String tempPrice = prcField.getText();
                       Float price = null;
                       Date currentDate = new Date(System.currentTimeMillis());
                       
                       // Validate Customer ID
                       
                       if (tempCustID.isEmpty()){
                            JOptionPane.showMessageDialog(null, "Please enter a valid customer ID."
                                + "\nEx)  312", "Error Message", JOptionPane.ERROR_MESSAGE);                                  
                       }
                       else if (!isInteger(tempCustID)){
                            JOptionPane.showMessageDialog(null, "Customer ID cannot contain letters."
                                + "\nEx)  312", "Error Message", JOptionPane.ERROR_MESSAGE);   
                       }
                       else if(!ss.userExists( Integer.parseInt(tempCustID) )){
                            JOptionPane.showMessageDialog(null, "User ID " + tempCustID + " does not exist."
                                + "\nPlease add user and try again", "Error Message", JOptionPane.ERROR_MESSAGE);                                
                       }
                       else if (tempEmpID.isEmpty()){
                            JOptionPane.showMessageDialog(null, "Please enter a valid employee ID."
                                + "\nEx)  312", "Error Message", JOptionPane.ERROR_MESSAGE);                                  
                       }
                       else if (!isInteger(tempEmpID)){
                            JOptionPane.showMessageDialog(null, "Employee ID cannot contain letters."
                                + "\nEx)  312", "Error Message", JOptionPane.ERROR_MESSAGE);   
                       }
                       else if(!ss.userExists( Integer.parseInt(tempEmpID) )){
                            JOptionPane.showMessageDialog(null, "User ID " + tempEmpID + " does not exist."
                                + "\nPlease add user and try again", "Error Message", JOptionPane.ERROR_MESSAGE);                                
                       }                       
                       else if(trackNum.isEmpty()){
                            JOptionPane.showMessageDialog(null, "Please enter a valid tracking number."
                                + "\nEx)  H7V32", "Error Message", JOptionPane.ERROR_MESSAGE);                             
                       }
                       else if(trackNum.length() > 5){
                            JOptionPane.showMessageDialog(null, "Tracking number cannot be longer than 5 characters."
                                + "\nEx)  H7V32", "Error Message", JOptionPane.ERROR_MESSAGE);                                  
                       }
                       else if (!ss.packageExists(trackNum)){
                            JOptionPane.showMessageDialog(null, "Tracking number " + trackNum + " does not exist."
                                + "\nPlease add package and try again", "Error Message", JOptionPane.ERROR_MESSAGE);                            
                       }
                       else if(tempPrice.isEmpty()){
                            JOptionPane.showMessageDialog(null, "Please enter a valid price."
                                    + "\nEx)  14.75", "Error Message", JOptionPane.ERROR_MESSAGE);                               
                       }
                       else if(!isFloat(tempPrice)){
                            JOptionPane.showMessageDialog(null, "Please enter a valid price."
                                + "\nEx)  14.75", "Error Message", JOptionPane.ERROR_MESSAGE);                                 
                       }
                       else if( Float.parseFloat(tempPrice) < 0 ){
                            JOptionPane.showMessageDialog(null, "Price cannot be negative."
                                + "\nEx)  14.75", "Error Message", JOptionPane.ERROR_MESSAGE);                                 
                       }
                       else{
                           custID = Integer.parseInt(tempCustID);
                           empID = Integer.parseInt(tempEmpID);
                           price = Float.parseFloat(tempPrice);
                           ss.addShppingTransaction(custID, empID, trackNum, currentDate, currentDate, price);
                           ss.deletePackage(trackNum);
                           JOptionPane.showMessageDialog(null, "Package " + trackNum + " was successfully delivered.", 
                                "Success", JOptionPane.INFORMATION_MESSAGE);              
                           dlrFrame.dispose();
                       }
                   } 
                });

                // ********************************************* Panel Row 7:  Padding
                JPanel padding2 = new JPanel();
                dlrFrame.add(padding2);                
                
                
                dlrFrame.pack();
                dlrFrame.setVisible(true);
                
                
           } 
        });
        
        //*** Button Command:  [show Transaction]
        showTransBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                    // Create new frame
                    JFrame frame = new JFrame("Show All Packages");
                    // Create a component
                    JTextArea textArea = new JTextArea(20, 60);
                    textArea.setEditable(false);
                    // Create a container for component
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    textArea.append(ss.getAllTransactionsText());
                    frame.add(scrollPane);
                    frame.pack();
                    frame.setVisible(true);                
            }
        });
        
        //*** Button Command:  [Show Users]
        showUsersBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    // Create new frame
                    JFrame frame = new JFrame("Show All Users");
                    // Create a component
                    JTextArea textArea = new JTextArea(20, 60);
                    textArea.setEditable(false);
                    // Create a container for component
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    textArea.append(ss.getAllUsersFormatted());
                    frame.add(scrollPane);
                    frame.pack();
                    frame.setVisible(true);
                }
        });        
        
        //*** Button Command:  [Add User]
        addUserBtn.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent ae){
               
                //Broadscope variables
                JLabel infoLabel1 = new JLabel("              Additional Info");
                JLabel infoLabel2 = new JLabel("              Additional Info");
                JLabel infoLabel3 = new JLabel("              Additional Info");
                JTextField infoField1 = new JTextField("<additional info>");                
                JTextField infoField2 = new JTextField("<additional info>");                
                JTextField infoField3 = new JTextField("<additional info>");                
                
                // *** Create new main frame
                JFrame aduFrame = new JFrame("Add New User");
                aduFrame.setLayout(new GridLayout(0, 1, 5, 10));
                
                // ********************************************* Panel Row 1:  Padding
                JPanel padding = new JPanel();
                aduFrame.add(padding);
             
                // ********************************************* Panel Row 2:  User Type
                // Create panel
                JPanel aduPanel = new JPanel();
                aduPanel.setLayout(new BorderLayout());
                aduFrame.add(aduPanel);
                
                // Create Label
                JLabel userType = new JLabel("              Select user type:              ");
                aduPanel.add(userType, BorderLayout.WEST);
                
                // Create combo box
                String[] userOptions = {"<user type>", "Customer", "Employee"};
                JComboBox userCombo = new JComboBox(userOptions);
                userCombo.setPreferredSize(eastDim);
                aduPanel.add(userCombo, BorderLayout.EAST);
                
                // Event Listener for Type Combo Box
                userCombo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        JComboBox<String> combo = (JComboBox<String>) event.getSource();
                        String s = (String) combo.getSelectedItem();

                        if (s.equals("Customer")) {
                            infoLabel1.setText("             Enter phone number:");
                            infoLabel2.setText("             Enter address:");
                            infoField1.setText("<phone number>");
                            infoField2.setText("<address>");                                
                            infoLabel1.setForeground(Color.black);
                            infoLabel2.setForeground(Color.black);
                            infoField1.setForeground(Color.black);
                            infoField2.setForeground(Color.black);
                            infoField1.setEditable(true);
                            infoField2.setEditable(true);       
                        } else if (s.equals("Employee")) {
                            infoLabel1.setText("             Enter monthly salary:");
                            infoLabel2.setText("             Enter social security number:");
                            infoLabel3.setText("             Enter bank account number:");
                            infoField1.setText("0");
                            infoField2.setText("0");                                
                            infoField3.setText("0");
                            infoLabel1.setForeground(Color.black);
                            infoLabel2.setForeground(Color.black);
                            infoLabel3.setForeground(Color.black);
                            infoField1.setForeground(Color.black);
                            infoField2.setForeground(Color.black);                                
                            infoField3.setForeground(Color.black);  
                            infoField1.setEditable(true);
                            infoField2.setEditable(true);      
                            infoField3.setEditable(true);
                        }
                    }
                });
                
                // ********************************************* Panel Row 3:  First Name
                // Create panel
                JPanel firstPanel = new JPanel();
                firstPanel.setLayout(new BorderLayout());
                aduFrame.add(firstPanel);
                
                // Create label
                JLabel firstLabel = new JLabel("              Enter first name:");
                firstLabel.setPreferredSize(eastDim);
                firstPanel.add(firstLabel, BorderLayout.WEST);
                
                // Create textfield
                JTextField firstField = new JTextField("<first name>");
                firstField.setPreferredSize(eastDim);
                firstPanel.add(firstField, BorderLayout.EAST);

                // ********************************************* Panel Row 4:  Last Name
                // Create panel
                JPanel lastPanel = new JPanel();
                lastPanel.setLayout(new BorderLayout());
                aduFrame.add(lastPanel);
                
                // Create label
                JLabel lastLabel = new JLabel("              Enter last name:");
                lastLabel.setPreferredSize(eastDim);
                lastPanel.add(lastLabel, BorderLayout.WEST);
                
                // Create textfield
                JTextField lastField = new JTextField("<last name>");
                lastField.setPreferredSize(eastDim);
                lastPanel.add(lastField, BorderLayout.EAST);                
                
               // ********************************************* Panel 5 Row:  Additional Info
                // Create Panel
                JPanel infoPanel1 = new JPanel();
                infoPanel1.setLayout(new BorderLayout());
                aduFrame.add(infoPanel1);                

                // Modify infoLabel1
                infoPanel1.add(infoLabel1, BorderLayout.WEST);
                infoLabel1.setForeground(Color.GRAY);

                // Modify infoField1     
                infoField1.setPreferredSize(eastDim);                    
                infoField1.setForeground(Color.gray);
                infoField1.setEditable(false);
                infoPanel1.add(infoField1, BorderLayout.EAST);       

                // ********************************************* Panel 6 Row:  Additional Info
                // Create Panel
                JPanel infoPanel2 = new JPanel();
                infoPanel2.setLayout(new BorderLayout());
                aduFrame.add(infoPanel2);                

                // Modify infoLabel2
                infoLabel2.setForeground(Color.GRAY);                    
                infoPanel2.add(infoLabel2, BorderLayout.WEST);   

                // Modify infoField2
                infoField2.setPreferredSize(eastDim);                    
                infoField2.setForeground(Color.gray);
                infoField2.setEditable(false);
                infoPanel2.add(infoField2, BorderLayout.EAST);  
                
                // ********************************************* Panel 8 Row:  Additional Info
                // Create Panel
                JPanel infoPanel3 = new JPanel();
                infoPanel3.setLayout(new BorderLayout());
                aduFrame.add(infoPanel3);                

                // Modify infoLabel2
                infoLabel3.setForeground(Color.GRAY);                    
                infoPanel3.add(infoLabel3, BorderLayout.WEST);   

                // Modify infoField2
                infoField3.setPreferredSize(eastDim);                    
                infoField3.setForeground(Color.gray);
                infoField3.setEditable(false);
                infoPanel3.add(infoField3, BorderLayout.EAST);   
                
                //*************************************************  Panel 8 Row: Save Button             
                // Create panel
                JPanel savePanel = new JPanel();
                savePanel.setLayout(new BorderLayout());
                aduFrame.add(savePanel);

                // Create save button
                JButton saveButton = new JButton("Add");
                saveButton.setPreferredSize(new Dimension(130, 30));
                savePanel.add(saveButton, BorderLayout.EAST);

                // [Save User] Action Listener
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae){ 
                        // Variables
                        String userType = (String)userCombo.getSelectedItem();
                        String first = firstField.getText();
                        String last = lastField.getText();
                        String phone = null;
                        String address = null;
                        Float monSalary = null;
                        Integer socialNum = null;
                        Integer bankNum = null;
                        
                        if (userType.equals("<user type>")){
                            JOptionPane.showMessageDialog(null, "Please select a user type.", 
                                "Error Message", JOptionPane.ERROR_MESSAGE);  
                        }
                        else if (first.equals("<first name>")){
                            JOptionPane.showMessageDialog(null, "Please enter first name.", 
                                "Error Message", JOptionPane.ERROR_MESSAGE);    
                        }
                        else if (last.equals("<last name>")){
                            JOptionPane.showMessageDialog(null, "Please enter last name.", 
                                "Error Message", JOptionPane.ERROR_MESSAGE);    
                        }                        
                        else if (userType.equals("Customer")){
                            
                            // Grab variables
                            phone = infoField1.getText();
                            address = infoField2.getText();
                            
                            if (phone.equals("<phone number")){
                            JOptionPane.showMessageDialog(null, "Please enter phone number.", 
                                "Error Message", JOptionPane.ERROR_MESSAGE);  
                            }
                            else if (address.equals("<address>")){
                            JOptionPane.showMessageDialog(null, "Please enter address.", 
                                "Error Message", JOptionPane.ERROR_MESSAGE);                                  
                            }
                            else {
                                // Add customer
                                ss.addCustomer(first, last, phone, address);
                                // Inform success
                                JOptionPane.showMessageDialog(null, "Your customer was successfully added.", 
                                    "Success", JOptionPane.INFORMATION_MESSAGE);           
                                // Clear add package frame
                                aduFrame.dispose();  
                            }
                        }
                        else if (userType.equals("Employee")){
                            
                            // Grab variables
                            monSalary = Float.parseFloat(infoField1.getText());
                            socialNum = Integer.parseInt(infoField2.getText());
                            bankNum = Integer.parseInt(infoField3.getText());    
                            
                            // Validate monthly salary
                            if (monSalary <= 0){
                            JOptionPane.showMessageDialog(null, "Monthly salary must be a positive number."
                                    + "\nEx)  3845.00", "Error Message", JOptionPane.ERROR_MESSAGE);                                       
                            }
                            // Validate social security number
                            else if (String.valueOf(socialNum).length() != 9){
                                JOptionPane.showMessageDialog(null, "Social security number is not 9 digits long."
                                        + "\nEx)  123456789", "Error Message", JOptionPane.ERROR_MESSAGE);                                   
                            }
                            else if (socialNum < 10000000 || socialNum > 999999999){
                                JOptionPane.showMessageDialog(null, "Give a correct 9 digit integer.",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);   
                            }                       
                            // Validate bank number
                            else if (bankNum <= 0){
                                JOptionPane.showMessageDialog(null, "Bank routing number must be a positive integer.",
                                    "Error Message", JOptionPane.ERROR_MESSAGE);        
                            }
                            // Add employee
                            else{
                                ss.addEmployee(first, last, socialNum, monSalary, bankNum);
                                // Inform success
                                JOptionPane.showMessageDialog(null, "Your customer was successfully added.", 
                                    "Success", JOptionPane.INFORMATION_MESSAGE);           
                                // Clear add package frame
                                aduFrame.dispose();                                  
                            }
                        }
                    }
                });
                
                //*************************************************  Panel LAST: Padding
                // Create panel
                JPanel paddingPanel = new JPanel();
                aduFrame.add(paddingPanel);

                aduFrame.pack();
                aduFrame.setVisible(true);   
                
           } 
        });

        //*** Button Command:  [Update User]
        updateBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                
                // Main Frame
                JFrame updFrame = new JFrame("Update User");
                updFrame.setLayout(new GridLayout(0, 1, 5, 10));
                
                // *************************************** Panel 1 Row: padding
                JPanel padding = new JPanel();
                updFrame.add(padding);
                
                // *************************************** Panel 2 Row: info
                JPanel updPanel = new JPanel();
                updPanel.setLayout(new BorderLayout());
                updFrame.add(updPanel);
                
                // Label
                JLabel updLabel = new JLabel("      Enter the userID:    ");
                updPanel.add(updLabel, BorderLayout.WEST);
                
                // Textfield
                JTextField updField = new JTextField("0");
                updField.setPreferredSize(eastDim);
                updPanel.add(updField, BorderLayout.EAST);     

                // *************************************** Panel 3 Row: [search]
                
                // Create panel
                JPanel srhPanel = new JPanel();
                srhPanel.setLayout(new BorderLayout());
                updFrame.add(srhPanel);
                
                 // Create save button
                 JButton srhButton = new JButton("Search");
                 srhButton.setPreferredSize(new Dimension(130, 30));
                 srhPanel.add(srhButton, BorderLayout.EAST);
                 
                 srhButton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae){  
                        
                        // Variables
                        Integer userID = Integer.parseInt(updField.getText());
                        
                        if (userID == 0){
                            JOptionPane.showMessageDialog(null, "Please enter a user ID."
                                    + "\nEx)  214", "Error Message", JOptionPane.ERROR_MESSAGE);    
                        }
                        else if (!ss.userExists(userID)){
                            JOptionPane.showMessageDialog(null, "User Id does not exist.  Please try again.", 
                                "Error Message", JOptionPane.ERROR_MESSAGE);                               
                        }
                        else if (ss.userExists(userID)){
                            
                            // Pop up copy of add new user frame, but with data filled in. **********************************
                            
                            //Prep work
                            updFrame.dispose();
                            String userType = null;
                            JLabel infoLabel1 = new JLabel("              Additional Info");
                            JLabel infoLabel2 = new JLabel("              Additional Info");
                            JLabel infoLabel3 = new JLabel("              Additional Info");
                            JTextField infoField1 = new JTextField("<additional info>");                
                            JTextField infoField2 = new JTextField("<additional info>");                
                            JTextField infoField3 = new JTextField("<additional info>");                                  
                            
                            if(ss.isCustomer(userID)){
                                userType = "Customer";
                            }
                            else{
                                userType = "Employee";
                            }
                            
                            // *** Create new main frame
                            JFrame aduFrame = new JFrame("Update User");
                            aduFrame.setLayout(new GridLayout(0, 1, 5, 10));

                            // ********************************************* Panel Row 1:  Padding
                            JPanel padding = new JPanel();
                            aduFrame.add(padding);

                            // ********************************************* Panel Row 2:  User Type
                            // Create panel
                            JPanel aduPanel = new JPanel();
                            aduPanel.setLayout(new BorderLayout());
                            aduFrame.add(aduPanel);

                            // Create Label
                            JLabel userLabel = new JLabel("              User type:              ");
                            aduPanel.add(userLabel, BorderLayout.WEST);

                            // Create combo box
                           JLabel userLabel2 = new JLabel(userType);
                            userLabel2.setPreferredSize(eastDim);
                            aduPanel.add(userLabel2, BorderLayout.EAST);
                            

                            // ********************************************* Panel Row 2:  User ID
                            // Create panel
                            JPanel uidPanel = new JPanel();
                            uidPanel.setLayout(new BorderLayout());
                            aduFrame.add(uidPanel);

                            // Create Label
                            JLabel uidLabel = new JLabel("              User ID:              ");
                            uidPanel.add(uidLabel, BorderLayout.WEST);

                            // Create combo box
                            JLabel uidLabel2 = new JLabel( "" + userID);
                            uidLabel2.setPreferredSize(eastDim);
                            uidPanel.add(uidLabel2, BorderLayout.EAST);                            

                            // ********************************************* Panel Row 3:  First Name
                            // Create panel
                            JPanel firstPanel = new JPanel();
                            firstPanel.setLayout(new BorderLayout());
                            aduFrame.add(firstPanel);

                            // Create label
                            JLabel firstLabel = new JLabel("              Enter first name:");
                            firstLabel.setPreferredSize(eastDim);
                            firstPanel.add(firstLabel, BorderLayout.WEST);

                            // Create textfield
                            JTextField firstField = new JTextField("<first name>");
                            firstField.setPreferredSize(eastDim);
                            firstPanel.add(firstField, BorderLayout.EAST);

                            // ********************************************* Panel Row 4:  Last Name
                            // Create panel
                            JPanel lastPanel = new JPanel();
                            lastPanel.setLayout(new BorderLayout());
                            aduFrame.add(lastPanel);

                            // Create label
                            JLabel lastLabel = new JLabel("              Enter last name:");
                            lastLabel.setPreferredSize(eastDim);
                            lastPanel.add(lastLabel, BorderLayout.WEST);

                            // Create textfield
                            JTextField lastField = new JTextField("<last name>");
                            lastField.setPreferredSize(eastDim);
                            lastPanel.add(lastField, BorderLayout.EAST);               
                            
                            // ********************************************* Panel 6 Row:  Additional Info
                            // Create Panel
                            JPanel infoPanel1 = new JPanel();
                            infoPanel1.setLayout(new BorderLayout());
                            aduFrame.add(infoPanel1);                

                            // Modify infoLabel1
                            infoLabel1.setForeground(Color.GRAY);                    
                            infoPanel1.add(infoLabel1, BorderLayout.WEST);   

                            // Modify infoField2
                            infoField1.setPreferredSize(eastDim);                    
                            infoField1.setForeground(Color.gray);
                            infoField1.setEditable(false);
                            infoPanel1.add(infoField1, BorderLayout.EAST); 
                            
                            // ********************************************* Panel 6 Row:  Additional Info
                            // Create Panel
                            JPanel infoPanel2 = new JPanel();
                            infoPanel2.setLayout(new BorderLayout());
                            aduFrame.add(infoPanel2);                

                            // Modify infoLabel2
                            infoLabel2.setForeground(Color.GRAY);                    
                            infoPanel2.add(infoLabel2, BorderLayout.WEST);   

                            // Modify infoField2
                            infoField2.setPreferredSize(eastDim);                    
                            infoField2.setForeground(Color.gray);
                            infoField2.setEditable(false);
                            infoPanel2.add(infoField2, BorderLayout.EAST);  

                            // ********************************************* Panel 8 Row:  Additional Info
                            // Create Panel
                            JPanel infoPanel3 = new JPanel();
                            infoPanel3.setLayout(new BorderLayout());
                            aduFrame.add(infoPanel3);                

                            // Modify infoLabel3
                            infoLabel3.setForeground(Color.GRAY);                    
                            infoPanel3.add(infoLabel3, BorderLayout.WEST);   
                            
                            // Modify infoField3
                            infoField3.setPreferredSize(eastDim);                    
                            infoField3.setForeground(Color.gray);
                            infoField3.setEditable(false);
                            infoPanel3.add(infoField3, BorderLayout.EAST);          
                            
                            // ********************************************** Modify Additional Info Panels
                            
                            if (userType == "Customer"){
                                infoLabel1.setText("             Enter phone number:");
                                infoLabel2.setText("             Enter address:");
                                infoField1.setText("<phone number>");
                                infoField2.setText("<address>");                                
                                infoLabel1.setForeground(Color.black);
                                infoLabel2.setForeground(Color.black);
                                infoField1.setForeground(Color.black);
                                infoField2.setForeground(Color.black);
                                infoField1.setEditable(true);
                                infoField2.setEditable(true);                                       
                            }
                            // if (userType == "Employee")
                            else{
                                infoLabel1.setText("             Enter monthly salary:");
                                infoLabel2.setText("             Enter social security number:");
                                infoLabel3.setText("             Enter bank account number:");
                                infoField1.setText("0");
                                infoField2.setText("0");                                
                                infoField3.setText("0");
                                infoLabel1.setForeground(Color.black);
                                infoLabel2.setForeground(Color.black);
                                infoLabel3.setForeground(Color.black);
                                infoField1.setForeground(Color.black);
                                infoField2.setForeground(Color.black);                                
                                infoField3.setForeground(Color.black);  
                                infoField1.setEditable(true);
                                infoField2.setEditable(true);      
                                infoField3.setEditable(true);     
                            }

                            //*************************************************  Panel 8 Row: Save Button             
                            // Create panel
                            JPanel savePanel = new JPanel();
                            savePanel.setLayout(new BorderLayout());
                            aduFrame.add(savePanel);

                            // Create save button
                            JButton saveButton = new JButton("Update");
                            saveButton.setPreferredSize(new Dimension(130, 30));
                            savePanel.add(saveButton, BorderLayout.EAST);

                            // [Save User] Action Listener
                            saveButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent ae){ 
                                    // Variables
                                    String userType = userLabel2.getText();                                            
                                    String first = firstField.getText();
                                    String last = lastField.getText();
                                    String phone = null;
                                    String address = null;
                                    Float monSalary = null;
                                    Integer socialNum = null;
                                    Integer bankNum = null;

                                    if (first.equals("<first name>")){
                                        JOptionPane.showMessageDialog(null, "Please enter first name.", 
                                            "Error Message", JOptionPane.ERROR_MESSAGE);    
                                    }
                                    else if (last.equals("<last name>")){
                                        JOptionPane.showMessageDialog(null, "Please enter last name.", 
                                            "Error Message", JOptionPane.ERROR_MESSAGE);    
                                    }                        
                                    
                                    else if (userType.equals("Customer")){

                                        // Grab variables
                                        phone = infoField1.getText();
                                        address = infoField2.getText();

                                        if (phone.equals("<phone number>")){
                                        JOptionPane.showMessageDialog(null, "Please enter phone number.", 
                                            "Error Message", JOptionPane.ERROR_MESSAGE);  
                                        }
                                        else if (address.equals("<address>")){
                                        JOptionPane.showMessageDialog(null, "Please enter address.", 
                                            "Error Message", JOptionPane.ERROR_MESSAGE);                                  
                                        }
                                        else {
                                            // Add customer
                                            ss.updateCustomer(userID, first, last, phone, address);
                                            // Inform success
                                            JOptionPane.showMessageDialog(null, "Your customer was successfully updated.", 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);           
                                            // Clear add package frame
                                            aduFrame.dispose();  
                                        }
                                    }
                                    else if (userType.equals("Employee")){

                                        // Grab variables
                                        monSalary = Float.parseFloat(infoField1.getText());
                                        socialNum = Integer.parseInt(infoField2.getText());
                                        bankNum = Integer.parseInt(infoField3.getText());    

                                        // Validate monthly salary
                                        if (monSalary <= 0){
                                        JOptionPane.showMessageDialog(null, "Monthly salary must be a positive number."
                                                + "\nEx)  3845.00", "Error Message", JOptionPane.ERROR_MESSAGE);                                       
                                        }
                                        // Validate social security number
                                        else if (String.valueOf(socialNum).length() != 9){
                                            JOptionPane.showMessageDialog(null, "Social security number is not 9 digits long."
                                                    + "\nEx)  123456789", "Error Message", JOptionPane.ERROR_MESSAGE);                                   
                                        }
                                        else if (socialNum < 10000000 || socialNum > 999999999){
                                            JOptionPane.showMessageDialog(null, "Give a correct 9 digit integer.",
                                                "Error Message", JOptionPane.ERROR_MESSAGE);   
                                        }                       
                                        // Validate bank number
                                        else if (bankNum <= 0){
                                            JOptionPane.showMessageDialog(null, "Bank routing number must be a positive integer.",
                                                "Error Message", JOptionPane.ERROR_MESSAGE);        
                                        }
                                        // Add employee
                                        else{
                                            ss.updateEmployee(userID, first, last, socialNum, monSalary, bankNum);
                                            // Inform success
                                            JOptionPane.showMessageDialog(null, "Your employee was successfully updated.", 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);           
                                            // Clear add package frame
                                            aduFrame.dispose();                                  
                                        }
                                    }
                                }
                            });

                            //*************************************************  Panel LAST: Padding
                            // Create panel
                            JPanel paddingPanel = new JPanel();
                            aduFrame.add(paddingPanel);

                            aduFrame.pack();
                            aduFrame.setVisible(true);             
                        }
                    }
                 });   

                //*************************************************  Panel LAST: Padding
                // Create panel
                JPanel paddingPanel = new JPanel();
                updFrame.add(paddingPanel);
                
                
                updFrame.pack();
                updFrame.setVisible(true);
                
            }
        });

        mainFrame.setVisible(true);
        

//************************************************************************************************************     
       int choice = 0;
       boolean exitProgram = false;
       do {
           printMenu();
           try {
               choice = sc.nextInt();

               switch (choice) {
                   case 1: showAllPackages(); break;
                   case 2: addNewPackage(); break;
                   case 3: deletePackage(); break;
                   case 4: searchPackage(); break;
                   case 5: showAllUsers(); break;
                   case 6: addNewUser(); break;
                   case 7: updateUser(); break;
                   case 8: deliverPackage(); break;
                   case 9: showAllTransactions(); break;
                   case 10: ss.writeDatabase(); exitProgram = true; break;
                   default: System.err.println("Please select a number between 1 and 10.");
               }
           } catch (InputMismatchException ex) {
               System.err.println("Input missmatch. Please Try again.");
               continue;
           } catch (BadInputException ex) {
               System.err.println("Bad input. "+ex.getMessage());
               System.err.println("Please try again.");
               continue;
           }
       } while (!exitProgram);
   }
   
   /**
    * 
    */
   public Boolean isFloat(String test){
       Float num = null;
       try{
           num = Float.parseFloat(test);
       }
       catch(NumberFormatException nft){
           return false;
       }
       return true;
   }
   
    /**
    * 
    */
   public Boolean isInteger(String test){
       Integer num = null;
       try{
           num = Integer.parseInt(test);
       }
       catch(NumberFormatException nft){
           return false;
       }
       return true;
   }

   /**
    * Auxiliary method that prints out the operations menu.
    */
   private static void printMenu() {

       System.out.println(
               "\n 1. Show all existing packages in the database.\n" +
               " 2. Add a new package to the database. \n" +
               " 3. Delete a package from a database (given its tracking number).\n" +
               " 4. Search for a package (given its tracking number).\n" +
               " 5. Show list of users.\n" +
               " 6. Add a new user to the database.\n" +
               " 7. Update user info (given their id).\n" +
               " 8. Deliver a package.\n" +
               " 9. Show a list of transactions.\n" +
               "10. Exit program.\n");
   }

    /**
     * This method prints out all the package currently in the inventory, in a
     * formatted manner.
     */
    public void showAllPackages() {
        System.out.println(ss.getAllPackagesFormatted());
    }
    
   /**
     * This method allows the user to enter a new package to the list
     * database.
     * @throws shippingstore.BadInputException bad input
     */
    public void addNewPackage() throws BadInputException {
        System.out.println("Select package type:\n"
                + "1. Envelope\n"
                + "2. Box\n"
                + "3. Crate\n"
                + "4. Drum");
        int packageType = sc.nextInt();
        if (packageType < 1 || packageType > 4) {
            throw new BadInputException("Legal package type values: 1-4.");
        }
        sc.nextLine();

        System.out.println("\nEnter tracking number (string): ");
        String ptn = sc.nextLine();
        if (ptn.length() > 5) {
            throw new BadInputException("Tracking number should not be more that 5 characters long.");
        }

        if (ss.packageExists(ptn)) {
            System.out.println("\nPackage with the same tracking number exists, try again");
            return;
        }

        System.out.println("\nEnter Specification: Fragile, Books, Catalogs, Do-not-bend, or N/A");
        String specification = sc.nextLine();
        boolean correct = false;

        correct = specification.equalsIgnoreCase("Fragile") || specification.equalsIgnoreCase("Books") || specification.equalsIgnoreCase("Catalogs");
        correct = correct || specification.equalsIgnoreCase("Do-not-bend") || specification.equalsIgnoreCase("N/A");

        if (!(correct)) {
            throw new BadInputException("Specifications can only be one of the following: Fragile, Books, Catalogs, Do-not-bend, or N/A");
        }

        System.out.println("\nEnter mailing class can be First-Class, Priority, Retail, Ground, or Metro.");
        String mailingClass = sc.nextLine();

        correct = mailingClass.equalsIgnoreCase("First-Class") || mailingClass.equalsIgnoreCase("Priority") || mailingClass.equalsIgnoreCase("Retail");
        correct = correct || mailingClass.equalsIgnoreCase("Ground") || mailingClass.equalsIgnoreCase("Metro");
        if (!(correct)) {
            throw new BadInputException("Specifications can only be one of the following: Fragile, Books, Catalogs, Do-not-bend, or N/A");
        }

        if (packageType == 1) {
            System.out.println("\nEnter height (inch), (int): ");
            int height = 0;
            if (sc.hasNextInt()) {
                height = sc.nextInt();
                sc.nextLine();
                if (height < 0) {
                    throw new BadInputException("Height of Envelope cannot be negative.");
                }
            } else {
                sc.nextLine();
                throw new BadInputException("Height of Envelope is integer.");
            }

            int width = 0;
            System.out.println("\nEnter width (inch), (int): ");
            if (sc.hasNextInt()) {
                width = sc.nextInt();
                sc.nextLine();
                if (width < 0) {
                    throw new BadInputException("Width of Envelope cannot be negative.");
                }
            } else {
                sc.nextLine();
                throw new BadInputException("Width of Envelope is integer.");
            }
            
            ss.addEnvelope(ptn, specification, mailingClass, height, width);

        } else if (packageType == 2) {
            System.out.println("\nEnter largest dimension (inch), (int): ");

            int dimension = 0;
            if (sc.hasNextInt()) {
                dimension = sc.nextInt();
                sc.nextLine();
                if (dimension < 0) {
                    throw new BadInputException("Largest dimension of Box cannot be negative.");
                }
            } else {
                sc.nextLine();
                throw new BadInputException("Dimension should be integer.");
            }

            System.out.println("\nEnter volume (inch^3), (int): ");

            int volume = 0;
            if (sc.hasNextInt()) {
                volume = sc.nextInt();
                sc.nextLine();
                if (volume < 0) {
                    throw new BadInputException("Volume of Box cannot be negative.");
                }
            } else {
                sc.nextLine();
                throw new BadInputException("Volume should be integer.");
            }

            ss.addBox(ptn, specification, mailingClass, dimension, volume);

        } else if (packageType == 3) {
            System.out.println("\nEnter maximum load weight (lb), (float): ");

            float weight = 0.0f;
            if (sc.hasNextFloat()) {
                weight = sc.nextFloat();
                sc.nextLine();
                if (weight < 0.0f) {
                    throw new BadInputException("Maximum load weight of Crate cannot be negative.");
                }
            } else {
                sc.nextLine();
                throw new BadInputException("Max load should be float");
            }

            System.out.println("\nEnter content (string): ");
            String content = sc.nextLine();

            ss.addCrate(ptn, specification, mailingClass, weight, content);
           
        } else if (packageType == 4) {

            System.out.println("\nEnter material (Plastic / Fiber): ");
            String material = sc.nextLine();
            if (!(material.equalsIgnoreCase("Plastic") || material.equalsIgnoreCase("Fiber"))) {
                throw new BadInputException("Material of Drum can only be plastic or fiber.");
            }

            float diameter = 0.0f;
            System.out.println("\nEnter diameter (float): ");
            if (sc.hasNextFloat()) {
                diameter = sc.nextFloat();
                sc.nextLine();
                if (diameter < 0.0f) {
                    throw new BadInputException("Diameter of Drum cannot be negative.");
                }
            } else {
                sc.nextLine();
                throw new BadInputException("Diameter should be float");
            }

            ss.addDrum(ptn, specification, mailingClass, material, diameter);
            
        } else {
            System.out.println("Unknown package type entered. Please try again.");
        }
    }

    
    /**
     * This method allows the user to delete a package from the inventory
     * database.
     */
    public void deletePackage() {
        sc.nextLine();
        System.out.print("\nEnter tracking number of pacakge to delete (string): ");
        String ptn = sc.nextLine();

        if (ss.deletePackage(ptn)) 
            System.out.println("Package deleted.");
        else 
            System.out.println("Package with given tracking number not found in the database.");
    }
    
    /**
     * This method allows the users to search for a package given its tracking number
     * and then it prints details about the package.
     */
    public void searchPackage() {
        sc.nextLine();
        System.out.print("\nEnter tracking number of package to search for (string): ");
        String ptn = sc.nextLine();

        if (ss.packageExists(ptn))
            System.out.println(ss.getPackageFormatted(ptn));
        else
            System.out.println("Package with PTN " + ptn + " not found in the database");
    }
    
    /**
     * Prints out a list of all users in the database.
     */
    public void showAllUsers() {
        System.out.println(ss.getAllUsersFormatted());
    }
    
    /**
     * This method allows a new user to be added to the database.
     *
     */
    public void addNewUser() {
        boolean success;
        // Add fields for new user
        int userType = 0;
        boolean check = false;

        while (!check) {
            System.out.println("Select user type:\n"
                    + "1. Customer\n"
                    + "2. Employee");

            if (sc.hasNextInt()) {
                userType = sc.nextInt();

                if (userType < 1 || userType > 2) {
                    System.out.println("Wrong integer value: choose 1 or 2");
                } else {
                    check = true;
                }
            } else {
                System.out.println("Please select 1 or 2");
            }
        }

        sc.nextLine();
        System.out.println("\nEnter first name (string): ");
        String firstName = sc.nextLine();

        System.out.println("\nEnter last name (string): ");
        String lastName = sc.nextLine();

        if (userType == 1) {
            System.out.println("\nEnter phone number (string): ");
            String phoneNumber = sc.nextLine();

            System.out.println("\nEnter address (string): ");
            String address = sc.nextLine();

            ss.addCustomer(firstName, lastName, phoneNumber, address);

        } else if (userType == 2) {

            check = false;
            float monthlySalary = 0.0f;

            while (!check) {

                System.out.println("\nEnter monthly salary (float): ");

                if (sc.hasNextFloat()) {
                    monthlySalary = sc.nextFloat();
                    if (monthlySalary < 0.0f) {
                        System.out.println("Monthly salary cannot be negative.");
                    } else {
                        check = true;
                    }
                    sc.nextLine();

                } else {
                    System.out.println("Please enter monthly salary as a non-zero float value.");
                    sc.nextLine();
                }
            }

            int ssn = 0;
            check = false;
            while (!check) {

                System.out.println("\nEnter SSN (9-digital int): ");
                if (sc.hasNextInt()) {
                    ssn = sc.nextInt();
                    if (String.valueOf(ssn).length() != 9) {
                        System.out.println("\nThat is not a nine digit number");
                    } else if (ssn < 10000000 || ssn > 999999999) {
                        System.out.println("\nGive a correct 9 digit integer");
                    } else {
                        check = true;
                    }
                    sc.nextLine();
                } else {
                    System.out.println("\nNot a number!");
                    sc.nextLine();
                } //end if
            }// end while

            check = false;
            int bankAccNumber = 0;
            while (!check) {
                System.out.println("\nEnter bank account number (int): ");
                if (sc.hasNextInt()) {
                    bankAccNumber = sc.nextInt();
                    if (bankAccNumber < 0) {
                        System.out.println("\nBank account cannot be negative");
                    } else {
                        check = true;
                    }
                    sc.nextLine();
                } else {
                    System.out.println("Invalid bank Account format, please try again");
                    sc.nextLine();
                }

            }//end while

            ss.addEmployee(firstName, lastName, ssn, monthlySalary, bankAccNumber);
        } else {
            System.out.println("Unknown user type. Please try again.");
        }

    }
    
    /**
     * This method can be used to update a user's information, given their user
     * ID.
     *
     * @throws shippingstore.BadInputException
     */
    public void updateUser() throws BadInputException {
        boolean check = false;
        System.out.print("\nEnter user ID: ");
        int userID = sc.nextInt();

        if (!ss.userExists(userID)) {
            System.out.println("User not found.");
            return;
        }

        sc.nextLine();
        System.out.print("\nEnter first name (string): ");
        String firstName = sc.nextLine();

        System.out.print("\nEnter last name (string): ");
        String lastName = sc.nextLine();

        if (ss.isCustomer(userID)) {
            System.out.print("\nEnter phone number (string): ");
            String phoneNumber = sc.nextLine();
            System.out.print("\nEnter address (string): ");
            String address = sc.nextLine();
            
            ss.updateCustomer(userID, firstName, lastName, phoneNumber, address);

        } else { //User is an employee

            float monthlySalary = 0.0f;
            check = false;
            while (!check) {

                System.out.println("\nEnter monthly salary (float): ");

                if (sc.hasNextFloat()) {
                    monthlySalary = sc.nextFloat();
                    if (monthlySalary < 0.0f) {
                        new BadInputException("Monthly salary cannot be negative.");
                    } else {
                        check = true;
                    }
                    sc.nextLine();
                } else {
                    System.out.println("Please enter monthly salary as a non-zero float value.");
                    sc.nextLine();
                }
            }

            int ssn = 0;
            check = false;
            while (!check) {

                System.out.println("\nEnter SSN (9-digital int): ");
                if (sc.hasNextInt()) {
                    ssn = sc.nextInt();
                    if (String.valueOf(ssn).length() != 9) {
                        new BadInputException("\nThat is not a nine digit number");
                    } else if (ssn < 10000000 || ssn > 999999999) {
                        new BadInputException("\nGive a correct 9 digit integer");

                    } else {
                        check = true;
                    }
                } //end if
                sc.nextLine();

            }// end while

            int bankAccNumber = 0;
            check = false;
            while (!check) {
                System.out.println("\nEnter bank account number (int): ");

                if (sc.hasNextInt()) {
                    bankAccNumber = sc.nextInt();
                    if (bankAccNumber < 0) {
                        new BadInputException("Bank account cannot be negative");
                    } else {
                        check = true;
                    }
                    sc.nextLine();
                } else {
                    System.out.println("Invalid bank Account format, please try again");
                    sc.nextLine();
                }
            } //end while

            ss.updateEmployee(userID, firstName, lastName, ssn, monthlySalary, bankAccNumber);
        }
    }
    
    /**
     * This method is used to complete a package shipping/delivery transaction.
     *
     * @throws shippingstore.BadInputException
     */
    public void deliverPackage() throws BadInputException {

        Date currentDate = new Date(System.currentTimeMillis());

        sc.nextLine();
        System.out.println("\nEnter customer ID (int): ");
        int customerId = sc.nextInt();
        //Check that the customer exists in database
        boolean customerExists = ss.userExists(customerId);

        if (!customerExists) {
            System.out.println("\nThe customer ID you have entered does not exist in the database.\n"
                    + "Please add the customer to the database first and then try again.");
            return;
        }

        System.out.println("\nEnter employee ID (int): ");

        int employeeId = 0;
        if (sc.hasNextInt()) {
            employeeId = sc.nextInt();
        }
        //Check that the employee exists in database
        boolean employeeExists = ss.userExists(employeeId);

        if (!employeeExists) {
            System.out.println("\nThe employee ID you have entered does not exist in the database.\n"
                    + "Please add the employee to the database first and then try again.");
            return;
        }

        sc.nextLine();
        System.out.println("\nEnter tracking number (string): ");
        String ptn = sc.nextLine();

        //Check that the package exists in database
        if (!ss.packageExists(ptn)) {
            System.out.println("\nThe package with the tracking number you are trying to deliver "
                    + "does not exist in the database. Aborting transaction.");
            return;
        }

        System.out.println("\nEnter price (float): ");
        float price = sc.nextFloat();
        if (price < 0.0f) {
            throw new BadInputException("Price cannot be negative.");
        }

        ss.addShppingTransaction(customerId, employeeId, ptn, currentDate, currentDate, price);
        ss.deletePackage(ptn);

        System.out.println("\nTransaction Completed!");
    }
    
    
    /**
     * Prints out a list of all recorded transactions.
     */
    public void showAllTransactions() {
        System.out.println(ss.getAllTransactionsText());
    }


    /**
     * The main method of the program.
     *
     * @param the command line arguments
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        MainApp app = new MainApp();
        try {
            app.runSoftware();
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
