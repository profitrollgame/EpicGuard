package me.xneox.epicguard.core.util;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.jetbrains.annotations.NotNull;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtils {
    private static final Pattern DIATRICAL_MARKS_PATTERN = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    public static String revertLeet(String str) {
        str = str.toLowerCase(Locale.ROOT);

        str = str.replace("0", "o");
        str = str.replace("1", "i");
        str = str.replace("2", "z");
        str = str.replace("3", "e");
        str = str.replace("4", "a");
        str = str.replace("5", "s");
        str = str.replace("6", "g");
        str = str.replace("7", "t");
        str = str.replace("8", "b");
        str = str.replace("9", "g");
        str = str.replace("&", "a");
        str = str.replace("@", "a");
        str = str.replace("(", "c");
        str = str.replace("#", "h");
        str = str.replace("!", "i");
        str = str.replace("]", "i");
        str = str.replace("|", "i");
        str = str.replace("}", "i");
        str = str.replace("?", "o");
        str = str.replace("$", "s");

        return str;
    }

    private static String stripAccents(final String input) {
        StringBuilder decomposed = new StringBuilder(Normalizer.normalize(input, Normalizer.Form.NFD));
        for (int i = 0; i < decomposed.length(); i++) {
            final char charAtPosition = decomposed.charAt(i);
            if (charAtPosition == 'Ł') {
                decomposed.setCharAt(i, 'L');
            } else if (charAtPosition == 'ł') {
                decomposed.setCharAt(i, 'l');
            } else if (charAtPosition == 'Ø') {
                decomposed.setCharAt(i, 'O');
            } else if (charAtPosition == 'ø') {
                decomposed.setCharAt(i, 'o');
            }
        }

        // Strip crazy stuff like zero-width spaces
        return DIATRICAL_MARKS_PATTERN.matcher(decomposed).replaceAll("");
    }

    public static String sanizitzeString(String str) {
        return revertLeet(stripAccents(str));
    }

    /**
     * Get the similarity in percent between two strings.
     *
     * @param string1 The first string.
     * @param string2 The second string.
     * @return A integer between 0 and 100.
     */
    public static int stringSimilarityInPercent(@NotNull String string1, @NotNull String string2) {
        return FuzzySearch.weightedRatio(string1, string2);
    }
}
