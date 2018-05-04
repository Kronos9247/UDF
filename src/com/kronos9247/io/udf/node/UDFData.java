package com.kronos9247.io.udf.node;

import java.io.Serializable;

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
public final class UDFData extends UDFValue implements Serializable {

	private static final long serialVersionUID = -449514916226671496L;
	
	private String value;
	
	public UDFData(String key, String value) {
		super(key);
		
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return genString(getKey(), getValue());
	}
	
	@Override
	public boolean isData() {
		return true;
	}

	@Override
	public boolean isArray() {
		return false;
	}
	
	public static String genString(String key, String value) {
		return key + ": " + value;
	}
	
}
