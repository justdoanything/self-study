# Serverless

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
    const callAPI = async (request) => {
      const response = await fetch(request.url, {
        method: "POST", // POST, GET, PUT, DELETE, ...
        mode: "cors",
        cache: "no-cache", // no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        redirect: "follow",
        referrer: "no-referrer",
        body: request.data ? JOSN.stringify(request.data) : null,
      });
      console.log(response);
    };
    ```
  - Amazon API Gateway에서 CORS 설정과 API Key 설정을 할 수 있다.
- S3 Hosting (OAI(Origin Access Identities) 방식)
  - `Create S3` ▻ 속성 ▻ 정적 웹 사이트 호스팅 ▻ index.html 설정하고 Endpoint 확인
  - 짜놓은 코드 파일을 `S3`에 업로드 (index.html, app.js, ...)
  - `S3`는 반드시 public 이어야 함 (보안성 취약, 불필요한 비용 증가 우려)
  - `CloudFront`를 사용해서 배포하기
    - `S3`를 private로 만들고 코드 파일 업로드
    - `Create CloudFront` ▻ `Create Distribution` ▻ `Origin Domain Name` 설정 ▻ `OAI` 설정 ▻ Default Root Object = index.html 설정
  - 🌟 AWS `임시 요청 Redirection` 문제
    - `CloudFront`를 생성할 때 `Origin Domain Name`에 `S3 Bucket`의 URL을 설정해준다. 이 때, Region을 제외하면 임시 요청 Redirection 문제가 발생한다.\
      _(예시 : https://{bucket-name}.s3.{region}.amazonaws.com)_
    - URL에 Region을 명시하지 않으면 Client의 요청이 다른 Region으로 갈 수 있다.
    - 예를들어, `US Region`으로 갔다면 `S3 Bucket`이 없기 때문에 AWS 라우터는 내부적으로 `Seoul Region`으로 가라고 알려주고 이 정보가 `Edge Location`에 전파된다.
    - 하지만 이 때, `Seoul Region`에 있는 `S3 주소(IP)`로 Redirect가 되도록하는데 `S3 Bucket`이 `Private` 라면 데이터를 정상적으로 받을 수 없게 된다.
    - `CloudFront`가 해야할 서비스는 사용자가 직접 `S3 Bucket`으로 접근하는게 아니라 `CloudFront`의 `OAI`를 통해서 받아온 `S3`의 자료를 미리 `Edge Location`에 뿌려놨어야 한다. 하지만 `임시 요청 Redirection`을 하면 `S3`에 직접 요청하기 때문에 문제가 발생한다.
    - `ClloudFront`에 `Origin Domain Name`을 선택할 때 URL에 반드시 Region 이름이 들어가야 한다.
- Cloud Watch Dashboard
  - Resource 상황을 볼 수 있는 Dashboard
  - Cloud Watch Alram을 통해서 에러가 발생했을 때 알람을 보낼 수 있다.
- AWS X-Ray & AWS Lambda Layer

  - aws-xray-sdk 설치 : `npm install aws-xray-sdk`
  - 설치한 폴더를 압축한 후 AWS Lambda ▻ 계층 ▻ 계층 생성 ▻ 압축 파일 업로드
  - AWS Lambda ▻ 함수 ▻ Add Layer
  - AWS Lambda를 사용하는 js 코드에 X-Ray 추가

    ```js
    // AWS X-Ray 사용을 위해 수정
    //var AWS = require("aws-sdk");
    var AWSXray = require("aws-xray-sdk");
    var AWS = AWSXray.captureAWS(require("aws-sdk"));
    var dynamoDB = new AWS.DynamoDB.DocumentClient({apiVersion: "2022-01-01"});

    exports.handler = async event => {
      // 상세정보를 X-Ray에 남기도록 추가
      var segment = AWSXray.getSegment();
      var subSegment = segment.addNewSubsegment("main");
      subSegment.addAnnotation("App", "Main Lambda");
      ...

      subSegment.addMetadata("Exception", exception);
      subSegment.addMetadata("Event", event);
      subSegment.addMetadata("Parameter", params);
      subSegment.close();
    }
    ```

- SAM(Serverless Application Model)
  - Create AWS account
  - Create an IAM user with Administrator Permissions.
    Admin User 생성 (root 계정 사용은 위험)
  - Install AWS CLI
    - Windows : https://awscli.amazonaws.com/AWSCLIV2.msi
    - Mac
      - `curl "https://awscli.amazonaws.com/AWSCLIV2.pkg" -o "AWSCLIV2.pkg"`
      - `sudo installer -pkg AWSCLIV2.pkg -target /`
    - `aws --version`
  - Install Docker
  - Install AWS SAM CLI
    - Windows : https://github.com/aws/aws-sam-cli/releases/latest/download/AWS_SAM_CLI_64_PY3.msi
    - Mac
      - `brew tap aws/tap`
      - `brew install aws-sam-cli`
      - 설치하다가 에러가 발생 시 아래 폴더 삭제 : `rm -rf /opt/homebrew/Library/Taps/homebrew/homebrew-core`\
        https://github.com/aws/homebrew-tap/issues/207
    - `sam --version`

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
  - `sam init`
    - 초기 `template.yml` 생성
    - `template.yml`을 root 경로로 이동
  - 

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
