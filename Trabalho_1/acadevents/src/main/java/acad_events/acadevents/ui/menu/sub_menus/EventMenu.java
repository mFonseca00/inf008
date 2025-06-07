package acad_events.acadevents.ui.menu.sub_menus;

import java.util.Scanner;

import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.ui.menu.Menu;
import acad_events.acadevents.ui.menu.enums.EventOption;

public class EventMenu extends Menu{
    
    @Override
    public void printOptions() {
        TextBoxUtils.printTitle("Event Menu");
        MenuUtils.listEnumOptions(EventOption.class);
    }

    @Override
    public Object readOption(Scanner scan){
        EventOption option = null;
        while (option == null){
            String inputStr = TextBoxUtils.inputLine(scan, "Please select an option (1-4): ");
            if(inputStr.matches("\\d+")){
                int input = Integer.parseInt(inputStr);
                option = EventOption.fromInt(input);
                if(option == null){
                    TextBoxUtils.printError("Invalid option. Please insert a number (1-4): ");
                }
            } else{
                TextBoxUtils.printError("invalid input. Please insert a number (1-4): ");
            }
        } 
        return option;
    }
}
