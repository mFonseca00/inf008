package acad_events.acadevents.models.event.entities.enums;

import acad_events.acadevents.common.utils.Interfaces.I_EnumOptionList;

public enum Modality implements I_EnumOptionList{
    PRESENTIAL (1, "Presential Event"),
    ONLINE (2, "Online Event"),
    HYBRID (3, "Hybrid Event");

    private final int value;
    private final String description;

    Modality(int value, String description) {
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
