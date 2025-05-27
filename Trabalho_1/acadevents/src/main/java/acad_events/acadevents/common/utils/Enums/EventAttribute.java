package acad_events.acadevents.common.utils.Enums;

import acad_events.acadevents.common.utils.Interfaces.I_EnumOptionList;

public enum EventAttribute implements I_EnumOptionList {
    TITLE(1, "Title of the event"),
    DATE(2, "Date of the event"),
    LOCATION(3, "Location of the event"),
    MODALITY(4, "Modality of the event"),
    CANCELLED(5, "Cancel operation");

    private final int value;
    private final String description;

    EventAttribute(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
