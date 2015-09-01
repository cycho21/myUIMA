package activemq;

public class ActiveMQ_Test {
	
	public ActiveMQ_Test() {
		
		Receiver rcv = new Receiver();
		rcv.setQueueName("test");
		rcv.init();
		Thread rcvThread = new Thread(rcv);
		rcvThread.start();
		
		Sender sdr = new Sender();
		sdr.init();
		sdr.createQueue("test");
		
		while(true){
			sdr.sendMessage("this is test");
			sdr.sendMessage("this is real");
		}
		
	}

	public static void main(String[] args) {
		new ActiveMQ_Test();
	}

}
