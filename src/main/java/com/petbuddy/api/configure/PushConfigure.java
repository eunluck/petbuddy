package com.petbuddy.api.configure;

import nl.martijndwars.webpush.PushService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.Security;

@Component
@ConfigurationProperties(prefix = "webpush")
public class PushConfigure {

    private String publicKey;
    private String privateKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Bean
    public PushService pushService() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        PushService pushService = new PushService();

        pushService.setPublicKey(publicKey);
        pushService.setPrivateKey(privateKey);
        return pushService;
    }

}
