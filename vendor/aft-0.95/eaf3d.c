/*************************************************************************

 eaf3d: Compute the empirical attainment function from a sequence of
        non-dominated point sets (three-objective case)

 ---------------------------------------------------------------------

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

#include "eaf.h"
#include "avl.h"
#include "io.h"

#include <stdlib.h>
#include <stdio.h>
#include <limits.h>
#include <math.h>
#include <stdbool.h>

#define max( a, b ) ( ((a) > (b)) ? (a) : (b) )

typedef struct dlnode {
    double *x;             /* The data vector */
    struct dlnode *next;
    struct dlnode *prev;
    int set;
} dlnode_t;

typedef struct removed_lst {
    avl_node_t *head;
} removed_l;

removed_l * rem_lst;

static inline bool avl_tree_is_empty (const avl_tree_t *avltree)
{
    return avltree->top == NULL;
}

static inline double *node_point(const avl_node_t *node)
{
    return (double*) node->item;
}


static int compare_node( const void *p1, const void* p2)
{
    const double x1 = *((*(const dlnode_t **)p1)->x);
    const double x2 = *((*(const dlnode_t **)p2)->x);

    if ( x1 == x2 )
        return 0;
    else
        return ( x1 < x2 ) ? -1 : 1;
}


/*
  Create a list of points, ordered by the third coordinate.
*/
static dlnode_t *
setup_cdllist(double *data, int d, int *sets, int nset)
{
    dlnode_t *head;
    dlnode_t **scratch;
    int i, j, k;

    int n = 0;
    for(i = 0; i < nset; i++) n += sets[i];

    head = malloc ((n+1) * sizeof(dlnode_t));

    head->x = data;
    
    head[0].set = 0;
    j = 0;
    k = 0;
    for (i = 1; i <= n; i++, j++) {
        if (j == sets[k]) {
            k++;
            j = 0;
        }
        head[i].set = k;
        // ->x points to the first coordinate of each point.
        head[i].x = head[i-1].x + d ;// this will be fixed a few lines below... 
        head[i].next = head[i-1].next;
        head[i].prev = head[i-1].prev;
    }
    head->x = NULL; // head contains no data 

    scratch = malloc(n * sizeof(dlnode_t*));
    for (i = 0; i < n; i++) 
        scratch[i] = head + i + 1; 

    // ->x points to the last coordinate of each point.
    for (i = 0; i < n; i++)
        scratch[i]->x--;
    
    // Sort according to the last coordinate.
    qsort(scratch, n, sizeof(dlnode_t*), compare_node);
    
    head->next = scratch[0];

    scratch[0]->prev = head;
    for (i = 1; i < n; i++) {
        scratch[i-1]->next = scratch[i];
        scratch[i]->prev = scratch[i-1];
    }
    scratch[n-1]->next = head;
    head->prev = scratch[n-1];
    
    // ->x points to the first coordinate.
    for (i = 0; i < n; i++)
        scratch[i]->x -= d-1;
    
    free(scratch);

    return head;
}


static int compare_tree_asc_x( const void *p1, const void *p2)
{
    const double x1= *((const double *)p1);
    const double x2= *((const double *)p2);

    if (x1 != x2)
        return (x1 < x2) ? -1 : 1;
    else
        return 0;
}


static int compare_tree_desc_y( const void *p1, const void *p2)
{
    const double x1= *((const double *)p1+1);
    const double x2= *((const double *)p2+1);

    if (x1 != x2)
        return (x1 > x2) ? -1 : 1;
    else
        return 0;
}

static int avl_search_closest_y(const avl_tree_t *avltree, const void *item, avl_node_t **avlnode) {
    avl_node_t *node;
    int c;

    if(!avlnode)
        avlnode = &node;

    node = avltree->top;

    if(!node)
        return *avlnode = NULL, 0;

    for(;;) {
        c = compare_tree_desc_y(item, node->item);
        if(c < 0) {
            if(node->left)
                node = node->left;
            else
                return *avlnode = node, -1;
        } else if(c > 0) {
            if(node->right)
                node = node->right;
            else
                return *avlnode = node, 1;
        } else {
            return *avlnode = node, 0;
        }
    }
}


static void find_all_promoters(avl_node_t * avlnode, int * dom_sets, int nruns){

    avl_node_t *node = avlnode;
    
    while(node != NULL){

        dom_sets[node->set] = 1;
        if(node->remover != NULL)
            find_all_promoters(node->remover, dom_sets, nruns);
        node = node->promoter;
    }
    
    node = avlnode->equal;
    
    while(node != NULL){

        dom_sets[node->set] = 1;
        node = node->equal;
    }
    
}

