package acad_events.acadevents.common.utils;

/**
 * ANSI escape code constants for console text styling in the AcadEvents system.
 * Provides predefined color and formatting codes for consistent UI presentation.
 * 
 * Usage: System.out.println(StyleStrings.GREEN + "Success message" + StyleStrings.RESET);
 * Note: ANSI codes work on most Unix terminals and modern Windows terminals.
 */
public class StyleStrings {
    // Reset all formatting to default console appearance
    public static final String RESET = "\033[0m";

    // Text formatting styles for emphasis and visual hierarchy
    public static final String BOLD = "\033[1m";
    public static final String ITALIC = "\033[3m";
    public static final String UNDERLINE = "\033[4m";
    public static final String BLINK = "\033[5m";
    public static final String REVERSED = "\033[7m";
    public static final String HIDDEN = "\033[8m";

    // Foreground text colors for semantic messaging (success, error, warning)
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";      // Used for error messages
    public static final String GREEN = "\033[0;32m";    // Used for success messages
    public static final String YELLOW = "\033[0;33m";   // Used for warning messages
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    // Background colors for highlighting important sections
    public static final String BLACK_BACKGROUND = "\033[40m";
    public static final String RED_BACKGROUND = "\033[41m";
    public static final String GREEN_BACKGROUND = "\033[42m";
    public static final String YELLOW_BACKGROUND = "\033[43m";
    public static final String BLUE_BACKGROUND = "\033[44m";
    public static final String PURPLE_BACKGROUND = "\033[45m";
    public static final String CYAN_BACKGROUND = "\033[46m";
    public static final String WHITE_BACKGROUND = "\033[47m";
}
