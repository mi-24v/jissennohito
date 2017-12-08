#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from bottle import route, run, template, request
import tempfile
import os

output = open("/tmp/output.ikisugi", 'w')

# 文字列から数値（float）への変換
def is_float_expression(s):
	try:
		val = float(s)
		return True
	except ValueError:
		return False

@route('/hello/<name>')
def index(name):
	return template('<b>Hello {{name}}</b>!', name=name)

@route('/rotation', method='POST')
def postRotation():
	data = request.json["rotationData"]["rotation"]
	if is_float_expression(data):
		output.write(str(data)+"\n")
		output.flush()
		return "success"
	else:
		return "failed"

try:
	run(host='192.168.81.1', port=4545)
except KeyboardInterrupt:
	output.close()
	tempfile.TemporaryDirectroy().cleanup()
	if os.path.exists("/tmp/output.ikisugi"):
		os.remove("/tmp/output.ikisugi")

