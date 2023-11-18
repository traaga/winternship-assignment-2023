import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

public class MatchController {
    private final HashMap<String, Match> matches;

    public MatchController() {
        this.matches = new HashMap<>();

        // Reading match data
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("match_data.txt");
        processInputStream(is);
    }

    private void processInputStream(InputStream is) {

        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8); BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                Match match = new Match(split[1], split[2], split[3]);
                matches.put(split[0], match);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Match getMatchByID(String id) {
        return matches.get(id);
    }
    
    public int calculateMatchWinnings(String matchId, int coins, String bet) {
        Match match = getMatchByID(matchId);
        if (Objects.equals(bet, match.getResult())) return (int) (coins * match.getReturnRate() + coins);
        else if (Objects.equals(match.getResult(), "DRAW")) return coins;
        else return 0;
    }
}
