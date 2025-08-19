# 🎟️ WerseTokens

**WerseTokens** is a simple and lightweight **Minecraft** plugin that introduces a premium currency – **Tokens** – which can be used by players and managed by administrators.

# ✨ Features
- Easy configuration of all messages (supports HEX `&#RRGGBB` in prefix).
- Commands available both from player and console (e.g., integration with web stores).
- Permission system separating player and admin commands.
- **Top 10** command with customizable list format.
- Placeholder `%wersetokens_amount%` for integration with other plugins.
---

# ⚙️ Configuration (`config.yml`)
Default configuration file:
```yaml
# PREFIX section always appears before a message and supports HEX in the format &#RRGGBB
PREFIX: "[WerseTokens]: "
messages:
  adminAddTokensToPlayer: "Added %value% to %player% balance!"
  adminRemoveTokensFromPlayer: "Removed %value% from %player% balance!"
  adminSetTokensPlayer: "New balance of tokens for %player% has been set to %value%!"
  playerReceivedTokensFromAdmin: "Server just added %value% Token's to your balance!"
  playerGiveTokensToPlayer: "You just gave %value% Tokens to %player%"
  playerReceivedTokensFromPlayer: "You just received %value% tokens from %player%"
  playerRemovedTokensByAdmin: "%value% tokens hes been removed from your account"
  playerInfoSetTokensByAdmin: "Your balance is now %value% tokens"
  adminCheckBalanceOfPlayer: "Token balance of %player% is %value%"
  playerCheckSelfBalance: "Your Token Balance is %value%"
errors:
  noPermission: "You do not have permission %permission% to do this!"
  noPlayerOnline: "Your target player %player% is wrong or he's offline"
  noEnoughTokens: "You don't have enough tokens to do this"
  noConsole: "Only players can execute this command!"
balanceTop:
  header: "--- TOP 10 Tokens ---"
  maxPlayersInList: 10
  list: "%number%. %player% - %value% Tokens"
  footer: "Your Position : %position%\nYour value %value%"
```
# 📝 Commands & Permissions
## 👑 Admin

- /tokens add <player> <amount> – add tokens
    - Permission: wersetokens.addtokens

- /tokens remove <player> <amount> – remove tokens
  - Permission: wersetokens.removetokens

- /tokens set <player> <amount> – set token balance
  - Permission: wersetokens.settokens

- /tokens adminbalance <player> – check player balance
  - Permission: wersetokens.adminbalance

## 🎮 Player

- /tokens balance – check own balance
  - Permission: wersetokens.balance

- /tokens give <player> <amount> – give tokens to another player
  - Permission: wersetokens.pay

## 📊 Balance Top

Displays a ranking of players with the most tokens.

Header, list, and footer are fully configurable.

Shows the player’s own position and balance, even if they are not in the TOP 10.

## 🧩 Placeholders (PlaceholderAPI required)

%wersetokens_amount% – the player’s current token balance.

## 🛠️ Installation

Download the latest plugin version from Releases.

Place the .jar file in your server’s plugins folder.

Start the server to generate the config.yml.

Customize the configuration to your needs.

# 📜 License

This project is available under the MIT License – you are free to use, modify, and distribute it.