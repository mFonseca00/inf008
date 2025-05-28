package acad_events.acadevents.ui.functionalities.enums;

import acad_events.acadevents.common.utils.interfaces.I_EnumOptionList;

public enum EventReportOption implements I_EnumOptionList{
    DATE(1, "Events reports by date"),
    TYPE(2, "Events reports by type");

    private int value;
    private String description;

    EventReportOption(int value, String description){
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
