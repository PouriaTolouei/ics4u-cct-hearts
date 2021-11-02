# ics4u-cct-hearts
ICS4U CCT Game: Hearts

__To work on a feature, use:__ 
* `git branch branch-name`
* Do `git checkout branch-name` before modifying files (important!).
  * We don't do pull request for this project, but just for reference: Do `git push -u origin branch-name` to push your branch to remote repo

__To merge your branch to your local master and push to remote repo, you:__
* `git checkout master`
* `git pull origin master` (or just git pull) (make sure you're in master to sync with GitHub before merging)
* `git merge branch-name`
  * `git branch --merged` (to check what branch has been merged)
* `git push origin master` (or just git push to push changes to remote repo)

__To delete a branch locally or remotely, you will:__
* `git branch --merged` (to again check your branch has been properly merged)
* `git branch -d branch-name` (to delete local branch) (be careful)
* `git branch -a` (just check what branches there are locally and remotely)
  * `git push origin --delete branch-name` (to delete remote branch) (no need, unless you pushed a branch to remote repo)

  git test 
  test 2
