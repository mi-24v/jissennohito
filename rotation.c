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
	float data;
	//値を抽出
	while(fscanf(process, "%f\n", &data) != 0){}
	return data;
}

void closeProcess(){
	pclose(process);
}
