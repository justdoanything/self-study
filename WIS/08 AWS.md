목차
===
- [AWS Certified](#aws-certified-architecture-associate)
  - [AWS Certified Architecture Associate](#aws-certified-architecture-associate) 
  - [AWS Certified Developer Associate](#aws-certified-developer-associate)
- [Quick Dictionary](#quick-dictionary)
  - [Storage Service](#storage-service)
  - [Global Infra](#global-infra)
  - [ELB](#elastic-load-balancing)
- [ECS와 EC2](#aws-ecs-and-ec2)
- [Cognito](#cognito)
- [Amazon Connect](#amazon-connect)
- [Amazon Lambda](#amazon-lambda)
  - [기본 개념](#1-기본개념)
  - [DynamoDB](#2-lambda-with-dynamodb)


---
AWS Certified Architecture Associate
===
### 📖 AWS Certified Solutions Architect - Associate를 공부하면서 정리한 내용입니다.
Reference : https://github.com/serithemage/AWSCertifiedSolutionsArchitectUnofficialStudyGuide

### 아키텍처에 따른 고려사항
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

### AWS Keywords
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

### AWS 글로벌 인프라의 이해

Keyword | 개념 | 사용하는 시기와 상황
---|---|---
Region | AWS 서버가 존재하는 위치
Aavailable Zone | 각 Region은 여러개의 가용영역으로 나눠져 있고 가용영역은 서로 분리되어 있다.
Local Zones | 짧은 대기 시간을 요구하는 Application을 최종 사용자와 더 가까운 위치에서 제공 | 동영상 렌더링 및 그래픽 집약적인 가상 데스크톱 애플리케이션 등 한 자릿수 밀리초 단위의 대기 시간이 요구되는 워크로드를 더 많은 위치에서 실행하도록 고안된 새로운 유형의 AWS 인프라입니다. 어떤 고객은 고유한 온프레미스 데이터 센터를 운영하길 원하지만 로컬 데이터 센터를 완전히 없애길 원하는 고객도 있을 수 있습니다. AWS Local Zones를 사용하는 고객은 자체 데이터 센터 인프라를 보유 및 운영할 필요 없이 컴퓨팅 및 스토리지 리소스를 최종 사용자에게 더 가까이에 두는 이점을 누릴 수 있습니다.
Wave Length | 5G 네트워크에서 AWS 컴퓨팅 및 스토리지 서비스를 포함하여 매우 낮은 대기 시간의 애플리케이션을 개발하고 배포하며 확장하기 위한 모바일 엣지 컴퓨팅 인프라를 제공 | AWS 인프라, 서비스 API 및 도구를 5G 네트워크로 확장하여 지연 시간이 짧은 애플리케이션을 5G 디바이스에 제공하도록 설계되었습니다. Wavelength는 스토리지 및 컴퓨팅 서비스를 이동 통신 사업자의 5G 네트워크 내에 포함하여 개발자가 IoT 디바이스, 게임 스트리밍, 자율 주행 차량, 라이브 미디어 제작 등 10밀리초 미만의 지연 시간이 요구되는 새로운 5G 최종 사용자를 위한 애플리케이션을 손쉽게 제작할 수 있게 합니다.
Outposts | 하이브리드 환경을 위해 거의 모든 온프레미스 또는 엣지 로케이션에 AWS 인프라 및 서비스를 제공하는 완전관리형 솔루션 패밀리 | 대기 시간 요구 사항으로 인해 온프레미스에 유지해야 하는 워크로드를 위해 설계되었습니다. 그러나 이 경우에도 고객은 온프레미스 워크로드가 AWS 워크로드와 원활하게 실행되길 원합니다. AWS Outposts는 AWS에서 설계한 하드웨어로 제작된 구성 가능한 완전관리형 컴퓨팅 및 스토리지 랙으로, 이를 통해 고객은 컴퓨팅 및 스토리지를 온프레미스에서 실행하는 동시에 AWS의 광범위한 클라우드 서비스에 원활하게 연결할 수 있습니다
- Amazon CloudFront와 Edge Network

### 클라우드 서비스를 설계하는 방법
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

### 느슨한 결합을 구현하기 위한 서비스들
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

### 안정적이고 복원력을 갖춘 스토리지 선택하기
- Keyword : EC2, EBS, EFS, Galcier, Storage Gateway
- `Amazon EBS (Elastic Block Store)`
  - 내구성이 있는 블록 수준 스토리지 볼륨을 제공하고 실행 중인 인스턴스에 연결하는 것이 가능
  - 세분화된 업데이트를 자주 수행하는 데이터
  - 인스턴스에서 데이터베이스를 실행할 때 권장
  - 스냅샷을 사용하여 S3에 저장하면 데이터 백업 사본 유지
  - FAQ
    - 7가지의 볼륨 유형을 제공하고 2개의 큰 카데고리로 분류할 수 있다. 
    - SSD : 트랜잭션 워크로드를 위한 SSD 지원 스토리지(주로 IOPS, 지연 시간 및 내구성이 성능을 좌우)
    - HDD : 처리량 워크로드를 위한 HDD 지원 스토리지(주로 MB/초로 측정한 처리량이 성능을 좌우)
    - 단일 구성 요소의 장애로 인한 데이터 손실을 방지하기 위해 가용 영역의 여러 서버에 복제되며, 이에 따른 추가 요금도 없음
- `Amazon S3 (Simple Storage Service)`
  - 저렴한 비용
  - 데이터 및 애플리케이션의 백업 복사본을 저장
  - EBS 스냅샷과 인스턴스 스토어 지원 AMI를 저장
- `Amazon EC2 Instance Store`
  - 여러 인스턴스는 호스트 컴퓨터에 물리적으로 연결된 디스크 스토리지에 액세스할 수 있는데 이러한 스토리지를 인스턴스 스토어 라고 한다.
  - 인스턴스 스토어는 연관 인스턴스 수명기간에만 유지되고 인스턴스를 중단하거나 최대 절전 모드가 되면 데이터가 손실된다.
  - 버퍼, 캐시, scratch 데이터 및 기타 임시 콘텐츠와 같이 자주 변경되는 정보의 임시 스토리지나 로드가 분산된 웹 서버 풀과 같은 여러 인스턴스상에서 복제되는 데이터에 가장 적합
- `Amazon EFS (Elastic File System)`
  - Amazon EC2에서 시용할 수 있는 확장 가능한 File Storage
  - 여러 인스턴스에서 실행하는 Application에 대한 공통 데이터 소스로 사용 가능
- S3 vs EBS & EFS vs FSx
  - S3 vs EBS
    - `S3`는 data를 bucket에 저장하고 metadata를 포함하고 있기 때문에 여러 인스턴스에서 참조하거나 사용하기 용이하다. 하지만 `EBS`는 metadat를 포함하고 있지 않고 큰 데이터에 대한 속도와 안정성을 보장하기 때문에 계층 정보 등 큰 용량의 데이터를 다룰 때 사용한다.
  - `EBS`는 EC2에서 실행되는 어떤 OS 시스템이 구동될 떄 필요한 볼륨을 제공한다. 높은 확장성(transaction-heavy workloads)을 요구되는 인스턴스들을 위해 제공한다. 아주 큰 Enterprise급 App에서 빅데이터 분석과 같은 작업을 빠른 시간으로 처리해야할 때 적합하다. 
    - `Block 단위로 저장한다는 의미`는 각 블록은 유일한 식별자를 갖고 있으며 블록 끼리는 metadata과 같은 연관 관계 정보는 갖고 있지 않다. 블록은 가장 효율적인 곳에 저장되기 때문에 하나의 서버/운영체제에 상주할 필요가 없고 분리되어 있기 때문에 매우 빠르고 안정적이며 효율적인 데이터 액세스를 제공한다.
    - 데이터베이스 서버와 같은 많은 IO 작업을 짧은 대기 시간으로 처리해야하는 앱에 이상적입니다. 
  - EC2 Instance Store vs EBS
    - EC2 Instance Store는 EC2를 hosting 하고 있는 물리적 서버에 위치한 SSD Storage 이다.
    - EC2 인스턴스를 위한 임시 블록 수준 저장소이다.
    - EC2 Instance는 버퍼 캐시, 스풀링 디렉터리와 같은 임시 저장소가 필요할 떄 사용해야 하고 인스턴스가 중단되면 사라지기 때문에 영구적인 데이터가 필요하면 EBS를 사용해야 한다.
  - EFS vs FSx
    - 주요 차이점은 FSx는 Windows 환경과 통합된다는 것
    - EFS는 Linux 및 Mac EC2 인스턴스와 온프레미스 컴퓨팅 리소스에 탑재할 수 있는 확장 가능한 완전 관리형 NFS 파일 시스템
    - EFS는 앱 중단 없이 페타바이트 규모의 데이터에 도달할 수 있는 탄력적이고 확장 가능한 스토리지를 제공합니다. 이 서비스는 일관되게 짧은 지연 시간으로 수천 개의 Amazon EC2 인스턴스와 함께 작동합니다.
    - EFS를 사용하면 공유 파일 시스템을 클라우드와 호환할 수 있으며 코드를 크게 변경하지 않고도 간단하게 통합할 수 있다.
    - EFS의 단점으론 파일 기반 스토리지가 IOPS(Input/Output Per Second) 기준으로 블록 스토리지와 동일한 성능 수준을 제공하지 않는다는 것입니다. 또한 NTFS 및 NFS와 같은 표준 프로토콜에서만 작동하기 때문에 Windows와 호환되지는 않습니다.
    - FSx는 Windows Server 메시지 블록 서비스를 실행하는 관리되는 Windows Server 환경을 제공한다.
  - Windws Server 요구사항에 최적화 하려면 `FSx`
  - 개별 EC2 instance에 대해 짧은 지연 시간과 고속 데이터 액세스가 필요하면 `EBS`
  - File System 기반의 시스템을 사용하고 여러 EC2 instance나 Linux, MacOS instance에 연결하면 `EFS`
  - 이 외에는 `S3`를 사용한다. 
  - Reference : [ebs-efs-fsx-s3-how-these-storage-options-differ](https://pilotcoresystems.com/insights/ebs-efs-fsx-s3-how-these-storage-options-differ/)

⚠️⚠️⚠️ 개념을 공부하는 것보단 기출문제와 FAQ 위주로 봐야할 것 같다!


---
<details>
<summary><font size="5"><b>Match Keywords</b></font></summary>

순번| 문제 | 답안
---|---|---
1 | 웹사이트의 DNS 레코드가 Route 53에 호스트되어 있고 도메인이 ALB를 바라보고 있을때 정적인 에러 페이지를 보여주려면 ? | `Route 53 active-passive failover configuration`을 설정해서 ALB가 바라보는 end-point가 비정상이면 S3에 있는 정적인 에러 페이지를 보여준다.
2 | HPC workload, EC2 인스턴스끼리 연결이 필요하고 빠른 네트워크 응답속도와 많은 연결을 견디려면 ? | 하나에 AZ에 하나의 cluster placement group으로 EC2 인스턴스를 구동하면 된다.
3 | 유저들은 전세계에서 다른 region에서 접근하고 GB 이상의 데이터를 올리고 다운받는다. 대기 시간 최소화, 성능 최대 활용, 효율적인 비용을 구현하려면 ? | EC2의 Auto Scaling을 사용하고 CloudFront를 어플리케이션에 호스팅한다.
4 | 온프로미스에서 AWS로 데이터 마이그레이션한다. DFSR은 쓰는 Windows 파일 서버 팜에 파일을 저장하고 파일 서버 팜을 교체해야한다면 ? | Amazon FSx 사용
5 | 두 부분으로 나눠서 동작하고 두번째가 더 오래 걸린다. EC2에 2개의 마이크로 서비스를 올리려고 하는데 두 서비스를 어떻게 통합 ? | 마이크로서비스1이 SQS 대기열로 데이터를 보내고 마이크로서비스2가 처리
6 | 여러 웹사이트의 click stream data를 일괄 처리한다. 실시간으로 처리, 적은 자원 소모로 streaming data 처리해야 한다. 어떤 조합으로 ? | AWS Lambda, `Amazon Kinesis Data Firehouse`
7 | ALB, EC2, Auto Scaling Group에서 동작하는 Application이 있다. 매월 1일 자정에 일괄 처리 작업 때문에 CPU 사용률이 100%가 되는데 어떤 조합으로 해결할까 ? | 일정에 맞게 EC2의 Auto Scaling을 스케쥴해서 사용하면 된다.
8 | ALB 뒤에 EC2, EC2 Auto Scaling Group, Auroa DB를 사용하는 Application이 있다. 요청 속도가 증가함에 따른 탄력성을 높인 설계에 필요한 것은 ? | `AWS Global Accelerator`, ALB 앞에 Amazon CloudFront 배포
9 | 데이터베이스 읽기가 높은 I/O를 유발하고 데이터베이스에 대한 쓰기 요청에 대기 시간을 추가한다는 것을 발견했습니다. 기 요청과 쓰기 요청을 분리하기 위해 무엇을 해야 할까? | 읽기 전용 replica를 만들고 적절한 엔드포인트를 사용하도록 애플리케이션을 수정합니다. 
10 | 데이터를 Migration 해야한다. 보안 네트워크 연결이 필요하고 일회성 데이터 Migration과 지속적인 데이터 Migration이 필요한 상황이다. | 초기 전송엔 `Snowball`, 지속적인 연결엔 `Direct Connect`
11 | ALB 뒤에 여러개의 EC2가 있다. 최근 저작권 제한이 변경되어 CIO(최고 정보 책임자)가 특정 국가에 대한 액세스를 차단하려고 합니다. | Amazon CloudFront를 사용하여 애플리케이션을 제공하고 차단된 국가에 대한 액세스를 거부합니다.
12 | 많은 데이터를 저장하는 App. 여러 AZ에 배포된 EC2 Linux에서 매시간 분석 및 수정된다. 6개월 동안 용량이 계속 증가될 것 이라고 예측된다. | EFS에 저장하고 파일 시스템을 App에 마운트한다.
13 | 3계층 App을 AWS로 Migration. App은 Mysql 필요. 기존에 App은 다양한 실시간 보고서를 만들어서 성능이 좋지 않았다. 이를 위한 솔루션은? | Aurora Mysql를 다중 AZ DB 클러스터에 생성하고 실시간 보고서를 위한 multi reads replicas를 만든다. App이 여러 reader endpoint를 사용하도록 설정한다.
14 | 여러 EC2에 분산 DB를 배포. 초당 수백만 건의 트랜잭션을 위한 짧은 대기시간과 처리량이 있는 block storage가 필요하다. 이를 위한 솔루션은 ? | Amazon EC2 instance store
15 | 정적 HTML로 온라인에 게시. 수백만의 조회수가 예상됨. 정적 파일은 S3에 있을 때 어떻 조치를 해야할까? | S3 버킷을 origin으로 CloudFront와 사용한다.
16 | 트랜잭션은 예상할 수 없고 초당 0~500개 이상이 될 수 있다. Back-end DB에 유지해야할 데이터는 1GB 미만이고 key-value 요청을 사용한다고 했을때 필요한 서비스는 ? | Lambda, DynamoDB
17 | App은 ALB + EC2 조합이고 us-east-1에 존재. us-west-1이 증가함에 따라 대기 시간이 짧고 가용성이 좋은 솔루션은 ? | us-west-1에 EC2를 프로비져닝하고 ALB를 설정한다. Global Accelerator에 두 리전의 ELB를 포함하는 Group을 사용하는 accelerator 생성
18 | 사용자는 커스텀 이미지를 제출할 수 있다. 이를 위한 매개변수는 API Gateway API로 전송되는 모든 요청에 있고 사용자 커스텀 이미지는 요청 시 생성되며 이를 보거나 다운로드할 수 있는 링크를 제공받는다. 이를 위한 솔루션은? | Lambda를 사용해서 사용자의 요청으로 원본 이미지를 조작한다. 원본 이미지와 커스텀된 이미지를 S3에 저장하고 S3 버킷의 오리진으로 CloudFront에 설정한다.
19 | 회사에 중요한 데이터를 S3로 Migration. 현재는 단일 리전(us-east-1)에 단일 S3 버킷을 사용한다. 재해 복구 정책에 따르면 데이터는 여러 리전에 있어야 한다. 이를 위한 S3 솔루션은 ? | 다른 리전에서 버전 관리를 사용하여 추가 S3 버킷을 생성하고 리전 간 복제를 구성합니다.
20 | App은 VPC 안에서 EC2 위에서 동작한다. App은 하나의 S3 API를 호출해서 객체를 저장하고 읽어야 한다. 보안 정책은 App의 인터넷 바인딩을 제한한다. 보안을 유지하면서 수정해야할 사항은 ? | `S3 Gateway Enpoint`을 설정한다.
21 | PostgreSQL DB를 사용하는 App. 매월 초 결산 때 대규모 쿼리를 수행함. App에 미치는 영향을 줄이고자 한다. 무엇을 해야할까 ? | Read Replica를 만들고 그 Replica로 트래픽을 보내면 된다.
22 | HPC App과 데이터를 AWS로 Migration. App이 구동되는 동안은 고성능 병렬 스토리지와 계층 스토리지를 쓰고 있다. 구동되지 않을 때는 데이터를 저장하기 위해서 cold 스토리지를 사용하고 있다.| S3의 `cold data storage`, FSx의 `Lustre for high-performance parallel storage`
23 | 단일 리전 EC2 인스턴스 App. 재해 발생 시 두번째 리전에 배포할 수 있는지 확인해야 한다. | 두번째 리전에서 `AMI`으로 새로운 EC2 인스턴스를 구동해야 한다. EC2에 AMI를 복사하고 대상에 맞는 두번째 리전을 지정한다.
24 | VPC의 EC2 인스턴스에서 DynamoDB에 대한 API 호출이 인터넷을 통과하지 않도록 해야 합니다. | api endpoint에 대한 라우트 테이블을 만들고 DynamoDB를 위한 gateway endpoint를 만들어야 한다.
25 | 기존 App은 암호화가 되어 있지 않은 Amazon RDS Mysql을 사용한다. 모든 데이터를 암호화하기 위해선 ? | RDS 인스턴스의 스냅샷을 만들고 그 스냅샷의 암호화된 복사본을 만든다. 그리고 암호화된 스냅샷에서 RDS 인스턴스를 복원한다.
26 | IoT 센서가 수천개 있고 AWS로 데이터를 보내야한다. App은 순서대로 이벤트를 수신하고 나중에 추가로 처리하기 위한 데이터 저장 솔루션이 필요하다. | 각 센서에 있는 하나의 대기열을 SQS 표준 대기열을 사용해서 실시간으로 처리하고 SQS 대기열에서 AWS Lambda 함수를 트리거하여 S3에 데이터를 저장
27 | ALB 뒤에 EC2. 동적/정적 컨텐츠가 복합적으로 있고 전 세계 사용자들이 속도가 너무 느리다고 한다. | CloudFront를 생성헤서 ALB를 오리진으로 설정한다. `Route 53`이 CloudFront를 라우팅하도록 설정합니다.
28 | RDS 인스턴스에 분석 데이터 저장, API를 사용하여 접근, App은 비활성 될 수 있지만 몇초 안에 엄청난 트래픽을 받을 수 있다. | API Gateway와 Lambda 사용
29 | 매월 1일 20개의 EC2를 실행하고 7일동안 실행됩니다. 7일동안 중단할 수 없다고 했을 때 비용 절감을 위한 것은 ? | `Scheduled Reserved Instances`
30 | 4 계층에서 사용자와 통신하는 게임을 위해 단일 AZ에 EC2를 보유하고 있다. 고가용성 및 비용 효율성을 위해 할 것은 ? | EC2 인스턴스 앞단에 Network Load Balancer를 구성하고 Auto Scaling Group를 사용해서 여러 AZ에서 인스턴스를 자동으로 추가하고 삭제하도록 합니다.
31 | RDS Mysql 사용. 자동 백업. 암호화 하지 않음. 앞으론 암호화해서 백업하고 암호화 되지 않은 데이터는 삭제하려고 한다. 지난 백업을 삭제하기 전에 암호화해서 백업을 만들 예정이다. 미래 데이터 백업을 위해 활성화해야할 것은 ? | 데이터베이스를 스냅샷하고 암호화 스냅샷으로 복사한다. 암호화한 스냅샷으로 데이터베이스를 다시 만든다.
32 | 멀티 ALB에 웹싸이트 호스팅. 나라마다 다른 컨텐츠 배포권이 있고 유저에 맞는 컨텐츠를 전달해야 한다. | Route 53에 `a geolocation policy`를 설정한다.
33 | 새로 만든 AWS 계정에 root 계정 접근을 막아야 한다. | 루트 유저가 강력한 비밀번호를 사용하고 있는지 확인. 루트 유저가 multi-factor를 사용하는지 확인.
34 | 로그를 S3에 저장. 얼마나/어떤 로그에 접근하는지 예측할 수 없다. 효율적인 비용을 고려했을 때 어떤 S3를 선택해야할까 ? | `S3 Intelligent-Tiering`
35 | EC2 Instance. Auto Scaling in ALB. ALB는 CloudFront 배포를 위한 Origin. SQL Injection 방얼르 위해 WAF 사용. 외부 악성 IP가 감지됨. 어떻게 방어해야할까? | WAF에 악성 IP 주소를 막는 조건을 추가한다.
36 | 첫번째 App은 동기이면서 빠르게 응답해야함. 두번째는 시간이 오래 걸려서 여러 컴포넌트로 나눠지는걸 고려해야함. 주문은 한번에 처리되야하고 받은 순서대로 처리해야 한다. | SNS topic을 생성하고 SQS FIFO가 구독하도록한다. 
37 | App은 AWS Cloud에 배포됨. Web Layer와 Database Layer로 two-tier 구조를 가져야함. XSS에 취약한 상태이다. | ALB를 만들고 ALB 뒤에 App를 구성하고 WAF를 활성화한다.
38 | RDS Mysql, Multi-AZ 사용. 외부 시스템이 DB에서 데이터를 가져갈 때 속도가 느려진다. 웹싸이트의 응답 속도가 느려지는 원인이 됨. | RDS DB instance에 read replica를 추가하고 외부 시스템이 read replica에서 데이터를 가져가도록 한다.
39 | EC2, Multi-AZ. Auto Scaling in ALB. CPU 가동률이 40%일 때 가장 잘 구동한다. 이를 지키기 위해 어떻게 해야할까? | `target tracking policy`를 사용해서 Auto Scaling 그룹을 동적으로 확장한다.
40 | EC2, Multi-AZ. Auto Scaling in ALB. work hour엔 20 intances, 밤에 scale 내려가면 2 instances. 아침에 App이 느리다는 불만이 있다. | 아침에 20 으로 설정하는 스케쥴 액션을 설정한다.

</details>

---

<details>
<summary><font size="5"><b>Quick Dictionary</b></font></summary>

- `Route 53 active-passive failover configuration`: Route 53의 상태를 확인해서 활성/비활성화(장애) 상태일 때의 조치 사항을 정의할 수 있다.
- `Route 53` : 
- `CloudFront` : 콘텐츠 전송 네트워크(CDN) 서비스
- `Kinesis Data Firehose` : 스트리밍 데이터를 캡쳐, 변환, 저장, 분석 서비스로 전달 (로그 및 데이터 수집, 실시간 분석)
- `Kinesis Data Streams` : 확장 가능하고 내구성이 좋은 실시간 데이터 스트리밍 서비스(클릭 스트림 분석, 로그 분석, 보안 모니터링)
- `Kinesis Data Analytics` : Apache Flink를 사용해서 스트리밍 데이터를 실시간으로 분석(스트리밍 ETL, 실시간 분석)
- `Route 53 ` : DNS 서비스로 최종 사용자를 인터넷 애플리케이션으로 라우팅할 수 있는 매우 안정적이고 비용 효율적인 방법. 최적의 방법으로 route 해주는건 아닌 것 같다. 
- `Global Accelerator` : 사용자와 가장 가까운 위치의 사용 가능한 정상 엔드포인트로 트래픽을 자동으로 재라우팅하여 엔드포인트 장애를 완화하고 자동 라우팅 최적화 기능은 인터넷이 혼잡할 때 패킷 손실, 지터 및 지연 시간을 일관적으로 낮게 유지
- `snowball` : 페타바이트 규모의 데이터를 AWS로 마이그레이션할 수 있습니다. 여러 디바이스가 필요한 작업의 경우 Snow의 대규모 데이터 마이그레이션 관리자를 통해 디바이스의 단계를 추적할 수 있습니다. 물리적 스토리지 디바이스를 사용하여 Amazon S3 (Amazon S3) 와 온사이트 데이터 스토리지 위치 간에 대량 데이터를 전송합니다.
- `Direct Connect` : AWS 리소스에 대한 최단 경로입니다. 전송하는 동안 네트워크 트래픽은 AWS 글로벌 네트워크에 남아있으며 퍼블릭 인터넷에 닿지 않습니다. 프라이빗 네트워크 연결을 생성할 수 있습니다.
- `EFS` : Windows나 Linux 시스템은 EFS를 사용하면 된다. 탄력적인 용량 증설이 가능하다.
- `S3 Gateway Enpoint` : S3 and Dynamo DB 만을 위해 사용 가능하다.
- `S3 - Cold Data Storage` : 용량이 크고 Read/Write가 적은 데이터를 저장하고 있는 Storage
- `FSx - Lustre for high-performance parallel storage` : 고성능 파일 시스템에 구축된 빠르고 확장 가능한 스토리지
- `Amazon Machine Image(AMI)` : 인스턴스를 시작하는 데 필요한 정보를 제공하는 AWS에서 지원되고 유지 관리되는 이미지입니다.
- `EC2 인스턴스 종류`
  - `On-Demand Instances` : 사용한 만큼 지불(시작/중지/수면)
  - `Reserved Instances` : On-Demand과 비교하여 EC2 비용을 대폭 절감. 물리적 인스턴스가 아니며 계정에서 온디맨드 인스턴스를 사용할 때 적용되는 결제 할인.
  - `Spot Block Instances` : 미사용 EC2 인스턴스를 요청할 수 있게 해줌. 큰 할인율로 비용을 대폭 절감. 스팟 인스턴스는의 시간당 가격을 스팟 가격.
  - `Scheduled Reserved Instances` : 1년 동안 지정된 시작 시간 및 기간으로 매일, 매주 또는 매월 반복적으로 정기 용량을 예약 및 사용. 현재는 서비스하지 않으며 예약을 위해선 `On-Demand Capacity Reservations`을 사용해야함
  - `On-Demand Capacity Reservations` : 특정 가용 영역의 Amazon EC2 인스턴스에 대해 원하는 기간만큼 컴퓨팅 용량을 예약.
- `Route 53의 routing policy`
  - `Simple` : 하나의 리소스에만 호스팅
  - `Failover` : active-passive 장애 조치를 구성
  - `Geolocation` : 사용자 위치(지역)에 기반
  - `Geoproximity` : 리소스 위치에 기반
  - `Latency` : 최상의 지연 시간
  - `IP-based` : 사용자의 위치(IP)에 기반
  - `Multivalue answer` : DNS 쿼리에 무작위로 선택된 최대 8개의 정상 레코드
  - `Weighted` : 용자가 지정하는 비율에 따라
- `S3 Intelligent-Tiering` : 데이터 액세스 패턴이 변경될 때 성능에 대한 영향이나 운영 오버헤드 없이 스토리지 비용을 자동으로 최적화하려는 고객을 위해 설계된 신규 Amazon S3 스토리지 클래스
- `EC2 Auto Scaling Policy`
  - `target tracking policy` : Amazon CloudWatch 지표와 애플리케이션의 이상적인 평균 사용률 또는 처리량(throughput) 수준을 나타내는 목표 값을 지정해서 Auto Scaling.
  - `simple scaling policy` :
  - `cheduled scaling actions` :

- ###### Storage Service
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
- ###### Global Infra
  Keyword | 개념 | 사용하는 시기와 상황
    ---|---|---
  Region | AWS 서버가 존재하는 위치
  Aavailable Zone | 각 Region은 여러개의 가용영역으로 나눠져 있고 가용영역은 서로 분리되어 있다.
  Local Zones | 짧은 대기 시간을 요구하는 Application을 최종 사용자와 더 가까운 위치에서 제공 | 동영상 렌더링 및 그래픽 집약적인 가상 데스크톱 애플리케이션 등 한 자릿수 밀리초 단위의 대기 시간이 요구되는 워크로드를 더 많은 위치에서 실행하도록 고안된 새로운 유형의 AWS 인프라입니다. 어떤 고객은 고유한 온프레미스 데이터 센터를 운영하길 원하지만 로컬 데이터 센터를 완전히 없애길 원하는 고객도 있을 수 있습니다. AWS Local Zones를 사용하는 고객은 자체 데이터 센터 인프라를 보유 및 운영할 필요 없이 컴퓨팅 및 스토리지 리소스를 최종 사용자에게 더 가까이에 두는 이점을 누릴 수 있습니다.
  Wave Length | 5G 네트워크에서 AWS 컴퓨팅 및 스토리지 서비스를 포함하여 매우 낮은 대기 시간의 애플리케이션을 개발하고 배포하며 확장하기 위한 모바일 엣지 컴퓨팅 인프라를 제공 | AWS 인프라, 서비스 API 및 도구를 5G 네트워크로 확장하여 지연 시간이 짧은 애플리케이션을 5G 디바이스에 제공하도록 설계되었습니다. Wavelength는 스토리지 및 컴퓨팅 서비스를 이동 통신 사업자의 5G 네트워크 내에 포함하여 개발자가 IoT 디바이스, 게임 스트리밍, 자율 주행 차량, 라이브 미디어 제작 등 10밀리초 미만의 지연 시간이 요구되는 새로운 5G 최종 사용자를 위한 애플리케이션을 손쉽게 제작할 수 있게 합니다.
  Outposts | 하이브리드 환경을 위해 거의 모든 온프레미스 또는 엣지 로케이션에 AWS 인프라 및 서비스를 제공하는 완전관리형 솔루션 패밀리 | 대기 시간 요구 사항으로 인해 온프레미스에 유지해야 하는 워크로드를 위해 설계되었습니다. 그러나 이 경우에도 고객은 온프레미스 워크로드가 AWS 워크로드와 원활하게 실행되길 원합니다. AWS Outposts는 AWS에서 설계한 하드웨어로 제작된 구성 가능한 완전관리형 컴퓨팅 및 스토리지 랙으로, 이를 통해 고객은 컴퓨팅 및 스토리지를 온프레미스에서 실행하는 동시에 AWS의 광범위한 클라우드 서비스에 원활하게 연결할 수 있습니다
- ###### Elastic Load Balancing
  Elastic Load Balancing | 용도
    ---|---
  Application Load Balancer | HTTP 요청을 로드 밸런싱
  Network Load Balancer | 네트워크/전송 프로토콜(4계층 - TCP, UDP) 로드 밸런싱의 경우와 고도의 성능이 요구되거나 대기 시간이 낮아야 하는 애플리케이션
  Gateway Load Balancer | Amazon Elastic Compute Cloud(Amazon EC2) Classic 네트워크 안에 구축된 경우
  Classic Load Balancer | 서드 파티 가상 어플라이언스를 배포하고 실행해야 하는 경우

</details>



---
AWS Certified Developer Associate
===
### 📖 AWS Certified Developer Associate를 공부하면서 정리한 내용입니다.
### 참고자료 : Udemy 강의 (best-aws-certified-developer-associate)

- `IAM 정책의 문`은 Sid, Effect, Principal, Action, Resource 및 Condition으로 구성됩니다. `version은 문이 아니라` IAM 정책 자체의 일부입니다.
- AWS Shared Responsibility Model에 따르면 다음 중 `AWS의 책임`은 `인프라`이다.
- `IAM의 보안도구`는 `IAM 자격 증명 보고서`이다.

Port | Protocol | Description 
---|----------|---
22 | SSH      | log into a Linux instance
21 | FTP      | upload files into a file share
22 | SFTP     | upload files using ssh
80 | HTTP     | access unsecured websites
443 | HTTPS    | access unsecured websites
3389 | RDP      | log into a Windows instance

ssh로 ec2에 접속하는 pem을 사용하기 전에 chmod 0400 으로 수정하고 사용해야 한다.

`ssh -i mykey.pem ec2-user@{public ip}`

EC2 요금 지불 방식 | 설명
---|---
On demand | 아무떄나 사용할 수 있고 사용한 만큼 요금을 지불
Reserved | 1년 또는 3년동안 사용할 것을 계획하고 미리 요금을 지불 (할인이 많이됨)
Saving plans | 일정 기간동안 일정 금액 이상을 지출하기로 약속하고 여러 자원에 자원을 쓸 수 있음
Spot instances | 빈 자원에 대해 입찰해서 사용할 수 있고 나보다 비싸게 자원을 사용하는 사람이 있으면 자원을 반환해야 함 (일반적으로 가격이 제일 쌈)
Dedicated hosts | 특정 영역 전체를 예약해서 나만의 영역으로 쓸 수 있음
Capacity reservations | 언제 사용할지 모르지만 일단 전체 가격을 지불하고 필요할 때 사용

- `메모리 최적화` EC2 인스턴스는 메모리에 `대용량 데이터 세트가 필요한 워크로드`에 적합합니다.
- `컴퓨팅 최적화` EC2 인스턴스는 `배치 처리`, `미디어 트랜스코딩`, `고성능 웹 서버`, `고성능 컴퓨팅`, `과학 모델링` 및 `머신러닝`, `전용 게임 서버`와 같은 고성능 프로세서가 필요한 인텐시브 컴퓨팅 작업량에 적합합니다.
- `데이터베이스 기술을 배포`하려고 하며 공급업체 `라이선스는 물리적 코어 및 기본 네트워크 소켓 가시성`을 기반으로 비용을 청구 => `전용 호스트`

- EBS는 같은 AZ에 있는 EC2에만 연결 가능하며 하나의 EBS에 1개 이상의 EC2를 연결할 수 없다. 1개의 EC2에 2개 이상의 EBS는 연결이 가능하다.

- IAM : 권한 관리
- AMI : Amazon Machine Image, 사용자 지정 EC2

- `32,000 IOPS 이상`을 요구할 때는 `io1`, `io2`, EC2 `Nitro`가 필요하다.
- `io1`, `io2`는 `다중 EC2 연결`이 가능하다.

- EFS는 네트워크 스토리지로 Linux 기반 AMI에만 호환된다. 용량을 사용한만큼 가격을 지불한다. EBS는 용량에 따라 요금을 지불한다.

- EFS 기본 옵션은 작은 지연 시간, Max I/O

- `루트 볼륨`은 `종료 시 삭제`,`다른 EBS 볼륨`은 종료 시 삭제 속성이 기본적으로 `비활성화`되어 있으므로 `삭제되지 않습니다.`

- EC2 인스턴스 스토어
  - EC2 인스턴스에 `고성능 로컬 캐시`, 종료될 때 캐시를 잃어도 상관 없을 때 사용  
  - 310,000의 IOPS를 요구하는 고성능 데이터베이스, 최고의 디스크 I/O 성능을 제공
  - EC2가 중지되면 데이터가 손실될 수 있기 때문에 복제, 백업 옵션을 사용할 수 있다.

- ALB를 사용하고 있는 EC2가 Client의 IP를 알기 위해 X-forwarded-Port, X-forwarded-Proto를 봐야 한다.

Load Balancer | Protocol                 | Description
---|--------------------------|---
CLB | 
ALB | HTTP, HTTPS              | 다양한 HTTP 속성값으로 분기 가능
NLB | TCP, UDP                 | 하나의 고정 IP를 노출
GWLB | 6081 포트의 GENEVE Protocol | 네트워크 트래픽 분석

- `Sticky Sessions` : 고정 세션. Cookie를 기준으로 Load Balancer가 동일한 인스턴스로 요청을 계속 보내도록 하는 것. EC2의 Target group의 속성으로 설정한다.
- `Cross Zero Load Balancing` : AZ가 달라도 전체 인스턴스에 균일하게 분산한다. AZ1에는 2개의 인스턴스, AZ2에는 5개의 인스턴스가 있을 때, 70만큼의 트래픽이 온다고 가정하면
  - `Cross Zero Load Balancing`이 설정되어 있는 경우, 1개의 인스턴스에 10씩 분산된다. 
  - `Cross Zero Load Balancing`이 설정이 안되어 있는 경우, AZ1에 있는 인스턴스에 17.5씩 분산되고 AZ2에는 7씩 분산된다.
  - ALB : 항상 켜져있고 끌 수 없음. 비용 부과 없음.
  - NLB : 기본값은 off. 키면 요금 부과
  - CLB : 기본값은 off. 키면 요금 부과

- `Connection Draining` : EC2가 Draining 상태가 되면 LB는 연결 중이던 Customer 연결은 유지하고 다음 요청부터는 Draining 상태로 보내지 않음. 

- `ALB`는 `URL 경로`, `호스트 이름`, `HTTP 헤더 및 쿼리 문자열`을 기반으로 트래픽을 다른 대상 그룹으로 라우팅할 수 있습니다.
- ALB의 대상 그룹에 NLB는 들어갈 수 없지만 NLB의 대상 그룹엔 ALB가 들어갈 수 있다.
- ALB가 사용하는 쿠키 이름은 AWSALB, AWSALBAPP, AWSALBTG
- 각 ASG에는 각 스케일링 활동 후 쿨다운 기간이 있습니다. 이 기간 동안 ASG는 EC2 인스턴스를 시작하거나 종료하지 않습니다. 이는 메트릭이 안정화될 시간을 제공합니다. 쿨다운 기간의 기본값은 300초(5분)입니다.

- RDS 읽기 복제 (RDS Read Replica)
  - 비동기 복제가 읽어나고 Main App에 성능 영향을 미치지 않고 데이터를 활용할 수 있음
  - 읽기 복제본은 SELECT 명령어만 동작함
  - 일반적인 자원에선 AZ를 이동할 때 비용이 발생하지만 읽기 복제는 같은 리전 내에 있다면 비용이 발생하지 않음
- 암호화 되지 않은 RDS 데이터베이스 암호화
  - 스냅샷 생성 (암호화되지 않은 상태)
  - 스냅샷 복제
  - 복제한 스냅샷의 암호화 설정
  - 암호화 된 스냅샷으로 데이터베이스 복원 (암호화 된 데이터베이스)
  - 모든 App을 암호화 된 데이터베이스로 옮김
  - 기존 데이터베이스는 삭제

- Elastic Cache
  - `Lazy Loading`, `Cache Aside`, `Lazy Population` : App이 cache를 보고 데이터가 있으면 cache 데이터를 반환, 없을 때 db를 가서 데이터 가져오기
  - `Write Through` : 일단 db를 읽고 db가 업데이트 될 때 캐시를 추가하거나 업데이트한다. 영원히 읽지 않는 데이터가 cache에 계속 쌓일 수 있음
  - `Time-To-Live (TTL)`

어떤 RDS(Aurora 제외) 기능을 사용할 때 SQL 연결 문자열을 변경할 필요가 없습니까?
- `다중 AZ` : 다중 AZ는 어떤 데이터베이스가 작동 중인지에 관계없이 동일한 연결 문자열을 유지합니다.
- 복제본 읽기 : 읽기 전용 복제본은 자체 DNS 이름으로 새 엔드포인트를 추가합니다. 읽기 부하의 균형을 맞추기 위해 개별적으로 참조하도록 애플리케이션을 변경해야 합니다.


고가용성을 보장하려는 ElastiCache Redis 클러스터를 실행 중입니다. 어떻게 해야 할까요?
- `다중 AZ 활성화`

사용자가 연결할 때 암호를 입력하도록 하여 ElastiCache Redis 클러스터의 보안을 강화할 수 있는 방법은 무엇일까요?
- `Redis 인증 사용`
- IAM 인증 사용 : IAM 인증은 ElastiCache Redis에 대해 지원되지 않습니다. RDS MySQL 및 RDS PostgreSQL 모두에서 작동합니다.
- 보안 그룹 : 보안 그룹은 ElastiCache Redis 클러스터 인스턴스로 가는 트래픽을 필터링하는 데 도움이 되지만 애플리케이션 수준 인증에는 도움이 되지 않습니다.

다음 중 RDS 읽기 전용 복제본과 다중 AZ의 복제에 대한 설명으로 옳지 않은 것은 무엇일까요?
- `읽기 전용 복제는 비동기식 복제를 하고 다중 AZ는 동기식 복제를 한다.`

RDS는 최대 5개의 복제본을 가질 수 있다.
Aurora DB는 클러스터에 15개의 읽기 복제본을 생성할 수 있다.
Elastic Cache 클러스터는 5개의 읽기 전용 복제본을 만들 수 있다.

SSL 연결을 강제 적용하려는 MySQL RDS 데이터베이스 인스턴스가 있습니다. 어떻게 해야 할까요?
- SSL 트래픽만 허용하도록 DB 보안 그룹 수정
- DB에서 SSL 인증서를 다운로한 다음 Application에서 이 인증서를 사용하여 SSL을 통해 연결
- MySQL RDS 데이터베이스 암호화 활성화
- `모든 DB 사용자에게 REQUIRE SSL SQL`문을 실행

- Route 53
  - Record Type
    - A : hostname to IPv4
    - AAAA : hostname to IPv6
    - CNAME : hostname to hostname

| CNAME                                    | Alias                                                          |
|------------------------------------------|----------------------------------------------------------------|
| Only Non-root domain<br>test.example.com | Root Domain & Non-root Domain<br>example.com                   |
| Domain Name                              | AWS Resource (A, AAAA for IPv4, IPv6)                          |
| Can set TTL                              | Cannot set TTL                                                 |
| -                                        | EC2 DNS 이름은 대상이 될 수 없음                                         |   
| -                                        | NS(Main Domain)에 CNAME을 줄 수 없기 때문에 Alias를 사용해서 ELB를 연결할 수 있다.  | 

- Routing Policy
  - Simple (A): A record 여러개 설정 가능
  - Weight (A): 각 인스턴스에 가중치를 부여해서 트래픽을 가중치에 따라 분산
  - Latency (A, Region) : 가장 가까운 리소스로 리다이렉팅, 지연시간이 가장 짧음
  - Health Check
    - 각 인스턴스가 살아있는지 상태를 확인
    - 여러 개의 Sub Health Check를 만들어서 통합된 지표로 상태를 확인
    - Cloud Watch 알람을 확인
  - Failover : First, Second 인스턴스를 지정해서 First가 동작하지 않으면 지정해 뒀던 Second으로 연결
  - Geolocation : 지리적 위치에 따라 인스턴스를 구분해서 연결
  - Geoproximity : 지리적 위치 + 평향값에 따라 트래픽을 분산 (평향값이 + 이면 더 많은 트래픽을, - 이면 더 적은 트래픽을 원할 때 설정)
  - Traffic flow : Diagram 형태의 Flow를 그려서 트래픽을 분산
  - Multi value : 여러 레코드를 설정하고 health한 여러개의 레코드를 Client에게 반환
  - 타사 도메인을 DNS 공급자로 사용하기 위해선 퍼블릭 호스팅 영역을 생성하고 타사 레지스트라 NS 레코드를 업데이트 해야함.

- VPC, Subnet, IGW, NAT
  - Region > VPC > AZ > Public/Private Subnet
  - www ↔ Internet Gateway ↔ EC2 in Public Subnet
  - www ↔ Internet Gateway ↔ NAT ↔ EC2 in Private Subnet
- NACL (Network ACL)
  - 특정 IP를 Allow/Deny 규칙 설정
  - Subnet 안에 설치되며 EC2 앞에서 방화벽 역할을 한다.
  - 비슷한 리소스로 Security Group이 있는데 Allow 규칙만 정할 수 있다.
- VPC Peering
  - 각 VPC는 다른 IP 대역대를 갖고 있어야 하고 CIDR이 겹치면 안된다.
  - VPC A, B, C를 연결하고 싶다면 3개의 VPC Peering이 필요하다.
- VPC Endpoint Gateway
  - 다른 AWS 자원들과 private network로 연결해준다.
- Site to Site VPN : 온프로미스와 AWS를 연결할 때 사용. 암호화 (public)
- Direct Connect : 물리적으로 연결 (비공개 회선) (private)

| 용어               | 설명                                                 |
|------------------|----------------------------------------------------|
| VPC              | Virtual Private Cloud. 1개의 Regison에 1개의 기본 VPC가 존재 |
| Subnet           | AZ에 속하고 VPC의 네트워크 파티션을 담당                          |
| Internet Gateway | Public Internet과 Public Subnet을 연결                 |
| NAT Gateway      | Public Internet과 Private subnet을 연결해줌              |
| NACL             | Subnet의 inboud/outboud rule을 적용. (Allow/Deny)      |
| Security Group   | EC2, ENI의 inboud/outboud rule을 적용. (Allow)         |
| VPC Peering      | 서로 다른 VPC를 연결                                      |
| VPC Endpoint     | VPC 내에 있는 AWS 자원들과 비공개 연결                          |
| VPC Flow Log     | VPC 내 트래픽 로그                                       |
| Site to Site VPN | On-promise 환경과 AWS를 연결 (Public Internet)           |
| Direct Connect   | AWS에 Private으로 직접 연결                               |

- 일반적인 3계층 아키텍처

| Public Subnet   | Private Subnet   | Data Subnet                                  |
|-----------------|------------------|----------------------------------------------|
| ELB<br>Route 53 | ASG = 여러 AZ에 EC2 | Elastic Cache (cache, session)<br>Amazon RDS | 

- LAMP Stack on EC2
  - Linux : OS
  - Apache : Web Server
  - MySQL : Database
  - PHP : Application
  - Additional :redis, memcached, EBS

- VPC Endpoint를 사용할 때 Interface Endpoint 대신 Gateway Endpoint가 있는 유일한 2개의 AWS 자원은 `S3`, `DynamoDB`

- S3 Versioning
  - Versioning 이전의 파일은 version = null
  - Versioning을 중단하면 기존 버전은 유지하고 이후 파일에 version = null
- S3 Encryption
  - SSE-S3
    - `"x-amz-server-side-encryption" : "AE256"`
    - S3에서 암호화 키를 관리
    - 객체 단위로 암호화 키를 관리해서 암호화
  - SSE-KMS
    - `"x-amz-server-side-encryption" : "aws:kms"`
    - KMS 서비스에서 암호화 키를 관리
    - KMS Customer Master Key로 암호화
    - 누가 어떤 키에 접근할 수 있는지 제어할 수 있고 누가 접근했는지 추적할 수 있다.
  - SSE-C
    - 외부에서 고객이 관리하는 Key를 사용. 사용한 Key는 폐기됨
    - 객체와 Client Side Key를 담아서 `반드시 HTTPS로 전송`
    - 객체를 받으려면 암호화 했을 때 사용한 Key를 동일하게 보내야한다. AWS는 Key를 폐기하기 때문에 Key 관리는 Client에서 해야함.
  - Client Side Encryption
    - AWS S3 Encryption Client Library로 암호화를 한 다음 S3로 전송
    - Key 관리는 Client에서 해야함
- S3 Security
  - Bucket에 ACL 등 보안 설정을 할 수 있다.
  - Networking : VPC Endpoint를 통해 www ↔ S3로 비공개 연결
  - ️MFA Delete
  - Pre-signed URL : AWS 자격 증명으로 서명된 URL로 1시간 동안 유지되고 프리미엄 회원에서 동영상을 제공하는 등에 활용할 수 있다.

|                           | SSE-S3 | SSE-KMS                | SSE-C              | Client Side Encryption |
|---------------------------|--------|------------------------|--------------------|------------------------|
| AWS에서 암호화가 수행되는가?         | O      | O                      | O                  | X                      |
| 암호화 키가 AWS에 저장/관리 되는가?    | O      | O                      | X                  | X                      |
| Client가 암호화 키를 제어할 수 있는가? | X      | 암호화 키 교체 정책을 제어할 수 있다. | 암호화 키를 완벽하게 제어 한다. | 암호화 키를 완벽하게 제어 한다.     |

- IAM 사용자가 S3 버킷의 파일을 읽고 쓸 수 있도록 S3 버킷 정책을 업데이트했지만 사용자 중 한 명이 PutObject API 호출을 수행할 수 없다고 불평합니다. 이것의 가능한 원인은 무엇일까요?
- IAM 정책의 명시적 DENY는 S3 버킷 정책보다 우선입니다.

- MFA with CLI
  - `temporary session`이 필요하고 `STS GetSessionToken API`를 호출해야함.
  - `aws sts get-session-token` 명령어를 입력하면 임시 자격 증명을 얻게됨
  - 임시 자격 증명으로 임시 profile 생성 : `aws configure --profile tempProfile` 
  - `~/.aws/credentials` 파일에 `aws_ssesion_token = {임시 자격 증명 값}` 넣고 임시 profile 사용하면됨
- AWS SDK의 기본 Region은 `us-east-1`
- 5xx 에러의 경우센 재시도 & 지수 백오프를 해야한다. 4xx 에러는 하지 않아야 한다.
- AWS CLI 자격증명 우선순위 : 명령줄 옵션 -> 환경변수 -> EC2 Profile/Container 자격증명
- AWS SDK 자격증명 우선순위 : Java Properties -> 환경변수 -> 기본 자격증명 -> EC2 Container/Instance 자격증명
- AWS CLI/SDK를 사용할 때 사용할 EC2 자격증명을 부여해도 환경변수를 우선순위로 사용하기 때문에 고려해야 한다.

- EC2의 IAM 자격증명을 입력하면 안된다.
- 온프로미스 서버에도 IAM 자격 증명을 넣으면 안된다.
- http://169.254.169.254/latest/meta-data
- EC2에 IAM을 연결하며 AWS CLI는 인스턴스 메타데이터를 읽어서 임시 자격 증명을 사용한다.

- S3 복제에서 삭제 마크는 복제되지 않는다.

| S3 종류                  | 설명                                                                                                          |
|------------------------|-------------------------------------------------------------------------------------------------------------|
| Standard               |                                                                                                             |
| IA (Infrequent Access) | 잘 사용하지 않는 객체 저장                                                                                             |
| One Zone IA            | Single AZ에 데이터를 먼저 자장.<br>Multi AZ의 경우엔 IA보다 가용성이 낮다.<br>속도와 성능이 빠르다.<br>IA보다 비용이 적다.<br>썸네일과 같은 이미지 저장에 용이 |
| Intelligent Tiering    | Standard와 IA 사이에서 자동으로 객체 교환                                                                                |
| Glacier                | 대용량 데이터를 장기관 보관                                                                                             | 

- Glacier의 복원
  - Expedited : 1-5분
  - Standard : 3-5시간
  - Bulk : 5-12시간
  - 최소 90일 보관
- Glacier Deep Archive의 복원
  - Standard : 12시간 이전에는 회수 못함
  - Bulk : 48시간 이전에는 회수 못함
  - 최소 180일 보관
- 썸네일은 재생성하기 용이하고 45일 동안 보관된다. 45일 동안은 원본 파일을 회수할 수 있고 이후에는 6시간 후 복원해야 한다. -> 원본사진은 S3 Standard, 45일 이후에는 Glacier로 이동하도록 정책설정. 썸네일은 One Zone IA에 놓고 45일 후 삭제 정책을 설정.
- 15일 동안은 바로 복원할 수 있어야 하고 그 이후에는 365일동안 보관하며 48시간 이내에 복원해야 한다. -> S3 versioning을 통해서 즉시 복구하도록 하고 과거 버전은 IA로 이동시키고 15일이 지나면 Deep Archive로 이동시킨다.
- S3 업로드 가속화 (Transfer Acceleration) : 엣지 로케이션에 올리고 엣지 로케이션은 리전에 private network로 빠르게 올림
- S3 다운로드 가속화 : 1개의 파일을 Byte Range로 나눠서 병력적으로 다운로드 받고 한 부분이 실패해도 빠르게 재실행됨

<details>
<summary><font size="5"><b>Match Keywords</b></font></summary>

⭐️2023년 2월 28일 개편되기 전 시험에 해당하는 정리입니다.

질문|답변|
----|---|
DynamoDB 개별 사용자 다른 사용자 데이터 액세스 불가 | 기본 키 값을 기반으로 항목 액세스 제한 | 
CodeCommit Repository에 대한 HTTPS 복제 URL 사전 구성 \ | AWS 자격 증명 프로필 사용 Git 자격 증명 도우미 설정. 도우미가 Repository로 경로를 보낼 수 있도록함 | 
API G/W API 특정 리소스 접근 유일한 사용자. 토큰 자동 만료/새로고침 | Cognito 사용자풀, Authorizer 구성, 자격 증명 또는 Access token |    
Elastic Beanstalk 배포 오래 걸림 | CodeCommit Repository 생성, 개발자가 커밋 허용, Elastic Beanstalk에 직접 배포 | 
S3 반복 호출해서 Limit Exceeded 발생 | 어플리케이션에서 지수 백오프 구현 | 
서버측 암호화 블록 암호 | Advanced Encryption Standard | 
DynamoDB | 낙관적 동시성 제어 사용, 일관성을 위해 조건부 쓰기 사용 | 
LAMP | EC2, Aurora | 
지연 이유는 DB에서 프로필 조회, 캐시 필요 | ElastiCache Cluster 생성. 캐시 제외 캐싱 전략 사용 | 
EC2 10개 | CloudWatch 고유한 메트릭 이름, 사용자 지정 네임스페이스 | 
API G/W & Lambda | Session은 DynamoDB에 저장 | 
EC2 4개, 각 고유한 권한, 메모리 예약 기반 컨테이너 | 각 ECS에 권한을 포함한 4개의 고유한 IAM 생성 후 IAM 역할 참조 하도록 ECS 작업 정의 구성 | 
30분 걸리는 사기 탐지 솔루션 | 주문을 SQS 대기열, Auto Scaling 그룹 구성, 사기 탐지 솔루션이 설치된 AZ에 EC2 집합 설정 | 
보안 인프라 관리 싫음. 암호화 키는 제어 | SSE-KMS | 
Kinesis Data Stream, ProvisionedThroyghputExceededException | Data Stream 샤드 수 증가, Get/PutRecords 호출에 지수 백오프 구현 | 
SQS 5분 소요. 메세지 성공적 처리, 중복 처리 최소화하면서 메시지 제거 | 가시성 시간 초과가 증가한 메세지 검색, 메시지 처리, 대기열에서 삭제 | 
붕투 암호화 KMS 작동 | Master Key = 암복호화용, Text Data Key = 고객 데이터 암호화 | 
기존 SNS 계정 사용 게임 로그인, 데이터는 DynamoDB 저장, DynamoDB API 안전한 접근 방식 | Web ID 연합 사용, 임시 보안 자격 증명을 요청에 서명 | 
AWS CLI, Serverless 시작 단계 | CloudFormation Package 사용 후 배포 | 
Elastic Beanstalk 배포 가능 기능 | ASG, ELB, RDS | 
최종 사용자에게 보낼 수 있는 양 제한. 경영진은 더 큰 패키지 구매 옵션 제공 | 기본 사용 계획 설정. 각 단계를 설정. 더 큰 패키지를 선택하면 적절한 값으로 사용자 지정 계획을 만들고 사용자와 연결. | 
Provisioning 된 처리량 효율성을 위한 DynamoDB Hash Key Schema | Application의 사용자 ID | 
EC2의 Public/Private IP 확인 방법 | Local Instance Metadata 쿼리 | 
CloudFormation Template로 Lambda 배포 절차 | Template에서 AWS::Lambda::Function 리소스 생성 후 CloudFormation Template 내부에 코드 작성, 코드 .zip S3에 업로드 후 AWS::Lambda::Function 리소스에 참조 추가 | 
민감 데이터 보호. 액세스 추적 필요. | EC2 System Manager Parameter Store에서 IAM으로 Application Access 권한 부여 | 
Lambda 핸들러 범위 밖에서 Client Instance 이점 | 연결 재사용 활용 | 
CPU 고사용 Lambda 배포. 런타임 최소화 | 메모리 할당이 최대로 설정된 함수 배포 | 
EC2 정적 콘텐츠 때문에 높은 지연 시간 | 정적 컨텐츠를 캐시할 CloudFront 배포, S3에 정적 컨텐츠 저장 | 
Lambda로 Kinesis Data Stream 처리. 함수가 중복 레코드 생성. Lambda 없는 스트림은 중복 없음 | Lambda가 오류를 처리하지 않고 Lambda 서비스가 재처리를 시도 | 
S3 버킷 민감 정보 저장. 모든 데이터 암호화 | x-amz-server-side-encryption 헤더가 포함되지 않는 객체 업로드 방지하는 정책 설정 | 
KMS 암호화 100 GB | Text Key와 Data Key의 암호화된 복사본을 반환하는 API 호출 생성. Text Key를 사용해서 데이터 암호화 | 
Lambda 함수 DynamoDB 접근 두번째 계정 생성. 테이블 액세스 방법 | 테이블 액세스할 때 Lambda 에서 새 역할을 맡음 | 
DynamoDB ProvisionedThroyghputExceededException 발생. CloudWatch에 처리량 초과하지 않음 | 특정 Hash Key에 대한 용량을 초과 | 
Elastic Beanstalk 지원 플랫폼 | tomcat, .NET | 
CORS 에러 발생 | CORS 구성을 생성해서 교차 출처 요청을 허용하는 cdfonts 버킷 구성 | 
S3 데이터 처리 어플리케이션. 하루 10번, 1분 소요 | Lambda로 배포하고 S3 이벤트 알림과 같이 호출 | 
가장 저렴한 비용으로 다운로드 액세스 안전하게 제어 | S3 Presigned URL와 함께 CloudFront 사용 | 
ELB + EC2, 세션 데이터 작성하는 위치 | ElasticCache에 데이터 쓰기 | 
이미징 서비스를 EC2로 마이그레이션. 이미지는 Private S3 버킷에서 가져옴 | S3 버킷에 대한 읽기 전용 권한이 있는 EC2 서비스 역할을 생성하고 연결 | 
AWS SDK 기본 리전 | us-east-1 | 
SQS 설명 | 메세지는 한 번 이상 배달되고 순서는 불확실 | 
Lambda 함수 테스트 해도 CloudWatch Log에 생성되지 않음 | Lambda 함수 실행 역할에 CloudWatch Log에 쓰기 권한이 없음 | 
DynamoDB에 실시간 동적 업데이트. 덮어쓰기 방지 옵션 | 조건부 쓰기 | 
병렬, 순차적 실행하는 어플을 Lambda로 리팩토링. POST는 G/W에서 처리. | Step Functions 상태 머신을 사용해서 Lambda 함수 조정 | 
주식 어플. Kinesis로 데이터 수집. 수신 데이터 따라갈 수 없음 | UpdateShardCount를 사용해서 스트림의 샤드 수를 증가 | 
Lambda 함수 오래 걸림 | Lambda 함수에 할당 된 메모리를 증가 | 
Step Function 작업 상태를 CloudWatch로 오류 표시. 원래 입려과 오류 모두 보존하는 방 법 | Catch문에서 ResultPath를 사용해서 원래 입력에 오류 표시 | 
ElastiCache 좋은 사례 | 읽기가 많은 어플의 워크로드 대기 시간과 처리량 개선, 컴퓨팅 집약적인 어플의 성능 향상 | 
사용자 자격 증명 절대 노출 금지 | Cognito 사용자풀 구성하고 Cognito API로 사용자 인증하고 권한 부여 | 
BGP 기반 VPN으로 EC2 연결. Subnet A는 액세스, B는 액세스 불가. 트래픽이 B에 도달했는지 확인 | VPC 흐름 로그 | 
응용 프로그램을 2개의 구성요소로 나누고 독립적으로 확장. Elastic Beanstalk 사용해서 배포 | 각 구성 요소를 별도의 Elastic Beanstalk 환경에 배포 | 
Access key를 AWS에서 관리하는 방법 | 계정 루트 사용자에 대한 모든 Access key를 삭제, Access key 대신 IAM 역할 사용 | 
S3 버킷에 프로필 사진 저장. 로그인할 때마다 표시. 공개적으로 액세스 불가 | 사진의 S3 key를 DynamoDB에 저장. 함수를 사용해서 Presigned URL 생성 후 반환 | 
EC2 어플이 DynamoDB 쓰기 권한. 보안키는 사용하지 않음 | EC2에 IAM 추가. DynamoDB에 쓰기 권한 IAM 역할 생성 | 
Lambda가 초당 여러번 호출. 호출당 50MB 파일 다운로드 | /tmp 경로에 파일을 캐시 | 
Lambda는 VPC의 RDS Mysql 읽고 다른 싸이트에서 데이터 가져옴 | Lambda 함수 기본 구성에 VPC Private Subnet 연결 추가, VPC에 NAT G/W 추가 | 
Elastic Beanstalk 배포. ELB가 있는 WEB 계층, RDS 계층 어플리케이션. RDS 인스턴스 분리하는 방법 | RDS 없는 새로운 Elasic Beanstalk 환경 다시 생성 | 
SQS 사용 어플리케이션. 분당 하나의 메세지가 대기열에 게시되서 비용 증가 | SQS 대기열 폴링 시간 초과를 늘린다. | 
CPU 집약 데이터 처리 Lambda nodejs 개발. 완료시간 단축 솔루션은? | Lambda 사용 가능 메모리 증가 | 
소스 변경될 때 배포. 배포 트리거 방식 | 소스 코드를 S3에 저장하고 파일이 변경되면 시작하는 CodePipeline 구성, 소스 코드를 CodeCommit Repository에 저장하고 커밋될 때 시작하는 CodePipeline 구성 | 
배포 오류일 때 Lambda 이전 버전으로 롤백 | 현재 버전을 가리키는 별칭을 사용하도록 어플을 변경. 새 코드 배포. 별칭 업데이트해서 트래픽 10%를 새로운 버전으로 보내고 오류 시 이전 버전으로 전송 | 
5개 쓰기 용량 단위 DynamoDB 트랜잭션. 읽기 처리량 높은 옵션 | 4KB 크기 항목을 읽는 5개 읽기 용량 단위의 최종 일관된 읽기 | 
S3에 저장하기 전 암호화. 암호화 키는 보안팀이 관리 | KMS 암호화 사용. 고객 마스터 키를 사용해서 클라이언트 측 암호화 구현 | 
Client IP 기준으로 처리. ALB 뒤에 배치되면서 같은 IP로 들어옴 | X-Forwarded-For 헤더를 검사하도록 어플 코드 변경 | 
EC2 어플. AWS API 호출하도록 구성 | 필요한 권한이 있는 EC2 역할을 지정 | 
CloudWatch Logs에 예외를 계산을 위한 메트릭 필터 생성. 결과가 반영되지 않음 | 필터를 만든 이후에 발생하는 데이터에만 지표를 생성 | 
ASG, EC2. 테스트를 위한 다수의 단기 Instance. CloudFormation Template 사용해서 관리자가 시작. 테스트 환경만 허용. 광범위 권한 부여하지 않음. | 환경 템플릿에서 Service Catalog 제품 생성. 기존 역할이 있는 제품에 시작 제약 조건 추가. 테스트 사용자에게 Service Catalog API만 사용할 수 있는 권한 부여. Service Catalog 콘솔에서 템플릿을 시작하도록 교육. | 
온프로미스 상태 저장 웹 서버를 마이그레이션. 더 큰 탄력성 원함. | DynamoDB에 세션 상태 데이터 자장. ASG이 있는 ELB 사용 | 
타사의 API 사용. 실패가 임계치면 알림 | CloudWatch에 사용자 지정 지표를 게시하고 SNS 사용 | 
인증 없는 제한된 액세스 게스트 허용 | 인증되지 않은 액세스가 활성화 된 Cognito|
Elastic Beanstalk 배포. 새 버전은 이전 버전과 호환 안됨. 배포 실패 시 이전 버전으로 롤백. 새 업데이트는 일괄 전환 수행 | 새 Elastic Beanstalk 환경에 새 버전을 배포하고 환경 URL을 교체 | 
Lambda 사용자 지정 라이브러리 사용 방법 | 라이브러리를 로컬에 설치하고 ZIP 파일을 업로드 | 
DynamoDB 많은 읽기 용량 소비. 속성이 매우 큼. 어플은 모든 속성이 필요하지 않음. | 최소한의 프로젝션 속성 집합으로 글로벌 보조 인덱스를 만든다. | 
API G/W를 사용해서 액세스 하는 온프로미스 Linux 환경. API 테스트 단계에서 X-Ray 추석 활성화. 온프로미스 서버에서 X-Ray 추적 활성화 방안 | 온프로미스 서버에 X-Ray 데몬을 설치하고 실행해서 데이터를 캡처하고 X-Ray 서비스에 전달 | 
X-Ray로 Lambda 기반 어플을 추적 | IAM 실행 역할을 사용하여 Lambda 함수 권한을 부여하고 추적을 활성화 | 
보안 문서 Private S3에 저장. 요청 된 사용자 15분 동안만 다운로드 가능 | 만료 시간이 15분인 Presigned S3 URL 생성 | 
DynamoDB 저장 되는 항목의 크기는 7KB, 읽기는 강력한 일관성 필요. 읽기 속도는 초당 3개 항목. 쓰기는 초당 10 항목. DynamoDB 크기는 | 6 읽기 용량 단위, 70 쓰기 용량 단위 | 
FE어플은 Congnito 사용자 풀 사용. 인증 흐름 처리. SDK 사용해서 DynamoDB 어플 통합. 비밀키 노출하지 않고 API 안전하게 호출 | Cognito 자격 증명 풀을 구성하고 JWT을 임시 자격 증명으로 교환 | 
특정 IAM 사용자 자격 증명 사용하도록 CLI 구성. 명령어 오류 반환됨. `aws dynamodb get-item --table-name ...` | IAM 사용자는 테이블에 대한 읽기 액세스 권한이 있는 정책이 필요 | 
ElastiCache for Redis 사용. 로드 증가. 장애 시 복원력이 필요 | ElastiCache를 수직적으로 확장 | 
EC2 실패할 경우 세션이 손실되지 않도록 해야 한다. | DynamoDB 사용해서 확장 가능한 세션 처리를 수행 | 
ELB 뒤에 여러개의 서버. 웹 서버의 메모리에 세션 데이터 저장. 세션 데이터 손실 방지. 다운타임 최소화 | Redis용 ElastiCache 클러스터 | 
DynamoDB 초당 90개 읽기 항목. 각 항목은 3KB. 필요한 읽기 용량 단위 프로비저닝 | 45 | 
ECS-Docker. 15초 동안 사용자 로드를 기반으로 확장  |  사용자 활동 데이터에 고해상도 지정. CludWatch 지표 5초마다 데이터 게시
DynamoDB를 트리거하는 Labmda. 실행역할은 추가됨. Lambda 활성화 했지만 트리거되지 않음. | Lambda 함수에 대한 이벤트 소스 매핑 구성
여러 공급업체에 요청하고 일주일 걸리는 프로세스 | Step Function을 사용해서 병행 Lambda 함수를 실행하고 결과 결합
온프로미스 DB를 RDS Mysql로 마이그레이션. 읽기가 많은 워크로드. 최적의 읽기 성능 | 읽기 쿼리에 RDS 읽기 전용 복제본을 사용하기 위한 연결 문자열 추가
S3 6GB 파일 업로드. 최대 크기 초과 에러 메시지 | 멀티파트 업로드 API 사용
실수로 EC2 인스턴스 종료. 방지하는 대책 | Resource Access Manager에서 EC2 종료 보호 활성화
CloudWatch API 호출 400 에러 | 지수 백오프로 통화 재시도
수백만 개의 이벤트 실시간 처리. 동시에 비용 효율적으로 처리 | Kinesis Stream 사용
EC2 인스턴스의 IPv4 주소 찾기 | 169.254.169.254/lastest/metadata/ 를 검색
단일 인터페이스 필요 | API Gateway
X-Ray 사용 시작 작업 | 어플이 있는 서버에 X-Ray 에이전트 설치, SDK를 사용해서 어플 추적 코드 계측
AWS에서 추가 비용 없이 포함되는 서비스 | Auto Scaling, CloudFormation
Github Repository -> CodeCommit HTTS를 통해서 마이그레이션 | IAM에서 생성된 Git 자격 증명 세트
Mysql RDS. DB 자격 증명이 안전하게 저장되고 액세스 되는지 확인 방법 | 자격 증명을 Secrets Manager에 저장하고 자동 암호 교체를 활성화
분라되있는 DB에서 다른 DB에서 거의 실시간으로 업데이트를 가져오는 방법 | DynamoDB Stream을 사용해서 다른 DB의 모든 변경 사항을 전달
KMS 암호화를 활성화하고 성능이 느려짐. 원인은? | KMS API 호출 제한이 원하는 성능을 달성하는데 필요한 것보다 적다.
등록된 사용자와 게스트가 있을 때 두 유형 모두에게 액세스를 제공하려면 ? | Cognito를 사용해서 인증된 역할과 인증되지 않은 역할을 사용해서 액세스 제공, IAM을 사용해서 사용자 유형에 따라 STS(Security Token Service) 작업을 사용해서 다른 역할을 맡도록 하고 위임한 역할에 대한 엑세스 제공
읽기 쿼리에 영향을 최소화 하면서 트래픽 급증을 대응하는 방법은? | ElastiCache를 사용해서 데이터 캐시
Lambda는 정기적으로 교체되는 사용자의 이름과 암호를 사용해서 외부 사이트에 액세스. 안전하게 보관하는 방법은 ? | System Manager Parameter Store, KMS
로그 때문에 메모리 가득참. 로그 중앙 집중화 필요 | CloudWatch를 설치해서 로그를 CloudWatch로 보내고 전송된 로그는 인스턴스에서 삭제한다.
SQS 대기열에 메시지 업데이트. 자주 변경되지 않지만 업데이트 시간 최소화 | 20초마다 긴 풀링을 사용하여 메시지 검색
SQS에 유료회원, 무료회원용 업로드. EC2가 SQS를 폴링. 유료회원 먼저 처리해야 함 | 2개의 SQS 대기열을 만들고 유료 회원을 먼저 폴링
SNS 모바일 푸쉬. 개별 장치에 직접 알림을 보내려면 장치 등록 식별자 혹은 토큰을 SNS에 등록 | CreatePlatformEndPint API 함수를 호출해서 여러 장치 토큰을 등록한다.
Elastic Beanstalk로 Python 배포. 소스 번들 생성 시 요구사항 | 최상위 디렉토리를 포함하지 않아야 하고, 단일 .zip, .war 파일로 생성되어야 한다.
어플의 키는 온프로미스 데이터 센터에서 관리. 암호화는 S3에서 처리. | 고객이 제공한 키로 서버 측 암호화 사용
Elastic Beanstalk의 EC2 인스턴스 특정 명령 세트 실행하려고 한다. Beanstalk의 기능은? | .ebextensions
로그인 프로토콜을 MFA로 고도화 하려고 한다. | MFA가 포함된 Cognito
CLI에서 aws 명령을 찾을 수 없음 | aws 실행 파일이 환경 변수에 없음
Lambda 코드를 S3에 새로 올렸지만 이전 버전이 실행됨 | 업데이트 기능 코드 API를 호출
각 EC2에 어플, DB 동작. 어플이 DB에 접근하기 위한 비밀키는 변경됨 | SecureString 데이터 유형과 함께 System Manager Parameter Store를 사용해서 비밀키를 저장
API G/W, Lambda, S3 호스팅웹에서 CORS 오류 | API G/W에서 메서드에 대한 CORS 활성화
S3에 대용량 파일 저장하고 메타 데이터를 제공해서 사용자가 선택해서 다운로드. 메타데이터를 인덱싱 하고 밀리초 내에 검색 기능 제공 | DynamoDB 를 사용해서 검색 기능 제공
Cognito 사용자 풀 사용. 회사 로고가 있는 로그인 페이지 만들고 싶다. | Cognito에서 호스팅 사용자 인터페이스를 생성하고 회사 로고로 사용자 지정
AMI(Amazon Machine Image) 목록을 검색할 때 사용하는 EC2 API | DescribeImages
회사의 모든 직원 정보가 SAML 직원 디렉터리에만 남아 있어야 한다. 직원에게 승인된 액세스를 제공해서 자신의 어플에만 액세스 하도록 해야한다. | Cognito 자격 증명 풀을 사용하고 SAML 공급자와 연동해서 IAM 조건 키를 사용해서 직원에게 액세스 권한 부여
Beanstalk 배포. 배포는 최소한의 영향. 최대한 빠른 롤백 전략 | Immutable
DB 연결해서 동작하는 Lambda 코드가 있다. 비용 증가 없이 성능 개선 방법 | Lambda 함수에 필요한 모듈만 패키징, RDS 연결을 핸들러 함수 외부로 이동
CLI 명령 후 에러 메세지가 암호화 되어 있다. | STS decode-authorization-message API로 디코딩
온프로미스 세션 공유하는 어플을 마이그레이션. 내결함성, 확장성, 무중단 필요. 세션 상태 저장 옵션은? | ElastiCache에 세션 상태 저장
SNS 전송 유형 | HTTP, SMS
AWS 계정을 감사하는 어플리케이션. A 계정에서 실행되서 계정 B,C 서비스에 액세스 필요. 어플리케이션이 각 계정 서비스를 호출하는 방법 | 각 감사 계정에서 교차 계정 역할을 구성하고 해당 역할을 맡는 계정 A에 코드 작성
Elastic Beanstalk 어플을 여러 리전에 배포. 각 리전에 서로 다른 AMI 필요. 리전에 대해 올바른 AMI을 지정할 CloudFormation Template Key는 ? | Mappings
매시간 대용량 데이터 수집. 메세지는 실시간으로 전달되어야 함 | Kinesis Client Library와 함께 Kinesis Data Stream을 사용해서 메세지 수집 및 전송
DynamoDB, Lambda, API G/W 구성. 요청 대기 시간 길어짐. 식별할 수 있는 방법 | API G/W와 Lambda에 X-Ray 추적을 활성화하고 사용자 요청 추적 및 분석
전달 파이프라인은 CodeCommit Repository의 Master Branch에 대한 변경이 트리거.<br>CodeBuild를 사용해서 프로세스 테스트 및 빌드 단계 구현<br>CodeDeploy로 배포<br>파이프라인은 정상. CodeDeploy가 배포를 안함. 가능한 원인은? | CodeCommit Repository의 Master Branch에서 변경 사항이 적용되지 않음, 파이프라인 초기 단계 중 하나가 실패해서 파이프라인 종료
Kinesis Data Stream의 샤드 수 4개에서 6개 됨. 데이터 처리를 위한 EC2 인스턴스 최대 수는? | 6
The specified bucket does not exist 에러 발생. 원인 분석 시작지는? | CloudTrail에서 DeleteBucket 이벤트 확인
어플의 기록을 조회하는 RDS. 기록 데이터 업데이트 많음. 읽기 성능 저하됨 | RDS 읽기 전용 복제본을 만들고 모든 읽기 트래픽을 복제본으로 전송
API G/W 사용해서 WebSocket API 구축. API로 전송되는 Payload는 생성, 업데이트, 제거의 값을 가진다. 값에 따라 다른 경로와 통합해야 한다. | 경로 선택 표현식 값을 $request.body.action으로 설정
서비스는 변경 세트를 피어 투 피어로 교환해서 여러 분산 Repository로 동기화하는지 확인해야 한다. 네트워크가 없어도 작업 가능해야 한다. | CodeCommit
데이터 파일은 로컬로 캐싱하고 공유 이미지를 로컬 디스크에 기록. 마이그레이션할 때 수평적 확장을 허용하기 위한 것 | 공유 이미지를 제공하기 위해 S3를 사용하도록 어플을 수정하고 캐시 데이터를 로컬 디스크에 쓴다.
S3로 사진 공유 웹사이트 운영하는데 다른 사이트에서 도용함 | S3의 공개 읽기 액세스를 제거하고 날짜가 있는 서명 URL 사용
API G/W는 통과했지만 Lambda로 도달 안됨. 두번째 계정의 Lambda는 최대 동시성 실행 | 두번째 Lambda 함수의 동시 실행 제한 구성
EC2에 있는 어플이 RDS로 연결. 사용자 ID/PWD를 코드에 저장히기 싫음. 자격 증명 자동 교체 필요 | Secrets Manager를 사용해서 자격 증명을 저장하고 검색



- 81번
1 RCU per 4k\
2 RCU per read and we have 3 per second, so 2x3=6 read capacity.\ 
Write uses 1k as its capacity, so 7k items will take 7 WCU,\
as we have 10 writes per second 10x7 = 70.


- 최대 4KB 항목의 강력히 일관된 읽기 요청에는 하나의 읽기 요청 단위가 필요합니다.\
최대 4KB 항목의 최종 읽기 일관성 요청에는 절반의 읽기 요청 단위가 필요합니다.\
최대 4KB 항목의 트랜잭션 읽기 요청에는 2개의 읽기 요청 단위가 필요합니다.


</details>

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

Cognito
===

<img width="411" alt="image" src="https://user-images.githubusercontent.com/21374902/193582302-89b9f50b-22a7-48d4-8edb-f8ac91b805ee.png">

- 웹 및 모바일 앱에 대한 인증, 권한 부여 및 사용자 관리를 제공합니다. 사용자는 사용자 이름과 암호를 사용하여 직접 로그인하거나 Facebook, Amazon, Google 또는 Apple 같은 타사를 통해 로그인할 수 있습니다.
- ### 사용자 흐름
  - 첫 번째 단계에서 앱 사용자는 `사용자 풀`을 통해 로그인하여 인증 성공 이후 사용자 풀 토큰을 받습니다.
  - 다음으로 앱은 `자격 증명 풀`을 통해 사용자 풀 토큰을 AWS 자격 증명으로 교환합니다.
  - 마지막으로 앱 사용자는 AWS 자격 증명을 사용하여 Amazon S3, DynamoDB 등 다른 AWS 서비스에 액세스할 수 있습니다.
- ### 사용자 풀(User Pool)
  - 가입 및 로그인, 소셜 로그인, 사용자 정보 관리, 2FA 등 기능을 제공
  - 사용자 풀 인증 흐름
    
    <img width="520" alt="image" src="https://user-images.githubusercontent.com/21374902/193584060-1e3b492b-74da-4771-ae4e-b1623dea7613.png">
- ### Lambda를 통한 Customize
  - Cognito를 사용할 때 특정 시점에 원하는 trigger 등과 같은 customize를 정의할 때 사용할 수 있습니다.
  - https://docs.aws.amazon.com/ko_kr/cognito/latest/developerguide/cognito-user-identity-pools-working-with-aws-lambda-triggers.html

    사용자 풀 흐름 | 작업 | 설명
    ---|---|---
    사용자 지정 인증 흐름 | 인증 문제 정의 | 사용자 지정 인증 흐름에서 다음 문제를 결정합니다.
    사용자 지정 인증 흐름 | 인증 문제 생성 |사용자 지정 인증 흐름에서 다음 문제를 생성합니다.
    사용자 지정 인증 흐름 | 인증 문제 응답 확인 | 사용자 지정 인증 흐름에서 응답이 올바른지 결정합니다.
    인증 이벤트 | 사전 인증 Lambda 트리거 | 로그인 요청 승인 및 거절에 대한 사용자 지정 검증입니다.
    인증 이벤트 | 사후 인증 Lambda 트리거 | 사용자 지정 분석을 위한 이벤트 기록
    인증 이벤트 | 사전 토큰 생성 Lambda 트리거 | 토큰 신청 증강 또는 억제
    가입 | 사전 가입 Lambda 트리거 | 가입 요청을 수락 또는 거부하는 사용자 지정 검증 수행
    가입 | 사후 확인 Lambda 트리거 | 사용자 지정 분석을 위한 사용자 지정 시작 메시지 또는 이벤트 로깅 추가
    가입 | 사용자 마이그레이션 Lambda 트리거 | 기존 사용자 디렉터리에 있는 사용자를 사용자 풀로 마이그레이션
    메시지 | 사용자 정의 메시지 Lambda 트리거 | 고급 사용자 지정 및 메시지 현지화 수행
    토큰 생성 | 사전 토큰 생성 Lambda 트리거 | ID 토큰 속성 추가 또는 제거
    이메일 및 SMS 서드 파티 공급자 | 사용자 지정 발신자 Lambda 트리거 | 서드 파티 공급자를 사용하여 SMS 및 이메일 메시지 보내기

- ### 3rd party 로그인
  
  <img width="718" alt="image" src="https://user-images.githubusercontent.com/21374902/193587955-dce3309e-351d-4ebc-aee0-6c9ae9d4a58a.png">

  <img width="718" alt="image" src="https://user-images.githubusercontent.com/21374902/193588007-410b62b1-2f01-4a43-a1b3-68b69e40b8ec.png">

- ### Sequence Diagram
  - 회원가입

    ![Untitled-2](https://user-images.githubusercontent.com/21374902/194447273-4da943ca-c310-4ce5-8f11-40523d04102b.png)

  - 로그인
    
    ![Untitled](https://user-images.githubusercontent.com/21374902/194447086-53d3d0b7-2605-4641-a2c8-6b5a93d865e2.png)

  - 로그아웃 및 회원탈퇴

    ![Untitled-3](https://user-images.githubusercontent.com/21374902/194447263-d9e1c057-996e-40a0-ae7f-087ecc1cb909.png)

- ### Reference
  - https://docs.aws.amazon.com/ko_kr/cognito/latest/developerguide/what-is-amazon-cognito.html
  - https://velog.io/@w1nu/쉽게-풀어쓴-AWS-Cognito-기초-이론

---

Amazon Connect
=== 
### AWS Document : https://aws.amazon.com/ko/connect
## 1. 서비스 개요
- #### 간단히 말하면 고객센터를 구축해주는 Amazon의 몇 안되는 `완성형 서비스`이다. 간단한 [데모 영상](https://www.youtube.com/watch?v=wnmXSqHlgyM)을 보고 따라해봤을 때 인스턴스를 구축하고 상담원과 전화 연결을 하는데 10분이 채 걸리지 않았다.
- #### Amazon Connect에서 제공하는 주요 서비스는 아래와 같다.
| 주요 서비스                                             | 설명                                        |
|----------------------------------------------------|-------------------------------------------|
| [Amazon Polly](https://aws.amazon.com/ko/polly/)   | TTS(Text-to-speech) 서비스 제공                |
| [Amazon Lex](https://aws.amazon.com/ko/lex/)       | AI Chatbot 서비스 제공                         |
| [Softphone](https://github.com/aws/connect-rtc-js) | 별도의 작업 없이 바로 상담원이 전화를 받고 걸 수 있는 서비스 제공    |
| [Amazon Lambda](https://aws.amazon.com/ko/lambda/) | 원하는 비지니스 로직을 Contact Flow에 적용할 수 있는 서비스   |
| Contact Flow                                       | 고객 응대 흐름을 Diagram 형태로 바로 만들고 수정할 수 있는 서비스 |


## 2. 서비스 특징
- #### AWS 공식 페이지에선 아래와 같이 설명을 하고 있다.
  - 클릭 몇 번으로 클라우드 고객 센터를 설정하고 에이전트를 온보딩하여 고객을 즉시 지원할 수 있다.
  - 올인원 형태의 AI 및 ML 기반 고객 센터를 통해 음성 및 디지털 채널 전반에서 에이전트 생산성과 고객 경험을 개선할 수 있습니다.
  - 위치에 관계없이 수만 명에 이르는 에이전트를 온보딩할 수 있는 유연성을 통해 고객 수요에 따라 간편하게 확장 또는 축소할 수 있습니다.
  - 기존 고객 센터 솔루션과 비교하여 최소 비용, 장기 약정 또는 선결제 라이선스 요금 없이 최대 80%를 절약할 수 있습니다.
- #### 인스턴스 구축에 필요한 대부분의 기능을 제공하기 때문에 클릭 몇 번으로 기본적인 인스턴스를 바로 만들고 운영할 수 있다.
- #### Amazon에서 기본적으로 제공하는 고객센터 번호를 바로 사용할 수 있다. _(수신자 부담, 발신자 부담 설정 가능)_
- #### 계정 관리를 통해 Admin, Manager, Caller를 설정할 수 있고 [Softphone](https://github.com/aws/connect-rtc-js), [AI Chatbot(Amazon Lex)](https://aws.amazon.com/ko/lex/)도 같이 제공한다. _(Multi Channel)_
- #### 고객 응대 흐름은 draw.io에서 diagram 그리듯 그릴 수 있어서 편리하다. [AI Chatbot(Amazon Lex)](https://aws.amazon.com/ko/lex/)에 대한 흐름도 그릴 수 있다.
- #### [Softphone](https://github.com/aws/connect-rtc-js)을 제공하기 때문에 인터넷 연결만 있으면 별도의 하드웨어(장비, 전화기 등) 없이 관리자와 상담원은 어디서든 사용할 수 있다. _(AutoScailing)_
- #### 이벤트 기간 등 트래픽이 몰리는 상황에서 시스템을 증설 및 축소를 간단하게 할 수 있다.
- #### [Amazon Polly](https://aws.amazon.com/ko/polly/)를 사용해 TTS(Text-To-Speech) 기능을 사용할 수 있다. 타이핑을 통해 안내 멘트를 간단하게 만들 수 있다. _(한국어 지원)_
- #### [Amazon Lambda](https://aws.amazon.com/ko/lambda/)를 통해서 원하는 비지니스 로직을 부여할 수 있다. 예를들어 비행기를 취소 요청한 고객이 전화했을 때 "취소한 여행편이 있습니다. 이에 관련한 문의신가요?" 라는 질문을 먼저 할 수 있다. _(동적 반응)_
- #### 통화, 상담 내용은 자동으로 암호화해서 저장하고 다시 들을 수 있다. _(KMS 암호화 방식)_
- #### 저장된 데이터들을 적재하고 빅데이터를 기반으로 여러 서비스를 만들 수 있다.
- #### 다양한 언어를 지원하기 때문에 Global 통합 운영 센터를 빠르게 만들 수 있다.
- #### [데모 영상](https://www.youtube.com/watch?v=wnmXSqHlgyM)에서 소개하는 특징은 아래와 같다.
  <img width="735" alt="스크린샷 2023-01-26 13 46 23" src="https://user-images.githubusercontent.com/21374902/215044938-2e5cf32e-d370-4b98-b408-ba0253cb2281.png">
  <img width="736" alt="스크린샷 2023-01-26 14 07 05" src="https://user-images.githubusercontent.com/21374902/215044948-193f7242-2e80-446e-8502-e274457eae4e.png">
  <img width="731" alt="스크린샷 2023-01-26 14 06 58" src="https://user-images.githubusercontent.com/21374902/215044944-5b9c3e68-ccbc-4ffd-bbdf-9161b8137527.png">
  <img width="736" alt="스크린샷 2023-01-26 15 25 31" src="https://user-images.githubusercontent.com/21374902/215044952-fe53947e-7db3-4e9c-b46e-5e3942862600.png">


## 3. 기대 효과
- #### 원하는 비지니스에 대한 기능은 [Amazon Lambda](https://aws.amazon.com/ko/lambda/) 통해 사용할 수 있고 같은 AWS Infra에 있다면 AWS Resource와 연계하여 사용하기에도 용이해보인다.
- #### 무엇보다 장비를 사거나 도메인을 할당받고 전화번호를 신청하고 그런 과정 없이 클릭 몇 번으로 고객센터를 바로 만들 수 있는게 큰 장점인 것 같다.
- #### 기본으로 제공되는 [Softphone](https://github.com/aws/connect-rtc-js), [AI Chatbot(Amazon Lex)](https://aws.amazon.com/ko/lex/), [Amazon Polly](https://aws.amazon.com/ko/polly/) 기능이 좋아서 상담원만 있으면 전화를 수신하고 상담할 수 있는 업무는 바로 시작할 수 있다.
- #### Contact Flow를 diagram으로 바로 만들 수 있는 것도 큰 장점이다.
- #### Multi Channel에서 수집되고 저장되는 빅데이터를 활용해서 다른 서비스를 만들 수 있다.
- #### [데모 영상](https://www.youtube.com/watch?v=wnmXSqHlgyM)에서 소개하는 기대효과는 아래와 같다.
  <img width="734" alt="스크린샷 2023-01-26 15 27 18" src="https://user-images.githubusercontent.com/21374902/215044954-01ae0d8e-2787-4d00-bf81-8f42f1a6ed14.png">
  <img width="736" alt="스크린샷 2023-01-26 15 27 42" src="https://user-images.githubusercontent.com/21374902/215044955-545de8b3-c026-4ec5-9b88-d5e5d313df7f.png">
  <img width="735" alt="스크린샷 2023-01-26 15 28 27" src="https://user-images.githubusercontent.com/21374902/215044956-29196c44-1330-453a-8826-6e2a1c960db0.png">
  <img width="734" alt="스크린샷 2023-01-26 15 32 05" src="https://user-images.githubusercontent.com/21374902/215044961-d78a3f27-cc47-4819-a8c5-178e1dc31b02.png">

## 4. 대표 구현 사례
- [Priceline - 통화량이 3배 증가하는 중에 고객 서비스를 최적화](https://aws.amazon.com/ko/solutions/case-studies/priceline/?did=cr_card&trk=cr_card)
- [Deliveroo - AWS를 사용하여 고객의 기대치에 부응](https://pages.awscloud.com/rs/112-TZM-766/images/AWS%20Deliveroo%20Innovator.pdf)
- [Unum - 완전히 새로운 보험 고객 경험 재정립](https://aws.amazon.com/ko/solutions/case-studies/unum-video/)


## 5. 기타 참고 자료
- [Amazon Connect](https://docs.aws.amazon.com/connect/index.html)
- [Amazon Lex](https://aws.amazon.com/ko/lex/)
- [Amazon Polly](https://aws.amazon.com/ko/polly/)
- [Amazon Lambda](https://aws.amazon.com/ko/lambda/)
- [Softphone](https://github.com/aws/connect-rtc-js)
- [Amazon Connect Admin Guide](https://docs.aws.amazon.com/ko_kr/connect/latest/adminguide/what-is-amazon-connect.html)
- [Amazon Connect Lambda Guide](https://docs.aws.amazon.com/ko_kr/connect/latest/adminguide/connect-lambda-functions.html)

## 6. Amazon Connect 만들어보기
- ### (1) Instance 구축
  - [Amazon Connect](https://docs.aws.amazon.com/connect/index.html) 접속 → Amazon Connect 시작하기 → Create Instance
    
    <img width="600" alt="스크린샷 2023-01-27 10 07 48" src="https://user-images.githubusercontent.com/21374902/215044965-02c2bd74-946e-444a-a375-b77c6d460625.png">

    <img width="300" alt="스크린샷 2023-01-27 10 20 03" src="https://user-images.githubusercontent.com/21374902/215044987-a8852510-79ea-4a52-9789-9b7fee63a9b4.png">

- ### (2) Amazon Connect Dashboard
  
  <img width="600" alt="스크린샷 2023-01-27 10 20 44" src="https://user-images.githubusercontent.com/21374902/215044989-d8e86809-4942-4d45-b99e-0db871bc1235.png">

- ### (3) Contact Flow
  - #### ⭐️ 중요 포인트
    - ##### TTS 기본 언어는 English로 되어 있기 때문에 한국어로 변경해줘야 한다. Flow 시작 부분에 `Set Voice` = Korean 으로 설정해야 한다. 
    - ##### Lambda 함수 응답은 JSON 형태의 depth가 1이고 value는 기본 타입을 사용해야한다. value에 list, json 등이 오면 `Invoke AWS Lambda Function`에서 Error로 판단한다.
    - ##### Play prompt 등에서 Flow 내 한국어로 된 변수(Lambda 응답, Contact Attribute 등)를 사용하려면 Type은 반드시 Text 이여야 한다. SSML은 한국어 변수를 제대로 인식하지 못한다. 
  - #### 개발적인 요소가 들어가는 곳은 Contact Flow에서 `Invoke AWS Lambda Function`에서 Lambda를 개발하고 변수를 활용하는 부분이다.
  - #### 1) Invoke AWS Lambda Function
    - ##### Document : [Amazon Connect Lambda 활용 가이드](https://docs.aws.amazon.com/ko_kr/connect/latest/adminguide/connect-lambda-functions.html)
    - ##### Lambda 작성하기 ([Amazon Lambda](#amazon-lambda-1) 참고)
        - #### Instance에 Lambda 추가하기
          - ##### Amazon Connect Console → 원하는 Instance의 alias 선택 → AWS Lambda 영역에서 Lambda 작성 또는 만들어둔 Lambda 선택
        - #### Contact Flow에 Lambda 활용하기
          - ##### Lambda에 Input Parameter 부여하기
            - ###### Input Parameter로 넘기는 방법은 크게 2가지로, `Contact Attribute`로 설정해서 넘기는 방법과 `Invoke AWS Lambda Function`에서 직접 부여하는 방법이 있다.
            - ###### `Contact Attribute` → Add another attribute → User defined로 임의의 값을 설정하거나 System value 사용

              <img width="500" alt="스크린샷 2023-01-31 16 39 07" src="https://user-images.githubusercontent.com/21374902/215784146-f924f1e5-4a54-4d30-bc0c-6d7fbc1fc6f4.png">
              
            - ###### `Invoke AWS Lambda Function` → Add a parameter → User defined로 임의의 값을 설정하거나 System value 사용

              <img width="500" alt="스크린샷 2023-01-31 16 41 20" src="https://user-images.githubusercontent.com/21374902/215784157-af7f3f5c-e111-4e8e-a05b-3e89084024af.png">
          - ##### Lambda에서 Input Parameter 사용하기
            - `Contact Attribute`로 설정한 값 : Details.ContactData.Attributes.contactName;
            - `Invoke AWS Lambda Function`로 설정한 값 : Details.Parameters.nickname;
            - Lambda Test를 할 때 amazonConnect 샘플을 보면 Input 데이터 형식을 볼 수 있다.
              ```json
              {
                "Name": "ContactFlowEvent",
                "Details": {
                  "ContactData": {
                    "Attributes": {},
                    "Channel": "VOICE",
                    "ContactId": "5ca32fbd-8f92-46af-92a5-6b0f970f0efe",
                    "CustomerEndpoint": {
                      "Address": "+11234567890",
                      "Type": "TELEPHONE_NUMBER"
                    },
                    "InitialContactId": "5ca32fbd-8f92-46af-92a5-6b0f970f0efe",
                    "InitiationMethod": "API",
                    "InstanceARN": "arn:aws:connect:us-east-1:123456789012:instance/9308c2a1-9bc6-4cea-8290-6c0b4a6d38fa",
                    "MediaStreams": {
                      "Customer": {
                        "Audio": {
                          "StartFragmentNumber": "91343852333181432392682062622220590765191907586",
                          "StartTimestamp": "1565781909613",
                          "StreamARN": "arn:aws:kinesisvideo:us-east-1:123456789012:stream/connect-contact-a3d73b84-ce0e-479a-a9dc-5637c9d30ac9/1565272947806"
                        }
                      }
                    },
                    "PreviousContactId": "5ca32fbd-8f92-46af-92a5-6b0f970f0efe",
                    "Queue": null,
                    "SystemEndpoint": {
                      "Address": "+11234567890",
                      "Type": "TELEPHONE_NUMBER"
                    }
                  },
                  "Parameters": {}
                }
              }              
              ```         
          - ##### Lambda의 Response를 Flow에서 사용하기
            - `$.` 문자로 변수를 사용할 수 있다.
            - 한국어를 사용할 경우 SSML은 사용할 수 없기 때문에 <say-as>, <speak> 태그는 사용하지 않아야 하고 interpret as = Text로 설정하고 변수를 바로 사용해야 한다. (예시 : `$.External.nickname`)
            
              <img width="500" alt="스크린샷 2023-01-31 16 39 32" src="https://user-images.githubusercontent.com/21374902/215784926-429b755e-6da6-4efd-8924-8cc6ef34bbca.png">
          
              <img width="500" alt="스크린샷 2023-01-31 16 41 58" src="https://user-images.githubusercontent.com/21374902/215785100-32f6fab8-336a-442e-8c44-7441461dc401.png">

---

Amazon Lambda
===
# 1. 기본개념 
### (1) 목표 : Amazon Lambda를 로컬 환경에서 개발하고 테스트할 수 있다.
OS | IDE | Language
---|---|---
macOS | Intellij Ultimate | Java 11

### (2) AWS SAM CLI 설치
- `brew tap aws/tap`
- `brew install aws-sam-cli`
- `sam --version`
- `brew update aws-sam-cli`

### (3) Intelli에 AWS Toolkit 설치
- Preferences -> Plugins -> Martketplace -> AWS Toolkit 설치

### (4) Serverless 프로젝트 생성
- File -> New -> Project -> AWS -> AWS Serverless Application
- `Validation of sam failed: Not installed.` 에러가 뜰 경우, SAM CLI executable에 `which sam` 해서 나온 경로 입력

### (5) Build & Test with SAM
- ⭐️ Docker를 Rancher로 돌리는 경우엔 sam 사용이 불가합니다. Docker Desktop을 설치하거나 AWS Lambda Console에 올려서 테스트 해야 합니다.
- Intellij
  - Run Configuration에서 input, aws profile 등 설정하고 실행
- Command
  - SAM 구동 후 테스트 : `sam local start-api` -> 원하는 URL 호출
  - 직접 호출하는 방법 : `sam local invoke "HelloWorldFunction" -e events/event.json`

### (6) 함수 개발
- #### ⭐ 중요 포인트
  - ##### 함수의 응답은 String 타입이 아닌 POJO 형식의 Class를 반환해야 한다. 응답을 String 타입의 JSON 형태로 하면 __"{\\"name\\":\\"beaver\\"}"__ 처럼 큰 따움표가 붙는다.
  - ##### POJO 형식의 Class를 반환하면 Lambda에서 알아서 Serialize를 해준다.
  - ##### 함수는 반드시 RequestHandler Class를 상속 받아야 하고 Input/Output Parameter의 형식을 지정할 수 있다.
  ```java
  public class ScanTable implements RequestHandler<Object, ScanTableResponse> {

    private final Region REGION = Region.AP_NORTHEAST_2;
    private final String tableName = "sampleTable";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(REGION).build();

    @Override
    public ScanTableResponse handleRequest(Object input, Context context) {
        JsonObject json = gson.toJsonTree(input).getAsJsonObject();
        String nickname = json.getAsJsonObject("Details").getAsJsonObject("Parameters").get("nickname").getAsString();
        String contactName = json.getAsJsonObject("Details").getAsJsonObject("ContactData").getAsJsonObject("Attributes").get("contactName").getAsString();
        String phoneNumber = json.getAsJsonObject("Details").getAsJsonObject("ContactData").getAsJsonObject("CustomerEndpoint").get("Address").getAsString();

        Map<String, AttributeValue> where = new HashMap<>();
        where.put(":phoneNumber", AttributeValue.builder().s(phoneNumber).build());

        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(tableName)
                .expressionAttributeValues(where)
                .filterExpression("phoneNumber = :phoneNumber")
                .build();

        try{
            ScanResponse response = dynamoDbClient.scan(scanRequest);
            return ScanTableResponse.builder()
                        .isReserved(true)
                        .nickname(nickname)
                        .contactName(contactName)
                        .phoneNumber(phoneNumber)
                        .flightNo(response.items().get(0).get("flightNo").s())
                        .build();
        }catch (Exception e){
            System.out.println("Logger Exception : " + e.toString());
            return ScanTableResponse.builder()
                    .isReserved(false)
                    .nickname(nickname)
                    .contactName(contactName)
                    .phoneNumber(phoneNumber)
                    .build();
        }
    }
  }   
  ```

### (7) Lmabda 테스트
- Lambda에 Jar 업로드하기
  - build.gradle에 task 추가
    ```yaml
    task buildZip(type: Zip) {
      from compileJava
      from processResources
      into('lib') {
        from configurations.runtimeClasspath
      }
    }
    ```
  - gradle에서 buildZip 실행
  - 생성된 Jar 파일을 AWS Lambda Console에 업로드
  - 아래 사진과 같이 Lambda Console에서 실행시킬 함수의 경로를 지정한다. (Runtime settings ➡️ Handler)
    
    <img width="1518" alt="image" src="https://user-images.githubusercontent.com/21374902/219037044-bcfaa3c8-f956-4cc2-9b16-597201c208e4.png">
    
- Lambda 함수 테스트
  - Lambda Console에서 Test 탭으로 이동 후 원하는 이벤트 타입 (Input Paramter)를 설정 후 구동해본다. 

# 2. Lambda with DynamoDB 
- dependencies in build.gradle
  ```yaml
  dependencies {
    implementation 'com.amazonaws:aws-lambda-java-core:1.2.1'
    implementation 'com.amazonaws:aws-lambda-java-events:3.11.0'
    implementation 'software.amazon.awssdk:dynamodb:2.19.31'
    implementation 'org.projectlombok:lombok:1.18.24'
    implementation 'com.google.code.gson:gson:2.9.0'
    annotationProcessor 'org.projectlombok:lombok:1.18.24'
  }
  ```
- DDL
  - Create a table
    - Create a table with a simple primary key
    - Create a table with a composite primary key
  - List tables
  - Describe (get information about) a table
  - Modify (update) a table
  - Delete a table
- DML
  - Retrieve (get) an item from a table
      ```java
    public class QueryTable implements RequestHandler<Object, QueryTableResponse> {
      private final Region REGION = Region.AP_NORTHEAST_2;
      private final String tableName = "table_name";

      @Override
      public QueryTableResponse handleRequest(Object input, Context context) {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(REGION).build();

        HashMap<String,AttributeValue> keyToGet = new HashMap<String,AttributeValue>();
        keyToGet.put("name",AttributeValue.builder().s("beaver").build());

        GetItemRequest getItemRequest = GetItemRequest.builder()
                  .tableName(tableName)
                  .key(keyToGet)
                  .build();

        try{
            Map<String,AttributeValue> returnedItem = dynamoDbClient.getItem(getItemRequest).item();
            if(returnedItem != null) {
                return QueryTableResponse.builder()
                                  .name(returnedItem.get("name").toString())
                                  .isReserved(true)
                                  .build();
            }else {
                return QueryTableResponse.builder()
                                  .isReserved(false)
                                  .build();
            }
        }catch (Exception e){
            return QueryTableResponse.builder()
                                .isReserved(false)
                                .build();
        }
      }
    }
    ```
  - Retrieve (get) an item from a table using the asynchronous client
  - Add a new item to a table
  - Update an existing item in a table
  - Delete an existing item in a table

# 3. Reference
- https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/examples-dynamodb-items.html
- https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStarted.html
- https://docs.aws.amazon.com/ko_kr/amazondynamodb/latest/developerguide/ScanJavaDocumentAPI.html