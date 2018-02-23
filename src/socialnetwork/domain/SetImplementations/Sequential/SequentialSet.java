package socialnetwork.domain.SetImplementations.Sequential;

import java.util.ArrayList;
import java.util.List;
import socialnetwork.domain.SetImplementations.Node;

public class SequentialSet<E> {

  // This code was taken from Anto's gitlab he uses for lectures

  // Slightly modified so that the head is created in here rather than as a
  // static variable in SeqNode.

  private int size = 0;
  private Node<E> head, tail;

  public SequentialSet() {
    head = new SequentialNode<>(null, Integer.MIN_VALUE, null);
    tail = new SequentialNode<>(null, Integer.MAX_VALUE, null);
    head.setNext(tail);
  }

  protected Node<E> getFirst(){
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

  private Position<E> find(Node<E> start, int key) {
    Node<E> pred, curr;
    curr = start;
    do {
      pred = curr;
      curr = curr.next();
    } while (curr.key() < key);  // until curr.key >= key
    return new Position<>(pred, curr);
  }

  public boolean add(E item) {
      Node<E> node = new SequentialNode<>(item);
      Position<E> where = find(head, node.key());
      if (where.curr.key() == node.key()) {
        return false;
      } else {
        node.setNext(where.curr);
        where.pred.setNext(node);
        size += 1;
        return true;
      }
  }

  public boolean remove(E item) {
      Node<E> node = new SequentialNode<>(item);
      Position<E> where = find(head, node.key());
      if (where.curr.key() > node.key()) {
        return false;
      } else {
        where.pred.setNext(where.curr.next());
        size -= 1;
        return true;
      }
  }

  public int size() {
    return size;
  }


  private static class Position<T> {

    final Node<T> pred, curr;

    Position(Node<T> pred, Node<T> curr) {
      this.pred = pred;
      this.curr = curr;
    }
  }
}
