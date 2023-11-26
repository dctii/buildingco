package com.solvd.buildingco;

import com.solvd.buildingco.utilities.WordCounterUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class WordCounter {
    private static final Logger LOGGER = LogManager.getLogger(WordCounter.class);
    private static final String INPUT_FILE_PATHNAME =
            "src/main/resources/article.txt";
    private static final String OUTPUT_FILE_PATHNAME =
            "src/main/resources/counted-words.txt";

    public static void main(String[] args) {
        try {
            final Charset UTF_8 = StandardCharsets.UTF_8;

            // read input file content and store
            File inputFile = new File(INPUT_FILE_PATHNAME);
            String inputFileContent = FileUtils.readFileToString(
                    inputFile,
                    UTF_8
            );


            /*
                count words in file and set key value to the word and the value to the count of
                occurrences
            */
            Map<String, Integer> wordCounts =
                    WordCounterUtils.countWords(inputFileContent);


            // prepare the counted words output content to be written to the outputFile
            String outputContent =
                    WordCounterUtils.generateCountedWordsList(wordCounts);


            // write the content to the output file
            File outputFile = new File(OUTPUT_FILE_PATHNAME);
            FileUtils.writeStringToFile(
                    outputFile,
                    outputContent,
                    UTF_8
            );

        } catch (IOException e) {
            final String IOEXCEPTION_ERROR_MESSAGE = "IO Exception occurred in WordCounter";
            LOGGER.error(IOEXCEPTION_ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }
}
