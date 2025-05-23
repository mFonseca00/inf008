package acad_events.acadevents.menu.subMenus;

import java.util.Scanner;

import acad_events.acadevents.menu.Menu;
import acad_events.acadevents.menu.enums.EventOption;
import acad_events.acadevents.utils.TextSeparators;

public class EventMenu extends Menu{
    
    @Override
    public void printOptions() {
        TextSeparators.printDisplayDivisor();       
        System.out.println("\tEvent Menu");
        super.listOptions(EventOption.values());
        System.out.printf("\tPlease select an option (1-4): ");
    }

    @Override
    public Object readOption(Scanner scan){
        EventOption option = null;
        while (option == null){
            String inputStr = scan.nextLine();
            if(inputStr.matches("\\d+")){
                int input = Integer.parseInt(inputStr);
                option = EventOption.fromInt(input);
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
