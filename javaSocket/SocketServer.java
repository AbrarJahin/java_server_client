//https://www.infoworld.com/article/2853780/socket-programming-for-scalable-systems.html

package javaSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Thread
{
	private ServerSocket serverSocket;
	private int port;
	private boolean running = false;

	public SocketServer( int port )
	{
		this.port = port;
	}

	public void startServer()
	{
		try
		{
			serverSocket = new ServerSocket( port );
			this.start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void stopServer()
	{
		running = false;
		this.interrupt();
	}

	@Override
	public void run()
	{
		running = true;
		while( running )
		{
			try
			{
				System.out.println( "Listening for a connection" );

				// Call accept() to receive the next connection
				Socket socket = serverSocket.accept();

				// Pass the socket to the RequestHandler thread for processing
				RequestHandler requestHandler = new RequestHandler( socket );
				requestHandler.start();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void main( String[] args )
	{
		///////////////////////////////
		// String userName = "a";
		// String password = "1";
		// UserLogin loginCheck = new UserLogin();
		// boolean ifLoggedIn = loginCheck.checkLogin(userName, password);
		// System.out.println(ifLoggedIn ? "print true": "print false");
		///////////////////////////////
		int port;
		if( args.length == 0 )
		{
			System.out.println( "Usage: SimpleSocketServer <port>" );
			//System.exit( 0 );
			port = 8080;
		}
		else
		{
			port = Integer.parseInt( args[ 0 ] );
			System.out.println( "Start server on port: " + port );
		}
		

		SocketServer server = new SocketServer( port );
		server.startServer();

		// Automatically shutdown in 1 minute
		try
		{
			Thread.sleep( 60000 );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		server.stopServer();
	}
}