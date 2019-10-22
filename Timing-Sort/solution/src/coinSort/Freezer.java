package coinSort;

/*
 * A demonstration class for "freezing" execution.
 *
 * The idea is that there is a supervisor thread and
 * a worker thread. This class provides an object on
 * which to synchronize.
 *
 * The supervisor thread calls toggle to control the
 * worker.
 * 
 * The worker thread calls permit and deny to allow
 * or ignore the supervisor and calls check whenever
 * it wants to allow for freezing. The call to check
 * is just an encapsulation of wait(), so it appears
 * to the caller as a no-op.
 *
 */
public class Freezer {

    protected boolean allowed;     // is freezing permitted?
    protected boolean frozen;      // is a freeze in effect?
    protected boolean singleStep;  // should the next call to check() block?

    public Freezer() {
        allowed = false;
        frozen = false;
        singleStep = false;
    }

    public synchronized void toggle(boolean ss) {
        if (allowed) {
            singleStep = ss;
            if (frozen) {
                frozen = false;
                notify();
            } else
                frozen = true;
        }
    }

    public synchronized void deny() {
        allowed = false;
    }

    public synchronized void permit() {
        allowed = true;
        frozen = false;       // unnecessary if only the worker calls permit()
        singleStep = false;
    }

    public synchronized void check() {
        while (frozen)
            try {
                wait();
            } catch (InterruptedException e) {
            }
        frozen = singleStep;
    }

}
