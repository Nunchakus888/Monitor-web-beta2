package com.sumscope.cdhplus.web; /**
 * Created by Administrator on 2015/10/23.
 */

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.websockets.extensions.ExtensionHandshake;
import io.undertow.websockets.extensions.PerMessageDeflateHandshake;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.server.standard.UndertowRequestUpgradeStrategy;
import org.xnio.*;

import java.nio.charset.Charset;

@EnableWebMvc
@SpringBootApplication
@ImportResource("classpath:bean-config.xml")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ServletInitializer extends SpringBootServletInitializer {

    @Value("${WORKER.IO.THREADS}")
    private int WORKER_IO_THREADS;

    @Value("${CONNECTION.HIGH.WATER}")
    private int CONNECTION_HIGH_WATER;

    @Value("${CONNECTION.LOW.WATER}")
    private int CONNECTION_LOW_WATER;

    @Value("${WORKER.TASK.CORE.THREADS}")
    private int WORKER_TASK_CORE_THREADS;

    @Value("${WORKER.TASK.MAX.THREADS}")
    private int WORKER_TASK_MAX_THREADS;

    @Value("${WORKER.TASK.KEEPALIVE}")
    private int WORKER_TASK_KEEPALIVE;

    @Value("${WORKER.TASK.LIMIT}")
    private int WORKER_TASK_LIMIT;

    @Bean
    public UndertowRequestUpgradeStrategy serverEndpointExporter() {
        UndertowRequestUpgradeStrategy undertowRequestUpgradeStrategy = new UndertowRequestUpgradeStrategy();
        return undertowRequestUpgradeStrategy;
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    /*@Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }*/

    @Bean(name = "websocketContainerCustomizer")
    public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
        Xnio xnio = Xnio.getInstance("nio",ServletInitializer.class.getClassLoader());
        System.out.println("CONNECTION_HIGH_WATER:" + CONNECTION_HIGH_WATER);
        try {
            XnioWorker worker = xnio.createWorker(OptionMap.builder()
                    .set(Options.WORKER_IO_THREADS, WORKER_IO_THREADS)
                    .set(Options.CONNECTION_HIGH_WATER, CONNECTION_HIGH_WATER)
                    .set(Options.CONNECTION_LOW_WATER, CONNECTION_LOW_WATER)
                    .set(Options.WORKER_TASK_CORE_THREADS, WORKER_TASK_CORE_THREADS)
                    .set(Options.WORKER_TASK_MAX_THREADS, WORKER_TASK_MAX_THREADS)
                    .set(Options.TCP_NODELAY, true)
                    .set(Options.WORKER_TASK_KEEPALIVE, WORKER_TASK_KEEPALIVE)
                    .set(Options.WORKER_TASK_LIMIT, WORKER_TASK_LIMIT)
                    //.set(Options.CORK, true)
                    .set(Options.KEEP_ALIVE, true)
                    .set(Options.WRITE_TIMEOUT,3000)
                    .set(Options.READ_TIMEOUT,3000)
                    .set(Options.REUSE_ADDRESSES,true)
                    .set(Options.COMPRESSION_TYPE, CompressionType.GZIP)
                    .getMap());
            UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();

            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(true, 1000));
            webSocketDeploymentInfo.setWorker(worker);
            ExtensionHandshake extensionHandshake = new PerMessageDeflateHandshake();
            webSocketDeploymentInfo.addExtension(extensionHandshake);
            //webSocketDeploymentInfo.addEndpoint();

            UndertowDeploymentInfoCustomizer deploymentInfo = new UndertowDeploymentInfoCustomizer(){
                @Override
                public void customize(DeploymentInfo deploymentInfo) {
                    System.out.println("deploymentInfo.customize");
                    deploymentInfo.setAllowNonStandardWrappers(true);
                    deploymentInfo.addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME,webSocketDeploymentInfo);
                    deploymentInfo.setDeploymentName("undertow");
                }
            };

            UndertowBuilderCustomizer undertowBuilderCustomizer = new UndertowBuilderCustomizer() {
                @Override
                public void customize(Undertow.Builder builder) {
                    builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true);
                }
            };

            factory.addBuilderCustomizers(undertowBuilderCustomizer);
            factory.addDeploymentInfoCustomizers(deploymentInfo);
            return factory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {

        SpringApplication.run(ServletInitializer.class, args);
    }
}