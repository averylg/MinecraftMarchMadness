# March Madness Minecraft Panel

## JMN-V
This is essentially a full-stack web app with the stack Java, Minecraft, NodeJS, VueJS (aka JMN-V).

## Breakdown:
The VueJS frontend uses:
- An HTTP client to POST the custom names of villagers and to alert the Minecraft server to spawn the zombies
- A WebSocket client to read results (villager names, health) send through the NodeJS server

The NodeJS server:
- Essentially the mediator that helps carry the data from one WebSocket client to another

The Minecraft server-side plugin made in Java using PaperMC, a fork of Spigot, contains:
- A WebSocket client to send data to the NodeJS server in real time (namely the villager health and the round status (still going, winner, draw))
- An HTTP Server using the Spark library to handle POST requests from the VueJS frontend client
- The logic to spawn villagers and zombies in an arena and process the results.
  - Two villagers and 20 zombies are spawned in an arena (location hard-coded).  The surviving villager wins and is made invulnerable to all except the server OP or whoever clicks the Spawn Villagers button

## Notes:
[This app was used to create a March Madness bracket.](ZombiesMarchMadnessBracket.pdf)
- For the villager names in each round, I used the team names in each bracket slot.
- Side note, since the Minecraft Zombie AI is what's choosing the villagers to target, this bracket is technically AI-generated...

The program/game had an oddly heavy bias towards villagers picked as team B (i.e. the zombies attacked the team A villager much more).

There was also exactly one draw (meaning both villagers died on the same tick)