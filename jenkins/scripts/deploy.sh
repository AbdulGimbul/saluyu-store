#!/bin/bash
if pgrep -f saluyustore-service.jar; then
    pkill -f saluyustore-service.jar
fi
cd app
java -jar saluyustore-service.jar &
