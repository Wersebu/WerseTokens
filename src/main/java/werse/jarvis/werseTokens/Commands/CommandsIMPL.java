package werse.jarvis.werseTokens.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import werse.jarvis.werseTokens.GetConfig.ConfigData;
import werse.jarvis.werseTokens.Logic.ReadDataFile;
import werse.jarvis.werseTokens.Logic.SaveDataToFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandsIMPL {

    private final JavaPlugin plugin;
    private final ReadDataFile readDataFile;
    private final SaveDataToFile saveDataToFile;
    private final ConfigData configData;
    private final PlayerJoinEventHandler playerJoinEventHandler;
    private static String prefix;
    private final String adminAddTokensToPlayer;
    private final String adminRemoveTokensFromPlayer;
    private final String adminSetTokensPlayer;
    private final String playerReceivedTokensFromAdmin;
    private final String playerGiveTokensToPlayer;
    private final String playerReceivedTokensFromPlayer;
    private final String playerRemovedTokensByAdmin;
    private final String playerInfoSetTokensByAdmin;
    private final String noPermission;
    private final String noPlayerOnline;
    private final String noEnoughTokens;
    private final String noConsole;
    private final String adminCheckBalanceOfPlayer;
    private final String playerCheckSelfBalance;
    private final String balTopHeader;
    private final int maxPlayersInList;
    private final String balToplist;
    private final String balTopFooter;


    public CommandsIMPL (JavaPlugin plugin) {
        this.plugin = plugin;
        this.readDataFile = new ReadDataFile(plugin);
        this.saveDataToFile = new SaveDataToFile(plugin);
        this.configData = new ConfigData(plugin);
        this.playerJoinEventHandler = new PlayerJoinEventHandler(plugin);
        this.prefix = configData.getColoredPrefix();
        this.adminAddTokensToPlayer = plugin.getConfig().getString("messages.adminAddTokensToPlayer");
        this.adminRemoveTokensFromPlayer = plugin.getConfig().getString("messages.adminAddTokensToPlayer");
        this.adminSetTokensPlayer = plugin.getConfig().getString("messages.adminSetTokensPlayer");
        this.playerReceivedTokensFromAdmin = plugin.getConfig().getString("messages.playerReceivedTokensFromAdmin");
        this.playerGiveTokensToPlayer = plugin.getConfig().getString("messages.playerGiveTokensToPlayer");
        this.playerReceivedTokensFromPlayer = plugin.getConfig().getString("messages.playerReceivedTokensFromPlayer");
        this.playerRemovedTokensByAdmin = plugin.getConfig().getString("messages.playerRemovedTokensByAdmin");
        this.playerInfoSetTokensByAdmin = plugin.getConfig().getString("messages.playerInfoSetTokensByAdmin");
        this.noPermission = plugin.getConfig().getString("errors.noPermission");
        this.noPlayerOnline = plugin.getConfig().getString("errors.noPlayerOnline");
        this.noEnoughTokens = plugin.getConfig().getString("errors.noEnoughTokens");
        this.noConsole = plugin.getConfig().getString("errors.noConsole");
        this.adminCheckBalanceOfPlayer = plugin.getConfig().getString("messages.adminCheckBalanceOfPlayer");
        this.playerCheckSelfBalance = plugin.getConfig().getString("messages.playerCheckSelfBalance");
        this.balTopHeader = plugin.getConfig().getString("balanceTop.header");
        this.maxPlayersInList = plugin.getConfig().getInt("balanceTop.maxPlayersInList");
        this.balToplist = plugin.getConfig().getString("balanceTop.list");
        this.balTopFooter = plugin.getConfig().getString("balanceTop.footer");
    }

    public void addTokensAdmin(CommandSender commandSender, String userName, int value) {

        Player addingPlayer = Bukkit.getPlayer(userName);

        if (commandSender instanceof Player) {

            Player player = ((Player) commandSender).getPlayer();

            if (player.hasPermission("wersetokens.addtokens") || player.isOp()) {
                if (addingPlayer.isOnline()) {

                    HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
                    String user = addingPlayer.getName();
                    int currentValue = updateData.getOrDefault(user, 0);
                    int newValue = currentValue + value;

                    updateData.put(user, newValue);
                    addingPlayer.sendMessage(prefix + playerReceivedTokensFromAdmin.replaceAll("%value%", String.valueOf(value)));
                    player.sendMessage(prefix + adminAddTokensToPlayer.replaceAll("%value%", String.valueOf(value)).replaceAll("%player%", addingPlayer.getName()));
                    saveDataToFile.addTokensToDatabase(updateData);

                } else {
                    player.sendMessage(prefix + noPlayerOnline.replaceAll("%player%", addingPlayer.getName()));
                }
            } else {
                player.sendMessage(prefix + noPermission.replaceAll("%permission%", "wersetokens.addtokens"));
            }

        } else {
            if (addingPlayer.isOnline()) {
                HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
                String user = addingPlayer.getName();
                int currentValue = updateData.getOrDefault(user, 0);
                int newValue = currentValue + value;

                updateData.put(user, newValue);
                addingPlayer.sendMessage(prefix + playerReceivedTokensFromAdmin.replaceAll("%value%", String.valueOf(value)));
                Bukkit.getLogger().info(prefix + adminAddTokensToPlayer.replaceAll("%value%", String.valueOf(value)).replaceAll("%player%", addingPlayer.getName()));
                saveDataToFile.addTokensToDatabase(updateData);
            } else {
                Bukkit.getLogger().info(prefix + noPlayerOnline.replaceAll("%player%", addingPlayer.getName()));
            }
        }
    }

    public void removeTokens(CommandSender commandSender, String userName, int value) {

        Player punishmentPlayer = Bukkit.getPlayer(userName);

        if (commandSender instanceof Player) {

            Player player = ((Player) commandSender).getPlayer();

            if (player.hasPermission("wersetokens.removetokens") || player.isOp()) {
                if (punishmentPlayer.isOnline()) {
                    HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
                    String user = punishmentPlayer.getName();

                    int currentValue = updateData.getOrDefault(user, 0);
                    int newValue = currentValue - value;

                    updateData.put(user, newValue);

                    punishmentPlayer.sendMessage(prefix + playerRemovedTokensByAdmin.replaceAll("%value%", String.valueOf(value)));
                    player.sendMessage(prefix + adminRemoveTokensFromPlayer.replaceAll("%value%", String.valueOf(value)).replaceAll("%player%", punishmentPlayer.getName()));
                    saveDataToFile.addTokensToDatabase(updateData);
                } else {
                    player.sendMessage(prefix + noPlayerOnline.replaceAll("%player%", punishmentPlayer.getName()));
                }
            } else {
                player.sendMessage(prefix + noPermission.replaceAll("%permission%", "wersetokens.removetokens"));
            }
        } else {
            if (punishmentPlayer.isOnline()) {
                HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
                String user = punishmentPlayer.getName();

                int currentValue = updateData.getOrDefault(user, 0);
                int newValue = currentValue - value;

                updateData.put(user, newValue);

                punishmentPlayer.sendMessage(prefix + playerRemovedTokensByAdmin.replaceAll("%value%", String.valueOf(value)));
                Bukkit.getLogger().info(prefix + adminRemoveTokensFromPlayer.replaceAll("%value%", String.valueOf(value)).replaceAll("%player%", punishmentPlayer.getName()));
                saveDataToFile.addTokensToDatabase(updateData);
            } else {
                Bukkit.getLogger().info(prefix + noPlayerOnline.replaceAll("%player%", punishmentPlayer.getName()));
            }
        }


    }

    public void setTokens(CommandSender commandSender, String userName, int value) {

        Player target = Bukkit.getPlayer(userName);

        if (commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();
            if (player.hasPermission("wersetokens.settokens") || player.isOp()) {
                if (target.isOnline()) {
                    HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();

                    updateData.put(userName, value);

                    target.sendMessage(prefix + playerInfoSetTokensByAdmin.replaceAll("%value%", String.valueOf(value)));
                    player.sendMessage(prefix + adminSetTokensPlayer.replaceAll("%value%", String.valueOf(value)).replaceAll("%player%", target.getName()));
                    saveDataToFile.addTokensToDatabase(updateData);

                } else {
                    player.sendMessage(prefix + noPlayerOnline.replaceAll("%player%", target.getName()));
                }
            } else {
                player.sendMessage(prefix + noPermission.replaceAll("%permission%", "wersetokens.settokens"));
            }
        } else {
            if (target.isOnline()) {
                HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();

                updateData.put(userName, value);

                target.sendMessage(prefix + playerInfoSetTokensByAdmin.replaceAll("%value%", String.valueOf(value)));
                Bukkit.getLogger().info(prefix + adminSetTokensPlayer.replaceAll("%value%", String.valueOf(value)).replaceAll("%player%", target.getName()));
                saveDataToFile.addTokensToDatabase(updateData);

            } else {
                Bukkit.getLogger().info(prefix + noPlayerOnline.replaceAll("%player%", target.getName()));
            }
        }


    }

    public void tokenBalanceAdmin(CommandSender commandSender, String userName) {

        Player askedPlayer = Bukkit.getPlayer(userName);

        if (commandSender instanceof Player) {

            Player player = ((Player) commandSender).getPlayer();

            if (player.hasPermission("wersetokens.adminbalance") || player.isOp()) {
                HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
                String user = askedPlayer.getName();

                if (updateData.get(user) != null) {
                    int value = updateData.get(user);

                    player.sendMessage(prefix + adminCheckBalanceOfPlayer.replaceAll("%player%", askedPlayer.getName()).replaceAll("%value%", String.valueOf(value)));
                    saveDataToFile.addTokensToDatabase(updateData);
                } else {
                    playerJoinEventHandler.setDeafultAmount(player, 0);
                    player.sendMessage(prefix + adminCheckBalanceOfPlayer.replaceAll("%player%", askedPlayer.getName()).replaceAll("%value%", String.valueOf(0)));
                }
            } else {
                player.sendMessage(prefix + noPermission.replaceAll("%permission%", "wersetokens.adminbalance"));
            }
        } else {
            HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
            String user = askedPlayer.getName();

            if (updateData.get(user) != null) {
                int value = updateData.get(user);

                Bukkit.getLogger().info(prefix + adminCheckBalanceOfPlayer.replaceAll("%player%", askedPlayer.getName()).replaceAll("%value%", String.valueOf(value)));
                saveDataToFile.addTokensToDatabase(updateData);
            } else {
                playerJoinEventHandler.setDeafultAmount(askedPlayer, 0);
                Bukkit.getLogger().info(prefix + adminCheckBalanceOfPlayer.replaceAll("%player%", askedPlayer.getName()).replaceAll("%value%", String.valueOf(0)));
            }
        }
    }

    public void giveTokens(CommandSender commandSender, String userName, int value) {

        Player toPlayer = Bukkit.getPlayer(userName);

        if (commandSender instanceof Player) {
            Player fromPlayer = ((Player) commandSender).getPlayer();
            if (fromPlayer.hasPermission("wersetokens.pay")) {
                HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();

                String fromPlayerName = fromPlayer.getName();
                String toPlayerName = toPlayer.getName();

                if (updateData.get(fromPlayerName) == null) {
                    playerJoinEventHandler.setDeafultAmount(fromPlayer, 0);
                }
                if (updateData.get(toPlayerName) == null) {
                    playerJoinEventHandler.setDeafultAmount(toPlayer, 0);
                }
                updateData = readDataFile.readDataFromDatabase();

                int accualValueFromPlayerName = updateData.get(fromPlayerName);

                if (accualValueFromPlayerName >= value) {
                    int newValueFromPlayerName = updateData.get(fromPlayerName) - value;
                    int newValueToPlayerName = updateData.get(toPlayerName) + value;

                    updateData.put(fromPlayerName, newValueFromPlayerName);
                    updateData.put(toPlayerName, newValueToPlayerName);

                    fromPlayer.sendMessage(prefix + playerGiveTokensToPlayer.replaceAll("%player%", toPlayerName).replaceAll("%value%", String.valueOf(value)));
                    toPlayer.sendMessage(prefix + playerReceivedTokensFromPlayer.replaceAll("%player%", fromPlayerName).replaceAll("%value%", String.valueOf(value)));

                    saveDataToFile.addTokensToDatabase(updateData);
                } else {
                    fromPlayer.sendMessage(prefix + noEnoughTokens);
                }
            } else {
                fromPlayer.sendMessage(prefix + noPermission.replaceAll("%permission%", "wersetokens.pay"));
            }
        } else {
            Bukkit.getLogger().info(prefix + noConsole);
        }
    }

    public void balancePlayer(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();
            if (player.hasPermission("wersetokens.balance")) {
                HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
                if (updateData.get(player.getName()) == null) {
                    playerJoinEventHandler.setDeafultAmount(player, 0);
                }
                updateData = readDataFile.readDataFromDatabase();

                player.sendMessage(prefix + playerCheckSelfBalance.replaceAll("%value%", String.valueOf(updateData.get(player.getName()))));
            } else {
                player.sendMessage(prefix + noPermission.replaceAll("%permission%","wersetokens.balance"));
            }
        } else {
            Bukkit.getLogger().info(prefix + noConsole);
        }

    }

    public void balanceTop(CommandSender commandSender) {
        if (commandSender instanceof Player) {

            Player player = ((Player) commandSender).getPlayer();

            if (maxPlayersInList > 0) {
                HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();

                List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(updateData.entrySet());
                sortedList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
                player.sendMessage(balTopHeader);

                for (int i = 0; i < Math.min(maxPlayersInList, sortedList.size()); i++) {
                    Map.Entry<String, Integer> entry = sortedList.get(i);
                    player.sendMessage(balToplist.replaceAll("%number%", String.valueOf(i + 1)).replaceAll("%player%", entry.getKey()).replaceAll("%value%", String.valueOf(entry.getValue())));
                }

                // Znalezienie pozycji gracza
                int playerPosition = -1;
                int playerTokens = updateData.getOrDefault(player.getName(), 0);
                for (int i = 0; i < sortedList.size(); i++) {
                    if (sortedList.get(i).getKey().equals(player.getName())) {
                        playerPosition = i + 1;
                        break;
                    }
                }
                if (playerPosition != -1) {
                    player.sendMessage(balTopFooter.replaceAll("%position%", String.valueOf(playerPosition)).replaceAll("%value%", String.valueOf(playerTokens)));
                } else {
                    player.sendMessage("§cNie masz jeszcze żadnych tokenów.");
                }
            }
        }


    }
}