/*
Prints the list in the tree, ordered by the second dimension, from the highest value to the lowest.
The values printed are the values from dimension 0 to dimension dim of each point
*/
static void
printlist(avl_tree_t *avltree, int dim, FILE *outfile)
{
    avl_node_t *aux;
    aux = avltree->head;
    int i;

    double * val;
    while(aux){
        val = (double *)aux->item;
        //printf("-> ");
        for(i = 0; i < dim; i++){
            fprintf(outfile, point_printf_format "\t", val[i]);
        }
        fprintf(outfile, "\n");
        aux = aux->next;
    }
}


void printset(avl_tree_t **set, int nset){

    int i;
    printf("#sets\n----------------------\n");
    for(i = 0; i < nset; i++){
        if(set[i]->top != NULL){
            printf("set: %d", i);
            printlist(set[i], 3, stdout);
        }
    }


}

void printlevel(avl_tree_t **level, int nset){

    int i;
    printf("#levels\n-------------------\n");
    for(i = 0; i < nset; i++){
        if(level[i]->top != NULL){
            printf("level: %d\n", i);
            printlist(level[i], 3, stdout);
        }
    }
}


static int
printlist_points_indic(avl_tree_t *avltree, int dim, int nruns, FILE *outfile, FILE *outfileindic)
{
    avl_node_t *aux;
    aux = avltree->head;
    int * dom_sets = (int *) malloc(nruns * sizeof(int));
    int i, k, totalp = 0;

    double * val;
    while(aux){
        val = (double *)aux->item;
        if(outfile){
            fprintf(outfile, point_printf_format, val[0]);
            for(i = 1; i < dim; i++){
                fprintf(outfile, "\t" point_printf_format, val[i]);
            }
            
            fprintf(outfile, (outfile == outfileindic) ? "\t" : "\n");
        }
        if(outfileindic){
            for(k = 0; k < nruns; k++)
                dom_sets[k] = 0;
            find_all_promoters(aux, dom_sets, nruns);
            fprintf(outfileindic, "%d", dom_sets[0]);
            for(k = 1; k < nruns; k++){
                fprintf(outfileindic, "\t%d", dom_sets[k]);
            }
            fprintf(outfileindic, "\n");
        }
        aux = aux->next;
    
        totalp++;
    }
    
    free(dom_sets);
    return totalp;
}

/* Returns the total number of points printed */
int printoutput(avl_tree_t **level, int nset, int d, FILE **outfile, int noutfiles, FILE **outfileindic, int noutfilesi, int * attlevel, int nlevels){

    int i, k, f, fi, totalp = 0;
    for(i = 0; i < nlevels; i++){
        k = attlevel[i] - 1;
        f = (noutfiles > 1) ? i : 0;
        fi = (noutfilesi > 1) ? i : 0;
        
        if(level[k]->head != NULL){
            totalp += printlist_points_indic(level[k], d, nset, (outfile ? outfile[f] : NULL), (outfileindic ? outfileindic[fi] : NULL));
        }
        
        if(i < nlevels -1){
            if(outfile)
                fprintf(outfile[f], "\n");
            if(outfileindic && (outfile == NULL || outfile[f] != outfileindic[fi])){
                fprintf(outfileindic[fi], "\n");
            }
        }
    }
    return totalp;
}



static void print_list_indic(avl_tree_t * level, int nruns, FILE * indicfile){
    int * dom_sets = (int *) malloc(nruns * sizeof(int));

    avl_node_t * avlnode;
    avlnode = level->head;
    int i;

    while(avlnode){
        for(i = 0;  i < nruns; i++){
            dom_sets[i] = 0;
        }
        find_all_promoters(avlnode, dom_sets, nruns);
        
        for(i = 0; i < nruns; i++){
            fprintf(indicfile, "%d\t", dom_sets[i]);
        }
        fprintf(indicfile, "\n");
        avlnode = avlnode->next;
    }
    
    free(dom_sets);
}

void printindic(avl_tree_t ** levels, int nruns, FILE ** indicfile, int nfiles, int * attlevel, int nlevels){
    
    int i, k, f;
    for(i=0; i < nlevels; i++){
        k = attlevel[i] - 1;
        f = (nfiles == 1) ? 0 : i;
        
        if(levels[k]->head != NULL){
            print_list_indic(levels[k], nruns, indicfile[f]);
        }
        
        fprintf(indicfile[f], "\n");
    }
    
}


