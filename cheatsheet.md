This file contains documentation for all commands available in the game.

# Commands
* The `help` command:
  * Provide players with possible choices that would match with their input.
  * when being directly, provide the players with an ideal format that they should phrase their input.
* The `exit` or `quit` commands:
  * Exits the game.
* The `manual` command:
  * When called, provide player with options of exiting the game and offering help.

# Challenges 
The player needs to pick a path when first entering the game, whether go to Albania or China to collect more hints for their investigation. No matter which path they chose, they will need to go to Tunisia for the next step, and talk to a Smithie who holds the key information about the murderer. 

Some paths through the game have restricted access until the player has chosen the right action or option. Some challenges that could get the player into a wrong path (and eventually lose the game) or result in a loop:

* When in Albania:
  * Start walking when everyone gives you mixed directions;
  * Choose not to eat any food that Grandma provides you
* When in China:
  * Argue with or explain to the waitress in the restaurant;
  * Call the police officers when encountering a suspicious man in the fancy gathering event
* When in Tunisia:
  * Choose not to peek through the window when arrived at hotel;
  * Choose to ignore the Smithie you encountered;
  * Ask about key information about the murderer on Smith Confessional.

# Description of the layout

Our game consists of the murder mystery of EggsBennedict, a resident of Elm St in Northampton. The player is the investigator tasked to solve the mystery. This can end up in catching the culprit in the best case, or missing him in the worst. The player's fate strictly depends on their own choices and decisions. The game starts at Logan Airport, and from there, the player is sent off abroad to follow the murderer. At each node (of type `Place`) the player gets to choose what their next move is, based on the options provided by the game. Although there aren't external objects that the player will have to pick up or use to be able to win the game, there are certain decisions that need to be made that determine the final outcome.

The path **spoiler alert** to winning the game consists of the following choices: 

  * If you chose Albania:
    * Take taxi ride and go to Elbasan;
    * Meet the grandma and take her food;
    * Take the address she gives you and follow it;
  * If you chose China:
    * Go home and rest;
    * Go to that family dinner;
    * Flip the fish and follow those coordinates;
  * When you're in Tunisia:
    * Go to the hotel;
    * Peek through the window;
    * Talk to the Smithie you saw;
    * Go back to campus;
    * Catch murderer at C-Z basement;

