#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

kubectl create configmap stt-api-conf \
    --from-file=/path/to/db.properties 
kubectl create secret generic stt-api-creds \
    --from-file=tokens=/path/to/creds.properties

kubectl create -f $SCRIPT_DIR/stt-api.yaml

# for localized testing
kubectl apply -f $SCRIPT_DIR/stt-api-service.yaml