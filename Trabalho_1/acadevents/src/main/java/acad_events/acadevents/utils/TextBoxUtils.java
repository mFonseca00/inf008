package acad_events.acadevents.utils;

import java.util.Scanner;

public class TextBoxUtils {
    private static String displayDivisor;
    private static int width = 80;

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
        System.out.println(leftText(text));
    }

    public static String inputLine(Scanner scan, String inputLabel){
        int leftPadding = 2;
        int maxInputLength = width - 2 - leftPadding - inputLabel.length();
        if (maxInputLength < 1) maxInputLength = 1;

        System.out.print("  >>>" + " ".repeat(leftPadding) + inputLabel);

        String value = scan.nextLine();
        if (value.length() > maxInputLength) {
            value = value.substring(0, maxInputLength);
        }

        return value;
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

}
