# 🎟️ WerseTokens

**WerseTokens** to prosty i lekki plugin do **Minecraft** dodający nową walutę premium – **Tokeny** – które mogą być używane przez graczy oraz zarządzane przez administratorów.

# ✨ Funkcje
- Łatwa konfiguracja wszystkich wiadomości (obsługa HEX `&#RRGGBB` w prefixie).
- Obsługa komend zarówno z poziomu gracza, jak i konsoli (np. integracja ze sklepem WWW).
- System permisji rozdzielający uprawnienia graczy i administratorów.
- Komenda **Top 10** z możliwością personalizacji wyglądu listy.
- Placeholder `%wersetokens_amount%` do integracji z innymi pluginami.

---

# ⚙️ Konfiguracja (`config.yml`)
Przykładowy plik konfiguracyjny:
```yaml
#Sekcja PREFIX pojawia się zawsze przed wiadomością i obsługuje HEX w formacie &#RRGGBB
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
# 📝 Komendy i Permisje
## 👑 Admin

/tokens add <player> <amount> – dodaje tokeny
Permisja: wersetokens.addtokens

/tokens remove <player> <amount> – usuwa tokeny
Permisja: wersetokens.removetokens

/tokens set <player> <amount> – ustawia saldo tokenów
Permisja: wersetokens.settokens

/tokens adminbalance <player> – sprawdza saldo gracza
Permisja: wersetokens.adminbalance

## 🎮 Gracz

/tokens balance – sprawdza własne saldo
Permisja: wersetokens.balance

/tokens give <player> <amount> – przekazuje tokeny innemu graczowi
Permisja: wersetokens.pay

## 📊 Balance Top

Wyświetla ranking graczy z największą liczbą tokenów.

Nagłówek, lista i stopka są w pełni konfigurowalne.

Wyświetla również pozycję oraz saldo gracza, nawet jeśli nie jest w TOP 10.

## 🧩 Placeholdery

%wersetokens_amount% – aktualna liczba tokenów gracza.

## 🛠️ Instalacja

Pobierz najnowszą wersję pluginu z Releases
.

Wrzuć plik .jar do folderu plugins na serwerze.

Uruchom serwer, aby wygenerował się plik config.yml.

Dostosuj konfigurację do swoich potrzeb.

## 📜 Licencja

Projekt dostępny na licencji MIT – możesz używać, modyfikować i rozwijać plugin w dowolny sposób.