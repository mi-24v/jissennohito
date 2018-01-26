import threading
from pwn import *

class dogeThread(threading.Thread):
	def __init__(self,rotation):
		super(dogeThread, self).__init__()
		self.rotation = -810.0

	def rundoge(self):
		cmd="./test"
		p = process(cmd)
		waitData()

	def waitData(self):
		print p.recvuntil("rotation:")

	def inputData(self, rotation):
		p.sendline(rotation)

	def waitdoge(self):
		p.interactive()

