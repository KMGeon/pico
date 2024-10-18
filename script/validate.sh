#!/bin/bash

echo ">Health 확인"
CURRENT_PROFILE=$(curl -s http://localhost/profile)
echo "> $CURRENT_PROFILE"

if [ "$CURRENT_PROFILE" == "blue" ]
then
  IDLE_PROFILE="green"
  IDLE_PORT=8082
elif [ "$CURRENT_PROFILE" == "green" ]
then
  IDLE_PROFILE="blue"
  IDLE_PORT=8081
else
  echo "> 일치하는 Profile이 없습니다. Profile: $CURRENT_PROFILE"
  echo "> blue을 할당합니다. IDLE_PROFILE: blue"
  IDLE_PROFILE="blue"
  IDLE_PORT=8081
fi

echo "> $IDLE_PORT"
echo "> $IDLE_PROFILE"

