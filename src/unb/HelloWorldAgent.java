package unb;

import jade.core.Agent;

public class HelloWorldAgent extends Agent {

	private static final long serialVersionUID = 1L;

	protected void setup() {
		System.out.println("-------------------------------------------------");
		System.out.println("Hello World!");
		System.out.println("My name: " + getLocalName());
		System.out.println("-------------------------------------------------");
	}

}
