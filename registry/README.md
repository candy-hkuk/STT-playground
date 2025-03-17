# Cluster Localized docker repository

In the event that developers do not wish to use Docker Hub to manage their container images for whatever reason, you may wish to use a k8s-localized repository.

Please review the convenience script `init-registry.sh` for set-up details

## Reference
* [Docker Registry + Helm](https://www.paulsblog.dev/how-to-install-a-private-docker-container-registry-in-kubernetes/)
* [Install Helm](https://phoenixnap.com/kb/install-helm)
* [Self-signed Certificate Example](https://www.baeldung.com/openssl-self-signed-cert)
* [Image management to k8s docker registry](https://forums.docker.com/t/push-to-docker-registry-inside-kubernetes-doesnt-even-attempt-conection/110046)