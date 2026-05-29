#!/bin/sh
set -e

mkdir -p /data
chown -R cupon:cupon /data

exec su-exec cupon java -jar /app/app.jar
