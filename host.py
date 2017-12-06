#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from bottle import route, run, template

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
		print(data)
		return "success"
	else:
		return "failed"

run(host='192.168.1.1', port=4545)

