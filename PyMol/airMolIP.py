#!/usr/bin/python
# -*- coding: utf-8 -*-

'''This file is part of AirMol.
    AirMol is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.
    AirMol is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
along with AirMol. If not, see <http://www.gnu.org/licenses/>.'''

from math import acos, asin, pi, sin
import socket
import tkMessageBox
import tkSimpleDialog
from Tkinter import *
from pymol import cmd
import time
from threading import *


class IPserver(Thread):

    def __init__(self):
        Thread.__init__(self)
        self.server_socket = socket.socket()
        self.IP = [l for l in ([ip for ip in
                   socket.gethostbyname_ex(socket.gethostname())[2]
                   if not ip.startswith('127.')][:1],
                   [[(s.connect(('8.8.8.8', 53)), s.getsockname()[0],
                   s.close()) for s in [socket.socket(socket.AF_INET,
                   socket.SOCK_DGRAM)]][0][1]]) if l][0][0]

        self.client_socket = socket.socket()
        self.infos = ''
        self.connected = False

        self.lastQuaternion = [0.0, 0.0, 0.0, 1.0]

    def start_server(self):
        self.server_socket = socket.socket(socket.AF_INET,
                socket.SOCK_STREAM)
        self.server_socket.bind(('', 8888))
        self.server_socket.listen(1)
        print 'Server waiting for connexion'
        (self.client_socket, self.infos) = self.server_socket.accept()
        self.connected = True
        print 'connected'
        while True:
            try:

                    # Envoyer les valeurs de quaternion et recuperer theta apres

                data = self.client_socket.recv(200).decode('utf-8')
                if data:

                    SplitData = [float(i) for i in (data.split(','))]
                    if SplitData[3] >= 1:
                        cmd.rotate([-6 * SplitData[0], -6
                                   * SplitData[1], -6
                                   * SplitData[2]], angle=3
                                   * SplitData[3],
                                   selection='all')
                    else:
                        pass
                else:
                    self.client_socket.close()

                        # self.connected=False

                    self.server_socket.close()
                    self.connected = False
                    print 'Disconnected'
                    break
            except:
                pass

        # self.client_socket.close()

    def run(self):
        self.start_server()


def __init__(self):
    self.menuBar.addmenuitem('Plugin', 'command', 'AirMol',
                             label='AirMol', command=lambda s=self: \
                             airMol(s))


def airMol(self):
    server = IPserver()
    server.start()
    root = Tk()
    root.title('AirMol')
    root.geometry('300x200')
    Label(root, text='      Local IP Address: ').grid(row=0, column=1,
            sticky=W)
    L = Label(root, text=server.IP)
    L.grid(row=0, column=2, sticky=W)

    Label(root, text='      Connected:').grid(row=4, column=1, sticky=W)
    label = Label(root, text='no')
    label.grid(row=4, column=2, sticky=W)
    label.rowconfigure(0, pad=3)

    def checkCon():
        t = StringVar()
        if server.connected:
            t.set('yes')
        else:
            t.set('no')
        label.configure(text=t.get())
        root.after(1000, checkCon)

    root.after(1000, checkCon)


cmd.extend('airMol', airMol)


            
