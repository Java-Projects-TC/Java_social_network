package socialnetwork.domain.SetImplementations;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockableNode<E> implements Node<E> {

  private Lock lock = new ReentrantLock();

  private E item;
  private int key;
  private LockableNode<E> next;

  LockableNode(E item) {
    this(item, null);
  }

  private LockableNode(E item, LockableNode<E> next) {
    this(item, item.hashCode(), next);
  }

  LockableNode(E item, int key, LockableNode<E> next) {
    this.item = item;
    this.key = key;
    this.next = next;
  }

  @Override
  public E item() {
    return item;
  }

  @Override
  public int key() {
    return key;
  }

  @Override
  public LockableNode<E> next() {
    return next;
  }

  @Override
  public void setItem(E item) {
    this.item = item;
  }

  @Override
  public void setKey(int key) {
    this.key = key;
  }

  @Override
  public void setNext(Node<E> next) {
    this.next = (LockableNode<E>) next;
  }

  void lock() {
    lock.lock();
  }

  void unlock() {
    lock.unlock();
  }
}
