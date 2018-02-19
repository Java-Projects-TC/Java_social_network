package socialnetwork.domain.CoarseDomains;

import java.util.Optional;
import socialnetwork.domain.Backlog;
import socialnetwork.domain.Task;

public class CoarseBacklog implements Backlog {

  @Override
  public boolean add(Task task) {
    return false;
  }

  @Override
  public Optional<Task> getNextTaskToProcess() {
    return null;
  }

  @Override
  public int numberOfTasksInTheBacklog() {
    return 0;
  }
}
