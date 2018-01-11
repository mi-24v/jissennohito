#include <stdio.h>
#include <stdlib.h>

FILE* process;

int initProcess(){
	if( (process=popen("./host.py","r")) == NULL){
		perror("could not execute host program");
		return -1;
	}else{
		return 0;
	}
}

float getRotation(){
	float data = 0;
	char *buf;
	//値を抽出
	while(data != 0){
		fgets(buf, 20, process);
		data = atof(buf);
	}
	return data;
}

void closeProcess(){
	pclose(process);
}
