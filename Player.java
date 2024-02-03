import java.util.*;

public class Player {
    private Stack<Worm> worms;
    private Dice[] dices;
    private Random random;
    private int points;
    private boolean canRoll, nextRoll;
    private List<Integer> addedDices;

    private String name;

    public Player(String name) {
        worms = new Stack<>();
        dices = new Dice[8];
        random = new Random();
        points = 0;
        canRoll = true;
        nextRoll = true;
        addedDices = new ArrayList<>(); 
        this.name = name;
    }

    public Dice[] getDices() {
        return dices;
    }

    public void rollDice() {
        for (int i = 0; i < dices.length; i++) {
            if (dices[i] == null || dices[i].canChange()) {
                int index = random.nextInt(6);
                dices[i] = new Dice(index + 1, Dice.names[index]);
            }
        }
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }

    public void setRoll(boolean canRoll) {
        this.canRoll = canRoll;
    }

    public boolean canRoll() {
        return canRoll;
    }

    public void setNextRoll(boolean nextRoll) {
        this.nextRoll = nextRoll;
    }

    public boolean isNextRoll() {
        return nextRoll;
    }

    public boolean contains(int dice) {
        return addedDices.contains(dice);
    }

    public void addDice(int dice) {
        addedDices.add(dice);
    }

    public boolean onlyNumbers() {
        return !addedDices.contains(6);
    }

    public void addWorm(Worm worm) {
        worms.add(worm);
    }

    public Worm pop() {
        if (!worms.isEmpty()) {
           return worms.pop();
        }
        return null;
    }

    public Worm lastWorm() {
        if (!worms.isEmpty()) {
            return worms.lastElement();
        }
        return null;
    }

    public int getWormCount() {
        int wormCount = 0;
        for (Worm worm : worms) {
            wormCount += worm.getWormCount();
        }
        return wormCount;
    }

    public void clear() {
        points = 0;
        addedDices.clear();
        canRoll = true;
        nextRoll = true;
        Arrays.fill(dices, null);
    }

    public String getName() {
        return name;
    }

    public int stackSize() {
        return worms.size();
    }

    public String getStackString() {
        StringBuilder wormString = new StringBuilder();
        for (Worm worm : worms) {
            wormString.append(worm.getSerialNumber()).append(" ");
        }
        return wormString.toString();
    }

    public int highestWorm() {
        int high = -1;
        for (Worm worm : worms) {
            if (worm.getSerialNumber() > high) {
                high = worm.getSerialNumber();
            }
        }
        return high;
    }
}
