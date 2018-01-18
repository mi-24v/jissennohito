#include <stdio.h>
#include <pthread.h>
#include "rotation.h"

int main(int argc, char* argv[])
{
	rotationClient client;
	int i;
	pthread_t thread;
	
	//initProcess(&client);
	pthread_create(&thread, NULL, setRotation, &client);
	for(i=0;i<114514;i++){
		printf("%f\n",client.rotation);
	}
	//closeProcess(&client);
	pthread_join(thread, NULL);
	return 0;
}
