package acad_events.acadevents.common.utils;

import acad_events.acadevents.common.utils.interfaces.I_EnumOptionList;

public class MenuUtils {
    public static <E extends Enum<E> & I_EnumOptionList> void listEnumOptions(Class<E> enumClass) {
        for (E option : enumClass.getEnumConstants()) {
            TextBoxUtils.printLeftText(option.getValue() + " - " + option.getDescription());
        }
        TextBoxUtils.printUnderLineDisplayDivisor();
    }
}
