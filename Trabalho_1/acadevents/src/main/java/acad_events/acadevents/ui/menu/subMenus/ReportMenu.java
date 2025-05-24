package acad_events.acadevents.ui.menu.subMenus;

import java.util.Scanner;

import acad_events.acadevents.common.utils.MenuUtils;
import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.ui.menu.Menu;
import acad_events.acadevents.ui.menu.enums.ReportOption;

public class ReportMenu extends Menu{
    @Override
    public void printOptions() {
        TextBoxUtils.printTitle("Report Menu");       
        MenuUtils.listEnumOptions(ReportOption.class);
    }

    @Override
    public Object readOption(Scanner scan){
        ReportOption option = null;
        while (option == null){
            String inputStr = TextBoxUtils.inputLine(scan, "Please select an option (1-3): ");
            if(inputStr.matches("\\d+")){
                int input = Integer.parseInt(inputStr);
                option = ReportOption.fromInt(input);
                if(option == null){
                    TextBoxUtils.inputLine(scan, "Invalid option. Please insert a number (1-3): ");
                }
            } else{
                TextBoxUtils.inputLine(scan, "invalid input. Please insert a number (1-3): ");
            }
        } 
        return option;
    }
}
