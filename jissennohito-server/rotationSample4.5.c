#include <stdio.h>
#include <stdlib.h>
#include <wiringPi.h>
#include "rotation.h"

#define LED1 21

void gpiosetup(){
 if(wiringPiSetupGpio() == -1)exit(1);
}

void blink(int gpionum){
 digitalWrite(gpionum, 0);
 delay(1000);
 digitalWrite(gpionum, 1);
 delay(1000);
}

int main(int argc, char* argv[]){
	float rotation = -810;
	gpiosetup();
    pinMode(LED1, OUTPUT);
	while(rotation < 180){
		blink(LED1);
        rotation = getRotation();
	}
	return 0;
}
