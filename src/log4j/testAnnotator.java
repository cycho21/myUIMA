package log4j;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.uima.UIMAFramework;
import org.apache.uima.jcas.JCas;

import enumType.AnnotatorType;
import enumType.ProcessType;
import activemq.Log4Anno;

public class testAnnotator extends Log4Anno {

	private String text;

	public testAnnotator() {
	}

	public void process(JCas paramJCas) {

		test testAnno = new test(paramJCas);
		init();
		testAnno.setProcessName("testAnnotator1");
		
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
			sendLog(pid, ipAdrs, AnnotatorType.TEST1, ProcessType.LOAD);
		} else {
			sendLog(pid, ipAdrs, AnnotatorType.TEST1, ProcessType.LOAD, paramJCas.getDocumentText());
		}
		
		if(text == "null"){
			sendLog(pid, ipAdrs, AnnotatorType.TEST1, ProcessType.PROCESSING);
		} else {
			sendLog(pid, ipAdrs, AnnotatorType.TEST1, ProcessType.PROCESSING, paramJCas.getDocumentText());
		}
		
		testAnno.addToIndexes();
		
		if(text == "null"){
			sendLog(pid, ipAdrs, AnnotatorType.TEST1, ProcessType.DONE);
		} else {
			sendLog(pid, ipAdrs, AnnotatorType.TEST1, ProcessType.DONE, paramJCas.getDocumentText());
		}
	}
}