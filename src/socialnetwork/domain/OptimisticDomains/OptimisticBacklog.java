package socialnetwork.domain.OptimisticDomains;

import java.util.Optional;
import socialnetwork.domain.Backlog;
import socialnetwork.domain.SetImplementations.Optimistic.OptimisticSet;
import socialnetwork.domain.SetImplementations.Optimistic.ReadWriteNode;
import socialnetwork.domain.Task;

public class OptimisticBacklog extends OptimisticSet implements Backlog {

  @Override
  public boolean add(Task task) {
    assert size() >= 0;
    return super.add(task);
  }

  @Override
  public Optional<Task> getNextTaskToProcess() {
    if (size() > 0) {
      ReadWriteNode<Task> nextTask = getFirst();
      if (remove(nextTask.item())) {
        return Optional.of(nextTask.item());
      }
    }
    return Optional.empty();
  }

  @Override
  public int numberOfTasksInTheBacklog() {
    return super.size();
  }
}
