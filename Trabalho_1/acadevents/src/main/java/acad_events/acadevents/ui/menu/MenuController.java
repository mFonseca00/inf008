package acad_events.acadevents.ui.menu;

import java.util.Scanner;

import acad_events.acadevents.common.utils.TextBoxUtils;
import acad_events.acadevents.ui.functionalities.EventFunctionalities;
import acad_events.acadevents.ui.functionalities.IntegrationFunctionalities;
import acad_events.acadevents.ui.functionalities.ParticipantFunctionalities;
import acad_events.acadevents.ui.functionalities.enums.EventReportOption;
import acad_events.acadevents.ui.menu.enums.EventOption;
import acad_events.acadevents.ui.menu.enums.MainMenuOption;
import acad_events.acadevents.ui.menu.enums.ParticipantOption;
import acad_events.acadevents.ui.menu.enums.ReportOption;
import acad_events.acadevents.ui.menu.sub_menus.EventMenu;
import acad_events.acadevents.ui.menu.sub_menus.MainMenu;
import acad_events.acadevents.ui.menu.sub_menus.ParticipantMenu;
import acad_events.acadevents.ui.menu.sub_menus.ReportMenu;

/**
 * Central navigation controller for the AcadEvents system's console-based user interface.
 * Manages the main application flow and coordinates all menu interactions and functionality execution.
 * Acts as the primary orchestrator between menu presentation and business logic execution.
 * 
 * Key features:
 * - Main application loop with persistent navigation until exit
 * - Hierarchical menu system with main menu and specialized sub-menus
 * - Dependency injection for all functionality classes to maintain clean separation
 * - Consistent UI flow with screen clearing, option handling, and user pause management
 * - Integrated test data generation for both participants and events
 * 
 * Menu structure:
 * - Main Menu: Entry point with 5 core options (events, participants, reports, test data, exit)
 * - Event Sub-menu: CRUD operations for academic events with multiple types
 * - Participant Sub-menu: Participant management plus integration features (enrollment, certificates)
 * - Report Sub-menu: Event reporting with type and date filtering options
 * 
 * Used by: AcadEvents main class as the primary UI controller
 * Integration: Coordinates EventFunctionalities, ParticipantFunctionalities, and IntegrationFunctionalities
 */
public class MenuController {
    // Menu presentation components for each specialized area
    private final MainMenu mainMenu = new MainMenu();
    private final EventMenu eventMenu = new EventMenu();
    private final ParticipantMenu partMenu = new ParticipantMenu();
    private final ReportMenu reportMenu = new ReportMenu();
    
    // Business logic functionality classes injected for clean separation of concerns
    private final ParticipantFunctionalities partFunctions;
    private final EventFunctionalities eventFunctions;
    private final IntegrationFunctionalities integrFunctions;

    public MenuController(ParticipantFunctionalities partFunctions, EventFunctionalities eventFunctions, IntegrationFunctionalities integrFunctions) {
        this.partFunctions = partFunctions;
        this.eventFunctions = eventFunctions;
        this.integrFunctions = integrFunctions;
    }

    // Main application loop - runs until user chooses to exit
    public void run(){
        Scanner scan = new Scanner(System.in);
        MainMenuOption option = null;
        do{
            TextBoxUtils.clearDisplay();
            mainMenu.printOptions();
            Object optionObj = mainMenu.readOption(scan);
            option = (MainMenuOption) optionObj;
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
                case GENERATE_TEST_DATA:
                    generateTestData(scan);
                    break;
                case EXIT:
                    System.out.println("\tExiting...");
                    break;
            }
        } while(option != MainMenuOption.EXIT);
        scan.close();
    }

    // Event management sub-menu handling CRUD operations for academic events
    private void eventsSubMenu(Scanner scan){
        EventOption option = null;
        do{
            TextBoxUtils.clearDisplay();
            eventMenu.printOptions();
            Object optionObj = eventMenu.readOption(scan);
            option = (EventOption) optionObj;
            switch (option) {
                case CREATE_EVENT:
                    eventFunctions.create(scan);
                    TextBoxUtils.pause(scan);
                    break;
                case DELETE_EVENT:
                    eventFunctions.remove(scan);
                    TextBoxUtils.pause(scan);
                    break;
                case LIST_EVENTS:
                    eventFunctions.listAll();
                    TextBoxUtils.pause(scan);
                    break;
                case RETURN:
                    System.out.println("\nReturning...");
                    break;
            }
        }while(option != EventOption.RETURN);
    }

    // Participant management sub-menu with both CRUD operations and integration features
    private void participantsSubMenu(Scanner scan){
        ParticipantOption option = null;
        do{
            TextBoxUtils.clearDisplay();
            partMenu.printOptions();
            Object optionObj = partMenu.readOption(scan);
            option = (ParticipantOption) optionObj;
            switch (option){
                case REGISTER_PARTICIPANT:
                    partFunctions.registerNew(scan);
                    TextBoxUtils.pause(scan);
                    break;
                case REMOVE_PARTICIPANT:
                    // Critical: Uses IntegrationFunctionalities to handle event unenrollment during deletion
                    partFunctions.remove(scan, integrFunctions); 
                    TextBoxUtils.pause(scan);
                    break;
                case LIST_PARTICIPANTS:
                    partFunctions.listAll();
                    TextBoxUtils.pause(scan);
                    break;
                case ENROLL_IN_EVENT:
                    integrFunctions.enrollParticipantInEvent(scan);
                    TextBoxUtils.pause(scan);
                    break;
                case REMOVE_FROM_EVENT:
                    integrFunctions.removeParticipantFromEvent(scan);
                    TextBoxUtils.pause(scan);
                    break;
                case GENERATE_CERTIFICATE:
                    integrFunctions.generateCertificate(scan);
                    TextBoxUtils.pause(scan);
                    break;
                case RETURN:
                    System.out.println("\nReturning...");
                    break;
            }
        }while(option != ParticipantOption.RETURN);
    }

    // Report generation sub-menu supporting both type-based and date-based filtering
    private void reportsSubMenu(Scanner scan){
        ReportOption option = null;
        do {
            TextBoxUtils.clearDisplay();
            reportMenu.printOptions();
            Object optionObj = reportMenu.readOption(scan);
            option = (ReportOption) optionObj;
            switch (option) {
                case REPORT_BY_TYPE:
                    eventFunctions.generateReport(scan,EventReportOption.TYPE);
                    TextBoxUtils.pause(scan);
                    break;
                case REPORT_BY_DATE:
                    eventFunctions.generateReport(scan,EventReportOption.DATE);
                    TextBoxUtils.pause(scan);
                    break;
                case RETURN:
                    System.out.println("\nReturning...");
                    break;           
            }
        } while (option != ReportOption.RETURN);
    }

    // Consolidated test data generation for both participants and events in a single operation
    private void generateTestData(Scanner scan) {
        TextBoxUtils.clearDisplay();
        partFunctions.generateRandomParticipant(scan);
        eventFunctions.generateRandomEvent(scan);
        TextBoxUtils.printSuccess("Generation finished.");
        TextBoxUtils.pause(scan);
    }
}
