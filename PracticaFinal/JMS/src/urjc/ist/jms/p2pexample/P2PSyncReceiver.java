package urjc.ist.jms.p2pexample;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Objects;

public class P2PSyncReceiver {
	
	private static final String factoryName = "Factoria1";
	private static final String queueName = "Cola1";

	public static void main(String args[]){
		try {
			InitialContext jndi = new InitialContext();
			QueueConnectionFactory factory = 
					(QueueConnectionFactory)jndi.lookup(factoryName);
			QueueConnection connection = factory.createQueueConnection();
			
			Queue queue = (Queue)jndi.lookup(queueName);
			QueueSession session = 
					connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			QueueReceiver receiver = session.createReceiver(queue);

			connection.start();
			System.err.println("Listening...");
			
			Message msg;

			while (!Thread.currentThread().isInterrupted()) {
				msg = receiver.receive();
				
				if ((msg != null) && (msg instanceof TextMessage)) {
					TextMessage m = (TextMessage)msg;
					
					if (Objects.equals(m.getText(), "CLOSE")){
						System.out.println("No more messages. Closing now!");
						break;
					} else {	
						System.out.println("Message received: " + m.getText());
					}
				} else {
					System.err.println("Message discarded, wrong format...");
				}
			}
			connection.close();   //closes the connection, the session and the receiver
		} catch (NamingException ex) {
			ex.printStackTrace();
		} catch (JMSException ex) {
			ex.printStackTrace();
		} 
	}
}
