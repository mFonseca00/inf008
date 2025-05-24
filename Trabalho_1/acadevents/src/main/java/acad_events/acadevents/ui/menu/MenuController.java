package acad_events.acadevents.ui.menu;

import java.util.Scanner;

import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.ui.functionalities.ParticipantFunctionalities;
import acad_events.acadevents.ui.menu.enums.EventOption;
import acad_events.acadevents.ui.menu.enums.MainMenuOption;
import acad_events.acadevents.ui.menu.enums.ParticipantOption;
import acad_events.acadevents.ui.menu.enums.ReportOption;
import acad_events.acadevents.ui.menu.subMenus.EventMenu;
import acad_events.acadevents.ui.menu.subMenus.MainMenu;
import acad_events.acadevents.ui.menu.subMenus.ParticipantMenu;
import acad_events.acadevents.ui.menu.subMenus.ReportMenu;

public class MenuController {
    private final MainMenu mainMenu = new MainMenu();
    private final EventMenu eventMenu = new EventMenu();
    private final ParticipantMenu partMenu = new ParticipantMenu();
    private final ReportMenu reportMenu = new ReportMenu();
    private final ParticipantFunctionalities partFunctions;

    public MenuController(ParticipantFunctionalities partFunctions) {
        this.partFunctions = partFunctions;
    }

    public void run(){
        Scanner scan = new Scanner(System.in);
        MainMenuOption option = null;
        do{
            mainMenu.printOptions();
            Object optionObj = mainMenu.readOption(scan);
            option = (MainMenuOption) optionObj;
            TextBoxUtils.spaceDisplay();
            switch(option){
                case MANAGE_EVENTS:
                    eventsSubMenu(scan);
                    break;
                case MANAGE_PARTICIPANTS:
                    participantsSubMenu(scan);
                    break;
                case GENERATE_REPORTS:
                    reportsSubMenu(scan);
                    break;
                case EXIT:
                    System.out.println("\tExiting...");
                    break;
            }
        } while(option != MainMenuOption.EXIT);
        scan.close();
    }

    private void eventsSubMenu(Scanner scan){
        EventOption option = null;
        do{
            eventMenu.printOptions();
            Object optionObj = eventMenu.readOption(scan);
            option = (EventOption) optionObj;
            TextBoxUtils.spaceDisplay();
            switch (option) {
                case CREATE_EVENT:
                    // ToDo
                    TextBoxUtils.pause(scan);
                    break;
                case DELETE_EVENT:
                    // ToDO
                    TextBoxUtils.pause(scan);
                    break;
                case LIST_EVENTS:
                    // ToDo
                    TextBoxUtils.pause(scan);
                    break;
                case RETURN:
                    System.out.println("\nReturning...");
                    break;
            }
        }while(option != EventOption.RETURN);
    }

    private void participantsSubMenu(Scanner scan){
        ParticipantOption option = null;
        do{
            partMenu.printOptions();
            Object optionObj = partMenu.readOption(scan);
            option = (ParticipantOption) optionObj;
            TextBoxUtils.spaceDisplay();
            switch (option){
                case REGISTER_PARTICIPANT:
                    partFunctions.registerNew(scan);
                    TextBoxUtils.pause(scan);
                    break;
                case DELETE_PARTICIPANT:
                    partFunctions.remove(scan);
                    TextBoxUtils.pause(scan);
                    break;
                case LIST_PARTICIPANTS:
                    partFunctions.listAll();
                    TextBoxUtils.pause(scan);
                    break;
                case ENROLL_IN_EVENT:
                    // ToDo
                    TextBoxUtils.pause(scan);
                    break;
                case GENERATE_CERTIFICATE:
                    // ToDo
                    TextBoxUtils.pause(scan);
                    break;
                case RETURN:
                    System.out.println("\nReturning...");
                    break;
            }
        }while(option != ParticipantOption.RETURN);
    }

    private void reportsSubMenu(Scanner scan){
        ReportOption option = null;
        do {
            reportMenu.printOptions();
            Object optionObj = reportMenu.readOption(scan);
            option = (ReportOption) optionObj;
            TextBoxUtils.spaceDisplay();
            switch (option) {
                case REPORT_BY_TYPE:
                    // ToDo
                    TextBoxUtils.pause(scan);
                    break;
                case REPORT_BY_DATE:
                    // ToDo
                    TextBoxUtils.pause(scan);
                    break;
                case RETURN:
                    System.out.println("\nReturning...");
                    break;           
            }
        } while (option != ReportOption.RETURN);
    }
}
