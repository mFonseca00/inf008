package acad_events.acadevents.ui.functionalities.enums;

import acad_events.acadevents.common.utils.Interfaces.I_EnumOptionList;

public enum EventWayToRemoveOption implements I_EnumOptionList{
    ATTRIBUTE_LIST(1, "Remove by an attribute list"),
    ALL_LIST(2, "Remove from all events list"),
    ID(3, "Remove from an ID"),
    CANCELLED(4, "Cancel remotion");

    private int value;
    private String description;

    EventWayToRemoveOption(int value, String description){
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
