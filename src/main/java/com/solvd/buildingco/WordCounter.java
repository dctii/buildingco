package com.solvd.buildingco;

import com.solvd.buildingco.utilities.WordCounterUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class WordCounter {
    private static final Logger LOGGER = LogManager.getLogger(WordCounter.class);
    private static final String INPUT_FILE_PATHNAME =
            "src/main/resources/article.txt";
    private static final String OUTPUT_FILE_PATHNAME =
            "src/main/resources/counted-words.txt";
    private static final Set<String> wordsToCount
            = Set.of("the", "with", "metaphors", "time");

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            LOGGER.info("Choose an option, input '1' or '2':");
            LOGGER.info("1. Count these words only: " + wordsToCount.toString());
            LOGGER.info("2. Count all words");
            int choice = scanner.nextInt();
            scanner.close();

            // read input file content and store
            String inputFileContent = FileUtils.readFileToString(
                    new File(INPUT_FILE_PATHNAME),
                    StandardCharsets.UTF_8
            );
            /*
                count words in file and set key value to the word and the value to the count of
                occurrences
            */
            Map<String, Integer> wordCounts;
            if (choice == 1) {
                wordCounts = WordCounterUtils.countWords(inputFileContent, wordsToCount);
            } else {
                wordCounts = WordCounterUtils.countWords(inputFileContent);
            }

            // prepare the counted words output content to be written to the outputFile
            String outputContent =
                    WordCounterUtils.generateCountedWordsList(wordCounts);


            // write the content to the output file
            File outputFile = new File(OUTPUT_FILE_PATHNAME);
            FileUtils.writeStringToFile(
                    outputFile,
                    outputContent,
                    StandardCharsets.UTF_8
            );


            LOGGER.info(
                    "Word counts successfully output as a list in '{}'", OUTPUT_FILE_PATHNAME
            );

        } catch (IOException e) {
            final String IOEXCEPTION_ERROR_MESSAGE = "IO Exception occurred in WordCounter";
            LOGGER.error(IOEXCEPTION_ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }
}
