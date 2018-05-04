package com.kronos9247.io.udf;

import java.io.FileWriter;
import java.io.IOException;

import com.kronos9247.io.udf.node.UDFData;

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
public class UDFWriter implements UDF {
	
	private final FileWriter writer;
	
	public UDFWriter(FileWriter writer) {
		this.writer = writer;
	}
	
	public void write(String key, String value) throws IOException {
		writer.write(UDFData.genString(key, value) + "\n");
		
		writer.flush();
	}
	
	public void write(String key, String[] values) throws IOException {
		writer.write(key + "{\n");
		for(int i = 0; i < values.length; i++) {
			writer.write(values[i] + (i-1 < values.length ? "," : "") + "\n");
			writer.flush();
		}
		writer.write("}\n");
	}
	
	public void close() throws IOException {
		writer.close();
	}

	@Override
	public String getVersion() {
		return "0.1";
	}

	@Override
	public boolean isParser() {
		return false;
	}

	@Override
	public boolean isWriter() {
		return true;
	}

}
