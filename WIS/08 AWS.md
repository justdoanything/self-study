목차
===
- [ECS와 EC2](#aws-ecs-and-ec2)

---
### 📖 AWS Certified Solutions Architect - Associate를 공부하면서 정리한 내용입니다.
Reference : https://github.com/serithemage/AWSCertifiedSolutionsArchitectUnofficialStudyGuide

---
# 아키텍처에 따른 고려사항
- ### `복원력`을 갖춘 아키텍처
  - 멀티 티어 아키텍처
  - 고가용성 및 내결함성 아키텍처
  - AWS 서비스를 사용하여 결합 해제 메커니즘
  - 적절한 복원력을 갖춘 스토리지 선택
- ### `고성능` 아키텍처
  - 워크로드를 위한 탄력적이고 확장 가능한 컴퓨팅 솔루션
  - 워크로드를 위한 확장 가능한 고성능 스토리지 솔루션
  - 워크로드를 위한 고성능 네트워킹 솔루션
  - 워크로드를 위한 고성능 데이터베이스 솔루션
- ### `안전한` 아키텍처
  - AWS 리소스에 대한 보안 액세스 설계
  - 보안 어플리케이션 계층 설계
  - 적절한 데이터 보안 옵션 선택
- ### `비용`에 최적화된 아키텍처
  - 비용 효율적인 스토리지 솔루션
  - 비용 효율적인 컴퓨팅 및 데이터베이스 솔루션
  - 비용에 최적화된 네트워크 솔루션

# AWS Keywords
<details>

  <summary>펼치기</summary>
  
  ### AWS 기본 기능
  - 컴퓨팅
  - 비용 관리
  - 데이터베이스
  - 재해 복구
  - 고가용성
  - 관리 및 거버넌스
  - 마이크로서비스 및 구성 요소 결합 해제
  - 마이그레이션 및 데이터 전송
  - 네트워킹, 연결 및 콘텐츠 전송
  - 보안
  - 서버리스 설계 원칙
  - 스토리지
  ### AWS 서비스 및 기능
  - ##### 분석
    - Amazon Athena
    - Amazon ES(Amazon Elasticsearch Service)
    - Amazon EMR
    - AWS Glue
    - Amazon Kinesis
    - Amazon QuickSight
  - ##### AWS 결제 및 비용 관리:
    - AWS Budgets
    - Cost Explorer
  - ##### 애플리케이션 통합
    - Amazon SNS(Amazon Simple Notification Service)
    - Amazon SQS(Amazon Simple Queue Service)
  - ##### 컴퓨팅
    - Amazon EC2
    - AWS Elastic Beanstalk
    - Amazon ECS(Amazon Elastic Container Service)
    - Amazon EKS(Amazon Elastic Kubernetes Service)
    - Elastic Load Balancing
    - AWS Fargate
    - AWS Lambda
  - ##### 데이터베이스
    - Amazon Aurora
    - Amazon DynamoDB
    - Amazon ElastiCache
    - Amazon RDS
    - Amazon Redshift
  - ##### 관리 및 거버넌스
    - AWS Auto Scaling
    - AWS Backup
    - AWS CloudFormation
    - AWS CloudTrail
    - Amazon CloudWatch
    - AWS Config
    - Amazon EventBridge(Amazon CloudWatch Events)
    - AWS Organizations
    - AWS Resource Access ManagerAWS Systems Manager
    - AWS Trusted Advisor
  - ##### 마이그레이션 및 전송
    - AWS DMS(AWS Database Migration Service)
    - AWS DataSync
    - AWS Migration Hub
    - AWS SMS(AWS Server Migration Service)
    - AWS Snowball
    - AWS Transfer Family
  - ##### 네트워킹 및 콘텐츠 전송
    - Amazon API Gateway
    - Amazon CloudFront
    - AWS Direct Connect
    - AWS Global Accelerator
    - Amazon Route 53
    - AWS Transit Gateway
    - Amazon VPC(및 관련 기능)
  - #### 보안, 자격 증명 및 규정 준수
    - ACM(AWS Certificate Manager)
    - AWS Directory Service
    - Amazon GuardDuty
    - AWS IAM(Identity and Access Management)
    - Amazon Inspector
    - AWS KMS(AWS Key Management Service)
    - Amazon Macie
    - AWS Secrets Manager
    - AWS Shield
    - AWS Single Sign-On
    - AWS WAF
  - #### 스토리지
    - Amazon EBS(Amazon Elastic Block Store)
    - Amazon EFS(Amazon Elastic File System)
    - Amazon FSx
    - Amazon S3
    - Amazon S3 Glacier
    - AWS Storage Gateway
</details>

---

## AWS 글로벌 인프라의 이해

Keyword | 개념 | 사용하는 시기와 상황
---|---|---
Region | AWS 서버가 존재하는 위치
Aavailable Zone | 각 Region은 여러개의 가용영역으로 나눠져 있고 가용영역은 서로 분리되어 있다.
Local Zones | 짧은 대기 시간을 요구하는 Application을 최종 사용자와 더 가까운 위치에서 제공 | 동영상 렌더링 및 그래픽 집약적인 가상 데스크톱 애플리케이션 등 한 자릿수 밀리초 단위의 대기 시간이 요구되는 워크로드를 더 많은 위치에서 실행하도록 고안된 새로운 유형의 AWS 인프라입니다. 어떤 고객은 고유한 온프레미스 데이터 센터를 운영하길 원하지만 로컬 데이터 센터를 완전히 없애길 원하는 고객도 있을 수 있습니다. AWS Local Zones를 사용하는 고객은 자체 데이터 센터 인프라를 보유 및 운영할 필요 없이 컴퓨팅 및 스토리지 리소스를 최종 사용자에게 더 가까이에 두는 이점을 누릴 수 있습니다.
Wave Length | 5G 네트워크에서 AWS 컴퓨팅 및 스토리지 서비스를 포함하여 매우 낮은 대기 시간의 애플리케이션을 개발하고 배포하며 확장하기 위한 모바일 엣지 컴퓨팅 인프라를 제공 | AWS 인프라, 서비스 API 및 도구를 5G 네트워크로 확장하여 지연 시간이 짧은 애플리케이션을 5G 디바이스에 제공하도록 설계되었습니다. Wavelength는 스토리지 및 컴퓨팅 서비스를 이동 통신 사업자의 5G 네트워크 내에 포함하여 개발자가 IoT 디바이스, 게임 스트리밍, 자율 주행 차량, 라이브 미디어 제작 등 10밀리초 미만의 지연 시간이 요구되는 새로운 5G 최종 사용자를 위한 애플리케이션을 손쉽게 제작할 수 있게 합니다.
Outposts | 하이브리드 환경을 위해 거의 모든 온프레미스 또는 엣지 로케이션에 AWS 인프라 및 서비스를 제공하는 완전관리형 솔루션 패밀리 | 대기 시간 요구 사항으로 인해 온프레미스에 유지해야 하는 워크로드를 위해 설계되었습니다. 그러나 이 경우에도 고객은 온프레미스 워크로드가 AWS 워크로드와 원활하게 실행되길 원합니다. AWS Outposts는 AWS에서 설계한 하드웨어로 제작된 구성 가능한 완전관리형 컴퓨팅 및 스토리지 랙으로, 이를 통해 고객은 컴퓨팅 및 스토리지를 온프레미스에서 실행하는 동시에 AWS의 광범위한 클라우드 서비스에 원활하게 연결할 수 있습니다
- Amazon CloudFront와 Edge Network

## 클라우드 서비스를 설계하는 방법
Storage | Data
---|---
Amazon RDS | Structure Data
Amazon CloudSearch | Search Indices
DynamoDB | Meta Data
Amazon Kinesis | Event Logs
Amazon EBS | FS Blocks
Ephemeral EC2 Storage | Temp Files
Amazon S3 | Static Assets
Amazon Glacier | Back-Ups

## 느슨한 결합을 구현하기 위한 서비스들
- ### ELB (Elastic Load Balancing)
  Elastic Load Balancing | 용도
  ---|---
  Application Load Balancer | HTTP 요청을 로드 밸런싱
  Network Load Balancer | 네트워크/전송 프로토콜(4계층 - TCP, UDP) 로드 밸런싱의 경우와 고도의 성능이 요구되거나 대기 시간이 낮아야 하는 애플리케이션
  Gateway Load Balancer | Amazon Elastic Compute Cloud(Amazon EC2) Classic 네트워크 안에 구축된 경우
  Classic Load Balancer | 서드 파티 가상 어플라이언스를 배포하고 실행해야 하는 경우
- ### `SQS (Simple Queue Service)` & `SNS (Simple Notification Service)`
  - `SNS (Simple Notification Service)`와 다른 점
    - `SNS`는 Application에서 정기적으로 update를 확인하거나 polling할 필요 없이 push만 하면 구독자에게 메세지를 보낼 수 있다. `SQS`는 분산 Application에서 Polling 모델을 통해 메세지를 교환하는데 사용되고 송신 요소와 수신 요소를 분리해서 사용할 수 있다.
  - `Amazon MQ`와 다른 점
    - 기존에 사용 중인 Messaging Application을 Cloud로 이동할 때 `Amazon MQ`를 사용하는게 유리하다. 업계 표준 API와 Protocol을 지원하기 때문에 다른 Message Brocker에서 Amazon MQ로 전환할 수 있다. Cloud에 새로운 Application을 구성한다면 `SQS`, `SNS`를 사용하는 것이 좋습니다.
  - `AKS (Amazon Kinesis Streams)`와 다른 점
    - `SQS`는 MSA와 같은 분산 Application에서 유용하며 배달 못한 편지 대기열과 포이즌 필(poison-pill) 관리 같은 일반적 미들웨어 구성체를 제공하고 웹 서비스 API도 제공합니다.
    - `AKS`는 빅 데이터 스트리밍을 실시간으로 처리하고 주어진 파티션 키에 대한 모든 레코드를 동일한 레코드 프로세서에 제공하므로 스트림 데이터를 읽는 여러 개의 Application를 구축할 때 용이합니다.
  - SQS는 FIFO 대기열을 사용하기 때문에 메세지 순서를 보장한다.
  - 표준 대기열의 각 메세지는 최소 1회 전달을 보장한다.
  - ReceiveMessage, DeleteMessage 작업이 별도로 존재한다. 사용자가 실제로 메세지를 수신했는지 상관없이 해당 메세지를 대기열에 그대로 보관한다. 사용자의 요청에 따라 메세지를 삭제할 수 있다.
  - SQS는 SSE(Server Side Encrpyt)를 지원합니다.

## 안정적이고 복원력을 갖춘 스토리지 선택하기
- Keyword : EC2, EBS, EFS, Galcier, Storage Gateway
- `Amazon EBS`
  - 내구성이 있는 블록 수준 스토리지 볼륨을 제공하고 실행 중인 인스턴스에 연결하는 것이 가능
  - 세분화된 업데이트를 자주 수행하는 데이터
  - 인스턴스에서 데이터베이스를 실행할 때 권장
  - 스냅샷을 사용하여 S3에 저장하면 데이터 백업 사본 유지
- `Amazon EC2 Instance Store`
  - 여러 인스턴스는 호스트 컴퓨터에 물리적으로 연결된 디스크 스토리지에 액세스할 수 있는데 이러한 스토리지를 인스턴스 스토어 라고 한다.
  - 인스턴스 스토어는 연관 인스턴스 수명기간에만 유지되고 인스턴스를 중단하거나 최대 절전 모드가 되면 데이터가 손실된다.
- `Amazon EFS (File System)`
  - Amazon EC2에서 시용할 수 있는 확장 가능한 File Storage
  - 여러 인스턴스에서 실행하는 Application에 대한 공통 데이터 소스로 사용 가능
- `Amazon S3`
  - 저렴한 비용
  - 데이터 및 애플리케이션의 백업 복사본을 저장
  - EBS 스냅샷과 인스턴스 스토어 지원 AMI를 저장
---

AWS ECS and EC2
===
- #### ECS (Elastic Container Service)
  - AWS 카테고리로 봤을 땐 `Container Orchestration`에 속한다.
  - Cluster에서 Container를 손쉽게 실행, 중지 및 관리할 수 있게 해주는 컨테이너 관리 서비스 
  - 간단한 API 호출을 통해서 Container 기반의 Application을 제어할 수 있습니다.
  - 리소스, 격리 정책, 가용성 요구사항을 기반으로 Cluster에 Container 배치를 예약할 수 있습니다.
  - 일관된 배포 및 구축 환경을 생성할 수 있고 배치 및 ETL(Extract-Transform-Load) 워크로드를 관리할 수 있습니다.
  - VPC 내에 Amazon ECS Cluster를 생성할 수 있고 Coinater Image는 ECR(Elastic Container Registry)에 저장됩니다.
  - `Task Definition` : JSON 타입의 파일로 사용할 자원, 실행할 container 개수 등을 정의한다. 여러개의 Task를 정의해서 Application을 시작할 때 시작시킬 수 있다.
  - `Task` and `Scheduling` : Task는 Cluster 내에서 Task Definition 기반으로 수행되는 인스턴스입니다. Scheduling은 cluster 내에서 수행할 task를 관리한다.
  - `ECS Cluster` : ECS Cluster는 Task 또는 Service의 논리적 그룹입니다. Cluster에 1개 이상의 EC2를 등록하고 여기에서 Task를 실행할 수 있습니다. 
  - `Container Agent` : ECS Cluster 내에 있는 Container 안에서 실행되고 Resource 사용률에 대한 정보를 ECS 로 전송합니다. Agent는 ECS로부터 요청을 받을때마다 시작되거나 중지된다.
  ![image](https://user-images.githubusercontent.com/21374902/150706403-5c721f9c-5b06-412d-8018-e46c3fffe862.png)
- #### EC2 (Elastic Compute Cloud)
  - AWS 카테고리로 봤을 땐 `Computing Option` 이고 `Cloud Computing Service` 라고도 불린다.
  - AWS 내에 컴퓨터 1개를 구성하는 것과 비슷한 개념으로 AWS가 제공하는 URL(Public DNS)을 통해 이 컴퓨터에 접근할 수 있다. (=> 기존에는 H/W를 구매해서 `컴퓨터`를 구성했지만 AWS에선 클릭 몇번으로 더 쉽게 구매와 설정이 가능해지도록 만들었다. )
  - 사용한만큼 비용을 내며 생성/삭제가 아주 간단하다.
  - SSH를 통해 외부에서 이 컴퓨터에 연결할 수 있다.
  - 보안, 네트워킹 그리고 스토리지 관리에 유용하다.
  - EC2가 제공하는 기능
    - AMI (Amazon Machine Image) : 서버에 필요한 운영체제와 여러 소프트웨어들이 적절히 구성된 상태로 제공되는 템플릿으로 인스턴스를 쉽게 만들 수 있다.
    - CPU, Memory, Storage, Network 등 여러 종류의 구성을 제공
    - Key Pair를 사용하여 인스턴스 로그인 정보 보호
    - Instance Store Volumn : 임시 데이터를 저장하는 스토리지 볼륨으로 인스턴스 중단, 최대 절전 모드로 전환 또는 종료 시 삭제됨
    - EBS (Elastic Block Store)를 사용해서 영구 Storage에 데이터 저장 가능


- Reference
  - https://docs.aws.amazon.com/ko_kr/AmazonECS/latest/developerguide/Welcome.html
  - https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/concepts.html
  - https://d1.awsstatic.com/training-and-certification/docs-sa-assoc/AWS-Certified-Solutions-Architect-Associate_Exam-Guide.pdf
---