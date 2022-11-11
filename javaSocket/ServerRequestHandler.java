package javaSocket;

import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;

public class ServerRequestHandler extends Thread
{
	private Socket socket;
	static int connectionClock = 0;
	static int failNumber = 0;

	ServerRequestHandler( Socket socket )
	{
		this.socket = socket;
	}

	private String reverseString(String input)
	{
		return new StringBuilder(input).reverse().toString();
	}

	void sendLoggedInFileResponse()
	{
		connectionClock+=1;
		try
		{
			//System.out.println( "Received a connection" );

			// Get input and output streams
			BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			PrintWriter out = new PrintWriter( socket.getOutputStream() );

			// Write out our header to the client
			//out.println( "Echo Server 1.0" );

			out.flush();

			// Echo lines back to the client until the client closes the connection or we receive an empty line
			String line = in.readLine();
			int receiverIndex = 0;
			String userName="", password="";
			List<String> splittedStringList = new ArrayList<String>();
			boolean statusOfUpload = false;
			while( line != null && line.length() > 0 )
			{
				if(receiverIndex==0)	//Username
				{
					userName = line;
				}
				else if(receiverIndex==1)	//Username
				{
					password = line;
					UserLogin loginCheck = new UserLogin();
					boolean ifLoggedIn = loginCheck.checkLogin(userName, password);
					//System.out.println(ifLoggedIn ? "Log In Successful": "Log In Failed");
					if(!ifLoggedIn)
					{
						break;
					}
				}
				else	//It is file encrypted chank text
				{
					String decrypted = AES.decrypt(line);
					//System.out.println("Received Content : " + line);
					//System.out.println("Decrypted : " + decrypted);
					splittedStringList.add(decrypted);
				}
				//Read next one
				line = in.readLine();
				receiverIndex+=1;
				statusOfUpload = true;
			}

			//File can be saved in server from here, as we don't need, I am not saving that in here
			// FileHandler fileHandler = new FileHandler("");
			// String fileOutput = fileHandler.getTextFormStringList(splittedStringList);
			// System.out.println("File Content :\n" + fileOutput);
			out.println( "Server Clock Count: " + connectionClock );
			out.flush();
			out.println( "Username: " + userName );
			out.flush();
			out.println( "Reversed Password: " + reverseString(password) );
			out.flush();
			out.println( "File Received and uploaded with encryption: " + splittedStringList==null ? "Failed" :"Successful" );
			out.flush();
			out.println( "File Fully Uploaded: " + statusOfUpload==null ? "Failed" :"Successful" );
			out.flush();
			// Close our connection
			in.close();
			out.close();
			socket.close();

			//System.out.println( "Connection closed" );
		}
		catch( Exception e )
		{
			failNumber+=1;
			e.printStackTrace();
			try {
				socket.close();	//Prevent possible memory lick
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		try {
			String fileDir = "D://Education//Academic//Distributed Computing//Assignments//2//java Code//javaSocket//javaSocket//output.csv";
			String textToSave = Integer.toString(connectionClock) +
						"," + Integer.toString(failNumber) +
						"," + Integer.toString(connectionClock-failNumber).getBytes();
			Files.write(Paths.get(fileDir), textToSave.getBytes(), StandardOpenOption.APPEND);
			Files.write(Paths.get(fileDir), "\n".getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
			//exception handling left as an exercise for the reader
			//e.printStackTrace();
		}
		//Check If Bizentine failure or other failure
		if(FailingModel.IsFailed())
		{
			failNumber+=1;
		}
		if(connectionClock%100==0)
		{
			System.out.println("Fail count out of "+connectionClock+","+failNumber);
		}
	}

	void sendDemoResponse()
	{
		try
		{
			//System.out.println( "Received a connection" );

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

			//System.out.println( "Connection closed" );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		//sendDemoResponse();
		sendLoggedInFileResponse();
	}
}