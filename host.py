#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from bottle import route, run, template, request
import concurrent.futures
#import tempfile
#import os

#output = open("/tmp/output.ikisugi", 'w')
mRotation = 0.0

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
		#output.write(str(data)+"\n")
		#output.flush()
		#print(data)
		mRotation = float(data)
		return "success"
	else:
		return "failed"

#TODO メイン制御部実装
def robotmain:
	print("doge")

if __name__ == "__main__":
	with concurrent.futures.ThreadPoolExecutor(max_workers=2) as executor:
		executor.submit(robotmain)
	run(host='192.168.81.1', port=4545)
	#run(host='127.0.0.1', port=4545, quiet=True)
	#run(host='192.168.43.54', port=4545)
	
	#try:
	#	run(host='192.168.81.1', port=4545)
	#except KeyboardInterrupt:
	#	output.close()
	#	tempfile.TemporaryDirectroy().cleanup()
	#	if os.path.exists("/tmp/output.ikisugi"):
	#		os.remove("/tmp/output.ikisugi")

