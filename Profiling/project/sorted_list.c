/*
 * Simple, sorted linear list ... faster than un-sorted
 *
 *  methods:
 *      sorted_list(int)                constructor
 *      sorted_add(WordList *, char *)  add reference to a word
 *      sorted_refs(WordList *, char *) return # refs to a word
 *	sorted_allocate(WordLit *)	allocate a new word_node
 */
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "word_list.h"

/*
 * the list as a collection of singly linked nodes
 */
struct word_node {
        char *word;                     /* word represented by this node */
        long refs;                      /* number of refs to this word   */
        struct word_node *next;         /* next node in the list         */
};

struct node_list {
	struct word_node *first;	/* first word_node in list	*/
	int num_free;			/* number of unused word_nodes	*/
	struct word_node *free_list;	/* yet unused word_nodes	*/
};

/*
 * word_node allocator
 *	to eliminate malloc-time from add calls
 * @param (struct word_list *) our word_list descriptor
 * @return address of next free word_node)
 */
struct word_node *sorted_allocate(struct word_list *this) {
        struct node_list *nodeList = (struct node_list *) this->list;

	assert(nodeList->num_free > 0);
	nodeList->num_free -= 1;
	return(nodeList->free_list++);
}

/*
 * allocate a new word_node and put it onto the front of the list
 *
 * @param (struct word_list *) WordList desciptor
 * @param (char *) word
 */
void sorted_add(struct word_list *this, char *word) {
        /* see if we already have a node for this word  */
        struct node_list *nodeList = (struct node_list *) this->list;
        struct word_node *node = nodeList->first;
        struct word_node *last_smaller = NULL;
        while(node != NULL) {
                int result = strcmp(node->word, word);
                if (result == 0) {      /* this is the one      */
                        node->refs += 1;
                        return;
                }

                if (result > 0)         /* we have passed it    */
                        break;
                        
                last_smaller = node;    /* remember end so we can append */
                node = node->next;
        }

        /* desired word is not yet in the list  */
	node = sorted_allocate(this);
        node->word = word;
        node->refs = 1;

	if (last_smaller == NULL) {     /* new first element of list	*/
                node->next = nodeList->first;
		nodeList->first = node;
        } else {                /* append after last    */
                node->next = last_smaller->next;
                last_smaller->next = node;
        }
}

/*
 * return the number of references to the specified word
 *
 * @param (struct word_list *) WordList desciptor
 * @param (char *) word
 */
long sorted_refs(struct word_list *this, char *word) {
        struct node_list *nodeList = (struct node_list *) this->list;
        struct word_node *node = nodeList->first;
        while(node != NULL) {
                int result = strcmp(node->word, word);
                if (result == 0)        /* this is the one      */
                        return node->refs;
                if (result > 0)         /* we have passed it    */
                        return 0;
                node = node->next;
        }

        return 0;       /* no references found  */
}

/*
 * constructor for a linear linked list of word references
 *
 * @param (int) max_size ... ignored
 */
struct word_list *sorted_list(int max_size) {
        
        struct word_list *my_list = (struct word_list *) malloc(sizeof (struct word_list));
        my_list->type = "sorted linked list";
        my_list->add_method = sorted_add;
        my_list->ref_method = sorted_refs;

	/* allocate and initialize the free node list */
	struct node_list *nodeList = (struct node_list *) malloc(sizeof(struct node_list));
	nodeList->first = NULL;
	nodeList->num_free = max_size;
	int size = max_size * sizeof (struct word_node);
	nodeList->free_list = (struct word_node *) malloc(size);

	my_list->list = nodeList;
        return(my_list);
}
