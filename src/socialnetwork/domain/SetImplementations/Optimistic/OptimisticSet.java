package socialnetwork.domain.SetImplementations.Optimistic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import socialnetwork.domain.SetImplementations.Node;

public class OptimisticSet<E> {

  // This was by far he fastest set implementation for the board and backlog.

  protected AtomicInteger size = new AtomicInteger(0);
  private ReadWriteNode<E> head, tail;

  public OptimisticSet() {
    head = new ReadWriteNode(null, Integer.MIN_VALUE, null);
    tail = new ReadWriteNode(null, Integer.MAX_VALUE, null);
    head.setNext(tail);
  }

  protected ReadWriteNode<E> getFirst() {
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

  private boolean valid(Node<E> pred, Node<E> curr) {
    ReadWriteNode<E> node = head;
    while (node.key() <= pred.key()) {
      if (node == pred) {
        return pred.next() == curr;
      }
      node = node.next();
    }
    return false;
  }

  private Position<E> find(ReadWriteNode<E> start, int key) {
    ReadWriteNode<E> pred, curr;
    curr = start;
    do {
      pred = curr;
      curr = curr.next();
    } while (curr.key() < key);

    return new Position<>(pred, curr);
  }

  public boolean add(E item) {
    ReadWriteNode<E> node = new ReadWriteNode<>(item);
    do {
      Position<E> where = find(head, node.key());
      ReadWriteNode<E> pred = where.pred, curr = where.curr;
      pred.lock();
      curr.lock();
      try {
        if (valid(pred, curr)) {
          if (where.curr.key() == node.key()) {
            return false;
          } else {
            node.setNext(where.curr);
            where.pred.setNext(node);
            size.incrementAndGet();
            return true;
          }
        }
      } finally {
        pred.unlock();
        curr.unlock();
      }
    } while (true);
  }

  public boolean remove(E item) {
    Node<E> node = new ReadWriteNode<>(item);
    do {
      Position<E> where = find(head, node.key());
      ReadWriteNode<E> pred = where.pred, curr = where.curr;
      pred.lock();
      curr.lock();
      try {
        if (valid(pred, curr)) {
          if (where.curr.key() > node.key()) {
            return false;
          } else {
            where.pred.setNext(where.curr.next());
            size.decrementAndGet();
            return true;
          }
        }
      } finally {
        pred.unlock();
        curr.unlock();
      }
    } while (true);
  }

  public int size() {
    return size.get();
  }

  protected static class Position<T> {

    final ReadWriteNode<T> pred, curr;

    Position(ReadWriteNode<T> pred, ReadWriteNode<T> curr) {
      this.pred = pred;
      this.curr = curr;
    }
  }
}

