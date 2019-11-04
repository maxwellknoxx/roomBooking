#!/bin/bash
set -e

POSTGRES="psql --username postgres"

echo "Creating database: roomBooking"

$POSTGRES <<EOSQL
CREATE DATABASE roomBooking OWNER postgres;
EOSQL

echo "Creating schema..."
psql -d roomBooking -a -U postgres -f /schema.sql

echo "Populating database..."
psql -d roomBooking -a  -U postgres -f /data.sql