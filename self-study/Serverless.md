Serverless
===

- ## 구현 내용
![image](https://user-images.githubusercontent.com/21374902/151154723-dc2e7c38-d46a-4c7b-b7c7-df03cb61d3c5.png)

- CloudWatch와 SNS(Simple Notification Service)
- Lambda
- Dynamo DB
- IAM
- API Gateway
- Deploy
- CORS
- S3 Hosting (OAI(Origin Access Identities) 방식)
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
    - sam local invoke -d 9999 HellowWorldFunction\
      - template.yaml에 Resouces 아래 있는 값이 name
      - package.json에 localRoot, port(9999), remoteRoot 추가

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