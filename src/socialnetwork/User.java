package socialnetwork;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import socialnetwork.domain.Board;
import socialnetwork.domain.Message;

public class User extends Thread {

  private static final AtomicInteger nextId = new AtomicInteger(0);

  final SocialNetwork socialNetwork;
  private final int id;
  private final String name;

  private final static int SLEEP_INTERVAL = 10;
  private static int maxActions = 100;
  private static int maxRecipients = 10;
  private final Random random;

  User(String username, SocialNetwork socialNetwork) {
    this.name = username;
    this.id = User.nextId.getAndIncrement();
    this.socialNetwork = socialNetwork;
    this.random = new Random(424);
  }

  int getUserId() {
    return id;
  }

  @Override
  public void run() {
    int performedActions = 0;

    while (performedActions++ < maxActions) {
      Board board = socialNetwork.userBoard(this);
      // look at other users boards
      Set<User> possibleRecipients = socialNetwork.getBoards().keySet();
      System.out.println("looking at boards");
      // send a message to some users
      pickAFewUsersAndSendThemAMessage(possibleRecipients);
      System.out.println("sending a message");

      if (random.nextBoolean()) {
        pickOneMessageToBeDeleted(board);
      }
      System.out.println("deleting a message");

      try {
        Thread.sleep(SLEEP_INTERVAL);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      performedActions++;
    }
  }


  public Random getRandom() {
    return random;
  }

  protected void pickAFewUsersAndSendThemAMessage(
      Set<User> possibleRecipients) {
    Set<User> everyoneElse = new HashSet<>();
    everyoneElse.addAll(possibleRecipients);
    assert everyoneElse.remove(this);

    int totalUsersToContact = Math.min(this.maxRecipients, everyoneElse.size());
    if (totalUsersToContact == 0) {
      return; // no users to contact
    }

    final int numUsersToContact = getRandom().nextInt(totalUsersToContact + 1);
    if (numUsersToContact > 0) {
      List<User> recipients = new ArrayList<>(everyoneElse);
      Collections.shuffle(recipients, getRandom());
      recipients = recipients.subList(0, numUsersToContact);
      String messageText = generateNewMessageFor(recipients);
      Message sent = this.socialNetwork
          .postMessage(this, recipients, messageText);
    }
  }

  protected void pickOneMessageToBeDeleted(Board board) {
    List<Message> messagesOnTheBoard = board.getBoardSnapshot();
    List<Message> myMessages = messagesOnTheBoard.stream()
        .filter(m -> m.getSender().getUserId() == this.getUserId()).collect(
            Collectors.toList());
    if (myMessages.isEmpty()) {
      return;
    }

    Collections.shuffle(myMessages, this.getRandom());
    Message chosen = myMessages.get(0);
    assert chosen.getSender().equals(this);

    socialNetwork.deleteMessage(chosen);
  }

  protected String generateNewMessageFor(Collection<User> recipients) {
    return "Message " + " from " + this.getName() + " [" + this.getUserId()
        + "] to " + recipients;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }

  @Override
  public int hashCode() {
    return id;
  }
}
