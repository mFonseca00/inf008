package acad_events.acadevents.ui.menu.subMenus;

import java.util.Scanner;

import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.ui.menu.Menu;
import acad_events.acadevents.ui.menu.enums.EventOption;

public class EventMenu extends Menu{
    
    @Override
    public void printOptions() {
        TextBoxUtils.printTitle("Event Menu");
        super.listOptions(EventOption.values());
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
                    TextBoxUtils.inputLine(scan, "Invalid option. Please insert a number (1-4): ");
                }
            } else{
                TextBoxUtils.inputLine(scan, "invalid input. Please insert a number (1-4): ");
            }
        } 
        return option;
    }
}
