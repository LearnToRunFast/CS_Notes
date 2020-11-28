#polo
#!/usr/bin/env zsh

filePath=$(dirname "$0")
file="$filePath/macro_out.txt"
echo "current path is $PWD"
cd $(cat $file) 
echo "path after cd is $PWD"
rm -f $file