# ics4u-cct-hearts
ICS4U CCT Game: Hearts  
Developed by: Haruki and Pouria  
Date: 2021 November 25th

## Git Cheatsheet

__To Start up a repo__
* If repo exists on githhub: `git clone HTTPS`
* If repo exists locally: create a repo on github with the folder name and `git remote add origin HTTPS`

__To work on a feature, do:__ 
* `git pull origin master` or `git pull` to sync your local master with remote master
* `git branch branch-name` to create a new local branch
* Do `git checkout branch-name` before modifying files (important!).
  * We don't do pull request for this project, but just for reference: Do `git push -u origin branch-name` to push your branch to remote repo

__While working on a feature, do:__
* `git add .` to stage changes
  * `git reset` to unstage
* `git commit -m "message"` to commit changes to your local branch
  * `git reset HEAD~1` to revert the last commit and unstage the related changes
* `git commit -am "message"` to stage and commit changes to your local branch (doesn't work for new files)
* `git status` for checking what files has been modified, staged, unstaged etc
* `git branch` to check which branch you are working on
* `git log` to check the commit logs
  * reset: goes back to the specified commit and removes all the commits that come after it from the log
    (Best to use when the commits are not made public yet because it can cause discrepency in the log)
    * `git reset commit-hash` to go back to a previous commit in the log but keeping the changes unstaged
    * `git reset --hard commit-hash` to go back to a previous commit in the log and also removing all the changes
      * `git push - f origin master` to force a push when it says "remote branch is ahead" 
  * revert: makes a commit for undoing the changes of the specified commit, but doesn't remove it from the log
    (Best practice when changes are made public but it doesn't prevent your mistake from showing up in the log)
    * `git revert --no-edit commit-hash` to make a commit for undoing the changes with a default message
    * `git revert commit-hash` to make a commit for undoing the changes with a custom message (opens vim editor)
* `git diff` for checking the difference between previous commit and the current state

__To merge your branch to your local master and push to remote repo, do:__
* `git checkout master`
* `git pull origin master` (or just `git pull`) (make sure you're in master to sync with GitHub before merging)
* `git merge branch-name`
  * `git branch --merged` (to check what branch has been merged)
* `git push origin master` (or just `git push` to push changes to remote repo)

__To delete a branch locally or remotely, do:__
* `git branch --merged` (to again check your branch has been properly merged)
* `git branch -d branch-name` (to delete local branch) (be careful)
* `git branch -a` (just check what branches there are locally and remotely)
  * `git push origin --delete branch-name` (to delete remote branch) (no need, unless you pushed a branch to remote repo)

## Game of Hearts
### Terminology
Term | Meaning
---------- | ----------
Hand | The time it takes to play all the cards that each player holds. Hand also refers to the cards the player holds. 
Trick | One round of play where each each player plays one card and make up a trick.
Game | Each game begins by dealing cards to each player and ends whenever someone reaches 100 or customized points. 
'Heart is broken' | A heart is broken when someone discards a Heart when another suit is led because s/he does not have cards of the suits led. Once Heart is broken, players can lead with a Heart or Spade of Queen (playing Spade of Queen does not break Heart). However, if the player to lead only has Hearts and the Heart has not been broken, then the lead is passed clockwise.
'Shot the moon' | It is when a player takes all 13 Hearts and a Spade of Queen in one hand. That player scores 0 and every other players scores 26 points.

### Rules
#### Objective Of The Game
The objective of the game is to get as little points as possible, as the player with the lowest score wins the game.
The game ends when one player hits the predetermined score or higher (The default score is 50).
The game can be played by 3 to 5 players.

#### Set Up
A standard deck of card is shuffled and dealt to players.  
In a 4-player game, each player is dealt 13 cards.  
In a 3-player game, each player is dealt 17 cards from a deck of card without 2 of Diamond.   
In a 5-player game, each player is dealth 10 cards from a deck of card without 2 of Diamond and 2 of Club.   

#### Passing Rotation
After looking at your hand, each player picks three cards from their hand, and passes them to another player.  
In a 4-player game, pass the cards to the person on the left on the 1st hand, right on the 2nd hand, across the player on 3rd hand, and no passing on 4th hand. This cycle continues.  
In a 3-player or 5-player game, pass the cards to the person on the left on 1st hand, then right on 2nd hand, and this cycle continues. 

#### Game Procedure
1. The player who has the 2 of Club after the deal makes the opening lead. If 2 of Club has been removed, it is the 3 of Club that makes the opening lead instead.
2. The play proceeds in the ascending order (by player ID) from the led player. 
3. Each player MUST follow suit if possible. If a player's hand does not contain the suit led, the card of any other suit may be played.
    1. However, if a player has no Clubs when the first trick is led, a Heart or the Queen of Spade may not be played.
4. The highest card of the suit led wins the trick, and the winner of that trick takes all the cards from that trick. 
5. The winner of the last trick leads the next trick.
    1. A player may not lead a Heart or Queen of Spade until "Heart has been broken"
    2. If the player to lead only has Hearts and Heart has not been broken, then they pass the lead to the next person.
6. The play continues until all the tricks have been taken, then points are scored for each player based on the cards from each of the tricks they have won.
7. If no player exceeds the maximum amount of points, then the cards are shuffled and dealt again.
8. The player with lowest score wins the game once someone exceeds the maximum amount of points.

#### Point System
Suit | Point
----- | -----
Heart | 1 point
Queen of Spade | 13 points
Spade | 0
Club | 0
Diamond | 0