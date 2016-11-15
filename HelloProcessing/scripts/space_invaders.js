var turn_frequency = 200;
var fire_frequency = 30;

function alienupdate() {
	print(time);
	game_time = time.getTime();
	origin = game_object.getProperty("origin");
	game_object.physicsC.translate(origin + Math.abs(game_time%turn_frequency - turn_frequency/2),game_object.physicsC.getY());
	if(game_time%fire_frequency==0){
		bullet_factory.SpawnBullet(game_object.physicsC.getX(),game_object.physicsC.getY(), .5-Math.random(), 1, "bullet");
		print(bullet);
	}
}
