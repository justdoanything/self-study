Serverless
===

- ## 구현 내용
![image](https://user-images.githubusercontent.com/21374902/151154723-dc2e7c38-d46a-4c7b-b7c7-df03cb61d3c5.png)

- CloudWatch와 SNS(Simple Notification Service)
  - 비용 관련 제한 설정을 하고 초과했을 시 알람이 올 수 있도록 할 수 있다.
- AWS Lambda
  - 별도의 Back-End Instance를 구성하지 않고 Lambda를 생성해서 처리할 수 있다.
  - `Create AWS Lambda`를 하고 `트리거`와 `대상`을 추가해주면 된다.
  - Nodejs, Java, Python, Ruby, .NET, Go 언어를 지원한다.
- Amazon DynamoDB
  - `Create Amazon DynamoDB`를 하고 `항목`을 설정해서 Attribute를 설정할 수 있다.
  - AWS Lambda에서 Nodejs를 사용해 DynamoDB를 연결하는 Sample
    ```js
    // AWS API를 사용하기 위한 준비
    var AWS = require("aws-sdk");
    var dynamoDB = new AWS.DynamoDB.DocumentClient({apiVersion: "2022-01-01"});

    exports.handler = async event => {
      console.log("Received : " + JSON.stringify(event, null, 2));
      let response = "";
      try {
        var params = {
          // FilterExpression: "Year = :this_year",
          // ExpressionAttributeValues: { ":this_year": 2015 },
          TableName: "sampleTable"
        };
        
        console.log("Params : " + params);
        const cards = await dynamoDB.scan(params).promise();

        response = {
          statusCode: 200,
          body: JSON.stringify(cards);
        };
      } catch(exception){
        console.error(exception);
        response = {
          statusCode: 500,
          body: JSON.stringify({"Message : ": exception})
        };
      }
      return response;
    }
    ```
- IAM
  - 각 Resource의 접근 권한 설정
- API Gateway
  - `Create Amazon API Gateway`를 하고 `Resource 생성`으로 Domain 구조를 잡는다.
  - `Method 생성`으로 AWS Lambda와 연결해줄 수 있다.
  - `API 배포`를 통해서 배포하고 URL를 알아낼 수 있다.
  - API Gateway를 배포할 때 `요율(1초에 처리할 수 있는 요청수)`과 `버스트(한 번에 처리할 수 있는 요청수)`를 설정할 수 있다.
- CORS
  - Client단에서 API를 호출하는 예제
    ```js
    const callAPI = async request => {
      const response = await fetch(request.url, {
        method: "POST", // POST, GET, PUT, DELETE, ...
        mode: "cors",
        cache: "no-cache", // no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin",
        headers: {
          "Content-Type": "application/json",
          "Accept": "application/json"
        },
        redirect: "follow",
        referrer: "no-referrer",
        body: request.data ? JOSN.stringify(request.data) : null
      });
      console.log(response)
    };
    ```
  - Amazon API Gateway에서 CORS 설정과 API Key 설정을 할 수 있다.
- S3 Hosting (OAI(Origin Access Identities) 방식)
  - `Create S3` -> 속성 -> 정적 웹 사이트 호스팅 -> index.html 설정하고 Endpoint 확인
  - 짜놓은 코드를 S3에 업로드 (index.html, app.js, ...)
- Cloud Watch Dashboard
- Lambda Layer
- X-Ray SDK
- SAM(Serverless Application Model)
  - Create AWS account
  - Create an IAM user with Administrator Permissions.
    Admin User 생성 (root 계정 사용은 위험)
  - Install AWS CLI 
  - Install Docker
  - Install AWS SAM CLI
    - sam init
      - template.yaml is important
    - sam build
    - sam deploy --guided
    - sam local start-api 
    - sam local invoke -d 9999 HellowWorldFunction
      - template.yaml에 Resouces 아래 있는 값이 name
      - package.json에 localRoot, port(9999), remoteRoot 추가
    - sam validate
- SAM 구성
  - Lambda 함수 이름 지정
  - API Gateway 생성
  - Cloud Watch 활성화
  - X-Ray 활성화
  - CORS 활성화
  - API-Key 설정
  - 사용량 계획
- 테스팅
  - sam local start-api
    - gateway를 통해 테스트, sam을 local에서 구동해서 local에 api 호출해서 테스트
  - sam local invoke
    - api gateway 없이 lambda로만 테스트, 
    - sam lcao invoke {template.yml에 있는 Function 이름}
    - gateway 없이 lambda로만 테스트하면 event 객체가 없기 때문에 events 폴더를 만들고 sam local generate-event 명령어를 활용할 수 있다.
    - sam local generate-event apigateway aws-proxy > event.json : 샘플을 event.json에 write 해줌. (body 수정)
    - sam local invoke -e lambda/postcard/events/event.json -d 5858 PostCardsFunction
- s3에 cmd로 파일 올리기
  - aws s3 ls
  - aws s3 sync ./frontend s3://{bucket name}

![image](https://user-images.githubusercontent.com/21374902/154208609-e7f15006-d53b-4a00-a25f-ac563c04b4a4.png)

- IaC

![image](https://user-images.githubusercontent.com/21374902/154211592-0593c68d-d9dd-4a37-a02d-14a319a34256.png)

- SAM

![image](https://user-images.githubusercontent.com/21374902/154211518-468ea1ee-3c73-43d8-9ad7-916b59407568.png)


---
### Reference
- https://www.inflearn.com/course/AWS-서버리스-웹앱
- https://github.com/hp-edu/awskanbanboard

### Implement
- https://github.com/justdoanything/awskanbanboard
---