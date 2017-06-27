package de.htwg.se.minesweeper.aview.gui;

import de.htwg.se.minesweeper.controller.IAkkaController;
import de.htwg.se.minesweeper.controller.IController;
import de.htwg.se.minesweeper.designpattern.observer.Event;
import de.htwg.se.minesweeper.designpattern.observer.IObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI extends JFrame implements ActionListener, IObserver, MouseListener {
	private static final long serialVersionUID = 1L;
	private JButton[][] gridAsJButtons;
	private IAkkaController controller;
	private GUISettings guiSettings;

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
	JMenuItem db4o;

	public GUI(IAkkaController controller) {
		this.controller = controller;
		controller.addObserver(this);
		mainFrame = new JFrame("Minesweeper");
		initJFrame();
	}

	private void initJFrame() {
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
		hibernate = new JMenuItem("use Hibernate");
		db4o = new JMenuItem("use DB4O");
		settingsmenu = new JMenuItem("Settings");
		menu.add(newGame);
		menu.add(settingsmenu);
		menu.add(quit);

		menuQuestion.add(help);
		db.add(loadToDB);
		db.add(saveToDB);
		selectDB.add(couchDB);
		selectDB.add(hibernate);
		selectDB.add(db4o);
		newGame.addActionListener(this);
		quit.addActionListener(this);
		help.addActionListener(this);
		settingsmenu.addActionListener(this);
		loadToDB.addActionListener(this);
		saveToDB.addActionListener(this);
		couchDB.addActionListener(this);
		hibernate.addActionListener(this);
		db4o.addActionListener(this);
		mainFrame.setJMenuBar(menuBar);

		mainFrame.setLayout(
				new GridLayout(controller.getGrid().getNumberOfRows(), controller.getGrid().getNumberOfColumns()));
		buildGameField();
		mainFrame.setSize(30, 30);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	private void buildGameField() {
		gridAsJButtons = new JButton[controller.getGrid().getNumberOfRows()][controller.getGrid().getNumberOfColumns()];
		buildButtons();

	}

	private void buildButtons() {
		for (int row = 0; row < controller.getGrid().getNumberOfRows(); row++) {
			for (int col = 0; col < controller.getGrid().getNumberOfColumns(); col++) {
				gridAsJButtons[row][col] = new JButton(controller.getGrid().getCellAt(row, col).toString());
				mainFrame.add(gridAsJButtons[row][col]);
				gridAsJButtons[row][col].setBackground(Color.GRAY);
				gridAsJButtons[row][col].addMouseListener(this);
				gridAsJButtons[row][col].setPreferredSize(new Dimension(50, 50));
			}
		}
	}

	private void updateGameField() {
		for (int row = 0; row < controller.getGrid().getNumberOfRows(); row++) {
			for (int col = 0; col < controller.getGrid().getNumberOfColumns(); col++) {
				setJButtonText(controller.getGrid().getCellAt(row, col).toString(), row, col);

				if ("0".equals(getJButtonText(row, col))) {
					setJButtonColor(row, col, Color.GREEN);
				} else if ("x".equals(getJButtonText(row, col))) {
					setJButtonColor(row, col, Color.GRAY);
				} else if ("b".equals(getJButtonText(row, col)) || "f".equals(getJButtonText(row, col))) {
					setJButtonColor(row, col, Color.RED);
					if ("b".equals(getJButtonText(row, col)))
						controller.setStateAndNotifyObservers(IController.State.GAME_LOST);
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
				new GridLayout(controller.getGrid().getNumberOfRows(), controller.getGrid().getNumberOfColumns()));

		buildGameField();

		for (int row = 0; row < controller.getGrid().getNumberOfRows(); row++) {
			for (int col = 0; col < controller.getGrid().getNumberOfColumns(); col++) {
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
			controller.startNewGame();
		} else if (e.getSource() == loadToDB) {
			controller.loadFromDB();
		} else if (e.getSource() == saveToDB) {
			controller.saveToDB();
		} else if (e.getSource() == couchDB) {
			controller.chooseDB(0);
		}

		else if (e.getSource() == hibernate) {
			controller.chooseDB(1);
		} else if (e.getSource() == db4o) {
			controller.chooseDB(2);
		}

		else if (e.getSource() == quit) {
			controller.quit();
		} else if (e.getSource() == help) {
			controller.setStateAndNotifyObservers(IController.State.HELP_TEXT);
		} else if (e.getSource() == settingsmenu) {
			showSettings();
		}

	}

	@Override
	public void update(Event e) {
		final IController.State state = controller.getState();

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
			messageDialog("You Won! " + controller.getElapsedTimeSeconds() + " Points!");
			break;

		case HELP_TEXT:
			messageDialog(controller.getHelpText());
			break;

		case CHANGE_SETTINGS_ACTIVATED:
			showSettings();
			break;

		case CHANGE_SETTINGS_SUCCESS:
			break;
		case LOAD_GAME:

			setEnableButtons(true);
			break;
		}
		if (controller.getState() != IController.State.GAME_LOST)
			updateGameField();
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
				controller.startNewGame();

			} else if (e.getSource() == quit) {
				controller.quit();

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
		for (int row = 0; row < controller.getGrid().getNumberOfRows(); row++) {
			for (int col = 0; col < controller.getGrid().getNumberOfColumns(); col++) {
				Object buttonText = e.getSource();
				if (buttonText.equals(gridAsJButtons[row][col])) {
					controller.toggleFlag(row, col);

				}

			}
		}
	}

	private void revealCell(MouseEvent e) {
		Object sourceButton = e.getSource();
		for (int row = 0; row < controller.getGrid().getNumberOfRows(); row++) {
			for (int col = 0; col < controller.getGrid().getNumberOfColumns(); col++) {
				if (sourceButton.equals(gridAsJButtons[row][col])) {
					controller.revealCell(row, col);
				}

			}
		}
	}

	private void showSettings() {
		guiSettings = new GUISettings(controller.getGrid().getNumberOfColumns(),
				controller.getGrid().getNumberOfMines(), controller, mainFrame);
		guiSettings.run();
	}

	private void applySettings() {
		SwingUtilities.updateComponentTreeUI(mainFrame);
	}

}
