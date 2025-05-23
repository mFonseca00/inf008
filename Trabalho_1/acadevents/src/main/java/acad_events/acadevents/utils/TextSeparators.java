package acad_events.acadevents.utils;

public class TextSeparators {
    private static String displayDivisor;
    private static int countDivisor = 50;

    public static void printDisplayDivisor(){
        displayDivisor = "=".repeat(countDivisor);
        System.out.println(displayDivisor);
    }

    public static void spaceDisplay(){
        int space = 10;
        for(int i=0; i<space; i++) System.out.println();
    }
}
