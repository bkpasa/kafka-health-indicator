package com.bkpasa.health.indicator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeClusterOptions;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

public class KafkaHealthIndicator extends AbstractHealthIndicator {

    public KafkaHealthIndicator() {
        super("kafka health check failed");
    }

    private static final Logger LOG = LoggerFactory.getLogger(KafkaHealthIndicator.class);

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Override
    public void doHealthCheck(Health.Builder builder) throws Exception {

        AdminClient adminClient = createAdminClient();
        DescribeClusterOptions describeOptions = new DescribeClusterOptions().timeoutMs(10_000);
        DescribeClusterResult result = adminClient.describeCluster(describeOptions);

        try {
            String clusterId = result.clusterId().get(10, TimeUnit.SECONDS);
            LOG.info("clusterId={}", clusterId);
            builder.up();
        } catch (ExecutionException | TimeoutException e) {
            LOG.error("Kafka connection failed", e);
            builder.down().withException(e.getCause() == null ? e : e.getCause()).withDetail("bootstrapServers",
                    bootstrapServers);
        }
    }

    private AdminClient createAdminClient() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return AdminClient.create(configs);
    }
}
