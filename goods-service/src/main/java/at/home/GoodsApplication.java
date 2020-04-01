package at.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.kubernetes.HazelcastKubernetesDiscoveryStrategyFactory;
import com.hazelcast.kubernetes.KubernetesProperties;
import com.hazelcast.spi.properties.ClusterProperty;

@SpringBootApplication
public class GoodsApplication {

    private static final String DEFAULT_FALSE = "false";
    private static final String HAZELCAST_SERVICE_NAME = "service-hazelcast-server.default.svc.cluster.local";
    private static final String MANCENTER_SERVICE_NAME = "service-hazelcast-management-center";

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        JoinConfig joinConfig = config.getNetworkConfig().getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);

        HazelcastKubernetesDiscoveryStrategyFactory hazelcastKubernetesDiscoveryStrategyFactory = new HazelcastKubernetesDiscoveryStrategyFactory();
        DiscoveryStrategyConfig discoveryStrategyConfig = new DiscoveryStrategyConfig(hazelcastKubernetesDiscoveryStrategyFactory);
        discoveryStrategyConfig.addProperty(KubernetesProperties.SERVICE_DNS.key(), HAZELCAST_SERVICE_NAME);
        config.setProperty(ClusterProperty.DISCOVERY_SPI_ENABLED.toString(), "true");
        joinConfig.getDiscoveryConfig().addDiscoveryStrategyConfig(discoveryStrategyConfig);

        return config;
    }

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }

}
