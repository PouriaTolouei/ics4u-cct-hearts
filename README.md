# ics4u-cct-hearts
ICS4U CCT Game: Hearts  
Developed by: Haruki and Pouria  
Date: 2021 November 25th

## Git Cheatsheet
__To work on a feature, do:__ 
* `git pull origin master` or `git pull` to sync your local master with remote master
* `git branch branch-name` to create a new local branch
* Do `git checkout branch-name` before modifying files (important!).
  * We don't do pull request for this project, but just for reference: Do `git push -u origin branch-name` to push your branch to remote repo

__While working on a feature, do:__
* `git add .` to stage changes
  * `git reset` to unstage
* `git commit -m "message"` to commit changes to your local branch
* `git status` for checking what files has been modified, staged, unstaged etc
* `git branch` to check which branch you are working on
* `git log` to check the commit logs
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
Hand | The time it takes to play all the cards that each player holds.
Trick | One round of play where each each player plays one card and make up a trick.
Game | Each game begins by dealing cards to each player and ends whenever someone reaches 100 or customized points. 
'Heart is broken' | A heart is broken when someone discards a Heart when another suit is led because s/he does not have cards of the suits led. Once Heart is broken, players can lead with a Heart or Spade of Queen (playing Spade of Queen does not break Heart). However, if the player to lead only has Hearts and the Heart has not been broken, then the lead is passed clockwise.
'Shot the moon' | It is when a player takes all 13 Hearts and a Spade of Queen in one hand. That player scores 0 and every other players scores 26 points.
