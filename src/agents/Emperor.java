package agents;


import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Emperor extends Agent{
	
	@SuppressWarnings("unused")
	private Object[] args;
	private static final long serialVersionUID = 1L;
	
	protected void setup(){
		args = getArguments();
		
		addBehaviour(new OneShotBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				System.out.println("Fight or die! Get your weapons and start the action!\n");
			}
		});
		
		addBehaviour(new CyclicBehaviour() {
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				ACLMessage msgReceived = receive();
				if(msgReceived != null){
					System.out.println("I the "+this.getAgent().getLocalName()+" pronounce: "+msgReceived.getContent()+" is the winner and"
							+ msgReceived.getLanguage()+" is the loser!\n");
				}
				
			}
		});
	}
}
	


