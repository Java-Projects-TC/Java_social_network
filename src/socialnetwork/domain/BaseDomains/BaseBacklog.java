package socialnetwork.domain.BaseDomains;

import java.util.Optional;
import socialnetwork.domain.Backlog;
import socialnetwork.domain.Task;
import socialnetwork.domain.sequential.SequentialSet;

public class BaseBacklog extends SequentialSet implements Backlog {

  @Override
  public boolean add(Task task) {
    return super.add(task);
  }

  @Override
  public Optional<Task> getNextTaskToProcess() {
    if (size() != 0) {
      Task nextTask = (Task) getFirst().item();
      if (remove(nextTask)) {
        return Optional.of(nextTask);
      }
    }
    return Optional.empty();
  }

  @Override
  public int numberOfTasksInTheBacklog() {
    return super.size();
  }
}
