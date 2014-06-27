package src;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Arena {

	static AgentController agentController;
	static AgentContainer container;
	static int numGladiator;
	static AgentController round[][];
	private static AgentController emperor;

	public static void main(String[] args) throws StaleProxyException {
		// create the main container, the arena
		createAcontainer();
		numGladiator = 6;
		// verify if the number of gladiators is a even number
		numGladiator = verifyNumGladiator(numGladiator);
		// create the combats and the gladiator in it
		createCombats();

	}

	private static void createCombats() throws StaleProxyException {
		round = new AgentController[numGladiator + 1][2];
		int gladiator = 0;
		emperor = container.createNewAgent("Emperor", "agents.Emperor", new Object[] {});
		emperor.start();

		for (int i = 1; i <= numGladiator / 2; i++) {
			round[i][0] = container.createNewAgent("Gladiator0" + Integer.toString(gladiator + 1),
					"agents.Gladiator", new Object[] { "Gladiator0" + Integer.toString(gladiator + 2) });
			round[i][0].start();
			round[i][1] = container.createNewAgent("Gladiator0" + Integer.toString(gladiator + 2),
					"agents.Gladiator", new Object[] { "Gladiator0" + Integer.toString(gladiator + 1) });
			round[i][1].start();
			gladiator = gladiator + 2;

		}
	}

	private static void createAcontainer() {
		Runtime rt = Runtime.instance();
		Properties p = new ExtendedProperties();
		p.setProperty("gui", "true");
		ProfileImpl pc = new ProfileImpl(p);
		container = rt.createMainContainer(pc);
	}

	private static int verifyNumGladiator(int numGladiator) {
		if (numGladiator % 2 == 0) {
			if (numGladiator == 0) {
				return 2;
			}
			else {
				return numGladiator;
			}
		}
		else {
			return numGladiator + 1;
		}
	}

}
