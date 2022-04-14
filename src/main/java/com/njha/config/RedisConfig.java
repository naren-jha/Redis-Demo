package com.njha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
public class RedisConfig {
    /*
    Here we need two things -
    1 - JedisConnectionFactory that will be used to connect to Redis and
    2 - RedisTemplate that will be used to interact with Redis using jedis connection
    So we'll need these two beans here.
     */

    // Standalone mode jedisConnectionFactory
    /*
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("127.0.0.1");
        redisStandaloneConfiguration.setPort(6379);
        //redisStandaloneConfiguration.setPassword("password");

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        // here we can set attributes for jedisConnectionFactory - like maxPoolSize, timeouts, etc
        return jedisConnectionFactory;
    }
    */


    // Cluster mode jedisConnectionFactory
    // m1
//    @Autowired
//    ClusterConfigurationProperties clusterProperties;

    // m2
//    @Bean
//    public RedisClusterConfiguration redisClusterConfiguration(){
//        Map<String, Object> source = new HashMap<>();
//        source.put("spring.redis.cluster.nodes", clusterNodes);
//        source.put("spring.redis.cluster.timeout", timeout);
//        source.put("spring.redis.cluster.max-redirects", redirects);
//        return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
//    }


    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;

//    @Value("${spring.redis.cluster.timeout}")
//    private Long timeout;

    @Value("${spring.redis.cluster.max-redirects}")
    private int maxRedirects;

//    @Value("${redis.maxIdle}")
//    private int maxIdle;
//
//    @Value("${redis.maxTotal}")
//    private int maxTotal;
//
//    @Value("${redis.maxWaitMillis}")
//    private long maxWaitMillis;
//
//    @Value("${redis.testOnBorrow}")
//    private boolean testOnBorrow;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        //JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(new RedisClusterConfiguration(clusterProperties.getNodes())); // m1

        // m3
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setClusterNodes(getClusterNodes());
        redisClusterConfiguration.setMaxRedirects(maxRedirects);

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration);
        // here we can set attributes for jedisConnectionFactory - like maxPoolSize, timeouts, etc

        return jedisConnectionFactory;
    }

    private Iterable<RedisNode> getClusterNodes() {
        String[] hostAndPorts = StringUtils.commaDelimitedListToStringArray(clusterNodes);
        Set<RedisNode> clusterNodes = new HashSet<>();
        for (String hostAndPort : hostAndPorts) {
            int lastScIndex = hostAndPort.lastIndexOf(":");
            if (lastScIndex == -1) continue;

            try {
                String host = hostAndPort.substring(0, lastScIndex);
                Integer port = Integer.parseInt(hostAndPort.substring(lastScIndex+1));
                clusterNodes.add(new RedisNode(host, port));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return clusterNodes;
    }

    // RedisTemplate remains unchanged irrespective of the mode
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
