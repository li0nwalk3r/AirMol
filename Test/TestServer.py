# -*- coding: utf-8 -*-

import socket
import cmd

print "CONNECTION EN COURS"
socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
socket.bind(('', 8888))
socket.listen(1)
client, info = socket.accept()
print "\nCONNEXTION ETABLIE";
print "\n\t[*] ADRESSE DISTANTE : {}\n\t[*] PORT DISTANT : {}\n\n".format(info[0], info[1])

msg = ''
while msg != "fin":
	msg = client.recv(4096).decode()
	print(msg)

socket.close();