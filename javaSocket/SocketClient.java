package javaSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SocketClient
{
	public static void main( String[] args )
	{
		String server;
		String path;
		int serverPort;
		if( args.length < 3 )
		{
			System.out.println( "Usage: SimpleSocketClientExample <server> <path>; using default server=localhost, port 8080" );
			//System.exit( 0 );
			server = "localhost";
			path = "/";
			serverPort = 8080;
		}
		else
		{
			server = args[ 0 ];
			path = args[ 1 ];
			serverPort = Integer.parseInt(args[2]);
		}

		System.out.println( "Loading contents of URL: " + server + ":" + serverPort );

		try
		{
			// Connect to the server
			Socket socket = new Socket( server, serverPort );

			// Create input and output streams to read from and write to the server
			PrintStream out = new PrintStream( socket.getOutputStream() );
			BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

			// Follow the HTTP protocol of GET <path> HTTP/1.0 followed by an empty line
			out.println( "GET " + path + " HTTP/1.0" );
			out.println();

			// Read data from the server until we finish reading the document
			String line = in.readLine();
			while( line != null )
			{
				System.out.println( line );
				line = in.readLine();
			}

			// Close our streams
			in.close();
			out.close();
			socket.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
}
