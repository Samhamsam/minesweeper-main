package de.htwg.se.minesweeper;

import de.htwg.se.minesweeper.aview.AkkaHTTP;
import de.htwg.se.minesweeper.aview.gui.GUI;
import de.htwg.se.minesweeper.aview.tui.TUI;
import de.htwg.se.minesweeper.controller.IAkkaController;
import de.htwg.se.minesweeper.controller.impl.AkkaController;

import java.io.IOException;
import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Injector;

import akka.actor.AbstractActor;
import akka.actor.Props;

 

public final class Minesweeper {
	// test push
	private static Scanner scanner;
	private TUI tui;
 	protected IAkkaController controller;
	private static Minesweeper instance = null;
	private static AkkaHTTP akkaHTTP;
	
	private Minesweeper() throws IOException {
		 Injector inject = Guice.createInjector(new MinesweeperModule());
		 controller = inject.getInstance(IAkkaController.class);
 	//	controller = new Controller();
		 
		tui = new TUI(controller);
		 new GUI(controller);
		 if (controller instanceof IAkkaController) {
			 akkaHTTP = new AkkaHTTP(controller);
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
