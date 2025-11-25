package com.jeopardy;

import org.junit.jupiter.api.*;
import com.jeopardy.model.Player;
import com.jeopardy.model.TurnRecord;
import com.jeopardy.report.ReportGenerator;

import java.io.File;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ReportTests{
    private ReportGenerator rg;
    private File reportTest;

    @BeforeEach
    void initialize() throws Exception{
        rg = new ReportGenerator();
        reportTest = File.createTempFile("report", ".txt");
        reportTest.deleteOnExit();
    }

    @Test
    void reportGenerationTest() throws Exception{
        Player player = new Player("P1", "Alice");
        TurnRecord tr = new TurnRecord("P1", "Alice", "Functions", 100, "What is the purpose of a function parameter?", 
                                        "(A) Store output", "(B) Pass data into function", "(C) Return value", "(D) Stop execution",
                                        "B", true, 100, 100, ZonedDateTime.now());

        rg.generateTxtReport(reportTest, Collections.singletonList(player), Collections.singletonList(tr));
        assertTrue(reportTest.length() > 0);
        String content = Files.readString(reportTest.toPath());
        assertTrue(content.contains("Alice"));
        assertTrue(content.contains("What is the purpose of a function parameter?"));
        assertTrue(content.contains("(A) Store output"));
        assertTrue(content.contains("(B) Pass data into function"));
    }
}
