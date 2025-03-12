#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

# compile customized mongodb configurations
docker build . -t test/mongodb -f $SCRIPT_DIR/Dockerfile 
docker tag test/mongodb localhost:5000/test/mongodb

# Push to local repository
docker push localhost:5000/test/mongodb