FROM amazoncorretto:21-alpine
ARG VERSION="1.0-SNAPSHOT"
ENV VERSION=${VERSION}

COPY ./target/stt-api-${VERSION}.jar .
COPY ./run.sh .

EXPOSE 8080

CMD ["ash", "./run.sh"]