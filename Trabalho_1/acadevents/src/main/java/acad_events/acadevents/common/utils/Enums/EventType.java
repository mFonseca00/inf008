package acad_events.acadevents.common.utils.enums;

import acad_events.acadevents.common.utils.interfaces.I_EnumOptionList;

public enum EventType implements I_EnumOptionList{
    COURSE(1, "Course"),
    LECTURE(2, "Lecture"),
    FAIR(3, "Fair"),
    WORKSHOP(4, "Workshop"),
    CANCELLED(5, "Cancel operation");

    private int value;
    private String description;

    EventType(int value, String description) {
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
