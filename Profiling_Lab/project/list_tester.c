/*
 * list_tester
 *
 *   The purpose of this program is to generate a great many
 *   lookups and updates to a moderately long list, so that 
 *   execution profiling can be used to assess the costs of
 *   multiple list implementations.
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "word_list.h"

#define MAX_WORDS       2048    /* words in dictionary  */
#define UPDATES         1000000 /* ref updates          */

/* available list implementations       */
extern WordList *linear_list(int max_size);
extern WordList *sorted_list(int max_size);
extern WordList *open_hash(int max_size);
extern WordList *bucket_hash(int max_size);

struct { char *arg;
         struct word_list *(*implementation)(int max_size);
} choices[] = {
        /* argument     constructor                                       */
        {"linear",      linear_list},   /* un-sorted linear search        */
        {"sorted",      sorted_list},   /* linear search sorted list      */
        {"open",        open_hash},     /* open hash table (linear probe) */
        {"bucket",      bucket_hash},   /* multi-bucket hash chains       */
        {NULL,          NULL}           /* end of list                    */
};

/* random word generation functions     */
extern char **make_words(int how_many);
extern char *choose_word(char **dictionary);

/*
 * print a usage message and exit
 *
 * @param bad_arg: invalid argument (if any)
 */
void usage(char *bad_arg) {
        if (bad_arg)
                fprintf(stderr, "Unrecognized argument: %s\n\n", bad_arg);

        fprintf(stderr, "Usage: list_tester list_type [max_size] [cycles]\n");
        fprintf(stderr, "   list types:");
        for(int i = 0; choices[i].arg; i++)
                fprintf(stderr, "%s ", choices[i].arg);
        fprintf(stderr, "\n");
        exit(1);
}

/*
 * main
 *   1. process arguments
 *   2. choose a list implementation
 *   3. generate a list of words
 *   4. do a large number of list updates
 */
int main(int argc, char **argv) {
        
        /* make sure we have enough arguments           */
        if (argc < 2)
                usage(NULL);
                
        /* see if we have a specified word count and cycles     */
        int max_size = MAX_WORDS;
        if (argc > 2)
                max_size = atoi(argv[2]);
        
        long updates = UPDATES;
        if (argc > 3)
                updates = atol(argv[3]);

        /* 2. figure out which type of list we are testing */
        WordList *list = 0;
        for(int i = 0; choices[i].arg; i++)
                if (strcmp(argv[1], choices[i].arg) == 0)
                        list = (*choices[i].implementation)(max_size);

        if (list == 0)
                usage(argv[1]);

        printf("Exercising %s word-list implementation\n", list->type);

        /* 3. generate a list of words  */
        printf("Generating random words ... ");
        char **words = make_words(max_size);
        printf("%d\n", max_size);

        /* 4. generate random references        */
        printf("Generating references   ... ");
        for(long i = 0; i < updates; i++)
                add_ref(list, choose_word(words));
        printf("%ld\n", updates);
        
        printf("Counting references     ... ");
        long refs = 0;
        for(int i = 0; i < max_size; i++)
                refs += references(list, words[i]);
        printf("%ld\n", refs);

        exit(0);
 }
