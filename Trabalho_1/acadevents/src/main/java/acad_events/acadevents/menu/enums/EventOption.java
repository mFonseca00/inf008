package acad_events.acadevents.menu.enums;

import acad_events.acadevents.menu.enums.Interfaces.IMenuOption;

public enum EventOption implements IMenuOption{
    CREATE_EVENT(1, "Create Event"),
    DELETE_EVENT(2, "Delete Event"),
    LIST_EVENTS(3, "List Events"),
    RETURN(4, "Return to Main Menu");

    private final int value;
    private final String description;

    public int getValue(){
        return value;
    }

    public String getDescription(){
        return description;
    }

    EventOption(int value, String description){
        this.value = value;
        this.description = description;
    }

    public static EventOption fromInt(int value){
        for(EventOption option : EventOption.values()){
            if(option.getValue() == value){
                return option;
            }
        }
        return null;
    }
}
