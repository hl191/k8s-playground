package at.home;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExportJob
    implements CommandLineRunner {

    @Autowired
    private ExportStuff exportStuff;

    public static void main(String[] args) {
        SpringApplication.run(ExportJob.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        System.out.println("We are doing da export");
        System.out.println(String.join(", ", args));
        Thread.sleep(3_000L);
        exportStuff.doMoreStuff();
        System.out.println("Finished da thing already");
    }
}
