package com.spacetrader.service.pilot;

public class Pilot {
	int skill;
	
	public Pilot() {
	}
	
	public void initialize(){
		setSkill(10);
	}

	public int getSkill() {
		return skill;
	}

	protected void setSkill(int skill) {
		this.skill = skill;
	}

	
}
