package activemq;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;

import enumType.AnnotatorType;
import enumType.ProcessType;
import activemq.Sender;

public abstract class Log4Anno extends JCasAnnotator_ImplBase {

	private Sender sdr;

	public void init() {
		this.sdr = new Sender();
		sdr.init();
		sdr.createQueue("SysLogMsg");
	}

	public void sendLog(String pid, String ipAddress, AnnotatorType anno, ProcessType procType, String Data) {
		sdr.sendMessage("PID:" + pid + "|" + "IP:" +ipAddress + "|" + "AnnotatorType:" + anno.toString() + "|ProcessType:" + procType + "|Data:" + Data);
	}

	public void sendLog(String pid, String ipAddress, AnnotatorType anno, ProcessType procType) {
		sdr.sendMessage("PID:" + pid + "|" + "IP:" +ipAddress + "|" +  "AnnotatorType:" + anno.toString() + "|ProcessType:" + procType);
	}
}
