package edu.ucsb.cs56.projects.misc.map_gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Dimension;
import java.util.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Aki Stankoski and Dennis Huynh
 * @author Noah Malik and Jonathan Moody
 * @author Jinfa Zhu and Shouzhi Wan
 */

public class TheGUI{
    //declare the panels and buttons to be accessed from multiple methods  
    JFrame frame         =      new JFrame("UCSB Campus Map");//main frame
    JPanel thePanel      =      new JPanel();//Back homescreen panel
    JPanel newPanel      =      new JPanel();//New panel when button is clicked
    JPanel leftPanel     =      new JPanel();//Left homescreen panel
    JPanel rightPanel    =      new JPanel();//Right homescreen panel
    JPanel bottomPanel   =      new JPanel();//Bottom subpanels
    JPanel topPanel      =      new JPanel();//Top subpanels
    JPanel infoPanel     =      new JPanel();//info panel on subpanels
    JTextField searchBar =      new JTextField(30);//Seach bar

    //Search bar popup box components
    final DefaultComboBoxModel suggestBoxModel = new DefaultComboBoxModel();
    final JComboBox suggestBox = new JComboBox(suggestBoxModel);

    static JButton T387     =    new JButton("<html><center>387<br />Trailer 387</center></html>");
    static JButton T387info =  new JButton("learn more");
    static JButton T429     =    new JButton("<html><center>429<br />Trailer 429</center></html>");
    static JButton T429info =  new JButton("learn more");
    static JButton BRDA     =    new JButton("<html><center>BRDA<br />Broida Hall</center></html>");
     static JButton BRDAinfo =  new JButton("learn more");
    static JButton BSIF     =    new JButton("<html><center>BSIF<br />Biological Sciences Instruction Facility</center></html>");
     static JButton BSIFinfo =  new JButton("learn more");
    static JButton GIRV     =    new JButton("<html><center>GIRV<br />Girvetz Hall</center></html>");
     static JButton GIRVinfo =  new JButton("learn more");
    static JButton HSSB     =    new JButton("<html><center>HSSB<br />Humanities and Social Sciences Building</center></html>");
     static JButton HSSBinfo =  new JButton("learn more");
    static JButton HFH      =    new JButton("<html><center>HFH<br />Harold Frank Hall</center></html>");
     static JButton HFHinfo =  new JButton("learn more");
    static JButton KERR     =    new JButton("<html><center>KERR<br />Kerr Hall</center></html>");
     static JButton KERRinfo =  new JButton("learn more");
    static JButton LLCH     =    new JButton("<html><center>LLCH<br />Lotte-Lehmann Concert Hall</center></html>");
     static JButton LLCHinfo =  new JButton("learn more");
    static JButton PHELP    =    new JButton("<html><center>PHELP<br />Phelps Hall</center></html>");
     static JButton PHELPinfo =  new JButton("learn more");
    static JButton NORTH    =    new JButton("<html><center>NORTH<br />North Hall</center></html>");
     static JButton NORTHinfo =  new JButton("learn more");
    static JButton SOUTH    = 	 new JButton("<html><center>SOUTH<br />South Hall</center></html>");
     static JButton SOUTHinfo =  new JButton("learn more");
    static JButton CAMPBELL = 	 new JButton("<html><center>CAMBELL<br />Cambell Hall</center></html>");
     static JButton CAMPBELLinfo =  new JButton("learn more");
    static JButton ENGRSCI  =	 new JButton("<html><center>ENGRSCI<br />Engineering Science Building</center></html>");
     static JButton ENGRSCIinfo =  new JButton("learn more");
    static JButton ENGR2    =	 new JButton("<html><center>ENGR2<br />Engineering II Building</center></html>");
     static JButton ENGR2info =  new JButton("learn more");
    static JButton LIBRARY  =	 new JButton("<html><center>LIBRARY<br />Davidson Library</center></html>");
     static JButton LIBRARYinfo =  new JButton("learn more");
    static JButton EXIT     =    new JButton("<html><center>EXIT<br />Exit Program</center></html>"); //added button for exit
    JButton cancel          =    new JButton("Cancel");//cancel button for subscreens

