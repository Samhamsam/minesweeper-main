package de.htwg.se.minesweeper;

import java.io.IOException;

import akka.actor.*;

public class Minesweeper extends AbstractActor{
	// Akka actor system
	ActorSystem system = ActorSystem.create("minesweeper");

	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(final String[] args) throws IOException {
		ActorSystem system = ActorSystem.create("testsystem");
		final ActorRef mainActor = system.actorOf(MinesweeperActorStart.props(), "mainActor");
		mainActor.tell("start", mainActor);
	}
	
	
}

