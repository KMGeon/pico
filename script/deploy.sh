#!/bin/bash
BASE_PATH=/home/ubuntu
BUILD_PATH=$(ls $BASE_PATH/build/libs/*.jar)
BASE_APPLICATION_NAME=pico.jar


JAR_NAME=${BASE_APPLICATION_NAME}
#$(basename $BUILD_PATH)
echo "> build 파일명: $JAR_NAME"

echo "> build 파일 복사"
DEPLOY_PATH=$BASE_PATH/jar/
# Build Path -> Deploy Path로 변경
cp $BUILD_PATH $DEPLOY_PATH

echo "> 현재 구동중인 Set 확인"
CURRENT_PROFILE=$(curl -s http://localhost/profile)
echo "> $CURRENT_PROFILE"


# 쉬고 있는 set 찾기: blue 사용중이면 green 쉬고 있고, 반대면 blue 쉬고 있음
if [ $CURRENT_PROFILE == blue ]
then
  IDLE_PROFILE=green
  IDLE_PORT=8082
elif [ $CURRENT_PROFILE == green ]
then
  IDLE_PROFILE=blue
  IDLE_PORT=8081
else
  echo "> 일치하는 Profile이 없습니다. Profile: $CURRENT_PROFILE"
  echo "> set1을 할당합니다. IDLE_PROFILE: set1"
  IDLE_PROFILE=blue
  IDLE_PORT=8081
fi

echo "> application.jar 교체"
IDLE_APPLICATION=$IDLE_PROFILE-$BASE_APPLICATION_NAME
echo "> IDLE_APPLICATION 이름 : ${IDLE_APPLICATION}"

IDLE_APPLICATION_PATH=$DEPLOY_PATH$IDLE_APPLICATION
echo "> IDLE_APPLICATION_PATH 이름 : ${IDLE_APPLICATION_PATH}"


ln -Tfs $DEPLOY_PATH$JAR_NAME $IDLE_APPLICATION_PATH

echo "> $IDLE_PROFILE 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(pgrep -f $IDLE_APPLICATION)


# -z는 길이가 0인지 판단한다. 즉. PID가 비어있는지 확인한다.
if [ -z $IDLE_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -9 $IDLE_PID"
  kill -9 $IDLE_PID
  sleep 5
fi

echo "> $IDLE_PROFILE 배포"
cd /home/ubuntu/
sudo chmod 777 logs

cd /home/ubuntu/logs
sudo chmod 777 tbnws_admin_back.log

echo "> $IDLE_PROFILE 배포"
echo "> IDLE_APPLICATION_PATH : $IDLE_APPLICATION_PATH"


nohup java -jar \
-Dspring.config.location=classpath:/application.properties,classpath:/application-database.properties,classpath:/application-env-prod.properties \
-Dspring.profiles.active=$IDLE_PROFILE \
$IDLE_APPLICATION_PATH >> /home/ubuntu/logs/tbnws_admin_back.log 2>&1 &

echo "> $IDLE_PROFILE 10초 후 Health check 시작"
echo "> curl -s http://localhost:$IDLE_PORT/actuator/health "
sleep 10

for retry_count in {1..10}
do
  response=$(curl -s http://localhost:$IDLE_PORT/actuator/health)
  up_count=$(echo $response | grep 'UP' | wc -l)

  if [ $up_count -ge 1 ]
  then # $up_count >= 1 ("UP" 문자열이 있는지 검증)
      echo "> Health check 성공"
      break
  else
      echo "> Health check의 응답을 알 수 없거나 혹은 status가 UP이 아닙니다."
      echo "> Health check: ${response}"
  fi

  if [ $retry_count -eq 10 ]
  then
    echo "> Health check 실패. "
    echo "> Nginx에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done