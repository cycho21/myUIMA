package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

public class CollectionReader extends CollectionReader_ImplBase{

	private ArrayList<String> inputList;
	private int index;
	private HashMap<String, Integer> indexMap;

	public CollectionReader() {
		inputList = new ArrayList<String>();
		index = 0;
		initCollectionReader();
	}
	
	
	
	private void initCollectionReader() {
		File f = new File("F:/data/input.data");
		String readData = null;
		ArrayList<String> strings = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = null;
			while((line = br.readLine())!=null){
				if (line.trim().length() == 0 ) {
				} else {
					inputList.add(line.trim());
					IndexNumInfo.getMap().put(inputList.get(inputList.size()-1), inputList.size()-1);
					IndexNumInfo.getTransactionMap().put(inputList.get(inputList.size()-1), f.getName());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void getNext(CAS paramCAS) throws IOException, CollectionException {
		paramCAS.setDocumentText(inputList.get(index));
		index++;
	}

	public boolean hasNext() throws IOException, CollectionException {
		return index<inputList.size();
	}

	public Progress[] getProgress() {
		return new Progress[]{new ProgressImpl(index, inputList.size(), Progress.ENTITIES)};
	}

	public void close() throws IOException {
	}

}
