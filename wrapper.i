%module nctrb_mechctrl_io

%{
#define _NCTRB_MECHCTRL_IO_H

#define	BYTE 	unsigned char 
#define WORD 	unsigned short
#define sWORD	short

#include <stdio.h>
#include <stdlib.h>
#include <linux/i2c-dev.h>
#include <fcntl.h>
#include <string.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

#include <termios.h>
#include <signal.h>
#include <errno.h>
%}

void nctrb_mc_io_initialize(void);
BYTE nctrb_mc_io_readStartButton(void);
BYTE LineSensor(void);
void Wheel(BYTE value);
BYTE ReadWheelState(void);
WORD nctrb_mc_io_readPortIN1500(void);
void nctrb_mc_io_writePortOUT1500(WORD value);
int nctrb_si_io_readUltraSonicSensor(int selectSensor);
int Usonic(int sensor);
