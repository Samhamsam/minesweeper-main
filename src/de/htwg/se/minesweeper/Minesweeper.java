package de.htwg.se.minesweeper;

import de.htwg.se.minesweeper.aview.AkkaHTTP;
import de.htwg.se.minesweeper.aview.tui.TUI;
import de.htwg.se.minesweeper.controller.IAkkaController;

import java.io.IOException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;

public final class Minesweeper {

	private static final Logger LOGGER = LogManager.getRootLogger();
	// test push
	private static Scanner scanner;
	private TUI tui;
	protected IAkkaController controller;
	private static Minesweeper instance = null;
	Injector inject;

	private Minesweeper() {
		try {
			inject = Guice.createInjector(new MinesweeperModule());
			controller = inject.getInstance(IAkkaController.class);
		}

		// controller = new Controller();
		catch (Exception e) {
			LOGGER.error("Error: Probably your VPN is not on!" + e);
			System.exit(1);
		}

		tui = new TUI(controller);
		// new GUI(controller);
		if (controller instanceof IAkkaController) {
			new AkkaHTTP(controller);
		}
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

	public IAkkaController getController() {
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
