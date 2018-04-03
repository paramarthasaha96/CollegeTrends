#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
#  register_cust.py
#  
#  Copyright 2017 salaciouscrumb <salaciouscrumb@salaciouscrumb-Inspiron-3537>
#  
#  This program is free software; you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation; either version 2 of the License, or
#  (at your option) any later version.
#  
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#  
#  You should have received a copy of the GNU General Public License
#  along with this program; if not, write to the Free Software
#  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
#  MA 02110-1301, USA.
#  
#  
from __init__ import app
import json, random, jsonpickle
import re, datetime
from models import Users, Faculty, Admins, Students
from models import db
from flask_jwt_extended import (
    JWTManager, jwt_required, create_access_token,
    jwt_refresh_token_required, create_refresh_token,
    get_jwt_identity, set_access_cookies, get_jwt_claims,
    set_refresh_cookies, unset_jwt_cookies
)

class UserIdentity:
	@staticmethod
	def get_id_for_type(claims, identity):
		try:
			if claims['roles'] == Users.ROLE_STUDENT:
				return get_student_id(claims, identity)
			elif claims['roles'] == Users.ROLE_FACULTY:
				return get_faculty_id(claims, identity)
			else:
				return get_admin_id(claims, identity)
		except Exception:
			return -1

	@staticmethod
	def get_student_id(claims, identity):
		try:
			if claims['roles'] != Users.ROLE_STUDENT:
				return -1
			else:
				student = Students.query.filter_by(user = identity).first().id
				return student
		except Exception:
			return -1

	@staticmethod
	def get_faculty_id(claims, identity):
		try:
			if claims['roles'] != Users.ROLE_FACULTY:
				return -1
			else:
				faculty = Faculty.query.filter_by(user = identity).first().id
				return faculty
		except Exception:
			return -1

	@staticmethod
	def get_admin_id(claims, identity):
		try:
			if claims['roles'] != Users.ROLE_ADMIN:
				return -1
			else:
				admin = Admins.query.filter_by(user = identity).first().id
				return admin
		except Exception:
			return -1