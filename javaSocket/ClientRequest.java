package javaSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientRequest {
	String server;
	String path;
	int serverPort;
	public ClientRequest(String _server, String _path, int _serverPort)
	{
		this.server = _server;
		this.path = _path;
		this.serverPort = _serverPort;
	}

	public void makeDemoRequest() {
		System.out.println( "Loading contents of URL: " + server + ":" + serverPort );
		try
		{
			// Connect to the server
			Socket socket = new Socket( server, serverPort );

			// Create input and output streams to read from and write to the server
			PrintStream out = new PrintStream( socket.getOutputStream() );	//Send data
			BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );	//Receive Data

			// Follow the HTTP protocol of GET <path> HTTP/1.0 followed by an empty line
			out.println( "GET " + path + " HTTP/1.000-> Abrar" );
			out.println( "Next Line" );
			out.println( "Next Next Line" );
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


	public void makeMultiLineRequest() {
		//https://stackoverflow.com/questions/42047834/java-client-server-sending-multiple-strings-over-a-socket-connection
		try
        {
            String host = server;
            int port = serverPort;
            InetAddress address = InetAddress.getByName(host);
            Socket socket = new Socket(address, port);

            //Send the message to the server
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            String list = "LISTALL";

            String sendMessage = list + "\n";
            bw.write(sendMessage);
            bw.flush();
            System.out.println("Message sent to the server : "+sendMessage);

            //Get the return message from the server
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            while (br.readLine() != null) {         
				String message = br.readLine();
				System.out.println("Message received from the server : " +message);
			}
			try
			{
				socket.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            //Closing the socket
            System.out.println("Request Ended");
        }
	}
}