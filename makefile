javaSocket/SocketServer.class: javaSocket/SocketServer.java javaSocket/AES.class javaSocket/Encryption.class javaSocket/FailingModel.class javaSocket/FileHandler.class javaSocket/ServerRequestHandler.class javaSocket/UserLogin.class
	javac javaSocket/SocketServer.java

javaSocket/SocketClient.class: javaSocket/SocketClient.java javaSocket/AES.class javaSocket/Encryption.class javaSocket/FailingModel.class javaSocket/FileHandler.class javaSocket/ClientRequest.class
	javac javaSocket/SocketClient.java


run: Driver.class
	java javaSocket/SocketServer 8090
	javaSocket/SocketClient localhost 8090 "my_file.txt" a 1

clean: 
	rm -rf *.class
	rm -rf */*.class