package activemq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.*;

import core.IndexNumInfo;

public class Receiver implements Runnable {

	private ActiveMQConnectionFactory factory;
	private Connection connection;
	private Session session;
	private String queueName;

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	private Queue queue;
	private MessageConsumer consumer;
	private Message message;
	private final Logger logger = Logger.getLogger(Receiver.class);
	private TextMessage tMsg;
	private Integer casIndex;
	private String collectionReaderName;
	private String inputFile;

	public String getCollectionReaderName() {
		return collectionReaderName;
	}

	public void setCollectionReaderName(String collectionReaderName) {
		this.collectionReaderName = collectionReaderName;
	}

	public TextMessage gettMsg() {
		return tMsg;
	}

	public void settMsg(TextMessage tMsg) {
		this.tMsg = tMsg;
	}

	public Receiver() {
	}

	private void consume() {
		try {
			consumer = session.createConsumer(queue);
			message = consumer.receive();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		try {
			while (true) {
				consume();
				tMsg = (TextMessage) message;
				String parsedString = parse(tMsg.getText());
				String[] parsed = tMsg.getText().split("\\|");
				casIndex = IndexNumInfo.indexMap.get(parsedString);
				inputFile = IndexNumInfo.transactionMap.get(parsedString);
				
				logger.info("InputFile:" + inputFile + "|"
						+ "CollectionReader:" + collectionReaderName + "|"
						+ parsed[0] + "|" + parsed[1] + "|" + parsed[2] + "|" + parsed[3] + "|casIndex:" + casIndex
						+ "|" + "Data:"+parsedString);
			}
		} catch (Exception e) {
		}

	}

	public String parse(String string) {
		String parsedString = string.split("Data:")[1].trim();
		return parsedString;
	}

	public void init() {
		PropertyConfigurator.configure("properties/log4j.properties");
		factory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_BROKER_URL);
		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			queue = session.createQueue(queueName);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
