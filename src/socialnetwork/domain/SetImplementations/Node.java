package socialnetwork.domain.SetImplementations;

public interface Node<E> {

  E item();

  int key();

  Node<E> next();

  void setItem(E item);

  void setKey(int key);

  void setNext(Node<E> next);
}