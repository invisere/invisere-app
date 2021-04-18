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
            <html>
            <head>

            <meta charset="utf-8">
            <meta http-equiv="x-ua-compatible" content="ie=edge">
            <title>Password Reset</title>
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <style type="text/css">
            /**
            * Google webfonts. Recommended to include the .woff version for cross-client compatibility.
            */
            @media screen {
                @font-face {
                font-family: 'Source Sans Pro';
                font-style: normal;
                font-weight: 400;
                src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');
                }

                @font-face {
                font-family: 'Source Sans Pro';
                font-style: normal;
                font-weight: 700;
                src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');
                }
            }

            /**
            * Avoid browser level font resizing.
            * 1. Windows Mobile
            * 2. iOS / OSX
            */
            body,
            table,
            td,
            a {
                -ms-text-size-adjust: 100%; /* 1 */
                -webkit-text-size-adjust: 100%; /* 2 */
            }

            /**
            * Remove extra space added to tables and cells in Outlook.
            */
            table,
            td {
                mso-table-rspace: 0pt;
                mso-table-lspace: 0pt;
            }

            /**
            * Better fluid images in Internet Explorer.
            */
            img {
                -ms-interpolation-mode: bicubic;
            }

            /**
            * Remove blue links for iOS devices.
            */
            a[x-apple-data-detectors] {
                font-family: inherit !important;
                font-size: inherit !important;
                font-weight: inherit !important;
                line-height: inherit !important;
                color: inherit !important;
                text-decoration: none !important;
            }

            /**
            * Fix centering issues in Android 4.4.
            */
            div[style*="margin: 16px 0;"] {
                margin: 0 !important;
            }

            body {
                width: 100% !important;
                height: 100% !important;
                padding: 0 !important;
                margin: 0 !important;
            }

            /**
            * Collapse table borders to avoid space between cells.
            */
            table {
                border-collapse: collapse !important;
            }

            a {
                color: #1a82e2;
            }

            img {
                height: auto;
                line-height: 100%;
                text-decoration: none;
                border: 0;
                outline: none;
            }
            </style>

            </head>
            <body style="background-color: #e9ecef;">

            <!-- start preheader -->
            <div class="preheader" style="display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;">
                A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.
            </div>
            <!-- end preheader -->

            <!-- start body -->
            <table border="0" cellpadding="0" cellspacing="0" width="100%">

                <!-- start logo -->
                <tr>
                <td align="center" bgcolor="#e9ecef">
                    <!--[if (gte mso 9)|(IE)]>
                    <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                    <tr>
                    <td align="center" valign="top" width="600">
                    <![endif]-->
                    <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">
                    <tr>
                        <td align="center" valign="top" style="padding: 36px 24px;">
                        <a href="https://github.com/Invisere/invisere-app" target="_blank" style="display: inline-block;">
                            <img src="cid:0" alt="Logo" border="0" width="48" style="display: block; width: 48px; max-width: 48px; min-width: 48px;">
                        </a>
                        </td>
                    </tr>
                    </table>
                    <!--[if (gte mso 9)|(IE)]>
                    </td>
                    </tr>
                    </table>
                    <![endif]-->
                </td>
                </tr>
                <!-- end logo -->

                <!-- start hero -->
                <tr>
                <td align="center" bgcolor="#e9ecef">
                    <!--[if (gte mso 9)|(IE)]>
                    <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                    <tr>
                    <td align="center" valign="top" width="600">
                    <![endif]-->
                    <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">
                    <tr>
                        <td align="left" bgcolor="#ffffff" style="padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;">
                        <h1 style="margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;">Reset Your Password</h1>
                        </td>
                    </tr>
                    </table>
                    <!--[if (gte mso 9)|(IE)]>
                    </td>
                    </tr>
                    </table>
                    <![endif]-->
                </td>
                </tr>
                <!-- end hero -->

                <!-- start copy block -->
                <tr>
                <td align="center" bgcolor="#e9ecef">
                    <!--[if (gte mso 9)|(IE)]>
                    <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                    <tr>
                    <td align="center" valign="top" width="600">
                    <![endif]-->
                    <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">

                    <!-- start copy -->
                    <tr>
                        <td align="left" bgcolor="#ffffff" style="padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;">
                        <p style="margin: 0;">Please, copy the code below to the recovery password screen in your application and then set the new password.</p>
                        <p style="margin: 0;">Code: {{code}}</p>
                        </td>
                    </tr>
                    <!-- end copy -->

                    
                    </table>
                    <!--[if (gte mso 9)|(IE)]>
                    </td>
                    </tr>
                    </table>
                    <![endif]-->
                </td>
                </tr>
                <!-- end copy block -->

            </table>
            <!-- end body -->

            </body>

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