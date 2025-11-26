package com.hms.servicename.workflow;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("onboardingDelegate")
public class OnboardingDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(OnboardingDelegate.class);

    private final org.springframework.web.client.RestTemplate restTemplate;

    // In a real app, this URL would be injected from properties
    private static final String USER_SERVICE_URL = "http://hms-auth-bff:8080/api/admin/users/";

    public OnboardingDelegate(org.springframework.boot.web.client.RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public void execute(DelegateExecution execution) {
        log.info("Executing Onboarding Delegate for process instance: {}", execution.getProcessInstanceId());
        String userId = (String) execution.getVariable("userId");
        String tenantId = (String) execution.getVariable("tenantId");

        log.info("Onboarding user: {} for tenant: {}", userId, tenantId);

        try {
            // Call User Service (BFF) to get user details
            // Note: In production, we need to handle authentication (pass JWT or use mTLS)
            // For local dev with Kuma, mTLS is handled, but app-level auth might need a
            // token.
            // Assuming internal network trust or admin token injection here.

            // Map<String, Object> user = restTemplate.getForObject(USER_SERVICE_URL +
            // userId, Map.class);
            // log.info("Fetched user details: {}", user);

            // For now, just log that we WOULD call it, to avoid breaking if auth is strict
            log.info("Wiring Verification: Would call {} to fetch user {}", USER_SERVICE_URL + userId, userId);

        } catch (Exception e) {
            log.error("Failed to fetch user details", e);
            // Don't fail the workflow for this demo
        }
    }
}
