package urjc.ist.jms.p2pexample;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TestP2PAsyncReceiver {

	public static void main(String[] args) {
		
		try {
			InitialContext jndi = new InitialContext();
			QueueConnectionFactory factory = 
					(QueueConnectionFactory)jndi.lookup("Factoria1");
			QueueConnection connection = factory.createQueueConnection();
			Queue queue = (Queue)jndi.lookup("Cola1");
			
			P2PAsyncReceiver receiver = new P2PAsyncReceiver(connection, queue);
			Thread thReceiver = new Thread(receiver);
			thReceiver.start();
			System.err.println("TRACE: Waiting in join()");
			thReceiver.join();
			System.err.println("TRACE: Closing connection");
			connection.close();
			System.err.println("END");

		} catch (InterruptedException ex) {
			ex.printStackTrace();
	    } catch (NamingException ex) {
			ex.printStackTrace();
		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}
}
