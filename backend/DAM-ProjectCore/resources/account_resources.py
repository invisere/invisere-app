#!/usr/bin/python
# -*- coding: utf-8 -*-

import base64
import datetime
import logging
import os

import falcon
from falcon.media.validators import jsonschema

import messages
import settings
from db.models import User, UserToken, GenereEnum, RolEnum
from hooks import requires_auth
from resources import utils
from resources.base_resources import DAMCoreResource
from resources.schemas import SchemaUserToken, SchemaUpdateUser
from settings import STATIC_DIRECTORY

import random
import string
import smtplib
from email.mime.multipart import MIMEMultipart
from sqlalchemy.orm.exc import NoResultFound
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.image import MIMEImage
from email.mime.base import MIMEBase
from email import encoders
from jinja2 import Environment 

mylogger = logging.getLogger(__name__)


class ResourceCreateUserToken(DAMCoreResource):
    def on_post(self, req, resp, *args, **kwargs):
        super(ResourceCreateUserToken, self).on_post(req, resp, *args, **kwargs)

        basic_auth_raw = req.get_header("Authorization")
        if basic_auth_raw is not None:
            basic_auth = basic_auth_raw.split()[1]
            auth_username, auth_password = (base64.b64decode(basic_auth).decode("utf-8").split(":"))
            if (auth_username is None) or (auth_password is None) or (auth_username == "") or (auth_password == ""):
                raise falcon.HTTPUnauthorized(description=messages.username_and_password_required)
        else:
            raise falcon.HTTPUnauthorized(description=messages.authorization_header_required)

        current_user = self.db_session.query(User).filter(User.email == auth_username).one_or_none()
        if current_user is None:
            current_user = self.db_session.query(User).filter(User.username == auth_username).one_or_none()

        if (current_user is not None) and (current_user.check_password(auth_password)):
            current_token = current_user.create_token()
            try:
                self.db_session.commit()
                resp.media = {"token": current_token.token}
                resp.status = falcon.HTTP_200
            except Exception as e:
                mylogger.critical("{}:{}".format(messages.error_saving_user_token, e))
                self.db_session.rollback()
                raise falcon.HTTPInternalServerError()
        else:
            raise falcon.HTTPUnauthorized(description=messages.user_not_found)


@falcon.before(requires_auth)
class ResourceDeleteUserToken(DAMCoreResource):
    @jsonschema.validate(SchemaUserToken)
    def on_post(self, req, resp, *args, **kwargs):
        super(ResourceDeleteUserToken, self).on_post(req, resp, *args, **kwargs)

        current_user = req.context["auth_user"]
        selected_token_string = req.media["token"]
        selected_token = self.db_session.query(UserToken).filter(UserToken.token == selected_token_string).one_or_none()

        if selected_token is not None:
            if selected_token.user.id == current_user.id:
                try:
                    self.db_session.delete(selected_token)
                    self.db_session.commit()

                    resp.status = falcon.HTTP_200
                except Exception as e:
                    mylogger.critical("{}:{}".format(messages.error_removing_user_token, e))
                    raise falcon.HTTPInternalServerError()
            else:
                raise falcon.HTTPUnauthorized(description=messages.token_doesnt_belongs_current_user)
        else:
            raise falcon.HTTPUnauthorized(description=messages.token_not_found)


@falcon.before(requires_auth)
class ResourceAccountUserProfile(DAMCoreResource):
    def on_get(self, req, resp, *args, **kwargs):
        super(ResourceAccountUserProfile, self).on_get(req, resp, *args, **kwargs)

        current_user = req.context["auth_user"]

        resp.media = current_user.json_model
        resp.status = falcon.HTTP_200

