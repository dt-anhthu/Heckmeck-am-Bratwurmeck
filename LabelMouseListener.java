import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LabelMouseListener extends MouseAdapter {
    private JLabel[] labels;
    private Player player;
    private JLabel playerLabel;
    private JLabel pointLabel;
    public LabelMouseListener(JLabel[] labels, JLabel playerLabel, JLabel pointLabel, Player player) {
        this.labels = labels;
        this.player = player;
        this.pointLabel = pointLabel;
        this.playerLabel = playerLabel;
    }
    @Override 
    public void mouseClicked(MouseEvent e) {
        if (player.isNextRoll()) {
            int index = 0;
            int diceValue = -1;
            while (index < 8) {
                if (e.getSource() == labels[index]) {
                    diceValue = player.getDices()[index].getValue();
                    break;
                }
                index++;
            }

            if (player.contains(diceValue)) {
                JOptionPane.showMessageDialog(null, "Already Taken!");
                return;
            }

            for (int i = 0; i < 8; i++) {
                if (player.getDices()[i].getValue() == diceValue) {
                    if (player.getDices()[i].canChange()) {
                        player.getDices()[i].setChange(false);
                        player.addDice(diceValue);
                        labels[i].setEnabled(false);
                        if (diceValue == 6) {
                            player.addPoints(5);
                        } else {
                            player.addPoints(diceValue);
                        }
                    }
                }
                labels[i].setToolTipText("");
            }

            playerLabel.setText(Integer.toString(player.getWormCount()));

            pointLabel.setText(String.format("%2d", player.getPoints()));
            player.setNextRoll(false);
            player.setRoll(true);
        }
    }
}