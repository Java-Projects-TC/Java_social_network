package socialnetwork.domain;

import java.util.Optional;
import socialnetwork.domain.Task.Command;

public class Worker extends Thread {

  private final Backlog backlog;
  private boolean interrupted = false;

  public Worker(Backlog backlog) {
    this.backlog = backlog;
  }

  @Override
  public void run() {
    while (!interrupted) {
      Optional<Task> nextTask = backlog.getNextTaskToProcess();
      if (!nextTask.isPresent()) {
        try {
          Thread.sleep(5);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      process(nextTask.get());
    }
  }

  public void interrupt() {
    this.interrupted = true;
  }

  public void process(Task nextTask) {
    if (nextTask.command.equals(Command.POST)) {
      nextTask.board.addMessage(nextTask.message);
    }
    if (nextTask.command.equals(Command.DELETE)) {
      if (!nextTask.board.deleteMessage(nextTask.message)) {
        backlog.add(nextTask);
      }
    }
    }
}
