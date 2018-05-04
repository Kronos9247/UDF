package com.kronos9247.io.udf;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kronos9247.io.udf.node.UDFArray;
import com.kronos9247.io.udf.node.UDFData;
import com.kronos9247.io.udf.node.UDFValue;

/*
 * This program is a reimplementation of Tewan's UDF file format.
 * This is a complete new software containing a Parser(Reader) / a Writer
 * of the file format.
 * 
 * For more informations read the Credits file
 * 
 *  Copyright (C) 2018  Rafael Orman
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public final class UDFParser implements UDF {
	
	public static final String KEY_PATTERN = "[\\w\\\\pS\\t \\p{L}.]*";
	public static final String VALUE_PATTERN = "\\s*(?<Value>[\\w\\pS\\t\\p{L}:.!?\\\\ ]*[\\w\\pS\\p{L}:.!?\\\\])";
	public static final Pattern PATTERN = Pattern.compile("\\s*(?<Key>" + KEY_PATTERN + "):" + VALUE_PATTERN);
	public static final Pattern ARRAY_PATTERN = Pattern.compile("\\s*(?<Key>" + KEY_PATTERN + ")\\{(.*)");
	public static final Pattern ARR_END_PATTERN = Pattern.compile("(.*)\\}");
	public static final Pattern ONELINE = Pattern.compile("\\{(?<Value>.*)\\}");
	public static final Pattern ARRAY_CAST = Pattern.compile(VALUE_PATTERN);
	
	public static final String ARRAY_SPLITTER = ",";
	
	private final Scanner scanner;
	private boolean parsed;
	private HashMap<String, UDFValue> values;
	
	private boolean array;
	private String arrayName;
	private String token;
	private ArrayList<String> arrayValues;
	
	public UDFParser(InputStream stream) {
		scanner = new Scanner(stream);
		scanner.useDelimiter("[\t ]*");
		
		values = new HashMap<String, UDFValue>();
		arrayValues = new ArrayList<String>();
	}
	
	private void formatLine(String[] values) {
		for(int i = 0; i < values.length; i++) {
			Matcher cast = ARRAY_CAST.matcher(values[i]);
			
			if(cast.find())
				values[i] = cast.group("Value");
		}
	}
	
	private void insertToken(String token) {
		if(this.token == null) this.token = token;
		else this.token += token;
	}
	
	private void parseArrayCode(String arrayCode) throws UDFException {
		if(arrayCode != null) {
			for(int i = 0; i < arrayCode.length(); i++) {
				if(arrayCode.charAt(i) == ARRAY_SPLITTER.charAt(0)) {
					if(token != null) arrayValues.add(token);
					token = null;
				}
				else {
					insertToken(String.valueOf(arrayCode.charAt(i)));
				}
			}
		}
//		String[] values = arrayCode.split(ARRAY_SPLITTER);
//		formatLine(values);
//		
//		Matcher matcher = LINE_END.matcher(arrayCode);
//		if(matcher.matches()) {
//			
//		}
	}
	
	public void parseLine(String line) throws UDFException {
		if(array) {
			Matcher matcher = ARR_END_PATTERN.matcher(line);
			
			if(matcher.find()) {
				if(token != null) arrayValues.add(token);
				
				String[] values = arrayValues.toArray(new String[arrayValues.size()]);
				formatLine(values);
				this.values.put(arrayName, new UDFArray(arrayName, values));
				
				array = false;
				arrayName = null;
				arrayValues.clear();
				
				String Value = matcher.group(1);
				if(Value != null && !Value.isEmpty()) parseArrayCode(Value);
			}
			else {
				parseArrayCode(line);
			}
		}
		else parseLine_1(line);
	}
	
	private void parseLine_1(String line) throws UDFException {
		Matcher matcher = ARRAY_PATTERN.matcher(line);
		
		if(matcher.find()) {
			String key = matcher.group("Key");
			Matcher matcher2 = ARR_END_PATTERN.matcher(line);
			
			if(matcher2.find()) {
				Matcher bracket = ONELINE.matcher(line);
				
				if(bracket.find()) {
					String[] values = bracket.group("Value").split(ARRAY_SPLITTER);
					
					formatLine(values);
					
					this.values.put(key, new UDFArray(key, values));
				}
				else throw new UDFException("Syntax Error: missing array brackets");
			}
			else {
				array = true;
				arrayName = key;
			}
		}
		else parseLine_2(line);
	}
	
	private void parseLine_2(String line) throws UDFException {
		Matcher matcher = PATTERN.matcher(line);
		
		if(matcher.find()) {
			String key = matcher.group("Key");
			if(key != null) {
				String value = matcher.group("Value");
				
				values.put(key, new UDFData(key, (value == null ? "" : value))); 
			}
			else throw new UDFException("Syntax Error: Unknown Key Value");
		}
	}
	
	public void parse() throws UDFException {
		this.parsed = parseFile();
	}
	
	private final boolean parseFile() throws UDFException {
		while(scanner.hasNextLine()) {
			parseLine(scanner.nextLine());
		}
		
		return true;
	}

	public boolean isParsed() {
		return parsed;
	}
	
	public void close() {
		scanner.close();
	}
	
	public Set<String> getKeys() {
		return values.keySet();
	}
	
	public Collection<UDFValue> getValues() {
		return values.values();
	}
	
	public UDFValue getValue(String key) {
		if(values.containsKey(key))
			return values.get(key);
		
		return null;
	}
	
	public String getData(String key) {
		if(values.containsKey(key)) {
			UDFValue value = values.get(key);
			if(values.get(key) instanceof UDFData) return ((UDFData) value).getValue();
		}
		
		return null;
	}
	
	public String[] getArray(String key) {
		if(values.containsKey(key)) {
			UDFValue value = values.get(key);
			if(values.get(key) instanceof UDFArray) return ((UDFArray) value).getValues();
		}
		
		return null;
	}

	@Override
	public String getVersion() {
		return "0.1";
	}

	@Override
	public boolean isParser() {
		return true;
	}

	@Override
	public boolean isWriter() {
		return !isParser();
	}

}
