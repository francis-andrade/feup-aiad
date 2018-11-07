package agents;

import java.io.IOException;
import java.io.Serializable;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import messages.CallEmergency;

public abstract class MainAgent extends Agent {
	public void sendMessage(String receiver, CallEmergency callEmergency) {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		try {
			msg.setContentObject(callEmergency);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("deprecation")
		AID dest = new AID(receiver, AID.ISLOCALNAME);
		msg.addReceiver(dest);
		send(msg);
	}
}
