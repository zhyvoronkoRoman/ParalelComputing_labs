package com.edu.authServiceApplication.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wallet-service")
public interface WalletClient {
    @PostMapping("api/wallet/create")
    void createWallet(@RequestParam("userId")Long userId);
}
