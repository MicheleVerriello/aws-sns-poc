package com.micheleverriello.snssmspoc.controllers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sns")
public class SimpleNotificationServiceController {

    @GetMapping("/sms/{receiverPhoneNumber}")
    public String sendSMS(@PathVariable("receiverPhoneNumber") String receiverPhoneNumber) {

        // Create AWS credentials
        String accessKey = "your_access_key";
        String secretKey = "your_secret_key";

        // Get access to AWS
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        // Create an SNS client
        AmazonSNS snsClient = AmazonSNSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_NORTH_1) // Replace 'EU_NORTH_1' with the desired AWS region
                .build();

        // Build message
        String message = "Hello from Amazon SNS! This is a test SMS.";

        // Create an SMS request
        PublishRequest publishRequest = new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(receiverPhoneNumber); // Receiver

        // Send SMS
        PublishResult publishResult = snsClient.publish(publishRequest);

        // Return a response indicating the status of the SMS sending
        return "SMS sent. Message ID: " + publishResult.getMessageId();
    }
}
