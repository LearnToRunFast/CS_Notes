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

`Git stash pop`: Same as `apply`, but it remove the top stashed element from the stash stack.

`Git branch -a` List both remote-tracking branches and local branches. 

`git reset --hard` Resets the index and working tree. Any changes to tracked files in the working tree since `<commit>` are discarded.

`git checkout .` Will discard all untracked changes but not staged files.

`git checkout HEAD <filename>` will discard changes for the file base on content on HEAD.

`git checkout -b newBranch` Create new branch called `newBranch` and checkout to that branch with current commit.

`git commit --amend` modify the most recent commit. It lets you combine staged changes with the previous commit instead of creating an entirely new commit. 

`git reset <filename>` move file from stage area to working directory, it can be apply to commit hash to. Eg. `git reset <hash value>` will bring all the changes from hash value to HEAD to working directory, if there are changes on working directory, they will merge.

`git reset --soft HEAD^` move latest commit to stage area.

`git reflog` will show all the `reset`, `checkout` logs.

`git pull <remote branch>` merge upstream with current branch.

`git rebase <remote branch>`  Similar to `git pull` but it will flatten the branch into one.

`git rebase –continue` when there is a conflict during rebase, we need to solve the conflict and add it to stage, finally run this command.

`git cat-file -p <hash_code>`: Gives the information about the specify commit.

`git log`: show the log 

`git log --all --graph --decorate --oneline` : Show the log with with indentation.

`git diff <file_name>` : show the change history

`git diff <hash> <file_name>`: Show the change history from \<hash\> until current HEAD.

`git clone --shallow <url>`: clone the project without git history.

`git add -p <file_name>` : select specify area to stage the file.

`git blame <file_name>`: check commit detail for specify file.

`git bisect`: Use to find last commit that one specify unit test passed.

### Events

#### Merge Single Commit into Mater

If you wanna merge one single commit into master.

```bash
git checkout master
git cherry-pick 62dki
```

#### Merge Consecutive Commits

```bash
git checkout -b newBranch <hashcode of last commit>
git rebase --onto master <first commit>^
```

#### Push Local Branch to New Remote Branch

```bash
git push -u origin localBranch:newRemoteBranch
```

#### Get the Remote Branch to Local

```bash
git fetch
git checkout remotebranchName
```

#### Merge Two Local Branches

```bash
git checkout branchOfMergeTo
git merge branchOfMergeFrom
```

#### Merge Current Stage Files With Last Commit

`--no-edit` Automatically commit with previous commit message.

```bash
git commit --amend --no-edit
```

#### Create a Copy of Remote Branch

```c
git branch --track <new-branch> origin/<base-branch>
```

#### Create a Branch From Commit

```c
git branch <new-branch> hashValue
```

### Remove Cache

To move cached file in order for git ignore to take effect

```
git rm --cached FILENAME
```

