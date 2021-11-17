#!/bin/bash

#get arguments from CLI
cmd=$1
db_username=$2
db_password=$3

#start docker if not already running
sudo systemctl status docker || systemctl start docker

#check if container is running
docker container inspect jrvs-psql
container_status=$?

#switch case depending on create/stop/start option selected
case $cmd in
  create)

  #if container is running, it already exists
  if [ $container_status -eq 0 ]; then
    echo 'Container already exists'
    exit 1
  fi

  #3 CLI arguments are required
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi

  #create PSQL container using PSQL image
  #create volume (to preserve data for the container)
  echo "Creating container"
  docker volume create pgdata
  docker run --name jrvs-psql -e POSTGRES_USER="$db_username" -e POSTGRES_PASSWORD="$db_password" -d -v PGDATA=/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
  exit $?
  ;;

  start|stop)
  #check container status
  if [ $container_status -eq 1 ]; then
    echo 'Container is not created'
    exit 1
  fi

  #start/stop the container
  docker container "$cmd" jrvs-psql
  exit $?
  ;;

  *)
  echo 'Illegal command'
  echo 'Commands: start|stop|create'
  exit 1
  ;;
esac

