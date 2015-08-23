/*************************************************************************

 eaf: Compute the empirical attainment function from a sequence of
        non-dominated point sets (command-line interface)

 ---------------------------------------------------------------------

    Copyright (c) 2005
            Carlos M. Fonseca <cmfonsec@ualg.pt>
    Copyright (c) 2009-2011
            Andreia Guerreiro <andreia.guerreiro@ist.utl.pt>
            Carlos M. Fonseca <cmfonsec@dei.uc.pt>
            Manuel Lopez-Ibanez <manuel.lopez-ibanez@ulb.ac.be>
            Luis Paquete <paquete@dei.uc.pt>

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

#include "io.h"
#include "eaf.h"
#include "timer.h"
#include "avl.h"

#include <errno.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h> // for isspace()

#include <unistd.h>  // for getopt()
#include <getopt.h> // for getopt_long()

#if !defined(TRUE) || !defined(FALSE)
#define TRUE 1
#define FALSE 0
#endif


#ifdef __USE_GNU
extern char *program_invocation_short_name;
#else
char *program_invocation_short_name;
#endif
static const char const * stdin_name = "<stdin>";


static int verbose_flag = 1;
static char *file = NULL;
static char *fileindic = NULL;

void usage(void)
{
    printf("\n"
           "Usage: %s [OPTIONS] [FILE1 [FILE2 ...]]\n\n", program_invocation_short_name);

    printf(

"Options:\n\n"
" -h, --help            print this summary and exit.\n\n"
" -i, --indicator=FILE  write attainment indicator values to FILE. If FILE\n"
"                       includes \"%%d\", the indicator values corresponding\n"
"                       to each level will be written to a separate file, with\n"
"                       %%d replaced by the number of that level.\n"
"                       By default no indicator information is written.\n\n"
" -l --level=l1,..,ln   choose which attainment levels to output. Levels are\n"
"                       numbered from 1 to n, where n is the number of input\n"
"                       sets. Level numbers should be separated by a comma.\n"
"                       Ranges may be specified as two level numbers separated\n"
"                       by a dash. Example: -l 1,2,4-6,9.\n"
"                       By default all levels are considered.\n\n"
" -o, --output=FILE     write output sets to FILE. Different sets are separated\n"
"                       from each other by a blank line. If FILE includes \"%%d\",\n"
"                       each level will be written to a separate file, with\n"
"                       %%d replaced by the number of that level.\n"
"                       By default output is sent to stdout.\n\n"
" -q, --quiet           be quiet. Suppress all warnings.\n\n"
" -V, --version         print version number and exit.\n\n"
" -v, --verbose         print additional information (running time, etc).\n\n"
);

}

void version(void)
{
    printf("%s version %s",
           program_invocation_short_name, VERSION);
    if(strcmp(ARCH,"") != 0)
        printf(" (optimised for %s)", ARCH);
    printf("\n\n"
"Copyright (C) 2011"
"\nAndreia P. Guerreiro <andreia.guerreiro@ist.utl.pt>"
"\nCarlos M. Fonseca <cmfonsec@dei.uc.pt>"
"\nManuel Lopez-Ibanez <manuel.lopez-ibanez@ulb.ac.be>"
"\nLuis Paquete <paquete@dei.uc.pt>\n"
"\n"
"This is free software, and you are welcome to redistribute it under certain\n"
"conditions.  See the GNU General Public License for details. There is NO   \n"
"warranty; not even for MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.\n"
"\n");
}

static inline void 
vector_printf (const double *vector, int size)
{
    int k;
    for (k = 0; k < size; k++) 
        printf (" %f", vector[k]);
}



static inline void
handle_read_data_error (int err, const char *filename)
{
    switch (err) {
    case 0: /* No error */
        break;

    case READ_INPUT_FILE_EMPTY:
        errprintf ("%s: no input data.", filename);
        exit (EXIT_FAILURE);

    default:
        exit (EXIT_FAILURE);
    }
}

/*
 * Returns the number of each level to be printed or -1 if the format is incorrect
 */
