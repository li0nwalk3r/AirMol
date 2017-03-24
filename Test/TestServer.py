# -*- coding: utf-8 -*-

import socket
import cmd
import os, time

print("CONNECTION EN COURS")
socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
socket.bind(('', 8888))
socket.listen(1)
client, info = socket.accept()
print("\nCONNEXTION ETABLIE")


msg = None

try:
	while msg != "":
		#print "\n\t[*] ADRESSE DISTANTE : {}\n\t[*] PORT DISTANT : {}\n\n".format(info[0], info[1])
		msg = client.recv(4096).decode()
		print(msg)
		print("\n"*47)

except KeyboardInterrupt:
	socket.close();


socket.close();

print("Déconnecté")