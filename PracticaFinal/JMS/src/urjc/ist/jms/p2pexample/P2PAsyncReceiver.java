package urjc.ist.jms.p2pexample;

import java.util.Objects;
import javax.jms.*;

public class P2PAsyncReceiver implements Runnable, MessageListener{

	private QueueConnection connection;
	private Queue queue;
	private boolean stopFlag;

	public P2PAsyncReceiver(QueueConnection con, Queue queue){
		connection = con;
		this.queue = queue;
		stopFlag = false;
	}

	@Override
	public void run(){
		try {   
			QueueSession session = 
					connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			QueueReceiver receiver = session.createReceiver(queue);
			receiver.setMessageListener(this);
			connection.start();
			System.out.println("Thread " + Thread.currentThread().getId() + " listening!");
			
			while (!stopFlag) {
				Thread.sleep(1000);
			}
			System.err.println("TRACE: Return Thread");
			return;
			
		} catch (JMSException ex) {
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void onMessage(Message msg) {
		try {
			TextMessage m = (TextMessage)msg;
			
			if (Objects.equals(m.getText(), "CLOSE")){
				System.out.println("No more messages. Closing now!");
				stopFlag = true; //Enable condition to stop current Thread
				
			} else {
				System.out.println("Listener, Thread " + 
						Thread.currentThread().getId() +
						" message received: " + m.getText());
			}
			
		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}

}
