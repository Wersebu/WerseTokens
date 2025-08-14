package werse.jarvis.werseTokens.Commands.AdminCommands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import werse.jarvis.werseTokens.Commands.SetDeafultAmout.SetDeafultAmount;
import werse.jarvis.werseTokens.GetConfig.ConfigData;
import werse.jarvis.werseTokens.Logic.ReadDataFile;
import werse.jarvis.werseTokens.Logic.SaveDataToFile;

import java.util.HashMap;

public class TokenBalanceAdmin {
    private final JavaPlugin plugin;
    private final ReadDataFile readDataFile;
    private final SaveDataToFile saveDataToFile;
    private final ConfigData configData;
    private final SetDeafultAmount setDeafultAmount;
    private static String PREFIX;

    public TokenBalanceAdmin(JavaPlugin plugin) {
        this.plugin = plugin;
        this.readDataFile = new ReadDataFile(plugin);
        this.saveDataToFile = new SaveDataToFile(plugin);
        this.configData = new ConfigData(plugin);
        this.setDeafultAmount = new SetDeafultAmount(plugin);
        this.PREFIX = configData.getColoredPrefix();
    }

    public void tokenBalanceAdmin(Player player, Player askedPlayer) {
        HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
        String user = askedPlayer.getName();

        if (updateData.get(user) != null) {
            int value = updateData.get(user);

            player.sendMessage(PREFIX + "Stan konta gracza " + user + " to " + value + " tokenów.");
            saveDataToFile.addTokensToDatabase(updateData);
        } else {
            setDeafultAmount.setDeafultAmount(player, 0);
            player.sendMessage(PREFIX + "Gracz nie posiada tokenów");
            player.sendMessage(PREFIX + "Stan konta gracza " + user + " to " + 0 + " tokenów.");
        }
    }
}
