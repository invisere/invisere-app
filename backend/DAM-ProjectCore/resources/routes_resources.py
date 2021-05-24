import logging

import falcon
from falcon.media.validators import jsonschema
from sqlalchemy.exc import IntegrityError
from sqlalchemy.orm.exc import NoResultFound

import messages
from db.models import User, GenereEnum, RolEnum, Route, UserRoutesAssociation
from hooks import requires_auth
from resources.base_resources import DAMCoreResource
from resources.schemas import SchemaRegisterUser

mylogger = logging.getLogger(__name__)

@falcon.before(requires_auth)
class ResourceGetRoutes(DAMCoreResource):

    def on_get(self, req, resp, *args, **kwargs):
        super(ResourceGetRoutes, self).on_get(req, resp, *args, **kwargs)
        # Filters
        filter_distance = req.get_param("distance", False)
        routes = list()
        current_user = req.context["auth_user"]
        cursor = self.db_session.query(Route).filter(Route.owner_id != current_user.id)

        # Apply filter
        if filter_distance is not None:
            cursor = cursor.filter(Route.distance <= filter_distance)

        if cursor is not None:
            for route in cursor.all():
                routes.append(route.json_model)


        resp.media = routes
        resp.status = falcon.HTTP_200


@falcon.before(requires_auth)
class ResourceGetOwnRoutes(DAMCoreResource):

    def on_get(self, req, resp, *args, **kwargs):
        super(ResourceGetOwnRoutes, self).on_get(req, resp, *args, **kwargs)
        # Filters
    
        routes = list()
        current_user = req.context["auth_user"]
        cursor = self.db_session.query(Route).filter(Route.owner_id == current_user.id)

        if cursor is not None:
            for route in cursor.all():
                routes.append(route.json_model)


        resp.media = routes
        resp.status = falcon.HTTP_200


@falcon.before(requires_auth)
class ResourceAddToFavourites(DAMCoreResource):
     def on_post(self, req, resp, *args, **kwargs):
        super(ResourceAddToFavourites, self).on_post(req, resp, *args, **kwargs)

        current_user = req.context["auth_user"]
        if "id" in kwargs:
            route = self.db_session.query(Route).filter(Route.id == kwargs["id"]).one_or_none()
            if route is not None:
               # Check if association exists
                association = self.db_session.query(UserRoutesAssociation).filter(
                    UserRoutesAssociation.user_id == current_user.id,
                    UserRoutesAssociation.route_id == kwargs["id"]
                ).one_or_none()

                if association is  None:
                    aux = UserRoutesAssociation()
                    aux.route_id =  kwargs["id"]
                    aux.user_id = current_user.id
                    aux.favourite = True
                    self.db_session.add(aux)
                    self.db_session.commit()

                elif association is not None and association.favourite is None:
                    association.favourite = True
                    self.db_session.commit() 

            else:
                raise falcon.HTTPNotFound(description="Route id not found in DB.")

        resp.status = falcon.HTTP_200

@falcon.before(requires_auth)
class ResourceDeleteFromFavourites(DAMCoreResource):
     def on_post(self, req, resp, *args, **kwargs):
        super(ResourceDeleteFromFavourites, self).on_post(req, resp, *args, **kwargs)
        print("HOLA DELETE")
        current_user = req.context["auth_user"]
        if "id" in kwargs:
            route = self.db_session.query(Route).filter(Route.id == kwargs["id"]).one_or_none()
            if route is not None:
               # Check if association exists
                association = self.db_session.query(UserRoutesAssociation).filter(
                    UserRoutesAssociation.user_id == current_user.id,
                    UserRoutesAssociation.route_id == kwargs["id"]
                ).one_or_none()

                if association is not None and association.valoracio is None:
                    print(association.json_model)
                    self.db_session.delete(association)
                    self.db_session.commit()

                elif association is not None and association.valoracio is not None:
                    association.favourite = None
                    self.db_session.commit() 

            else:
                raise falcon.HTTPNotFound(description="Route id not found in DB.")

        resp.status = falcon.HTTP_200


