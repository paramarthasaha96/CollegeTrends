# -*- coding: utf-8 -*-
#
#  __init__.py
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
from flask import Flask
from flask_jwt_extended import (
    JWTManager, jwt_required, create_access_token,
    get_jwt_identity
)
class Server:
	SERVER_URL = "0.0.0.0"
	SERVER_PORT = 5000
	
app = Flask(__name__)
app.secret_key = 'I8m2ih,?u5!Z;^K5,wYI'
app.config['UPLOAD_FOLDER'] = ''
app.config['JWT_SECRET_KEY'] = '8gs#\=hw;/Pa*AgR93%$y%i~g\l@y#Mr|vCAJwQ?;0Gjc,J?=c'  
app.config['JWT_TOKEN_LOCATION'] = ['cookies']
app.config['JWT_COOKIE_SECURE'] = False
app.config['JWT_COOKIE_CSRF_PROTECT'] = True
app.config['JWT_CSRF_IN_COOKIES'] = True

jwt = JWTManager(app)