void printitem(double *value, int dim){

    int i;
    for(i = 0; i < dim; i++)
        printf("%f ", value[i]);
    printf("\n");

}


void add2output(avl_tree_t *output, avl_node_t *tnode){

    if(output->top != NULL){

        output->tail->next = tnode;
        output->tail = tnode;

    }else{
        avl_insert_top(output, tnode);
    }

}

void add2output_all(avl_tree_t *output, avl_tree_t *tree_add){
  
    avl_node_t *node = tree_add->head;
    avl_unlink_node(tree_add, node);
    free(node->item);
    free(node);

    node = tree_add->tail;
    avl_unlink_node(tree_add, node);
    free(node->item);
    free(node);

    
    if(tree_add->head != NULL){
        if(output->tail != NULL){
            output->tail->next=tree_add->head;   
        }else{
            output->head=tree_add->head;
        }
    }
}



//this fuction is called only when item isn't dominated by any point in tree.
//note: prevnode is above and at item's left, so any dominated point that it may dominate is at its right
static void
add2set(avl_tree_t *tree, avl_node_t *prevnode, avl_node_t *tnode, double *item)
{

    avl_init_node (tnode, item);
    if(node_point(prevnode)[0] == item[0])
        prevnode = prevnode->prev;
    avl_insert_after (tree, prevnode, tnode);
    
    tnode = tnode->next;

    // tnode->next is dominated by item
    while (node_point(tnode)[1] >= item[1]) {
        avl_unlink_node(tree, tnode);
        avl_node_t *node = tnode;
        tnode = tnode->next;
        free(node);
    }

}


static void avl_add_promoter(avl_node_t *avlnode, int set, avl_node_t *promoter){
    avlnode->set = set;
    avlnode->promoter = promoter;
    avlnode->remover = NULL;
    avlnode->equal = NULL;
}


static void add_removed(avl_node_t *node){
    node->next = rem_lst->head;
    rem_lst->head = node;
}

static avl_node_t *
add2level(avl_tree_t *tree, double *item, avl_tree_t *output, int set, avl_node_t * promoter)
{

    avl_node_t *prevnode;
    avl_node_t *aux;

    switch (avl_search_closest_y(tree, item, &prevnode)) {
    case -1:
        prevnode = prevnode->prev;
        break;
    case 0:
        prevnode = (node_point(prevnode)[0] > item[0])
            ? prevnode : prevnode->prev;
        break;

    case 1: break;
    }

    //aux represents the point immediately below item.
    aux = prevnode->next;
    // A new point (item) is added, but only if it isn't dominated.
    if (node_point(aux)[0] > item[0]) {
        avl_node_t *tnode = malloc(sizeof(avl_node_t));
        avl_init_node(tnode, item);
        avl_add_promoter(tnode, set, promoter);
        avl_node_t *newnode = tnode;
        avl_insert_after(tree, prevnode, tnode);
        tnode = tnode->prev;
            // tnode->prev is dominated by item
        while (node_point(tnode)[0] >= item[0]) {
            avl_unlink_node(tree,tnode);
            aux = tnode;
            tnode = tnode->prev;
            
            if (node_point(aux)[2] < item[2])
                add2output(output, aux);
            else{
                free(aux->item);
                add_removed(aux);
                aux->remover = newnode;
            }
            /* Each point that is removed from this level,
                becomes a final point but only if the point
                that dominates it (item) dominates it only from
                dimension 0 to 1.  */
        }
        return newnode;
    }else{
        free(item);
        return NULL;
    }
}


static double * copy_point(double * v){
    double *value = malloc(3 * sizeof(double));
    
    value[0] = v[0];
    value[1] = v[1];
    value[2] = v[2];
    
    return value;
}

//at left or equal
static avl_node_t * find_point_at_left(avl_tree_t *tree, double *item){

    avl_node_t *leftNode;
    
    if (avl_search_closest(tree, item, &leftNode) < 0) 
        leftNode = leftNode->prev;
    
    return leftNode;
}

//below and not equal
static avl_node_t * find_point_below(avl_tree_t *tree, double *item){

    avl_node_t *belowNode;
    
    if (avl_search_closest_y(tree, item, &belowNode) >= 0) 
        belowNode = belowNode->next;
    
    return belowNode;
    
}



static double * new_point(double x1, double x2, double x3){
    
    double *value = malloc(3 * sizeof(double));
    value[0] = x1;
    value[1] = x2;
    value[2] = x3;

    return value;
}


