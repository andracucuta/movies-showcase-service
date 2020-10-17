package com.cucutaa.moviesshowcaseservice;

import com.cucutaa.moviesshowcaseservice.externalservices.MgTechMock;
import org.junit.ClassRule;
import org.mockserver.client.MockServerClient;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MockServerContainer;

@Component
public class ContainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {


    @ClassRule
    public static MockServerContainer mockServer = new MockServerContainer();

    @ClassRule
    public static MockServerClient mockServerClient;

    private MgTechMock mgTechMock;

    @ClassRule
    public GenericContainer redis = new GenericContainer("redis:alpine").withExposedPorts(6379);

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        startContainers();

        mockServerClient = new MockServerClient(mockServer.getHost(), mockServer.getServerPort());

        TestPropertyValues.of(
                "external-services.mg-tech-test.base-path="
                        + "http://localhost:" + mockServer.getServerPort()
        ).applyTo(configurableApplicationContext.getEnvironment());

        mgTechMock = new MgTechMock(mockServerClient);
        mgTechMock.mockGetShowCase();
    }

    public void startContainers(){
        redis.start();
        mockServer.start();
    }
}
