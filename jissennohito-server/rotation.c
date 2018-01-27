#include <stdio.h>
#include "rotation.h"

float getRotation(){
	float rotation = -810;
    printf("rotation:");
	scanf("%f", &rotation);
	printf("ok\n");
	//値を抽出(loop)
//	while(rotation == -810){
//      printf("rotation:");
//		scanf("%f", &rotation);
//		printf("ok\n");
//	}
    return rotation;
}

