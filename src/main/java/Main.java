import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static void addEmptyLine(BufferedWriter writer) throws IOException {
        writer.write("\n");
        writer.newLine();
    }

    public static void main(String[] args) throws IOException {
        Casino casino = new Casino();
        MatchController matchController = new MatchController();
        PlayerController playerController = new PlayerController(matchController, casino);

        // Writing results to result.txt

        List<Player> illegitimatePlayers = new ArrayList<>();
        boolean resultHasLegimatePlayers = false;

        BufferedWriter resultWriter = new BufferedWriter(new FileWriter("src/main/java/result.txt"));

        for (Player player : playerController.getPlayers()) {
            if (player.isLegitimate()) {
                resultHasLegimatePlayers = true;
                resultWriter.write(player.getId() + " " + player.getCoins() + " " + player.calculateBettingWinRate());
            } else illegitimatePlayers.add(player);
        }

        if (!resultHasLegimatePlayers) addEmptyLine(resultWriter);

        addEmptyLine(resultWriter);

        for (Player player : illegitimatePlayers) {
            resultWriter.write(player.getId() + " " + player.getFirstIllegalOperation());
        }

        if (illegitimatePlayers.isEmpty()) addEmptyLine(resultWriter);

        addEmptyLine(resultWriter);

        resultWriter.write(String.valueOf(casino.getBalance()));

        resultWriter.close();
    }
}
