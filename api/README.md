# STT Playground API server

This project is written in Java 21, using Jetty as the embedded server. There are two apis available:
* `/api/audio[/<id>]` - where http `POST`, `GET`, `PUT` and `DELETE` methods have all been implemented
* `/api/transcribe` - where audio files submitted via `POST` as part of the `multipart/form-data` Content-Type will be transcribed and will both return via a http-servlet response and persisted into mongoDB 

## To build
```bash
./prepare-api.sh
```

## To deploy
First update the script with the location of the `creds.properties` and `db.properties` file
```bash
./start-api.sh
```

## References
* [github.io/elidaniel92/jetty-12-rest-api-sample](https://github.com/elidaniel92/jetty-12-rest-api-sample)

## Testing Samples
* STT recording sample for local testing: [Kaggle/datasets/pavanelisetty](https://www.kaggle.com/datasets/pavanelisetty/sample-audio-files-for-speech-recognition?resource=download)