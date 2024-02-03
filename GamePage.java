import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class GamePage extends JFrame {
    private Worm[] worms = new Worm[16];

    private Player player1 = new Player("Player One");
    private Player player2 = new Player("Player Two");
    private Player currentPlayer;
    private JLabel currentLabel, player1Label, player2Label;
    private JLabel currentPointLabel, player1PointLabel, player2PointLabel;
    private JLabel turnPlayer1, turnPlayer2;

    private JPanel currentPanel, player1wormStack, player2wormStack;

    private JLabel[] labels = new JLabel[8];
    private LabelMouseListener listener;

    public GamePage() {
        //Start music 
        SoundHandler.RunMusic("src/resource/Music.wav");
        setWorms();

        currentPlayer = player1;

        setTitle("Heckmeck am Bratwurmeck");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 575);
        setLocationRelativeTo(null);
        setResizable(false);

        JLayeredPane layeredpane = new JLayeredPane();
        add(layeredpane);

        JLabel pictureLabel = new JLabel();
        pictureLabel.setIcon(new ImageIcon(MenuPage.class.getResource("/resource/playing.png")));
        pictureLabel.setBounds(0, -5, 950, 550);

        JPanel contentPane = new JPanel();
        contentPane.setOpaque(false);
        contentPane.setBounds(0, 0, 900, 500);
        contentPane.setLayout(null);

        JButton menuButton = new JButton("Menu");
        menuButton.setFocusPainted(false);
        menuButton.setFont(new Font("Arial", Font.BOLD, 14));
        menuButton.setBounds(70, 10, 100, 30);
        contentPane.add(menuButton);

        JPanel wormPanel = new JPanel();
        wormPanel.setBounds(70, 50, 800, 100);
        wormPanel.setOpaque(false);

        GridLayout wormPanelLayout = new GridLayout(1, 0);
        wormPanelLayout.setHgap(5);
        wormPanel.setLayout(wormPanelLayout);
        for (Worm worm : worms) {
            wormPanel.add(worm.getPanel());
        }
        contentPane.add(wormPanel);

        player1wormStack = new JPanel();
        player1wormStack.setBounds(180, 200, 50, 110);
        contentPane.add(player1wormStack);

        currentPanel = player1wormStack;

        player1PointLabel = new JLabel(String.format("%2d", 0));
        player1PointLabel.setBounds(258, 216, 50, 50);
        player1PointLabel.setFont(new Font("Arial", Font.BOLD, 40));
        contentPane.add(player1PointLabel);

        currentPointLabel = player1PointLabel;

        player1Label = new JLabel(String.format("%2d", 0));
        player1Label.setBounds(180, 340, 50, 50);
        player1Label.setFont(new Font("Arial", Font.BOLD, 40));
        contentPane.add(player1Label);
        
              
        turnPlayer1 = new JLabel();
        turnPlayer1.setBounds(106, 170, 35, 35);
        turnPlayer1.setOpaque(true);
        turnPlayer1.setBorder(null);

        turnPlayer1.setBackground(Color.RED);
        contentPane.add(turnPlayer1); 
        turnPlayer1.setVisible(true);
        

        contentPane.add(player1Label);

        currentLabel = player1Label;

        player2wormStack = new JPanel();
        player2wormStack.setBounds(730, 200, 50, 110);
        contentPane.add(player2wormStack);

        player2PointLabel = new JLabel(String.format("%2d", 0));
        player2PointLabel.setBounds(258, 216, 50, 50);
        player2PointLabel.setFont(new Font("Arial", Font.BOLD, 40));
        player2PointLabel.setVisible(false);
        
        contentPane.add(player2PointLabel);

        player2Label = new JLabel(String.format("%2d", 0));
        player2Label.setBounds(750, 340, 50, 50);
        player2Label.setFont(new Font("Arial", Font.BOLD, 40));
        contentPane.add(player2Label);
        
        turnPlayer2 = new JLabel();
        turnPlayer2.setBounds(825, 170, 35, 35);
        turnPlayer2.setOpaque(true);
        turnPlayer2.setBackground(Color.RED);
        contentPane.add(turnPlayer2);
        turnPlayer2.setVisible(false);


        JPanel labelPanel = new JPanel();
        labelPanel.setBounds(350, 290, 250, 130);
        labelPanel.setOpaque(false);

        GridLayout gridLayout = new GridLayout(2, 4);
        gridLayout.setHgap(5);
        gridLayout.setVgap(5);
        labelPanel.setLayout(gridLayout);

        for (int i = 0; i < 8; i++) {
            labels[i] = new JLabel();
            labels[i].setOpaque(false);
            labelPanel.add(labels[i]);
        }
        contentPane.add(labelPanel);

        JButton rollButton = new JButton("Roll");
        rollButton.setFocusPainted(false);
        rollButton.setFont(new Font("Arial", Font.BOLD, 14));
        rollButton.setBounds(370, 430, 100, 30);
        contentPane.add(rollButton);

        JButton stopButton = new JButton("Stop");
        stopButton.setFocusPainted(false);
        stopButton.setFont(new Font("Arial", Font.BOLD, 14));
        stopButton.setBounds(490, 430, 100, 30);
        contentPane.add(stopButton);

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                new MenuPage().setVisible(true);
            }
        });

        listener = new LabelMouseListener(labels, currentLabel, currentPointLabel, currentPlayer);
        for (JLabel label : labels) {
            label.addMouseListener(listener);
        }

        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (currentPlayer.canRoll()) {
                    currentPlayer.rollDice();
                    currentPlayer.setRoll(false);
                    currentPlayer.setNextRoll(true);

                    int index = 0;
                    for (Dice dice : currentPlayer.getDices()) {
                        if (dice.canChange()) {
                            labels[index].setToolTipText("Click dice");
                        }
                        labels[index].setIcon(new ImageIcon(MenuPage.class.getResource(dice.getIconPath())));
                        index++;
                    }
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (currentPlayer.getPoints() < 21 || currentPlayer.getPoints() > 36) {
                    JOptionPane.showMessageDialog(null, "Sorry!!! Unsuccessfully Turned! Point is not in range.");
                    updateWorm();
                } else if (currentPlayer.onlyNumbers()) {
                    JOptionPane.showMessageDialog(null, "Sorry!!! Unsuccessfully Turned! Not contains any worm.");
                    updateWorm();
                } else {
                    Player otherPlayer = null;
                    JPanel otherPlayerPanel = null;

                    if (currentPlayer == player1) {
                        otherPlayer = player2;
                        otherPlayerPanel = player2wormStack;
                    } else {
                        otherPlayer = player1;
                        otherPlayerPanel = player1wormStack;
                    }

                    if (otherPlayer.lastWorm() != null &&
                            otherPlayer.lastWorm().getSerialNumber() == currentPlayer.getPoints()) {
                        System.out.println("GAME LOG: WORM IS AT TOP OF OTHER PLAYER");

                        Worm poppedWorm = otherPlayer.pop();

                        currentPlayer.addWorm(poppedWorm);
                        currentPanel.removeAll();
                        currentPanel.add(new Worm(poppedWorm.getSerialNumber(), -1, poppedWorm.getIconPath()).getPanel());

                        Worm lastWorm = otherPlayer.lastWorm();

                        otherPlayerPanel.removeAll();
                        if (lastWorm != null) {
                            otherPlayerPanel.add(new Worm(lastWorm.getSerialNumber(), -1, lastWorm.getIconPath()).getPanel());
                        } else {
                            otherPlayerPanel.add(new JPanel());
                        }
                        getWinner();
                        togglePlayer();
                        return;
                    }

                    int index = 15;
                    while ((index >= 0
                            && worms[index].getSerialNumber() > currentPlayer.getPoints())
                            || !worms[index].isActive()) {
                        index--;
                        if (index == -1) {
                            break;
                        }
                    }

                    System.out.println("WORM INDEX : " + index);

                    if (index == -1) {
                        if (otherPlayer.lastWorm() != null &&
                                otherPlayer.lastWorm().getSerialNumber() == currentPlayer.getPoints()) {
                            System.out.println("GAME LOG: WORM IS AT TOP OF OTHER PLAYER");

                            Worm poppedWorm = otherPlayer.pop();

                            currentPlayer.addWorm(poppedWorm);
                            currentPanel.removeAll();
                            currentPanel.add(new Worm(poppedWorm.getSerialNumber(), -1, poppedWorm.getIconPath()).getPanel());

                            Worm lastWorm = otherPlayer.lastWorm();

                            otherPlayerPanel.removeAll();
                            if (lastWorm != null) {
                                otherPlayerPanel.add(new Worm(lastWorm.getSerialNumber(), -1, lastWorm.getIconPath()).getPanel());
                            } else {
                                otherPlayerPanel.add(new JPanel());
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Sorry!!! Unsuccessfully Turned! No Available Worms.");
                            updateWorm();
                        }
                        getWinner();
                        togglePlayer();
                        return;
                    }

                    currentPanel.removeAll();
                    currentPanel.add(new Worm(worms[index].getSerialNumber(), -1, worms[index].getIconPath()).getPanel());
                    currentPlayer.addWorm(worms[index]);
                    worms[index].setDisabled();
                    worms[index].setActive(false);
                    
                    currentLabel.setText(Integer.toString(currentPlayer.getWormCount()));
                                        
                    System.out.println("GAME LOG: " + worms[index].getSerialNumber() + " INSERTED TO " + currentPlayer.getName() + ", STACK SIZE: " + currentPlayer.stackSize() + " STACK : " + currentPlayer.getStackString());
                }
                getWinner();
                currentPointLabel.setText(String.format("%2d", 0));
                togglePlayer();
            }
        });

        layeredpane.add(pictureLabel, new Integer(0), 0);
        layeredpane.add(contentPane, new Integer(1), 0);
    }

    public void setWorms() {
        String[] fileNames = new String[] {"OneWormTile", "TwoWormTile", "ThreeWormTile", "FourWormTile"};

        int serialNo = 21;
        int wormCount = 1;
        int index = 0;
        for (String fileName : fileNames) {
            for (int i = 0; i < 4; i++) {
                Worm worm = new Worm(serialNo, wormCount, fileName);
                worms[index] = worm;
                serialNo++;
                index++;
            }
            wormCount++;
        }
    }

    public void togglePlayer() {
        currentPlayer.clear();
        if (currentPlayer == player1) {
            currentPlayer = player2;
            currentLabel = player2Label;
            currentPointLabel = player2PointLabel;
            currentPanel = player2wormStack;
            
            player2PointLabel.setVisible(true);
            player1PointLabel.setVisible(false);
            
            turnPlayer2.setVisible(true);
            turnPlayer1.setVisible(false);

        } else {
            currentPlayer = player1;
            currentPointLabel = player1PointLabel;
            currentLabel = player1Label;
            currentPanel = player1wormStack;
            
            player1PointLabel.setVisible(true);
            player2PointLabel.setVisible(false);
            
            turnPlayer1.setVisible(true);
            turnPlayer2.setVisible(false);
            
        }

        for (JLabel label : labels) {
            label.setIcon(null);
            label.setEnabled(true);
            label.removeMouseListener(listener);
        }

        listener = new LabelMouseListener(labels, currentLabel, currentPointLabel, currentPlayer);
        for (JLabel label : labels) {
            label.addMouseListener(listener);
        }
    }

    public void updateWorm() {
        Worm poppedWorm = currentPlayer.pop();

        if (poppedWorm != null) {
            for (Worm worm : worms) {
                if (worm.getSerialNumber() == poppedWorm.getSerialNumber()) {
                    worm.setEnabled();
                    worm.setActive(true);
                    break;
                }
            }
            System.out.println("GAME LOG: " + poppedWorm.getSerialNumber() + " REMOVED FROM " + currentPlayer.getName() + ", STACK SIZE: " + currentPlayer.stackSize() + " STACK : " + currentPlayer.getStackString());
        } else {
            System.out.println("GAME LOG: NOTHING REMOVED FROM " + currentPlayer.getName() + ", STACK SIZE: " + currentPlayer.stackSize() + " STACK : " + currentPlayer.getStackString());
        }
        Worm lastWorm = currentPlayer.lastWorm();

        currentPanel.removeAll();
        if (lastWorm != null) {
            System.out.println("GAME LOG: LAST WORM " + lastWorm.getSerialNumber() + " OF " + currentPlayer.getName() + ", STACK SIZE: " + currentPlayer.stackSize() + " STACK : " + currentPlayer.getStackString());
            currentPanel.add(new Worm(lastWorm.getSerialNumber(), -1, lastWorm.getIconPath()).getPanel());
        } else {
            System.out.println("GAME LOG: NO LAST WORM OF " + currentPlayer.getName() + ", STACK SIZE: " + currentPlayer.stackSize() + " STACK : " + currentPlayer.getStackString());
            currentPanel.add(new JPanel());
        }

        int index = 15;
        while (!worms[index].isActive()) {
            index--;
        }

        if (poppedWorm == null || poppedWorm.getSerialNumber() != worms[index].getSerialNumber()) {
            worms[index].clear();
            worms[index].setActive(false);
        }
    }

    public void getWinner() {
        boolean isAllInactive = true;
        for (Worm worm : worms) {
            if (worm.isActive()) {
                isAllInactive = false;
                break;
            }
        }

        if (isAllInactive) {
            if (player1.getWormCount() > player2.getWormCount()) {
                JOptionPane.showMessageDialog(null, "Player 1 Wins!!!");
            } else if (player1.getWormCount() < player2.getWormCount()) {
                JOptionPane.showMessageDialog(null, "Player 2 Wins!!!");
            } else {
                int highestNumber1 = player1.highestWorm();
                int highestNumber2 = player2.highestWorm();

                if (highestNumber1 > highestNumber2) {
                    JOptionPane.showMessageDialog(null, "Player 1 Wins!!!");
                } else {
                    JOptionPane.showMessageDialog(null, "Player 2 Wins!!!");
                }
            }
            dispose();
        }
    }

    public static void main(String[] args) {
        new GamePage().setVisible(true);
    }
}