    JLabel searchLabel   =      new JLabel("Search: ");
    JLabel T387Label     =      new JLabel("387 - Trailer 387");
    JLabel T429Label     =      new JLabel("429 - Trailer 429");
    JLabel BRDALabel     =      new JLabel("BRDA - Broida Hall");
    JLabel BSIFLabel     =      new JLabel("BSIF - Biological Sciences Instruction Facility");
    JLabel GIRVLabel     =      new JLabel("GIRV - Girvetz Hall");
    JLabel HSSBLabel     =      new JLabel("HSSB - Humanities and Social Sciences Building");
    JLabel HFHLabel      =      new JLabel("HFH - Harold Frank Hall");
    JLabel KERRLabel     =      new JLabel("KERR - Kerr Hall");
    JLabel LLCHLabel     =      new JLabel("LLCH - Lotte-Lehmann Concert Hall");
    JLabel PHELPLabel    =      new JLabel("PHELP - Phelps Hall");
    JLabel NORTHLabel    =      new JLabel("NORTH - North Hall");
    JLabel SOUTHLabel	 =      new JLabel("SOUTH - South Hall");
    JLabel CAMPBELLLabel =      new JLabel("CAMPBELL - Campbell Hall");
    JLabel ENGRSCILabel  =      new JLabel("ENGRSCI - Engineering Science Building");
    JLabel ENGR2Label    =      new JLabel("ENGR2 - Engineering II Building");
    JLabel LIBRARYLabel  =      new JLabel("LIBRARY - Davidson Library");

   

    // Methods for handling search bar adjusting
    // This makes sure the popup box isn't edited by multiple functions at the same time
    private static boolean isAdjusting(JComboBox cb) {
          if (cb.getClientProperty("is_adjusting") instanceof Boolean) {
                return ((Boolean) cb.getClientProperty("is_adjusting"));
          }
        return false;
    }//end isAdjusting

    private static void setAdjusting(JComboBox cb, boolean tof) {
          cb.putClientProperty("is_adjusting", tof);
    }//end setAdjusting

    //These methods account for keyEvents being triggered when they are not supposed to:
    //As far as I can tell, something is wrong with the methods on JAVA's end
    //causing multiple events to trigger on a single key press
    //This method property makes sure only one key press executes at a time
    private static boolean isAdjusting2(JComboBox cb) {
          if (cb.getClientProperty("is_adjusting_more") instanceof Boolean) {
                return ((Boolean) cb.getClientProperty("is_adjusting_more"));
          }
          return false;
    }//end isAdjusting2

    private static void setAdjusting2(JComboBox cb, boolean tof) {
          cb.putClientProperty("is_adjusting_more", tof);
    }//end setAdjusting2
    

    //function to set up the welcome page
    JFrame welcomeFrame = new JFrame("Welcome Page");
    JButton Enter = new JButton("Enter");
    JPanel tPanel = new JPanel();
    JPanel mPanel = new JPanel();
    JPanel nPanel = new JPanel();
    JPanel bPanel = new JPanel();
    JTextArea title = new JTextArea("cs56-misc-map-gui");
    JTextArea text = new JTextArea("Map GUI for navigating around UCSB's campus");

    public void setUpWelcomePage() throws IOException{
		guiRemoveAll();
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		tPanel.setSize(480,50);
		mPanel.setSize(480,50);
		nPanel.setSize(480,108);
		bPanel.setSize(600,800);

		title.setEditable(false);
		text.setEditable(false);
		Enter.addActionListener(new ENTERActionListener());
		Enter.setPreferredSize(new Dimension(150,75));
		Enter.setFont(new Font("Enter",0,40));
		java.net.URL UCSBLOGO_URL = getClass().getResource("/ucsb-wave-logo.png");
		JLabel LOGOlabel = new JLabel(new ImageIcon(UCSBLOGO_URL));


		tPanel.add(title);
		mPanel.add(text);
		nPanel.add(LOGOlabel);
		bPanel.add(Enter);
		
		welcomeFrame.setLayout(new BoxLayout(welcomeFrame.getContentPane(), BoxLayout.Y_AXIS));
		welcomeFrame.add(tPanel);
		welcomeFrame.add(mPanel);
		welcomeFrame.add(nPanel);
		welcomeFrame.add(bPanel);
		welcomeFrame.setSize(480,320);
		welcomeFrame.setBackground(Color.WHITE);
		welcomeFrame.setResizable(false);		
		welcomeFrame.setVisible(true);
	}//end setUpWelcomePage


