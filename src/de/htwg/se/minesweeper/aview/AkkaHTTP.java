package de.htwg.se.minesweeper.aview;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import static j2html.TagCreator.*;
import javax.json.JsonObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.IncomingConnection;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpMethods;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Uri;

import akka.japi.function.Function;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import de.htwg.se.minesweeper.controller.IAkkaController;

public class AkkaHTTP {

	ActorSystem system = ActorSystem.create();
	final Materializer materializer = ActorMaterializer.create(system);
	private IAkkaController controller;

	public AkkaHTTP(IAkkaController controller) {
		this.controller = controller;

		Source<IncomingConnection, CompletionStage<ServerBinding>> serverSource = Http.get(system)
				.bind(ConnectHttp.toHost("0.0.0.0", 8080), materializer);

		final Function<HttpRequest, HttpResponse> requestHandler = new Function<HttpRequest, HttpResponse>() {

			private static final long serialVersionUID = 1L;
			private final HttpResponse NOT_FOUND = HttpResponse.create().withStatus(404)
					.withEntity("Unknown resource!");

			@Override
			public HttpResponse apply(HttpRequest request) throws Exception {
				Uri uri = request.getUri();
				List<JsonObject> json = controller.jsonObj();
				if (request.method() == HttpMethods.GET) {

					if (uri.path().equals("/current")) {

						return HttpResponse.create().withEntity(prettyJSON(json.toString()));

					} else if (uri.path().equals("/new")) {
						controller.startNewGame();
						return HttpResponse.create().withEntity(prettyJSON(json.toString()));

					} else if (uri.path().equals("/state")) {

						return HttpResponse.create().withEntity(prettyJSON(json.get(0).toString()));

					} else if (uri.path().equals("/grid")) {

						return HttpResponse.create().withEntity(prettyJSON(json.get(2).toString()));

					} else if (uri.path().equals("/cells")) {
						ArrayList<String> test = new ArrayList<>();
						for (int i = 3; i < json.size(); i++) {
							test.add(json.get(i).toString());
						}
						return HttpResponse.create().withEntity(prettyJSON(test.toString()));
					} else if (uri.path().equals("/point")) {

						return HttpResponse.create().withEntity(prettyJSON(json.get(1).toString()));

					} else if (uri.path().equals("/help")) {

						return HttpResponse.create().withEntity(controller.getHelpText().toString());

					} else if (uri.path().equals("/")) {

						return HttpResponse.create().withEntity(ContentTypes.TEXT_HTML_UTF8, htmlString());

					} else {
						return NOT_FOUND;
					}
				} else {
					return NOT_FOUND;
				}
			}
		};

		CompletionStage<ServerBinding> serverBindingFuture = serverSource.to(Sink.foreach(connection -> {
			System.out.println("Accepted new connection from " + connection.remoteAddress());

			connection.handleWithSyncHandler(requestHandler, materializer);

		})).run(materializer);

	}

	private static String prettyJSON(String resp) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(resp);

		return gson.toJson(je);
	}

	private String htmlString() {
		return "<html><body><h1>Hello!</h1><ul><li><p> Create new Game :</p><div><a href=new>click here </a></div></li><li><p>Get help :</p><div><a href=help> click here</a></div></li><li><p>Get current Game</p><div><a href=current>click here </a></div></li> <li><p>Get Cells infos</p><div><a href=cells>click here </a></div></li><li><p>Get points :</p><div><a href=point>click here </a></div></li><li><p>Get Grid infos</p><div><a href=grid> click here </a></div></li><li><p>Get Game State :</p><div><a href=state> click here</a></div></li></ul></body></html>";

	}
}