class ResourceAccountRecovery(DAMCoreResource):
    def on_post(self, req, resp, *args, **kwargs):
        super().on_post(req, resp, *args, **kwargs)

        email = req.media["email"]
        code = ''.join(random.choices(string.ascii_uppercase + string.digits, k=6))
        
        try:
            aux_user = self.db_session.query(User).filter(User.email == email).one()
            aux_user.recovery_code = code
            self.db_session.add(aux_user)
            self.db_session.commit()

            # Sending mail
            smtp_server = "smtp.gmail.com"
            sender_email = "invisere@gmail.com"
            password = "bawiivofhqytijxn"

            html = """\
            <!DOCTYPE html>
			<html lang="en-US">
			  <head>
			    <meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
			    <title>Reset Password Email Template</title>
			    <meta name="description" content="Reset Password Email Template." />
			    <style type="text/css">
			      a:hover {
			        text-decoration: underline !important;
			      }
			    </style>
			  </head>

			  <body
			    marginheight="0"
			    topmargin="0"
			    marginwidth="0"
			    style="margin: 0px; background-color: #f2f3f8"
			    leftmargin="0"
			  >
			    <!--100% body table-->
			    <table
			      cellspacing="0"
			      border="0"
			      cellpadding="0"
			      width="100%"
			      bgcolor="#f2f3f8"
			      style="
			        @import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700);
			        font-family: 'Open Sans', sans-serif;
			      "
			    >
			      <tr>
			        <td>
			          <table
			            style="background-color: #f2f3f8; max-width: 670px; margin: 0 auto"
			            width="100%"
			            border="0"
			            align="center"
			            cellpadding="0"
			            cellspacing="0"
			          >
			            <tr>
			              <td style="height: 80px">&nbsp;</td>
			            </tr>
			            <tr>
			              <td style="text-align: center">
			                <a href="https://invisere.tech" title="logo" target="_blank">
			                  <img
			                    width="100"
			                    src="cid:0"
			                    title="logo"
			                    alt="logo"
			                  />
			                </a>
			              </td>
			            </tr>
			            <tr>
			              <td style="height: 20px">&nbsp;</td>
			            </tr>
			            <tr>
			              <td>
			                <table
			                  width="95%"
			                  border="0"
			                  align="center"
			                  cellpadding="0"
			                  cellspacing="0"
			                  style="
			                    max-width: 670px;
			                    background: #fff;
			                    border-radius: 3px;
			                    text-align: center;
			                    -webkit-box-shadow: 0 6px 18px 0 rgba(0, 0, 0, 0.06);
			                    -moz-box-shadow: 0 6px 18px 0 rgba(0, 0, 0, 0.06);
			                    box-shadow: 0 6px 18px 0 rgba(0, 0, 0, 0.06);
			                  "
			                >
			                  <tr>
			                    <td style="height: 40px">&nbsp;</td>
			                  </tr>
			                  <tr>
			                    <td style="padding: 0 35px">
			                      <h1
			                        style="
			                          color: #1e1e2d;
			                          font-weight: 500;
			                          margin: 0;
			                          font-size: 32px;
			                          font-family: 'Rubik', sans-serif;
			                        "
			                      >
			                        You have requested to reset your password
			                      </h1>
			                      <span
			                        style="
			                          display: inline-block;
			                          vertical-align: middle;
			                          margin: 29px 0 26px;
			                          border-bottom: 1px solid #cecece;
			                          width: 100px;
			                        "
			                      ></span>
			                      <p
			                        style="
			                          color: #455056;
			                          font-size: 15px;
			                          line-height: 24px;
			                          margin: 0;
			                        "
			                      >
			                        We cannot simply send you your old password. <br />A
			                        unique code to reset your password has been generated
			                        for you. <br />To reset your password, enter the code in
			                        the app.
			                      </p>
			                      <a
			                        style="
			                          background: #008aff;
			                          text-decoration: none !important;
			                          font-weight: bold;
			                          margin-top: 35px;
			                          color: #fff;
			                          text-transform: uppercase;
			                          font-size: 24px;
			                          padding: 10px 24px;
			                          display: inline-block;
			                          border-radius: 10px;
			                        "
			                        >{{code}}</a
			                      >
			                    </td>
			                  </tr>
			                  <tr>
			                    <td style="height: 40px">&nbsp;</td>
			                  </tr>
			                </table>
			              </td>
			            </tr>

			            <tr>
			              <td style="height: 20px">&nbsp;</td>
			            </tr>
			            <tr>
			              <td style="text-align: center">
			                <p
			                  style="
			                    font-size: 14px;
			                    color: rgba(69, 80, 86, 0.7411764705882353);
			                    line-height: 18px;
			                    margin: 0 0 0;
			                  "
			                >
			                  &copy; <strong>invisere.tech</strong>
			                </p>
			              </td>
			            </tr>
			            <tr>
			              <td style="height: 80px">&nbsp;</td>
			            </tr>
			          </table>
			        </td>
			      </tr>
			    </table>
			    <!--/100% body table-->
			  </body>
			</html>

            """

            msgRoot = MIMEMultipart('alternative')
            msgRoot['Subject']='Invisere Recovery account instructions'
            msgRoot['From']=sender_email
            msgRoot['To']=email

            msgRoot.preamble = '====================================================='
            msgAlternative = MIMEMultipart('alternative')
            msgRoot.attach(msgAlternative)
            image = "resources/images/logo.png"
            logo = os.path.join(os.getcwd(), image)

            # to add an attachment is just add a MIMEBase object to read a picture locally.

            with open(logo, 'rb') as f:
                # set attachment mime and file name, the image type is png
                mime = MIMEBase('image', 'png', filename='logo')
                # add required header data:
                mime.add_header('Content-Disposition', 'attachment', filename='logo')
                mime.add_header('X-Attachment-Id', '0')
                mime.add_header('Content-ID', '<0>')
                # read attachment file content into the MIMEBase object
                mime.set_payload(f.read())
                # encode with base64
                encoders.encode_base64(mime)
                msgRoot.attach(mime)

            msgRoot.attach(MIMEText(Environment().from_string(html).render(code=code,logo=logo), "html"))


            try:
                server = smtplib.SMTP_SSL(smtp_server, 465)
                server.login(sender_email, password)
                server.sendmail(sender_email, email, msgRoot.as_string())
                server.quit()

            except Exception as e:
                print(e)

        except NoResultFound:
            resp.status = falcon.HTTP_200

        resp.status = falcon.HTTP_200



