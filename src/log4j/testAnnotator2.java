package log4j;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import enumType.AnnotatorType;
import enumType.ProcessType;
import activemq.Log4Anno;

public class testAnnotator2 extends Log4Anno {

	private String text;

	public testAnnotator2() {
	}

	public void process(JCas paramJCas) throws AnalysisEngineProcessException {
		test testAnno = new test(paramJCas);
		init();
		testAnno.setProcessName("testAnnotator2");
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
			sendLog(pid, ipAdrs, AnnotatorType.TEST2, ProcessType.LOAD);
		} else {
			sendLog(pid, ipAdrs, AnnotatorType.TEST2, ProcessType.LOAD, paramJCas.getDocumentText());
		}
		
		if(text == "null"){
			sendLog(pid, ipAdrs, AnnotatorType.TEST2, ProcessType.PROCESSING);
		} else {
			sendLog(pid, ipAdrs, AnnotatorType.TEST2, ProcessType.PROCESSING, paramJCas.getDocumentText());
		}
		
		
		testAnno.addToIndexes();
		
		if(text == "null"){
			sendLog(pid, ipAdrs, AnnotatorType.TEST2, ProcessType.DONE);
		} else {
			sendLog(pid, ipAdrs, AnnotatorType.TEST2, ProcessType.DONE, paramJCas.getDocumentText());
		}
	}
}
