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
import werse.jarvis.werseTokens.Commands.AdminCommands.AddTokens;
import werse.jarvis.werseTokens.Commands.AdminCommands.RemoveTokens;
import werse.jarvis.werseTokens.Commands.AdminCommands.SetTokens;
import werse.jarvis.werseTokens.Commands.AdminCommands.TokenBalanceAdmin;
import werse.jarvis.werseTokens.Commands.ConsoleCommands.AddTokensConsole;
import werse.jarvis.werseTokens.Commands.ConsoleCommands.RemoveTokensConsole;
import werse.jarvis.werseTokens.Commands.ConsoleCommands.SetTokensConsole;
import werse.jarvis.werseTokens.Commands.ConsoleCommands.TokenBalanceAdminConsole;
import werse.jarvis.werseTokens.Commands.PlayerCommands.GiveTokens;
import werse.jarvis.werseTokens.Commands.PlayerCommands.TokenBalance;
import werse.jarvis.werseTokens.GetConfig.ConfigData;

import java.util.ArrayList;
import java.util.List;

public class CommandsHandler implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;
    private final TokenBalance tokenBalance;
    private final AddTokens addTokens;
    private final RemoveTokens removeTokens;
    private final SetTokens setTokens;
    private final TokenBalanceAdmin tokenBalanceAdmin;
    private final GiveTokens giveTokens;
    private final ConfigData configData;
    private final AddTokensConsole addTokensConsole;
    private final RemoveTokensConsole removeTokensConsole;
    private final SetTokensConsole setTokensConsole;
    private final TokenBalanceAdminConsole tokenBalanceAdminConsole;
    private String PREFIX;

    public CommandsHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        this.tokenBalance = new TokenBalance(plugin);
        this.addTokens = new AddTokens(plugin);
        this.removeTokens = new RemoveTokens(plugin);
        this.setTokens = new SetTokens(plugin);
        this.tokenBalanceAdmin = new TokenBalanceAdmin(plugin);
        this.giveTokens = new GiveTokens(plugin);
        this.configData = new ConfigData(plugin);
        this.PREFIX = configData.getColoredPrefix();
        this.addTokensConsole = new AddTokensConsole(plugin);
        this.removeTokensConsole = new RemoveTokensConsole(plugin);
        this.setTokensConsole = new SetTokensConsole(plugin);
        this.tokenBalanceAdminConsole = new TokenBalanceAdminConsole(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(PREFIX + "Niepoprawne użycie /tokens\n"
                    + "Poprawne użycie:\n"
                    + "/tokens balance ---> Sprawdzenie stanu konta\n"
                    + "/tokens balancetop ---> Lista najbogatszych\n"
                    + "/tokens give <gracz> <wartość> ---> Przekazanie tokenów dla innego gracza");
            Bukkit.getLogger().info("Gracz: " + commandSender.getName() + " niepoprawnie użył komendy /tokens");
            Bukkit.getLogger().info("Użyto " + command);
            return true;
        }

        //Komendy Graczy
        if (commandSender instanceof Player) {
            if (args[0].equalsIgnoreCase("balance")) {
                if (commandSender.hasPermission("wersetokens.op")) {
                    if (args.length == 2) {
                        Player player = Bukkit.getPlayer(commandSender.getName());
                        Player askedPlayer = Bukkit.getPlayer(args[1]);
                        tokenBalanceAdmin.tokenBalanceAdmin(player, askedPlayer);
                        return true;
                    } else if (args.length == 1) {
                        Player player = Bukkit.getPlayer(commandSender.getName());
                        tokenBalance.balancePlayer(player);
                        return true;
                    } else {
                        commandSender.sendMessage(PREFIX + "Niepoprawne użycie /tokens\n"
                                + "Poprawne użycie:\n"
                                + "/tokens balance ---> Sprawdzenie stanu konta\n"
                                + "/tokens balancetop ---> Lista najbogatszych\n"
                                + "/tokens give <gracz> <wartość> ---> Przekazanie tokenów dla innego gracza");
                        return true;
                    }

                } else if (commandSender.hasPermission("wersetokens.balance")) {
                    Player player = Bukkit.getPlayer(commandSender.getName());
                    tokenBalance.balancePlayer(player);
                    return true;
                } else {
                    Bukkit.getLogger().warning("Gracz: " + (commandSender).getName()
                            + "Próbował uzyć: /tokens balance lecz nie posiada uprawnienia wersetokens.balance");
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("balancetop")) {
                if (commandSender.hasPermission("wersetokens.balancetop")) {
                    Player player = Bukkit.getPlayer(commandSender.getName());
                    tokenBalance.balanceTop(player);
                    return true;
                } else {
                    Bukkit.getLogger().warning("Gracz: " + (commandSender).getName()
                            + "Próbował uzyć: /tokens balancetop lecz nie posiada uprawnienia wersetokens.balancetop");
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("give")) {
                if (args.length == 3) {
                    if (commandSender.hasPermission("wersetokens.give")) {
                        Player fromPlayer = Bukkit.getPlayer(commandSender.getName());
                        Player toPlayer = Bukkit.getPlayer(args[1]);
                        int value = Integer.parseInt(args[2]);
                        giveTokens.giveTokens(fromPlayer, toPlayer, value);
                        return true;
                    } else {
                        Bukkit.getLogger().warning("Gracz: " + (commandSender).getName()
                                + "Próbował uzyć: /tokens give lecz nie posiada uprawnienia wersetokens.give");
                        return true;
                    }
                } else {
                    commandSender.sendMessage(PREFIX + "Niepoprawne użycie /tokens\n"
                            + "Poprawne użycie:\n"
                            + "/tokens balance ---> Sprawdzenie stanu konta\n"
                            + "/tokens balancetop ---> Lista najbogatszych\n"
                            + "/tokens give <gracz> <wartość> ---> Przekazanie tokenów dla innego gracza");
                    return true;
                }

            }
        }

        if (commandSender instanceof Player) {
            //Komendy Administracyjne
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length == 3) {
                    if (commandSender.hasPermission("wersetokens.op")) {
                        Player player = Bukkit.getPlayer(commandSender.getName());
                        Player addingPlayer = Bukkit.getPlayer(args[1]);
                        int value = Integer.parseInt(args[2]);
                        addTokens.addTokensAdmin(player, addingPlayer, value);
                        return true;
                    } else {
                        Bukkit.getLogger().warning("Gracz: " + (commandSender).getName()
                                + "Próbował uzyć: /tokens add lecz nie posiada uprawnienia wersetokens.op");
                        return true;
                    }
                } else {
                    commandSender.sendMessage(PREFIX + "Poprawne uzycie komendy /tokens add <Nick> <Wartość>");
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("remove")) {
                if (args.length == 3) {
                    if (commandSender.hasPermission("wersetokens.op")) {
                        Player punishmentPlayer = Bukkit.getPlayer(args[1]);
                        Player player = Bukkit.getPlayer(commandSender.getName());
                        int value = Integer.parseInt(args[2]);
                        removeTokens.removeTokens(player, punishmentPlayer, value);
                        return true;
                    } else {
                        Bukkit.getLogger().warning("Gracz: " + (commandSender).getName()
                                + "Próbował uzyć: /tokens remove lecz nie posiada uprawnienia wersetokens.op");
                        return true;
                    }
                } else {
                    commandSender.sendMessage(PREFIX + "Poprawne uzycie komendy /tokens remove <Nick> <Wartość>");
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("set")) {
                if (args.length == 3) {
                    if (commandSender.hasPermission("wersetokens.op")) {
                        Player admin = Bukkit.getPlayer(commandSender.getName());
                        Player player = Bukkit.getPlayer(args[1]);
                        int value = Integer.parseInt(args[2]);
                        setTokens.setTokens(admin, player, value);
                        return true;
                    } else {
                        Bukkit.getLogger().warning("Gracz: " + (commandSender).getName()
                                + "Próbował uzyć: /tokens set lecz nie posiada uprawnienia wersetokens.op");
                        return true;
                    }
                } else {
                    commandSender.sendMessage(PREFIX + "Poprawne uzycie komendy /tokens set <Nick> <Wartość>");
                    return true;
                }
            }
        } else {
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length == 3) {
                    Player addingPlayer = Bukkit.getPlayer(args[1]);
                    int value = Integer.parseInt(args[2]);
                    addTokensConsole.addTokensAdminConsole(addingPlayer, value);
                    return true;
                } else {
                    Bukkit.getLogger().info("Poprawne uzycie komendy /tokens add <Nick> <Wartość>");
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("remove")) {
                if (args.length == 3) {
                    Player punishmentPlayer = Bukkit.getPlayer(args[1]);
                    int value = Integer.parseInt(args[2]);
                    removeTokensConsole.removeTokensConsole(punishmentPlayer, value);
                    return true;
                } else {
                    Bukkit.getLogger().info("Poprawne uzycie komendy /tokens remove <Nick> <Wartość>");
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("set")) {
                if (args.length == 3) {
                    Player player = Bukkit.getPlayer(args[1]);
                    int value = Integer.parseInt(args[2]);
                    setTokensConsole.setTokensConsole(player, value);
                    return true;

                } else {
                    Bukkit.getLogger().info("Poprawne uzycie komendy /tokens set <Nick> <Wartość>");
                    return true;
                }
            }
            if (args.length == 2) {
                Player askedPlayer = Bukkit.getPlayer(args[1]);
                tokenBalanceAdminConsole.tokenBalanceAdminConsole(askedPlayer);
                return true;
            } else {
                Bukkit.getLogger().info("Niepoprawne użycie /tokens\n"
                        + "Poprawne użycie:\n"
                        + "/tokens add <nick> <wartość> ---> Dodanie tokenów do konta gracza\n"
                        + "/tokens remove <nick> <wartość> ---> Usunięcie tokenów z konta gracza\n"
                        + "/tokens set <gracz> <wartość> ---> Ustawienie konkretnej wartości tokenów u gracza"
                        + "/tokens balance <nick> ---> Sprawdzenie stanu tokenów danego gracza");
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
            } else {
                completions.add("balance");
                completions.add("balancetop");
                completions.add("give");
            }
        }

        if (args.length == 2) {
            if (commandSender.hasPermission("wersetokens.op")) {
                if (args[0].equalsIgnoreCase("add") ||
                        args[0].equalsIgnoreCase("set") ||
                        args[0].equalsIgnoreCase("remove") ||
                        args[0].equalsIgnoreCase("balance") ||
                        args[0].equalsIgnoreCase("balancetop")) {

                    // Dodawanie graczy online do podpowiedzi
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        completions.add(player.getName());
                    }
                }
            } else {
                if (args[0].equalsIgnoreCase("give")) {
                    // Dodawanie graczy online do podpowiedzi
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        completions.add(player.getName());
                    }
                }
            }
        }


        return completions;
    }
}
