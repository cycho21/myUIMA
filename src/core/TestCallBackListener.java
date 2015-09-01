package core;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.uima.aae.UimaASApplicationEvent.EventTrigger;
import org.apache.uima.aae.client.UimaASProcessStatus;
import org.apache.uima.aae.client.UimaAsBaseCallbackListener;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.EntityProcessStatus;

public class TestCallBackListener extends UimaAsBaseCallbackListener {

	private Logger logger;

	public TestCallBackListener() {
		logger = Logger.getLogger(TestCallBackListener.class);
		logger.info("\n* ---------------------- \n"
					+"* Run Task Process Start \n");
	}

	public void initializationComplete(EntityProcessStatus aStatus) {
		if (aStatus != null && aStatus.isException()) {
			logger.info("Error on getMeta call to remote service:");
			@SuppressWarnings("rawtypes")
			List exceptions = aStatus.getExceptions();
			for (int i = 0; i < exceptions.size(); i++) {
				((Throwable) exceptions.get(i)).printStackTrace();
			}
			System.err.println("Terminating Client...");
		}
		logger.info("UIMA AS Service Initialization Complete");
	}
	@Override
	public void collectionProcessComplete(EntityProcessStatus aStatus) {
		super.collectionProcessComplete(aStatus);
	}
	
	@Override
	public void entityProcessComplete(CAS aCas, EntityProcessStatus aStatus) {
		// TODO Auto-generated method stub
		super.entityProcessComplete(aCas, aStatus);
	}
	
	@Override
	public void onBeforeMessageSend(UimaASProcessStatus status) {
		// TODO Auto-generated method stub
		super.onBeforeMessageSend(status);
	}
	
	@Override
	public void onBeforeProcessMeta(String nodeIP, String pid) {
		// TODO Auto-generated method stub
		super.onBeforeProcessMeta(nodeIP, pid);
	}
	
	@Override
	public void onUimaAsServiceExit(EventTrigger cause) {
		// TODO Auto-generated method stub
		super.onUimaAsServiceExit(cause);
	}
	
	@Override
	public void onBeforeProcessCAS(UimaASProcessStatus status, String nodeIP,
			String pid) {
		// TODO Auto-generated method stub
		super.onBeforeProcessCAS(status, nodeIP, pid);
	}
}