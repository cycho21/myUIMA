package core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.uima.UIMAFramework;
import org.apache.uima.aae.client.UimaAsynchronousEngine;
import org.apache.uima.adapter.jms.client.BaseUIMAAsynchronousEngine_impl;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;

import activemq.Receiver;

public class RunTask {

	private Logger logger;

	public static void main(String[] args) {
		try {
			new RunTask();
		} catch (ResourceProcessException e) {
			e.printStackTrace();
		}
	}

	private void logger_Init() {
		logger = Logger.getLogger(RunTask.class);
		logger.info("* Run Task Process Start /n");
	}

	private File collectionReaderDescriptor;
	private BaseUIMAAsynchronousEngine_impl uimaEEEngine;
	private HashMap<String, Object> uimaSetting;
	private CollectionReaderDescription collectionReaderDescription;
	private CollectionReader collectionReader;

	public RunTask() throws ResourceProcessException {
		logger_Init();
		uimaEEEngine = new BaseUIMAAsynchronousEngine_impl();
		uimaSetting = new HashMap<String, Object>();
		collectionReaderDescriptor = new File(EnvironmentVariable.COLLECTIONREADER_PATH);
		setCollectionReader();
		setUIMASetting();
		runEngine();
	}

	private void runEngine() {
		try {
			uimaEEEngine.deploy("C:/Users/HentaiMaster/Documents/SpringWorkspace/my-uima/desc/TestDeploymentDescriptor.xml", uimaSetting);
			uimaEEEngine.addStatusCallbackListener(new TestCallBackListener() {});
			uimaEEEngine.initialize(uimaSetting);
			
			setReceiver();
			
			uimaEEEngine.process();
			logger.info("End Point Name : " + uimaEEEngine.getEndPointName());
			/*
			 * 
			 */
			logger.info("Meta Data : " + uimaEEEngine.getMetaData().getName());
		} catch (Exception e) {
			System.out.println("Uima Engine Error - Throws Exception");
		}
	}

	private void setReceiver() {
		Receiver rcv = new Receiver();
		rcv.setCollectionReaderName(collectionReaderDescription.getCollectionReaderMetaData().getName());
		rcv.setQueueName("SysLogMsg");
		rcv.init();
		Thread rcvThread = new Thread(rcv);
		rcvThread.start();
	}

	private void setUIMASetting() {
		System.setProperty("defaultBrokerURL", "tcp://SuzumuraAiri:61616");
		uimaSetting.put(UimaAsynchronousEngine.DD2SpringXsltFilePath, EnvironmentVariable.UIMA_HOME + "/bin/dd2spring.xsl");
		uimaSetting.put(UimaAsynchronousEngine.SaxonClasspath, "file:" + EnvironmentVariable.UIMA_HOME + "/saxon/saxon8.jar");
		uimaSetting.put(UimaAsynchronousEngine.ServerUri, "tcp://SuzumuraAiri:61616");
		uimaSetting.put(UimaAsynchronousEngine.ENDPOINT, "myQueueName");
		uimaSetting.put(UimaAsynchronousEngine.GetMetaTimeout, 10 * 1000);
		uimaSetting.put(UimaAsynchronousEngine.CasPoolSize, 100);
	}

	private void setCollectionReader() {
		try {
			collectionReaderDescription = UIMAFramework.getXMLParser().parseCollectionReaderDescription(new XMLInputSource(collectionReaderDescriptor));
			collectionReader = UIMAFramework.produceCollectionReader(collectionReaderDescription);
			uimaEEEngine.setCollectionReader(collectionReader);
		} catch (InvalidXMLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ResourceInitializationException e) {
			e.printStackTrace();
		}
	}
}
