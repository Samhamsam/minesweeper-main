package de.htwg.se.minesweeper.aview;

import java.util.concurrent.CompletionStage;

import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.IncomingConnection;
import akka.http.javadsl.ServerBinding;
 import akka.http.javadsl.model.HttpMethods;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.ResponseEntity;
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
				.bind(ConnectHttp.toHost("localhost", 8080), materializer);

		final Function<HttpRequest, HttpResponse> requestHandler = new Function<HttpRequest, HttpResponse>() {
			  
			private static final long serialVersionUID = 1L;
			private final HttpResponse NOT_FOUND = HttpResponse.create().withStatus(404)
					.withEntity("Unknown resource!");

			@Override
			public HttpResponse apply(HttpRequest request) throws Exception {
				Uri uri = request.getUri();
				if (request.method() == HttpMethods.GET) {
					 
					if (uri.path().equals("/create")) {
						return HttpResponse.create().withEntity(  controller.jsonObj());
					
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
			// this is equivalent to
			// connection.handleWith(Flow.of(HttpRequest.class).map(requestHandler),
			// materializer);
		})).run(materializer);

	}

}
