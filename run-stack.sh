#!/bin/bash

# create k8s registry to bypass image pull issues
mkdir -p certs
openssl req \
    -newkey rsa:4096 -nodes -sha256 -keyout certs/registry.key \
    -x509 -days 3650 -out certs/registry.crt
kubectl create secret tls registry-cert \
    --cert=certs/registry.crt \
    --key=certs/registry.key
kubectl create -f registry.yaml

# initialize mongodb
#./mongodb/start-mongo.sh
