#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <regex.h>
#include <err.h>

#define MAX_SIZE 256

regex_t* compileregex(char* pattern){
	/* Compile regular expression */
	regex_t regex;
	if(regcomp(&regex, pattern, REG_EXTENDED)) {
		fprintf(stderr, "Could not compile regex\n");
		exit(1);
	}
	return &regex;
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
		    regerror(reti, &regex, msgbuf, sizeof(msgbuf));
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
		ismatch = tryregmatch(str);
	}
	return atof(str);
}

int main(int argc, char* argv[])
{
	FILE* process;
	regex_t* regex = compileregex("\d{2-3}.\d{6}");
	if( (process=popen("./host.py",r)) == NULL){
		perror("could not execute host program");
		exit(1);
	}
    printf("%f\n",getRotation(&process, regex));
    return 0;
}
