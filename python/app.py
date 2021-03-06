#!bin/python3
# -*- coding: utf-8 -*-
#
#  app.py
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
from __init__ import app, jwt, Server
from flask import Blueprint

from login import loginp
from community import communities
from registration import register
from material import materials

app.register_blueprint(loginp)
app.register_blueprint(communities)
app.register_blueprint(register)
app.register_blueprint(materials)

if __name__ == '__main__':
	app.run(host=Server.SERVER_URL, port=Server.SERVER_PORT, threaded=True, debug = True)