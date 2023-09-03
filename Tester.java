package LoggingApi.Task2;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tester {
    private static Logger logger = Logger.getLogger("Chat!");
    static {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);
        consoleHandler.setLevel(Level.ALL);
    }
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please write your email:");
        String email= sc.nextLine();
        System.out.print("Please write your login:");
        String login = sc.nextLine();
        if(authorization(email,login)){
            System.out.print("Welcome "+login);
            Files.writeString(Path.of("C:\\Users\\viktusi5\\IdeaProjects\\FourthModule\\src\\LoggingApi\\Task2\\RegistrationTime"),"Email: %s\nLogin: %s\nTime: %s\n\n".formatted(email,login, LocalDateTime.now()),StandardOpenOption.APPEND);
            System.out.print(". Who are you sending your message? Please write his/her email:");
            String receiver = sc.nextLine();
            if(authorization(receiver)){
                System.out.print("Write your message:");
                String message = sc.nextLine();
                Chat chat = new Chat(email,receiver,LocalTime.now(),message);
                Files.writeString(Path.of("C:\\\\Users\\\\viktusi5\\\\IdeaProjects\\\\FourthModule\\\\src\\\\LoggingApi\\\\Task2\\\\DataBaseOfUserMessages"),"From: %s\nTo: %s\nTime: %s\nMessage: %s\n\n".formatted(chat.from,chat.to,chat.dateTime,chat.message), StandardOpenOption.APPEND);
                logger.log(Level.INFO,"Message has been sent successfully");
            }
            else logger.log(Level.INFO,"Wrong email,Please try again!");
        }
        else logger.log(Level.INFO,"Wrong login or email, Please try again!");
    }
    public static boolean authorization(String email,String login){
        Pattern patternEmail = Pattern.compile("(\\w+@([\\w-]+))\\.(\\w{2,4})");
        Matcher matcher = patternEmail.matcher(email);
        Pattern patternLogin = Pattern.compile("\\w{8,16}");
        Matcher matcher2 = patternLogin.matcher(login);
        return matcher2.matches() && matcher.matches();
    }
    public static boolean authorization(String email){
        Pattern patternEmail = Pattern.compile("(\\w+@([\\w-]+))\\.(\\w{2,4})");
        Matcher matcher = patternEmail.matcher(email);
        return matcher.matches();
    }
}
class Chat{
    public Chat(String from, String to, LocalTime dateTime, String message) {
        this.from = from;
        this.to = to;
        this.dateTime = dateTime;
        this.message = message;
    }

    String from;
    String to;
    LocalTime dateTime;
    String message;
}
