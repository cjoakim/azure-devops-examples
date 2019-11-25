package com.chrisjoakim.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * Utility class for reading CSV files using the Apache commons-csv library.
 * 
 * @author Chris Joakim
 * @date   2019/11/02
 */
public class CsvReader {

	public CsvReader() {
		
		super();
	}
	
	public Iterable<CSVRecord> readWithHeader(String infile) throws FileNotFoundException, IOException {
		
		try {
			Reader in = new FileReader(infile);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
			return records;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
