package socialnetwork.domain.SetImplementations.LockFree;


import socialnetwork.domain.SetImplementations.Node;

public class FrozenNode<E> extends LockFreeNode<E> {

  private final LockFreeNode<E> next;
  private final boolean valid;

  FrozenNode(LockFreeNode<E> next, boolean valid) {
    super(null, 0);
    this.next = next;
    this.valid = valid;
  }

  @Override
  public LockFreeNode<E> next() {
    return next;
  }

  @Override
  public boolean valid() {
    return valid;
  }


  public boolean setValid() {
    throw new UnsupportedOperationException(
        "Method setValid() is not implemented.");
  }

  public boolean setInvalid() {
    throw new UnsupportedOperationException(
        "Method setInvalid() is not implemented.");
  }

  public boolean setNextIfValid(Node<E> expectNext, Node<E> newNext) {
    throw new UnsupportedOperationException(
        "Method setNextIfValid() is not implemented.");
  }

  public LockFreeNode<E> nextValid() {
    throw new UnsupportedOperationException(
        "Method nextValid() is not implemented.");
  }

  @Override
  public E item() {
    throw new UnsupportedOperationException(
        "Method item() is not implemented.");
  }

  @Override
  public int key() {
    throw new UnsupportedOperationException("Method key() is not implemented.");
  }

  @Override
  public void setItem(E item) {
    throw new UnsupportedOperationException(
        "Method setItem() is not implemented.");
  }

  @Override
  public void setKey(int key) {
    throw new UnsupportedOperationException(
        "Method setKey() is not implemented.");
  }

  @Override
  public void setNext(Node<E> next) {
    throw new UnsupportedOperationException(
        "Method setNext() is not implemented.");
  }
}
