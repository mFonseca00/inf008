package acad_events.acadevents.ui.functionalities.enums;

import acad_events.acadevents.common.utils.interfaces.I_EnumOptionList;

public enum YesOrNoOption implements I_EnumOptionList{
    YES(1,"Yes!"),
    NO(2,"NO.");

    private int value;
    private String description;

    YesOrNoOption(int value, String description){
        this.value = value;
        this.description =description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
    
}
