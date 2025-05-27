package acad_events.acadevents.models.participant.entities.enums;

import acad_events.acadevents.common.utils.interfaces.I_EnumOptionList;

public enum AcademyDepartment implements I_EnumOptionList{
    NONE (1, "No department"),
    COMPUTER_SCIENCE (2, "Department of Computer Science"),
    ELECTRICAL_ENGINEERING (3, "Department of Electrical Engineering"),
    INFORMATION_SYSTEMS (4, "Department of Information Systems"),
    SOFTWARE_ENGINEERING (5, "Department of Software Engineering"),
    COMPUTER_ENGINEERING (6, "Department of Computer Engineering"),
    MATHEMATICS (7, "Department of Mathematics"),
    PHYSICS (8, "Department of Physics"),
    CHEMISTRY (9, "Department of Chemistry");

    private final int value;
    private final String description;

    AcademyDepartment(int value, String description) {
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
