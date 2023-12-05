package com.solvd.buildingco.utilities;

import com.solvd.buildingco.exception.InvalidLineException;
import com.solvd.buildingco.exception.InvalidValueException;
import com.solvd.buildingco.exception.WordParsingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;


public class WordCounterUtils {
    private static final Logger LOGGER = LogManager.getLogger(WordCounterUtils.class);

    public static String generateCountedWordsList(Map<String, Integer> wordCounts) {
        /*
            populate the output content where each line follows this format:
                ...
                word = 10
                dog = 20
                goodness = 2
                ...
        */

        Set<Map.Entry<String, Integer>> wordCountsSet = wordCounts.entrySet();

        String outputContent =
                wordCountsSet.stream()
                        .map(entry -> {
                            String word = entry.getKey();
                            int count = entry.getValue();
                            return StringUtils.join(word, StringConstants.PADDED_EQUALS_OPERATOR, count);
                        })
                        .collect(Collectors.joining(StringConstants.NEWLINE));

        return outputContent;
    }

    public static Map<String, Integer> countWords(String content, Set<String> wordsToCount) {
        checkIfBlankContent(content);

        content = deleteParenthesizedNumbers(content);
        content = deleteParenthesizedRomanNumerals(content);
        content = replaceEmDashesWithWhitespace(content);

        Map<String, Integer> wordCounts = new HashMap<>();

        Arrays.stream(splitLines(content))
                .filter(line -> !isMetadataLine(line))
                .flatMap(line -> splitWords(line).stream())
                .forEach(word -> {
                    word = word.toLowerCase();
                    if (StringUtils.isNotBlank(word) && wordsToCount.contains(word)) {
                        updateWordCount(wordCounts, word);
                    }
                });

        return wordCounts;
    }

    public static Map<String, Integer> countWords(String content) {
        checkIfBlankContent(content);

        content = deleteParenthesizedNumbers(content);
        content = deleteParenthesizedRomanNumerals(content);
        content = replaceEmDashesWithWhitespace(content);

        // key is the word name and the integer is how many occurrences of said word
        Map<String, Integer> wordCounts = new HashMap<>();

        Arrays.stream(splitLines(content))
                .filter(line -> !isMetadataLine(line))
                /*
                    splitWords() will parse words, but will not consider number labels as words
                    (e.g., `(i)`, `(iv)` or `(1)`, `(100)`, etc.
                */
                .flatMap(line -> splitWords(line).stream())
                .forEach(word -> {
                    word = word.toLowerCase();
                    if (StringUtils.isNotBlank(word)) {
                        updateWordCount(wordCounts, word);
                    }
                });

        return wordCounts;
    }

    private static String replaceEmDashesWithWhitespace(String content) {
        /*
            Replaces the string "--" that denotes an intermission within a sentence with a single
             whitespace character, such as in this sentence:

                "People who write in this manner usually have a general emotional meaning--they
                dislike one thing and want to express solidarity with another--but they are not
                interested in the detail of what they are saying."
        */
        return StringUtils.replace(
                content,
                StringConstants.EM_DASH,
                StringConstants.SINGLE_WHITESPACE
        );
    }

    private static String deleteParenthesizedRomanNumerals(String content) {
        /*
            Removes any denotations for parenthesized roman numerals like
            "(iii)" or "(iv)", etc.
        */
        return content.replaceAll(
                RegExpConstants.PARENTHESIZED_ROMAN_NUMERALS,
                StringConstants.EMPTY_STRING
        );
    }

    private static String deleteParenthesizedNumbers(String content) {
        /*
            Removes any denotations for a numbered list item that are a number wrapped
            in parentheses like "(1)", "(2)", etc.
        */
        return content.replaceAll(
                RegExpConstants.PARENTHESIZED_NUMBERS,
                StringConstants.EMPTY_STRING
        );
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
                StringConstants.CARRIAGE_RETURN
                        + StringConstants.NEWLINE;

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
        return Arrays.stream(metadataLabels)
                .anyMatch(metadatumLabel -> StringUtils.startsWith(line, metadatumLabel));
    }

    public static ArrayList<String> splitWords(String line) {
        try {
            String[] splitWordsArray = line.split(RegExpConstants.SEPARATOR_CHARS);

            ArrayList<String> wordsList = new ArrayList<>();

            Arrays.stream(splitWordsArray)
                    .forEach(word -> {
                        if (StringUtils.isNotBlank(word)) {
                            word = StringUtils.strip(
                                    word,
                                    StringConstants.SINGLE_QUOTATION
                            );
                        }
                        wordsList.add(word);
                    });

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
