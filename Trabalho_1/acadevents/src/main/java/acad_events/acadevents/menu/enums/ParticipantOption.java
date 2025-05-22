package acad_events.acadevents.menu.enums;

import acad_events.acadevents.menu.enums.Interfaces.IMenuOption;

public enum ParticipantOption implements IMenuOption{
    CREATE_PARTICIPANT(1, "Create new participant"),
    DELETE_PARTICIPANT(2, "Delete participant"),
    LIST_PARTICIPANTS(3, "List participants"),
    GENERATE_CERTIFICATE(4, "Generate a event certificate"),
    RETURN(5, "Return to Main Menu");

    private final int value;
    private final String description;

    public int getValue(){
        return value;
    }

    public String getDescription(){
        return description;
    }

    ParticipantOption(int value, String description){
        this.value = value;
        this.description = description;
    }

    public static ParticipantOption fromInt(int value){
        for(ParticipantOption option : ParticipantOption.values()){
            if(option.getValue() == value){
                return option;
            }
        }
        return null;
    }
}
