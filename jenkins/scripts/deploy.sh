#!/bin/bash
if pgrep -f saluyustore-0.0.1.jar; then
    pkill -f saluyustore-0.0.1.jar
fi
java -jar saluyustore-0.0.1.jar &
