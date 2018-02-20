package socialnetwork.domain.sequential;

// Taken from Antonio's gitlab

public class SequentialNode<E> implements Node<E> {
  static final SequentialNode head = new SequentialNode<>(null, Integer.MIN_VALUE, null);
  static final SequentialNode tail = new SequentialNode<>(null, Integer.MAX_VALUE, null);

  private E item;
  private int key;
  private Node<E> next;

  public SequentialNode(E item) {
    this(item, null);
  }

  public SequentialNode(E item, Node<E> next) {
    this(item, item.hashCode(), next);
  }

  protected SequentialNode(E item, int key, Node<E> next) {
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
  public Node<E> next() {
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
    this.next = next;
  }
}
