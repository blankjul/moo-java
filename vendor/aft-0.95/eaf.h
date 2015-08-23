/*************************************************************************

 eaf: Compute the empirical attainment function from a sequence of
        non-dominated point sets

 ---------------------------------------------------------------------

    Copyright (c) 2005
            Carlos M. Fonseca <cmfonsec@ualg.pt>
    Copyright (c) 2009-2011
            Andreia Guerreiro <andreia.guerreiro@ist.utl.pt>
            Carlos M. Fonseca <cmfonsec@dei.uc.pt>
            Luis Paquete <paquete@dei.uc.pt>
            Manuel Lopez-Ibanez <manuel.lopez-ibanez@ulb.ac.be>

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

#ifndef ATTAIN_H_
#define ATTAIN_H_

#include "avl.h"
#include <stdio.h>

/* EAF 3D functions */
avl_tree_t ** eaf3d(double *data, int dim, int *sets, int nset);
int printoutput(avl_tree_t **level, int nset, int d, FILE **outfile, int noutfiles, FILE **outfileindic, int noutfilesi, int * attlevel, int nlevels);
void printindic(avl_tree_t ** levels, int nruns, FILE ** indicfile, int nfiles, int * attlevel, int nlevels);
void freeoutput(avl_tree_t **output, int nset);



/* EAF 2D functions */
int eaf2d(double *data,   /* the objective vectors         */
        int nobj,       /* the number of objectives      */
        int *cumsize,   /* the cumulative sizes of the runs */
        int nruns,      /* the number of runs            */
        int *attlevel,  /* the desired attainment levels */
        int nlevels,    /* the number of att levels      */
        FILE **coord,   /* output files (coordinates)    */
        FILE **indic,       /* output files (attainment indicators) */
        int noutfiles,   /* number of output files (coordinates)     */
        int noutfilesi   /* number of output files (attainment indicators) */
);

//int input_double(FILE *infile, double **datap, int *nvarsp, int **cumsizesp, int *nrunsp);

int eaf_ks_stat(int *data, int nvars, int npoints);
int eaf2_ks_stat(int *data, int nvars, int npoints);

int *eaf_ks_tail(int *data, int nvars, int npoints, double alpha, int nperms);
int *eaf2_ks_tail(int *data, int nvars, int npoints, double alpha, int nperms);

#if __GNUC__ >= 3
# define __unused	__attribute__ ((unused))
#else
# define __unused	/* no 'unused' attribute available */
#endif


#endif
