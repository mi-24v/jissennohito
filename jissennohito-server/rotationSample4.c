#include <stdio.h>
#include "nctrb_mechctrl_io.h"

#define ROTATE_CCW 0b01010101

int main(int argc, char* argv[]){
	float rotation = -810;
	nctrb_mc_io_initialize();
	while(rotation < 0){
		Wheel(ROTATE_CCW);
		printf("rotation:");
		scanf("%f", &rotation);
        printf("ok");
	}
	Wheel(0b11111111);
	return 0;
}
