#syntax=docker/dockerfile:1.2

FROM alpine
RUN --mount=type=secret,id=dockerhub_username \
  cat /run/secrets/dockerhub_username
