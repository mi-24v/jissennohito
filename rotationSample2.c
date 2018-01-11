#include <stdio.h>
#include "rotation.h"

int main(int argc, char* argv[])
{
	int i;
	initProcess();
	for(i=0;i<114514;i++){
    printf("%f\n",getRotation());
    }
    closeProcess();
    return 0;
}
