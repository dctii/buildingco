package com.solvd.buildingco.utilities;

import com.solvd.buildingco.exception.InvalidValueException;
import com.solvd.buildingco.exception.InvalidLineException;
import com.solvd.buildingco.exception.WordParsingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        final String PADDED_EQUALS_OPERATOR_STRING = " = ";
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            String word = entry.getKey();
            int wordCount = entry.getValue();
            outputLines.add(word + PADDED_EQUALS_OPERATOR_STRING + wordCount);
        }

        // join each line with a newline
        String outputContent = StringUtils.join(
                outputLines,
                NEWLINE_CHAR_STRING
        );

        return outputContent;
    }

    public static Map<String, Integer> countWords(String content, Set<String> wordsToCount) {
        checkIfBlankContent(content);

        Map<String, Integer> wordCounts = new HashMap<>();

        for (String line : splitLines(content)) {
            if (isMetadataLine(line)) {
                continue;
            }

            for (String word : splitWords(line)) {
                word = word.toLowerCase();
                if (wordsToCount.contains(word)) {
                    updateWordCount(wordCounts, word);
                }
            }
        }

        return wordCounts;
    }

    public static Map<String, Integer> countWords(String content) {
        checkIfBlankContent(content);

        /*
            Removes any denotations for a numbered list item that are a number wrapped
            in parentheses like "(1)", "(2)", etc. Also for the same type of denotation
            but wrapped roman numerals like "(iii)" or "(iv)".
        */
        content = content.replaceAll(
                NUMBERS_IN_PARENTHESES_PATTERN,
                EMPTY_STRING
        );
        content = content.replaceAll(
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
        content = StringUtils.replace(
                content,
                MANUAL_EM_DASH_STRING,
                SINGLE_WHITESPACE_CHAR_STRING
        );


        // key is the word name and the integer is how many occurrences of said word
        Map<String, Integer> wordCounts = new HashMap<>();
        for (String line : splitLines(content)) {
            // skip to next iteration if line is metadata
            if (isMetadataLine(line)) {
                continue;
            }
            /*
                splitWords() will parse words, but will not consider number labels as words
                (e.g., `(i)`, `(iv)` or `(1)`, `(100)`, etc.
            */
            for (String word : splitWords(line)) {
                word = word.toLowerCase();
                if (StringUtils.isNotBlank(word)) {
                    updateWordCount(wordCounts, word);
                }
            }
        }

        return wordCounts;
    }

    private static void checkIfBlankContent(String content) {
        if (StringUtils.isBlank(content)) {
            final String BLANK_CONTENT_MESSAGE = "Content for word counting is blank.";
            LOGGER.warn(BLANK_CONTENT_MESSAGE);
            throw new InvalidValueException(BLANK_CONTENT_MESSAGE);
        }
    }

    private static void updateWordCount(Map<String, Integer> wordCounts, String word) {
        final int WORD_COUNT_DEFAULT_VALUE = 0;
        final int WORD_COUNT_INCREMENT_VALUE = 1;
        wordCounts.put(
                word,
                wordCounts.getOrDefault(
                        word,
                        WORD_COUNT_DEFAULT_VALUE
                ) + WORD_COUNT_INCREMENT_VALUE
        );
    }

    private static String[] splitLines(String content) {
        final String lineSeparatorCharacters =
                CARRIAGE_RETURN_CHAR_STRING
                        + NEWLINE_CHAR_STRING;

        return StringUtils.split(
                content,
                lineSeparatorCharacters
        );
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

    private WordCounterUtils() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
