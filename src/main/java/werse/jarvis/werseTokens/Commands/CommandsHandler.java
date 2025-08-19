package werse.jarvis.werseTokens.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import werse.jarvis.werseTokens.GetConfig.ConfigData;

import java.util.ArrayList;
import java.util.List;

public class CommandsHandler implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;
    private final ConfigData configData;
    private final CommandsIMPL commandsIMPL;
    private String PREFIX;

    public CommandsHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        commandsIMPL = new CommandsIMPL(plugin);
        this.configData = new ConfigData(plugin);
        this.PREFIX = configData.getColoredPrefix();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0 || args.length > 3) {
            commandSender.sendMessage(PREFIX + "wrong usage of /tokens\n"
                    + "Correct usage:\n"
                    + "/tokens balance ---> Check your balance\n"
                    + "/tokens balancetop ---> List of Richest Players\n"
                    + "/tokens pay <user> <amount> ---> Give some tokens to another players");
            Bukkit.getLogger().info("Gracz: " + commandSender.getName() + " niepoprawnie użył komendy /tokens");
            Bukkit.getLogger().info("Użyto " + command);
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("balance")) {
                commandsIMPL.balancePlayer(commandSender);
                return true;
            } else if (args[0].equalsIgnoreCase("balancetop")) {
                commandsIMPL.balanceTop(commandSender);
                return true;
            }
        }

        if (args.length == 2) {
            String userName = args[1];
            if (args[0].equalsIgnoreCase("adminbalance")) {
                commandsIMPL.tokenBalanceAdmin(commandSender, userName);
                return true;
            }
        }

        if (args.length == 3) {

            String userName = args[1];
            int value = Integer.parseInt(args[2]);

            if (args[0].equalsIgnoreCase("pay")) {
                commandsIMPL.giveTokens(commandSender, userName, value);
                return true;
            } else if (args[0].equalsIgnoreCase("balancetop")) {
                commandsIMPL.balanceTop(commandSender);
                return true;
            } else if (args[0].equalsIgnoreCase("set")) {
                commandsIMPL.setTokens(commandSender, userName, value);
                return true;
            } else if (args[0].equalsIgnoreCase("add")) {
                commandsIMPL.addTokensAdmin(commandSender, userName, value);
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                commandsIMPL.removeTokens(commandSender, userName, value);
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (commandSender.hasPermission("wersetokens.op")) {
                completions.add("add");
                completions.add("set");
                completions.add("remove");
                completions.add("balance");
                completions.add("balancetop");
                completions.add("pay");
            } else {
                completions.add("balance");
                completions.add("balancetop");
                completions.add("pay");
            }
        }

        if (args.length == 2) {
            if (commandSender.hasPermission("wersetokens.op")) {
                if (args[0].equalsIgnoreCase("add") ||
                        args[0].equalsIgnoreCase("set") ||
                        args[0].equalsIgnoreCase("remove") ||
                        args[0].equalsIgnoreCase("balance") ||
                        args[0].equalsIgnoreCase("balancetop")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        completions.add(player.getName());
                    }
                }
            } else {
                if (args[0].equalsIgnoreCase("pay")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        completions.add(player.getName());
                    }
                }
            }
        }
        return completions;
    }
}
