package com.refrigerator.springboot.service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberService {
    private final DefaultMessageService messageService;
    String apiKey = "NCSF2EAU1JXGBGOP";
    String secretKey = "XAU074ZFTZPPNYWVIBGMYAN9PDOS4HZU";
    private String code;

    public PhoneNumberService() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, "https://api.coolsms.co.kr");
    }

    public String createCode() {
        int code = (int) Math.floor((Math.random() * 900000) + 100000);

        return String.valueOf(code);
    }

    public Message createMessage(String to) {
        Message message = new Message();
        message.setFrom("01049004660");
        message.setTo(to);
        message.setText("[내 손 안에 냉장고]\n" + "인증번호는 [" + code + "] 입니다.");

        return message;
    }

    public String sendOne(String to) throws NurigoMessageNotReceivedException, NurigoEmptyResponseException, NurigoUnknownException {
        code = createCode();
        Message message = createMessage(to);
        System.out.println("code : " + code);
        System.out.println("to : " + to);

        messageService.send(message);

        return code;
    }
}





























