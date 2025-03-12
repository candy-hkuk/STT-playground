# Localized Implementation of MongoDB

For simplicity, an unauthenticated implementation of the Community Edition of MongoDB was implemented. Containerization and persistence was also implemented to simulate real-life use case, where the close proximity of persistence to applications, as well as the potential for security configurations (as in IaC set-up), would greatly enhance the data turnaround.

## To Build
```bash
./prepare-mongo.sh
```

## To Run on kubernetes
```bash
./start-mongo.sh
```