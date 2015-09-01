package core;

import java.util.HashMap;

public class IndexNumInfo {

	public static HashMap<String, Integer> indexMap = null;
	public static HashMap<String, String> readerMap = null;
	public static HashMap<String, String> transactionMap = null;

	public IndexNumInfo() {
	}

	public static HashMap<String, Integer> getMap() {
		if (indexMap == null) {
			indexMap = new HashMap<String, Integer>();
			return indexMap;
		} else {
			return indexMap;
		}
	}

	public static HashMap<String, String> getReaderMap() {
		if (readerMap == null) {
			readerMap = new HashMap<String, String>();
			return readerMap;
		} else {
			return readerMap;
		}
	}

	public static HashMap<String, String> getTransactionMap() {
		if (transactionMap == null) {
			transactionMap = new HashMap<String, String>();
			return transactionMap;
		} else {
			return transactionMap;
		}
	}
}
