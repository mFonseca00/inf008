package acad_events.acadevents.common.utils;

import acad_events.acadevents.common.utils.interfaces.I_EnumOptionList;

/**
 * Utility class for menu operations in the AcadEvents system.
 * Provides methods to display enum-based menu options in a standardized format.
 */
public class MenuUtils {
    
    /**
     * Displays all options from an enum that implements I_EnumOptionList interface.
     * Each option is shown with its value and description in a formatted layout.
     * 
     * @param <E> Generic type that extends Enum and implements I_EnumOptionList
     * @param enumClass The enum class containing the menu options to display
     * 
     * Usage example: MenuUtils.listEnumOptions(ParticipantTypeOption.class);
     * This will display options like "1 - Student", "2 - Professor", etc.
     */
    public static <E extends Enum<E> & I_EnumOptionList> void listEnumOptions(Class<E> enumClass) {
        for (E option : enumClass.getEnumConstants()) {
            TextBoxUtils.printLeftText(option.getValue() + " - " + option.getDescription());
        }
        TextBoxUtils.printUnderLineDisplayDivisor();
    }
}
