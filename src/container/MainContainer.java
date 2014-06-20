package container;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class MainContainer {

	public static void main(String[] args) {
		createAcontainer();
	}

	private static void createAcontainer() {
		Runtime rt = Runtime.instance();
		Properties p = new ExtendedProperties();
		p.setProperty("gui", "true");
		ProfileImpl pc = new ProfileImpl(p);
		AgentContainer container = rt.createMainContainer(pc);
		
		createAnAgent(container);
	}

	private static void createAnAgent(AgentContainer container){
		try {
			int numGladiator = 0;
			numGladiator = verifyNumGladiator(numGladiator);
			
			for (int i = 0; i < numGladiator; i++) {
				AgentController agentController = container.createNewAgent("GladiatorNum0"+Integer.toString(i+1), "agents.Gladiator", new Object[]{});
				agentController.start();
			}
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static int verifyNumGladiator(int numGladiator) {
		if(numGladiator % 2 == 0){
			if(numGladiator == 0)
				return 2;
			else
				return numGladiator;
		}else
			return numGladiator + 1;
	}

}