void 
eaf3df(dlnode_t *list, avl_tree_t **set, avl_tree_t **level, 
        avl_tree_t **output, int nset)
{
    dlnode_t *new = list->next;//new - represents the new point
    avl_node_t *tnode;
    
    /*newPrev - point from new's set immediately at its left, setNode - a point from new's set
    at its right, both correspond to q in pseudocode */
    avl_node_t *newPrev, *setNode;
    
    //point from some level immediately at new's left, corresponds to r in pseudocode
    avl_node_t *leftNodeL; 
    
    /*levelNode[t] is the point being verified (possibly to be promoted) from level t,
    corresponds to s_t in pseudocode */
    avl_node_t **levelNode = malloc(nset * sizeof(avl_node_t *)); 
    
    avl_node_t auxNodes[nset]; //these points are needed to represent the intersections of new
                                //with the point, of each level, immediately at new's left
    double auxValues[nset][2];
    
    int i, start_at = 0, stop_at; //indicates which levels must be verified (from and to which)
    double lb; //lower bound
    
    avl_node_t *dom_new;
    avl_node_t *promoters[nset];

    //add new to its set
    tnode = avl_init_node(malloc(sizeof(avl_node_t)), new->x);
    avl_insert_after(set[new->set], set[new->set]->head, tnode);

    //new is the only point so far, so it is added to the first level
    tnode = avl_init_node(malloc(sizeof(avl_node_t)), copy_point(new->x));
    avl_add_promoter(tnode, new->set, NULL);
    avl_insert_after(level[0], level[0]->head, tnode);

    
    bool mask[nset]; //needed to now how many different sets were considered so far
    
    for(i = 0; i < nset; i++)
        mask[i] = false;
    
    mask[new->set] = true;
    

    new = new->next;
    // Points are sorted in ascending order with respect to the third coordinate.
    while (new->x != NULL) {
    
        newPrev = find_point_at_left(set[new->set], new->x);
        
        //if new is not dominated by a point from its set
        if(new->x[1] < node_point(newPrev)[1]){
            dom_new = NULL;
            stop_at = 0;
            int k;
            
            //First part
            for(k = start_at; k >=stop_at; k--){
                
                leftNodeL = find_point_at_left(level[k], new->x);
                
                //new is dominated by a point from level k
                if(node_point(leftNodeL)[1] <= new->x[1]){
                    dom_new = leftNodeL;
                    stop_at = k+1;
                
                }else if(node_point(leftNodeL)[1] < node_point(newPrev)[1]){
                    /*the intersection point of new with the point in level k immediately at
                    new's left should be added to level k+1, so this intersection point is
                    saved in levelNode in order to be added later */
                    auxValues[k][0] = new->x[0];
                    auxValues[k][1] = node_point(leftNodeL)[1];
                    
                    auxNodes[k].item = auxValues[k];
                    auxNodes[k].next = leftNodeL->next;
                    
                    levelNode[k] = &auxNodes[k];
                    promoters[k] = leftNodeL;
                    
                }else{
                    
                    levelNode[k] = find_point_below(level[k], newPrev->item);
                    promoters[k] = levelNode[k];
                }
            }
            
            setNode = newPrev;
            
            //Second part
            do{
                setNode = setNode->next;
                lb = max(node_point(setNode)[1], new->x[1]);
                
                for(k = start_at; k >= stop_at; k--){
                
                    //while levelNode is dominated by new but not by any point from new's set, levelNode is promoted
                    while(node_point(levelNode[k])[1] >= lb && (node_point(levelNode[k])[1] > lb || lb > new->x[1])){
                        
                        if(node_point(levelNode[k])[0] >= node_point(setNode)[0]){
                            
                            levelNode[k] = find_point_below(level[k], setNode->item);
                            promoters[k] = levelNode[k];
                            
                        }else{
                            double *value;
                            value = new_point(node_point(levelNode[k])[0], node_point(levelNode[k])[1], new->x[2]);
                            tnode = add2level(level[k+1], value, output[k+1], new->set, promoters[k]);
                            if(tnode != NULL && new->x[2] == node_point(promoters[k])[2] && node_point(levelNode[k])[0] == node_point(promoters[k])[0]){
                                promoters[k]->equal = tnode;
                            }
                            
                            levelNode[k] = levelNode[k]->next;
                            promoters[k] = levelNode[k];
                        }
                    }
                }
            
            }while(node_point(setNode)[1] > new->x[1]);
            
            //Third part
            for(k = start_at; k >= stop_at; k--){
                //if the intersection point of new with the point from level k immediately below new
                //isn't dominated, it is added to level k+1
                if(node_point(levelNode[k])[0] < node_point(setNode)[0]){
                    
                    double *value;
                    value = new_point(node_point(levelNode[k])[0], new->x[1], new->x[2]);
                    tnode = add2level(level[k+1], value, output[k+1], new->set, promoters[k]);
                    if(tnode != NULL && new->x[2] == node_point(levelNode[k])[2] && new->x[1] == node_point(levelNode[k])[1]){
                        promoters[k]->equal = tnode;
                    }
                }
            }
            
            //add new to its set
            tnode = avl_init_node(malloc(sizeof(avl_node_t)), new->x);
            add2set(set[new->set], newPrev, tnode, new->x);
            
            //add new to the lowest level where it isn't dominated by any point from that level
            tnode = add2level(level[stop_at], copy_point(new->x), output[stop_at], new->set, dom_new);
            if(stop_at > 0 && tnode != NULL && new->x[2] == node_point(dom_new)[2] &&
                new->x[1] == node_point(dom_new)[1] && new->x[0] == node_point(dom_new)[0]){
                dom_new->equal = tnode;
            }
            
            if(mask[new->set] == false){
                
                if(start_at < (nset - 2))
                    start_at++;
                
                mask[new->set] = true;
                
            }
        }
        
        new = new->next;
        
    }
    
    free(levelNode);
}


