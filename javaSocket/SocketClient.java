package javaSocket;

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
		ClientRequest clientRequest = new ClientRequest(server, path, serverPort);
		clientRequest.makeDemoRequest();
	}
}
