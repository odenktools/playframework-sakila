#!/bin/bash

SCRIPT_PATH=$(dirname "$0")
CONF_PATH=$(cd $SCRIPT_PATH/conf && pwd)

# Check if artifacts/data.sql is exist.
if [ -e $CONF_PATH/data.sql ]
then
  # Extract and import the database.
  pv $CONF_PATH/data.sql | mysqld -u 'ubuntu' --password='ubuntu' test_play
else
  echo "Cannot import database, $CONF_PATH/data.sql not found!"
  exit 1
fi