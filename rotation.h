typedef struct rotationClient{
	FILE* process;
	float rotation;
}rotationClient;

int initProcess(rotationClient* client);
void setRotation(rotationClient* client);
void closeProcess(rotationClient* client);
