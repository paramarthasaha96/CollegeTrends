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
from flask import Blueprint, request, flash, url_for, redirect, render_template, jsonify, session
from sqlalchemy import exc
import re
from models import Users
from models import db
from flask_jwt_extended import (
    JWTManager, jwt_required, create_access_token,
    jwt_refresh_token_required, create_refresh_token,
    get_jwt_identity, set_access_cookies,
    set_refresh_cookies, unset_jwt_cookies
)
register = Blueprint('register', __name__)

def validate_fields(mobile, email):
	if not re.search(r"^[789]\d{9}$", mobile):
		return False
	elif email and not re.search(r"(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$)", email):
		return False
	else:
		return True

def complete(form):
	reqFields = ['mobile', 'pwd']
	for field in reqFields:
		if form[field] is None:
			return False
	return True
	
@register.route('/')
def register_cust():
	return render_template('homepage.html')
	
@register.route('/api/register/students', methods = ['POST'])
def register_cust_scr():
	if request.method == 'POST':
		if not complete(request.form):
			return jsonify([{ 'response': 1, 'msg': "Incomplete form" }])
		else:
			print(str(request.form['email']))
			if 'email' not in request.form:
				e_mail = ''
			else:
				e_mail = request.form['email']
			if validate_fields(request.form['mobile'], request.form['email']) == True:
				db.create_all()
				user = Users(mobile = request.form['mobile'], fname = request.form['fname'],
					lname = request.form['lname'], email = e_mail, pwd = request.form['pwd'], role = Users.ROLE_USER)
				try:
					db.session.add(user)
					db.session.commit()
				except exc.IntegrityError:
					db.session.rollback()
					return jsonify([{ 'response': 4, 'msg': "Integrity constraint error" }])
				return jsonify([{ 'response': 0, 'msg': "Success" }])
			else:
				return jsonify([{ 'response': 2, 'msg': "Invalid fields" }])
	else:
		return jsonify([{ 'response': 3, 'msg': "Bad request" }])

