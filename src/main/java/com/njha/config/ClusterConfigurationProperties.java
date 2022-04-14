package com.njha.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class ClusterConfigurationProperties {

    // https://stackoverflow.com/questions/41918560/redis-cluster-integration-with-spring-boot
    /*
     * spring.redis.cluster.nodes[0] = 127.0.0.1:7000
     * spring.redis.cluster.nodes[1] = 127.0.0.1:7001
     * spring.redis.cluster.nodes[1] = 127.0.0.1:7002
     */
    List<String> nodes;



    /**
     * Get initial collection of known cluster nodes in format {@code host:port}.
     *
     * @return
     */
    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
}