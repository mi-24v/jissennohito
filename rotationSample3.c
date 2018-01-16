#include <stdio.h>
#include <pthread.h>
#include "nctrb_mechctrl_io.h"
#include "rotation.h"

#define ROTATE_CCW 0b01010101

rotationClient client;
pthread_t thread;

int main(int argc, char* argv[]){
	nctrb_mc_io_initialize();
	initProcess(&client);
	pthread_create(&thread, NULL, &setRotation, &client);
	//Wheel(ROTATE_CCW);
	sleep(1);
	while(client.rotation < 0){
		Wheel(ROTATE_CCW);
	}
	pthread_join(thread, NULL);
	closeProcess(&client);
	return 0;
}