static int str2ints(char * str, int * attlevel, int nruns){
    int strsz = strlen(str);
    char ch;
    char num[2];
    num[1] = '\0';
    int index = 0;
    int n = 0, nprev;
    int hyphen = 0;
    int i, k;
    for(i = 0; i < nruns; i++)
        attlevel[i] = 0;
    
    for(i = 0; i < strsz; i++){
        ch = str[i];
        if(ch >= '0' && ch <= '9'){
            num[0] = ch;
            n = n * 10 + atoi(num);
            //hyphen = 0;
        }else if(ch == ','){
            if(hyphen == 1 && ( n == 0  || n < nprev)){
                return -1;
            }
            
            if(hyphen == 0){
               attlevel[index++] = n;
               
            }else{
                /*if(n <= nprev){
                   return -1;
                }*/
                
                for(k = nprev; k <= n; k++){
                    attlevel[index++] = k;
                }
                
                hyphen = 0;
            }
            n = 0;
            
        }else if(ch == '-'){
            if(hyphen == 1 || n == 0){
                return -1;
            }
            hyphen = 1;
            nprev = n;
            n = 0;
            
        }else{
            return -1;
        }
    }
    
     if(hyphen == 0){
        attlevel[index++] = n;
    }else{
        if(n == 0 || n < nprev){
            return -1;
        }
        for(k = nprev; k <= n; k++){
            attlevel[index++] = k;
        }
    }
    
    for(i = 0; i < index; i++)
        if(attlevel[i] > nruns || attlevel[i] == 0)
            return -1;
    return index;
}


/*
 * Returns 0 if "%d" isn't included, otherwise returns the minimum
 * size of the variable part. (if contains %d, the minimum is 1, if
 * it contains %02d, the minimum is 2 (can't be more than 10).
 */
static int verify_filename(char *filenm){
    int percentage = 0;
    int size = strlen(filenm);
    int i;
    int n = 0;
    char ch[2];
    ch[1] = '\0';
    
    for(i = 0; i < size; i++){
        if(filenm[i] == '%'){
            if(percentage > 0){
                errprintf ("Invalid name: %s", filenm);
                exit (EXIT_FAILURE);
            }
            percentage = 1;
        }else if(percentage == 1){
            if(filenm[i] == 'd'){
                percentage = 2;
                n++;
            }else if(filenm[i] >= '0' && filenm[i] <= '9'){
                ch[0] = filenm[i];
                n = n*10 + atoi(ch);
            
            }else{
                errprintf ("Invalid name: %s", filenm);
                exit (EXIT_FAILURE);   
            }
        }
    }

    
    if(percentage == 0)
        return 0;
    if(percentage == 2 && n <= 11)
        return n;
    
    errprintf ("Invalid name: %s", filenm);
    exit (EXIT_FAILURE);   
    
}




/*
 * larg - -l option argument
 * Returns the total number of wanted levels.
 * In attlevel it will be saved each level to be printed.
 */
static int set_attlevels(int * attlevel, char *larg, int nruns){
    
    int k;
    int nlevels;
    
    
    if(larg == NULL){
        for(k = 0; k < nruns; k++)
            attlevel[k] = k+1;
        nlevels = nruns;
    }else{
        nlevels = str2ints(larg, attlevel, nruns);

        if(nlevels < 0){
            errprintf ("Invalid argument: \"%s\"", larg);
            exit (EXIT_FAILURE);    
        }
        
    }
    
    return nlevels;
}
/*
 * Opens the necessary output files.
 * Type: 0 - Output file, 1 - Indicator file, 2 - Output and Indicator file are the same
 */
static void set_output_files(char * inputfile, FILE ** outfile, int nfiles, int * attlevel, int nlevels, int minfilenamesz, char *larg, int type){
    
    char * outfilename;
    if(minfilenamesz)
        outfilename = (char *) malloc((strlen(inputfile)+minfilenamesz+12) * sizeof(char));
    else
        outfilename = (char *) malloc((strlen(inputfile)+1) * sizeof(char));

    int i;
    
    for(i = 0; i < nfiles; i++){
        if(minfilenamesz){
            sprintf(outfilename, inputfile, attlevel[i]);
            if(verbose_flag >= 2){
                switch(type){
                    case 0: fprintf(stdout, "# Output file"); break;
                    case 1: fprintf(stdout, "# Indicator file"); break;
                    case 2: fprintf(stdout, "# Output and indicator file"); break; 
                }
                fprintf(stdout, " (level %d): %s\n", attlevel[i], outfilename);
            }
        
        }else{
            sprintf(outfilename, inputfile);
            if(verbose_flag >= 2){
                switch(type){
                    case 0: fprintf(stdout, "# Output file"); break;
                    case 1: fprintf(stdout, "# Indicator file"); break;
                    case 2: fprintf(stdout, "# Output and indicator file"); break;
                }
                if(larg != NULL)
                    fprintf(stdout, " (levels %s)", larg);
                fprintf(stdout, ": %s\n", outfilename);
            }
        }
        outfile[i] = fopen (outfilename, "w");
        if (outfile == NULL) {
            errprintf ("%s: %s\n", outfilename, strerror(errno));
            exit (EXIT_FAILURE);
        }
    }
    
    free(outfilename);
}


