package emergency;

import java.util.ArrayList;

public class EmergencyList {
	private static ArrayList<Emergency> emergencies;
	private static boolean emergenciesSet = false;

	public static void handleInstance() {
		if (! emergenciesSet) {
			emergencies = new ArrayList<Emergency>();
			addEmergencies();
		}	
	}

	

	private static void addEmergencies() {
		emergencies.add(new Emergency("Appendicitis", 5,5, EmergencyUnit.AMBULANCE)); //0
		emergencies.add(new Emergency("Heart attack", 8,2, EmergencyUnit.AMBULANCE)); //1
		emergencies.add(new Emergency("Panic attack and Anxiety", 4,15, EmergencyUnit.AMBULANCE)); //2
		emergencies.add(new Emergency("Trampling", 8,12, EmergencyUnit.AMBULANCE)); //3
		emergencies.add(new Emergency("Epileptic attack",6, 14, EmergencyUnit.AMBULANCE)); //4
		emergencies.add(new Emergency("Stroke", 9,1, EmergencyUnit.AMBULANCE)); //5
		emergencies.add(new Emergency("Anaphylactic shock", 8,1, EmergencyUnit.AMBULANCE)); //6
		emergencies.add(new Emergency("Fainting", 3,6, EmergencyUnit.AMBULANCE)); //7
		emergencies.add(new Emergency("Dismemberment", 6,8, EmergencyUnit.AMBULANCE)); //8
		emergencies.add(new Emergency("Poisoning", 8,2, EmergencyUnit.AMBULANCE)); //9
		emergencies.add(new Emergency("Intoxication", 2,4, EmergencyUnit.AMBULANCE)); //10
		emergencies.add(new Emergency("Overdose", 9,9, EmergencyUnit.AMBULANCE)); //11
		emergencies.add(new Emergency("Cardiac arrest", 10,3, EmergencyUnit.AMBULANCE)); //12
		emergencies.add(new Emergency("Broken arm", 2, 7, EmergencyUnit.AMBULANCE)); //13
		emergencies.add(new Emergency("Broken back", 3,12, EmergencyUnit.AMBULANCE)); //14
		emergencies.add(new Emergency("Broken leg", 3,6, EmergencyUnit.AMBULANCE)); //15
		emergencies.add(new Emergency("Broken pulse", 2,5,  EmergencyUnit.AMBULANCE)); //16
		emergencies.add(new Emergency("Fall of person", 5,40, EmergencyUnit.AMBULANCE)); //17
		emergencies.add(new Emergency("Suicide attempt", 9,50, EmergencyUnit.AMBULANCE)); //18
		emergencies.add(new Emergency("Labor", 5,69, EmergencyUnit.AMBULANCE));//Parto! 19
		emergencies.add(new Emergency("Hit car and run", 1,16, EmergencyUnit.POLICE));//don't know 20
		
		emergencies.add(new Emergency("Bomb threat", 8,29, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Bank robbery", 7,9, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Robbery theft", 7,13, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("swindle", 1,199, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Mugging", 4,11, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Carjacking", 5,27, EmergencyUnit.POLICE)); // pelos riscos que envolve, bater assaltos violento e o andar a alta velocidade a fugir a pol�cia e o risco que isso implica! 
		emergencies.add(new Emergency("Lost person", 1,18, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Missing person", 5,34, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Kidnapping", 8,59, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Brawl", 2,8, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Shootout", 8,13, EmergencyUnit.POLICE));
		emergencies.add(new Emergency("Assault", 6,15, EmergencyUnit.POLICE, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Attempted murder", 9,6, EmergencyUnit.POLICE, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Attempted rape", 6,19, EmergencyUnit.POLICE, EmergencyUnit.AMBULANCE));
		
		
		emergencies.add(new Emergency("Electric accident", 8,10, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Flooding", 5,77, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Industrial explosion", 10,53, EmergencyUnit.FIREFIGHTER));//j� explodiu.. s� se houver (risco) possibilidade de explodir mais
		emergencies.add(new Emergency("Cat stuck in tree", 1,100, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Domestic fire", 9,22, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Forest fire", 9,23, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Industrial urban fire", 9,20, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Lost in the sea", 9,45, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Stuck in a cave", 8,111, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Stuck in the elevator", 2,8, EmergencyUnit.FIREFIGHTER));
		emergencies.add(new Emergency("Drowning", 9,3, EmergencyUnit.FIREFIGHTER, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Burning", 8,9, EmergencyUnit.FIREFIGHTER, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Fall in a hole", 6,14, EmergencyUnit.FIREFIGHTER, EmergencyUnit.AMBULANCE));
		emergencies.add(new Emergency("Road accident", 6,12, EmergencyUnit.FIREFIGHTER, EmergencyUnit.AMBULANCE));
	}
	
	public static  ArrayList<Emergency> getEmergencies(){
		handleInstance();
		return emergencies;
	}
	
}
