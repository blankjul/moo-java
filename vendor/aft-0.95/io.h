/*************************************************************************

 I/O functions

 ---------------------------------------------------------------------

    Copyright (c) 2005-2011
            Carlos M. Fonseca <cmfonsec@dei.uc.pt>
            Manuel Lopez-Ibanez <manuel.lopez-ibanez@ulb.ac.be>
            Luis Paquete <paquete@dei.uc.pt>
    Copyright (c) 2009-2011
            Andreia Guerreiro <andreia.guerreiro@ist.utl.pt>

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, you can obtain a copy of the GNU
 General Public License at:
                 http://www.gnu.org/copyleft/gpl.html
 or by writing to:
           Free Software Foundation, Inc., 59 Temple Place,
                 Suite 330, Boston, MA 02111-1307 USA

 ----------------------------------------------------------------------

*************************************************************************/

#ifndef _HV_IO_H_
#define _HV_IO_H_

#include <stdlib.h>
#include <stdio.h>
#include <stdarg.h>

#define PAGE_SIZE 4096
#define DOUBLE_DATA_INC (PAGE_SIZE/sizeof(double))
#define INT_DATA_INC (PAGE_SIZE/sizeof(int))
#define SIZES_INC (PAGE_SIZE/sizeof(int))

//#define point_printf_format "%-16.15g"
#define point_printf_format "%.16g"

/* If we're not using GNU C, elide __attribute__ */
#ifndef __GNUC__
#  define  __attribute__(x)  /* NOTHING */
#endif

void 
errprintf(const char * template,...) 
/* enables the compiler to check the format string against the
   parameters */  __attribute__ ((format(printf, 1, 2)));

void warnprintf(const char *template,...)
/* enables the compiler to check the format string against the
   parameters */  __attribute__ ((format(printf, 1, 2)));

/* Error codes for read_data.  */
#define READ_INPUT_FILE_EMPTY -1
#define READ_INPUT_WRONG_INITIAL_DIM -2

int
read_data (const char *filename, double **data_p, 
           int *nobjs_p, int **cumsizes_p, int *nsets_p);


int input_int(FILE *infile, int **datap, int *nvarsp, int **cumsizesp, int *nrunsp);

#endif
