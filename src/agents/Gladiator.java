package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Random;

public class Gladiator extends Agent {

	private static final long serialVersionUID = 1L;

	private ACLMessage msgSent;
	private ACLMessage emperorMsg;
	private Object[] args;
	private Random rand = new Random();
	private String weapon = getWeaponRandomly();
	private int skillPoints = rand.nextInt((7 - 3) + 1) + 3;
	private int finalSkillPoints;
	private int healthPoints;

	private String getWeaponRandomly() {
		int weaponNumber = rand.nextInt((5 - 1) + 1) + 1;

		switch (weaponNumber) {
			case 1:
				return "Dagger";
			case 2:
				return "Spear";
			case 3:
				return "Sword";
			case 4:
				return "Axe";
			case 5:
				return "Mace";
			default:
				break;
		}
		return "Dagger";
	}

	private int calculateBonusPoint(String opponentsWeapon) {
		// advantage[ownWeapon][advantage][disadvantage]
		final String advantage[][][] = { { { "Axe" }, { "Dagger", "Mace" }, { "Sword", "Spear" } },
				{ { "Mace" }, { "Sword", "Spear" }, { "Dagger", "Axe" } },
				{ { "Sword" }, { "Dagger", "Axe" }, { "Mace", "Spear" } },
				{ { "Spear" }, { "Sword", "Axe" }, { "Dagger", "Mace" } },
				{ { "Dagger" }, { "Spear", "Mace" }, { "Sword", "Axe" } } };
		for (int i = 0; i < 5; i++) {
			if (this.getWeapon().equals(advantage[i][0][0])) {
				// advantage
				if (opponentsWeapon.equals(advantage[i][1][0]) || opponentsWeapon.equals(advantage[i][1][1])) {
					return getSkillPoints() + 3;
				}
				// disadvantage
				if (opponentsWeapon.equals(advantage[i][2][0]) || opponentsWeapon.equals(advantage[i][2][1])) {
					return getSkillPoints();
				}
			}
		}
		return getSkillPoints();
	}

	public int getSkillPoints() {
		return skillPoints;
	}

	public void setSkillPoints(int skillPoints) {
		this.skillPoints = skillPoints;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

	protected void setup() {
		args = getArguments();

		sendMessage();

		addBehaviour(new OneShotBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				System.out.println("Created " + this.getAgent().getLocalName() + " skill:" + skillPoints
						+ " weapon: " + weapon + "\n");
			}
		});

		addBehaviour(new TickerBehaviour(this, 500) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onTick() {

				ACLMessage msgReceived = receive();

				if (msgReceived != null && msgReceived.getContent().equals("Ready to fight!")) {

					if (msgSent.getContent().equals("You die!")) {

						emperorMsg = new ACLMessage(ACLMessage.INFORM);
						emperorMsg.addReceiver(new AID("Emperor", AID.ISLOCALNAME));
						emperorMsg.setContent(this.getAgent().getLocalName());
						emperorMsg.setLanguage(msgReceived.getSender().getLocalName());
						send(emperorMsg);
						stop();
					}
					if (msgReceived.getContent().equals("Ready to fight!")
							&& Integer.parseInt(msgSent.getProtocol()) > 0) {
						fight(msgReceived);
					}
				}
				else {

					this.myAgent.doDelete();
				}
			}

			private void fight(ACLMessage msgReceived) {
				healthPoints = Integer.parseInt(msgSent.getProtocol());
				// Print if there is any bonus point to give for the Gladiator
				if (healthPoints == 100 && calculateBonusPoint(msgReceived.getLanguage()) != skillPoints) {
					System.out.println(this.getAgent().getLocalName() + " recieves 3 bonus points because "
							+ weapon + " has advantage on " + msgReceived.getLanguage() + "\n");
				}

				finalSkillPoints = calculateBonusPoint(msgReceived.getLanguage());
				msgSent.setOntology(finalSkillPoints + "");
				send(msgSent);

				if (healthPoints > 0 && Integer.parseInt(msgReceived.getProtocol()) >= 0) {
					// If randomly get a 8 the opponent dodges the hit
					if ((rand.nextInt((10 - 5) + 1) + 5) == 8) {
						System.out.println(this.getAgent().getLocalName() + " you are not lucky today! "
								+ msgReceived.getSender().getLocalName() + " dodges your hit!");
						System.out.println(msgReceived.getSender().getLocalName() + " HP: " + healthPoints + "\n");
					}
					// Else damage the opponent
					else {
						System.out.println(this.getAgent().getLocalName() + " causes " + msgSent.getOntology()
								+ " points of damage on " + msgReceived.getSender().getLocalName() + "");
						healthPoints = healthPoints - Integer.parseInt(msgSent.getOntology());
						// If the gladiator has damaged the opponent enough to kill him, the agent says a final word
						if (healthPoints <= 0) {
							System.out.println(this.getAgent().getLocalName() + " says: you are about to die "
									+ msgReceived.getSender().getLocalName() + "\n");
							msgSent.setContent("You die!");
							msgSent.setProtocol(healthPoints + "");

						}
						// Else the fight continuous
						else {
							System.out.println(msgReceived.getSender().getLocalName() + " HP: " + healthPoints
									+ "\n");
							msgSent.setProtocol(healthPoints + "");
						}
					}
				}
			}
		});

	}

	private void sendMessage() {
		msgSent = new ACLMessage(ACLMessage.INFORM);
		msgSent.addReceiver(new AID(args[0].toString(), AID.ISLOCALNAME));
		msgSent.setContent("Ready to fight!");
		msgSent.setLanguage(this.getWeapon());
		msgSent.setProtocol("100");
		msgSent.setOntology(this.getSkillPoints() + "");
		send(msgSent);
	}

	protected void takeDown() {
		System.out.println(this.getLocalName() + " is officially dead!");
	}
}
