package de.htwg.se.minesweeper.controller;

import java.util.List;

import javax.json.JsonObject;

import akka.http.javadsl.model.ResponseEntity;

public interface IAkkaController extends IController {

	List<JsonObject> jsonObj();
}
