/*
 * bucket hash lists ... should be very fast
 *
 *  methods:
 *      bucket_hash(int)                constructor
 *      bucket_add(WordList *, char *)  add reference to a word
 *      bucket_refs(WordList *, char *) return # refs to a word
 */
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <assert.h>
#include "word_list.h"

#define BUCKET_SIZE     15      /* target words per bucket      */

/*
 * the hash table is multiple buckets.
 * each bucket is a linear list of word nodes.
 */
struct word_node {
        char *word;     /* word represented by this node */
        long refs;      /* number of refs to this word   */
        struct word_node *next; /* next in list          */
        
};

struct buckets {
        int     num_buckets;		/* number of buckets	*/
	int	num_free;		/* size of free_list	*/
	struct word_node *free_list;	/* free word_nodes	*/
        struct word_node *buckets[];	/* bucket headers	*/
};

extern unsigned long hash_word(unsigned char *word);

/*
 * word_node allocator
 *	to eliminate malloc-time from add calls
 * @param (struct word_list *) our word_list descriptor
 * @return address of next free word_node)
 */
struct word_node *hash_allocate(struct word_list *this) {
        struct buckets *buckets = (struct buckets *) this->list;

	assert(buckets->num_free > 0);
	buckets->num_free -= 1;
	return(buckets->free_list++);
 }

/*
 * find and incrment, or initialize new word_entry
 *
 * @param (struct word_list *) WordList desciptor
 * @param (char *) word
 */
void bucket_add(struct word_list *this, char *word) {
        struct buckets *buckets = (struct buckets *) this->list;

        /* hash to find the right bucket        */
        unsigned long index = hash_word((unsigned char *) word)
			      % buckets->num_buckets;

        /* search that hash chain for a match   */
        struct word_node *last = NULL;
        for(struct word_node *node = buckets->buckets[index]; 
	    node != NULL; node = node->next) {
                if (strcmp(node->word, word) == 0) {
                        node->refs += 1;
                        return;
                }
                last = node;
        }

        /* allocate a new node                  */
        struct word_node *node = hash_allocate(this);
        node->word = word;
        node->refs = 1;
        node->next = NULL;

        /* add it to the end of the chain       */
        if (last != NULL)
                last->next = node;
        else
                buckets->buckets[index] = node;
}

/*
 * return the number of references to the specified word
 *
 * @param (struct word_list *) WordList desciptor
 * @param (char *) word
 * @return (long) reference count for this word
 */
long bucket_refs(struct word_list *this, char *word) {
        struct buckets *buckets = (struct buckets *) this->list;

        /* hash to find the right bucket        */
        unsigned long index = hash_word((unsigned char *) word) % buckets->num_buckets;

        /* search that hash chain for a match   */
        for(struct word_node *node = buckets->buckets[index]; node != NULL; node = node->next)
                if (strcmp(node->word, word) == 0)
                        return(node->refs);

        return 0;
}

/*
 * constructor for a open hash table of word references
 *
 * @param (int) max_size ... used to estimate # of buckets
 */
struct word_list *bucket_hash(int max_size) {
        
        struct word_list *my_list = (struct word_list *) malloc(sizeof (struct word_list));
        my_list->type = "hash buckets and chains";
        my_list->add_method = bucket_add;
        my_list->ref_method = bucket_refs;

        /* figure out how many buckets I want   */
        int num_buckets = max_size / BUCKET_SIZE;

        /* allocate and initialize the open hash table          */
        int size = sizeof (struct buckets) + 
		   (num_buckets * sizeof (struct word_node *));
        struct buckets *bucket_list = malloc(size);
        bzero(bucket_list, size);
        bucket_list->num_buckets = num_buckets;
        my_list->list = bucket_list;

	/* allocate and initialize the free node list */
	bucket_list->num_free = max_size;
	size = max_size * sizeof (struct word_node);
	bucket_list->free_list = (struct word_node *) malloc(size);

        return(my_list);
}
