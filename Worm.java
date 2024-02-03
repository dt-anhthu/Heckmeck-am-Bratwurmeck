import javax.swing.*;
import java.awt.*;

public class Worm {
    private int serialNumber;
    private int wormCount;
    private String iconPath;
    private JPanel panel;
    private JLabel numberLabel, iconLabel;

    private boolean active;

    public Worm(int serialNumber, int wormCount, String iconPath) {
        this.serialNumber = serialNumber;
        this.wormCount = wormCount;
        this.iconPath = iconPath;
        this.active = true;

        panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
 
        numberLabel = new JLabel("" + serialNumber);
        numberLabel.setFont(new Font("Arial", Font.BOLD, 22));
        numberLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(numberLabel);

        iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon(MenuPage.class.getResource("/resource/" + iconPath + ".png")));
        panel.add(iconLabel);
    }

    public JPanel getPanel() {
        return panel;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public int getWormCount() {
        return wormCount;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setDisabled() {
        this.iconLabel.setEnabled(false);
        this.numberLabel.setEnabled(false);
    }

    public void setEnabled() {
        this.iconLabel.setEnabled(true);
        this.numberLabel.setEnabled(true);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void clear() {
        this.iconLabel.setIcon(null);
        this.numberLabel.setText("");
    }
}
