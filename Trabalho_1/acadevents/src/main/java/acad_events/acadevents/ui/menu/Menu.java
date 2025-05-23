package acad_events.acadevents.ui.menu;

import java.util.Scanner;

import acad_events.acadevents.ui.menu.enums.Interfaces.IMenuOption;
import acad_events.acadevents.utils.TextSeparators;

public abstract class Menu {
    

    public abstract Object readOption(Scanner scan);

    public abstract void printOptions();

    public void listOptions(IMenuOption[] options){
        TextSeparators.printDisplayDivisor();
        for(IMenuOption option : options){
            System.out.println("\t" + option.getValue() + " - " + option.getDescription());
        }
        TextSeparators.printDisplayDivisor();
    }

    

}
