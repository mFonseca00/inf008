package acad_events.acadevents.menu.subMenus;

import java.util.Scanner;

import acad_events.acadevents.menu.Menu;
import acad_events.acadevents.menu.enums.MainMenuOption;
import acad_events.acadevents.utils.TextSeparators;

public class MainMenu extends Menu {
    
    @Override
    public void printOptions() {
        TextSeparators.printDisplayDivisor();       
        System.out.println("\tWelcome to the Academy Events !");
        super.listOptions(MainMenuOption.values());
        System.out.printf("\tPlease select an option (1-4): ");
    }

    @Override
    public Object readOption(Scanner scan){
        MainMenuOption option = null;
        while (option == null){
            String inputStr = scan.nextLine();
            if(inputStr.matches("\\d+")){
                int input = Integer.parseInt(inputStr);
                option = MainMenuOption.fromInt(input);
                if(option == null){
                    System.out.printf("\tInvalid option.\n\tPlease insert a number (1-4): ");
                }
            } else{
                System.out.printf("\tinvalid input.\n\tPlease insert a number (1-4): ");
            }
        } 
        return option;
    }
}
