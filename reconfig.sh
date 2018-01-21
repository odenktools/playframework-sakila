#!/bin/bash

PLAY_CONF_FILE="conf/application.conf"
sed -i "s;^session.cookieName *=.*;session.cookieName=\""${PLAY_ODK_COOKIENAME}"\";g" "${PLAY_CONF_FILE}"
sed -i "s;^db.default.url *=.*;db.default.url=\""${PLAY_ODK_DB_URL}"\";g" "${PLAY_CONF_FILE}"
sed -i "s;^db.default.username *=.*;db.default.username=\""${PLAY_ODK_DB_USERNAME}"\";g" "${PLAY_CONF_FILE}"
sed -i "s;^db.default.password *=.*;db.default.password=\""${PLAY_ODK_DB_PASSWORD}"\";g" "${PLAY_CONF_FILE}"
