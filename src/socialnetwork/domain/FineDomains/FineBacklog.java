package socialnetwork.domain.FineDomains;

import java.util.Optional;
import socialnetwork.domain.Backlog;
import socialnetwork.domain.SetImplementations.Fine.FineSet;
import socialnetwork.domain.Task;

public class FineBacklog extends FineSet implements Backlog{

  // Again, Duplication is here because I wanted to compare speeds with
  // coarse grained.

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
