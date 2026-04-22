#!/bin/bash

DATE=`command -v date`
CUT=`command -v cut`
SHUF=`command -v shuf`
OD=`command -v od`
NC=`command -v nc`
GREP=`command -v grep`

PORT=9000
PATH="/metrics"
#METRIC_NAME="random_number"
METRIC_NAME="request_processing_seconds_sum"
METRIC_HELP="Random number sequences, generated using various methods (check the labels)"
METRIC_TYPE="gauge"

# Bash/Nc-based Web server
# See: https://funprojects.blog/2021/04/11/a-web-server-in-1-line-of-bash/
echo Starting server on port ${PORT}
while true;
#for i in {1..5}
do
  REQUEST_PATH=$( {
    echo "HTTP/1.1 200 OK"
    echo "Content-Type: text/plain; version=0.0.4" 
    echo -e ""
    echo "# HELP ${METRIC_NAME} ${METRIC_HELP}"
    echo "# TYPE ${METRIC_NAME} ${METRIC_TYPE}"
    TIMESTAMP=`$DATE +%s%N | $CUT -b1-13`;
    RND1=`$SHUF -i 0-255 -n 1`;
    RND2=`$OD -A n -t d -N 1 /dev/urandom`;
    RND2="${RND2#"${RND2%%[![:space:]]*}"}"
    RND2="${RND2%"${RND2##*[![:space:]]}"}"
    echo "${METRIC_NAME}{method=\"shuf\"} ${RND1} ${TIMESTAMP}"
    echo "${METRIC_NAME}{method=\"urandom\"} ${RND2} ${TIMESTAMP}"
  }  \
  | $NC -l -k -p ${PORT} -q 1  \
  | $GREP "GET /" ; )
  echo $REQUEST_PATH
#  if [[ $REQUEST_PATH =~ ^GET\ /exit\ .* ]]; then
#    break
#  fi
done
echo Server exited.
