package log4j;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import enumType.AnnotatorType;
import enumType.ProcessType;
import activemq.Log4Anno;

public class testAnnotator4 extends Log4Anno {

	private String text;

	public testAnnotator4() {
	}

	public void process(JCas paramJCas) throws AnalysisEngineProcessException {
		test testAnno = new test(paramJCas);
		init();
		testAnno.setProcessName("testAnnotator4");
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
			sendLog(pid, ipAdrs, AnnotatorType.TEST4, ProcessType.LOAD);
		} else {
			sendLog(pid, ipAdrs, AnnotatorType.TEST4, ProcessType.LOAD, paramJCas.getDocumentText());
		}
		
		if(text == "null"){
			sendLog(pid, ipAdrs, AnnotatorType.TEST4, ProcessType.PROCESSING);
		} else {
			sendLog(pid, ipAdrs, AnnotatorType.TEST4, ProcessType.PROCESSING, paramJCas.getDocumentText());
		}
		
		testAnno.addToIndexes();
		
		if(text == "null"){
			sendLog(pid, ipAdrs, AnnotatorType.TEST4, ProcessType.DONE);
		} else {
			sendLog(pid, ipAdrs, AnnotatorType.TEST4, ProcessType.DONE, paramJCas.getDocumentText());
		}
	}

}
