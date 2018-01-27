#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <regex.h>
#include <err.h>

#define MAX_SIZE 256

void compileregex(char* pattern, regex_t* regex){
	/* Compile regular expression */
	int rc;
	char buffer[100];
	if(0 != (rc = regcomp(regex, pattern, REG_EXTENDED))) {
		fprintf(stderr, "Could not compile regex\n");
		regerror(rc, regex, buffer, 100);
		exit(1);
	}
	return;
}

unsigned char tryregmatch(regex_t* regex, char* str){
	int reti;
	char msgbuf[100];


	/* Execute regular expression */
	reti = regexec(regex, str, 0, NULL, 0);
	if (!reti) {
		    //puts("Match");
		    return 0;
	}
	else if (reti == REG_NOMATCH) {
		    //puts("No match");
		    return 1;
	}
	else {
		    regerror(reti, regex, msgbuf, sizeof(msgbuf));
		    fprintf(stderr, "Regex match failed: %s\n", msgbuf);
		    //exit(1);
		    return 1;
	}
}

float getRotation(FILE *process, regex_t* regex){
	char* str;
	char buf[MAX_SIZE];
	unsigned char ismatch = 1;
	//値を抽出
	while(ismatch != 0){
		str = fgets(buf, MAX_SIZE, process);
		ismatch = tryregmatch(regex ,str);
	}
	return atof(str);
}

int main(int argc, char* argv[])
{
	FILE* process;
	regex_t regex;
	compileregex("^\\d{1,3}\\.\\d{6}", &regex);
	if( (process=popen("./host.py","r")) == NULL){
		perror("could not execute host program");
		exit(1);
	}
    printf("%f\n",getRotation(process, &regex));
    //pclose(process);
    return 0;
}
