package de.htwg.se.minesweeper.aview.gui;

import de.htwg.se.minesweeper.aview.tui.TUI;
import de.htwg.se.minesweeper.controller.IAkkaController;
import de.htwg.se.minesweeper.controller.IController;
import de.htwg.se.minesweeper.controller.IController.State;
import de.htwg.se.minesweeper.controller.impl.messages.NewSettingRequest;
import de.htwg.se.minesweeper.controller.impl.messages.RevealCellRequest;
import de.htwg.se.minesweeper.controller.impl.messages.ScannerRequest;
import de.htwg.se.minesweeper.controller.impl.messages.SetFlagRequest;
import de.htwg.se.minesweeper.controller.impl.messages.UpdateRequest;
import de.htwg.se.minesweeper.model.Cell;
import de.htwg.se.minesweeper.model.Grid;

import javax.swing.*;

import com.google.inject.Stage;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

public class GUI extends AbstractActor implements ActionListener, MouseListener {
	private JButton[][] gridAsJButtons;
	JFrame mainFrame;
	JMenuBar menuBar;
	JMenu menu;
	JMenu menuQuestion;
	JMenuItem newGame;
	JMenuItem quit, settingsmenu, help;
	 
	JMenu db;
	JMenu selectDB;
	JMenuItem loadToDB;
	JMenuItem saveToDB;
	JMenuItem couchDB;
	JMenuItem hibernate;

	Grid grid;

	
	ActorRef controller;
	ActorRef guiSettings;
	
	private int numberOfRows;
	private int numberOfColumns;
	private int numberOfMines;
	


	public GUI(final ActorRef controller) throws InterruptedException {
		this.controller = controller;
		guiSettings = getContext().actorOf(Props.create(GUISettings.class,self()),"guisettings");
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(UpdateRequest.class, s->{
					setNumberOfRows(s.grid.getNumberOfRows());
					setNumberOfColumns(s.grid.getNumberOfColumns());
					setNumberOfMines(s.grid.getNumberOfMines());
					this.grid = s.grid;
					if(s.state == State.FIRST_START){
						mainFrame = new JFrame("Minesweeper");
						initJFrame();
					} else if(s.state == State.CHANGE_SETTINGS_SUCCESS){
						//repaint
						mainFrame.setVisible(false);
						mainFrame = new JFrame("Minesweeper");
						initJFrame();
						
					} else if(s.state == State.NEW_GAME){
						//repaint
						mainFrame.setVisible(false);
						mainFrame = new JFrame("Minesweeper");
						initJFrame();
						
					}
					updateImpl(s.state, s.elapsedTimeSeconds,s.getHelpText);
				})
				.match(NewSettingRequest.class, s->{
					controller.tell(new NewSettingRequest(s.numRowsAndColumns, s.numberOfMines, null), self());
				})
				
				.build();
	}

	private void initJFrame() throws InterruptedException {
		
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		menuQuestion = new JMenu("?");
		db = new JMenu("DB");
		selectDB = new JMenu("SelectDB");
		menuBar.add(menu);
		menuBar.add(menuQuestion);
		menuBar.add(db);
		menuBar.add(selectDB);
		newGame = new JMenuItem("New Game");
 		quit = new JMenuItem("Quit");
		help = new JMenuItem("Help");

		loadToDB = new JMenuItem("load from DB");
		saveToDB = new JMenuItem("Save to DB");
		couchDB = new JMenuItem("use CouchDB");
		hibernate = new JMenuItem("use hibernate");
		settingsmenu = new JMenuItem("Settings");
		menu.add(newGame);
 		menu.add(settingsmenu);
		menu.add(quit);

		menuQuestion.add(help);
		db.add(loadToDB);
		db.add(saveToDB);
		selectDB.add(couchDB);
		selectDB.add(hibernate);
		newGame.addActionListener(this);
 		quit.addActionListener(this);
		help.addActionListener(this);
		settingsmenu.addActionListener(this);
		loadToDB.addActionListener(this);
		saveToDB.addActionListener(this);
		couchDB.addActionListener(this);
		hibernate.addActionListener(this);
		mainFrame.setJMenuBar(menuBar);
		buildGameField();

		mainFrame.setLayout(
				new GridLayout(getNumberOfRows(), getNumberOfColumns()));

		mainFrame.setSize(30, 30);
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		
		mainFrame.setVisible(true);
		
		
	}

	private void buildGameField() {
		gridAsJButtons = new JButton[getNumberOfRows()][getNumberOfColumns()];
		buildButtonsImpl();

	}
	
	private void buildButtonsImpl() {
		
		for (int row = 0; row < getNumberOfRows(); row++) {
			for (int col = 0; col < getNumberOfColumns(); col++) {
				
				//controller.tell(new GetCellAtRequest(row, col, "buildButtonRequest"), self());
				gridAsJButtons[row][col] = new JButton();
				mainFrame.add(gridAsJButtons[row][col]);
				gridAsJButtons[row][col].setBackground(Color.GRAY);
				gridAsJButtons[row][col].addMouseListener(this);
				gridAsJButtons[row][col].setPreferredSize(new Dimension(50, 50));
			}
		}
	}

