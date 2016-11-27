var turn_frequency = 200;
var fire_frequency = 200;
var charge_frequency = 100;
var player_is_dead = true;
var player;

function alienupdate(alien) {
	game_time = time.getTime();
	origin = alien.getProperty("origin");
	
	if(alien.getProperty("charging")==true)
		alien.physicsC.setSpeed(0,1);
	else
		{
		alien.physicsC.translate(origin + Math.abs(game_time%turn_frequency - turn_frequency/2),alien.getProperty("originY"));
		alien.physicsC.setSpeed(0,0);
		}
	
	if((game_time +origin*50) %fire_frequency  ==0){
		bullet_factory.SpawnBullet(alien.physicsC.getX(),alien.physicsC.getY()+20, .5-Math.random(), 1, "alien_bullet", [255,0,0,255]);
	}
	if((game_time + origin*75)%charge_frequency ==0){
		alien.setProperty("charging",true);
	}
	if(alien.physicsC.getY()>300){
		print("end charge");
		alien.setProperty("charging", false);
	}
}

function on_collision(event){
	if(event.object1.getGameObject().alive&&event.object2.getGameObject().alive){
		if(event.object1.getGameObject().getProperty("tag")=="alien_bullet" && event.object2.getGameObject().getProperty("tag")=="player"){
			event.object1.getGameObject().kill();
			event.object2.getGameObject().kill();
			player_is_dead = true;
		}
		else if(event.object1.getGameObject().getProperty("tag")=="player_bullet" && event.object2.getGameObject().getProperty("tag")=="alien"){
				event.object1.getGameObject().kill();
				event.object2.getGameObject().kill();
			Game.aliens_alive--;
			if(Game.aliens_alive==0){
				player.destroy();
				Game.initiate();
				on_spawn(null);
			}
		}
		else if (event.object1.getGameObject().getProperty("tag")=="player" && event.object2.getGameObject().getProperty("tag")=="alien"){
			player_is_dead = true;
			event.object1.getGameObject().kill();
		}
	}
}

function on_spawn(event){
	player = player_object_factory.create(100,200,1);
	player_is_dead = false;
}

function initiate(){
	Game.initiate();
	on_spawn(null);
}

function on_input(event){
	if(event.command == "no_op")
		return;
	if(player_is_dead){
		player_is_dead==true;
		on_spawn(null);
		return;
	}
	if(event.command != "no_op"){
		if(event.command=="left"){
			player.physicsC.addForceLeftRight(-1);
			print("left");
			return;
		}
		if(event.command=="right"){
			player.physicsC.addForceLeftRight(1);
			print("right");
			return;
		}
		if(event.command=="jump"||event.command=="up"){
			print("fire!");
			bullet_factory.SpawnBullet(player.physicsC.getX()+6, player.physicsC.getY()-5, 0, -1, "player_bullet", [0,0,255,255]);
			return;
		}
		print("Command not found");
	}
}