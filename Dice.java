import javax.swing.*;
import java.awt.*;

public class Dice {
    protected static final String[] names = new String[] {"Dice1", "Dice2", "Dice3", "Dice4", "Dice5", "Dice6"};
    private int value;
    private String iconPath;
    private boolean canChange;

    public Dice(int value, String iconPath) {
        this.value = value;
        this.iconPath = "/resource/" + iconPath + ".png";
        this.canChange = true;
    }

    public void setChange(boolean canChange) {
        this.canChange = canChange;
    }

    public boolean canChange() {
        return canChange;
    }

    public int getValue() {
        return value;
    }

    public String getIconPath() {
        return iconPath;
    }
}
