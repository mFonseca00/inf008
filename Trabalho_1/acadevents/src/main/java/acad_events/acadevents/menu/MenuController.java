package acad_events.acadevents.menu;

import java.util.Scanner;

import acad_events.acadevents.menu.enums.EventOption;
import acad_events.acadevents.menu.enums.MainMenuOption;
import acad_events.acadevents.menu.enums.ParticipantOption;
import acad_events.acadevents.menu.enums.ReportOption;
import acad_events.acadevents.menu.subMenus.EventMenu;
import acad_events.acadevents.menu.subMenus.MainMenu;
import acad_events.acadevents.menu.subMenus.ParticipantMenu;
import acad_events.acadevents.menu.subMenus.ReportMenu;

public class MenuController {
    private final MainMenu mainMenu = new MainMenu();
    private final EventMenu eventMenu = new EventMenu();
    private final ParticipantMenu partMenu = new ParticipantMenu();
    private final ReportMenu reportMenu = new ReportMenu();

    public void run(){
        Scanner scan =new Scanner(System.in);
        MainMenuOption option = null;
        do{
            mainMenu.printOptions();
            Object optionObj = mainMenu.readOption(scan);
            option = (MainMenuOption) optionObj;
            System.out.println("\n\tOption: " + option.getDescription()); //DEBUG
            mainMenu.spaceDisplay();
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
            System.out.println("\n\tOption: " + option.getDescription()); //DEBUG
            eventMenu.spaceDisplay();
            switch (option) {
                case CREATE_EVENT:
                    // ToDo
                    break;
                case DELETE_EVENT:
                    // ToDO
                    break;
                case LIST_EVENTS:
                    // ToDo
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
            System.out.println("\n\tOption: " + option.getDescription()); //DEBUG
            partMenu.spaceDisplay();
            switch (option){
                case CREATE_PARTICIPANT:
                    // ToDo
                    break;
                case DELETE_PARTICIPANT:
                    // ToDo
                    break;
                case LIST_PARTICIPANTS:
                    // ToDo
                    break;
                case GENERATE_CERTIFICATE:
                    // ToDo
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
            System.out.println("\n\tOption: " + option.getDescription()); //DEBUG
            reportMenu.spaceDisplay();
            switch (option) {
                case REPORT_BY_TYPE:
                    // ToDo
                    break;
                case REPORT_BY_DATE:
                    // ToDo
                    break;
                case RETURN:
                    System.out.println("\nReturning...");
                    break;           
                }
        } while (option != ReportOption.RETURN);
    }
}
