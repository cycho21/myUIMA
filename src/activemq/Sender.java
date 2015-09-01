package activemq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {

	private ActiveMQConnectionFactory factory;
	private Connection connection;
	private Session session;
	private Queue queue;
	private MessageProducer producer;

	public Sender() {
	}

	public void createQueue(String queueName) {
		try {
			queue = session.createQueue(queueName);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		set();
	}
	
	private void set() {
		try {
			producer = session.createProducer(queue);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void init() {
			factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msg) {
			TextMessage message;
			try {
				message = session.createTextMessage();
				message.setText(msg);
				producer.send(message);
			} catch (JMSException e) {
				e.printStackTrace();
			}
	}
	
}
