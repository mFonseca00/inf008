package acad_events.acadevents.ui.functionalities.enums;

import acad_events.acadevents.common.utils.interfaces.I_EnumOptionList;

public enum EventWayToSelectEventsOption implements I_EnumOptionList{
    ATTRIBUTE_LIST(1, "By an attribute list"),
    ALL_LIST(2, "From all events list"),
    ID(3, "From an ID"),
    CANCELLED(4, "Cancel operation");

    private int value;
    private String description;

    EventWayToSelectEventsOption(int value, String description){
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
