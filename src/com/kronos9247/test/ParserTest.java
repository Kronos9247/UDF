package com.kronos9247.test;

import org.junit.jupiter.api.Test;

import com.kronos9247.io.udf.UDFException;
import com.kronos9247.io.udf.UDFParser;
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
class ParserTest {

	@Test
	void test() {
		UDFParser parser = new UDFParser(ParserTest.class.getResourceAsStream("/test1.udf"));
		try {
			long time = System.currentTimeMillis();
			parser.parse();
			
			System.out.println("Time: " + String.valueOf(System.currentTimeMillis() - time) + "ms");
		} catch (UDFException e) {
			e.printStackTrace();
		}
		
		if(parser.isParsed())
			for(UDFValue value : parser.getValues()) {
				System.out.println(value.toString());
			}
		
		parser.close();
	}

}
