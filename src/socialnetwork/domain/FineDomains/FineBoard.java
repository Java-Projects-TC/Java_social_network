package socialnetwork.domain.FineDomains;

import java.util.List;
import socialnetwork.domain.Board;
import socialnetwork.domain.Message;
import socialnetwork.domain.SetImplementations.FineSet;

public class FineBoard extends FineSet implements Board{

  // I know I have duplication here with BaseBoard but wanted to keep my
  // coarse implementation to compare the speeds.

  @Override
  public boolean addMessage(Message message) {
    return super.add(message);
  }

  @Override
  public boolean deleteMessage(Message message) {
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
