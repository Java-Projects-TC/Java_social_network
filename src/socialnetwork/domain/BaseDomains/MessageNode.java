package socialnetwork.domain.BaseDomains;

import socialnetwork.domain.Message;

public class MessageNode extends Message {
  static final MessageNode head = new MessageNode(null, Integer.MIN_VALUE,
      null);
  static final MessageNode tail = new MessageNode(null, Integer.MAX_VALUE,
      null);

  private String text;
  private int id;
  private MessageNode next;

  public MessageNode(String text) {
    this(text, null);
  }

  public MessageNode(String text, MessageNode next) {
    this(text, id.hashCode(), next);
  }

  protected MessageNode(String text, int id, MessageNode next) {
    this.text = text;
    this.id = id;
    this.next = next;
  }

  @Override
  public String text() {
    return text;
  }

  @Override
  public int id() {
    return id;
  }

  @Override
  public MessageNode next() {
    return next;
  }

}
