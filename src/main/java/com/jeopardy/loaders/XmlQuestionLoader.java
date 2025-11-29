package com.jeopardy.loaders; // package declaration

import com.fasterxml.jackson.dataformat.xml.XmlMapper; // for XML mapping
import com.fasterxml.jackson.databind.JsonNode; // for JSON tree nodes
import com.jeopardy.model.Question; // question model

import java.io.File; // for file representation
import java.util.ArrayList; // for list implementation
import java.util.List; // for list interface
import java.util.Map; // for map interface
 
/**
 * XML format question loader implementation.
 * Supports multiple XML structures and nested option formats.
 * 
 * @author Group 33
 * @version 1.0
 */

// XML question loader implementation
public class XmlQuestionLoader implements QuestionLoader { // implement QuestionLoader interface
    private final XmlMapper xml = new XmlMapper(); // initialize XML object mapper

    /**
     * Loads questions from an XML file.
     * Supports multiple XML structures including nested options.
     * 
     * @param file the XML file to load questions from
     * @return list of Question objects parsed from the XML file
     * @throws Exception if file reading or parsing fails
     */

    @Override // override load method
    @SuppressWarnings("unchecked")
    public List<Question> load(File file) throws Exception { // load questions from file
        List<Question> out = new ArrayList<>(); // initialize output list
        try { // try parsing root as list of question maps
            // Try parsing root as list of question maps
            List<Map<String, Object>> arr = xml.readValue(file, List.class); // read XML as list of maps
            for (Map<String, Object> m : arr) { // iterate over each map
                String category = String.valueOf(m.getOrDefault("category", m.getOrDefault("Category", ""))); // get category from map
                int value = 0; try { value = Integer.parseInt(String.valueOf(m.getOrDefault("value", m.getOrDefault("Value", "0")))); } catch (Exception ignored) {} // parse value
                String text = String.valueOf(m.getOrDefault("question", m.getOrDefault("Question", m.getOrDefault("QuestionText", "")))); // get question text
                String answer = String.valueOf(m.getOrDefault("answer", m.getOrDefault("Answer", m.getOrDefault("CorrectAnswer", "")))); // get answer
                // choices may be nested under an 'Options' map
                String a = null, b = null, c = null, d = null; // initialize choices
                Object opts = m.get("Options"); // get options object
                if (opts instanceof Map) { // if options is a map
                    Map<?,?> om = (Map<?,?>) opts; // cast to map
                    Object oa = om.get("OptionA"); if (oa == null) oa = om.get("optionA"); if (oa == null) oa = om.get("Optiona"); if (oa != null) a = String.valueOf(oa); // get choice A
                    Object ob = om.get("OptionB"); if (ob == null) ob = om.get("optionB"); if (ob == null) ob = om.get("Optionb"); if (ob != null) b = String.valueOf(ob); // get choice B
                    Object oc = om.get("OptionC"); if (oc == null) oc = om.get("optionC"); if (oc == null) oc = om.get("Optionc"); if (oc != null) c = String.valueOf(oc); // get choice C
                    Object od = om.get("OptionD"); if (od == null) od = om.get("optionD"); if (od == null) od = om.get("Optiond"); if (od != null) d = String.valueOf(od); // get choice D
                } // end if options is a map
                // or direct choice fields on the map
                if (a == null) a = (String) m.getOrDefault("choiceA", m.getOrDefault("choicea", m.getOrDefault("OptionA", m.getOrDefault("optionA", null)))); // get choice A
                if (b == null) b = (String) m.getOrDefault("choiceB", m.getOrDefault("choiceb", m.getOrDefault("OptionB", m.getOrDefault("optionB", null)))); // get choice B
                if (c == null) c = (String) m.getOrDefault("choiceC", m.getOrDefault("choicec", m.getOrDefault("OptionC", m.getOrDefault("optionC", null)))); // get choice C
                if (d == null) d = (String) m.getOrDefault("choiceD", m.getOrDefault("choiced", m.getOrDefault("OptionD", m.getOrDefault("optionD", null)))); // get choice D

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
        } catch (Exception ex) { // if parsing as list fails
            // fallback: try wrapper object with field 'questions'
            Map<String, Object> wrapper = xml.readValue(file, Map.class); // read XML as map
            Object maybe = wrapper.get("questions"); // get 'questions' field
            if (maybe instanceof List) { // if 'questions' is a list
                List<Map<String, Object>> arr = (List<Map<String, Object>>) maybe; // cast to list of maps
                for (Map<String, Object> m : arr) { // iterate over each map
                    String category = String.valueOf(m.getOrDefault("category", m.getOrDefault("Category", ""))); // get category
                    int value = 0; try { value = Integer.parseInt(String.valueOf(m.getOrDefault("value", m.getOrDefault("Value", "0")))); } catch (Exception ignored) {} // parse value
                    String text = String.valueOf(m.getOrDefault("question", m.getOrDefault("Question", m.getOrDefault("QuestionText", "")))); // get question text
                    String answer = String.valueOf(m.getOrDefault("answer", m.getOrDefault("Answer", m.getOrDefault("CorrectAnswer", "")))); // get answer
                    out.add(new com.jeopardy.model.QuestionBuilder() // build question
                            .category(category) // set category
                            .value(value) // set value
                            .text(text) // set question text
                            .answer(answer) // set answer
                            .choiceA((String)m.getOrDefault("choiceA", m.getOrDefault("choicea", m.getOrDefault("OptionA", m.getOrDefault("optionA", null))))) // set choice A
                            .choiceB((String)m.getOrDefault("choiceB", m.getOrDefault("choiceb", m.getOrDefault("OptionB", m.getOrDefault("optionB", null))))) // set choice B
                            .choiceC((String)m.getOrDefault("choiceC", m.getOrDefault("choicec", m.getOrDefault("OptionC", m.getOrDefault("optionC", null))))) // set choice C
                            .choiceD((String)m.getOrDefault("choiceD", m.getOrDefault("choiced", m.getOrDefault("OptionD", m.getOrDefault("optionD", null))))) // set choice D
                            .build()); // build and add to list
                } // end for
            } // end if 'questions' is a list
            // Additional fallback: use tree parsing to find nodes with Category/QuestionText structure (handles sample_game_XML.xml)
            try {
                JsonNode root = xml.readTree(file); // read XML as tree
                List<JsonNode> found = new ArrayList<>(); // initialize list for found nodes
                collectQuestionNodes(root, found); // collect question nodes
                for (JsonNode node : found) { // iterate over found nodes
                    String category = firstText(node, "Category", "category");  // get category
                    String text = firstText(node, "QuestionText", "QuestionText", "question", "Question", "questionText"); // get question text
                    String answer = firstText(node, "CorrectAnswer", "Correctanswer", "correctAnswer", "answer", "Answer"); // get answer
                    String a = null, b = null, c = null, d = null; // initialize choices
                    // Options may be nested under 'Options' -> 'OptionA' etc.
                    JsonNode opts = node.get("Options"); // get options node
                    if (opts == null) opts = node.get("options"); // try lowercase 'options'
                    if (opts != null && opts.isObject()) { // if options node is an object
                        a = firstText(opts, "OptionA", "optionA", "Optiona", "optiona"); // get choice A
                        b = firstText(opts, "OptionB", "optionB", "Optionb", "optionb"); // get choice B
                        c = firstText(opts, "OptionC", "optionC", "Optionc", "optionc"); // get choice C
                        d = firstText(opts, "OptionD", "optionD", "Optiond", "optiond"); // get choice D
                    } // end if options node is an object
                    // or direct OptionA fields on the node
                    if (a == null) a = firstText(node, "OptionA", "optionA", "optiona", "Optiona"); // get choice A
                    if (b == null) b = firstText(node, "OptionB", "optionB", "optionb", "Optionb"); // get choice B
                    if (c == null) c = firstText(node, "OptionC", "optionC", "optionc", "Optionc"); // get choice C
                    if (d == null) d = firstText(node, "OptionD", "optionD", "optiond", "Optiond"); // get choice D

                    int value = 0; try { value = Integer.parseInt(firstText(node, "Value", "value", "Value")); } catch (Exception ignored) {} // parse value
                    out.add(new com.jeopardy.model.QuestionBuilder() // build question
                            .category(category == null ? "" : category) // set category
                            .value(value) // set value
                            .text(text == null ? "" : text) // set text
                            .answer(answer == null ? "" : answer) // set answer
                            .choiceA(a) // set choice A
                            .choiceB(b) // set choice B
                            .choiceC(c) // set choice C
                            .choiceD(d) // set choice D
                            .build()); // build and add to list
                } // end for
            } catch (Exception ignore) { 
                // if tree parsing fails, ignore
            } // end catch
        } // end catch
        return out; // return list of questions
    } // end load method

    /**
     * Recursively collects question nodes from the XML tree.
     * A question node is identified by the presence of Category and QuestionText fields.
     * @param node the current JSON node being inspected
     * @param out the list to collect question nodes into
     */

    private static void collectQuestionNodes(JsonNode node, List<JsonNode> out) { // recursively collect question nodes
        if (node == null) return; // return if node is null
        if (node.isObject()) { // if node is an object
            if (node.has("Category") || node.has("category") || node.has("QuestionText") || node.has("question") || node.has("Question")) { // check for question indicators
                out.add(node); // add node to output list
                return; // return after adding
            } // end if question indicators
            node.fields().forEachRemaining(e -> collectQuestionNodes(e.getValue(), out)); // recurse into fields
        } else if (node.isArray()) { // if node is an array
            for (JsonNode el : node) collectQuestionNodes(el, out); // recurse into array elements
        } // end else if
    } // end collectQuestionNodes method

    /**
     * Gets the first non-null text value for the given keys from the JsonNode.
     * @param node the JSON node to search
     * @param keys the keys to search for in order
     * @return the first found text value, or null if none found
     */

    private static String firstText(JsonNode node, String... keys) { // get first non-null text for given keys
        if (node == null) return null; // return null if node is null
        for (String k : keys) { // iterate over keys
            JsonNode v = node.get(k); // get value for key
            if (v != null && !v.isMissingNode()) return v.asText(); // return text if found
        } // end for
        return null; // return null if none found
    } // end firstText method
} // end class