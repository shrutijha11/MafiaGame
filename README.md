# MafiaGame
This is a game called Mafia.        
Mafia is a popular party game that requires one to think, manipulate, and deceive others in order to win.     
Since the COVID-19 pandemic, it has become rather difficult for people to host parties; hence I developed a mafia game simulator.          
    
Rules of the game: 

Plot: There is a village of N players. A player can be either a commoner, a detective, a healer,   
or a mafia. A commoner only knows that he is a commoner. A detective knows all the other        
detectives. A mafia knows all the other Mafia players. A healer knows all other healers in the         
game.    

Objectives: The objective for the mafias is to kill or eliminate all the non-mafias such that the        
ratio of the alive mafias to all others is 1:1.
A player can be eliminated in two ways:
1) By being killed by the Mafia or 2) Be eliminated in a vote out. Once a player is eliminated, they cannot be         
brought back to life. Mafias cannot kill themselves.                                            
The objective for all other players(except the mafias) is to eliminate the mafias through a vote    
out(as the Mafias cannot be killed). Therefore, by using special powers of detectives and     
healers, they are required to save themselves and vote out the Mafias. 

Role of different type of players:                         
1. Mafia: To kill all other players to achieve a 1:1 ratio.                                 
2. Detective: They can randomly test one of the players (except detective) to test whether    
the player is mafia or not. If they correctly identify a mafia, the caught mafia will be voted     
out in that round by default.                                                             
3. Healer: They randomly select a player from the game to give him a boost of 500 HP (All   
players, including mafias and healers themselves).                                          
4. Commoner: They don’t have any special role. They only take part in the voting process. 

End of Game:                                                                                
The game ends when either all mafias are voted out or the ratio of mafias to others becomes      
1:1.                                                       
The Mafia wins in the latter case and loses in the former.                                    
The winners are announced at the end of the game along with the roles of each player.       
                                                 
The design/working of the game is as follows:      
[A] On starting the game:                                                                 
1) Each invocation of the game will be considered a new game. At the beginning of each execution, the user is       
supposed to enter the number N, i.e., the total number of players in the game. The             
minimum number of players for the game is 6.                       
2) Then the user is given the option of either choosing their character (Mafia, Commoner,     
Detective, or Healer) on their own or be allotted a character randomly by the program.             
After allocating the user his/her role, there should be N/5 Mafias, N/5 Detectives, max {1,       
N/10} healers, and the remaining players should be commoners.
[B] Gameplay                                                                                
1) The game will run in rounds. In each round, there will be fixed steps that are followed.     
a) Firstly the Mafias choose to kill a person(other than mafias). If the user is a mafia       
player, then he/she will be asked to choose the target.                             
b) Then the detective will test a person to know whether he is mafia or not. If the     
user is a detective player, then he/she will be asked who they want to test.           
c) Then the healer will heal a person by giving him an HP boost as defined in HP       
rules. If the user is a healer player, then he/she will be asked who they want to        
heal.                                                                               
d) If after healing, the HP of the person killed by the Mafia reaches 0, the player     
dies.                                                                            
e) After this, there is a voting round where each player votes randomly, and the    
user(If alive) votes for whoever he/she wants to vote for. The person with the      
highest votes is kicked out of the game.                                             
f) However, if the detective tests positive on a mafia, there will be no voting, and         
the caught mafia will be voted out by default. Once a player is out of the game,        
they can not re-enter the game.                                                              
g) If there are no detectives left, there will be no test and/or if there are no healers         
left, there will be no HP boost. The game will keep on moving until we arrive at an        
end of game condition.                                                                         
j) In case the votes are tied, the voting process is repeated until there is no
tie.
        
2) HP Rules:    
a) All commoners start with an HP of 1000 each.    
b) All mafias will start with 2500 HP each.       
c) All Detectives and Healers start with an HP of 800 each.      
d) In each Round, Healers can increase any players HP by 500.      
e) When the mafias choose a target, if their combined HP is equal to or more than    
that target's current HP, the target's HP becomes 0. However, if their combined    
HP is less than that targets current HP, the targets HP reduces by the combined   
HP of the mafias.                                                               
f) Further, as damage, each mafias HP will be reduced by X/Y, where X is the initial       
HP of the target(before being killed), and Y is the number of alive mafias whose      
HP is greater than 0. If the target’s HP falls below or equal to 0, the target dies.    
Note that mafias cannot be killed even if their HP falls to 0. They can only        
be voted out. Please note that HP can not fall below 0 for any player.       
In case, at the time of selection of target, one of the mafias has an HP less than   
X/Y, then this mafia will contribute to the damage by his total HP(making his HP     
to zero), and the remaining Damage will be equally divided. This process is       
continued until all Damage is absorbed, or the HP of all mafias becomes 0. For    
example, if there are two mafias mafia1 and mafia2 with HP 100 and 200           
respectively and they kill a player with HP 250, then the final HP of mafias after   
the kill are 0 and 50.                   
g) If the Healer chooses a person who was a target that died in that round itself, the      
target is revived, and the new HP of the target is 500. No one dies in such a           
situation. However, the mafias will still take the damage as explained earlier.      
     
3) There is only a single-player playing the game. Other players are simulated and take no
input.        
