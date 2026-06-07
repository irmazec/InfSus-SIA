package com.InfSus.SIA.config;

import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.spring.boot.starter.configuration.impl.DefaultMetricsConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Configuration;

@Configuration
@Primary
public class CamundaConfig extends DefaultMetricsConfiguration {

    @Override
    public void preInit(ProcessEngineConfigurationImpl config) {
        config.setMetricsEnabled(false);
        config.setDbMetricsReporterActivate(false);
    }
}