#include <stdio.h>
#include <stdlib.h>
#include "rotation.h"

int initProcess(rotationClient* client){
	if( (client->process=popen("./host.py","r")) == NULL){
		perror("could not execute host program");
		return -1;
	}else{
		return 0;
	}
}

void setRotation(rotationClient* client){
	client->rotation = -810;
	char *buf;
	//値を抽出
	while(client->rotation == -810 || client->rotation != -1919){
		fgets(buf, 20, client->process);
		client->rotation = atof(buf);
		printf("%s\n",buf);
	}
}

void closeProcess(rotationClient* client){
	pclose(client->process);
}
