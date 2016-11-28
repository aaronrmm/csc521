var turn_frequency = 200;
var fire_frequency = 200;
var charge_frequency = 100;
var player_is_dead = true;
var player;

function update_platform(platform) {
	game_time = time.getTime();
	origin = platform.getProperty("originX");
	seed = platform.getProperty("seed");
	platform.physicsC.translate(origin + Math.abs((game_time + seed)%turn_frequency - turn_frequency/2),platform.getProperty("originY"));
	platform.physicsC.setSpeed(0,0);
}

function on_collision(event){
	if(event.object1.getGameObject().entityClass=="PLAYER" && event.object2.getGameObject().entityClass=="KILLZONE"){
		event.object1.getGameObject().kill();
	}
}