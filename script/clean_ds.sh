#!/bin/bash

PRO=`cd \` dirname $0 \` cd .. ; cd ..;  pwd `
# 输出当前文件目录
echo "current project path is  ->  $PRO"

for file in $PRO/*;
do
    if [ -d "$file" ]
    then
      echo "$file is directory"
      rm -rf $file/.DS_Store

    elif [ -f "$file" ]
    then
      echo "$file is file"
    fi
done
