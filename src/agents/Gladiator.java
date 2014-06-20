package agents;

import java.util.Random;

import jade.core.Agent;

public class Gladiator extends Agent{
	Random rand = new Random();
	
	private int healthPoints = 100;
	private int weapon = rand.nextInt((5 - 1) + 1) + 1;
	private int skillPoints = rand.nextInt((7 - 2) + 1) + 2;
	private int finalSkillPoints = calculateBonusPoint();
	/*
	 * Where:
	 * 		weapon == 1 == Dagger
	 * 		weapon == 2 == Spear
	 * 		weapon == 3 == Sword
	 * 		weapon == 4 == Axe
	 * 		weapon == 5 == Mace
	 */
	
	public int getHealthPoints() {
		return healthPoints;
	}

	private int calculateBonusPoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}

	public int getSkillPoints() {
		return skillPoints;
	}

	public void setSkillPoints(int skillPoints) {
		this.skillPoints = skillPoints;
	}

	public int getWeapon() {
		return weapon;
	}

	public void setWeapon(int weapon) {
		this.weapon = weapon;
	}

	protected void setup(){
		System.out.println("Gladiator "+this.getAID().getName()+" kill:"+this.getSkillPoints()+" weapon:"+this.getWeapon());
	}

}
