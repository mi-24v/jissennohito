#include <stdio.h>
#include "rotation.h"

int main(int argc, char* argv[])
{
	initProcess();
    printf("%f\n",getRotation());
    closeProcess();
    return 0;
}
