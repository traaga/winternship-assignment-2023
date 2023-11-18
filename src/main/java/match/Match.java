import java.util.Objects;

public class Match {
    private final double returnRate;
    private final String result;

    public Match(String returnRateA, String returnRateB, String result) {
        if (Objects.equals(result, "A")) this.returnRate = Double.parseDouble(returnRateA);
        else this.returnRate = Double.parseDouble(returnRateB);
        this.result = result;
    }

    public double getReturnRate() {
        return returnRate;
    }

    public String getResult() {
        return result;
    }
}
