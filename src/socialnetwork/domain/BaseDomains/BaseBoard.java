package socialnetwork.domain.BaseDomains;

import java.util.List;
import socialnetwork.domain.Board;
import socialnetwork.domain.Message;
import socialnetwork.domain.SetImplementations.Sequential.SequentialSet;

public class BaseBoard extends SequentialSet implements Board {

  public boolean addMessage(Message message) {
    return super.add(message);
  }

  public boolean deleteMessage(Message message) {
    assert size() >= 0 : "cannot delete from empty board";
    return super.remove(message);
  }

  @Override
  public int size() {
    return super.size();
  }

  @Override
  public List<Message> getBoardSnapshot() {
    return toArrayList();
  }
}
