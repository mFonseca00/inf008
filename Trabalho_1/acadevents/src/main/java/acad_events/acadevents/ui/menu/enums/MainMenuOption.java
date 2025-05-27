package acad_events.acadevents.ui.menu.enums;

import acad_events.acadevents.common.utils.interfaces.I_EnumOptionList;

public enum MainMenuOption implements I_EnumOptionList{
    MANAGE_EVENTS(1, "Manage Events"),
    MANAGE_PARTICIPANTS(2, "Manage Participants"),
    GENERATE_REPORTS(3, "Generate Reports"),
    EXIT(4, "Exit");

    private final int value;
    private final String description;

    MainMenuOption(int value, String description){
        this.value = value;
        this.description = description;
    }

    public int getValue(){
        return value;
    }

    public String getDescription(){
        return description;
    }

    public static MainMenuOption fromInt(int value){
        for(MainMenuOption option : MainMenuOption.values()){
            if(option.getValue() == value){
                return option;
            }
        }
        return null;
    }
}
