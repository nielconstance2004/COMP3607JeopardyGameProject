package com.jeopardy;

import com.jeopardy.loaders.XmlQuestionLoader;
import com.jeopardy.model.Question;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExtendedLoaderTests {

    @Test
    public void sampleXmlParsesComplexStructure() throws Exception {
        File f = new File("sample_game_XML.xml");
        assertTrue(f.exists(), "sample_game_XML.xml must exist in project root for this test");
        XmlQuestionLoader loader = new XmlQuestionLoader();
        List<Question> list = loader.load(f);
        
        assertFalse(list.isEmpty(), "Loader should find questions in provided sample XML");
        boolean found = false;
        for (Question q : list) {
            if (q.getText() != null && !q.getText().trim().isEmpty()) {
                // ensure choices are parsed for at least one question
                if ((q.getChoiceA() != null && !q.getChoiceA().trim().isEmpty()) ||
                        (q.getChoiceB() != null && !q.getChoiceB().trim().isEmpty())) {
                    found = true;
                    break;
                }
            }
        }
        assertTrue(found, "At least one question with text and choices should be parsed from sample_game_XML.xml");
    }
}
