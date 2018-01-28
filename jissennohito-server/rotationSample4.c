#include <stdio.h>
#include "rotation.h"
#include "nctrb_mechctrl_io.h"

#define ROTATE_CCW 0b01010101

int main(int argc, char* argv[]){
	float rotation = -810;
	nctrb_mc_io_initialize();
	while(rotation < 180){
		Wheel(ROTATE_CCW);
		rotation = getRotation();
	}
	Wheel(0b11111111);
	return 0;
}
