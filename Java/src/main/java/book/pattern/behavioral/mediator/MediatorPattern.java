package book.pattern.behavioral.mediator;

public class MediatorPattern {
    public static void main(String[] agrs) {
        ChatServer chatServer = new ChatServerImpl();
        ChatClient john = new ChatClientImpl(chatServer, "John");
        ChatClient cool = new ChatClientImpl(chatServer, "Cool");
        ChatClient dan = new ChatClientImpl(chatServer, "Dan");
        ChatClient tony = new ChatClientImpl(chatServer, "Tony");

        chatServer.addUser(john);
        chatServer.addUser(cool);
        chatServer.addUser(dan);
        chatServer.addUser(tony);
        System.out.println();

        john.sendMessage("Hello, guys.");
        System.out.println();

        cool.sendMessage("Nice to meet you guys.");
        System.out.println();

        chatServer.deleteUser(john);
        System.out.println();

        dan.sendMessage("Oh, John has left this chat.");
        System.out.println();
    }
}
