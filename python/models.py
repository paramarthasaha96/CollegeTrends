#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
#  models.py
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
from datetime import datetime
from db_config import db
from sqlalchemy import UniqueConstraint, PrimaryKeyConstraint, exc
import cgi

class Users(db.Model):
	ROLE_STUDENT = 0
	ROLE_FACULTY = 1
	ROLE_ADMIN = 2
	id = db.Column('user_id', db.Integer, primary_key = True)
	fname = db.Column('user_fname', db.Text)
	lname = db.Column('user_lname', db.Text)
	mobile = db.Column('user_mobile', db.Integer, unique = True)
	email = db.Column('user_email', db.Text, unique = True)
	pwd = db.Column('user_pwd', db.Text) 
	salt = db.Column('user_salt', db.Text)
	role = db.Column('user_role', db.Text)
	
	def is_authenticated(self):
		return True
 
	def is_active(self):
		return True
	
	def is_anonymous(self):
		return False
	
	def get_id(self):
		return str(self.id)
		
	def __init__(self, mobile, fname, lname, email, pwd, role):
		self.mobile =  int(mobile)
		self.fname = cgi.escape(fname)
		self.lname = cgi.escape(lname)
		if len(email) == 0:
			self.email = None
		else: self.email = cgi.escape(email)
		self.salt = uuid.uuid4().hex
		self.pwd = hashlib.sha512((pwd + self.salt).encode('utf-8')).hexdigest()
		self.role = int(role)

class Students(db.Model):
	id = db.Column('student_id', db.Integer, primary_key = True)
	user = db.Column('student_user_id', db.Integer, unique = True)
	stream = db.Column('student_stream', db.Integer)
	year = db.Column('student_year', db.Integer)

	def __init__(self, curUser, stream, year):
		self.user = curUser.id
		self.stream = stream
		self.year = year

class Admins(db.Model):
	id = db.Column('admin_id', db.Integer, primary_key = True)
	user = db.Column('admin_user_id', db.Integer, unique = True)

	def __init__(self, curUser):
		self.user = curUser.id

class Faculty(db.Model):
	id = db.Column('faculty_id', db.Integer, primary_key = True)
	user = db.Column('faculty_user_id', db.Integer, unique = True)
	stream = db.Column('faculty_stream', db.Integer)

	def __init__(self, curUser, stream):
		self.user = curUser.id
		self.stream = stream

class UserToken(db.Model):
	__tablename__ = 'user_token'
	__table_args__ = ( PrimaryKeyConstraint('token_user_id', 'token_refresh'), )
	user_id = db.Column('token_user_id', db.Integer)
	refresh = db.Column('token_refresh', db.String(200))
	expiry = db.Column('token_expiry', db.DateTime)
	agent = db.Column('token_agent', db.String(150))
	timestamp = db.Column('token_timestamp', db.DateTime)
	
	def __init__(self, uid, refresh, agent, expiry):
		self.user_id = uid
		self.refresh = hashlib.sha512((refresh).encode('utf-8')).hexdigest()
		self.agent = agent
		self.expiry = expiry
		
class Communities(db.Model):
	id = db.Column('community_id', db.Integer, primary_key = True)
	name = db.Column('community_name', db.String(100))
	parent = db.Column('community_parent', db.Integer)

	def __init__(self, name, parent=None):
		self.name = cgi.escape(name)
		if parent is not None:
			self.parent = int(parent)
		else:
			self.parent = None

class UserCommunity(db.Model):
	__tablename__ = 'user_community'
	__table_args__ = ( PrimaryKeyConstraint('uc_user_id', 'uc_community_id'), )
	uid = db.Column('uc_user_id', db.Integer)
	cid = db.Column('uc_community_id', db.Integer)
	
	def __init__(self, uid, cid):
		self.uid = int(uid)
		self.cid = int(cid)

class Materials(db.Model):
	id = db.Column('material_id', db.Integer, primary_key = True)
	title = db.Column('material_title', db.String(400))
	content = db.Column('material_content', db.Text)
	link = db.Column('material_link', db.String(400))
	faculty = db.Column('material_faculty', db.Integer)
	community = db.Column('material_community', db.Integer)
	thread = db.Column('material_thread', db.String(20))
	timestamp = db.Column('material_timestamp', db.DateTime)

	def __init__(self, title, content, link, faculty, community, thread):
		self.title = cgi.escape(title)
		self.content = cgi.escape(content)
		self.link = cgi.escape(link)
		self.faculty = int(faculty)
		self.community = int(community)
		self.thread = thread

class DiscThreads(db.Model):
	__tablename__ = 'disc_threads'
	id = db.Column('disc_thread_id', db.String(20), primary_key = True)
	community = db.Column('disc_thread_community', db.Integer)
	timestamp = db.Column('disc_thread_timestamp', db.DateTime)

	def __init__(self, id, community):
		self.community = int(community)
		self.id = id