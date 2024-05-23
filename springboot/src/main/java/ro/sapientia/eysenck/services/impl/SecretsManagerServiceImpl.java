package ro.sapientia.eysenck.services.impl;

import org.springframework.stereotype.Service;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import ro.sapientia.eysenck.services.SecretsManagerService;

@Service
public class SecretsManagerServiceImpl implements SecretsManagerService {
    public String getSecret(String secretName) {
        // Create an instance of the AWS Secrets Manager client
        AWSSecretsManager secretsManager = AWSSecretsManagerClientBuilder.defaultClient();

        // Create a request to get the secret value
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);

        // Retrieve the secret value
        GetSecretValueResult getSecretValueResult = secretsManager.getSecretValue(getSecretValueRequest);

        // Return the secret value
        return getSecretValueResult.getSecretString();
    }
}
