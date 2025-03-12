#!/bin/bash

if [ ! -d certs ]; then
mkdir -p certs
fi

## Long Version
# # Create the private key and CSR
# openssl req \
#     -newkey rsa:4096 -nodes -keyout certs/registry.key \
#     -out certs/registry.csr

# # Create the self-signed certificate for local testing
# openssl x509 -signkey certs/registry.key \
#     -in certs/registry.csr \
#     -req -days 365 \
#     -out certs/registry.crt

## One liner version
openssl req -newkey rsa:2048 \
    -nodes \
    -keyout certs/registry.key \
    -x509 -days 365 \
    -out certs/registry.crt

# create k8s registry to bypass image pull issues
kubectl create secret tls registry-cert \
    --cert=certs/registry.crt \
    --key=certs/registry.key
kubectl create -f registry.yaml
