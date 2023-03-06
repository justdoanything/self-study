package connect;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import model.ScanTableResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.HashMap;
import java.util.Map;

public class ScanTable implements RequestHandler<Object, ScanTableResponse> {

    private final Region REGION = Region.AP_NORTHEAST_2;
    private final String tableName = "beaver_amazon_connect_reservation";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(REGION).build();

    @Override
    public ScanTableResponse handleRequest(Object input, Context context) {
        JsonObject json = gson.toJsonTree(input).getAsJsonObject();
        String phoneNumber = json.getAsJsonObject("Details").getAsJsonObject("Parameters").get("customerNumber").getAsString();

        Map<String, AttributeValue> where = new HashMap<>();
        where.put(":phoneNumber", AttributeValue.builder().s(phoneNumber).build());

        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(tableName)
                .expressionAttributeValues(where)
                .filterExpression("phoneNumber = :phoneNumber")
                .build();

        try{
            ScanResponse response = dynamoDbClient.scan(scanRequest);
            System.out.println(response.items().toString());
            if(!response.hasItems())
                return ScanTableResponse.builder()
                    .isReserved(false)
                    .build();
            else
                return ScanTableResponse.builder()
                        .isReserved(true)
                        .name(response.items().get(0).get("name").s())
                        .flightNo(response.items().get(0).get("flightNo").s())
                        .build();
        }catch (Exception e){
            System.out.println("Logger Exception : " + e.toString());
            return ScanTableResponse.builder()
                    .isReserved(false)
                    .build();
        }
    }
}
