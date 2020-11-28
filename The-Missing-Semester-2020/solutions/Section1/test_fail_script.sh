 #!/usr/bin/env bash

cd "$(dirname "$0")"

error=./testError.txt
output=./testOutput.txt
file=./fail_script.sh

touch $error
touch $output
count=0;
chmod a+x $file 
while [ true ]; do 

    count=$((count+1))
    $file >> $output 2>> error || break
done
echo -e "Error happened in $count times"
echo -e $(cat $output)
echo -e $(cat $error)
rm -f $error
rm -f $output