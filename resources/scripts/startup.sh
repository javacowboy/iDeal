#!/bin/bash

DISPLAY=:0
JAVA_HOME=/home/matthew/programs/java/jdk1.6.0_21/
JAR_DIR=~/programs/eyedeal/

export DISPLAY=$DISPLAY

#find eyedeal already running
#pid, prog name matching "eyedeal".  ignore the "grep" for eyedeal pid.  ignore myself(.sh) or parent (cron)
running=`ps -e -o pid,command | grep eyedeal | grep -v grep | grep -v $$ | grep -v $PPID`

if [ -z $running ]; then
	echo eyedeal not running already
else
	#kill the pid and children (.sh, java, and firefox)
	echo $running > get_it
	get_pid=`gawk -F" " '{ print $1 }' get_it`
	echo killing $get_pid
	kill -TERM -$get_pid
fi 

#run eyedeal
echo starting parent $PPID and self $$
cd $JAR_DIR
mkdir -p $JAR_DIR/logs/
$JAVA_HOME/bin/java -jar Eye-Deal-1.0.jar > $JAR_DIR/logs/eyedeal.log 2>&1
