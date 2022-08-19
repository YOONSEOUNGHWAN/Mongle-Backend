package com.rtsj.return_to_soju.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.*;
import com.rtsj.return_to_soju.model.dto.dto.FcmMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseCloudMessageService{
    @Value("${fcm.key.name}")
    public String keyName;
    @Value("${fcm.key.scope}")
    public String fcmScope;

    @Value("${fcm.api.url}")
    public String fcmUrl;

    private final ObjectMapper objectMapper;

    public void sendMessageListWithToken(List<String> fcmTokenList, String title, String body){
        List<Message> messages = fcmTokenList.stream().map(
                token -> Message.builder()
                        .putData("time", LocalDateTime.now().toString())
                        .setNotification(new Notification(title, body))
                        .setToken(token)
                        .build())
                .collect(Collectors.toList());

        BatchResponse response;

        try{
            response = FirebaseMessaging.getInstance().sendAll(messages);
            if(response.getFailureCount()>0){
                List<SendResponse> responses = response.getResponses();
                List<String> failedTokens = new ArrayList<>();
                for(int i=0; i<responses.size(); i++){
                    if(!responses.get(i).isSuccessful()){
                        failedTokens.add(fcmTokenList.get(i));
                    }
                }
                log.error("List of tokens are not valid FCM token : " + failedTokens);
            }
        }catch (FirebaseMessagingException e){
            log.error("Cannot send to memberList push message. error info : {}", e.getMessage());
        }
    }

    public void sendMessageTo(String targetToken, String title, String body) throws IOException{
        OkHttpClient client = new OkHttpClient();
        String message = makeMessage(targetToken, title, body);

        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json;charset=utf-8"));
        Request request = new Request.Builder()
                .url(fcmUrl)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION,"Bearer"+getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE,"application/json;UTF-8")
                .build();
        Response response = client.newCall(request)
                .execute();
        System.out.println(response.body().string());
    }

    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image("statics/image/행복.png")
                                .build()
                        ).build()
                )
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);

    }

    private String getAccessToken() throws IOException{
        String firebaseConfigPath = "firebase/"+keyName;

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath)
                        .getInputStream())
                .createScoped(List.of(fcmScope));
        googleCredentials.refreshIfExpired(); //access를 생성하기 위함.
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
