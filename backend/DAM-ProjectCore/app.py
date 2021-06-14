#!/usr/bin/python
# -*- coding: utf-8 -*-

import logging.config

import falcon

import messages
import middlewares
from falcon_multipart.middleware import MultipartMiddleware
from resources import account_resources, common_resources, user_resources, routes_resources, places_resources
from settings import configure_logging

# LOGGING
mylogger = logging.getLogger(__name__)
configure_logging()


# DEFAULT 404
# noinspection PyUnusedLocal
def handle_404(req, resp):
    resp.media = messages.resource_not_found
    resp.status = falcon.HTTP_404


# FALCON
app = application = falcon.API(
    middleware=[
        middlewares.DBSessionManager(),
        middlewares.Falconi18n(),
        MultipartMiddleware()
    ]
)
application.add_route("/", common_resources.ResourceHome())

application.add_route("/account/profile", account_resources.ResourceAccountUserProfile())
application.add_route("/account/profile/update_profile_image", account_resources.ResourceAccountUpdateProfileImage())
application.add_route("/account/create_token", account_resources.ResourceCreateUserToken())
application.add_route("/account/delete_token", account_resources.ResourceDeleteUserToken())
application.add_route("/account/delete", account_resources.ResourceAccountDelete())
application.add_route("/account/update", account_resources.ResourceAccountUpdate())
application.add_route("/account/recovery", account_resources.ResourceAccountRecovery())
application.add_route("/account/password_update", account_resources.ResourceAccountPasswordUpdate())

application.add_route("/users/register", user_resources.ResourceRegisterUser())
application.add_route("/users/show/{username}", user_resources.ResourceGetUserProfile())

application.add_route("/routes", routes_resources.ResourceGetRoutes())
application.add_route("/routes/own", routes_resources.ResourceGetOwnRoutes())
application.add_route("/routes/favorites", routes_resources.ResourceGetFavourites())
application.add_route("/routes/favorites/add/{id:int}", routes_resources.ResourceAddToFavourites())
application.add_route("/routes/favorites/delete/{id:int}", routes_resources.ResourceDeleteFromFavourites())

application.add_route("/places", places_resources.ResourceGetPlaces())

application.add_sink(handle_404, "")
