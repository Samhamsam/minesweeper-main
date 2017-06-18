package de.htwg.se.minesweeper.controller.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;

import org.apache.logging.log4j.core.appender.mom.kafka.KafkaAppender;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.inject.Inject;

import de.htwg.se.minesweeper.controller.IAkkaController;
import de.htwg.se.minesweeper.model.Cell;
import de.htwg.se.minesweeper.persistence.IGridDao;

public class AkkaController extends Controller implements IAkkaController {

	IAkkaController c;
	private Set<IGridDao> allOfThem;
	@Inject
	public AkkaController(Set<IGridDao> allOfThem, IAkkaController c) throws IOException {
		//super(allOfThem);
		this.c = c;
	}

	@Override
	public List<JsonObject> jsonObj() {
		// better to get every one as json object
		JsonObject jsonObjectGrid = null;
		JsonObject jsonObjectState = null;
		JsonObject jsonObjectPoint = null;
		JsonObject jsonObjectCell = null;

		List<JsonObject> all = new ArrayList<>();

		jsonObjectState = Json.createObjectBuilder().add("State", c.getState().toString()).build(); // or .add() 
				
		jsonObjectPoint =  Json.createObjectBuilder().add("Points", "").build();

		jsonObjectGrid  = Json.createObjectBuilder().add("Grid",
						Json.createObjectBuilder().add("numberOfRows", c.getGrid().getNumberOfRows())
								.add("numberOfColumns", c.getGrid().getNumberOfColumns())
								.add("numberOfMines", c.getGrid().getNumberOfMines()))
				.build();
		all.add(jsonObjectState);
		all.add(jsonObjectPoint);
		all.add(jsonObjectGrid);
		
		for (Cell cell : c.getGrid().getCells()) {

			jsonObjectCell = Json.createObjectBuilder()
					.add("Cell", Json.createObjectBuilder()
							.add("position",
									Json.createObjectBuilder().add("row", cell.getPosition().getRow()).add("col",
											cell.getPosition().getCol()))
							.add("isFlagged", cell.isFlagged()).add("hasMine", cell.hasMine())
							.add("isRevealed", cell.isRevealed()).add("surroundingMines", cell.getSurroundingMines()))

					.build();
		
			all.add(jsonObjectCell);
		}
	
		
		
		return  all;
	}

	

}
