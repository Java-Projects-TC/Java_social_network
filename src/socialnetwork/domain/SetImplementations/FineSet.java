package socialnetwork.domain.SetImplementations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FineSet<E> {

  private AtomicInteger size = new AtomicInteger(0);
  private LockableNode<E> head, tail;

  public FineSet() {
    head = new LockableNode(null, Integer.MIN_VALUE, null);
    tail = new LockableNode(null, Integer.MAX_VALUE, null);
    head.setNext(tail);
  }

  protected LockableNode<E> getFirst(){
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

  private Position<E> find(LockableNode<E> start, int key) {
    LockableNode<E> pred, curr;
    pred = start;
    pred.lock();
    curr = start.next();
    curr.lock();
    while (curr.key() < key) {
      pred.unlock();
      pred = curr;
      curr = curr.next();
      curr.lock();
    }
    return new Position<>(pred, curr);
  }

  public boolean add(E item) {
    LockableNode<E> node = new LockableNode<>(item);
    LockableNode<E> pred = null, curr = null;
    try {
      Position<E> where = find(head, node.key());
      pred = where.pred;
      curr = where.curr;
      if (where.curr.key() == node.key()) {
        return false;
      } else {
        node.setNext(where.curr);
        where.pred.setNext(node);
        size.incrementAndGet();
        return true;
      }
    } finally {
      pred.unlock();
      curr.unlock();
    }
  }

  public boolean remove(E item) {
    LockableNode<E> node = new LockableNode<>(item);
    LockableNode<E> pred = null, curr = null;
    try {
      Position<E> where = find(head, node.key());
      pred = where.pred;
      curr = where.curr;
      if (where.curr.key() > node.key()) {
        return false;
      } else {
        where.pred.setNext(where.curr.next());
        size.decrementAndGet();
        return true;
      }
    } finally {
      pred.unlock();
      curr.unlock();
    }
  }

  public int size() {
    return size.get();
  }

  private static class Position<T> {

    final LockableNode<T> pred, curr;

    Position(LockableNode<T> pred, LockableNode<T> curr) {
      this.pred = pred;
      this.curr = curr;
    }
  }

}