/* 
   FILENAME: input filename. If NULL, read stdin.

*/
void 
eaf_file (const char **filename, int nInputFiles, char * larg)
{
    double *data = NULL;
    int *cumsizes = NULL;
    int nruns = 0;
    int nobj = 0;
    FILE **outfile;
    FILE **outfileindic = NULL;
    int nfiles = 1;
    int nfilesi = 1;
    avl_tree_t ** output;
    int i, set, totalinpp = 0; //total number of input points
    
    int minfilenamesz = 0;
    int minfilenameszi = 0;
    
    
    if(file){
        minfilenamesz = verify_filename(file);
    }
    if(fileindic){
        minfilenameszi = verify_filename(fileindic);
    }

    int err;
    
    if (!filename){
        filename = (const char **) malloc(sizeof(const char *));
        filename[0] = NULL;
        nInputFiles = 1;
    }
    
    set = 0;
    for(i = 0; i < nInputFiles; i++){
        if (verbose_flag >= 2){
            fprintf(stdout, "# Input file: %s\n", filename[i]);
        }
        
        err = read_data (filename[i], &data, &nobj, &cumsizes, &nruns);
        if (!filename) filename[i] = stdin_name;
        handle_read_data_error (err, filename[i]);
        
        
        for(; set < nruns; set++){
            if (verbose_flag >= 2){
                fprintf(stdout, "#   Set %d: %d points\n", (set+1), cumsizes[set]);
            }
            totalinpp += cumsizes[set];
        }
    }
    
    if (verbose_flag){
        if(verbose_flag >= 2)
            fprintf(stdout, "#\n");
        fprintf(stdout, "# %d input sets, %d points in total\n", nruns, totalinpp);
    }
    
    int * attlevel = (int *) malloc (nruns * sizeof(int));
    int nlevels = set_attlevels(attlevel, larg, nruns);

    
    if (file == NULL){
        if(fileindic != NULL){
            outfile = NULL;
        }else{
            outfile = (FILE **) malloc (sizeof(FILE *));
            outfile[0] = stdout;
        }    
    }else if(strcmp(file, "-") == 0){
        outfile = (FILE **) malloc (sizeof(FILE *));
        outfile[0] = stdout;
    }else{
        nfiles = (minfilenamesz) ? nlevels : 1;
        outfile = (FILE **) malloc (nfiles * sizeof(FILE *));

        if(verbose_flag >= 2)
            fprintf(stdout, "#\n");
        int equalfiles = (fileindic != NULL && strcmp(file, fileindic) == 0) ? 2 : 0;
        set_output_files(file, outfile, nfiles, attlevel, nlevels, minfilenamesz, larg, equalfiles);

    }
    
    if(fileindic != NULL){
        if(strcmp(fileindic, "-") == 0){
            outfileindic = (FILE **) malloc (sizeof(FILE *));
            outfileindic[0] = stdout;
        }else{
            
            if(file != NULL && strcmp(file, fileindic) == 0){
                minfilenameszi = minfilenamesz;
                nfilesi = nfiles;
                
                outfileindic = (FILE **) malloc (nfilesi * sizeof(FILE *));
                for(i = 0; i < nfilesi; i++){
                    outfileindic[i] = outfile[i];
                }
            }else{
                if(verbose_flag >= 2 && (file == NULL || strcmp(file, "-") == 0))
                    fprintf(stdout, "#\n");
                nfilesi = (minfilenameszi) ? nlevels : 1;
                outfileindic = (FILE **) malloc (nfilesi * sizeof(FILE *));
                set_output_files(fileindic, outfileindic, nfilesi, attlevel, nlevels, minfilenameszi, larg, 1);
            }
        }            
    }

    if(verbose_flag >= 2){
        fprintf(stdout, "#\n# Computing EAF...\n");
    }
    

    double time_elapsed = 0;
    int totalp = 0;

    Timer_start ();
    switch(nobj){
        case 2:
            
            for(i = 1; i < nruns; i++)
                cumsizes[i] += cumsizes[i-1]; 
            
            totalp = eaf2d(data, nobj, cumsizes, nruns, attlevel, nlevels, outfile, outfileindic, nfiles, nfilesi);
            time_elapsed = Timer_elapsed_virtual ();
            if(totalp < 0)
                exit(EXIT_FAILURE);
            break;
            
        case 3: 
            output = eaf3d(data, nobj, cumsizes, nruns); //nruns - number of sets
                        //cumsizes - array with nruns elements with the size of every set
            time_elapsed = Timer_elapsed_virtual ();
            
            totalp = printoutput(output, nruns, nobj, outfile, nfiles, outfileindic, nfilesi, attlevel, nlevels);
          
            freeoutput(output, nruns);
            
            break;
        case 4: printf("The computation of the EAF for 4 dimensions is not implemented yet\n");
            break;
        default:
            printf("There is not an implementation to compute the EAF for %d dimensions\n", nobj);
    }
    

    if(outfile != NULL){
        if (outfile[0] != stdout) {
            for(i = 0; i < nfiles; i++){
                fclose (outfile[i]);
            }
        }
        free (outfile);
    }
    
    if(outfileindic != NULL){
        if(outfileindic[0] != stdout){
            for(i = 0; i < nfilesi; i++){
                if(file == NULL || strcmp(file, fileindic) != 0)
                    fclose (outfileindic[i]);
            }
        }
        free(outfileindic);
    }
    
    if(verbose_flag){
        fprintf(stdout, "# %d output points, done.\n", totalp);
        if (verbose_flag >= 2)
            fprintf(stdout, "# Time: %f seconds\n", time_elapsed);
    }
  
    
    free (data);
    free (cumsizes);
    free(attlevel);
    free(filename);

}


