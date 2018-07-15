package urjc.ist.jms.pubsubexample;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.concurrent.*;

public class TestPubSubAsyncReceiver {
	
	private static final String factoryName = "Factoria2";
	private static final String topicName = "Topic1";

	public static void main(String[] args) {

		try {
			InitialContext jndi = new InitialContext();
			TopicConnectionFactory factory = 
					(TopicConnectionFactory)jndi.lookup(factoryName);
			TopicConnection connection = factory.createTopicConnection();
			Topic topic = (Topic)jndi.lookup(topicName);

			PubSubAsyncReceiver receiver = new PubSubAsyncReceiver(connection, topic);
			ExecutorService e = Executors.newSingleThreadExecutor();
			e.execute(receiver);
			System.err.println("TRACE: Waiting in join()");
			e.shutdown();
			while (!e.isTerminated()) {
	        }
			System.err.println("TRACE: Closing connection");
			connection.close();
			System.err.println("END");

		} catch (NamingException ex) {
			ex.printStackTrace();
		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}
}
