package com.jeopardy.loaders;

import com.jeopardy.model.Question;
import java.io.File;
import java.util.List;

/**
 * Interface for loading questions from various file formats.
 * Defines the contract for question loader implementations.
 * 
 * @author Group 33
 * @version 1.0
 */
public interface QuestionLoader {
    
    /**
     * Loads questions from the specified file.
     * 
     * @param file the file containing question data in supported format
     * @return a list of Question objects parsed from the file
     * @throws Exception if file reading or parsing fails
     */
    List<Question> load(File file) throws Exception;
}