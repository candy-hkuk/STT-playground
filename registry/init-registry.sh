#!/bin/bash

#####################################
# Create the registry users         #
#####################################
export REGISTRY_USER=admin
export REGISTRY_PASS=somePassword
export CREDS_DIR=./creds 

if [ ! -d $CREDS_DIR ]; then
    mkdir -p $CREDS_DIR 
fi

docker run --entrypoint htpasswd httpd:2 \
    -Bbn ${REGISTRY_USER} ${REGISTRY_PASS} \
    > ${CREDS_DIR}/htpasswd

unset REGISTRY_USER REGISTRY_PASS CREDS_DIR

docker run -itd \
    -p 5000:5000 \
    --name registry:2 \
    -v "$(pwd)/certs:/certs" \
    -v "$(pwd)/creds:/auth" \
    -e "REGISTRY_AUTH=htpasswd" \
    -e "REGISTRY_AUTH_HTPASSWD_REALM=Registry Realm" \
    -e REGISTRY_AUTH_HTPASSWD_PATH=/auth/htpasswd \
    registry

exit 0

#####################################
# Generate Certificates             #
#####################################
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

#################################################
# Prepare and Deploy private docker repository  #
#################################################
# Upload generated certs to k8s' secrets store
kubectl create secret tls registry-cert \
    --cert=certs/registry.crt \
    --key=certs/registry.key

# initialize registry with public access
kubectl create -f registry.yaml
