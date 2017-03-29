package de.htwg.se.minesweeper;

import de.htwg.se.minesweeper.aview.gui.GUI;
import de.htwg.se.minesweeper.aview.tui.TUI;
import de.htwg.se.minesweeper.controller.IController;
import de.htwg.se.minesweeper.controller.impl.Controller;

import java.util.Scanner;


public final class Minesweeper {
// test push
    private static Scanner scanner;
    private TUI tui;
    private GUI gui;
    protected IController controller;
    private static Minesweeper instance = null;

    private Minesweeper() {
        // Injector inject = Guice.createInjector();
        //controller = inject.getInstance(IController.class);
        // TODO Mark: juice
        controller = new Controller();

        tui = new TUI(controller);
        gui = new GUI(controller);
        tui.printTUI();
    }

    public static Minesweeper getInstance() {
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

    public static void main(final String[] args) {

        Minesweeper game = Minesweeper.getInstance();

        boolean loop = true;
        scanner = new Scanner(System.in);

        while (loop) {
            loop = game.getTUI().processInput(scanner.next());
        }
    }

}
