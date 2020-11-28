# marco
#!/usr/bin/env zsh 

filePath=$(dirname $0)
outputPath="$filePath/macro_out.txt 
echo "$outputPath"
PWD > "$outputPath"