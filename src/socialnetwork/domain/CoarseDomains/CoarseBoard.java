package socialnetwork.domain.CoarseDomains;


import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import socialnetwork.domain.BaseDomains.BaseBoard;
import socialnetwork.domain.Message;

public class CoarseBoard extends BaseBoard {

  private Lock lock = new ReentrantLock();

  @Override
  public boolean addMessage(Message message) {
    lock.lock();
    try {
      return super.addMessage(message);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean deleteMessage(Message message) {
    lock.lock();
    try {
      return super.deleteMessage(message);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public int size() {
    return super.size();
  }

  @Override
  public List<Message> getBoardSnapshot() {
    lock.lock();
    try {
      return super.getBoardSnapshot();
    } finally {
      lock.unlock();
    }
  }

}
