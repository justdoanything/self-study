package book.pattern.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

/* Concrete Mediator */
public class ChatServerImpl implements ChatServer {
    private final List<ChatClient> chatClients;

    public ChatServerImpl() {
        this.chatClients = new ArrayList<>();
    }

    @Override
    public void addUser(ChatClient chatClient) {
        System.out.println("[SERVER] " + chatClient.name + " joined this chat.");
        this.chatClients.add(chatClient);
    }

    @Override
    public void deleteUser(ChatClient chatClient) {
        System.out.println("[SERVER] " + chatClient.name + " has left this chat.");
        this.chatClients.remove(chatClient);
    }

    @Override
    public void sendMessage(ChatClient chatClient, String message) {
        for(ChatClient client: this.chatClients) {
            if(client != chatClient){
                client.receiveMessage(message);
            }
        }
    }
}
