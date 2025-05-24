package acad_events.acadevents.ui.menu.subMenus;

import java.util.Scanner;

import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.ui.menu.Menu;
import acad_events.acadevents.ui.menu.enums.ParticipantOption;

public class ParticipantMenu extends Menu{

    @Override
    public void printOptions() {
        TextBoxUtils.printTitle("Participant Menu");       
        super.listOptions(ParticipantOption.values());
    }

    @Override
    public Object readOption(Scanner scan){
        ParticipantOption option = null;
        while (option == null){
            String inputStr = TextBoxUtils.inputLine(scan, "Please select an option (1-6): ");
            if(inputStr.matches("\\d+")){
                int input = Integer.parseInt(inputStr);
                option = ParticipantOption.fromInt(input);
                if(option == null){
                    TextBoxUtils.inputLine(scan, "Invalid option. Please insert a number (1-6): ");
                }
            } else{
                TextBoxUtils.inputLine(scan, "invalid input. Please insert a number (1-6): ");
            }
        } 
        return option;
    }
}
