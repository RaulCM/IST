package urjc.ist.jms.p2pexample;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class P2PSender {
	
	private static final String factoryName = "Factoria1";
	private static final String queueName = "Cola1";

	public static void main(String args[]) {

		try {
			InitialContext jndi = new InitialContext();			//permite compartir información
			QueueConnectionFactory factory = 
					(QueueConnectionFactory)jndi.lookup(factoryName);
			Queue queue = (Queue)jndi.lookup(queueName);

			QueueConnection connection = factory.createQueueConnection();	//creacion de una conexión a través de factory
			QueueSession session = connection.createQueueSession(false,		//creacion de una sesion en esa conexión
					             QueueSession.AUTO_ACKNOWLEDGE);
			QueueSender sender = session.createSender(queue);				//tansaccional: atomica e idempotente	

			TextMessage msg = session.createTextMessage();					//creacion de un mensaje de texto
			for(int i = 0; i < 10; i++){								//envío de 10 mensajes
				
				msg.setText("Mensaje " + i + " to " + queueName);
				sender.send(msg);
				System.err.println("Enviado mensaje " + i + " a " + queueName);
				Thread.sleep(1000);									//tiempo de espera para que se vacíe el buffer
			}
			System.err.println("\nSending message to close connection...");
			// Enviar mensaje de cierre al receptor
			msg.setText("CLOSE");
			sender.send(msg);
			connection.close();  //closes the connection, the session and the receiver
			System.err.println("Closing sender...");

		} catch (NamingException ex) {
			ex.printStackTrace();
		} catch (JMSException ex) {
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} 
	}
}
