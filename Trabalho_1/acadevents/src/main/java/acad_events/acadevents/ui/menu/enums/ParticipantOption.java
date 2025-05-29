package acad_events.acadevents.ui.menu.enums;

import acad_events.acadevents.common.utils.interfaces.I_EnumOptionList;

public enum ParticipantOption implements I_EnumOptionList{
    REGISTER_PARTICIPANT(1, "Register new participant"),
    DELETE_PARTICIPANT(2, "Delete participant"),
    LIST_PARTICIPANTS(3, "List participants"),
    ENROLL_IN_EVENT(4, "Enroll participant in event"),
    REMOVE_FROM_EVENT(5, "Remove participant from event"),
    GENERATE_CERTIFICATE(6, "Generate a event certificate"),
    RETURN(7, "Return to Main Menu");

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