int main(int argc, char *argv[])
{
    int numfiles, k;

    int opt; /* it's actually going to hold a char.  */
    int longopt_index;
    char *larg = NULL;
    /* see the man page for getopt_long for an explanation of these fields.  */
    static struct option long_options[] = {
        {"help",       no_argument,       NULL, 'h'},
        {"version",    no_argument,       NULL, 'V'},
        {"verbose",    no_argument,       NULL, 'v'},
        {"quiet",      no_argument,       NULL, 'q'},
        {"output",     required_argument, NULL, 'o'},
        {"level",      required_argument, NULL, 'l'},
        {"indicator",      required_argument, NULL, 'i'},

        {NULL, 0, NULL, 0} /* marks end of list */
    };

#ifndef __USE_GNU
    program_invocation_short_name = argv[0];
#endif
    
    while (0 < (opt = getopt_long (argc, argv, "hvVqo:l:i:",
                                   long_options, &longopt_index))) {
        switch (opt) {
        case 'i':
            fileindic = optarg;
            break;
        
        case 'o':
            file = optarg;
            break;
        
        case 'l':
            larg = optarg;
            break;

        case 'V': // --version
            version();
            exit(EXIT_SUCCESS);


        case 'v': // --verbose
            verbose_flag = 2;
            break;

        case '?':
            // getopt prints an error message right here
            fprintf (stderr, "Try `%s --help' for more information.\n",
                     program_invocation_short_name);
            exit(EXIT_FAILURE);
        case 'h':
            usage();
            exit(EXIT_SUCCESS);
            
        case 'q':
            verbose_flag = 0;
            break;
        

        default: // should never happen
            abort();
        }
    }

    numfiles = argc - optind;
    const char ** filename = (const char **) malloc(numfiles * sizeof(const char *));
    for (k = 0; k < numfiles; k++) 
        filename[k] = argv[optind + k];
    
    if (numfiles < 1) /* Read stdin.  */
        eaf_file (NULL, 0, larg);

    else if (numfiles == 1) {
        eaf_file (filename, numfiles, larg);
    } 
    else {
        eaf_file (filename, numfiles, larg);
    }

    return EXIT_SUCCESS;
}
