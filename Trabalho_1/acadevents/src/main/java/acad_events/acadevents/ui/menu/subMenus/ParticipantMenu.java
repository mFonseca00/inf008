package acad_events.acadevents.ui.menu.subMenus;

import java.util.Scanner;

import acad_events.acadevents.ui.menu.Menu;
import acad_events.acadevents.ui.menu.enums.ParticipantOption;
import acad_events.acadevents.utils.TextSeparators;

public class ParticipantMenu extends Menu{

    @Override
    public void printOptions() {
        TextSeparators.printDisplayDivisor();       
        System.out.println("\tParticipant Menu");
        super.listOptions(ParticipantOption.values());
        System.out.printf("\tPlease select an option (1-6): ");
    }

    @Override
    public Object readOption(Scanner scan){
        ParticipantOption option = null;
        while (option == null){
            String inputStr = scan.nextLine();
            if(inputStr.matches("\\d+")){
                int input = Integer.parseInt(inputStr);
                option = ParticipantOption.fromInt(input);
                if(option == null){
                    System.out.printf("\tInvalid option. Please insert a number (1-6): ");
                }
            } else{
                System.out.printf("\tinvalid input. Please insert a number (1-6): ");
            }
        } 
        return option;
    }
}
