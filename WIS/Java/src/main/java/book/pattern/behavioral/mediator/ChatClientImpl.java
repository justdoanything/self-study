package book.pattern.behavioral.mediator;

/* Concrete Colleague */
public class ChatClientImpl extends ChatClient {
    public ChatClientImpl(ChatServer chatServer, String name) {
        super(chatServer, name);
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(this.name + " sends message : " + message);
        this.chatServer.sendMessage(this, message);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("==> " + this.name + " receives message : " + message);
    }
}
