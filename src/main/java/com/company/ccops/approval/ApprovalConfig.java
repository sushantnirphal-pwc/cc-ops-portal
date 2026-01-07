package com.company.ccops.approval;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ApprovalPolicyConfig.class)
public class ApprovalConfig {}
