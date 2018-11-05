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
		emergencies.add(new Emergency("Appendicitis", 7, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Heart attack", 8, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Panic attack and Anxiety", 4, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Trampling", 8, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Epileptic attack", 6, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Stroke", 9, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Anaphylactic shock", 8, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Fainting", 3, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Dismemberment", 6, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Poisoning", 8, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Intoxication", 2, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Overdose", 9, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Cardiac arrest", 10, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Split arm", 2, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Starting back", 3, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Broken limb", 3, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Break pulse", 2, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Fall of person", 6, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Suicide attempt", 9, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Labor", 6, EmergencyUnit.AMBULANCE));//Parto!
		emergencies.add(new Emergency("Electocutaneous", 8, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Broken limb", 3, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Hit and run", 1, EmergencyUnit.AMBULANCE, EmergencyUnit.POLICE));//don't know
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
