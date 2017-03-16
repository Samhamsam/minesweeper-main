package de.htwg.se.minesweeper.aview.gui;

import de.htwg.se.minesweeper.controller.IController;

import javax.swing.*;
import java.awt.*;


public class GUISettings {

    Frame settingsFrame;

    private int maximumCR = 40;
    private int maximumB = 100;
    private int minimum = 5;
    private int majorSpacing = 5;
    private int minorSpacing = 1;
    private int initialValueColumnAndRow;
    private int initialValueMines;

    private int newRowColumn;
    private int newMines;

    private IController controller;
    static JSlider rowColSlider = new JSlider(JSlider.HORIZONTAL);
    static JSlider mineSlider = new JSlider(JSlider.HORIZONTAL);

    public GUISettings(int initialValueColumnAndRow, int initialValueMines, IController controller, Frame settingsFrame) {
        this.controller = controller;
        this.settingsFrame = settingsFrame;
        this.initialValueColumnAndRow = initialValueColumnAndRow;
        this.initialValueMines = initialValueMines;
    }

    public void run() {
        rowColSlider.setMaximum(maximumCR);
        rowColSlider.setMinimum(minimum);
        rowColSlider.setValue(initialValueColumnAndRow);
        rowColSlider.setMajorTickSpacing(majorSpacing);
        rowColSlider.setMinorTickSpacing(minorSpacing);
        rowColSlider.setSnapToTicks(true);
        rowColSlider.setPaintTicks(true);
        rowColSlider.setPaintLabels(true);

        mineSlider.setMaximum(maximumB);
        mineSlider.setMinimum(minimum);
        mineSlider.setValue(initialValueMines);
        mineSlider.setMajorTickSpacing(majorSpacing);
        mineSlider.setMinorTickSpacing(minorSpacing);
        mineSlider.setSnapToTicks(true);
        mineSlider.setPaintTicks(true);
        mineSlider.setPaintLabels(true);

        UIManager.put("OptionPane.minimumSize", new Dimension(500, 100));
        Object[] complexMsg = {"Set number row/column", rowColSlider, "Set number mines", mineSlider};

        int result = JOptionPane.showConfirmDialog(settingsFrame, complexMsg,
                "Settings", JOptionPane.OK_CANCEL_OPTION);

        newRowColumn = rowColSlider.getValue();
        newMines = mineSlider.getValue();
        if (result == JOptionPane.OK_OPTION) {
            setController();
        }

    }


    private void setController() {
        String answer = "c," + newRowColumn + "," + newMines;
        controller.commitNewSettingsAndRestart(newRowColumn, newMines);
    }

}