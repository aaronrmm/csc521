README

In this zip are three runnable jars, one for each game. There is also a client runnable jar.
 This same client jar can be run with any of the games once the game server has started.


Alternatively, this is the archive of an eclipse Java project. To compile, open Eclipse. On the menu bar, select File->Import...
Under the General category find Existing Project into Workspace. Select it and click Next >.
Click on the radio button for Select root directory:
Click Browse...
Find the extracted HelloProcessing directory in your file system.
Select the directory and click Ok.
Make sure HelloProcessing is checked under Projects:
Click Finish.
Right click on HelloProcessing in the Project Explorer View and select Run As->Java Application
In this menu, double click the following items for the given games:

	Main - games.platforms
	Main - games.spaceinvaders
	Main - games.snake
	
To open clients
	
	HW3ClientMain - hw3
	
TO PLAY Platforms: 
Start the games.platforms.Main main server class. Then start as many clients as desired (hw3.HW3ClientMain.java)
Focus a client and use A and D keys to move the corresponding 
     player game object left and right. Space to jump.
	
TO PLAY SPACE INVADERS:
Start the games.spaceinvaders.Main main server class. Then start a client. Note for debug purposes, inputting commands
into the server has the same effect as inputting them into the client.
Use A and D keys to move the player ship left and right. Use W or space to fire.

TO PLAY SNAKE:
Start the games.snake.Main main server class. Then start a client. Note for debug purposes, inputting commands
into the server has the same effect as inputting them into the client.
Use W A S and D keys to move the snake up, left, down, or right.
