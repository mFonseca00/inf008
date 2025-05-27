package acad_events.acadevents.ui.functionalities.enums;

import acad_events.acadevents.common.utils.interfaces.I_EnumOptionList;

public enum ParticipantTypeOption implements I_EnumOptionList{
    STUDENT(1, "Student"),
    PROFESSOR(2, "Professor"),
    EXTERNAL(3, "External Participant"),
    CANCELLED(4, "Cancel registration");

    private int value;
    private String description;

    ParticipantTypeOption(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
    
}
