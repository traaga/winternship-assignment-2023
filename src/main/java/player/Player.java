import java.math.BigDecimal;
import java.math.RoundingMode;

public class Player {
    private final String id;
    private long coins;
    private boolean legitimate;
    private int placedBets;
    private int betsWon;
    private String firstIllegalOperation;

    public Player(String id) {
        this.id = id;
        this.coins = 0;
        this.legitimate = true;
        this.placedBets = 0;
        this.betsWon = 0;
        this.firstIllegalOperation = "";
    }

    public String getId() {
        return id;
    }

    public long getCoins() {
        return coins;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public boolean isLegitimate() {
        return legitimate;
    }

    public void setLegitimate(boolean legitimate) {
        this.legitimate = legitimate;
    }

    public String calculateBettingWinRate() {
        return new BigDecimal(betsWon).divide(new BigDecimal(placedBets), 2, RoundingMode.DOWN).toString();
    }

    public String getFirstIllegalOperation() {
        return firstIllegalOperation;
    }

    public void setFirstIllegalOperation(String firstIllegalOperation) {
        this.firstIllegalOperation = firstIllegalOperation;
    }

    public void increaseBetsPlaced() {
        this.placedBets += 1;
    }

    public void increaseBetsWon() {
        this.betsWon += 1;
    }
}