static void
freetree(avl_tree_t *avltree)
{
    avl_node_t *aux, *aux2;
    aux=avltree->head;
    
    if(aux){    
        
        while(aux){
            aux2 = aux;
            aux = aux2->next;
            free(aux2->item);
            free(aux2);
        }
    }
    
    free(avltree);
}

static void
freetree2(avl_tree_t *avltree)
{
    avl_node_t *aux, *aux2;
    aux=avltree->head;

    free(aux->item);
    
    while(aux->next){
        aux2 = aux;
        aux = aux2->next;
        free(aux2);
    }
    
    free(aux->item);
    free(aux);
    
    free(avltree);
}


void free_removed(){
    
    avl_node_t * aux, * node = rem_lst->head;
    
    while(node != NULL){
        aux = node;
        node = node->next;
        free(aux);
    }
    
    free(rem_lst);
}

void freeoutput(avl_tree_t **output, int nset){

    int i;
    for(i = 0; i < nset; i++){
        freetree(output[i]);
    }
    free(output);
    
    free_removed();
}


void add_sentinels(avl_tree_t * tree, int n){

    double *value = malloc(n * sizeof(double));
    value[0] = -INFINITY;
    value[1] = INFINITY;
    
    avl_node_t *tnode, *tnode2;
    tnode = avl_init_node(malloc(sizeof(avl_node_t)), value);
    avl_insert_top(tree ,tnode);
    
    value = malloc(n * sizeof(double));
    value[0] = INFINITY;
    value[1] = -INFINITY;
    
    tnode2 = avl_init_node(malloc(sizeof(avl_node_t)), value);
    avl_insert_after(tree, tnode, tnode2);
    
}


avl_tree_t ** eaf3d(double *data, int dim, int *sets, int nset)
{

    int i;

    dlnode_t *list;
    avl_tree_t **set = malloc (nset * sizeof(avl_tree_t));
    avl_tree_t **level = malloc (nset * sizeof(avl_tree_t));
    avl_tree_t **output = malloc (nset * sizeof(avl_tree_t));

    for(i = 0; i < nset; i++){
        set[i]  = avl_alloc_tree ((avl_compare_t) compare_tree_asc_x, (avl_freeitem_t) free);
        level[i]  = avl_alloc_tree ((avl_compare_t) compare_tree_asc_x, (avl_freeitem_t) free);
        output[i]  = avl_alloc_tree ((avl_compare_t) compare_tree_asc_x, (avl_freeitem_t) free);
        
        add_sentinels(set[i], dim);
        add_sentinels(level[i], dim);
    
    }
    
    rem_lst = (removed_l *) malloc(sizeof(rem_lst));
    rem_lst->head = NULL;
    list = setup_cdllist(data, dim, sets, nset);

    eaf3df(list, set, level, output, nset);
   
    for(i=0; i<nset; i++){
        add2output_all(output[i], level[i]);
    }

    for(i=0; i<nset; i++){
        freetree2(set[i]);
        free(level[i]);
    }

    free(list);
    free(set);
    free(level);
    
    return output;
}

