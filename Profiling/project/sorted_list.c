/*
 * Simple, sorted linear list ... faster than un-sorted
 *
 *  methods:
 *      sorted_list(int)                constructor
 *      sorted_add(WordList *, char *)  add reference to a word
 *      sorted_refs(WordList *, char *) return # refs to a word
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
void sorted_add(struct word_list *this, char *word) {
        /* see if we already have a node for this word  */
        struct word_node *node = (struct word_node *) this->list;
        struct word_node *last = NULL;
        while(node != NULL) {
                int result = strcmp(node->word, word);
                if (result == 0) {      /* this is the one      */
                        node->refs += 1;
                        return;
                }

                if (result > 0)         /* we have passed it    */
                        break;
                        
                last = node;            /* remember end so we can append */
                node = node->next;
        }

        /* desired word is not yet in the list  */
        node = (struct word_node *) malloc(sizeof (struct word_node));
        node->word = word;
        node->refs = 1;

        if (last == NULL) {     /* list is empty        */
                node->next = NULL;
                this->list = node;
        } else {                /* append after last    */
                node->next = last->next;
                last->next = node;
        }
}

/*
 * return the number of references to the specified word
 *
 * @param (struct word_list *) WordList desciptor
 * @param (char *) word
 */
long sorted_refs(struct word_list *this, char *word) {
        struct word_node *node = (struct word_node *) this->list;
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
        my_list->list = 0;      /* list starts out empty        */

        return(my_list);
}
