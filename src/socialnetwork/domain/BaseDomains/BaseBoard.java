package socialnetwork.domain.BaseDomains;

import java.util.List;
import socialnetwork.domain.Board;
import socialnetwork.domain.Message;

public class BaseBoard implements Board{

  int size = 0;

  private Position find(Message start, int id) {
    Message pred, curr;
    curr = start;
    do {
      pred = curr;
      curr = curr.next();
    } while (curr.key() < key);  // until curr.key >= key
    return new Position(pred, curr);
  }

  @Override
  public boolean addMessage(Message message) {
    return false;
  }

  @Override
  public boolean deleteMessage(Message message) {
    return false;
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public List<Message> getBoardSnapshot() {
    return null;
  }

  private static class Position<Message> {

    public final Message pred, curr;

    public Position(Message pred, Message curr) {
      this.pred = pred;
      this.curr = curr;
    }
  }

}
