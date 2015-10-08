package org.eu.mmacedo.integration.winnpipes;



import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:META-INF/spring/integration/spring-integration-context.xml")
public class Application implements CommandLineRunner {
	
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);   	
//        ConfigurableApplicationContext ctx = new SpringApplication("classpath:META-INF/spring/integration/plc-context.xml").run(args);
        System.out.println("Hit Enter to terminate");
        System.in.read();
        ctx.close();	
    }

	@Override
	public void run(String... arg0) throws Exception {
		for (String string : arg0) {
			System.out.println(string);
		}
	
	}
}
