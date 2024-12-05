package com.studentmanagement;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelCastConfig {
    @Bean
    public Config hazelcastCfg(){
        Config config=new Config();
        config.setInstanceName("hazelcast instance")
                .addMapConfig(
                        new MapConfig()
                                .setName("default")

                                .setTimeToLiveSeconds(180)
                                .setMaxIdleSeconds(30)
                );
        return config;
    }
}
