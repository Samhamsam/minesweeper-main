package de.htwg.se.minesweeper;

import de.htwg.se.minesweeper.aview.gui.GUI;
import de.htwg.se.minesweeper.aview.tui.TUI;
import de.htwg.se.minesweeper.controller.IController;
 
import java.io.IOException;
import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Injector;

public final class Minesweeper {
	// test push
	private static Scanner scanner;
	private TUI tui;
 	protected IController controller;
	private static Minesweeper instance = null;

	private Minesweeper() throws IOException {
		 Injector inject = Guice.createInjector(new MinesweeperModule());
		 controller = inject.getInstance(IController.class);
 	//	controller = new Controller();

		tui = new TUI(controller);
		 new GUI(controller);
		tui.printTUI();
	}

	public static Minesweeper getInstance() throws IOException {
		if (instance == null) {
			instance = new Minesweeper();
		}
		return instance;
	}

	public TUI getTUI() {
		return tui;
	}

	public IController getController() {
		return controller;
	}

	public static void main(final String[] args) throws IOException {

		Minesweeper game = Minesweeper.getInstance();

		boolean loop = true;
		scanner = new Scanner(System.in);

		while (loop) {
			loop = game.getTUI().processInput(scanner.next());
		}
	}

}
