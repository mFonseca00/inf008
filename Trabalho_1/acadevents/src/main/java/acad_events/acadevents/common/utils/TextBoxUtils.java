package acad_events.acadevents.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextBoxUtils {
    private static String displayDivisor;
    private static int width = 100;

    public static void spaceDisplay(){
        int space = 10;
        for(int i=0; i<space; i++) System.out.println();
    }

    public static void printTitle(String title){
        System.out.println(" " + "_".repeat(width - 2));
        printEmptyLine();
        System.out.println(centerText(title));
        printUnderLineDisplayDivisor();
    }

    public static void printDisplayDivisor(){
        displayDivisor = "|" + "-".repeat(width - 2) + "|";
        System.out.println(displayDivisor);
    }

    public static void printUnderLineDisplayDivisor(){
        displayDivisor = "|" + "_".repeat(width - 2) + "|";
        System.out.println(displayDivisor);
    }

    public static void printEmptyLine(){
        System.out.println(emptyLine());
    }

    public static void printLeftText(String text){
        for (String line : wrapText(text, width - 2 - 2)) {
            System.out.println(leftText(line));
        }
    }

    public static String inputLine(Scanner scan, String inputLabel){
        int leftPadding = 2;
        int maxInputLength = width - 2 - leftPadding;
        List<String> labelLines = wrapText(inputLabel, maxInputLength);
        for (int i = 0; i < labelLines.size(); i++) {
            String line = labelLines.get(i);
            if (i == 0 && labelLines.size() == 1) {
                System.out.print("  >>>" + " ".repeat(leftPadding) + line);
            } else if (i == 0) {
                System.out.println("  >>>" + " ".repeat(leftPadding) + line);
            } else if (i == labelLines.size() - 1) {
                System.out.print(" ".repeat(6 + leftPadding) + line);
            } else {
                System.out.println(" ".repeat(6 + leftPadding) + line);
            }
        }
        String value = scan.nextLine();
        return value;
    }

    public static void printTableRow(String col1, String col2, String col3) {
        int totalWidth = width - 4;
        int colPadding = 2;
        int colWidth = (totalWidth - 2 * colPadding) / 3;
        int remainder = (totalWidth - 2 * colPadding) % 3;

        int colWidth1 = colWidth;
        int colWidth2 = colWidth;
        int colWidth3 = colWidth + remainder;

        List<String> lines1 = wrapText(col1, colWidth1);
        List<String> lines2 = wrapText(col2, colWidth2);
        List<String> lines3 = wrapText(col3, colWidth3);

        int maxLines = Math.max(lines1.size(), Math.max(lines2.size(), lines3.size()));

        // Linha de divisão superior
        System.out.println("|" + "_".repeat(colWidth1) + "_".repeat(colPadding) + "|" +
                           "_".repeat(colWidth2) + "_".repeat(colPadding) + "|" +
                           "_".repeat(colWidth3) + "|");

        for (int i = 0; i < maxLines; i++) {
            String part1 = i < lines1.size() ? lines1.get(i) : "";
            String part2 = i < lines2.size() ? lines2.get(i) : "";
            String part3 = i < lines3.size() ? lines3.get(i) : "";

            part1 = padRight(part1, colWidth1);
            part2 = padRight(part2, colWidth2);
            part3 = padRight(part3, colWidth3);

            System.out.println("|" + part1 + " ".repeat(colPadding) + "|" + part2 + " ".repeat(colPadding) + "|" + part3 + "|");
        }

        // Linha de divisão inferior
        System.out.println("|" + "_".repeat(colWidth1) + "_".repeat(colPadding) + "|" +
                           "_".repeat(colWidth2) + "_".repeat(colPadding) + "|" +
                           "_".repeat(colWidth3) + "|");
    }

    // Função auxiliar para preencher à direita
    private static String padRight(String text, int length) {
        if (text.length() >= length) return text;
        return text + " ".repeat(length - text.length());
    }

    private static String emptyLine() {
        return "|" + " ".repeat(width - 2) + "|";
    }

    private static String centerText(String text) {
        if (text.length() >= width - 2) {
            return "|" + text.substring(0, width - 2) + "|";
        }
        int padding = (width - 2 - text.length()) / 2;
        String pad = " ".repeat(padding);
        String padRight = " ".repeat(width - 2 - text.length() - padding);
        return "|" + pad + text + padRight + "|";
    }

    private static String leftText(String text){
        int leftPadding = 2;
        int rightPadding = width - 2 - text.length() - leftPadding;
        String padLeft = " ".repeat(leftPadding);
        String padRight = " ".repeat(rightPadding);
        return "|" + padLeft + text + padRight + "|";
    }

    private static List<String> wrapText(String text, int maxWidth) {
        List<String> lines = new ArrayList<>();
        text = text.trim();
        while (text.length() > maxWidth) {
            int breakAt = maxWidth;
            // Procura o último espaço antes do limite
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
