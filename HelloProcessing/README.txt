README

In this zip is a runnable called Game.jar which opens a server and three clients.
Alternatively, this is the archive of an eclipse Java project. To compile, open Eclipse. On the menu bar, select File->Import...
Under the General category find Existing Project into Workspace. Select it and click Next >.
Click on the radio button for Select root directory:
Click Browse...
Find the extracted HelloProcessing directory in your file system.
Select the directory and click Ok.
Make sure HelloProcessing is checked under Projects:
Click Finish.
Right click on HelloProcessing in the Project Explorer View and select Run As->Java Application
In this menu, double click the following items for the given sections:

  HW2Test - hw2 is what I use to quickly test the game. It has a main method that opens a server and opens three clients to
     connect to it.
     To play, spread the four processing windows out, focus a client (grey background) and use A and D keys to move the corresponding 
     player game object left and right. Space to jump.
     
	
To open clients and servers separately,
	Server: ServerMain - hw2
	Client: ClientMain - hw2
	
TO PLAY:
	 Focus the client (grey background) and use A and D keys to move the corresponding 
     player game object left and right. Space to jump.

TO TEST NETWORKING PROTOCOL:
	SerializableSendingTest - hw2.networkTest.minimalist
	The current configuration is: 
		Message Type: Strings
		Clients: 2
		Game Objects: 500
		Update Iterations: 1000
	but these can easily be changed by modifying the constants at the top of this class file.