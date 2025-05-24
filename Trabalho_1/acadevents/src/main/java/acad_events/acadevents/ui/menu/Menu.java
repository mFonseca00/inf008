package acad_events.acadevents.ui.menu;

import java.util.Scanner;

import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.ui.menu.enums.Interfaces.IMenuOption;

public abstract class Menu {
    

    public abstract Object readOption(Scanner scan);

    public abstract void printOptions();

    public void listOptions(IMenuOption[] options){
        TextBoxUtils.printEmptyLine();
        for(IMenuOption option : options){
            TextBoxUtils.printLeftText(option.getValue() + " - " + option.getDescription());
        }
        TextBoxUtils.printUnderLineDisplayDivisor();
    }

    

}
