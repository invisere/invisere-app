#!/usr/bin/python
# -*- coding: utf-8 -*-

SchemaUserToken = {
    "type": "object",
    "properties": {
        "token": {"type": "string"},
    },
    "required": ["token"]
}

SchemaRegisterUser = {
    "type": "object",
    "properties": {
        "name": {"type": "string"},
        "username": {"type": "string"},
        "password": {"type": "string"},
        "email": {"type": "string"},
    },
    "required": ["name", "username", "email", "password"]
}

SchemaUpdateUser = {
    "type": "object",
    "properties": {
        "name": {"type": "string"},
        "username": {"type": "string"},
        "password": {"type": "string"},
        "email": {"type": "string"},
    }
}