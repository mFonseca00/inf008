package acad_events.acadevents.ui.menu;

import java.util.Scanner;

public abstract class Menu {

    public abstract Object readOption(Scanner scan);
    public abstract void printOptions();
}
