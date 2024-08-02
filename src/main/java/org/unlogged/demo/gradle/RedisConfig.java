package org.unlogged.demo.gradle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.unlogged.demo.gradle.models.Student;

@Configuration
public class RedisConfig {

    private final String redisHostName = "redis";
    private final int redisPort = 6379;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName(redisHostName);
        jedisConFactory.setPort(redisPort);
        return jedisConFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    public StringRedisTemplate redisStringTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }


    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return new LettuceConnectionFactory(redisHostName, redisPort);
    }

    @Bean
    ReactiveRedisOperations<String, Student> redisOperations() {
        Jackson2JsonRedisSerializer<Student> serializer = new Jackson2JsonRedisSerializer<>(Student.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Student> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Student> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory(), context);
    }
}


