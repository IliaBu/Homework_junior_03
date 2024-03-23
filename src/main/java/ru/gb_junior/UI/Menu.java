package gb.java_core.UI;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu {

    protected List<MenuItem> menuItems = new ArrayList<>();

    public void execute(int index) {
        if (index >= 0 && index < menuItems.size()) {
            menuItems.get(index).execute();
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("\n");
        int index = 1;
        for (MenuItem command : menuItems) {
            builder.append(index++).append(". ").append(command.toString()).append("\n");
        }
        return builder.toString();
    }

    public int size() {
        return menuItems.size();
    }
}
