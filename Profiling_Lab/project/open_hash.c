/*
 * open_hash table ... very short searches in dictionary (until it fills up)
 *
 *  methods:
 *      open_hash(int)                constructor
 *      open_add(WordList *, char *)  add reference to a word
 *      open_refs(WordList *, char *) return # refs to a word
 */
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include "word_list.h"

/* 5% over-allocation should be safe   */
#define OVER_ALLOCATE(size) ((size * 21)/20)

/*
 * the list as a collection contiguous word entries
 */
struct word_entry {
        char *word;     /* word represented by this node */
        long refs;      /* number of refs to this word   */
};

struct open_hash_table {
        int     num_entries;
        struct word_entry entries[];
};


extern unsigned long hash_word(unsigned char *word);

/*
 * find the entry for a given word
 *
 * @param (struct open_hash_table) table: descriptor
 * @param (char *) name: word being sought
 *
 * @return (stuct word_entry *) allocated entry or free slot
 */
struct word_entry *open_find_entry(struct open_hash_table *table, char *name) {
        unsigned long index = hash_word((unsigned char *) name) % table->num_entries;
        unsigned long start = index;

        do {    /* search til we find a match or a hole         */
                struct word_entry *e = &table->entries[index];
                if (e->word == NULL)
                        return(e);      /* empty slot, use it   */
                if (strcmp(e->word, name) == 0)
                        return(e);      /* desired entry        */

                /* see if we need to wrap around                */
                if (index == table->num_entries - 1)
                        index = 0;
                else
                        index += 1;
        } while (index != start);

        /* we went full circle without finding entry or hole    */
        abort();
}


/*
 * find and incrment, or initialize new word_entry
 *
 * @param (struct word_list *) WordList desciptor
 * @param (char *) word
 */
void open_add(struct word_list *this, char *word) {
        struct open_hash_table *table = (struct open_hash_table *) this->list;

        /* see if we already have an entry      */
        struct word_entry *e = open_find_entry(table, word);
        if (e->word == NULL) {  /* initialize noew entry        */
                e->word = word;
                e->refs = 1;
        } else {                /* increment reference count    */
                e->refs += 1;
        }
}

/*
 * return the number of references to the specified word
 *
 * @param (struct word_list *) WordList desciptor
 * @param (char *) word
 * @return (long) reference count for this word
 */
long open_refs(struct word_list *this, char *word) {
        struct open_hash_table *table = (struct open_hash_table *) this->list;

        struct word_entry *e = open_find_entry(table, word);
        if (e->word == NULL)
                return 0;
        else
                return e->refs;
}

/*
 * constructor for a open hash table of word references
 *
 * @param (int) max_size ... used to calculate deired hash table size
 */
struct word_list *open_hash(int max_size) {
        
        struct word_list *my_list = (struct word_list *) malloc(sizeof (struct word_list));
        my_list->type = "open hash table";
        my_list->add_method = open_add;
        my_list->ref_method = open_refs;

        /* figure out how large we should make the table        */
        int entries = OVER_ALLOCATE(max_size);

        /* allocate and initialize the open hash table          */
        int size = sizeof (struct open_hash_table) + 
		   (entries * sizeof (struct word_entry));
        struct open_hash_table *table = malloc(size);
        bzero(table, size);
        table->num_entries = entries;
        my_list->list = table;

        return(my_list);
}
