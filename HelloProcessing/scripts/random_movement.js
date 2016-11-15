function update() {
	x = Math.floor((Math.random()*13)+1)-3;
	y = Math.floor((Math.random()*13)+1)-3;
	//print(game_object.physicsC);
	game_object.physicsC.translate(x,y);
	
}
