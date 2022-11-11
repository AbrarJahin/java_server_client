package javaSocket;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;

public class ServerRequestHandler extends Thread
{
	private Socket socket;
	ServerRequestHandler( Socket socket )
	{
		this.socket = socket;
	}

	void sendDemoResponse()
	{
		try
		{
			System.out.println( "Received a connection" );

			// Get input and output streams
			BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			PrintWriter out = new PrintWriter( socket.getOutputStream() );

			// Write out our header to the client
			out.println( "Echo Server 1.0" );
			out.flush();

			// Echo lines back to the client until the client closes the connection or we receive an empty line
			String line = in.readLine();
			while( line != null && line.length() > 0 )
			{
				//out.println( "Echo: " + line );
				out.println( "Echo: " + new StringBuilder(line).reverse().toString() );
				out.flush();
				line = in.readLine();
			}

			// Close our connection
			in.close();
			out.close();
			socket.close();

			System.out.println( "Connection closed" );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		sendDemoResponse();
	}
}