package javaSocket;

public class SocketClient
{
	public static void main( String[] args ) throws InterruptedException
	{
		String server;
		String filePath;
		int serverPort;
		String userNam;
		String password;
		if( args.length < 5 )	//Default values
		{
			System.out.println( "Usage: SimpleSocketClientExample <server> <path>; using default server=localhost, port 8080" );
			//System.exit( 0 );
			server = "localhost";
			filePath = "D://Education//Academic//Distributed Computing//Assignments//2//java Code//javaSocket//javaSocket//try.txt";
			serverPort = 8080;
			userNam = "a";
			password = "1";
		}
		else
		{
			server = args[ 0 ];
			serverPort = Integer.parseInt(args[1]);
			filePath = args[ 2 ];
			userNam = args[ 3 ];
			password = args[ 4 ];
		}
		int successCount = 0;
		ClientRequest clientRequest = new ClientRequest(server, serverPort);
		//clientRequest.makeDemoRequest();
		for(int i=0; i<1000; i++)
		{
			Thread.sleep(5);
			boolean requestStatus = clientRequest.makeFileSendRequest(userNam, password, filePath);
			if(requestStatus)
			{
				successCount+=1;
			}
			if(i%100==0)
			{
				System.out.println("Success count out of "+i+","+successCount);
			}
		}
	}
}
