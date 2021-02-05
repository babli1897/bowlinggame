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