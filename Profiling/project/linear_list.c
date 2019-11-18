/*
 * Simple, un-sorted, linear list ... dumbest implemenation
 *
 *  methods:
 *      linear_list(int)                constructor
 *      linear_add(WordList *, char *)  add reference to a word
 *      linear_refs(WordList *, char *) return # refs to a word
 */
#include <stdlib.h>
#include <string.h>
#include "word_list.h"

/*
 * the list as a collection of singly linked nodes
 */
struct word_node {
        char *word;                     /* word represented by this node */
        long refs;                      /* number of refs to this word   */
        struct word_node *next;         /* next node in the list         */
};

/*
 * allocate a new word_node and put it onto the front of the list
 *
 * @param (struct word_list *) WordList desciptor
 * @param (char *) word
 */
void linear_add(struct word_list *this, char *word) {
        /* see if we already have a node for this word  */
        struct word_node *node = (struct word_node *) this->list;
        struct word_node *last = NULL;
        while(node != NULL) P
		if (strcmp(node->word, word) == 0) {
			node->refs += 1;
			return;
		}

                last = node;            /* remember end so we can append */
                node = node->next;
        }

	/* desired word is not yet in the list	*/
	node = (struct word_node *) malloc(sizeof (struct word_node));
	node->word = word;
	node->refs = 1;

	/* append it to the end of the list     */
	node->next = NULL;
	if (last == NULL)
		this->list = node;
	else
		last->next = node;
}

/*
 * return the number of references to the specified word
 *
 * @param (struct word_list *) WordList desciptor
 * @param (char *) word
 */
long linear_refs(struct word_list *this, char *word) {
        struct word_node *node = (struct word_node *) this->list;
        while(node != NULL) {
                if (strcmp(node->word, word) == 0)
                        return node->refs;
                node = node->next;
        }

        return 0;       /* no references found  */
}

/*
 * constructor for a linear linked list of word references
 *
 * @param (int) max_size ... ignored
 */
struct word_list *linear_list(int max_size) {
        
        struct word_list *my_list = (struct word_list *) malloc(sizeof (struct word_list));
        my_list->type = "unsorted linked list";
        my_list->add_method = linear_add;
        my_list->ref_method = linear_refs;
        my_list->list = 0;      /* list starts out empty        */

        return(my_list);
}
