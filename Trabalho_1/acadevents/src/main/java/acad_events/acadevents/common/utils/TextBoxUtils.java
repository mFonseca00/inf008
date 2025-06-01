package acad_events.acadevents.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TextBoxUtils {
    private static String displayDivisor;
    private static int width = 100;
    private static String displayLimitator = "|";

    public static void clearDisplay() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("powershell", "-Command", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static void spaceDisplay() {
        int space = 2;
        for (int i = 0; i < space; i++) System.out.println();
    }

    private static void printFormattedMessage(String message, String style) {
        System.out.println(" " + "_".repeat(width - (displayLimitator.length() * 2)));
        printEmptyLine();
        System.out.println(displayLimitator + style + centerTextContent(message) + StyleStrings.RESET + displayLimitator);
        printUnderLineDisplayDivisor();
    }

    public static void printTitle(String title) {
        printFormattedMessage(title, StyleStrings.BOLD);
    }

    public static void printWarn(String message) {
        printFormattedMessage(message, StyleStrings.YELLOW);
    }

    public static void printError(String error) {
        printFormattedMessage("Error: " + error, StyleStrings.RED);
    }

    public static void printSuccess(String success) {
        printFormattedMessage(success, StyleStrings.GREEN);
    }

    public static void printDisplayDivisor() {
        displayDivisor = displayLimitator + "-".repeat(width - (displayLimitator.length()*2)) + displayLimitator;
        System.out.println(displayDivisor);
    }

    public static void printUnderLineDisplayDivisor() {
        displayDivisor = displayLimitator + "_".repeat(width - (displayLimitator.length()*2)) + displayLimitator;
        System.out.println(displayDivisor);
    }

    public static void printEmptyLine() {
        System.out.println(emptyLine());
    }

    public static void printLeftText(String text) {
        for (String line : wrapText(text, width - (displayLimitator.length() * 2) - 2)) {
            System.out.println(displayLimitator + leftTextContent(line) + displayLimitator);
        }
    }

    public static String inputLine(Scanner scan, String inputLabel){
        int leftPadding = 2;
        int maxInputLength = width - 2 - leftPadding;
        List<String> labelLines = wrapText(inputLabel, maxInputLength);
        for (int i = 0; i < labelLines.size(); i++) {
            String line = labelLines.get(i);
            if (i == 0 && labelLines.size() == 1) {
                System.out.print(StyleStrings.GREEN + "  >>>" + " ".repeat(leftPadding) + line + StyleStrings.RESET);
            } else if (i == 0) {
                System.out.println(StyleStrings.GREEN + "  >>>" + " ".repeat(leftPadding) + line + StyleStrings.RESET);
            } else if (i == labelLines.size() - 1) {
                System.out.print(StyleStrings.GREEN + " ".repeat(6 + leftPadding) + line + StyleStrings.RESET);
            } else {
                System.out.println(StyleStrings.GREEN + " ".repeat(6 + leftPadding) + line + StyleStrings.RESET);
            }
        }
        String value = scan.nextLine();
        return value;
    }

    public static void printTableRow(String col1, String col2, String col3) {
        int totalWidth = width - (displayLimitator.length()*4);
        int colPadding = 2;
        int colWidth = (totalWidth - (displayLimitator.length()*2) * colPadding) / 3;
        int remainder = (totalWidth - (displayLimitator.length()*2) * colPadding) % 3;

        int colWidth1 = colWidth;
        int colWidth2 = colWidth;
        int colWidth3 = colWidth + remainder;

        List<String> lines1 = wrapText(col1, colWidth1);
        List<String> lines2 = wrapText(col2, colWidth2);
        List<String> lines3 = wrapText(col3, colWidth3);

        int maxLines = Math.max(lines1.size(), Math.max(lines2.size(), lines3.size()));

        System.out.println(displayLimitator + "_".repeat(colWidth1) + "_".repeat(colPadding) + displayLimitator +
                           "_".repeat(colWidth2) + "_".repeat(colPadding) + displayLimitator +
                           "_".repeat(colWidth3) + displayLimitator);

        for (int i = 0; i < maxLines; i++) {
            String part1 = i < lines1.size() ? lines1.get(i) : "";
            String part2 = i < lines2.size() ? lines2.get(i) : "";
            String part3 = i < lines3.size() ? lines3.get(i) : "";

            part1 = padRight(part1, colWidth1);
            part2 = padRight(part2, colWidth2);
            part3 = padRight(part3, colWidth3);

            System.out.println(displayLimitator + 
                                part1 + " ".repeat(colPadding) + displayLimitator +
                                part2 + " ".repeat(colPadding) + displayLimitator +
                                part3 + displayLimitator);
        }

        System.out.println(displayLimitator + "_".repeat(colWidth1) + "_".repeat(colPadding) + displayLimitator +
                           "_".repeat(colWidth2) + "_".repeat(colPadding) + displayLimitator +
                           "_".repeat(colWidth3) + displayLimitator);
    }

    public static String formatedCertificateText(String text) {
        StringBuilder certificateBuilder = new StringBuilder();
        String inputText = (text == null) ? "" : text;

        certificateBuilder.append(" " + "_".repeat(width - (displayLimitator.length()*2)))
                          .append(System.lineSeparator())
                          .append(displayLimitator)
                          .append(centerTextContent("AcadEvents Certificate"))
                          .append(displayLimitator)
                          .append(System.lineSeparator());

        certificateBuilder.append(emptyLine()).append(System.lineSeparator());
        certificateBuilder.append(emptyLine()).append(System.lineSeparator());

        int contentWidth = width - (displayLimitator.length() * 2);
        List<String> textLines = wrapText(inputText, contentWidth);

        if (textLines.isEmpty()) {
             certificateBuilder.append(displayLimitator)
                               .append(centerTextContent(""))
                               .append(displayLimitator)
                               .append(System.lineSeparator());
        } else {
            for (String line : textLines) {
                certificateBuilder.append(displayLimitator)
                                  .append(centerTextContent(line))
                                  .append(displayLimitator)
                                  .append(System.lineSeparator());
            }
        }
        
        certificateBuilder.append(emptyLine()).append(System.lineSeparator());
        certificateBuilder.append(emptyLine()).append(System.lineSeparator());

        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = date.format(new Date());
        String dateLine = "Certified at: " + currentDate;

        certificateBuilder.append(displayLimitator)
                          .append(centerTextContent(dateLine))
                          .append(displayLimitator)
                          .append(System.lineSeparator())
                          .append(displayLimitator + "_".repeat(width - (displayLimitator.length()*2)) + displayLimitator);
    
        return certificateBuilder.toString();
    }

    private static String padRight(String text, int length) {
        if (text.length() >= length) return text;
        return text + " ".repeat(length - text.length());
    }

    private static String emptyLine() {
        return displayLimitator + " ".repeat(width - (displayLimitator.length()*2)) + displayLimitator;
    }

    private static String centerTextContent(String text) {
        if (text.length() >= width - (displayLimitator.length()*2)) {
            return text.substring(0, width - (displayLimitator.length()*2));
        }
        int padding = (width - (displayLimitator.length()*2) - text.length()) / 2;
        String pad = " ".repeat(padding);
        String padRight = " ".repeat(width - (displayLimitator.length()*2) - text.length() - padding);
        return pad + text + padRight;
    }

    private static String leftTextContent(String text) {
        int leftPadding = 2;
        int rightPadding = width - (displayLimitator.length()*2) - text.length() - leftPadding;
        String padLeft = " ".repeat(leftPadding);
        String padRight = " ".repeat(rightPadding);
        return padLeft + text + padRight;
    }

    private static List<String> wrapText(String text, int maxWidth) {
        List<String> lines = new ArrayList<>();
        text = text.trim();
        while (text.length() > maxWidth) {
            int breakAt = maxWidth;
            int lastSpace = text.lastIndexOf(' ', maxWidth);
            if (lastSpace > 0) {
                breakAt = lastSpace;
            }
            lines.add(text.substring(0, breakAt).trim());
            text = text.substring(breakAt).trim();
        }
        if (!text.isEmpty()) {
            lines.add(text);
        }
        return lines;
    }

    public static void pause(Scanner scan) {
        System.out.println();
        System.out.print("Press ENTER to continue...");
        scan.nextLine();
    }
}
