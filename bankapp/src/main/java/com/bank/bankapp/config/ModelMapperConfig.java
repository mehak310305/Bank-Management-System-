package com.bank.bankapp.config;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.*;
@Configuration
public class ModelMapperConfig {
      @Bean
    public ModelMapper modelmapper(){
        return new ModelMapper();
    }
}
