package acad_events.acadevents.common.enums;

import acad_events.acadevents.common.utils.Interfaces.I_EnumOptionList;

public enum ExternalRole implements I_EnumOptionList{
    SPEAKER(1, "Speaker"),
    GUEST(2, "Guest"),
    ORGANIZER(3, "Organizer"),
    EXHIBITOR(4, "Exhibitor"),
    VOLUNTEER(5, "Volunteer"),
    CONSULTANT(6, "Consultant"),
    OBSERVER(7, "Observer");

    private final int value;
    private final String description;

    ExternalRole(int value, String description) {
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
