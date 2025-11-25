package com.jeopardy.loaders; // package declaration
import com.jeopardy.model.Question; // import Question model
import java.io.File; // import File class
import java.util.List; // import List interface

// QuestionLoader interface for loading questions from a file
public interface QuestionLoader { // start of interface
    List<Question> load(File file) throws Exception; // method to load questions from a file
} // end of interface

