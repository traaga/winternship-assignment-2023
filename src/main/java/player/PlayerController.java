import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class PlayerController {
    private final HashMap<String, Player> players;
    private final MatchController matchController;
    private final Casino casino;

    public PlayerController(MatchController matchController, Casino casino) {
        this.players = new HashMap<>();
        this.matchController = matchController;
        this.casino = casino;

        // Reading player data
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("player_data.txt");
        processInputStream(is);
    }

    private Player getPlayerByID(String id) {
        Player player = players.get(id);
        if (player == null) player = new Player(id);
        return player;
    }

    public Collection<Player> getPlayers() {
        return players.values();
    }

    private void updatePlayer(Player player) {
        players.put(player.getId(), player);
    }

    private void processInputStream(InputStream is) {

        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8); BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");

                String playerId = split[0];
                PlayerOperation operation = PlayerOperation.valueOf(split[1]);
                int coins = Integer.parseInt(split[3]);

                if (operation == PlayerOperation.BET) {
                    String matchId = split[2];
                    String bet = split[4];
                    processBet(playerId, matchId, coins, bet);
                } else if (operation == PlayerOperation.DEPOSIT) processDeposit(playerId, coins);
                else if (operation == PlayerOperation.WITHDRAW) processWithdraw(playerId, coins);
                else throw new Error("Invalid player operation: " + operation);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processBet(String playerId, String matchId, int coins, String bet) {
        Player player = getPlayerByID(playerId);
        Match match = matchController.getMatchByID(matchId);

        if (!player.isLegitimate()) return;

        if (player.getCoins() < coins) {
            player.setFirstIllegalOperation(PlayerOperation.BET + " " + matchId + " " + coins + " " + bet);
            player.setLegitimate(false);

        } else {
            player.addCoins(-coins);
            player.increaseBetsPlaced();

            int winnings = matchController.calculateMatchWinnings(matchId, coins, bet);
            player.addCoins(winnings);

            casino.addBalance(coins - winnings);

            if (Objects.equals(match.getResult(), bet)) player.increaseBetsWon();
        }

        updatePlayer(player);
    }

    private void processDeposit(String playerId, int coins) {
        Player player = getPlayerByID(playerId);

        player.addCoins(coins);
        updatePlayer(player);
    }

    private void processWithdraw(String playerId, int coins) {
        Player player = getPlayerByID(playerId);

        if (player.getCoins() < coins) {
            if (player.isLegitimate()) {
                player.setFirstIllegalOperation(PlayerOperation.WITHDRAW + " null " + coins + " null");
                player.setLegitimate(false);
            }
        } else player.addCoins(-coins);

        updatePlayer(player);
    }
}
