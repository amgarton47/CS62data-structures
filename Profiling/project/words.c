/*
 * list_tester
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define WORD_LEN        4       /* chars per word       */

int words_dictionary_size;      /* how large is our dictionary? */

/*
 * allocate an array of string pointers and initialize
 * each with a randomly generated word.
 *
 * @param (int) how_many: the desired number of words
 * @return (char **) pointer to list of words
 */
char **make_words(int how_many) {

        /* initialize the random number generator       */
        srand(time(NULL));

        /* word generation tables */
        static char *letters = "abcdefghijklmnopqrstuvwxyz";
        int num_letters = strlen(letters);

        /* allocate the array to return */
        char **dictionary = (char **) malloc((1 + how_many) * sizeof (char *));
        words_dictionary_size = how_many;

        /* initialize it                */
        char wordbuf[WORD_LEN + 1];
        int used = 0;
        for(int i = 0; i < how_many; i++) {
                int start = used++;
                for(int j = 0; j < WORD_LEN; j++) {
                        wordbuf[j] = letters[ start % num_letters ];
                        start /= num_letters;
                }
                wordbuf[WORD_LEN] = 0;

                /* add a copy of this word to the dictionary    */
                dictionary[i] = strdup(wordbuf);
        }

        /* null terminate and return it */
        dictionary[how_many] = NULL;
        return dictionary;
}

/*
 * choose a word (at random) from a supplied list
 *
 * @param (char **) dictionary: pointer to list of words
 *
 * @return (char *) pointer to a randomly chosen word
 */
char *choose_word(char **dictionary) {
        int index = rand() % words_dictionary_size;

        return dictionary[index];
}

/*
 * hash a word (DJB2 by Dan Bernstein)
 * 
 * @param (char *) word
 * @return (int) hash code
 */
unsigned long hash_word(unsigned char *word) {
        unsigned long hash = 5381;
        int c;

        while(c = *word++)
                hash = ((hash << 5) + hash) + c;

        return hash;
}
