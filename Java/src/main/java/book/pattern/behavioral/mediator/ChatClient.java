package book.pattern.behavioral.mediator;

/* Colleague */
public abstract class ChatClient {
    protected ChatServer chatServer;
    protected String name;

    public ChatClient(ChatServer chatServer, String name) {
        this.chatServer = chatServer;
        this.name = name;
    }

    public abstract void sendMessage(String message);
    public abstract void receiveMessage(String message);
}
