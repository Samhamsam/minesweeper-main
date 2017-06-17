package de.htwg.se.minesweeper;

import de.htwg.se.minesweeper.aview.AkkaHTTP;
import de.htwg.se.minesweeper.aview.gui.GUI;
import de.htwg.se.minesweeper.aview.tui.TUI;
import de.htwg.se.minesweeper.controller.IAkkaController;
import de.htwg.se.minesweeper.controller.impl.AkkaController;
import de.htwg.se.minesweeper.controller.impl.Controller;

import java.io.IOException;
import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Injector;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

 

public final class MinesweeperActorStart extends AbstractActor {
	
	@Override
	public void preStart() {
		ActorRef controller = getContext().actorOf(Props.create(Controller.class),"controller");
		ActorRef tui = getContext().actorOf(Props.create(TUI.class),"tui");
		ActorRef gui = getContext().actorOf(Props.create(GUI.class),"gui");
		
		boolean loop = true;
		scanner = new Scanner(System.in);

		while (loop) {
			loop = game.getTUI().processInput(scanner.next());
		}
	}
	// test push
	private static Scanner scanner;
	private TUI tui;
 	protected IAkkaController controller;
	private static MinesweeperActorStart instance = null;
	private static AkkaHTTP akkaHTTP;
	
/*	private MinesweeperActorStart() throws IOException {
		 Injector inject = Guice.createInjector(new MinesweeperModule());
		 controller = inject.getInstance(IAkkaController.class);
 	//	controller = new Controller();
		 
		tui = new TUI(controller);
		 new GUI(controller);
		 if (controller instanceof IAkkaController) {
			 akkaHTTP = new AkkaHTTP(controller);
			}
		tui.printTUI();
	}*/

	public static MinesweeperActorStart getInstance() throws IOException {
		if (instance == null) {
			instance = new MinesweeperActorStart();
		}
		return instance;
	}

	public TUI getTUI() {
		return tui;
	}

	public IAkkaController getController() {
		return controller;
	}

/*	public static void main(final String[] args) throws IOException {

		Minesweeper game = Minesweeper.getInstance();

		boolean loop = true;
		scanner = new Scanner(System.in);

		while (loop) {
			loop = game.getTUI().processInput(scanner.next());
		}
	}*/

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.matchAny(s->{

				})
				.build();
	}
	
	static Props props(){
		return Props.create(MinesweeperActorStart.class);
	}

}