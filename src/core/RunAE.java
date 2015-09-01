package core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.uima.UIMAFramework;
import org.apache.uima.aae.client.UimaASProcessStatus;
import org.apache.uima.aae.client.UimaAsBaseCallbackListener;
import org.apache.uima.aae.client.UimaAsynchronousEngine;
import org.apache.uima.adapter.jms.client.BaseUIMAAsynchronousEngine_impl;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.collection.EntityProcessStatus;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.ProcessTraceEvent;
import org.apache.uima.util.XMLInputSource;

public class RunAE {

	private BaseUIMAAsynchronousEngine_impl uimaEEEngine;
	private File collectionReaderDescriptor;
	private HashMap<String, Object> uimaSetting;

	
	public static void main(String[] args) {
		new RunAE();
	}
	
	public RunAE() {
		init();
	}

	private void init() {
		uimaEEEngine = new BaseUIMAAsynchronousEngine_impl();
		uimaSetting = new HashMap<String, Object>();
		collectionReaderDescriptor = new File(EnvironmentVariable.COLLECTIONREADER_PATH);
		setCollectionReader();
 		setUIMASetting();
	}

	private void setUIMASetting() {
		System.setProperty("defaultBrokerURL", "tcp://SuzumuraAiri:61616");
		uimaSetting.put(UimaAsynchronousEngine.DD2SpringXsltFilePath, EnvironmentVariable.UIMA_HOME + "/bin/dd2spring.xsl");
		uimaSetting.put(UimaAsynchronousEngine.SaxonClasspath, "file:" + EnvironmentVariable.UIMA_HOME + "/saxon/saxon8.jar");
	    uimaSetting.put(UimaAsynchronousEngine.ServerUri, "tcp://SuzumuraAiri:61616");
	    uimaSetting.put(UimaAsynchronousEngine.ENDPOINT, "RoomNumberAnnotatorQueue");
	    uimaSetting.put(UimaAsynchronousEngine.GetMetaTimeout, 10 * 1000);
	    uimaSetting.put(UimaAsynchronousEngine.CasPoolSize, 100);
//	    uimaSetting.put(UimaAsynchronousEngine.Timeout, 10 * 1000);
//	    uimaSetting.put(UimaAsynchronousEngine.CpcTimeout, 10 * 1000);
//	    uimaSetting.put(UIMAFramework.CAS_INITIAL_HEAP_SIZE, Integer.valueOf(8192 / 4).toString());
		
	    uimaEEEngine.addStatusCallbackListener(new StatusCallbackListenerImpl());
	    
		try {
			uimaEEEngine.deploy("C:/Users/HentaiMaster/Documents/SpringWorkspace/my-uima/deploy/as/Deploy_RoomNumberAnnotator.xml", uimaSetting);
			uimaEEEngine.initialize(uimaSetting);
			uimaEEEngine.process();
		} catch (ResourceInitializationException e) {
			System.out.println("Resource Init Exception");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setCollectionReader() {
		try {
			CollectionReaderDescription collectionReaderDescription = UIMAFramework.getXMLParser().parseCollectionReaderDescription(new XMLInputSource(collectionReaderDescriptor));
			CollectionReader collectionReader = UIMAFramework.produceCollectionReader(collectionReaderDescription);
			uimaEEEngine.setCollectionReader(collectionReader);
		} catch (ResourceInitializationException e) {
		} catch (InvalidXMLException e) {
		} catch (IOException e) {
		}
	}

	class StatusCallbackListenerImpl extends UimaAsBaseCallbackListener {
	    int entityCount = 0;

	    long size = 0;
		private boolean ignoreErrors;
		private boolean logCas;
		private ConcurrentHashMap casMap = new ConcurrentHashMap();
		private long mStartTime = System.nanoTime() / 1000000;
		private Object outputDir;

	    /**
	     * Called when the initialization is completed.
	     * 
	     * @see org.apache.uima.collection.processing.StatusCallbackListener#initializationComplete()
	     */
	    public void initializationComplete(EntityProcessStatus aStatus) {
	      if (aStatus != null && aStatus.isException()) {
	        System.err.println("Error on getMeta call to remote service:");
	        List exceptions = aStatus.getExceptions();
	        for (int i = 0; i < exceptions.size(); i++) {
	          ((Throwable) exceptions.get(i)).printStackTrace();
	        }
	        System.err.println("Terminating Client...");
	        stop();
	        
	      }
	      System.out.println("UIMA AS Service Initialization Complete");
	    }
	    private void stop() {
	      try {
	        uimaEEEngine.stop();
	      } catch( Exception e) {
	        
	      }
	      System.exit(0);
	      
	    }
	    /**
	     * Called when the collection processing is completed.
	     * 
	     * @see org.apache.uima.collection.processing.StatusCallbackListener#collectionProcessComplete()
	     */
	    public void collectionProcessComplete(EntityProcessStatus aStatus) {
	      if (aStatus != null && aStatus.isException()) {
	        System.err.println("Error on collection process complete call to remote service:");
	        List exceptions = aStatus.getExceptions();
	        for (int i = 0; i < exceptions.size(); i++) {
	          ((Throwable) exceptions.get(i)).printStackTrace();
	        }
	        System.err.println("Terminating Client...");
	        stop();
	      }
	      System.out.print("Completed " + entityCount + " documents");
	      if (size > 0) {
	        System.out.print("; " + size + " characters");
	      }
	      System.out.println();
	      long elapsedTime = System.nanoTime() / 1000000 - mStartTime;
	      System.out.println("Time Elapsed : " + elapsedTime + " ms ");

	      String perfReport = uimaEEEngine.getPerformanceReport();
	      if (perfReport != null) {
	        System.out.println("\n\n ------------------ PERFORMANCE REPORT ------------------\n");
	        System.out.println(uimaEEEngine.getPerformanceReport());
	      }
	      // stop the JVM. Dont stop here if running with -d (deploy) option. 
	      // service must be first be undeployed before stop() is called. Otherwise,
	      // this process hangs
	    //  if ( springContainerId == null ) {
	      //  stop();
	      //}
	    }

	    /**
	     * Called when the processing of a Document is completed. <br>
	     * The process status can be looked at and corresponding actions taken.
	     * 
	     * @param aCas
	     *          CAS corresponding to the completed processing
	     * @param aStatus
	     *          EntityProcessStatus that holds the status of all the events for aEntity
	     */
	    public void entityProcessComplete(CAS aCas, EntityProcessStatus aStatus) {
	      if (aStatus != null) {
	        if (aStatus.isException()) {
	          System.err.println("Error on process CAS call to remote service:");
	          List exceptions = aStatus.getExceptions();
	          for (int i = 0; i < exceptions.size(); i++) {
	            ((Throwable) exceptions.get(i)).printStackTrace();
	          }
	          if (!ignoreErrors) {
	            System.err.println("Terminating Client...");
	            stop();
	          }
	        }
	        if (logCas) {
	          String ip = "no IP";
	          List eList = aStatus.getProcessTrace().getEventsByComponentName("UimaEE", false);
	          for (int e = 0; e < eList.size(); e++) {
	            ProcessTraceEvent event = (ProcessTraceEvent) eList.get(e);
	            if (event.getDescription().equals("Service IP")) {
	              ip = event.getResultMessage();
	            }
	          }
	          String casId = ((UimaASProcessStatus) aStatus).getCasReferenceId();
	          if (casId != null) {
	            long current = System.nanoTime() / 1000000 - mStartTime;
	            if (casMap.containsKey(casId)) {
	              Object value = casMap.get(casId);
	              if (value != null && value instanceof Long) {
	                long start = ((Long) value).longValue();
	                System.out.println(ip + "\t" + start + "\t" + (current - start));
	              }
	            }
	          }

	        } else {
	          System.out.print(".");
	          if (0 == (entityCount + 1) % 50) {
	            System.out.print((entityCount + 1) + " processed\n");
	          }
	        }
	      }

	      // if output dir specified, dump CAS to XMI
	      if (outputDir != null) {
	        // try to retrieve the filename of the input file from the CAS
	        File outFile = null;
	        Type srcDocInfoType = aCas.getTypeSystem().getType(
	                "org.apache.uima.examples.SourceDocumentInformation");
	        if (srcDocInfoType != null) {
	          FSIterator it = aCas.getIndexRepository().getAllIndexedFS(srcDocInfoType);
	          if (it.hasNext()) {
	            FeatureStructure srcDocInfoFs = it.get();
	            Feature uriFeat = srcDocInfoType.getFeatureByBaseName("uri");
	            Feature offsetInSourceFeat = srcDocInfoType.getFeatureByBaseName("offsetInSource");
	            String uri = srcDocInfoFs.getStringValue(uriFeat);
	            int offsetInSource = srcDocInfoFs.getIntValue(offsetInSourceFeat);
	            File inFile;
	            try {
	              inFile = new File(new URL(uri).getPath());
	              String outFileName = inFile.getName();
	              if (offsetInSource > 0) {
	                outFileName += ("_" + offsetInSource);
	              }
	              outFileName += ".xmi";
	              outFile = new File((String) outputDir, outFileName);
	            } catch (MalformedURLException e1) {
	              // invalid URI, use default processing below
	            }
	          }
	        }
	        if (outFile == null) {
	          outFile = new File((String) outputDir, "doc" + entityCount);
	        }
	        try {
	          FileOutputStream outStream = new FileOutputStream(outFile);
	          try {
	            XmiCasSerializer.serialize(aCas, outStream);
	          } finally {
	            outStream.close();
	          }
	        } catch (Exception e) {
	          System.err.println("Could not save CAS to XMI file");
	          e.printStackTrace();
	        }
	      }

	      // update stats
	      entityCount++;
	      String docText = aCas.getDocumentText();
	      if (docText != null) {
	        size += docText.length();
	      }

	      // Called just before sendCas with next CAS from collection reader
	    }

	    public void onBeforeMessageSend(UimaASProcessStatus status) {
	      long current = System.nanoTime() / 1000000 - mStartTime;
	      casMap.put(status.getCasReferenceId(), current);
	    }
	    /**
	     * This method is called when a CAS is picked up by remote UIMA AS
	     * from a queue right before processing. This callback identifies
	     * on which machine the CAS is being processed and by which UIMA AS
	     * service (PID).
	     */
	    public void onBeforeProcessCAS(UimaASProcessStatus status, String nodeIP, String pid) {
	      
	    }

	  }

	}

