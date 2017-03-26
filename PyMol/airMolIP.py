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

from math import acos,asin,pi,sin
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
        self.server_socket=socket.socket()
        self.IP=([l for l in ([ip for ip in socket.gethostbyname_ex(socket.gethostname())[2] if not ip.startswith("127.")][:1], [[(s.connect(('8.8.8.8', 53)), s.getsockname()[0], s.close()) for s in [socket.socket(socket.AF_INET, socket.SOCK_DGRAM)]][0][1]]) if l][0][0])

        self.client_socket=socket.socket()
        self.infos=""
        self.connected=False

        self.lastQuaternion=[0.0,0.0,0.0,1.0]

    def start_server(self):
        self.server_socket=socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	self.server_socket.bind(("",8888))
        self.server_socket.listen(1)
        print("Server waiting for connexion")
        self.client_socket,self.infos=self.server_socket.accept()
        self.connected=True
        print("connected")
        while True: 
		try:
            		#Envoyer les valeurs de quaternion et recuperer theta apres
            		data = self.client_socket.recv(1024).decode('utf-8')
            		if(data):

                		currentQuaternion = [float(i) for i in (data.split(','))]
                		#print("current:"+str(currentQuaternion)) 
                		conjugatedQuaternion=[-1*i for i in currentQuaternion[:3]]
                		conjugatedQuaternion.append(currentQuaternion[3])
                		#print("conjugated:"+str(conjugatedQuaternion)) 
                		q1=conjugatedQuaternion
                		#print("q1:"+str(q1)) 
                                q2=self.lastQuaternion
                		#print("q2:"+str(q2)) 
                
                		combinedQuaternion=[
                	        q1[3]*q2[0]+q1[0]*q2[3]+q1[1]*q2[2]-q1[2]*q2[1],
                        	q1[3]*q2[1]-q1[0]*q2[2]+q1[1]*q2[3]+q1[2]*q2[0],
                	        q1[3]*q2[2]+q1[0]*q2[1]-q1[1]*q2[0]+q1[2]*q2[3],
                	        q1[3]*q2[3]-q1[0]*q2[0]-q1[1]*q2[1]-q1[2]*q2[2]
                	        ]
                
               			#print("combined:"+str(combinedQuaternion))
                                print("magnitude:"+str(combinedQuaternion[0]**2+combinedQuaternion[1]**2+
                                        combinedQuaternion[2]**2+combinedQuaternion[3]**2))
                		self.lastQuaternion=[i for i in currentQuaternion]
                
                		thetaRad=(2.0*acos(combinedQuaternion[3]))
                		thetaDeg=(thetaRad*180.0/pi)
                		sinThetaSurDeux=sin(thetaRad/2)

                		for i in range(2):
                		    combinedQuaternion[i]/=sinThetaSurDeux
                		combinedQuaternion[3]=thetaDeg

                		#print("\nX : " + str(combinedQuaternion[0]))
                		#print("\nY : " + str(combinedQuaternion[1]))
                		#print("\nZ : " + str(combinedQuaternion[2]))
                		print("\nTheta : " + str(combinedQuaternion[3]))
                		print ("\n\n")
				cmd.rotate([combinedQuaternion[0],combinedQuaternion[1],combinedQuaternion[2]],angle=-combinedQuaternion[3]/2)
            		else:
                		self.client_socket.close()
                		#self.connected=False
                		self.server_socket.close()
                		self.connected = False
                		print("Disconnected")
                		break
		except:
			pass

        #self.client_socket.close()

    def run(self):
        self.start_server()

def __init__(self):
	self.menuBar.addmenuitem('Plugin', 'command',
                            'AirMol',
                            label = 'AirMol',
                            command = lambda s=self : airMol(s))
	
def airMol(self):
    server=IPserver()
    server.start()
    root = Tk()
    root.title("AirMol")
    root.geometry("300x200")
    Label(root, text="        Local IP Address: ").grid(row=0,column=1,sticky=W)
    L = Label(root, text=server.IP)
    L.grid(row=0,column=2,sticky=W)
    
    Label(root, text="        Connected:").grid(row=4,column=1,sticky=W)
    label = Label(root, text="no")
    label.grid(row=4,column=2,sticky=W)
    label.rowconfigure(0, pad=3)
    
    def checkCon():
        t=StringVar()
        if server.connected:
            t.set("yes")
        else:
            t.set("no")
        label.configure(text=t.get())
        root.after(1000, checkCon)
    root.after(1000,checkCon)
    
    
cmd.extend('airMol', airMol)
