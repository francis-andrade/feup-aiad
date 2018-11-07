package agents;

import java.io.IOException;
import java.io.Serializable;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public abstract class MainAgent extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void sendMessage(String receiver, Object contentObject) {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		try {
			msg.setContentObject((Serializable) contentObject);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AID dest = new AID(receiver, AID.ISLOCALNAME);
		msg.addReceiver(dest);
		send(msg);
	}
}
