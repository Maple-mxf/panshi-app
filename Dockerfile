FROM openjdk:8-jdk-alpine

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

RUN apk add tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo Asia/Shanghai > /etc/timezone && \
    sed -i 's/dl-cdn.alpinelinux.org/mirrors.tencent.com/g' /etc/apk/repositories \
    && apk add --update --no-cache openjdk8-jre-base \
    && rm -f /var/cache/apk/*

CMD ["sleep","1h"]
