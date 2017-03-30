package net.rakugakibox.spring.boot.logback.access;

import net.rakugakibox.spring.boot.logback.access.test.InMemoryLogbackAccessEventQueueAppenderRule;
import net.rakugakibox.spring.boot.logback.access.test.TestControllerConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * The base class for testing to unuse the server port.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        value = {
                "server.useForwardHeaders=true",
                "logback.access.config=classpath:logback-access.in-memory-queue.xml",
                "logback.access.useServerPortInsteadOfLocalPort=false",
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class AbstractServerPortUnusingTest {

    /**
     * The REST template.
     */
    @Autowired
    protected TestRestTemplate rest;

    /**
     * The server port.
     */
    @LocalServerPort
    protected int port;

    /**
     * Creates a test rule.
     *
     * @return a test rule.
     */
    @Rule
    public TestRule rule() {
        return new InMemoryLogbackAccessEventQueueAppenderRule();
    }

    /**
     * Tests a Logback-access event.
     */
    @Test
    public abstract void logbackAccessEvent();

    /**
     * The base class of context configuration.
     */
    @EnableAutoConfiguration
    @Import(TestControllerConfiguration.class)
    public static abstract class AbstractContextConfiguration {
    }

}