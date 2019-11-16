/*
 * A word_list is a struct through which different implementations
 * of word reference tracking can be accessed.  It has two methods:
 *      add_word ... add a new word to the list
 *      references ... return the number of references
 *
 * A list constructor will create and return one of these, after
 * which the list and its add/ref methods can be accessed through
 * the macros below.
 *
 * FYI: this is a fairly typical approach to C object implementation
 */
typedef struct word_list {
        char *type;                     /* name of this implementation  */

        /* add a new word to the list                                   */
        void (*add_method)(struct word_list *this, char *word);

        /* return number of references to a word                        */
        long (*ref_method)(struct word_list *this, char *word);

        /* this field is entirely implemenation dependent               */
        void *list;
} WordList;


/*
 * macros to encapsulate indirection to the implementations
 */
#define add_ref(list, word) (*list->add_method)(list, word)
#define references(list, word) (*list->ref_method)(list, word)
