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
from sqlalchemy import exc, or_, and_, desc
from user_identity import UserIdentity
import json, random, jsonpickle
import re, datetime
from models import Users, Faculty, Materials, DiscThreads
from models import db
from flask_jwt_extended import (
    JWTManager, jwt_required, create_access_token,
    jwt_refresh_token_required, create_refresh_token,
    get_jwt_identity, set_access_cookies, get_jwt_claims,
    set_refresh_cookies, unset_jwt_cookies
)

materials = Blueprint('materials', __name__)

def complete(form, reqFields):
	for field in reqFields:
		if form[field] is None:
			return False
	return True

@materials.route('/api/materials/upload', methods = ['POST'])
@jwt_required
def upload_material():
	user = get_jwt_identity()
	faculty_id = UserIdentity.get_faculty_id(get_jwt_claims(), user)
	if faculty_id == -1:
		return jsonify({ 'success': -1 }), 401 #invalid access
	try:
		if complete(request.form, ['title', 'content', 'link', 'community']) == False:
			return jsonify({'success': 0 }), 201
		thread = uuid.uuid4().urn[9:]
		title = request.form['title']
		community = int(request.form['community'])
		content = content
		link = request.form['link']
		valid = db.session.query(UserCommunity).filter(UserCommunity.cid == community).\
													filter(UserCommunity.uid == user).first()
		if valid is None or len(valid) == 0:
			return jsonify([{'success':  -2}]) #faculty cannot post in this community
		material = Materials(title,content,link, faculty_id, community, thread)
		thread = DiscThreads(thread, community)
		try:
			db.session.add(material)
			db.session.add(thread)
			db.session.commit()
			return jsonify({'success': 1, 'thread_id': thread, 'material_id': material.id})
		except Exception:
			db.session.rollback()
			return jsonify({'success': -3 })
	except Exception:
		return jsonify({ 'success':  -4 }), 201
	return jsonify({ 'success':  -5 })
