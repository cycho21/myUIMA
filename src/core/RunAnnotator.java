package core;

import java.util.HashMap;

import org.apache.uima.aae.client.UimaAsynchronousEngine;
import org.apache.uima.adapter.jms.client.BaseUIMAAsynchronousEngine_impl;

public class RunAnnotator {

	private BaseUIMAAsynchronousEngine_impl uimaEEEngine;
	private HashMap<String, Object> uimaSetting;

	public static void main(String[] args) {
		new RunAnnotator();
	}
	
	public RunAnnotator() {
		setAnnotator();
		init();
	}


	private void setAnnotator() {
		uimaEEEngine = new BaseUIMAAsynchronousEngine_impl();
		uimaSetting = new HashMap<String, Object>();		
	}

	private void init() {
		System.setProperty("defaultBrokerURL", "tcp://SuzumuraAiri:61616");
		uimaSetting.put(UimaAsynchronousEngine.DD2SpringXsltFilePath, EnvironmentVariable.UIMA_HOME + "/bin/dd2spring.xsl");
		uimaSetting.put(UimaAsynchronousEngine.SaxonClasspath, "file:" + EnvironmentVariable.UIMA_HOME + "/saxon/saxon8.jar");
	    uimaSetting.put(UimaAsynchronousEngine.ServerUri, "tcp://SuzumuraAiri:61616");
	    uimaSetting.put(UimaAsynchronousEngine.ENDPOINT, "myQueueName");
	    uimaSetting.put(UimaAsynchronousEngine.GetMetaTimeout, 10 * 1000);
	    uimaSetting.put(UimaAsynchronousEngine.CasPoolSize, 100);
//	    uimaSetting.put(UimaAsynchronousEngine.Timeout, 10 * 1000);
//	    uimaSetting.put(UimaAsynchronousEngine.CpcTimeout, 10 * 1000);
//	    uimaSetting.put(UIMAFramework.CAS_INITIAL_HEAP_SIZE, Integer.valueOf(8192 / 4).toString());
	
	    try {
	    	uimaEEEngine.deploy("C:/Users/HentaiMaster/Documents/SpringWorkspace/my-uima/desc/TestDeploymentDescriptor.xml", uimaSetting);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
}
