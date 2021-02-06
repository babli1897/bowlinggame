Start Game API (uri "/start/game" )
Assumption: A Player will be assigned a new Lane only if previous lane has no more space, in case only one player remains in last lane, game will not be initialized then
Player Name is considered to be unique
This API initializes the game (creates game), assigns Players to a lane and pair with game id

curl -X POST 'http://localhost:9603/start/game?requestId=TGT01' -H 'Content-Type: application/json' -d '{"playerNames": ["Ankita Gupta","Sweta Karn","Dipanshu Verma","Himanshu Verma","Ritesh Kumar","Prateek Pradhan","Anjali Singh","Rahul Nag","Vishal Gautam"]}'


Get Score API (uri "/get/score")
This API is used to get information about a player in a particular game
curl -X GET 'http://localhost:9603/get/score?requestId=TGT01&gameId=40&playerName=Dipanshu%20Verma' -H 'Content-Type: application/json'


Get currentScoreBoard API (uri "/get/current/scoreboard")
This API is used to get the current scoreboard, i.e., All games in PLAYING status. A GAME can have 3 status (INITIALIZED, PLAYING, COMPLETED)
curl -X GET 'http://localhost:9603/get/current/scoreboard?requestId=TGT01' -H 'Content-Type: application/json'


Run db_scripts.sql  on mysql instance(5.x)
Run the main application


Problem Statement:
Design a Bowling Game: 
Game will consist of players and their scorecards which can be obtained at any point of the game. During the game, players and their scores will be maintained and shown by the system and winner will be declared at the end of the game.
Following are rules to be followed:
	•	In a single lane only 3 players are allowed to play. For ex: If there are 4 lanes (A, B, C and D) than each lane can have 3 players allocated for playing game.
	•	Game will consist of ten sets per player and in each set, each player has two opportunities to knock down ten pins (bottles in simple term) 
    so, in total a player has twenty chances for all ten sets. 
The score for a set is the total number of pins knocked down, plus bonuses for strikes and spares.
A spare is when the player knocks down all ten pins in two tries. If there is spare the player gets 5 bonus points.
A strike is when the player knocks down all ten pins on his/her first try. If there is a strike the player gets 10 bonus points.
In the final set, a player who rolls a spare or a strike is allowed to roll the extra balls to complete the set. However, only a maximum of three balls can be rolled in the final set.
