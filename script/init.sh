#!/bin/bash
# sh -x init.sh 可以逐步查看脚本的输出结果

PRO=`cd \` dirname $0 \` cd .. ;  pwd `
# 输出当前文件目录
echo "current project path is  ->  $PRO"
# 输出当前时间
CUR_DATE=$(date +%Y-%m-%d-%H-%H-%M-%S)
CUR_DATE=`date +%Y%m%d%H%H%M%S`
echo "current datetime is ->  $CUR_DATE"

# 循环变量
TMP_VAL="23 44 45 23 45 12"

for node in $TMP_VAL
do
   echo "node is ::  $node"
done

# 获取进程号
process_list=`netstat -ntlp | awk '{if ($7 !~ /-/) print $7}' | awk -F '/' ' NR>2 {print $1}'`

for pd in $process_list
do
  echo "process is $pd"
done

# 删除前两行
sed -i '1,2d' filename

sed -i '$d' filename
