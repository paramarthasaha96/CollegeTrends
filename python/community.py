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
import json, random, jsonpickle
import re, datetime
from models import Users, Drivers, Bookings, Trip, DriverDetails
from models import db
from flask_jwt_extended import (
    JWTManager, jwt_required, create_access_token,
    jwt_refresh_token_required, create_refresh_token,
    get_jwt_identity, set_access_cookies, get_jwt_claims,
    set_refresh_cookies, unset_jwt_cookies
)

communities = Blueprint('communities', __name__)

@communities.route('/api/communities/root', methods = ['POST'])
@jwt_required
def root_community():
	user = get_jwt_identity()
	try:
		results = db.session.query(Communities, UserCommunity, Users).filter(Communities.id == UserCommunity.cid).filter(Communities.parent.is_(None)).\
													filter(UserCommunity.uid == Users.id).all()
		if results is None or len(results) == 0:
			return jsonify([{'success':  0}])
		resp = []
		for result in results:
			resp.append({'id': result[0].id, 'name': result[0].name})
		return jsonify([{'success':  1}, resp]), 200
	except Exception:
		return jsonify([{ 'success':  0 }]), 201
	return jsonify([{ 'success':  0 }])
