package acad_events.acadevents.ui.functionalities.enums;

import acad_events.acadevents.common.utils.interfaces.I_EnumOptionList;

public enum EventTypeOption implements I_EnumOptionList{
    COURSE(1, "Course"),
    LECTURE(2, "Lecture"),
    FAIR(3, "Fair"),
    WORKSHOP(4, "Workshop"),
    CANCELLED(5, "Cancel event creation");

    private int value;
    private String description;

    EventTypeOption(int value, String description) {
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
