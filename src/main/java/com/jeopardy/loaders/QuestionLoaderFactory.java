package com.jeopardy.loaders; // package declaration
import java.io.File; // import File class

// Factory class to get appropriate QuestionLoader based on file extension
public class QuestionLoaderFactory { // start of factory class
    public static QuestionLoader getLoader(File file) { // method to get QuestionLoader
        String name = file.getName().toLowerCase(); // get file name in lowercase
        if (name.endsWith(".csv")) return new CsvQuestionLoader(); // return CSV loader
        if (name.endsWith(".json")) return new JsonQuestionLoader(); // return JSON loader
        if (name.endsWith(".xml")) return new XmlQuestionLoader(); // return XML loader
        return null; // return null if no matching loader
    } // end of getLoader method
} // end of factory class