	private void updateGameField() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int col = 0; col < numberOfColumns; col++) {
				setJButtonText(this.grid.getCellAt(row, col).toString(), row, col);
				
				if ("0".equals(getJButtonText(row, col))) {
					setJButtonColor(row, col, Color.GREEN);
				} else if ("x".equals(getJButtonText(row, col))) {
					setJButtonColor(row, col, Color.GRAY);
				} else if ("b".equals(getJButtonText(row, col)) || "f".equals(getJButtonText(row, col))) {
					setJButtonColor(row, col, Color.RED);
					if ("b".equals(getJButtonText(row, col)))
						controller.tell(IController.State.GAME_LOST, self());
				} else {
					setJButtonColor(row, col, Color.WHITE);
				}
			}
		}
	}
	

	private void setEnableButtons(boolean status) {
		for (JButton[] buttons : gridAsJButtons) {
			for (JButton button : buttons) {
				mainFrame.getContentPane().remove(button);
			}
		}
		// Remove the button to start clean
		mainFrame.setLayout(
				new GridLayout(getNumberOfRows(), getNumberOfColumns()));

		buildGameField();

		for (int row = 0; row < getNumberOfRows(); row++) {
			for (int col = 0; col < getNumberOfColumns(); col++) {
				gridAsJButtons[col][row].setEnabled(status);
			}
		}
	}

	public void setJButtonColor(int i, int j, Color color) {
		gridAsJButtons[i][j].setBackground(color);
	}

	public void setJButtonText(String text, int i, int j) {
		gridAsJButtons[i][j].setText(text);
	}

	public String getJButtonText(int i, int j) {
		return gridAsJButtons[i][j].getText();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == newGame) {
			controller.tell(IController.State.NEW_GAME, self());
			//controller.startNewGame();
		} else if (e.getSource() == loadToDB) {
			controller.tell("loadDB", self());
			//controller.loadFromDB();
		}  else if (e.getSource() == saveToDB) {
			controller.tell("safeDB", self());
			//controller.saveToDB();
		}
		 else if (e.getSource() == couchDB) {
				//controller.chooseDB(0);
			}
		 
		 
		 else if (e.getSource() == hibernate) {
				//controller.chooseDB(1);
			}
		 
		 else if (e.getSource() == quit) {
			controller.tell(IController.State.EXIT_GAME, self());
			 //controller.quit();
		} else if (e.getSource() == help) {
			controller.tell(IController.State.HELP_TEXT, self());
			//controller.setStateAndNotifyObservers(IController.State.HELP_TEXT);
		} else if (e.getSource() == settingsmenu) {
			guiSettings.tell(new NewSettingRequest(getNumberOfColumns(), getNumberOfMines(), mainFrame), self());
			//showSettings();
		}

	}
	
	public void updateImpl(IController.State state, long time, String helpText) {
		if (state == IController.State.ERROR) {
			messageDialog("Some Error occured! Please Check it");

		}

		switch (state) {
		
		case NEW_GAME:
			setEnableButtons(true);
			break;

		case GAME_LOST:
			messageDialog("You Lost!");
			setEnableButtons(false);
			break;

		case GAME_WON:
			messageDialog("You Won! " + time + " Points!");
			break;

		case HELP_TEXT:
			messageDialog(helpText);
			break;

		case CHANGE_SETTINGS_ACTIVATED:
			//showSettings();
			
			break;

		case CHANGE_SETTINGS_SUCCESS:
			break;
		case LOAD_GAME:
			setEnableButtons(true);
			break;
		}
		if (state != IController.State.GAME_LOST){
			updateGameField();
		}
		applySettings();
	}

	private void messageDialog(String text) {
		Thread t = new Thread(() -> JOptionPane.showMessageDialog(mainFrame, text));
		t.start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Not needed
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON1) {

			if (e.getSource() == newGame) {
				//controller.startNewGame();
				controller.tell(IController.State.NEW_GAME, self());
			} else if (e.getSource() == quit) {
				//controller.quit();
				controller.tell(IController.State.EXIT_GAME, self());
			} else {
				revealCell(e);
			}
		}

		// set flag with right click
		else if (e.getButton() == MouseEvent.BUTTON3) {
			setFlag(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Not needed
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Not needed
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Not needed
	}

	private void setFlag(MouseEvent e) {
		for (int row = 0; row < getNumberOfRows(); row++) {
			for (int col = 0; col < getNumberOfColumns(); col++) {
				Object buttonText = e.getSource();
				if (buttonText.equals(gridAsJButtons[row][col])) {
					controller.tell(new SetFlagRequest(col, row),self());
				}

			}
		}
	}

	private void revealCell(MouseEvent e) {
		Object sourceButton = e.getSource();
		for (int row = 0; row < getNumberOfRows(); row++) {
			for (int col = 0; col < getNumberOfColumns(); col++) {
				if (sourceButton.equals(gridAsJButtons[row][col])) {
					controller.tell(new RevealCellRequest(col, row), self());
				}

			}
		}
	}

	private void applySettings() {
		SwingUtilities.updateComponentTreeUI(mainFrame);
	}
	
	
	
	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	public int getNumberOfMines() {
		return numberOfMines;
	}

	public void setNumberOfMines(int numberOfMines) {
		this.numberOfMines = numberOfMines;
	}


}
