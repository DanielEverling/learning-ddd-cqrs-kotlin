#!/bin/bash

# Exit immediately if a command exits with a non-zero status.
set -e

host="$1"
databases="$2" ## the database name
username="$3" ## the username in db
pgpass="$4" ## the password in db

>&2 echo "ARGS"
>&2 echo "$host"
>&2 echo "$databases"
>&2 echo "$username"
>&2 echo "$pgpass"

shift

until PGPASSWORD=$pgpass psql -h "$host" -U "$username" -c '\l'; do
  >&2 echo "Postgres is unavailable - sleeping"
  sleep 1
done

>&2 echo "Postgres is up - starting the Flyway"

# migrate the database
>&2 echo "Flyway is now running pending migrations -if any- on the database"

for database in $(echo $databases | tr "," "\n"); do
  flyway -url=jdbc:postgresql://"$host":5432/"$database" -user="$username" -password="$pgpass" migrate
done

