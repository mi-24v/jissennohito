#include <stdio.h>
//#include <pthread.h>
#include <string.h>

#include "nctrb_mechctrl_io.h"
#include "pipe-rotation.h"

#define ROTATE_CCW 0b01010101

int main(int argc, char* argv[])
{
	float data = -810;
	char buf[255];
    int stat;
	int filediscriptor = fileno(stdin);
	//pthread_t thread;
	
	nctrb_mc_io_initialize();
	piper();
	//pthread_create(&thread, NULL, piper, &data);
	while(data < 0){
		Wheel(ROTATE_CCW);
		stat = read(filediscriptor, buf, 255);
		if (stat == -1) {
			perror("error");
			continue;
		}
		//printf("test\n");
		char* tok;
		tok = strtok(buf, "\n");
		while(tok != NULL){
			data = atof(stat);
			printf("%f\n",data);
			tok = strtok(NULL,"\n");
		}
		//fwrite(buf, sizeof(buf[0]), stat, stdout);
		fflush(stdout);
	}
	Wheel(0b11111111);
	//pthread_join(thread, NULL);
	return 0;
}
