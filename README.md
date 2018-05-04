# UDF-RegEx
A reimplemenation of Twean's UDF file format using regex

# Credits
The "UDF" file format is created by [Tewan](github.com/SpyceTewan) and
is protected under the [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.de.html)

Original Project: [Universal-Data-Format](github.com/SpyceTewan/Universal-Data-Format)


This Format-Parser (Software) is written by [Kronos924](github.com\Kronos9247).

This Program is just a proof of concept and I used regex to parse the file syntax.
**This software is a total rewrite!!
No copied code from the original project!**


## Usage
  * **Writer:**
    ```java
UDFWriter writer = new UDFWriter(new FileWriter("C:\\pathToYourFile\\fileName.udf"));
writer.write("KeyName", "Value");
writer.write("KeyName", new String[] { "YourValues1", "YourValues2" });
    ```
