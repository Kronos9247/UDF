package com.kronos9247.test;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.kronos9247.io.udf.UDFWriter;


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
class WriterTest {
	
	public static final String CHAR_SET = "abcdefghijklmnopqertuvwxyzABCDEFGHIJKLMNOPQERSTUVWXYZ0123456789";
	public static String randomString(int length) {
		String out = "";
		for(int i = 0; i < length; i++) {
			out += CHAR_SET.charAt((int) Math.floor(Math.random() * CHAR_SET.length()));
		}
		
		return out;
	}

	@Test
	void test() {
		try {
			UDFWriter writer = new UDFWriter(new FileWriter("C:\\Users\\Rafael\\Documents\\Java\\Java Projects\\UDF\\test.udf"));
		
			for(int i = 0; i< 10000; i++) {
				writer.write(randomString(10), "test xd!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
