package emergency;

import java.util.ArrayList;

public class EmergencyList {
	private ArrayList<Emergency> emergencies;
	private EmergencyList instance;

	public EmergencyList getInstance() {
		if (this.instance == null) {
			this.instance = new EmergencyList();
		}

		return this.instance;
	}

	private EmergencyList() {
		emergencies = new ArrayList<Emergency>();
		addAmbulanceEmergencies();
		addPoliceEmergencies();
		addFirefighterEmergencies();
	}

	private void addAmbulanceEmergencies() {
		emergencies.add(new Emergency("Appendicitis", 0, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Heart attack", 0, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Epileptic attack", 0, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Stroke", 0, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Anaphylactic shock", 0, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Dismemberment", 0, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Poisoning", 0, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Intoxication", 0, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Overdose", 0, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Cardiac arrest", 0, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Broken limb", 0, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Labor", 0, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Suicide attempt", 0, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Hit and run", 0, EmergencyUnit.AMBULANCE, EmergencyUnit.POLICE));
	}

	private void addPoliceEmergencies() {
		emergencies.add(new Emergency("Bomb threat", 0, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Bank robbery", 0, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Cat burglar", 0, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Mugging", 0, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Carjacking", 0, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Lost person", 0, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Missing person", 0, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Kidnapping", 0, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Brawl", 0, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Hostage situation", 0, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Shootout", 0, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Assault", 0, EmergencyUnit.POLICE, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Attempted murder", 0, EmergencyUnit.POLICE, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Attempted rape", 0, EmergencyUnit.POLICE, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Terrorist attack", 0, EmergencyUnit.POLICE, EmergencyUnit.AMBULANCE, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Crumbling building", 0, EmergencyUnit.POLICE, EmergencyUnit.AMBULANCE, EmergencyUnit.FIREFIGHTER));
	}

	private void addFirefighterEmergencies() {
		emergencies.add(new Emergency("Electric accident", 0, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Flooding", 0, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Industrial explosion", 0, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Cat stuck in tree", 0, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Domestic fire", 0, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Forest fire", 0, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Industrial fire", 0, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Lost at sea", 0, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Caved in", 0, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Stuck in elevator", 0, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Drowning", 0, EmergencyUnit.FIREFIGHTER, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Burning", 0, EmergencyUnit.FIREFIGHTER, EmergencyUnit.AMBULANCE));
	}

}
