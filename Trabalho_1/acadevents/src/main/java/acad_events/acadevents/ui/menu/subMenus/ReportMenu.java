package acad_events.acadevents.ui.menu.subMenus;

import java.util.Scanner;

import acad_events.acadevents.ui.menu.Menu;
import acad_events.acadevents.ui.menu.enums.ReportOption;
import acad_events.acadevents.utils.TextSeparators;

public class ReportMenu extends Menu{
    @Override
    public void printOptions() {
        TextSeparators.printDisplayDivisor();       
        System.out.println("\tReport Menu");
        super.listOptions(ReportOption.values());
        System.out.printf("\tPlease select an option (1-3): ");
    }

    @Override
    public Object readOption(Scanner scan){
        ReportOption option = null;
        while (option == null){
            String inputStr = scan.nextLine();
            if(inputStr.matches("\\d+")){
                int input = Integer.parseInt(inputStr);
                option = ReportOption.fromInt(input);
                if(option == null){
                    System.out.printf("\tInvalid option. Please insert a number (1-3): ");
                }
            } else{
                System.out.printf("\tinvalid input. Please insert a number (1-3): ");
            }
        } 
        return option;
    }
}
