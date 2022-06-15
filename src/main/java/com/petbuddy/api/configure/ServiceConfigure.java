package com.petbuddy.api.configure;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.petbuddy.api.aws.S3Client;
import com.petbuddy.api.model.commons.EnumMapper;
import com.petbuddy.api.model.pet.PersonalityType;
import com.petbuddy.api.util.MessageUtils;
import com.zaxxer.hikari.HikariDataSource;
import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ServiceConfigure {



  @Bean
  @Profile("test")
  public DataSource testDataSource() {
    DataSourceBuilder<? extends DataSource> factory = DataSourceBuilder
      .create()
      .driverClassName("org.h2.Driver")
      .url("jdbc:h2:mem:test_petbuddy_server;MODE=PostgreSQL;DB_CLOSE_DELAY=-1");
    HikariDataSource dataSource = (HikariDataSource) factory.build();
    dataSource.setPoolName("TEST_H2_DB");
    dataSource.setMinimumIdle(1);
    dataSource.setMaximumPoolSize(1);
    return new Log4jdbcProxyDataSource(dataSource);
  }

  @Bean
  public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
    MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(messageSource);
    MessageUtils.setMessageSourceAccessor(messageSourceAccessor);
    return messageSourceAccessor;
  }
/*
  @Bean
  public Jwt jwt(JwtTokenConfigure jwtTokenConfigure) {
    return new Jwt(jwtTokenConfigure.getIssuer(), jwtTokenConfigure.getClientSecret(), jwtTokenConfigure.getExpirySeconds());
  }*/

  @Bean
  public AmazonS3 amazonS3Client(AwsConfigure awsConfigure) {
    return AmazonS3ClientBuilder.standard()
      .withRegion(Regions.fromName(awsConfigure.getRegion()))
      .withCredentials(
        new AWSStaticCredentialsProvider(
          new BasicAWSCredentials(
            awsConfigure.getAccessKey(),
            awsConfigure.getSecretKey())
        )
      )
      .build();
  }

  @Bean
  public S3Client s3Client(AmazonS3 amazonS3, AwsConfigure awsConfigure) {
    return new S3Client(amazonS3, awsConfigure.getUrl(), awsConfigure.getBucketName());
  }

  @Bean
  public Jackson2ObjectMapperBuilder configureObjectMapper() {
    // Java time module
    JavaTimeModule jtm = new JavaTimeModule();
    jtm.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
      @Override
      public void configure(ObjectMapper objectMapper) {
        super.configure(objectMapper);
        objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
      }
    };
    builder.serializationInclusion(JsonInclude.Include.NON_NULL);
    builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    builder.modulesToInstall(jtm);
    return builder;
  }

  @Bean
  public EnumMapper enumMapper(){
    EnumMapper enumMapper = new EnumMapper();
    enumMapper.put("PersonalityType", PersonalityType.class);
    return enumMapper;
  }

}