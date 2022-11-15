package edu.um.cps3230;

import edu.um.cps3230.testdoubles.Api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiRequest {

    Api api;
    String API_URL = "https://api.marketalertum.com/Alert";
    public ApiRequest(){}

    public void setApiStatus(Api api){
        this.api = api;
    }

    public int postAlert(String jsonBody) throws Exception {

        if (api != null) {
            if (api.getApi() == Api.AVAILABLE) {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());
                return response.statusCode();
            } else{
                return -1;
            }
        }
        return -1;
    }


            //Method used to remove alerts by sending a DELETE request
            public int removeAlert() throws Exception {
                if (api != null) {
                    if (api.getApi() == Api.AVAILABLE) {
                        HttpClient client = HttpClient.newHttpClient();

                        //Make a delete request to the server
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://api.marketalertum.com/Alert?userId=aba2df1c-5441-4581-9dc2-5413c9691825"))
                                .header("Content-Type", "application/json").DELETE().build();

                        //Send the request
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        System.out.println(response.body());

                        //Return the response body
                        return response.statusCode();
                    }
                }else return -1;

                return -1;
            }

}
