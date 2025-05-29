package acad_events.acadevents.ui.menu.subMenus;

import java.util.Scanner;

import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.ui.menu.Menu;
import acad_events.acadevents.ui.menu.enums.MainMenuOption;

public class MainMenu extends Menu {
    
    @Override
    public void printOptions() {
        TextBoxUtils.printTitle("Welcome to the Academy Events !");       
        MenuUtils.listEnumOptions(MainMenuOption.class);
    }

    @Override
    public Object readOption(Scanner scan){
        MainMenuOption option = null;
        while (option == null){
            String inputStr = TextBoxUtils.inputLine(scan, "Please select an option (1-5): ");
            if(inputStr.matches("\\d+")){
                int input = Integer.parseInt(inputStr);
                option = MainMenuOption.fromInt(input);
                if(option == null){
                    TextBoxUtils.inputLine(scan, "Invalid option. Please insert a number (1-5): ");
                }
            } else{
                TextBoxUtils.inputLine(scan, "invalid input. Please insert a number (1-5): ");
            }
        } 
        return option;
    }
}
