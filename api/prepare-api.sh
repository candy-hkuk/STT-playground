#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

# compile project and kill script if unsuccessful
mvn install > /dev/null
if [ "$?" -ne 0 ]; then
    echo "Maven may not have been installed"
    exit 1
fi

# build container image
docker build -t test/stt-api -f $SCRIPT_DIR/Dockerfile .
docker tag test/stt-api localhost:5000/test/stt-api

# push to local repository
docker push localhost:5000/test/stt-api