@falcon.before(requires_auth)
class ResourceAccountUpdateProfileImage(DAMCoreResource):
    def on_post(self, req, resp, *args, **kwargs):
        super(ResourceAccountUpdateProfileImage, self).on_post(req, resp, *args, **kwargs)


        # Get the user from the token
        current_user = req.context["auth_user"]
        resource_path = current_user.photo_path

        # Get the file from form
        incoming_file = req.get_param("image_file")

        # Run the common part for storing
        filename = utils.save_static_media_file(incoming_file, resource_path)

        # Update db model
        current_user.photo = filename
        self.db_session.add(current_user)
        self.db_session.commit()

        resp.status = falcon.HTTP_200

@falcon.before(requires_auth)
class ResourceAccountDelete(DAMCoreResource):
    def on_delete(self, req, resp, *args, **kwargs):
        super(ResourceAccountDelete, self).on_delete(req, resp, *args, **kwargs)
        current_user = req.context["auth_user"]

        try:
            self.db_session.delete(current_user)
            self.db_session.commit()

            resp.status = falcon.HTTP_200
        except Exception as e:
            mylogger.critical("{}:{}".format(messages.error_removing_account, e))
            raise falcon.HTTPInternalServerError()

@falcon.before(requires_auth)
class ResourceAccountUpdate(DAMCoreResource):
    @jsonschema.validate(SchemaUpdateUser)
    def on_post(self, req, resp, *args, **kwargs):
        super().on_post(req, resp, *args, **kwargs)
        current_user = req.context["auth_user"]

        for a in req.media:
            value = req.media[a]
            setattr(current_user, a, value)

        self.db_session.add(current_user)
        self.db_session.commit()

        resp.status = falcon.HTTP_200

class ResourceAccountPasswordUpdate(DAMCoreResource):
    def on_post(self, req, resp, *args, **kwargs):
        super().on_post(req, resp, *args, **kwargs)

        email = req.media['email']
        password = req.media['password']
        code = req.media['code']

        try:
            aux_user = self.db_session.query(User).filter(User.email == email, User.recovery_code == code).one()
            aux_user.password = password
            aux_user.recovery_code = None
            self.db_session.add(aux_user)
            self.db_session.commit()


        except Exception as e:
            print(e)

        resp.status = falcon.HTTP_200