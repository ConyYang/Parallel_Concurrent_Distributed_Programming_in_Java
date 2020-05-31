package edu.coursera.concurrent;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Wrapper class for two lock-based concurrent list implementations.
 */
public final class CoarseLists {
    /**
     * An implementation of the ListSet interface that uses Java locks to
     * protect against concurrent accesses.
     *
     * TODO Implement the add, remove, and contains methods below to support
     * correct, concurrent access to this list. Use a Java ReentrantLock object
     * to protect against those concurrent accesses. You may refer to
     * SyncList.java for help understanding the list management logic, and for
     * guidance in understanding where to place lock-based synchronization.
     */
    public static final class CoarseList extends ListSet {
        /*
         * TODO Declare a lock for this class to be used in implementing the
         * concurrent add, remove, and contains methods below.
         */
        //private final Entry head;
        private final ReentrantLock lock = new ReentrantLock();

        /**
         * Default constructor.
         * @param
         */
        public CoarseList() {
           super();
//            this.head = new Entry(Integer.MIN_VALUE);
//            this.head.next = new Entry(Integer.MAX_VALUE);
        }


        /**
         * {@inheritDoc}
         *
         * TODO Use a lock to protect against concurrent access.
         */
        @Override
        boolean add(final Integer object) {
            try{
                lock.lock();

                Entry pred;
                Entry curr;
                final int key = object.hashCode();
                pred = this.head;
                curr = pred.next;

                while (curr.object.compareTo(object) < 0)
                {
                    pred = curr;
                    curr = curr.next;
                }
                if (object.equals(curr.object)){
                    return false;
                } else
                {
                    final Entry entry =  new Entry(object);
                    entry.next = curr;
                    pred.next = entry;
                    return true;
                }
            }finally {
                lock.unlock();
            }
        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a lock to protect against concurrent access.
         */
        @Override
        boolean remove(final Integer object) {
            try{
                lock.lock();

                Entry pred;
                Entry curr;
                final int key = object.hashCode();
                pred = this.head;
                curr = pred.next;

                while (curr.object.compareTo(object) < 0)
                {
                    pred = curr;
                    curr = curr.next;
                }
                if (object.equals(curr.object)){
                    pred.next = curr.next;
                    return true;
                } else
                    return false;
            }finally {
                lock.unlock();
            }
        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a lock to protect against concurrent access.
         */
        @Override
        boolean contains(final Integer object) {
            try{
                lock.lock();

                Entry pred;
                Entry curr;
                final int key = object.hashCode();
                pred = this.head;
                curr = pred.next;

                while (curr.object.compareTo(object) < 0)
                {
                    pred = curr;
                    curr = curr.next;
                }
                return (object.equals(curr.object));

            }finally {
                lock.unlock();
            }
        }
    }

    /**
     * An implementation of the ListSet interface that uses Java read-write
     * locks to protect against concurrent accesses.
     *
     * TODO Implement the add, remove, and contains methods below to support
     * correct, concurrent access to this list. Use a Java
     * ReentrantReadWriteLock object to protect against those concurrent
     * accesses. You may refer to SyncList.java for help understanding the list
     * management logic, and for guidance in understanding where to place
     * lock-based synchronization.
     */
    public static final class RWCoarseList extends ListSet {
        /*
         * TODO Declare a read-write lock for this class to be used in
         * implementing the concurrent add, remove, and contains methods below.
         */
       // private final Entry head;
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        /**
         * Default constructor.
         */
        public RWCoarseList() {
            super();
//            this.head = new Entry(Integer.MIN_VALUE);
//            this.head.next = new Entry(Integer.MAX_VALUE);
        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a read-write lock to protect against concurrent access.
         */
        @Override
        boolean add(final Integer object) {
            try{
                lock.writeLock().lock();

                Entry pred;
                Entry curr;
                final int key = object.hashCode();
                pred = this.head;
                curr = pred.next;

                while (curr.object.compareTo(object) < 0)
                {
                    pred = curr;
                    curr = curr.next;
                }
                if (object.equals(curr.object)){
                    return false;
                } else {
                    final Entry entry =  new Entry(object);
                    entry.next = curr;
                    pred.next = entry;
                    return true;
                }
            }finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a read-write lock to protect against concurrent access.
         */
        @Override
        boolean remove(final Integer object) {
//            Entry pred = this.head;
//            Entry curr = pred.next;
//
//            while (curr.object.compareTo(object) < 0) {
//                pred = curr;
//                curr = curr.next;
//            }
//
//            if (object.equals(curr.object)) {
//                pred.next = curr.next;
//                return true;
//            } else {
//                return false;
//            }
            try{
                lock.writeLock().lock();

                Entry pred;
                Entry curr;
                final int key = object.hashCode();
                pred = this.head;
                curr = pred.next;

                while (curr.object.compareTo(object) < 0)
                {
                    pred = curr;
                    curr = curr.next;
                }
                if (object.equals(curr.object)){
                    pred.next = curr.next;
                    return true;
                } else
                    return false;
            }finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a read-write lock to protect against concurrent access.
         */
        @Override
        boolean contains(final Integer object) {
            try{
                    lock.readLock().lock();

                    Entry pred;
                    Entry curr;
                    final int key = object.hashCode();
                    pred = this.head;
                    curr = pred.next;

                    while (curr.object.compareTo(object) < 0)
                    {
                        pred = curr;
                        curr = curr.next;
                    }
                    return (object.equals(curr.object));
                }finally {
                    lock.readLock().unlock();
                }
            }
        }
    }
