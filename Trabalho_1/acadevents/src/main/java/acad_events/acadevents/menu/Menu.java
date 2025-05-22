package acad_events.acadevents.menu;

import java.util.Scanner;

import acad_events.acadevents.menu.enums.Interfaces.IMenuOption;

public abstract class Menu {
    private String displayDivisor;
    private int countDivisor = 50;

    public abstract Object readOption(Scanner scan);

    public abstract void printOptions();

    public void listOptions(IMenuOption[] options){
        printDisplayDivisor();
        for(IMenuOption option : options){
            System.out.println("\t" + option.getValue() + " - " + option.getDescription());
        }
        printDisplayDivisor();
    }

    public void printDisplayDivisor(){
        this.displayDivisor = "=".repeat(countDivisor);
        System.out.println(displayDivisor);
    }

    public static void spaceDisplay(){
        int space = 10;
        for(int i=0; i<space; i++) System.out.println();
    }

}
