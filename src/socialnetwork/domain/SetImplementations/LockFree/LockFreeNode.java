package socialnetwork.domain.SetImplementations.LockFree;

import java.util.concurrent.atomic.AtomicMarkableReference;
import socialnetwork.domain.SetImplementations.Node;

public class LockFreeNode<E> implements Node<E> {

  private AtomicMarkableReference<LockFreeNode<E>> nextValid;

  private E item;
  private int key;

  LockFreeNode(E item) {
    this(item, item.hashCode());
  }

  LockFreeNode(E item, int key) {
    this.item = item;
    this.key = key;
    this.nextValid = new AtomicMarkableReference<LockFreeNode<E>>(null, true);
  }

  LockFreeNode(E item, LockFreeNode<E> next) {
    this(item, item.hashCode(), next);
  }


  LockFreeNode(E item, int key, LockFreeNode<E> next) {
    this.item = item;
    this.key = key;
    nextValid = new AtomicMarkableReference<LockFreeNode<E>>(next, true);
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
  public LockFreeNode<E> next() {
    return nextValid.getReference();
  }


  public boolean valid() {
    return nextValid.isMarked();
  }

  public boolean setValid() {
    return nextValid.attemptMark(next(), true);
  }

  public boolean setInvalid() {
    LockFreeNode<E> next = next();
    return nextValid.compareAndSet(next, next, true, false);
  }

  boolean setNextIfValid(LockFreeNode<E> expectNext, LockFreeNode<E> newNext) {
    return nextValid.compareAndSet(expectNext, newNext, true, true);
  }

  public LockFreeNode<E> nextValid() {
    boolean[] valid = {true};
    LockFreeNode<E> next = nextValid.get(valid);
    return new FrozenNode<>(next, valid[0]);
  }


  @Override
  public void setNext(Node<E> next) {
    throw new UnsupportedOperationException("Method setNext() is not implemented.");
  }

  @Override
  public void setItem(E item) {

  }

  @Override
  public void setKey(int key) {

  }
}
