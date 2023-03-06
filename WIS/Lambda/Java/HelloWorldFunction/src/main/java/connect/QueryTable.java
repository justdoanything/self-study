package connect;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import model.QueryTableResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.HashMap;
import java.util.Map;

public class QueryTable implements RequestHandler<Object, QueryTableResponse> {

    private final Region REGION = Region.AP_NORTHEAST_2;
    private final String tableName = "beaver_amazon_connect_reservation";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(REGION).build();

    @Override
    public QueryTableResponse handleRequest(Object input, Context context) {
        JsonObject json = gson.toJsonTree(input).getAsJsonObject();
        String nickname = json.getAsJsonObject("Details").getAsJsonObject("Parameters").get("nickname").getAsString();
        String contactName = json.getAsJsonObject("Details").getAsJsonObject("ContactData").getAsJsonObject("Attributes").get("contactName").getAsString();
        String phoneNumber = json.getAsJsonObject("Details").getAsJsonObject("ContactData").getAsJsonObject("CustomerEndpoint").get("Address").getAsString();

        HashMap<String,AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put("id", AttributeValue.builder().s("0").build());
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(tableName)
                .key(keyToGet)
                .build();

        try{
            Map<String,AttributeValue> returnedItem = dynamoDbClient.getItem(getItemRequest).item();
            if(returnedItem != null) {
                return QueryTableResponse.builder()
                        .name(returnedItem.get("name").s())
                        .flightNo(returnedItem.get("flightNo").toString())
                        .isReserved(true)
                        .nickname(nickname)
                        .contactName(contactName)
                        .phoneNumber(phoneNumber)
                        .build();
            }else {
                return QueryTableResponse.builder()
                        .isReserved(false)
                        .nickname(nickname)
                        .contactName(contactName)
                        .phoneNumber(phoneNumber)
                        .build();
            }
        }catch (Exception e){
            System.out.println("Logger Exception : " + e.toString());
            return QueryTableResponse.builder()
                    .isReserved(false)
                    .nickname(nickname)
                    .contactName(contactName)
                    .phoneNumber(phoneNumber)
                    .build();
        }
    }
}
