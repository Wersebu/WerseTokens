package werse.jarvis.werseTokens.Commands.PlayerCommands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import werse.jarvis.werseTokens.Commands.AdminCommands.SetTokens;
import werse.jarvis.werseTokens.Commands.SetDeafultAmout.SetDeafultAmount;
import werse.jarvis.werseTokens.GetConfig.ConfigData;
import werse.jarvis.werseTokens.Logic.ReadDataFile;
import werse.jarvis.werseTokens.Logic.SaveDataToFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenBalance {
    private final JavaPlugin plugin;
    private final ReadDataFile readDataFile;
    private final SaveDataToFile saveDataToFile;
    private final ConfigData configData;
    private final SetDeafultAmount setDeafultAmount;
    private static String PREFIX;

    public TokenBalance(JavaPlugin plugin) {
        this.plugin = plugin;
        this.readDataFile = new ReadDataFile(plugin);
        this.saveDataToFile = new SaveDataToFile(plugin);
        this.configData = new ConfigData(plugin);
        this.setDeafultAmount = new SetDeafultAmount(plugin);
        this.PREFIX = configData.getColoredPrefix();
    }

    public void balancePlayer(Player player) {
        HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
        String user = player.getName();
        if (updateData.get(user) == null) {
            setDeafultAmount.setDeafultAmount(player, 0);
        }
        updateData = readDataFile.readDataFromDatabase();

        int accualValue = updateData.get(user);

        player.sendMessage(PREFIX + "Stan twoich tokenów to " + accualValue);
    }

    public void balanceTop(Player player) {
        HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();

        // Sortowanie wyników od największej liczby tokenów
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(updateData.entrySet());
        sortedList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue())); // Sortowanie malejące

        int pageSize = 10; // Ilość wyników na stronie
        player.sendMessage("§a--- TOP 10 Tokenów ---");

        // Wyświetlanie pierwszych 10 wyników
        for (int i = 0; i < Math.min(pageSize, sortedList.size()); i++) {
            Map.Entry<String, Integer> entry = sortedList.get(i);
            player.sendMessage("§e" + (i + 1) + ". " + entry.getKey() + ": §a" + entry.getValue() + " Tokenów");
        }

        // Znalezienie pozycji gracza
        int playerPosition = -1;
        int playerTokens = updateData.getOrDefault(player.getName(), 0);
        for (int i = 0; i < sortedList.size(); i++) {
            if (sortedList.get(i).getKey().equals(player.getName())) {
                playerPosition = i + 1; // Pozycja gracza (indeks + 1)
                break;
            }
        }

        // Wyświetlanie pozycji gracza
        if (playerPosition != -1) {
            player.sendMessage("§bTwoja pozycja: §e" + playerPosition + " §bTwoje tokeny: §a" + playerTokens);
        } else {
            player.sendMessage("§cNie masz jeszcze żadnych tokenów.");
        }
    }


}