        //function to set up the homescreen
	public void setUpHomeScreen() throws IOException{
	
		guiRemoveAll();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.X_AXIS));
	    thePanel.setBackground(Color.WHITE);
        thePanel.setSize(600,400);
		topPanel.setBackground(Color.WHITE);
		topPanel.setSize(600,50);
		leftPanel.setBackground(Color.WHITE);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setSize(300,200);
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setSize(300,200);
	 
		//setting size and adding actionlister
        searchBar.addKeyListener(new SearchBarKeyListener());
        searchBar.getDocument().addDocumentListener(new SearchBarDocumentListener());
        setAdjusting(suggestBox, false);
        suggestBox.setSelectedItem(null);
        suggestBox.addActionListener(new SuggestionListener());
		T387.setPreferredSize(new Dimension(100,100));
		T387.addActionListener(new T387Listener());
		T429.setPreferredSize(new Dimension(100,100));
		T429.addActionListener(new T429Listener());
		BRDA.setPreferredSize(new Dimension(100,100));
		BRDA.addActionListener(new BRDAListener());
		BSIF.setPreferredSize(new Dimension(100,100));
		BSIF.addActionListener(new BSIFListener());
		GIRV.setPreferredSize(new Dimension(100,100));
		GIRV.addActionListener(new GIRVListener());
		HSSB.setPreferredSize(new Dimension(100,100));
		HSSB.addActionListener(new HSSBListener());
		HFH.setPreferredSize(new Dimension(100,100));
		HFH.addActionListener(new HFHListener());
		LLCH.setPreferredSize(new Dimension(100,100));
		LLCH.addActionListener(new LLCHListener());
		PHELP.setPreferredSize(new Dimension(100,100));
		PHELP.addActionListener(new PHELPListener());
		KERR.setPreferredSize(new Dimension(100,100));
		KERR.addActionListener(new KERRListener());
	    NORTH.setPreferredSize(new Dimension(100,100));
	    NORTH.addActionListener(new NORTHListener());
	    SOUTH.setPreferredSize(new Dimension(100,100));
	    SOUTH.addActionListener(new SOUTHListener());
	    ENGRSCI.setPreferredSize(new Dimension(100,100));
	    ENGRSCI.addActionListener(new ENGRSCIListener());
	    ENGR2.setPreferredSize(new Dimension(100,100));
	    ENGR2.addActionListener(new ENGR2Listener());
	    LIBRARY.setPreferredSize(new Dimension(100,100));
	    LIBRARY.addActionListener(new LIBRARYListener());
	    EXIT.setPreferredSize(new Dimension(100,100));
	    EXIT.addActionListener(new EXITListener());
	    
	    // POP UP INFORMATION WINDOWS
	    	T387info.setPreferredSize(new Dimension(100,100));
		T387info.addActionListener(new T387infoListener());
		T429info.setPreferredSize(new Dimension(100,100));
		T429info.addActionListener(new T429infoListener());
		BRDAinfo.setPreferredSize(new Dimension(100,100));
		BRDAinfo.addActionListener(new BRDAinfoListener());
		BSIFinfo.setPreferredSize(new Dimension(100,100));
		BSIFinfo.addActionListener(new BSIFinfoListener());
		GIRVinfo.setPreferredSize(new Dimension(100,100));
		GIRVinfo.addActionListener(new GIRVinfoListener());
		HSSBinfo.setPreferredSize(new Dimension(100,100));
		HSSBinfo.addActionListener(new HSSBinfoListener());
		HFHinfo.setPreferredSize(new Dimension(100,100));
		HFHinfo.addActionListener(new HFHinfoListener());
		LLCHinfo.setPreferredSize(new Dimension(100,100));
		LLCHinfo.addActionListener(new LLCHinfoListener());
		PHELPinfo.setPreferredSize(new Dimension(100,100));
		PHELPinfo.addActionListener(new PHELPinfoListener());
		KERRinfo.setPreferredSize(new Dimension(100,100));
		KERRinfo.addActionListener(new KERRinfoListener());
	    NORTHinfo.setPreferredSize(new Dimension(100,100));
	    NORTHinfo.addActionListener(new NORTHinfoListener());
	    SOUTHinfo.setPreferredSize(new Dimension(100,100));
	    SOUTHinfo.addActionListener(new SOUTHinfoListener());
	    ENGRSCIinfo.setPreferredSize(new Dimension(100,100));
	    ENGRSCIinfo.addActionListener(new ENGRSCIinfoListener());
	    ENGR2info.setPreferredSize(new Dimension(100,100));
	    ENGR2info.addActionListener(new ENGR2infoListener());

	    suggestBox.setPreferredSize(new Dimension(20, 0));

		//adding panels and setting dimensions
		topPanel.add(searchLabel);
		topPanel.add(searchBar);
		leftPanel.add(T387);
		leftPanel.add(T387info);
		leftPanel.add(Box.createRigidArea(new Dimension(50,25)));

		leftPanel.add(T429);
		leftPanel.add(T429info);
		leftPanel.add(Box.createRigidArea(new Dimension(50,25)));
		
		leftPanel.add(BRDA);
		leftPanel.add(BRDAinfo);
		leftPanel.add(Box.createRigidArea(new Dimension(50,25)));
		
		leftPanel.add(BSIF);
		leftPanel.add(BSIFinfo);
		leftPanel.add(Box.createRigidArea(new Dimension(50,25)));
		
		leftPanel.add(GIRV);
		leftPanel.add(GIRVinfo);
		leftPanel.add(Box.createRigidArea(new Dimension(50,25)));
		
		leftPanel.add(NORTH);
		leftPanel.add(NORTHinfo);
		leftPanel.add(Box.createRigidArea(new Dimension(50,25)));
		
		leftPanel.add(ENGRSCI);
		leftPanel.add(ENGRSCIinfo);
		leftPanel.add(Box.createRigidArea(new Dimension(50,25)));
		
		leftPanel.add(LIBRARY);

		rightPanel.add(HSSB);
		rightPanel.add(HSSBinfo);
		rightPanel.add(Box.createRigidArea(new Dimension(50,25)));
		
		rightPanel.add(HFH);
		rightPanel.add(HFHinfo);
		rightPanel.add(Box.createRigidArea(new Dimension(50,25)));
		
		rightPanel.add(KERR);
		rightPanel.add(KERRinfo);
		rightPanel.add(Box.createRigidArea(new Dimension(50,25)));
		
		rightPanel.add(LLCH);
		rightPanel.add(LLCHinfo);
		rightPanel.add(Box.createRigidArea(new Dimension(50,25)));
		
		rightPanel.add(PHELP);
		rightPanel.add(PHELPinfo);
		rightPanel.add(Box.createRigidArea(new Dimension(50,25)));
		
		rightPanel.add(SOUTH);
		rightPanel.add(SOUTHinfo);
		rightPanel.add(Box.createRigidArea(new Dimension(50,25)));

		
		rightPanel.add(ENGR2);
		rightPanel.add(ENGR2info);
		rightPanel.add(Box.createRigidArea(new Dimension(50,25)));
		
		rightPanel.add(EXIT);
		
		thePanel.add(leftPanel);
		thePanel.add(rightPanel);
		searchBar.setLayout(new BorderLayout());
        searchBar.add(suggestBox, BorderLayout.SOUTH);
		frame.getContentPane().add(BorderLayout.CENTER, thePanel);
		frame.getContentPane().add(BorderLayout.NORTH, topPanel);
		frame.setSize(600, 800);
		frame.setResizable(false);
		frame.setBackground(Color.WHITE);
		frame.setVisible(true);
		searchBar.requestFocus(); //put cursor in search bar
		setAdjusting2(suggestBox, false);
	}//end setUpHomeScreen
	

 	//function to clear the main frame
	public void guiRemoveAll() {
	
    	//this wipes the frame clean, use before switching panels
    		setAdjusting2(suggestBox, true);
            leftPanel.removeAll();
            rightPanel.removeAll();
            bottomPanel.removeAll();
            topPanel.removeAll();
            infoPanel.removeAll();
            thePanel.removeAll();
            newPanel.removeAll();
            searchBar.setText("");
            suggestBox.removeAllItems();
            frame.getContentPane().removeAll();
            frame.getContentPane().remove(thePanel);
            frame.getContentPane().remove(newPanel);
			frame.validate();
            frame.repaint();
	}//end guiRemoveAll
	

	public void search() {
  
		String query = searchBar.getText();
	    if(!isAdjusting(suggestBox)) {
			if(MapStatics.buttonMap.containsKey(query)) {
		    	setAdjusting(suggestBox, true); //reset during setUpHomeScreen
		    	((JButton) MapStatics.buttonMap.get(query)).doClick();
			}
	    }
      }//end search
	  

	public void autoComplete() {
		setAdjusting(suggestBox, true);
            suggestBoxModel.removeAllElements();
            String query = searchBar.getText();
            boolean bool = false;

            if(!query.isEmpty()) {
                  for(String item : MapStatics.bldgNames) {
                        if(item.toLowerCase().startsWith(query.toLowerCase())) {
                              suggestBoxModel.addElement(item);
                              bool = true;
                        }
                  }
                  for(String item : MapStatics.bldgAbbrs) {
                        if(item.toLowerCase().startsWith(query.toLowerCase())) {
                              suggestBoxModel.addElement(item);
                              bool = true;
                        }
                  }
            }

        suggestBox.setPopupVisible(bool);
        setAdjusting(suggestBox, false);
    }//end autoComplete
    
    public void setSuggestion() {
    	if(!isAdjusting2(suggestBox)){
	      searchBar.setText(suggestBox.getSelectedItem().toString());
	      suggestBox.setPopupVisible(false);}
      }//end setSuggestion
  

	public void T387() throws IOException{
		//guiRemoveAll();
		//newPanel.setBackground(Color.WHITE);
		//newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
		//newPanel.setSize(800,625);
		//JTextArea T387TA = new JTextArea(MapStatics.T387Info);
		//T387TA.setEditable(false);
		//T387TA.setLineWrap(true);
		//T387TA.setWrapStyleWord(true);
		//JScrollPane T387Scroll = new JScrollPane(T387TA);
		//T387Scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//T387TA.setPreferredSize(new Dimension(200,600));
		//infoPanel.add(T387Scroll);
		//bottomPanel.add(cancel);
		//newPanel.add(bottomPanel);
		//topPanel.add(T387Label);
		//cancel.addActionListener(new CancelActionListener());
		//java.net.URL T387_URL = getClass().getResource("/387.jpg");
		//JLabel T387label = new JLabel(new ImageIcon(T387_URL));

		//String Path = "387.jpg";
		//File File = new File(Path);
		//BufferedImage Image = ImageIO.read(File);
		//JLabel label = new JLabel(new ImageIcon(Image));

		//newPanel.add(T387label);
		//frame.getContentPane().add(BorderLayout.EAST, infoPanel);
		//frame.getContentPane().add(BorderLayout.NORTH, topPanel);
		//frame.getContentPane().add(BorderLayout.CENTER,newPanel);
		//frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
		//frame.setSize(1000,625);
		//frame.setBackground(Color.WHITE);
		//frame.setVisible(true);
		setupbutton(MapStatics.T387Info, T387Label, "/387.jpg");
	}//end T387

	
	public void T429() throws IOException{
		setupbutton(MapStatics.T429Info, T429Label, "/429.jpg");

	}
    
	public void BRDA() throws IOException{
		setupbutton(MapStatics.BRDAInfo, BRDALabel, "/BRDA.jpg");
	}
       
	public void BSIF() throws IOException{
		setupbutton(MapStatics.BSIFInfo, BSIFLabel, "/BSIF.jpg");
	}
       
	public void GIRV() throws IOException{
		setupbutton(MapStatics.GIRVInfo, GIRVLabel, "/GIRV.jpg");
	}
       
	public void HFH() throws IOException{
		setupbutton(MapStatics.HFHInfo, HFHLabel, "/HFH.jpg");
	}
        
	public void HSSB() throws IOException{
		setupbutton(MapStatics.HSSBInfo, HSSBLabel, "/HSSB.jpg");
	}
    
	public void KERR() throws IOException{
		setupbutton(MapStatics.KERRInfo, KERRLabel, "/KERR.jpg");
	}
    
	public void LLCH() throws IOException{
		setupbutton(MapStatics.LLCHInfo, LLCHLabel, "/LLCH.jpg");
	}
       
	public void PHELP() throws IOException{
		setupbutton(MapStatics.PHELPInfo, PHELPLabel, "/PHELP.jpg");
	}
    
	public void NORTH() throws IOException {
		setupbutton(MapStatics.NORTHInfo, NORTHLabel, "/NORTH.jpg");
	}
	
	public void SOUTH() throws IOException {
		setupbutton(MapStatics.SOUTHInfo, SOUTHLabel, "/SOUTH.jpg");
	}
	
	public void ENGRSCI() throws IOException {
		setupbutton(MapStatics.ENGRSCIInfo, ENGRSCILabel, "/ENGRSCI.jpg");
	}
	
	public void ENGR2() throws IOException {
		setupbutton(MapStatics.ENGR2Info, ENGR2Label, "/ENGR2.jpg");
	}
	
	public void LIBRARY() throws IOException {
		setupbutton(MapStatics.LIBRARYInfo, LIBRARYLabel, "/LIBRARY.jpg");
	}

	public void setupbutton(String info, JLabel Label, String pics){
		guiRemoveAll();
		newPanel.setBackground(Color.WHITE);
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
		newPanel.setSize(800,625);
		JTextArea TA=new JTextArea(info);
		TA.setEditable(false);
		TA.setLineWrap(true);
		TA.setWrapStyleWord(true);
		JScrollPane Scroll = new JScrollPane(TA);
		Scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		TA.setPreferredSize(new Dimension(200,600));
		infoPanel.add(Scroll);
		bottomPanel.add(cancel);
		newPanel.add(bottomPanel);
		topPanel.add(Label);
		cancel.addActionListener(new CancelActionListener());
		java.net.URL URL = getClass().getResource(pics);
		JLabel label = new JLabel(new ImageIcon(URL));
		newPanel.add(label);
		frame.getContentPane().add(BorderLayout.EAST, infoPanel);
		frame.getContentPane().add(BorderLayout.NORTH, topPanel);
		frame.getContentPane().add(BorderLayout.CENTER,newPanel);
		frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
		frame.setSize(1000,625);
		frame.setBackground(Color.WHITE);
		frame.setVisible(true);
	}

    	public void EXIT() throws IOException {
	    System.exit(0);
	}
	
	
	class SearchBarKeyListener extends KeyAdapter {

            public void keyPressed(KeyEvent event) {
                  setAdjusting(suggestBox, true);
                  int keyCode = event.getKeyCode();

                  if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
                        //These following two lines forward the appropriate action to the JComboBox based on the key pressed?
                        event.setSource(suggestBox);
                        suggestBox.dispatchEvent(event);
                    }

                        if(keyCode == KeyEvent.VK_ENTER) {
                              setSuggestion();
                              search();
                        }
                  

                  if(keyCode == KeyEvent.VK_ESCAPE) {
                        suggestBox.setPopupVisible(false);
                  }
                  setAdjusting(suggestBox, false);
            }

      }// end SearchBarKeyListener
      

      //document listener for the search bar
      //this is called for every change made to the JTextField 'searchBar'
      class SearchBarDocumentListener implements DocumentListener{
	  public void changedUpdate(DocumentEvent e){
	      autoComplete();
	  }
	  //called when there is an insertion to the document
	  public void insertUpdate(DocumentEvent e){
	      autoComplete();
	  }
	  //called when there is a deletion in the document
	  public void removeUpdate(DocumentEvent e){
	      autoComplete();
	  }
      }//end SearchBarDocumentActionListener


      //called when a selection in the suggestion bar is made
      class SuggestionListener implements ActionListener{
            public void actionPerformed(ActionEvent event){
                  if(!isAdjusting(suggestBox) && suggestBox.getSelectedItem() != null) {
                        setSuggestion();
                        search();
                  }
            }
      }//end SuggestionListener

	
	//action listener class for the cancel button
	class CancelActionListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		try {setUpHomeScreen();}
			catch (IOException ex) {
				Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
	}//end CancelActionListener

	class T387Listener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		try {T387();}
			catch (IOException ex) {
				Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
	}//end T387Listener

	class T429Listener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		try {T429();} 
			catch (IOException ex) {
				Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
	}//end T429Listener

	class BRDAListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		try {BRDA();}
			catch (IOException ex) {
				Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
	}//end BRDAListener

	class BSIFListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		try {BSIF();}
			catch (IOException ex) {
				Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
	}//end BSIFListener

	class GIRVListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		try {GIRV();} 
			catch (IOException ex) {
				Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
	}//end GIRVListener

	class HFHListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		try {HFH();}
			catch (IOException ex) {
				Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
	}//end HFHListener

	class HSSBListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		try {HSSB();}
			catch (IOException ex) {
				Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
	}//end HSSBListener

	class KERRListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		try {KERR();}
			catch (IOException ex) {
				Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
	}//end KERRListener

	class LLCHListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		try {LLCH();} 
			catch (IOException ex) {
				Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
	}//end LLCHListener

	class PHELPListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		try {PHELP();}
			catch (IOException ex) {
				Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
	}//end PHELPListener
	
	class NORTHListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {NORTH();}
				catch (IOException ex) {
					Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
					}
		}
	}//end NORTHListener
	
	class SOUTHListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {SOUTH();}
				catch (IOException ex) {
					Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
					}
		}
	}//end SOUTHListener
	
	class ENGRSCIListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {ENGRSCI();}
				catch (IOException ex) {
					Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
					}
		}
	}//end ENGRSCIListener
	
	class ENGR2Listener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {ENGR2();}
				catch (IOException ex) {
					Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
					}
		}
	}//end ENGR2Listener
	
	class LIBRARYListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {LIBRARY();}
				catch (IOException ex) {
					Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
					}
		}
	}//end LIBRARYListener

    class EXITListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
		    try{EXIT();}
		        catch (IOException ex){
			        Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
			        }
		}
	}//end EXITListener

	class ENTERActionListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			try {welcomeFrame.dispose();setUpHomeScreen();}
			    catch (IOException ex) {
				    Logger.getLogger(TheGUI.class.getName()).log(Level.SEVERE, null, ex);
				    }
		}
	}//end ENTERActionListener


    // Listeners for info buttin
    class T387infoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    JOptionPane.showMessageDialog(new JFrame(), "Trailer office near Psychology building");
	}
    }
    class T429infoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	   	JOptionPane.showMessageDialog(new JFrame(), "Trailer office near Psychology building");
	}
    }
    class BRDAinfoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	   	JOptionPane.showMessageDialog(new JFrame(), "Department of Physics");
	}
    }
    class BSIFinfoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	   	JOptionPane.showMessageDialog(new JFrame(), "Department of Biology Science");
	}
    }
    class GIRVinfoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    	JOptionPane.showMessageDialog(new JFrame(), "Classrooms of Sections");
	}
    }
    class HSSBinfoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	   	JOptionPane.showMessageDialog(new JFrame(), "Department of Human and Social Science");
	}
    }
    class HFHinfoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	  	JOptionPane.showMessageDialog(new JFrame(), "Department of Computer Science and Electrical Engineerings");
	}
    }
    class KERRinfoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	   	JOptionPane.showMessageDialog(new JFrame(), "Department of Digital and Media");
	}
    }
    class LLCHinfoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	   	JOptionPane.showMessageDialog(new JFrame(), "Department of Music");
	}
    }
    class PHELPinfoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	   	JOptionPane.showMessageDialog(new JFrame(), "Building of Computer Science labs");
	}
    }
    class NORTHinfoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	   	JOptionPane.showMessageDialog(new JFrame(), "Department of Economics");
	}
    }
    class SOUTHinfoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	  	JOptionPane.showMessageDialog(new JFrame(), "Department of Mathmatics and Writing");
	}
    }
    class CAMPBELLinfoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(new JFrame(), "The Hall for specail events");
	}
    }
    class ENGRSCIinfoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(new JFrame(), "Department of Engineering Science");

	}
    }
    class ENGR2infoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(new JFrame(), "Department of Engineering");
	   
	}
    }
  
    
	   
	   
} //end class

