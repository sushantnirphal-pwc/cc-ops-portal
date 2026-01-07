package com.company.ccops.approval;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "ccops.security")
public class ApprovalPolicyConfig {
    private List<String> approvalRequiredActions = new ArrayList<>();

    public List<String> getApprovalRequiredActions() { return approvalRequiredActions; }
    public void setApprovalRequiredActions(List<String> approvalRequiredActions) { this.approvalRequiredActions = approvalRequiredActions; }

    public boolean requiresApproval(String policyKey) {
        return approvalRequiredActions.stream().anyMatch(a -> a.equalsIgnoreCase(policyKey));
    }
}
