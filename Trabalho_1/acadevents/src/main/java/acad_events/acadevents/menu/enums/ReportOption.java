package acad_events.acadevents.menu.enums;

import acad_events.acadevents.menu.enums.Interfaces.IMenuOption;

public enum ReportOption implements IMenuOption{
    REPORT_BY_TYPE(1, "Report events by type"),
    REPORT_BY_DATE(2, "Report events by date"),
    RETURN(3, "Return to Main Menu");

    private final int value;
    private final String description;

    public int getValue(){
        return value;
    }

    public String getDescription(){
        return description;
    }

    ReportOption(int value, String description){
        this.value = value;
        this.description = description;
    }

    public static ReportOption fromInt(int value){
        for(ReportOption option : ReportOption.values()){
            if(option.getValue() == value){
                return option;
            }
        }
        return null;
    }
}
