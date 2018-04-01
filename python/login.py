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
import hashlib, uuid, re	
from __init__ import jwt, app
from flask import Blueprint, session, request, flash, url_for, redirect, render_template, jsonify
from models import Users, Students, Faculty, Admins, UserToken
from sqlalchemy import exc
import datetime
from flask_jwt_extended import (
    JWTManager, jwt_required, create_access_token,
    jwt_refresh_token_required, create_refresh_token,
    get_jwt_identity, set_access_cookies, get_raw_jwt,
    set_refresh_cookies, unset_jwt_cookies
)
from werkzeug.security import safe_str_cmp

loginp = Blueprint('login', __name__)

def validate_pass(user, pwd):
	salt = user.salt
	pwdhash = hashlib.sha512((pwd + salt).encode('utf-8')).hexdigest()
	if safe_str_cmp(user.pwd, pwdhash):
		return True
	else:
		return False
		
@jwt.user_claims_loader
def add_claims_to_access_token(user):
    return {
		'roles': user.role,
		'useragent': user.agent
	}
	
@jwt.user_identity_loader
def user_identity_lookup(user):
    return user.id

@loginp.route('/token/login/student', methods = ['POST'])
def login_student():
	if request.method == 'POST':
		if not request.form['mobile'] or not request.form['pwd']:
			return jsonify( { 'success': 0 } ), 400	
		else:
			curUser = db.session.query(Users,Students).filter(Users.mobile == request.form['mobile']).first()
			if curUser and validate_pass(user = curUser[0], pwd = request.form['pwd']):
				remember = False;
				try:
					remember = request.form['remember']
				except Exception as e:
					remember = False
				curUser.agent = request.headers.get('User-Agent')
				if remember == True or remember == 'true':
					expires_access = datetime.timedelta(days=365)
					expires_refresh = datetime.timedelta(days=3650)
					access_token = create_access_token(identity = curUser[0], expires_delta=expires_access)
					refresh_token = create_refresh_token(identity = curUser[0], expires_delta=expires_refresh)
					resp = jsonify({'success': 1, 'student_id': curUser[1].id, 'student_year': curUser[1].year, 'student_stream': curUser[1].stream, 'user_fname': curUser[0].fname, 'user_mobile': curUser[0].mobile, 'user_email': curUser[0].email, 'user_lname': curUser[0].lname})						
					expiry = datetime.datetime.now() + datetime.timedelta(days=3650)
					token_entry = UserToken(curUser[0].id, refresh_token, curUser.agent, expiry)
					try:
						db.session.add(token_entry)
						db.session.commit()
					except exc.IntegrityError:
						db.session.rollback()
						return jsonify( {'success': 0 } ), 401
					set_access_cookies(resp, access_token)
					set_refresh_cookies(resp, refresh_token)
					return resp
				else:
					expires_access = datetime.timedelta(hours=24)
					access_token = create_access_token(identity = curUser)
					resp = jsonify({'success': 1, 'student_id': curUser[1].id, 'student_year': curUser[1].year, 'student_stream': curUser[1].stream, 'user_fname': curUser[0].fname, 'user_mobile': curUser[0].mobile, 'user_email': curUser[0].email, 'user_lname': curUser[0].lname})						
					set_access_cookies(resp, access_token)
					return resp
					
		return jsonify( {'success': 0 } ), 401
	return jsonify( {'success': 0 } ), 400

@loginp.route('/token/login/faculty', methods = ['POST'])
def login_faculty():
	if request.method == 'POST':
		if not request.form['mobile'] or not request.form['pwd']:
			return jsonify( { 'success': 0 } ), 400	
		else:
			curUser = db.session.query(Users,Faculty).filter(Users.mobile == request.form['mobile']).first()
			if curUser and validate_pass(user = curUser[0], pwd = request.form['pwd']):
				remember = False;
				try:
					remember = request.form['remember']
				except Exception as e:
					remember = False
				curUser.agent = request.headers.get('User-Agent')
				if remember == True or remember == 'true':
					expires_access = datetime.timedelta(days=365)
					expires_refresh = datetime.timedelta(days=3650)
					access_token = create_access_token(identity = curUser[0], expires_delta=expires_access)
					refresh_token = create_refresh_token(identity = curUser[0], expires_delta=expires_refresh)
					resp = jsonify({'success': 1, 'faculty_id': curUser[1].id, 'faculty_stream': curUser[1].stream, 'user_fname': curUser[0].fname, 'user_mobile': curUser[0].mobile, 'user_email': curUser[0].email, 'user_lname': curUser[0].lname})						
					expiry = datetime.datetime.now() + datetime.timedelta(days=3650)
					token_entry = UserToken(curUser[0].id, refresh_token, curUser.agent, expiry)
					try:
						db.session.add(token_entry)
						db.session.commit()
					except exc.IntegrityError:
						db.session.rollback()
						return jsonify( {'success': 0 } ), 401
					set_access_cookies(resp, access_token)
					set_refresh_cookies(resp, refresh_token)
					return resp
				else:
					expires_access = datetime.timedelta(hours=24)
					access_token = create_access_token(identity = curUser)
					resp = jsonify({'success': 1, 'faculty_id': curUser[1].id, 'faculty_stream': curUser[1].stream, 'user_fname': curUser[0].fname, 'user_mobile': curUser[0].mobile, 'user_email': curUser[0].email, 'user_lname': curUser[0].lname})						
					set_access_cookies(resp, access_token)
					return resp
					
		return jsonify( {'success': 0 } ), 401
	return jsonify( {'success': 0 } ), 400

@loginp.route('/token/refresh', methods=['POST'])
@jwt_refresh_token_required
def refresh():
	current_user = get_jwt_identity()
	new_user = Users.query.filter(Users.id == current_user).first()
	new_user.agent = request.headers.get('User-Agent')
	token_hash = hashlib.sha512((request.cookies.get(app.config['JWT_REFRESH_COOKIE_NAME'])).encode('utf-8')).hexdigest()
	user_token = UserToken.query.filter(UserToken.user_id == new_user.id).filter(UserToken.refresh == token_hash).order_by(UserToken.timestamp.desc()).first()
	now = datetime.datetime.now()
	if user_token.expiry < now:
		return jsonify({'refresh': False})
	#Unsure how to check user agents, so ignoring for now
	access_token = create_access_token(identity = new_user)
	resp = jsonify({'refresh': True})
	set_access_cookies(resp, access_token)
	return resp, 200

@loginp.route('/token/remove', methods=['POST'])
@jwt_required
def logout():
    resp = jsonify({'logout': True})
    unset_jwt_cookies(resp)
    return resp, 200

@loginp.route('/token/verify', methods=['POST'])
@jwt_required
def verify():
	current_user = get_jwt_identity()
	if current_user:
		return jsonify({'success': 1}), 200
	else: return jsonify({'success': 0}), 401
	