-------------------------
1. Application Description
-------------------------
We have some legacy data, which we would like to transform. Legacy data is stored in flat files on a file-system. Some of the files include post codes and we need to identify, where that data is and pull out the lines, which include post codes. Please provide code which will do the above.

Assumptions:
- Implementation as a Java offline application.
- User can input post-code format and flat-file extension.
- The application is to be run from the file-system.

----------------------
2. Design
----------------------
This application is designed as four classes:
- Main Class : Here goes the main method and all the components of the GUI.
- PostCodeSearch: This class lists all the files. It check if the file ends with the extension specified by user. When a flat-file with the specified extension is found, a new thread is created. In this thread, SearchFile Class is called. In case Post-Codes are found in the SearchFile, it returns data as String. PostCodeSearch Class write the returned data into an output file.
- SearchFile Class: This class searches a file for a certain pattern 'post-code' specified as a regular expression.
- FileWrite Class: This Class takes care of creating, writing, close to the output file.

----------------------------------
3. Implemented Concepts/Structures
----------------------------------
- Java Swing.
- Lambda Expression.
- Event Dispatched Thread.
- Multi-threading.
- Sempahores (to limit number of created threads).
- Sempahores (to lock file output)
- Synchronized methods (to control global variables modification)
- Regular expressions.
- Singleton Class.
- File Output, File arrays.
