/**
 *
 * @author Darshan Shetty, Ankita Mohapatra
 */

import javax.swing.*;        
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.geom.Point2D;

public class Keyboard{
	
	JFrame window;
	Key[] keys;
	JPanel board; //for loading buttons
	JPanel suggestionBox; //for displaying suggestions
	JTextField inputdisplay;
	JLabel outputdisplay;
	ArrayList<JButton> suggestionButtons;
	JButton suggestion1Button,suggestion2Button,suggestion3Button,suggestion4Button;
	Container panel;
	MouseListener mouselistener;
	JButton setGoalButton,setResetButton;//,LevensteinDistance;
	private String formattedUserWords = "";
	private static boolean USE_CROSS_PLATFORM_UI = true;
	
	public Keyboard() throws FileNotFoundException{
		if(USE_CROSS_PLATFORM_UI) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		mouselistener = new MouseListener();
		window = new JFrame("keyboard");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(600,400);
		
		//set the keyboard pad layout
		board = new JPanel();
		Border border = BorderFactory.createEmptyBorder(0,10,20,10);
		board.setBorder(border);
		
				//input display
		inputdisplay = new JTextField("");
		//inputdisplay.setText("<html><font color= red>blah blah</font></html>");
		inputdisplay.setFont(new Font("Serif", Font.PLAIN, 16));
		inputdisplay.setBackground(new Color(237,237,237));
		inputdisplay.setEditable(false);
		Border title = BorderFactory.createTitledBorder("Input: ");
		Border bevel = BorderFactory.createLoweredBevelBorder();
		Border border1 = BorderFactory.createEmptyBorder(0,10,5,10);
		Border border2 = BorderFactory.createEmptyBorder(10,10,5,10);
		Border border3 = BorderFactory.createCompoundBorder(border1, bevel);
		Border border4 = BorderFactory.createCompoundBorder(border3, title);
		inputdisplay.setBorder(BorderFactory.createCompoundBorder(border4, border1));
		
		//modify input sample border
		outputdisplay = new JLabel("_");
		//outputdisplay.setText("<html><font color= red>blah blah</font></html>"+"<html><font color= blue>blah blah</font></html>");
		Border title2 = BorderFactory.createTitledBorder("Output:");
		Border border5 = BorderFactory.createEmptyBorder(20,10,20,10);
		Border border6 = BorderFactory.createEmptyBorder(10,9,10,9);
		Border border7 = BorderFactory.createCompoundBorder(title2,border6);
		outputdisplay.setBorder(BorderFactory.createCompoundBorder(border5,border7));
		outputdisplay.setForeground(Color.blue);
		outputdisplay.setFont(new Font("Serif", Font.PLAIN, 16));
		
		//suggestion box
		suggestionBox = new JPanel();
		suggestionBox.setFont(new Font("Serif", Font.PLAIN, 16));
		suggestionBox.setBackground(new Color(237,237,237));
		Border title3 = BorderFactory.createTitledBorder("Suggestions:");
		Border border8 = BorderFactory.createEmptyBorder(20,10,20,10);
		Border border9 = BorderFactory.createEmptyBorder(10,9,10,9);
		Border border10 = BorderFactory.createCompoundBorder(title3,border9);
		suggestionBox.setBorder(BorderFactory.createCompoundBorder(border8,border10));
		suggestionBox.setForeground(Color.blue);
		suggestionBox.setFont(new Font("Serif", Font.PLAIN, 16));
		
		suggestionButtons = new ArrayList<JButton>(4);
		suggestion1Button = new javax.swing.JButton();
		suggestion1Button.setPreferredSize(new Dimension(190, 50));
        suggestion2Button = new javax.swing.JButton();
        suggestion2Button.setPreferredSize(new Dimension(190, 50));
        suggestion3Button = new javax.swing.JButton();
        suggestion3Button.setPreferredSize(new Dimension(190, 50));
        suggestion4Button = new javax.swing.JButton();
        suggestion4Button.setPreferredSize(new Dimension(190, 50));
        suggestionButtons.add(suggestion1Button);
        suggestionButtons.add(suggestion2Button);
        suggestionButtons.add(suggestion3Button);
        suggestionButtons.add(suggestion4Button);
        suggestionBox.add(suggestion1Button);
        suggestionBox.add(suggestion2Button);
        suggestionBox.add(suggestion3Button);
        suggestionBox.add(suggestion4Button);
      
        suggestion1Button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        suggestion1Button.setEnabled(true);
        suggestion1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestionButtonActionPerformed(evt);
            }
        });

        suggestion2Button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        suggestion2Button.setEnabled(true);
        suggestion2Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestionButtonActionPerformed(evt);
            }
        });

        suggestion3Button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        suggestion3Button.setEnabled(true);
        suggestion3Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestionButtonActionPerformed(evt);
            }

			});

        suggestion4Button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        suggestion4Button.setEnabled(true);
        suggestion4Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	suggestionButtonActionPerformed(evt);
            }
        });

                
        setResetButton= new javax.swing.JButton();
        setGoalButton = new javax.swing.JButton();
        //LevensteinDistance = new javax.swing.JButton();
                
        setGoalButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        setGoalButton.setEnabled(true);
        setGoalButton.setText("Expected");
        setGoalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setGoalButtonActionPerformed(evt);
            }
        });
                      
        setResetButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        setResetButton.setEnabled(true);
        setResetButton.setText("Clear");
        setResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetInputButtonActionPeformed(evt);
            }
        }); 
        /*
        LevensteinDistance.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LevensteinDistance.setEnabled(true);
        LevensteinDistance.setText("Calculate Levenstein Distance");
        LevensteinDistance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateLevensteinDistanceButtonActionPeformed(evt);
            }
			
        }); */
			        
		board.setLayout(new GridBagLayout());
		//set the buttons:
		int[] keyNum =  {10,9,7};
		keys = new Key[30];
		String[] keyLabels ={"QWERTYUIOP","ASDFGHJKL","ZXCVBNM"}; //change to keyboard setting
		
		//first line
		for (int i = 0; i < keyNum[0]; i++){ //first line of keys
			String label = keyLabels[0].substring(i, i+1);
			keys[i] = new Key(label);
			keys[i].setName(label);
			keys[i].setFocusPainted(true);
			keys[i].setOpaque(true);
			keys[i].setBackground(Color.WHITE);
			keys[i].addMouseListener(mouselistener);
			keys[i].addMouseMotionListener(mouselistener);
			addKey(board,keys[i],i+1,0,1,1);
		}
					
		//second line
		for (int i = 0; i < keyNum[1]; i++){ //second line of keys
			String label = keyLabels[1].substring(i, i+1);
			keys[i] = new Key(label);
			keys[i].setName(label);
			keys[i].setFocusPainted(true);
			keys[i].setBackground(Color.WHITE);
			keys[i].setOpaque(true);
			keys[i].addMouseListener(mouselistener);
			keys[i].addMouseMotionListener(mouselistener);
			addKey(board,keys[i],i+1,1,1,1);
					
			}
									
		//third line
		for (int i = 0;i< keyNum[2]; i++){ //third line of keys
			String label = keyLabels[2].substring(i, i+1);
			keys[i] = new Key(label);
			keys[i].setName(label);
			keys[i].setFocusPainted(true);
			keys[i].setBackground(Color.WHITE);
			keys[i].setOpaque(true);
			keys[i].addMouseListener(mouselistener);
			keys[i].addMouseMotionListener(mouselistener);
			addKey(board,keys[i],i+2,2,1,1);
		}
		
		//set the space button
		keys[26] = new Key(" ");
		keys[26].setName(" ");
		keys[26].setFocusPainted(true);
		keys[26].setOpaque(true);
		keys[26].setBackground(Color.WHITE);
		keys[26].addMouseListener(mouselistener);
		keys[26].addMouseMotionListener(mouselistener);
		addKey(board,keys[26],2,3,7,1);
				       
		panel = window.getContentPane();
		//use gridBag layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
	    c.gridwidth = 4;
		c.gridheight = 1;
		c.anchor= GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		panel.add(inputdisplay,c);
		
		c.gridx = 4;
		c.gridy = 0;
		//c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		panel.add(setGoalButton,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;
		c.gridheight = 1;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(1,5,5,5);
		panel.add(outputdisplay,c);
		
		c.gridx = 4;
		c.gridy = 1;
