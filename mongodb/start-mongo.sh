#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

echo $SCRIPT_DIR

# initialize db persistent storage
kubectl apply -f $SCRIPT_DIR/mongo-pv.yaml

# initialize db persistence claim
kubectl apply -f $SCRIPT_DIR/mongo-pv-claim.yaml

# compile customized mongodb configurations
docker build . -t test/mongodb -f $SCRIPT_DIR/Dockerfile 

# start Mongo pod