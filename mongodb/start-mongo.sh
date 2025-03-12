#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

# initialize db persistent storage
kubectl apply -f $SCRIPT_DIR/mongo-pv.yaml

# initialize db persistence claim
kubectl apply -f $SCRIPT_DIR/mongo-pv-claim.yaml

# start Mongo pod
kubectl apply -f $SCRIPT_DIR/mongodb.yaml