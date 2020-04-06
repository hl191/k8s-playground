package at.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExportStuff {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportStuff.class);

    void doMoreStuff(){
        System.out.println("Doing more stuff");
        LOGGER.error("got a failure ma friend ... jk");
    }
}
