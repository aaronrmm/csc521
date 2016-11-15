var turn_frequency = 200;
var fire_frequency = 70;
var player_is_dead = true;

function alienupdate() {
	game_time = time.getTime();
	origin = game_object.getProperty("origin");
	game_object.physicsC.translate(origin + Math.abs(game_time%turn_frequency - turn_frequency/2),game_object.physicsC.getY());
	if((game_time +origin) %fire_frequency  ==0){
		bullet_factory.SpawnBullet(game_object.physicsC.getX(),game_object.physicsC.getY(), .5-Math.random(), 1, "bullet");
	}
}

function on_collision(event){
	if(event.object1.getGameObject().entityClass == "BULLET" && event.object2.getGameObject().entityClass == "PLAYER"){
		event.object1.getGameObject().destroy();
		event.object2.getGameObject().destroy();
		player_is_dead = true;
		print("player hit");
	}
}

function on_spawn(event){
	print("spawning");
	player_object_factory.create(100,100,1);
	player_is_dead = false;
}

function initiate(){
	on_spawn(null);
}