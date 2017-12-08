#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_SIZE 256

struct LogPosition { int pos, len; };
typedef struct LogPosition LogPosition;

char* getLogMessage(const char* filename, int maxLine)
{
    LogPosition* lp = (LogPosition*)calloc(maxLine,sizeof(LogPosition));
    FILE* fp = fopen(filename,"r"); if ( fp == NULL ) return NULL;
    int linei = 0, lineno = 0, retline = 0;
    char buffer[MAX_SIZE];
    while ( fgets(buffer,MAX_SIZE,fp) != NULL ) lineno ++;
    rewind(fp);
    long logLength = 0;
    while ( fgets(buffer,MAX_SIZE,fp) != NULL ) {
        if ( (lineno-linei-1) < maxLine ) {
            int buflen = strlen(buffer);
            lp[lineno-linei-1].pos = ftell(fp) - buflen; lp[lineno-linei-1].len = buflen;
            logLength += buflen;
            retline ++;
        }
        linei ++;
    }
    rewind(fp);
    char* message = (char*)calloc(logLength + 1,sizeof(char));
    logLength = 0;
    for ( int i=0; i<retline; i++ ) {
        fseek(fp,lp[i].pos,SEEK_SET); fread(message + logLength,1,lp[i].len,fp); logLength += lp[i].len;
    }
    fclose(fp);
    free(lp);
    return message;
}

float getRotation(){
	char* str = getLogMessage("/tmp/output.ikisugi", 1);
	return atof(str);
}

int main(int argc, char* argv[])
{
    printf("%f\n",getRotation());
    return 0;
}
