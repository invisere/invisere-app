#!/usr/bin/python
# -*- coding: utf-8 -*-

import datetime
import logging
import os

from sqlalchemy.sql import text

import db
import settings
from db.models import SQLAlchemyBase, User, GenereEnum, RolEnum, UserToken, Place, Route, Place, UserRoutesAssociation
from settings import DEFAULT_LANGUAGE

import random
import string

# LOGGING
mylogger = logging.getLogger(__name__)
settings.configure_logging()

def randomString(length):
    password = ''.join([random.choice( string.ascii_letters + string.digits) for n in range(length)])
    return password

def execute_sql_file(sql_file):
    sql_folder_path = os.path.join(os.path.dirname(__file__), "sql")
    sql_file_path = open(os.path.join(sql_folder_path, sql_file), encoding="utf-8")
    sql_command = text(sql_file_path.read())
    db_session.execute(sql_command)
    db_session.commit()
    sql_file_path.close()


if __name__ == "__main__":
    settings.configure_logging()

    db_session = db.create_db_session()

    # -------------------- REMOVE AND CREATE TABLES --------------------
    mylogger.info("Removing database...")
    SQLAlchemyBase.metadata.drop_all(db.DB_ENGINE)
    mylogger.info("Creating database...")
    SQLAlchemyBase.metadata.create_all(db.DB_ENGINE)



    # -------------------- CREATE USERS --------------------
    mylogger.info("Creating default users...")
    # noinspection PyArgumentList
    user_admin = User(
        created_at=datetime.datetime(2020, 1, 1, 0, 1, 1),
        name="Administrator",
        username="admin",
        email="admin@damcore.com",
    )
    user_admin.set_password("DAMCore")

    db_session.add(user_admin)

    users = []

    for i in range(1, 20):
        aux_user = User(
            created_at=datetime.datetime.now(),
            name="user",
            username="user"+str(i),
            email="user"+str(i)+"@gmail.com",
        )
        aux_user.set_password(randomString(random.randint(4,8)))
        aux_user.tokens.append(UserToken(token=randomString(50)))

        users.append(aux_user)
        
    for user in users:
        db_session.add(user)

      # -------------------- CREATE Places --------------------

    mylogger.info("Creating default places...")

    listPlaces = []

    for i in range(1, 20):
        aux_place = Place(
            id = i,
            name="place"+str(i),
            latitude = round(random.uniform(41.60990,  41.47613), 5),
            longitude = round(random.uniform(1.36508,  1.80063), 5),
            photo = "",
            description = ""
        )

        listPlaces.append(aux_place)
        db_session.add(aux_place)


    # -------------------- CREATE ROUTES --------------------

    mylogger.info("Creating default routes...")
    listRoutes = []

    for i in range(1, 20):

        aux_route = Route(
            id=i,
            name = "route"+str(i),
            distance = i,
            owner_id = random.randint(1,20),
            points = random.sample(listPlaces, random.randint(3,6))
        )

        listRoutes.append(aux_route)

        db_session.add(aux_route)

    # -------------------- CREATE UserRouteAssociation --------------------

    mylogger.info("Creating default user-route-association...")

    for i in range(1, 10): 
        aux = UserRoutesAssociation(
                favourite=bool(random.getrandbits(1)),
                valoracio=random.randint(1,5),
                route_id=random.randint(1,19),
                user_id=random.randint(1,19),
            )

        try:
            db_session.add(aux)

        except:
            print(aux.json_model)



    db_session.commit()
    db_session.close()


