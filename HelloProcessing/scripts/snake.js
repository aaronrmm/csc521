var apple_frequency = 50;
var apples_eaten = 0;
var apples = 0;
var snake_growth_rate = 10;
var snake_size = 10;
var direction = "up";
var next_direction = "up";
var snake_alive = false;
var snake_head;
var width = 300;
var height = 300;
var snake_links_first;
var snake_links_last;
var shift_delay = 10;
var time_of_last_shift = 0;
var grid_size = 10;
var snake_neck = { x:0, y:0 };

function update_snake() {
	if(snake_alive){
		if(time.getTime()-time_of_last_shift>shift_delay){
			time_of_last_shift = time.getTime();
			if(next_direction=="up")
				snake_head.physicsC.setSpeed(0,-1);
			else if (next_direction=="left")
				snake_head.physicsC.setSpeed(-1,0);
			else if (next_direction=="right")
				snake_head.physicsC.setSpeed(1,0);
			else if(next_direction=="down")
				snake_head.physicsC.setSpeed(0,1);
			direction = next_direction;
			
			snake_neck = snake_segment_factory.create(snake_head.physicsC.getX(), snake_head.physicsC.getY(),grid_size, grid_size, 1);
			snake_neck.setProperty("time_of_creation", time.getTime());
			snake_neck.setProperty("tag", "snake_segment");
		}

		if(snake_head.physicsC.getX()>300 ||
			snake_head.physicsC.getX()<0 ||
			snake_head.physicsC.getY()>300 ||
			snake_head.physicsC.getY()<0){
			kill_snake();
		}
	}
	else
		init();
}

function init(){
	print("spawning snake");
	snake_head = snake_head_factory.create(width/2,height/2, grid_size, grid_size,1);
	snake_neck = snake_segment_factory.create(width/2, height/2-grid_size, grid_size, grid_size, 1);
	snake_alive = true;
	if(apples==0)
		random_apple();
}

function random_apple(){
	apples++;
	x = Math.floor((Math.random()*296+2));
	y = Math.floor((Math.random()*296+2));
	apple = apple_factory.create(x, y, grid_size*2, grid_size*2, 0);
	apple.setProperty("tag", "apple");
}

function update_segment(segment){
	if(time.getTime() > segment.getProperty("time_of_creation")+ snake_size * shift_delay)
		segment.kill();
}

function on_collision(event){
	if(event.object1.getGameObject().alive&&event.object2.getGameObject().alive){
		if(event.object1.getGameObject()==snake_head && event.object2.getGameObject().getProperty("tag")=="snake_segment" && event.object2.getGameObject()!=snake_neck){
			kill_snake();
		}
		if(event.object1.getGameObject()==snake_head && event.object2.getGameObject().getProperty("tag")=="apple"){
			event.object2.getGameObject().kill();
			grow_snake();
			random_apple();
		}
	}
	//if snake with apple
}

function kill_snake(){
	snake_head.kill();
	snake_size = 1;
	init();
}

function on_input(event){
	if(event.command=="right"){
		if(direction!="left")
			next_direction = "right";
		print("right");
		return;
	}
	if(event.command=="left"){
		if(direction!="right")
			next_direction = "left";
		print("left");
		return;
	}
	if(event.command=="up"){
		if(direction!="down")
			next_direction = "up";
		print("up");
		return;
	}
	if(event.command=="down"){
		if(direction!="up")
			next_direction = "down";
		print("down");
		return;
	}
}

function grow_snake(){
	snake_size += snake_growth_rate;
}