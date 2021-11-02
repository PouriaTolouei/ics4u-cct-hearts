# ics4u-cct-hearts
ICS4U CCT Game: Hearts
Developed by: Haruki and Pouria
Date: 2021 November 25th

__To work on a feature, do:__ 
* `git branch branch-name`
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
* `git pull origin master` (or just git pull) (make sure you're in master to sync with GitHub before merging)
* `git merge branch-name`
  * `git branch --merged` (to check what branch has been merged)
* `git push origin master` (or just git push to push changes to remote repo)

__To delete a branch locally or remotely, do:__
* `git branch --merged` (to again check your branch has been properly merged)
* `git branch -d branch-name` (to delete local branch) (be careful)
* `git branch -a` (just check what branches there are locally and remotely)
  * `git push origin --delete branch-name` (to delete remote branch) (no need, unless you pushed a branch to remote repo)
