package de.htwg.se.minesweeper.controller;

import javax.json.JsonObject;

import akka.http.javadsl.model.ResponseEntity;

public interface IAkkaController extends IController {

	String jsonObj();
}
