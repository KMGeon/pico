#!/bin/bash

current_datetime=$(date +"%Y%m%d")
# 원본 로그 파일 이름 (전체 경로 포함)
original_log="/home/ubuntu/logs/pico.log"
# 새로운 로그 파일 이름 (날짜와 시간 포함)
new_log_name="${current_datetime}_$(basename "$original_log")"
# archived 폴더 경로
archive_dir="/home/ubuntu/logs/archived"

# archived 폴더가 없으면 생성합니다
if [ ! -d "$archive_dir" ]; then
    mkdir -p "$archive_dir"
fi

# 로그 파일을 archived 폴더로 복사합니다
cp "$original_log" "${archive_dir}/${new_log_name}"

# 복사가 성공했는지 확인합니다
if [ $? -eq 0 ]; then
    echo "로그 파일이 성공적으로 아카이브되었습니다: ${archive_dir}/${new_log_name}"
    # 원본 로그 파일을 비웁니다 (선택사항)
    > "$original_log"
else
    echo "로그 파일 아카이브 중 오류가 발생했습니다."
fi

# 날짜 차이를 계산하는 함수
calculate_date_difference() {
    local date1=$1
    local date2=$2
    local diff=$(( ($(date -d "$date2" +%s) - $(date -d "$date1" +%s)) / 86400 ))
    echo "$diff"
}

if [ "$(ls -A "$archive_dir")" ]; then
    for item in "$archive_dir"/*; do
        filename=$(basename "$item")
        echo "파일명: $filename"

        # 파일명에서 날짜 추출 (YYYYMMDD 형식 가정)
        file_date=$(echo "$filename" | grep -oE '^[0-9]{8}')

        if [ -n "$file_date" ]; then
            echo "추출된 날짜: $file_date"
            echo "현재 날짜 : $current_datetime"

            date_diff=$(calculate_date_difference "$file_date" "$current_datetime")

            if [ "$file_date" = "$current_datetime" ]; then
                echo "결과: 현재 날짜와 같습니다."
            else
                echo "결과: 현재 날짜와 다릅니다."
                echo "날짜 차이: $date_diff 일"

                # 14일이 넘은 파일 삭제
                if [ $date_diff -gt 14 ]; then
                    echo "파일이 14일 이상 지났습니다. 삭제합니다."
                    rm "$item"
                    if [ $? -eq 0 ]; then
                        echo "파일이 성공적으로 삭제되었습니다."
                    else
                        echo "파일 삭제 중 오류가 발생했습니다."
                    fi
                fi
            fi
        else
            echo "날짜를 찾을 수 없습니다."
        fi
        echo "------------------------"
    done
else
    echo "오류가 발생했습니다: 디렉토리가 비어있거나 접근할 수 없습니다."
fi