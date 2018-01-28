#!/usr/bin/env python2
# -*- coding: utf-8 -*-

from bottle import route, run, template, request
from pwn import *
#import tempfile
#import os

#output = open("/tmp/output.ikisugi", 'w')

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
        response = ""
        payload = ""
	if is_float_expression(data):
		#output.write(str(data)+"\n")
		#output.flush()
		#print data
		payload = str(data)
		response = "success"
	else:
		payload = "-810"
		response = "failed"
        try:
            main_process.sendline(payload)
            print main_process.recvuntil('ok')
        except EOFError:
            pass
        return response

main_process = process("./test")

#run(host='127.0.0.1', port=4545, quiet=True)
run(host='192.168.81.1', port=4545, quiet=True)
main_process.interactive()
#run(host='192.168.43.54', port=4545)

#try:
#	run(host='192.168.81.1', port=4545)
#except KeyboardInterrupt:
#	output.close()
#	tempfile.TemporaryDirectroy().cleanup()
#	if os.path.exists("/tmp/output.ikisugi"):
#		os.remove("/tmp/output.ikisugi")

