# UDF-RegEx
A reimplemenation of Tewan's UDF file format using regex

# Credits
The "UDF" file format is created by [Tewan](github.com/SpyceTewan) and
is protected under the [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.de.html)

Original Project: [Universal-Data-Format](github.com/SpyceTewan/Universal-Data-Format)


This Format-Parser (Software) is written by [Kronos924](github.com\Kronos9247).

This Program is just a proof of concept and I used regex to parse the file syntax.
**This software is a total reimplemenation!!
No copied code from the original project!**


## Usage
  * **Writer:**
    ```java 
    UDFWriter writer = new UDFWriter(new FileWriter("C:\\pathToYourFile\\fileName.udf"));
    writer.write("KeyName", "Value");
    writer.write("KeyName", new String[] { "YourValues1", "YourValues2" }); 
    
    writer.close();
    ```
  * **Parser:**
    ```java 
    UDFParser parser = new UDFParser(ParserTest.class.getResourceAsStream("/fileName.udf")); //Internal file
    try {
      parser.parse();
    } catch (UDFException e) {
      e.printStackTrace();
    }

    if(parser.isParsed())
       for(UDFValue value : parser.getValues()) { //Looping through every entry
         System.out.println(value.toString()); //Printing out those entries
       }

    parser.close();
    ```
    
**File Syntax:**
  ```
  test.test: heöfjs test : dhdhh

  Hello124: XD

  xd xd: xjsbgz db \n gdgf	djhs

      ggdgdg: 		 dggd        

  öshcvhh: test

  test { test1, test2       }


  ml {
   multi line array,
   xd 1245	^   	,
   multiline 
    multiline
  }
  ```
