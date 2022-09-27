package com.vmo.chatapp2.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmo.chatapp2.account.dao.AccountDao;
import com.vmo.chatapp2.account.form.AccountForm;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class OkHttpClientCommon {

    public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
     OkHttpClient client = new OkHttpClient.Builder()
             .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
             .writeTimeout(5, TimeUnit.MINUTES) // write timeout
             .readTimeout(5, TimeUnit.MINUTES) // read timeout
             .build();    // socket timeout

    public String access(String API_URL) throws IOException {
        Request request = new Request.Builder()
                .url(API_URL)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();

        }
    }

//    public static void main(String[] args) throws IOException {
//        OkHttpClientCommon ok = new OkHttpClientCommon();
//        AccountForm form = new AccountForm("nam123","123456","nam123","2.jpg","nam@gmail.com");
//        ok.create("http://localhost:8080/api/auth/signup",form);
//    }

    /**
     * @POST
     */
    public CommonResponse create(String API_URL,Object o) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, createJsonData(o));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
//            ResponseBody result = ResponseBody.create(JSON, createJsonData(response.body()));
//            System.out.println("createOrder: " + result);
//            int num = Integer.parseInt(result.substring(0,0));
            String str = response.body().string();
            System.out.println(str);
            Object obj = createObjectData(str);
            return (CommonResponse) obj;

        }
    }

    /**
     * @GET http://localhost:8080/RestfulWebServiceExample/rest/orders/1
     */
    public String retrieve(String API_URL,Long id) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(API_URL).newBuilder();
        urlBuilder.addPathSegment(String.valueOf(id));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * @PUT http://localhost:8080/RestfulWebServiceExample/rest/orders
     */
    public CommonResponse update(String API_URL,Object o,Long id) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, createJsonData(o));
        HttpUrl.Builder urlBuilder = HttpUrl.parse(API_URL).newBuilder();
        urlBuilder.addPathSegment(String.valueOf(id));
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
//                .header("Authorization", "Bearer " + token)
                .url(url)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            System.out.println(str);
            Object obj = createObjectData(str);
            return (CommonResponse) obj;
        }
    }

    /**
     * @DELETE http://localhost:8080/RestfulWebServiceExample/rest/orders/1
     */
    public void delete(String token,String API_URL, Long id) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(API_URL).newBuilder();
        String id2 = String.valueOf(id);
        urlBuilder.addPathSegment(id2);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + token)
                .url(url)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            System.out.println("deleteOrder: " + result);
        }
    }

    private String createJsonData(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(o);
            System.out.println(json);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object createObjectData(String string) {
        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(getMyBook());
//        Object obj = mapper.convertValue(orderData, Object.class);
//        return obj;
        try {
            Object book = mapper.readValue(string, CommonResponse.class);
            System.out.println(string);
            return book;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Object> createListObjectData(String string) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Object> participantJsonList = mapper.readValue(string, new TypeReference<>(){});
            System.out.println(string);
            return participantJsonList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