//		c.gridwidth = 1;
//		c.gridheight = 1;	
		c.anchor=GridBagConstraints.CENTER;
		//int a=c.NONE;
//		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		panel.add(setResetButton,c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(1,5,5,5);
		panel.add(suggestionBox,c);
		
		/*c.gridx = 2;
		c.gridy = 3;
		//c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor=GridBagConstraints.WEST;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		panel.add(LevensteinDistance,c);*/
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 3;
		c.gridheight = 4;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,10,10,10);
		panel.add(board,c);
						
		window.pack();// adjust the window size
		window.setVisible(true);
	}	
	
	protected void resetInputButtonActionPeformed(java.awt.event.ActionEvent evt) {
		// TODO Auto-generated method stub
		    if(rawGoal != null && !rawGoal.equals(""))
	        {
	            resetGoal(rawGoal);
	        }
	        else
	        {
	            formattedGoalWords = "";
	            formattedUserWords = "";
	            currentword = "";
	            //currentWordButton.setText("");
	            userWords.clear();
	            outputdisplay.setText("_");
	        }
	    }
	protected void setGoalButtonActionPerformed(ActionEvent evt) {
        String input = JOptionPane.showInputDialog("Enter expected sentence:");
        if(input != null && !(input.equals("")))
        {
            resetGoal(input);
        }
    }
	private void resetGoal(String newGoal)
    {
        // Clean the goal sentence up
        newGoal = newGoal.toUpperCase().trim();
        String cleanGoal = "";
        
        boolean lastWasSpace = true;
        for(int i = 0; i < newGoal.length(); i++)
        {
            char c = newGoal.charAt(i);
            if(c == ' ' && !lastWasSpace)
            {
                cleanGoal = cleanGoal + c;
                lastWasSpace = true;
            }
            else if(c >= 'A' && c <= 'Z')
            {
                cleanGoal = cleanGoal + c;
                lastWasSpace = false;
            }
        }
        
        cleanGoal = cleanGoal.trim();
        rawGoal = cleanGoal;
        inputdisplay.setText(cleanGoal);
        outputdisplay.setText("_");
        currentword = "";
        formattedGoalWords = "";
        formattedUserWords = "";
        userWords.clear();
        clearWord();
        goalWords = new ArrayList<String>(Arrays.asList(cleanGoal.split(" ")));
    }

	//function to clear words
	private void clearWord()
	{
	    currentword = "";
	    outputdisplay.setText(formattedUserWords + "_");
		for(int i = 0; i < suggestionButtons.size(); i++)
		{
		     suggestionButtons.get(i).setText("");
		     suggestionButtons.get(i).setEnabled(false);
		}
	}	
	
    private void suggestionButtonActionPerformed(java.awt.event.ActionEvent evt)
    {
        JButton button = (JButton)(evt.getSource());
        updateOutputDisplay(button.getText());
        currentword = "";
    }
	 
    private void updateOutputDisplay (String theChar){
    	int i;
		String oldString = outputdisplay.getText();
		currentword = theChar;
        for(i = oldString.length() - 2; i > 0; i--) {
            if(oldString.charAt(i) == ' ')
        	    break;
        }
        String lastWord = oldString.substring(0, i);
        String newUserWordText;
        if(lastWord.length() > 0)
        	newUserWordText = lastWord + " " + currentword + " _";
        else
        	newUserWordText = currentword + " _";
        
        //System.out.println(oldString.substring(0, i));
   	    outputdisplay.setText(newUserWordText);
   	    calculateLevensteinDistance();
	}
    
    private void calculateLevensteinDistance()
    {
        if(currentword == null || currentword.equals(""))
        {
            return;
        }
        
        String newUserWordText = formattedUserWords;
        String newGoalWordText = formattedGoalWords;
        int wordIndex = userWords.size();
        userWords.add(currentword);
        
        if(goalWords.size() > wordIndex)
        {
            String goalWord = goalWords.get(wordIndex);
            System.out.println("goal " + goalWord + "current word " + currentword);
            ArrayList<Edit> differences = LevensteinDistance.getDistance(currentword, goalWord);
            for(Edit e : differences)
            {
                EditType editType = e.getEditType();
                if(editType == EditType.DELETION)
                {
                    newGoalWordText = addColoredLetter(newGoalWordText, e.getOldChar(), "Yellow");
                    System.out.println(newGoalWordText + "Y old character" + e.getOldChar());
                    errors++;
                }
                else if(editType == EditType.INSERTION)
                {
                    newUserWordText = addColoredLetter(newUserWordText, e.getNewChar(), "Purple");
                    System.out.println(newUserWordText + "P old character" + e.getOldChar());
                    errors++;
                }
                else if(editType == EditType.MISMATCH)
                {
                    newGoalWordText = addColoredLetter(newGoalWordText, e.getOldChar(), "Red");
                    newUserWordText = addColoredLetter(newUserWordText, e.getNewChar(), "Red");
                    System.out.println(newGoalWordText + "R old character" + e.getOldChar());
                    errors++;
                }
                else
                {
                    newGoalWordText = addColoredLetter(newGoalWordText, e.getOldChar(), "Green");
                    newUserWordText = addColoredLetter(newUserWordText, e.getNewChar(), "Green");
                    System.out.println(newGoalWordText + "G old character" + e.getOldChar());
                }
            }
        }
        else
        {
            newUserWordText += currentword;
            System.out.println(currentword);
        }
        
        formattedUserWords = newUserWordText + " ";
        formattedGoalWords = newGoalWordText + " ";
        currentword = "";
        
        for(int i = wordIndex + 1; i < goalWords.size(); i++)
        {
            newGoalWordText += " " + goalWords.get(i);
        }
        
        //inputdisplay.setText(newGoalWordText);
        outputdisplay.setText("<html>" + newUserWordText + "</html>_");
        //currentWordButton.setText("");
        for(int i = 0; i < 4; i++)
        {
            suggestionButtons.get(i).setEnabled(false);
            suggestionButtons.get(i).setText("");
        }
    }
    

    private String addColoredLetter(String destination, char letter, String colorName)
    {
        if(colorName != null && !colorName.equals(""))
        {
            
            //destination+= "<html><font color=" + colorName + ">" + letter + "</font></html>";
            destination += "<span style='color:" + colorName + ";'>" + letter + "</span>";
        }
        else
        {
            destination += letter;
        }
        
        return destination;
    }

    
	public void addKey(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		c.ipady = 30;
		c.ipadx = 30;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,0,0,0);
		container.add(component, c);
	}
	
	
	 
	class MouseListener extends MouseAdapter implements MouseMotionListener{
		boolean tracing;			// whether the input method is button clicking or tracing
		ArrayList<Key> tracelist;// a list to store all buttons on the trace
		ArrayList<String> keylist;
		Key curKey;
		
		MouseListener() throws FileNotFoundException{
			super();
			tracing = false;
			tracelist = new ArrayList<Key>();
			keylist = new ArrayList<String>();
			curKey = new Key("");
		}
		
		private void updateOutput (String theChar){
			//String theChar = theEventer.getText();
			String oldString = outputdisplay.getText();
			String newString;
			if (!oldString.startsWith("<h"))
				newString = oldString.substring(0, oldString.length()-1) + theChar + "_";
			else 
				newString = oldString.substring(6, oldString.length()-8) + theChar + "_";
			outputdisplay.setText("<html>" + newString + "</html>");
			//System.out.println("why not" + newString);
		}
		
				 
		private void recoverState(){
			//when mouse is released, tracing is ended. reset the letter state in the tmptlist
			//change status
			while (!tracelist.isEmpty()){
				Key e = tracelist.get(0);
				e.setBackground(Color.WHITE);
				e.LineList.clear();
				e.PointList.clear();
				e.repaint();
				tracelist.remove(0);	
			}
		}
		@Override
		public void mouseDragged(MouseEvent e){
			Key theEventer = (Key) e.getSource();
			Key tempKey = theEventer;
			if (tracing == false){//start tracing
				curKey = theEventer;
				tracelist.add(theEventer);
				tracing = true;
				System.out.println("Entering Mouse tracing mode");
                theEventer.PointList.add(e.getPoint());
			} else{
				Point2D p = e.getPoint();
				//System.out.println("current key" + curKey);
				//System.out.println("eventer" + theEventer);
				int x = (int)p.getX()- (curKey.getX() - theEventer.getX());
				int y = (int)p.getY()- (curKey.getY() - theEventer.getY());
				Point newPoint = new Point(x, y);
				System.out.println("Mouse position:(" + (curKey.getX() + x) + "," + (curKey.getY() + y) + "), In key " + curKey.getText() + ".");
				curKey.PointList.add(newPoint);
				curKey.repaint();
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if (tracing) {
				Key theEventer = (Key) e.getSource();
				curKey = theEventer;
				tracelist.add(theEventer);
				theEventer.setFocusPainted(true);
				theEventer.setOpaque(true);
				theEventer.setBackground(Color.green);
				
	            //start the mouse trace in this button
	            theEventer.PointList.add(e.getPoint());
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(tracing){
				Key theEventer = (Key) e.getSource();
				theEventer.setFocusPainted(true);
				theEventer.setOpaque(true);
				theEventer.setBackground(Color.white);
                theEventer.LineList.add(theEventer.PointList);
                theEventer.PointList = new ArrayList<Point>(); 
			}
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			Key theEventer = (Key) e.getSource();
			theEventer.setFocusPainted(true);
			theEventer.setOpaque(true);
			theEventer.setBackground(Color.CYAN);
			if (theEventer.getText()=="Clear")
			{
				clearWord();
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			Key theEventer = (Key) e.getSource();//e is the same source as pressed
			theEventer.setBackground(Color.WHITE);
			if (tracing == false) { //tap mode
				String theChar = theEventer.getText();
				updateOutput(theChar);
				if (theEventer.getText() != " ") {
					currentword += theEventer.getText();
					System.out.println("Input key " + currentword);
					ArrayList<WordResult> suggestions = wordMatcher.lookupPrefix(currentword, 4);
	                for (int i = 0; i < suggestionButtons.size(); i++) {
	                    if (i < suggestions.size()) {
	                        suggestionButtons.get(i).setEnabled(true);
	                        suggestionButtons.get(i).setText(suggestions.get(i).getWord());
	                    } else {
	                        suggestionButtons.get(i).setText("");
	                        suggestionButtons.get(i).setEnabled(false);
	                    }
	                }
				} else {
					//currentword = "";
					calculateLevensteinDistance();
				}
			} else { //trace mode
				Key spacekey = new Key(" ");
				WordSearchCharacter tempInput;
				tracing = false;
				//System.out.println("tracelist contents:" + tracelist);
				System.out.println("Tracing Completes. Clear all traces.");
				tracelist.add(spacekey);
				//currentword = "";
				for(Key c:tracelist)
				{
					String currentChar = c.getText();
					keylist.add(currentChar);
					System.out.println("check " + currentChar.charAt(0));
					if (currentChar.charAt(0) != ' ') {
						traceInput.add(new WordSearchCharacter(currentChar.charAt(0), 2));
						//traceInput.add(new WordSearchCharacter(currentChar.charAt(0), 1));
					}
				}
				
				
				tempInput = traceInput.get(0);
                tempInput.setNecessity(4);
                traceInput.set(0, tempInput);

                tempInput = traceInput.get(traceInput.size()-1);
                tempInput.setNecessity(4);
                traceInput.set(traceInput.size()-1, tempInput);
                
				System.out.println("suggestions" + traceInput.toString());
				ArrayList<WordResult> suggestions = wordMatcher.lookupUnknownPrefix(traceInput, 5);
                if(suggestions.size()  != 0)
                {
                	currentword = suggestions.get(0).getWord();
                	System.out.println("recent" + currentword);
                	updateOutput(currentword);
                }
                
                
                for (int i = 0; i < suggestionButtons.size(); i++) {
                    if (i < suggestions.size()-1) {
                        suggestionButtons.get(i).setEnabled(true);
                        suggestionButtons.get(i).setText(suggestions.get(i+1).getWord());
                    } else {
                        suggestionButtons.get(i).setText("");
                        suggestionButtons.get(i).setEnabled(false);
                    }
                }
				
                traceInput.clear();
				//System.out.println("keylist contents:" + keylist);
				recoverState();
			}
		}
	}
	 
	int errors;
	String rawGoal = "";
	String currentword = "";
	String newUserWordText = "";
	String formattedGoalWords = "";
	ArrayList<String> userWords = new ArrayList<String>();
    ArrayList<String> goalWords = new ArrayList<String>();
	int wordIndex = formattedUserWords.length();
	WordMatcher wordMatcher = new WordMatcher("./wordf.txt");
	ArrayList<WordSearchCharacter> traceInput = new ArrayList<WordSearchCharacter>();
	public static void main(String[] args) throws FileNotFoundException {
		Keyboard gui = new Keyboard(); 
	}
}
