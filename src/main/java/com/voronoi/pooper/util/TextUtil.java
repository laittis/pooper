package com.voronoi.pooper.util;

import com.mythicscape.batclient.interfaces.BatClientPlugin;
import com.mythicscape.batclient.interfaces.ParsedResult;

import java.awt.*;
import java.awt.font.TextAttribute;

public class TextUtil {

    // To use colors the general form is such:
    // BOLD+BACKGROUNDCOLOR+TEXTCOLOR+"Some text"+RESET
    // If you want bold text, BOLD must be first. BACKGROUNDCOLOR is also optional
    // HIGHLY recommended you end your string with RESET
    public static final String RESET = "\u001b[0m";
    public static final String BOLD = "\u001b[1m";
    public static final String BLACK = "\u001b[30m";
    public static final String RED = "\u001b[31m";
    public static final String GREEN = "\u001b[32m";
    public static final String YELLOW = "\u001b[33m";
    public static final String BLUE = "\u001b[34m";
    public static final String MAGENTA = "\u001b[35m";
    public static final String CYAN = "\u001b[36m";
    public static final String WHITE = "\u001b[37m";
    public static final String BGBLACK = "\u001b[40m";
    public static final String BGRED = "\u001b[41m";
    public static final String BGGREEN = "\u001b[42m";
    public static final String BGYELLOW = "\u001b[43m";
    public static final String BGBLUE = "\u001b[44m";
    public static final String BGMAGENTA = "\u001b[45m";
    public static final String BGCYAN = "\u001b[46m";
    public static final String BGWHITE = "\u001b[47m";

    /**
     * format color string
     * @param content need format string
     * @param color format color
     * @return formatted string
     */
    public static String colorText(String content,String color){
        return BOLD+color+content+RESET;
    }

    /** format time
     * @param time time
     * @return formatted string
     */
    public static long formatTime(long time){
        return (System.currentTimeMillis()-time)/1000;
    }

    /**
     * format duration
     * @param duration duration
     * @return formatted string
     */
    public static String formatDuration(long duration) {
        // Calculate time components
        long hours = duration / 3600000;
        long remainingAfterHours = duration % 3600000;

        long minutes = remainingAfterHours / 60000;
        long remainingAfterMinutes = remainingAfterHours % 60000;

        long seconds = remainingAfterMinutes / 1000;
        long milliseconds = remainingAfterMinutes % 1000;

        // Efficiently build the string
        StringBuilder sb = new StringBuilder(16); // Rough estimate of needed capacity
        if (hours > 0) {
            sb.append(hours).append("h");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m");
        }
        if (seconds > 0) {
            sb.append(seconds).append("s");
        }
        if (milliseconds > 0) {
            sb.append(milliseconds).append("ms");
        }

        return sb.toString();
    }

    /**
     * Appends text to the ParsedResult and applies color attributes.
     *
     * @param parsedResult     The ParsedResult to modify.
     * @param text             The text to append.
     * @param innerColor       The color for the inner text.
     * @param controlCharColor The color for control characters.
     */
    public static void appendText(ParsedResult parsedResult, String text, Color innerColor, Color controlCharColor) {
        String currentText = parsedResult.getStrippedText();

        // Remove any trailing whitespace or linebreaks
        currentText = currentText.replaceAll("\\s+$", "");

        int baseIndex = currentText.length();
        String modifiedText = currentText + text;
        parsedResult.setStrippedText(modifiedText);

        int index = baseIndex;
        while (index < modifiedText.length()) {
            char c = modifiedText.charAt(index);
            if ("()[]{},.'".indexOf(c) >= 0) {
                // Control character
                parsedResult.addAttribute(TextAttribute.FOREGROUND, controlCharColor, index, index + 1);
                index++;
            } else if (Character.isWhitespace(c)) {
                // Skip whitespace
                index++;
            } else {
                // Regular text
                int start = index;
                while (index < modifiedText.length() && " ()[]{},.'\n".indexOf(modifiedText.charAt(index)) == -1) {
                    index++;
                }
                int end = index;
                parsedResult.addAttribute(TextAttribute.FOREGROUND, innerColor, start, end);
            }
        }
    }
}
