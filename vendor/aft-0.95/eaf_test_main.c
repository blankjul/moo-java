/*************************************************************************

 eaf-test: two-sided two-sample Kolmogorov-Smirnov-like hypothesis tests
           based on empirical first or second-order attainment functions
           (command-line interface)

 ---------------------------------------------------------------------

    Copyright (c) 2005, Carlos M. Fonseca <cmfonsec@ualg.pt>
    Copyright (c) 2011, Carlos M. Fonseca <cmfonsec@dei.uc.pt>
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

#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include "eaf.h"
#include "io.h"

#ifdef __USE_GNU
extern char *program_invocation_short_name;
#else
char *program_invocation_short_name;
#endif

int main(int argc, char *argv[])
{

    int *data = NULL;
    int* cumsizes = NULL;
    int nsets = 0, nruns = 0;
    int c, k;
    unsigned seed;
    int do2 = 0;
    int d1, d2, p1, p2, nperms = 10000, *tail1, *tail2;
    double alpha = .05;
    char *perm_str = NULL, *alpha_str = NULL, *seed_str = NULL;
    FILE *infile = stdin;
    FILE *outfile = stdout;

#ifndef __USE_GNU
    program_invocation_short_name = argv[0];
#endif
    
    /* Determine input and output files,
       and get list of attlevels in string format to decode later
       (we may need to read the data before we can determine nlevels) */
    while ((c = getopt(argc, argv, "a:p:o:s:2")) != -1) {
	switch (c) {
	    case 'p':	/* number of permutations to use */
		perm_str = optarg;
		break;
            case 'a':
                alpha_str = optarg;
                break;
            case 's':
                seed_str = optarg;
                break;
	    case 'o':
		if (strcmp(optarg,"-")==0)
			outfile = stdout;
		else
			outfile = fopen(optarg,"w");
		break;
            case '2':
                do2 = 1;
		break;
	    default:
		;
	}
/*	fprintf(stderr, "%c: %s\n", c, optarg); */
    }	

    if (optind < argc)
	for (k = optind; k < argc; k++)
	    if (strcmp(argv[k],"-")) {
		infile = fopen(argv[k],"r");
		if (infile)
		  input_int(infile, &data, &nruns, &cumsizes, &nsets);
                else {
                  fprintf(stderr, "ERROR: Cannot open input file '%s'\n", argv[k]);
                  exit(1);
                }  
		fclose(infile);
	    } else
		input_int(stdin, &data, &nruns, &cumsizes, &nsets);
    else
	input_int(stdin, &data, &nruns, &cumsizes, &nsets);

    if (alpha_str) {
        alpha = strtod(alpha_str, NULL);
    }
    if (perm_str) {
        nperms = strtoul(perm_str, NULL, 0);
    }
    if (seed_str) {
        seed = strtoul(seed_str, NULL, 0);
        fprintf(outfile, "Setting random seed to %u\n", seed);
        srand(seed);
    } else
        fprintf(outfile, "Using default random seed\n");

    fprintf(outfile, "Read %d rows, %d columns\n", cumsizes[nsets-1], nruns);
    fprintf(outfile, "Assuming 2 sets of %d runs each\n\n", nruns/2);

    if (!do2) {
        fprintf(outfile, "First-order EAF KS-like two-sample two-sided test\n");
        fprintf(outfile, "==================================================\n\n");
        d1 = eaf_ks_stat(data, nruns, cumsizes[nsets-1]);
        fprintf(outfile, "Test statistic = %d/%d\n", d1, nruns/2);
        fprintf(outfile, "               = %g\n\n", (2.0*d1)/nruns);
        if (nperms) {
            fprintf(outfile, "Using %d random permutations to estimate null distribution\n", nperms);
            fprintf(outfile, "Please be patient... ");
            fflush(outfile);
            tail1 = eaf_ks_tail(data, nruns, cumsizes[nsets-1], alpha, nperms);
            if (!tail1)
              return -1;
            fprintf(outfile, "done.\n\nCritical value = %d/%d\n", tail1[0], nruns/2);
            fprintf(outfile, "               = %g\n\n", (2.0*tail1[0])/nruns);
            if (d1 > tail1[0]) {
              p1 = 0;
              for (k = nruns/2; k >= d1; p1 += tail1[k--]);
              fprintf(outfile, "p-value = %g\n\n", ((double)p1)/nperms);
              fprintf(outfile, "Decision: REJECT the null hypothesis\n\n");
            } else {
              fprintf(outfile, "p-value > %g\n\n", alpha);
              fprintf(outfile, "Decision: do NOT REJECT the null hypothesis\n\n");
            }
            free(tail1);
        }
    } else {
        fprintf(outfile, "Second-order EAF KS-like two-sample two-sided test\n");
        fprintf(outfile, "==================================================\n\n");
        d2 = eaf2_ks_stat(data, nruns, cumsizes[nsets-1]);
        fprintf(outfile, "Test statistic = %d/%d\n", d2, nruns/2);
        fprintf(outfile, "               = %g\n\n", (2.0*d2)/nruns);
        if (nperms) {
            fprintf(outfile, "Using %d random permutations to estimate null distribution\n", nperms);
            fprintf(outfile, "Please be patient... ");
            fflush(outfile);
            tail2 = eaf2_ks_tail(data, nruns, cumsizes[nsets-1], alpha, nperms);
            if (!tail2)
              return -1;
            fprintf(outfile, "done.\n\nCritical value = %d/%d\n", tail2[0], nruns/2);
            fprintf(outfile, "               = %g\n\n", (2.0*tail2[0])/nruns);
            if (d2 > tail2[0]) {
              p2 = 0;
              for (k = nruns/2; k >= d2; p2 += tail2[k--])
                /* printf("%d\n", tail2[k]) */;
              fprintf(outfile, "p-value = %g\n\n", ((double)p2)/nperms);
              fprintf(outfile, "Decision: REJECT the null hypothesis\n\n");
            } else {
              fprintf(outfile, "p-value > %g\n\n", alpha);
              fprintf(outfile, "Decision: do NOT REJECT the null hypothesis\n\n");
            }
            free(tail2);
        }
    }

    free(data);
    free(cumsizes);

    return 0;
}
