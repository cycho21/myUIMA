package log4j;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import enumType.AnnotatorType;
import enumType.ProcessType;
import activemq.Log4Anno;

public class testAnnotator3 extends Log4Anno {

	private String text;

	public testAnnotator3() {
	}

	public void process(JCas paramJCas) throws AnalysisEngineProcessException {
		test testAnno = new test(paramJCas);
		init();
		testAnno.setProcessName("testAnnotator3");
		text = paramJCas.getDocumentText();
		
		String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String ipAdrs = ip.getHostAddress();
		
		if(text == "null"){
			sendLog(pid, ipAdrs, AnnotatorType.TEST3, ProcessType.LOAD);
		} else {
			sendLog(pid, ipAdrs, AnnotatorType.TEST3, ProcessType.LOAD, paramJCas.getDocumentText());
		}
		
		if(text == "null"){
			sendLog(pid, ipAdrs, AnnotatorType.TEST3, ProcessType.PROCESSING);
		} else {
			sendLog(pid, ipAdrs, AnnotatorType.TEST3, ProcessType.PROCESSING, paramJCas.getDocumentText());
		}
		
		testAnno.addToIndexes();
		
		if(text == "null"){
			sendLog(pid, ipAdrs, AnnotatorType.TEST3, ProcessType.DONE);
		} else {
			sendLog(pid, ipAdrs, AnnotatorType.TEST3, ProcessType.DONE, paramJCas.getDocumentText());
		}
	}

}
