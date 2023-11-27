package com.solvd.buildingco.utilities;

import com.solvd.buildingco.exception.InvalidContentException;
import com.solvd.buildingco.exception.InvalidLineException;
import com.solvd.buildingco.exception.WordParsingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.solvd.buildingco.utilities.RegExpPatternConstants.*;
import static com.solvd.buildingco.utilities.StringConstants.*;

public class WordCounterUtils {
    private static final Logger LOGGER = LogManager.getLogger(WordCounterUtils.class);

    public static String generateCountedWordsList(Map<String, Integer> wordCounts) {
        ArrayList<String> outputLines = new ArrayList<>();

        /*
            populate the output content where each line follows this format:
                ...
                word = 10
                dog = 20
                goodness = 2
                ...
        */
        final String PADDED_EQUALS_OPERAND_STRING = " = ";
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            String word = entry.getKey();
            Integer wordCount = entry.getValue();
            outputLines.add(word + PADDED_EQUALS_OPERAND_STRING + wordCount);
        }

        // join each line with a newline
        String outputContent = StringUtils.join(
                outputLines,
                NEWLINE_CHAR_STRING
        );

        return outputContent;
    }

    public static Map<String, Integer> countWords(String content) {
        if (StringUtils.isBlank(content)) {
            final String BLANK_CONTENT_MESSAGE = "Content for word counting is blank.";
            LOGGER.warn(BLANK_CONTENT_MESSAGE);
            throw new InvalidContentException(BLANK_CONTENT_MESSAGE);
        }

        // split the content into lines for every "\r\n"
        final String lineSeparatorCharacters =
                CARRIAGE_RETURN_CHAR_STRING
                        + NEWLINE_CHAR_STRING;
        String[] lines = StringUtils.split(
                content,
                lineSeparatorCharacters
        );

        // key is the word name and the integer is how many occurrences of said word
        Map<String, Integer> wordCounts = new HashMap<>();
        final int WORD_COUNT_DEFAULT_VALUE = 0;
        final int WORD_COUNT_INCREMENT_VALUE = 1;
        for (String line : lines) {
            // skip to next iteration if line is metadata
            if (isMetadataLine(line)) {
                continue;
            }

            /*
                splitWords() will parse words, but will not consider number labels as words
                (e.g., `(i)`, `(iv)` or `(1)`, `(100)`, etc.
            */
            for (String word : splitWords(line)) {
                if (StringUtils.isNotBlank(word)) {
                    // desensitize the word
                    word = word.toLowerCase();

                    // update or initialize the count for the given word
                    wordCounts.put(
                            word,
                            wordCounts.getOrDefault(
                                    word,
                                    WORD_COUNT_DEFAULT_VALUE
                            ) + WORD_COUNT_INCREMENT_VALUE
                    );
                }
            }
        }

        return wordCounts;
    }

    public static boolean isMetadataLine(String line) {
        if (line == null) {
            final String NULL_LINE_MESSAGE = "Line for metadata check is null.";
            LOGGER.warn(NULL_LINE_MESSAGE);
            throw new InvalidLineException(NULL_LINE_MESSAGE);
        }

        // metadata clues that should appear at the head of the document
        final String TITLE_LINE_LABEL = "Title:";
        final String AUTHOR_LINE_LABEL = "Author:";
        final String SOURCE_URL_LINE_LABEL = "Source URL:";

        final String[] metadataLabels = {
                TITLE_LINE_LABEL,
                AUTHOR_LINE_LABEL,
                SOURCE_URL_LINE_LABEL
        };

        // true if the line starts with any of the items in metadataLabels
        boolean isTrue = false;
        for (String metadatumLabel : metadataLabels) {
            if (StringUtils.startsWith(line, metadatumLabel)) {
                isTrue = true;
                break;
            }
        }

        return isTrue;
    }

    public static ArrayList<String> splitWords(String line) {
        try {

        /*
            Removes any denotations for a numbered list item that are a number wrapped
            in parentheses like "(1)", "(2)", etc. Also for the same type of denotation
            but wrapped roman numerals like "(iii)" or "(iv)".
        */
            line = line.replaceAll(
                    NUMBERS_IN_PARENTHESES_PATTERN,
                    EMPTY_STRING
            );
            line = line.replaceAll(
                    ROMAN_NUMERAL_IN_PARENTHESES_PATTERN,
                    EMPTY_STRING
            );
        /*
            Replaces the string "--" that denotes an intermission within a sentence with a single
             whitespace character, such as in this sentence:

                "People who write in this manner usually have a general emotional meaning--they
                dislike one thing and want to express solidarity with another--but they are not
                interested in the detail of what they are saying."
        */
            line = StringUtils.replace(
                    line,
                    MANUAL_EM_DASH_STRING,
                    SINGLE_WHITESPACE_CHAR_STRING
            );

            String[] splitWordsArray = line.split(SEPARATOR_CHARS);

            ArrayList<String> wordsList = new ArrayList<>();

            for (String word : splitWordsArray) {
                // ensure `word` is not null or an empty string
                if (StringUtils.isNotBlank(word)) {

                    // remove single quotation marks from word if it has them
                    word = StringUtils.strip(
                            word,
                            SINGLE_QUOTATION
                    );

                    // add word to list
                    wordsList.add(word);
                }
            }

            return wordsList;

        } catch (RuntimeException e) {
            final String WORD_PARSING_EXCEPTION_MESSAGE_LABEL = "Error parsing words in line: ";
            String wordParsingExceptionMessage =
                    WORD_PARSING_EXCEPTION_MESSAGE_LABEL + e.getMessage();
            LOGGER.warn(wordParsingExceptionMessage);
            throw new WordParsingException(wordParsingExceptionMessage);
        }
    }
}
