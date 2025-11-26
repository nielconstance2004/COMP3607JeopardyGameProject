package com.jeopardy.loaders; // package declaration

import com.fasterxml.jackson.core.type.TypeReference; // for type reference
import com.fasterxml.jackson.databind.ObjectMapper; // for JSON mapping
import com.jeopardy.model.Question; // question model

import java.io.File; // for file representation
import java.util.ArrayList; // for list implementation
import java.util.List; // for list interface
import java.util.Map; // for map interface
 
// JSON question loader implementation
public class JsonQuestionLoader implements QuestionLoader { // implement QuestionLoader interface
    private final ObjectMapper mapper = new ObjectMapper(); // initialize JSON object mapper

    @Override // override load method
    public List<Question> load(File file) throws Exception { // load questions from file
        List<Question> out = new ArrayList<>(); // initialize output list
        // Expect either array of objects with fields: category, value, question, answer OR map
        List<Map<String, Object>> arr = mapper.readValue(file, new TypeReference<List<Map<String, Object>>>(){}); // read JSON as list of maps
        for (Map<String, Object> m : arr) { // iterate over each map
                String category = String.valueOf(m.getOrDefault("category", m.getOrDefault("Category", ""))); // get category
                int value = 0; // initialize value
                try { value = Integer.parseInt(String.valueOf(m.getOrDefault("value", m.getOrDefault("Value", "0")))); } catch (Exception ignored) {} // parse value
                String text = String.valueOf(m.getOrDefault("question", m.getOrDefault("Question", ""))); // get question text
                String answer = String.valueOf(m.getOrDefault("answer", m.getOrDefault("Answer", ""))); // get answer
                // choices may be provided as fields, array under "choices", or map under "Options"
                String a = null, b = null, c = null, d = null; // initialize choices
                Object choicesObj = m.get("choices"); // get choices object
                if (choicesObj instanceof java.util.List) { // if choices is a list
                    java.util.List<?> lst = (java.util.List<?>) choicesObj; // cast to list
                    if (lst.size() > 0) a = String.valueOf(lst.get(0)); // get choice A
                    if (lst.size() > 1) b = String.valueOf(lst.get(1)); // get choice B
                    if (lst.size() > 2) c = String.valueOf(lst.get(2)); // get choice C
                    if (lst.size() > 3) d = String.valueOf(lst.get(3)); // get choice D
                }
                // handle Options map like {"A":"...","B":"..."}
                Object opts = m.get("Options");
                if (opts instanceof java.util.Map) {
                    java.util.Map<?,?> om = (java.util.Map<?,?>) opts;
                    if (om.get("A") != null) a = String.valueOf(om.get("A"));
                    if (om.get("B") != null) b = String.valueOf(om.get("B"));
                    if (om.get("C") != null) c = String.valueOf(om.get("C"));
                    if (om.get("D") != null) d = String.valueOf(om.get("D"));
                    // also support lowercase or alternative keys
                    if (a == null && om.get("OptionA") != null) a = String.valueOf(om.get("OptionA"));
                    if (b == null && om.get("OptionB") != null) b = String.valueOf(om.get("OptionB"));
                    if (c == null && om.get("OptionC") != null) c = String.valueOf(om.get("OptionC"));
                    if (d == null && om.get("OptionD") != null) d = String.valueOf(om.get("OptionD"));
                }
                if (m.get("choiceA") != null) a = String.valueOf(m.get("choiceA")); // get choice A
                if (m.get("choiceB") != null) b = String.valueOf(m.get("choiceB")); // get choice B
                if (m.get("choiceC") != null) c = String.valueOf(m.get("choiceC")); // get choice C
                if (m.get("choiceD") != null) d = String.valueOf(m.get("choiceD")); // get choice D
                out.add(new com.jeopardy.model.QuestionBuilder() // build question
                    .category(category) // set category
                    .value(value) // set value
                    .text(text) // set question text
                    .answer(answer) // set answer
                    .choiceA(a) // set choice A
                    .choiceB(b) // set choice B
                    .choiceC(c) // set choice C
                    .choiceD(d) // set choice D
                    .build()); // build and add to list
        } // end for
        return out; // return list of questions
    } // end load method
} // end class