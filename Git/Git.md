# Git

Git has three stages locally, they are `working directory`, `stage/index area` and `commit history`.

![https://rogerdudler.github.io/git-guide](Asserts/Git/trees.png)

`working directory` contains all the untracked files.

`stage area` By executing `git add <file names>`, you move the files with given filenames from `working directory` to `stage area`.

`commit history` When there are exist staged files, we execute `git commit ` to save the changes into `commit history`.

With `commit history`, we can execute `git push <upstream>` to push the changes to remote branch.

## Common Commands

`Git status` Show files tracking information

`Git stash` Put all the untracked files to a temporary area which is useful when you dont want to stage your files and you want to merge code from other branch to your branch.

`Git stash apply` After you merge the upstream code into your current branch, you can run this command to put back the untracked files. 

`Git branch -a` List both remote-tracking branches and local branches. 

`git reset --hard` Resets the index and working tree. Any changes to tracked files in the working tree since `<commit>` are discarded.

`git checkout .` Will discard all untracked changes but not staged files.

`git checkout HEAD <filename>` will discard changes for the file base on content on HEAD.

`git checkout -b newBranch` Create new branch called `newBranch` and checkout to that branch with current commit.

`git commit --amend` modify the most recent commit. It lets you combine staged changes with the previous commit instead of creating an entirely new commit. 

`git reset <filename>` move file from stage area to working directory, it can be apply to commit hash to. Eg. `git reset <hash value>` will bring all the changes from hash value to HEAD to working directory, if there are changes on working directory, they will merge.

`git reflog` will show all the `reset`, `checkout` logs.

`git pull <remote branch>` merge upstream with current branch.

`git rebase <remote branch>`  Similar to `git pull` but it will flatten the branch into one.

`git rebase –continue` when there is a conflict during rebase, we need to solve the conflict and add it to stage, finally run this command.

