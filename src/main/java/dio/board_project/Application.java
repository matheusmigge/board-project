package dio.board_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dio.board_project.ui.MainMenu;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);
        MainMenu mainMenu = context.getBean(MainMenu.class);
        mainMenu.execute();
    }
}