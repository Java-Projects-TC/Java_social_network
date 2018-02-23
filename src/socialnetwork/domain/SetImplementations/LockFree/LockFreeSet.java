package socialnetwork.domain.SetImplementations.LockFree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import socialnetwork.domain.SetImplementations.Node;

public class LockFreeSet<E> {

  private AtomicInteger size = new AtomicInteger(0);
  private LockFreeNode<E> head, tail;

  public LockFreeSet() {
    this.tail = new LockFreeNode(null, Integer.MAX_VALUE);
    this.head = new LockFreeNode<E>(null, Integer.MIN_VALUE, tail);
  }

  protected LockFreeNode<E> getFirst() {
    return head.next();
  }

  protected List<E> toArrayList() {
    List<E> list = new ArrayList<>();
    Node<E> node = head.next();
    while (node != tail) {
      list.add(0, node.item());
      node = node.next();
    }
    return list;
  }

  public boolean add(E item) {
    do {
      Position<E> where = findAndRemoveInvalidNodes(head, item.hashCode());
      LockFreeNode<E> pred = where.pred, curr = where.curr;

      LockFreeNode<E> node = new LockFreeNode<>(item, where.curr);
      if (curr.key() == node.key()) {
        return false; // item already in the set
      }
      // if pred still points to curr, make it point to node
      if (pred.setNextIfValid(curr, node)) {
        size.incrementAndGet();
        return true;
      }
    } while (true);
  }

  public boolean remove(E item) {
    LockFreeNode<E> node = new LockFreeNode<>(item);
    do {
      Position<E> where = findAndRemoveInvalidNodes(head, node.key());
      LockFreeNode<E> pred = where.pred, curr = where.curr;
      if (curr.key() != node.key()) {
        return false; // item not in the set
      }
      // if node is modified while trying to invalidate it: retry
      if (!curr.setInvalid()) {
        continue;
      }
      // try to remove curr by redirecting pred
      // if it fails, another thread will do it
      pred.setNextIfValid(curr, curr.next());
      size.decrementAndGet();
      return true;
    } while (true);
  }

  private Position<E> findAndRemoveInvalidNodes(LockFreeNode<E> start,
      int key) {
    LockFreeNode<E> nextValid;
    boolean valid;
    LockFreeNode<E> pred = null, curr = null, succ = null;
    retry:
    do {
      pred = start;
      curr = pred.next();
      do {
        nextValid = curr.nextValid();
        succ = nextValid.next();
        valid = nextValid.valid();
        while (!valid) {  // curr is not valid: remove it
          // set pred.next to succ
          boolean success = pred.setNextIfValid(curr, succ);
          // !success iff pred has been changed since the current thread read it
          if (!success) {
            continue retry;  // in this case, start from scratch
          }
          curr = succ;  // if removal of curr is successful, succ is the new curr
          nextValid = curr.nextValid();
          succ = nextValid.next();
          valid = nextValid.valid();
        }
        // pred and curr are now valid
        // if pred and curr denote the correct position, return them
        if (curr.key() >= key) {
          return new Position<E>(pred, curr);
        }
        // otherwise, move to the next node
        pred = curr;
        curr = succ;
      } while (true);
    } while (true);
  }

  public int size() {
    return size.get();
  }

  private Position<E> find(LockFreeNode<E> start, int key) {
    LockFreeNode<E> pred, curr;
    curr = start;
    do {
      pred = curr;
      curr = curr.next();
    } while (curr.key() < key);

    return new Position<E>(pred, curr);
  }

  private static class Position<T> {

    final LockFreeNode<T> pred, curr;

    Position(LockFreeNode<T> pred, LockFreeNode<T> curr) {
      this.pred = pred;
      this.curr = curr;
    }
  }
}
