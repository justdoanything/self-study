package book.pattern.behavioral.mediator;

/* Mediator */
public interface ChatServer {
    void addUser(ChatClient chatClient);
    void deleteUser(ChatClient chatClient);
    void sendMessage(ChatClient chatClient, String message);
}
