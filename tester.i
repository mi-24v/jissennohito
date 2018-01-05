%module jissen_main

%{
#include <stdio.h>
#include <unistd.h>
#include "nctrb_mechctrl_io.h"
%}

void Stopper(void);
BYTE GetLineSensor(int pos);
BYTE Get1500(int pos);
void Counter(void);
unsigned int Forward(int len);
unsigned int Back(int len);
unsigned int Right(int mode);
unsigned int Left(int mode  );
unsigned int RightBack(int mode);

unsigned int LeftBack(int mode);
void kaihi();

void biasF();
void biasR();
void biasL();
void  Rotate ();
