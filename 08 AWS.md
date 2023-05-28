AWS
===

# 목차
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

---

<details>
<summary><font size="5"><b>AWS Keywords</b></font></summary>

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

<details>
<summary><font size="5"><b>AWS 내용 정리</b></font></summary>

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

</details>

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
### 참고자료 : [Udemy 강의](https://udemy.com/course/best-aws-certified-developer-associate), [기출문제](https://exam.ogu45.com/#/study/1)
### 👨‍🎓 2023.02.27 취득 완료

---

<details>
<summary><font size="5"><b>합격 후기</b></font></summary>

- ### 시험 신청
  - 서울에는 시험장이 `4곳`이 있고 날짜를 선택하면 `시험 시간`도 시간 단위로 선택할 수 있다.
  - 시험 일정 변경은 `2회`까지 가능하다.
  - 시험을 보는 언어를 선택할 수 있는데 한국어로 선택하면 번역 문제 때문에 최신 유형이 안나온다는 소문이 있었다.
  - 영어로 시험을 볼 때 `시험 편의 지원`에서 신청을 하면 시험 시간을 30분 더 받을 수 있다. (대체로 시험 시간이 부족하지는 않다.)
  - 한국어로 시험을 신청해도 시험볼 때 영어로 문제를 볼 수 있는 기능이 있기 때문에 굳이 영어로 신청하지 않아도 된다.
  - 시험 준비물로 `신분증`과 `신용카드`가 있는데 신용카드에 적혀있는 `영어 이름`과 `시험 신청서에 있는 이름`이 반드시 똑같아야 한다.
- ### 시험 공부
  - AWS 공부보다 시험 합격이 목표라면 기출문제만 봐도 무관하다.
  - 시험 관련 강의는 메가존클우드, 인프런, 유데미를 봤었는데 유데미가 강의가 가장 길지만 WEB 전반적인 내용까지 같이 다뤄주고 간단한 실습도 포함되어 있어서 가장 유익했고 시험을 합격한 후에도 완강할 예정이다.
  - 기출 공부는 아래와 같은 순서로 진행했다.
    1. [기출 문제](https://exam.ogu45.com/#/study/1)의 답이 나오게 켜놓고 모든 문제를 key-value로 정리했다.\
      문제 : 회사에서는 시스템 관리자가 문제를 보다 효과적으로 해결할 수 있도록 개발자가 작성한 AWS `Lambda 함수가 오류를 기록`하도록 요구합니다. 개발자는 이 요구 사항을 충족하기 위해 무엇을 구현해야 합니까?\
      정답 : Lambda 함수 코드의 `로깅 문`을 통해 오류를 보고합니다.\
      ➡ key-value : Lambda 함수 오류 기록 / 로깅\
      🔗 아래에 있는 `Match Keywords` 영역에 모든 문제를 정리했다.
    2. key-value로 정리한 문제를 자주 나오는 keyword로 grouping해서 정리하고 비슷한 문제를 붙여놓았다.\
       자주 나오는 keyword는 Lambda, DynamoDB, Kinesis Stream 등이 있다.
    3. 정리한 내용에서 중요한 단어 위주로 `마스킹`을 했다.
    4. `마스킹`한 내용 위주로 전체를 한 번 읽는다.
    5. 이정도까지 정리하면 자연스럽게 대부분의 기출이 외워지기 때문에 기출 문제를 빠르게 풀면서 틀린 것만 오답노트로 간단하게 정리한다. 
- ### 시험
  - ️신분증과 ⭐신용카드⭐를 반드시 챙겨야 한다. (신용카드에 있는 영어 이름과 시험 신청서에 있는 이름을 대조한다.)
  - 시험장에 입장하면 손을 씻고 신분증, 신용카드 제출을 하고 몇가지 시험 안내를 받는다.
  - 모든 짐과 겉옷과 악세사리 등은 시험장에 들어가기 전에 사물함에 보관한다.
  - 시험은 학동역쪽 경복빌딩에서 14시에 봤었고 40분쯤 가니까 미리 입장에서 바로 시험을 볼 수 있었다.
  - 시험 문제는 총 65문제로 기출에서 60%정도가 그대로 나와서 바로 풀 수 있었고 몇몇 문제는 기출 문제에서 비슷하게 나와서 쉽게 풀 수 있었다.
  - 시험 문제마다 버튼이 있어서 문제와 보기를 영어로 볼 수 있었다.
  - 40분 정도 시험을 보고 나왔고 당일날 밤에 결과를 확인할 수 있었다. (결과까지 최대 5일 소요)

</details>

---

<details>
<summary><font size="5"><b>AWS 내용 정리</b></font></summary>

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

</details>

---

<details>
<summary><font size="5"><b>시험에 자주 나오는 개념</b></font></summary>

- ### API Gateway
- ### Elastic Beanstalk
- ### CodeCommit & CodePipeline & CodeBuild & CodeDeploy
- ### Cognito
- ### CloudFormation
- ### CloudWatch
- ### DynamoDB
- ### ElastiCache & Load Balancer
- ### S3 & Encryption
- ### SNS & SQS
- ### Lambda
- ### X-Ray와
- ### Beanstalk
- ### 읽기/쓰기 처리량 계산
  - 강력한 일관된 일기(RCU)는 4KB 단위로 늘어나고 초당 처리 단위로 환산해서 계산한다.
  - 강력한 일관된 쓰기(WCU)는 1KB 단위로 늘어나고 초당 처리 단위로 환산해서 계산한다.
  - 5KB를 처리하기 때문에 2 RCU가 필요하고 초당 3개를 처리하니까 읽기 처리량은 2*3 = 6\
    7KB를 처리하기 때문에 7 WCU가 필요하고 초당 10개를 처리하니까 쓰기 처리량은 7*10 = 70
  - 최대 4KB 항목의 강력히 일관된 읽기 요청에는 하나의 읽기 요청 단위가 필요합니다.\
    최대 4KB 항목의 최종 읽기 일관성 요청에는 절반의 읽기 요청 단위가 필요합니다.\
    최대 4KB 항목의 트랜잭션 읽기 요청에는 2개의 읽기 요청 단위가 필요합니다.
  - 강력한 일관된 읽기는 1 RCU of 4KB. 5KB는 2 RCU가 필요하기 때문에 100 * 2 CRU = 200
  - 1분에 600 write => 초당 10 write. 1KB는 1 WCU가 필요하기 때문에 10 * 1 = 10

</details>

---

<details>
<summary><font size="5"><b>Match Keywords</b></font></summary>

⭐️ 2023년 2월 28일 개편되기 전 시험에 해당하는 정리입니다.

⭐ 자주 사용되는 Keyword로 Grouping

## API Gateway
문제 | 답안 
---|---
API G/W API 특정 리소스 접근이 가능하도록 `유일한 사용자 구성`. `토큰 자동 만료/새로고침`이 가능하도록 하려면? | Cognito 사용자풀 사용, Authorizer 구성, 자격 증명 또는 Access token 사용
API G/W, Lambda, S3 호스팅웹에서 CORS 오류 | API G/W에서 메서드에 대한 `CORS 활성화`
DynamoDB, Lambda, API G/W 구성. `요청 대기 시간 길어짐`. 식별할 수 있는 방법 | API G/W와 Lambda에 `X-Ray 추적을 활성화`하고 사용자 요청 추적 및 분석
API G/W 사용해서 WebSocket API 구축. API로 전송되는 `Payload`는 생성, 업데이트, 제거의 값을 가진다. `값에 따라 다른 경로와 통합`해야 한다. | 경로 선택 표현식 값을 `$request.body.action`으로 설정
API G/W는 통과했지만 Lambda로 도달 안됨. 두번째 계정의 Lambda는 최대 동시성 실행 | 두번째 Lambda 함수의 `동시 실행 제한 구성`
API G/W, Lambda, GET 메소드 허용 | Lambda 함수가 있는 API G/W, API G/W에 `노출된 GET 메소드`
새 페이지는 CreateApiKey를 사용해서 새 API Key를 생성하고 사용한다. 기존 사용자는 정상인데 `새로운 사용자`는 `403 Forbedden 오류` 수신 | 새로 생성된 API Key를 `올바른 사용 계획과 연결`하려면 `createUsagePlanKey` 메서드를 호출
API G/W, Lambda 어플리케이션. API 호출하면 `Method completed with status: 502` | API 호출에 대한 `Lambda 응답 형식 변경`
API G/W, v1, v2 버전 배포. v1은 6개월 사용 허용 | v2용 `새로운 url` 만들어서 배포
테스트 목적으로 `다른 버전의 API` 호출 | 각 버전에 맞는 `다른 URL`을 배포
API G/W에 활성화된 `캐시를 무효화`하는 옵션을 요구했고 조치할 수 있는 방법 | 고객에게 `Cache-control:max-age=0` 이라는 HTTP 헤더를 전달하도록 요청
특정 AWS 계정의 `사용자에 대한` API 액세스를 제한하는 방법 | API G/W `리소스 정책`
API G/W, Restful에 대한 인증 구현. 호출을 인증하려면 Client ID, User ID가 있는 HTTP 헤더가 포함되어야 한다. 자격 증명은 DynamoDB 인증 데이터와 비교. API G/W에 구현하려면 해야할 작업은? | DynamoDB `인증 테이블을 참조`하는 `Lambda 권한 부여자 구현`
API G/W를 업데이트 하지 않고 `Code Push`를 효율적으로 수행하는 방법 | Lambda에서 별칭 및 버전 생성
`XML 기반 SOAP Interface`. API G/W를 사용해서 외부에 노출 필요 | API G/W Restful API 생성하고 `Mapping Template`를 사용해서 들어오는 `JSON을 SOAP Interface에 유효한 XML Message로 변환`

## Elastic Beanstalk
문제 | 답안 
---|---
Elastic Beanstalk `배포가 오래 걸리는 문제를 해결`하는 방법은? | CodeCommit Repository 생성, 개발자가 커밋 허용, Elastic Beanstalk에 직접 배포
`Elastic Beanstalk`가 배포할 수 있는 AWS 서비스는? | `ASG`, `ELB`, `RDS`
Elastic Beanstalk 지원 플랫폼 | `Tomcat`, `.NET`
Beanstalk에서 지원하는 플랫폼 | `Apache tomcat`, `.NET`
응용 프로그램을 2개의 구성요소로 나누고 독립적으로 확장. Elastic Beanstalk 사용해서 배포. 어떻게 해야할까? | 각 구성 요소를 `별도의` Elastic Beanstalk 환경에 배포
Elastic Beanstalk 배포. ELB가 있는 WEB 계층, RDS 계층 어플리케이션. RDS 인스턴스 분리하는 방법 | RDS 없는 `새로운` Elasic Beanstalk 환경 다시 생성
Elastic Beanstalk 배포. 새 버전은 이전 버전과 호환 안됨. 배포 실패 시 이전 버전으로 롤백. 새 업데이트는 일괄 전환 수행 | `새로운` Elastic Beanstalk 환경에 새 버전을 배포하고 환경 URL을 교체
Elastic Beanstalk로 Python 배포. 소스 번들 생성 시 요구사항 | `최상위 디렉토리`를 포함하지 않아야 하고, `단일 파일`(.zip, .war)로 생성되어야 한다.
Elastic Beanstalk의 `EC2 인스턴스 특정 명령 세트 실행`하려고 한다. Beanstalk의 기능은? | `.ebextensions`
Elastic Beanstalk 어플을 `여러 리전`에 배포. 각 리전에 `서로 다른 AMI` 필요. 리전에 대해 올바른 AMI을 지정할 `CloudFormation Template Key`는 ? | `Mappings`
`Docker` 어플리케이션으로 작업. 다운타임 없이 `자동으로 변경 및 업데이트` | `Elastic Beanstalk`를 사용하고 `일괄 업데이트 정책` 선택
트래픽이 많은 `동적 웹`. `개발을 제외`한 모든 것을 AWS로 `마이그레이션` | Elastic Beanstalk 환경에서 `웹 사이트 코드를 배포`. `ASG로 Instance 수 확장`
Elastic Beanstalk를 사용해서 버전 수를 25개로 제한. 소스 번들은 S3 소스 버킷에서 삭제됨. 어플 `버전 수명 주기` 설정에서 무엇을 해야하나? | 연령별 어플리케이션 `버전 제한 설정을 0`으로 설정
Elastic Beanstalk 환경을 `새 어플 버전`으로 업데이트하는 솔루션 | 어플 코드를 `.zip으로 패키징`하고 Management 콘솔에서 패키징 된 어플을 `업로드`한 다음 `배포`, 어플 코드를 `.zip으로 패키징`하고 Management 콘솔에서 `새 어플 버전을 생성`한 다음 `CLI 사용해서 환경을 재구축`
Beanstalk에서 `다중 컨테이너 Docker 인스턴스`를 구성하기위한 필요한 것 | `ECS 작업 정의`
Elastic Beanstalk `CLB -> ALB`로 마이그레이션. Management 콘솔에서 해야할 작업 | `LB 유형을 제외`하고 동일한 구성으로 새 환경 생성, 기존 환경과 `동일한 버전의 어플리케이션 배포`, `swap-environment-cnames` 작업 실행
`healthcheckurl.config`는 Elastic Beanstalk 구성 파일을 어플리케이션 소스 번들의 어디에 배치? | `.ebextensions 폴더`에
`healthcheckurl.yaml` 에러 | `healthcheckurl.config`로 변경해야함
Elastic Beanstalk 새 버전의 어플 배포. 개발자 수행 작업은? | Elastic Beanstalk Console에서 `새로운 어플리케이션` 버전 업로드 및 배포
Beanstalk 배포. 배포는 `최소한의 영향`. 최대한 빠른 롤백 전략 | Immutable
Elastic Beanstalk 롤아웃을 완료하기 전 `특정 기간 변경 사항을 평가`해야 한다. | Immutable
Beanstalk 배포. `중단 최소화`. 어플 액세스 `로그 보관`. 배포 정책은? | Rolling
Elastic Beanstalk에서 실행할 Linux 어플리케이션. `비용을 최소화`하면서 업데이트 중 `전체 용량을 유지`해야함. 배포 정책은? | Roliing with additional batch
Beanstalk `전체 용량 유지`하고 실패한 배포의 `영향을 최소화` 하는 정책 | Rolling with an additional batch
Elastic Beanstalk `전체 용량을 유지`하면서 배포하는 방법 | `Rolling with additional batch`


## CodePipeline, CodeCommit, CodeBuild, CodeDeploy
문제 | 답안 
---|---
`CodeCommit Repository`에 대한 `HTTPS 복제 URL` 사전 구성 방법은? | AWS `자격 증명 프로필` 사용하고 `Git 자격 증명 도우미` 설정. 도우미가 `Repository로 경로`를 보낼 수 있도록 구성
Github Repository -> `CodeCommit` HTTS를 통해서 마이그레이션 | IAM에서 생성된 `Git 자격 증명 세트`
`소스 변경`될 때 배포. `배포 트리거` 방식 | 소스 코드를 `S3에 저장`하고 파일이 변경되면 시작하는 `CodePipeline 구성`, 소스 코드를 `CodeCommit Repository에 저장`하고 커밋될 때 시작하는 `CodePipeline 구성`
전달 파이프라인은 `CodeCommit Repository`의 Master Branch에 대한 `변경 트리거`<br>`CodeBuild`를 사용해서 프로세스 테스트 및 빌드 단계 구현<br>`CodeDeploy`로 배포<br>파이프라인은 정상. CodeDeploy가 `배포를 안함`. 가능한 원인은? | CodeCommit Repository의 `Master Branch에서 변경 사항이 적용되지 않음`, 파이프라인 `초기 단계 중 하나가 실패`해서 파이프라인 종료
`CodePipeline`에서 `단위 테스트`를 작성하고 파이프라인 `일부로 실행`하려면 | 단위 테스트 `실행 단계를 포함`하도록 `CodeBuild` 사양 업데이트
stg, test, prod 환경이 있다. Stg `배포 후` QA, Prod에 배포하려고 한다. | `CodeDeploy`를 사용해서 `여러 배포 그룹`을 생성
stg, test, prod 환경에 배포. 관리할 `리소스 수를 최소화`하면서 배포하는 방법 | `여러 별칭`이 있는 `하나의 Lambda 함수`를 사용해서 여러 단계의 API G/W 하나를 생성
stg, test, prod 환경에서 API G/W는 `237GB 캐시`를 사용중. 효율적인 배포 전략은? | `필요할 때`만 개발 및 테스트 환경용 `캐시를 활성화`
운영 배포 전 코드를 검토하고 `승인이 필요`함 | `CodePipeline에 승인 작업을 추가`하고 승인이 필요할 때 `SNS 주제에 게시`해서 승인 작업을 기다린다.
코드가 Prod에 배포 되기 전 `승인이 필요` | `CodePipeline` 단계에서 `승인 작업` 사용
`CodeDeploy`로 배포할 때 `내부 배포`에 대한 `후크 실행 순서` | `프로그램 중지` -> `설치 전` -> `설치 후` -> `프로그램 시작`
`CodePipeline`을 구현하고 각 단계의 작업 상태를 `Lambda로 알림`을 보내려고 함. Lambda 함수를 `이벤트 소스와 연결하는 단계` | `CodePipeline`을 이벤트 소스로 사용하는 `CloudWatch Events 규칙`을 생성
`CodeBuild`는 코드 빌드 -> 도커 이미지 생성 -> ECR에 푸쉬 -> 이미지에 태그 지정. 개발자가 `CLI`를 구성한 경우 `도커 이미지를 가져오는 방법` | `aws ecr get-login`의 출력을 실행한 후 `docker pull repository uri:tag` 실행
로컬에 어플의 빌드,번들,패키징. EC2에 배포해야함 | `번들을 S3`에 업로드 하고 `CodeDeploy를 사용해서 배포`를 수행할 때 `S3 위치 지정`
`Code Repository`에서 Commit을 위한 Pipeline에서 `단위 테스트를 Trigger`하고 Pipeline의 `실패 이벤트에 대해 알림` 수신 | `CodeCommit`에 `Code를 저장`하고 `CodePipeline`을 생성해서 `단위 테스트를 자동화`하고 `CloudWatch를 사용해서 실패 이벤트 알림`을 Trigger
`온프로미스`에서 실행되는 `EC2 인스턴스` 및 `가상 서버`에 어플리케이션 패키지 `배포를 자동화` | `CodeDeploy`
`EC2 인스턴스`와 `온프로미스`에서 실행되는 `가상 서버` 모두에 S/W Package `배포를 자동화` | `CodeDeploy` 사용
여러 개발자와 `코드 공유`, 여러 버전 및 `일괄 변경 추적`과 함께 `장기간 저장` 필요. | `CodeCommit`
여러 파일에 대한 일괄 변경 지원, 병렬 분기, 버전 추적 | CodeCommit
`CodeDeploy`로 `EC2에 배포`. 특정 배포 파일에 `파일 권한 변경`. 어떤 `수명 주기 이벤트`를 사용해야 하나 | `AfterInstall`
서비스는 변경 세트를 피어 투 피어로 교환해서 `여러 분산 Repository로 동기화`하는지 확인해야 한다. `네트워크가 없어`도 작업 가능해야 한다. | `CodeCommit`
`CodeDeploy`를 통해 `재배포` 하기 위한 파일 | `appspec.yml`
`CodeDeploy`를 작동하려면 `appspec.yml` 파일은 어디에 배치? | 어플리케이션 소스 코드의 `루트 디렉토리`
`CodeDeploy` 사용해서 외부 MySQL과 연결하는 어플리케이션 배포 자동화. `API Key, DB Pwd`에 `안전하게` 액세스 하기 원함 | `System Manager Parameter Store`를 사용해서 EC2 인스턴스 IAM 역할을 사용하여 암호를 저장하고 프로그래밍 방식으로 액세스
`CodeBuild` 프로젝트를 실행할 때 `환경 변수 길이`가 결합된 문자 최대 길이 `초과` | `System Manager Parameter Store`를 사용해서 `환경 변수를 저장`

## Cognito
문제 | 답안 
---|---
API G/W로 액세스 하는 어플리케이션. `타사 SAML ID 공급자`의 인증을 받아야 한다. 인증이 완료되면 AWS 자원에 액세스 가능 | SAML 자격 증명 공급자가 있는 `Cognito 자격 증명 풀`을 `인증 공급자` 중 하나로 사용
`사용자 자격 증명` 절대 노출 금지 | Cognito `사용자풀` 구성하고 Cognito API로 `사용자 인증`하고 `권한 부여`
인증 없는 제한된 액세스 `게스트` 허용 | `인증되지 않은 액세스`가 활성화 된 Cognito
등록된 사용자와 게스트가 있을 때 두 유형 `모두에게 액세스`를 제공하려면 ? | Cognito를 사용해서 `인증된 역할`과 `인증되지 않은 역할`을 사용해서 액세스 제공, IAM을 사용해서 사용자 유형에 따라 `STS(Security Token Service) 작업`을 사용해서 다른 역할을 맡도록 하고 `위임한 역할`에 대한 엑세스 제공
비로그인 `게스트`가 Cognito 자원 사이트에 액세스해서 `S3 파일을 다운로드` 허용 | `새 자격 증명 풀을 생성`하고 `인증된 자격 증명에 대한 액세스를 활성화`하고 S3 액세스 권한 부여
FE어플은 Congnito 사용자 풀 사용. 인증 흐름 처리. SDK 사용해서 DynamoDB 어플 통합. `비밀키 노출하지 않고` API 안전하게 호출 | Cognito `자격 증명 풀`을 구성하고 `JWT`을 임시 자격 증명으로 교환
로그인 프로토콜을 `MFA로 고도화` 하려고 한다. | `MFA가 포함된 Cognito`
Cognito `사용자 풀` 사용. 회사 로고가 있는 `로그인 페이지` 만들고 싶다. | Cognito에서 `호스팅 사용자 인터페이스`를 생성하고 `회사 로고로 사용자 지정`
회사의 모든 직원 정보가 `SAML 직원 디렉터리에만` 남아 있어야 한다. 직원에게 승인된 액세스를 제공해서 `자신의 어플에만` 액세스 하도록 해야한다. | Cognito `자격 증명 풀`을 사용하고 `SAML 공급자와 연동`해서 `IAM 조건 키`를 사용해서 직원에게 액세스 권한 부여
사용자 자신이 `비밀번호를 재설정`하도록 허용하면서 `사용자 인증 및 권한`을 관리할 수 있는 것 | Cognito `사용자 풀`, `자격 증명 풀`
Cognito에서 사용자를 `인증`하고 `DynamoDB 레코드`를 생성하는 흐름 | Cognito 사용자 풀에서 `토큰을 인증`하고 받는다. Cognito 자격증명 풀을 사용해서 토큰을 `AWS 자격증명`으로 바꾼다. AWS 자격증명으로 DynamoDB에 액세스한다.
Facebook 등 `OpenID 자격 증명 공급자`를 기반으로 인증이 필요. `사용자 지정 권한 부여` 모델을 기반으로 액세스 허용 | Cognito `사용자 풀`과 `사용자 지정 권한 부여자`를 사용해서 `JWT 기반`으로 사용자를 인증하고 권한을 부여
`다단계 인증`이 필요한 모바일 앱 | Congnito `사용자 풀`을 생성하고 사용자 생성, 사용자 풀에 대한 `다단계 인증 활성화`
ALB. CloudFront. `소셜 미디어`로 로그인 | Cognito `인증 공급자` 중 하나로 사용하도록 `CloudFront 구성`
모바일 게임. 데이터는 로컬 저장. `여러 장치`에서 사용하니까 `데이터 동기화` 필요 | Cognito로 `데이터 동기화`
특정 ID에 액세스하는 `모든 장치에 푸쉬`하려면 프로필 데이터에 업데이트가 필요 | `Cognito Sync`
Cognito `모든 장치` 업데이트 자동으로 알림 기능 | 적절한 `IAM 역할`과 `푸시 동기화 기능`

## CloudFormation
문제 | 답안 
---|---
CloudFormation Template로 `Lambda 배포 절차`는? | Template에서 AWS::Lambda::Function `리소스 생성` 후 CloudFormation Template `내부에 코드 작성`, 코드 .zip `S3에 업로드` 후 AWS::Lambda::Function 리소스에 `참조 추가`
ASG, EC2. 테스트를 위한 다수의 단기 Instance. CloudFormation Template 사용해서 관리자가 시작. `테스터에게 테스트 환경만` 허용. `광범위 권한 부여하지 않음.` | 환경 템플릿에서 `Service Catalog` 제품 생성. 기존 역할이 있는 제품에 `시작 제약 조건` 추가. 테스트 사용자에게 `Service Catalog API`만 사용할 수 있는 권한 부여. `Service Catalog` 콘솔에서 템플릿을 시작하도록 교육.
CloudFormation Template에 `Serverless Model`에 의해 정의된 객체를 포함하려면 `문서 루트`에 포함해야 하는 섹션 | `Transform`
Serverless에서 배포할 때 `롤백`할 수 있는 기능이 필요함. `자동화` 방안은? | CloudFormation Template `Serverless Application Model`을 준수하는 구문을 사용해서 `Lambda` 함수 리소스를 정의
Lambda, DynamoDB, API G/W. 배포 준비 끝. `롤백 기능` 필요. 자동화 방안 | CloudFormation Template의 `Serverless Application Model`을 준수하는 구문을 사용해서 `Lambda` Resource를 정의
CLI 명령어 `aws cloudformation deploy`를 사용해서 배포할 수 있도록 `template`을 준비하려면? | `aws cloudformation package`를 사용해서 `소스 코드를 S3 버킷`에 업로드하고 수정된 `CloudFormation Template를 생성`한다.
AWS::ElasticLoadBalancing::LoadBalancer 리소스 이름이 "ElasticLoad Balancer"인 CloudFormation에서 생성된 `로드 밸런싱`된 `웹 사이트의 URL을 반환`하는 코드 | "Fn::Join" : ["". [ "http://", {"Fn::GetAtr" : [ "ElasticLoadBalancer","DNSName"]}]]
CloudFormation Stack의 Resource 중 `하나를 생성할 수 없으면`? | 기존에 생성된 Resource를 `삭제`하고 Stack `생성을 종료`
CloudFormation Template으로 배포. 스택 중 `하나 업데이트`. 실행 중인 리소스에 미치는 영향 파악 | `변경 세트 조사`
CloudFormation Template 배포. DB의 이름 `변경`. DeletionPolicy 속성이 기본값에서 변경이 안됨. | 새 데이터베이스를 `생성`하고 이전 데이터베이스를 `삭제`한다.
AWS CLI 사용, `Serverless 시작 단계` | `CloudFormation` Package 사용 후 배포

## CloudWatch
문제 | 답안 
---|---
`Step Function` 작업 상태를 CloudWatch로 `오류 표시`. `원래 입려`과 `오류` 모두 보존하는 방법 | `Catch문`에서 `ResultPath`를 사용해서 원래 입력에 오류 표시
CloudWatch Logs에 `예외를 계산을 위한 메트릭 필터` 생성. 결과가 반영되지 않음. `원인`은? | 필터를 만든 `이후에 발생하는 데이터에만` 지표를 생성
타사의 API 사용. 실패가 임계치면 알림. | CloudWatch에 `사용자 지정 지표`를 게시하고 `SNS` 사용
CloudWatch API 호출 `400 에러` | `지수 백오프`로 통화 재시도
로그 때문에 메모리 가득참. `로그 중앙 집중화` 필요 | `CloudWatch`를 설치해서 로그를 CloudWatch로 보내고 전송된 로그는 `인스턴스에서 삭제`한다.
EC2에서 실행되는 어플. `중앙 집권 로`그 | `CloudWatch Logs`
`온프로미스` 어플도 중앙 집권적으로 CloudWatch에 보고 싶음 | CloudWatch `Agent를 온프로미스 서버에 설치`하고 `IAM 사용자 자격 증명`을 사용하도록 Agent를 구성
EC2에서 실행중인 어플의 `로그 데이터를 액세스` 하고 싶다. | EC2에서 CloudWatch Logs `Agent를 설치`
EC2 인스턴스에 CloudWatch `어플 지표를 저장`하려고 한다 | CloudWatch `PutMetricData API` 호출을 사용해서 사용자 지정 지표를 제출한다. API 호출을 활성화하기 위한 `IAM 역할로 EC2 인스턴스를 시작`한다.
Lambda 함수가 설정된 시간 제한 미만으로 완료되도 `API G/W에서 시간초과`가 발생. CloudWatch의 `API G/W 지표` 중 도움이 되는 것 | `IntegrationLatency`, `Latency`
개발과 운영 `로깅 추가` | 코드에서 다른 로깅 로직을 구현하는 다른 `Lambda 함수`를 가리키고 `CloudWatch Lgos에 액세스`
`콜백 트래픽`이 많음. 콜백이 지속적으로 수신되는지 확인하고 싶고 데이터를 `10일 동안 유지`. 콜백 수의 임계치가 초과하면 `경고` 받고 싶음 | 콜백 데이터를 CloudWatch에 `사용자 지정 지표`로 푸쉬하고 CloudWatch `알림 매커니즘`을 사용해서 시스템 관리자에게 알림
CloudWatch Logs의 Log Group에 중요한 로그 데이터를 게시. KMS 고객 마스터 키를 사용해서 로그 데이터를 `암호화` 해야함 | CLI `Associate-kms-key` 명령을 사용해서 ARN 지정

## DynamoDB
문제 | 답안 
---|---
온프로미스 상태 저장 웹 서버를 마이그레이션. 더 큰 `탄력성` 원함. | `DynamoDB에 세션 상태 데이터 저장`. `ASG이 있는 ELB 사용`
DynamoDB. 개별 사용자로 구성. `다른 사용자 데이터 액세스 불가`하도록 하려면? | `기본 키 값을 기반`으로 항목 액세스 제한
DynamoDB 특징은? | `낙관적 동시성 제어` 사용, `일관성을 위해 조건부 쓰기` 사용
기존 SNS 계정 사용해서 게임 로그인, 데이터는 DynamoDB 저장, DynamoDB API 안전한 접근 방식 필요. `DynamoDB API 요청에 서명`하는 방법은? | `Web ID 연합` 사용, `임시 보안 자격 증명`을 요청에 서명
`Provisioning 된 처리량 효율성`을 위한 DynamoDB Hash Key Schema의 예시는? | Application의 `User ID`
DynamoDB에서 `ProvisionedThroughputExceededException` 발생. CloudWatch에 처리량 초과하지 않음. 원인은? | 특정 Hash Key에 대한 용량을 초과
DynamoDB `한도를 높일 수 있는` 항목 | 계정당 `테이블 수`, `프로비저닝된 처리량 단위 수`
DynamoDB `대량 트래픽` | `Accelerator`를 사용해서 데이터를 캐시
DynamoDB 요청의 95%가 `반복 읽기`. NoSQL 계층을 확장해서 캐싱하기 위한 전략 | DynamoDB `Accelerator`
DynamoDB. 읽기/쓰기 작업에 대한 `응답 시간 줄이고 싶음` | DynamoDB `Accelerator`
Lambda로 `정적 텍스트`와 `이미지`가 모두 포함된 뉴스레터를 다수 사용자에게 보내야함. 뉴스레터에 `하이퍼링크될 이미지를 저장`할 수 있는 빠르고 확장성 있는 저장소 필요 | `S3 Bucket`과 `S3 Transfer Acceleration`을 사용해서 이미지 다운로드 속도 향상
DynamoDB에 `실시간 동적 업데이트`. `덮어쓰기 방지` 옵션은? | `조건부 쓰기`
EC2 어플이 DynamoDB `쓰기` 권한. `보안키`는 사용하지 않음 | EC2에 `IAM 추가`. DynamoDB에 `쓰기 권한 IAM 역할 생성`
DynamoDB `많은 읽기` 용량 소비. 속성이 매우 큼. 어플은 모든 속성이 필요하지 않음. | 최소한의 `프로젝션 속성 집합`으로 `글로벌 보조 인덱스`를 만든다.
EC2 실패할 경우 `세션이 손실`되지 않도록 해야 한다. | DynamoDB 사용해서 `확장 가능한 세션 처리`를 수행
`분라`되있는 DB에서 다른 DB에서 거의 `실시간으로 업데이트`를 가져오는 방법 | DynamoDB `Stream`을 사용해서 다른 DB의 모든 변경 사항을 전달
설문조사 결과를 DynamoDB에 저장. 데이터 처리 후 레코드를 S3에 저장. 각 설문 조사의 데이터를 보관할 수 있는 방식 | 테이블 DynamoDB `Stream을 활성화`하고 Stream을 `Lambda 트리거`로 작성. Stream 레코드가 수정되면 S3에 저장
폴링 응용 프로그램. 풀 결과를 `DynamoDB에 저장`. 풀 데이터 제거 후 `S3에 저장`. | DynamoDB `Stream`을 활성화해서 Stream을 `Lambda에 대한 Trigger`로 구성. Stream 레코드가 수정되면 `S3에 저장`.
여러 EC2에 어플이 있고 DynamoDB에 데이터 저장. `읽기가 강력하게 일관성`이 있는지 확인 | GetItem을 호출할 때 `ConsistentRead`를 true로 설정
아이템 거래. `두 사용자 레코드를 단일 트랜잭션`으로 업데이트하거나 롤백해야 한다. 이런 기능을 위한 DB 옵션 | `트랜잭션 블록` 내에서 수행되는 작업이 있는 MYSQL, `Transact* 작업`을 사용하여 읽기 쓰기를 수행하는 DynamoDB
DynamoDB 글로벌 보조 인덱스 항목을 찾으려고 한다. `가장 적은 수의 읽기 용량 단위`를 사용하기 위해 호출하는 API | `최종 일관성 읽기를 사용한 쿼리 작업`
DynamoDB 테이블에 `세션 정보 캐싱`. 오래된 항목을 자동으로 삭제하는 방법 | 만료 시간이 있는 속성을 추가해서 해당 속성 기반의 `TTL 기능 활성화`
DynamoDB에 요금 데이터 저장. 수시로 변경. 고객한테 변경이 반영되지 않음. 원인은? | 아이템 가격 변경 시 `캐시가 무효화`되지 않는다.
DynamoDB 테이블은 주문 날짜를 기준으로 분할. 트래픽 증가. 쓰기 제한되고 소비된 처리량은 `provisioning된 처리량보다 낮다.` | 파티션 키 값에 `난수 접미사를 추가`한다.
DynamoDB는 `GSI`를 사용해서 읽기 쿼리 지원. 쓰기 활동이 많을 때 작업이 제한됨. `쓰기 용량` 단위는 넉넉함. 제한되는 이유 | `GSI 쓰기 용량` 단위가 부족
DynamoDB에서 대규모 스캔 작업을 사용할 때 테이블에 `프로비저닝된 처리량`에 대한 스캔 영향을 최소화 하기 위한 기술 | 스캔에 대해 더 작은 `페이지` 크기를 설정
DynamoDB에 쓰는 어플이 있다. 사용량이 많아져서 `ConditionalCheckFailedException` 발생. 여러 클라이언트가 동일 레코드에 쓰기 작업 개선 | jiter를 사용해서 `오류 재시도` 및 `지수 백오프` 구현
DynamoDB API. `ThrouttlingException` 오류 발생. SDK와 호환되지 않는 언어로 코딩 | 어플리케이션 로직에 `지수 백오프` 추가
단일 API 호출로 DynamoDB 테이블에서 `여러 항목을 검색`할 수 있는 작업 | `BatchGetItem`
DynamoDB 한시간에 한번씩 분석하고 더이상 필요하지 않음 | 테이블 `삭제` 및 시간당 새 테이블 `생성`
DynamoDB 테이블에서 데이터를 읽을 때 선택한 `일관성 모델`이 `프로비저닝된 처리량`에 미치는 영향 | `강력하게 일관된 읽기`는 `최종 일관된 읽기`보다 더 많은 처리량을 사용한다.
NoSQL을 DynamoDB로 마이그레이션. `빈번한 쿼리` 최적화, 읽기 지연 시간 감소, 테이블의 특정 `주요 속성`에 대한 빈번한 쿼리에 대한 계획 | 자주 쿼리되는 키에 `글로벌 보조 인덱스`를 만들고 `색인`에 필요한 `속성을 추가`
DynamoDB 테이블 스캔 실행 시간을 최소화. 워크로드는 `강력하게 일관된 읽기 용량` 단위의 평균 절반 | 속도를 제한하면서 `병렬 스캔` 사용
Lambda & DynamoDB. 항목을 `검색`, 속성 `업데이트`, 항목 `생성`, 기본 키에 액세스. 필요한 IAM 권한은? | `UpdateItem`, `GetItem`, `PutItem`
파티션 키=user_id, 정렬 키=sport_name. sport_name에 대한 점수를 기반으로 최고 성과자를 표시하는 리더보드. 효율적인 추출 방법 | `파티션 키`가 sprot_name이고 `정렬 키`가 score인 `글로벌 보조 인덱스`를 만들어서 결과를 가져온다.
DynamoDB 대용량의 작업이 `일시적으로만 필요`하다면 | 테이블을 `생성`하고 `삭제`한다.
DynamoDB에서 `Client 요청`에 문제가 있으면? | `4xx` HTTP 응답
DynamoDB API 호출이 `가장 적은` 레코드를 `재처리`하기 위한 방법 | 성공적으로 처라된 항목을 `삭제`하고 새로운 `BatchWriteItem 작업을 다시 실행`
DynamoDB에 제품 정보가 들어있는데 `추가로 이미지`를 포함해야 한다. | `S3`에 이미지를 저장하고 "제품" 테이블 항목에 `S3 URL Pointer`를 추가
DynamoDB 테이블에 있는 여러 항목에 대한 `조정된 전체 변경`을 수행 | `TransactWriteItem` 작업을 사용해서 `변경 사항을 그룹화`하고 테이블 항목을 `업데이트`
DynamoDB `읽기 요청 성능` 향상 | `ElastiCache`의 Creational Cluster의 데이터를 Caching 하도록 어플리케이션을 구성, 테이블의 `읽기 용량` 늘리기
5개 `쓰기 용량 단위`가 있는 DynamoDB 테이블에 트랜잭션을 쓰고 있음. 읽기 처리량 높은 옵션은? | 4KB 크기 항목을 읽는 5개 `읽기 용량 단위`의 `최종 일관된 읽기`
DynamoDB 저장 되는 항목의 크기는 `7KB`, 읽기는 강력한 일관성 필요. 읽기 속도는 `초당 3개` 항목. 쓰기는 `초당 10` 항목. DynamoDB 크기는 | 6 읽기 용량 단위, 70 쓰기 용량 단위
DynamoDB `초당 90개` 읽기 항목. 각 항목은 3KB. 필요한 `읽기 용량 단위` 프로비저닝 | 45
DynamoDB `강력한 일관된 읽기` `초당 100`개 항목을 읽어야 하고 각 항목의 크기는 `5KB`. 프로비저닝된 읽기 처리량을 어떤 값으로 설정해야 하나? | 200 읽기 용량 단위
`600개`의 온도 게이지를 모니터링해서 1분마다 온도 샘플을 수집하고 DynamoDB에 저장. 각 샘플에는 1K 데이터 쓰기가 포함. 테이블에 필요한 쓰기 처리량 | 10 쓰기 용량

## ElastiCache & Load Balancer
문제 | 답안 
---|---
API G/W `endpoint 과부하`가 걸려서 트래픽을 줄여야 한다. | ElastiCache에서 `API 캐싱을 활성화`
ELB + EC2, `세션 데이터` 작성하는 위치는? | `ElasticCache`에 데이터 쓰기
ElastiCache for Redis 사용. 로드 증가. 장애 시 복원력이 필요 | ElastiCache를 `수직적으로 확장`
ELB 뒤에 여러개의 서버. `웹 서버`의 메모리에 세션 데이터 `저장`. 세션 데이터 `손실 방지`. `다운타임` 최소화 | `Redis용 ElastiCache Cluster`
성능 지연 이유는 `모든 페이지에서 DB 프로필 조회`가 발생. 캐시 필요하다. 해결방법은? | `ElastiCache Cluster` 생성. `cache-aside caching` 전략 사용
ElastiCache 좋은 사례 | `읽기가 많은` 어플의 워크로드 대기 시간과 처리량 개선, `컴퓨팅 집약적인` 어플의 성능 향상
`읽기 쿼리에 영향을 최소화` 하면서 트래픽 급증을 대응하는 방법은? | `ElastiCache를 사용해서 데이터 캐시`
`읽기 전용` 레코드가 대용량 트래픽 | `Redis용 ElastiCache`를 배포하고 어플리케이션에 대한 `데이터를 캐시`
`반복 읽기 요청`이 많다. 반복 읽기 쿼리를 위한 인메모리 저장소 | `ElastiCache`
온프로미스 `세션 공유`하는 어플을 마이그레이션. `내결함성`, `확장성`, `무중단` 필요. 세션 상태 저장 옵션은? | `ElastiCache에 세션 상태 저장`
ElastiCache를 사용해서 캐쉬 계층 구현. 반응형 어플리케이션이라 가격에 대한 업데이트는 `강력한 일관성` 필요 | 먼저 `백엔드`에 쓰고 캐시 `무효화`
RDS `앞에 캐싱` 계층. 서비스 실패 시 `재생성`하는데 비용이 많이 듬 | `클러스터 모드`에서 `ElastiCache Redis` 구현
인메모리 저장소를 사용해서 누적된 게임 결과를 저장하는 어플. 개별 결과는 DB 저장. AWS로 마이그레이션. 일관된 결과를 위해 `누적 게임 결과`는 어디에 저장? | `ElastiCache`
ELB로 분산처리 어플 작성. 기존 로그인 사용자들이 `다시 로그인`해야하는 문제 발생. 방지 대책은? | `ElastiCache에 세션 상태 저장`
세션 데이터를 `외부화` 하는 방법 | `ElastiCache Memcached Cluster`를 생성한 다음 어플리케이션 수준에서 `세션 처리를 구현`하여 세션 데이터 스토리지용 클러스터 활용
ALB 로그 파일에 `Client Public IP Address`를 캡쳐하기 위한 방법 | `X-Forwarded-For` Header를 로그 구성에 추가
Client IP 기준으로 처리. ALB 뒤에 배치되면서 같은 IP로 들어옴 | `X-Forwarded-For` 헤더를 검사하도록 어플 코드 변경
ELB를 사용하고 EC2에 CPU 제약이 있다. `EC2의 CPU 로드`를 늘리지 않으면서 `웹 사이트를 보호`하는 전략 | ELB에서 `SSL 구성`, SSL 종료로 `ELB 구성`
ALB에 등록된 Lambda 함수에 `다중 값 헤더`를 보내려고 한다. | ALB에서` 다중 값 헤더를 활성화` 해야함
CLI로 ALB에 Lambda 함수 등록. Client에서 ALB 통해 `Lambda 호출 실패`. 이유는? | Lambda 함수 `호출 권한이 없음`

## S3 & Encryption
문제 | 답안 
---|---
S3 반복 호출해서 `Limit Exceeded` 발생. 해결방법은? | 어플리케이션에서 `지수 백오프 구현`
S3 KMS. `AWSKMS; Status Code: 400; Error Code: ThrottlingException` 발생 | AWS Support에 `KMS 속도 제한 증가`를 요청 문의. 응용 프로그램 코드에 `지수 백오프`를 사용해서 오류 재시도 수행.
`The specified bucket does not exist` 에러 발생. 원인 분석 시작지는? | `CloudTrail`에서 `DeleteBucket` 이벤트 확인
`코드 버킷`에서 `자산 버킷`에 접근할 때 모든 사용자에게 `403 오류` | `자산 버킷`의 정책을 수정해서 `모든 보안 주체에 대한 액세스 허용`
S3에서 `400 에러` | `S3 허용 용량 초과`
S3 웹 호스팅. `CORS 에러` 발생 | `CORS 구성을 생성`해서 교차 출처 요청을 허용하는 `cdfonts 버킷` 구성
S3 웹 호스팅. `다른 bucket`의 이미지 다운로드 실패 | S3에서 `CORS 활성화`
EC2 `정적 콘텐츠` 때문에 높은 지연 시간 발생. 해결방안은?| 정적 컨텐츠를 캐시할 `CloudFront 배포`, `S3에 정적 컨텐츠 저장`
S3에서 `가장 저렴한 비용`으로 `다운로드 액세스` 안전하게 제어하는 방법은? | `S3 Presigned URL`와 함께 `CloudFront` 사용
S3 버킷에 프로필 사진 저장. 로그인할 때마다 표시. 공개적으로 `액세스 불가` | 사진의 `S3 key를 DynamoDB에 저장`. 함수를 사용해서 `Presigned URL` 생성 후 반환
보안 문서 `Private S3`에 저장. 요청 된 사용자 `15분 동안만 다운로드` 가능 | 만료 시간이 15분인 `Presigned S3 URL` 생성
S3로 사진 공유 웹사이트 운영하는데 다른 사이트에서 도용함 | S3의 `공개 읽기 액세스를 제거`하고 날짜가 있는 `Presigned URL` 사용
유료 회원에게만 콘텐츠 제공. 현재는 모든 객체 `비공개`. 유료 회원에게만 다운로드를 제공하는 방법 | 유료 가입자가 다운로드 요청을 할 때 `Pre-signed URL 생성`
S3 데이터 처리 어플리케이션. 하루 10번, 1분 소요. `배포`하고 `호출`하는 방법은? | `Lambda로 배포`하고 `S3 이벤트 알림`과 같이 호출
S3 상태 대시보드. S3 메타 데이터는 `DynamoDB에 저장`. `최적의 비용` 설계 | `Lambda`가 지원하는 `S3 이벤트 알림`을 사용해서 메타데이터를 `DynamoDB에 유지`. 대시보드는 `DynamoDB를 폴링`해서 변경 사항 반영
S3 버킷 `파일 추가`되면 DynamoDB 레코드 삽입 | DynamoDB에 삽입하는 `Lambda`를 호출하는 `S3 이벤트` 구성
이미징 서비스를 EC2로 마이그레이션. 이미지는 `Private S3 버킷`에서 가져옴. 해야할 설정은? | `S3 버킷`에 대한 `읽기 전용 권한`이 있는 `EC2 서비스 역할`을 생성하고 연결
KMS 암호화를 활성화하고 `성능이 느려짐`. 원인은? | KMS API `호출 제한`이 원하는 성능을 달성하는데 필요한 것보다 적다.
S3에 `대용량 파일 저장`하고 메타 데이터를 제공해서 사용자가 선택해서 다운로드. `메타데이터를 인덱싱` 하고 밀리초 내에 검색 기능 제공 | `DynamoDB`를 사용해서 검색 기능 제공
데이터 파일은 `로컬로 캐싱`하고 공유 이미지를 `로컬 디스크에 기록`. 마이그레이션할 때 `수평적 확장`을 허용하기 위한 것 | 공유 이미지를 제공하기 위해 `S3를 사용`하도록 어플을 수정하고 캐시 데이터를 `로컬 디스크`에 쓴다.
S3의 `us-standard` region에 객체를 저장하고 성공 확인. 객체를 읽으려고 했는데 `실패` | us-standard는 `최종 일관성을 사용`하고 bucket에서 객체를 읽기 위해선 `시간이 필요하다.`
S3의 putObject 권한이 있는 `IAM 사용자 생성`. KMS 관리형 키로 `암호화`. IAM 사용자의 키로 putObject 했을 때 `Access denied` | `kms:GenerateDataKey` 작업을 허용하도록 `IAM 사용자 정책 업데이트`
`EBS 지원 Instance`와 `Instance Store 지원`의 주요 차이 | `EBS 지원 Instance`는 `중지하고 다시 시작`할 수 있다.
S3 데이터 `액세스 제한` 기능 | S3 버킷 `정책 설정`, 버킷이나 객체에 `S3 ACL 설정`
버킷의 `로깅이 활성화` 되어 있고 개발자는 `문서를 이동`한 후 작업을 중단함. 버킷의 용량이 `50GB`. 원인은? | `동일한 버킷에 로그인`하면 로그가 기하급수적으로 증가
S3 Bucket에 정보를 반환하는 API가 필요하고 Lambda와 API G/W로 개발. `MSA 어플`이 S3 버킷에 필요한 `액세스 권한`을 갖도록 하려면? | S3 버킷에 액세스 할 수있는 권한이 있는 `IAM 역할을 생성`하고 이를 `Lambda의 실행 역할`로 할당
S3에 복사하는 Lambda 작성함. 두번째 버킷에 복사되지 않고 평균 `500초정도` 걸림. 원인은? | `Lambda 함수 최대 실행 시간`이 `300초`
S3 버킷에 액세스 하기 위한 역할을 CLI로 생성. `create-role` 명령어. `EC2 서비스`가 역할을 맡도록 하기 위한 정책 | `신뢰 정책을 추가`해야함
S3에 호스팅하고 있는 웹. `초기 파일 변경`하려면? | S3에 `새로운 html` 업로드 후 색인 문서 속성을 `새로운 html로 변경`
10명 팀원에게 `고유한 폴더 경로` S3 권한 부여. 10개의 권한을 생성하지 않고 `일반화하는 방법`은? | `IAM 정책 변수 사용`
S3 버킷은 `초당 300개 이상`의 `GET 요청` 처리 | `CloudFront`를 `S3와 통합`, S3 `키 이름 접두사`를 무작위로 지정
S3 버킷 `읽기 성능 향상` | 20개 이상의 `접두사`를 생성. 접두사로 파일을 배치. 접두사를 `병렬로 읽기`
`많은 트래픽`. `수천 개의 인스턴스`. 시간당 로그파일 `저장`. S3에서 최적의 성능을 제공하는 `명명 체계` | `HH-DD-MM-YYYY-log_instanceID`
초당 수천개의 `PUT 요청`을 커버하는 S3 경로는? | 파일 이름에 `타임스탬프를 접두사`로 붙인다. (과거엔 무작위 해쉬 문자열)
`1MB 미만` 파일 S3 버킷에 `업로드 시간` 너무 오래 걸림 | 객체 키에 `임의의 접두사` 추가
S3 보안 인프라 `관리 싫음`. `암호화 키는 제어`하고 싶음. | `SSE-KMS` 사용
`자체 마스터 키`를 사용해서 AWS 서비스 활용 | `KMS를 사용한 SSE`
S3 암호화. 마스터 키 `사용 이력 추적` 가능해야함 | `SSE-KMS`
암호화 키는 온프로미스 `데이터 센터에서 관리`. 암호화는 `S3에서` 처리. | 고객이 제공한 키로 `서버 측 암호화` 사용
S3에 저장하기 전 `암호화`. 암호화 키는 보안팀이 관리 | `KMS 암호화` 사용. 고객 마스터 키를 사용해서 `클라이언트 측 암호화` 구현
Envelope 암호화 KMS 작동 방법은? | `Master Key`는 `암복호화`용, `Text Data Key`는 `고객 데이터 암호화`용
S3 서버측 암호화를 위헤 제공하는 블록 암호 유형은? | `Advanced Encryption Standard`
객체 업로드 시 `SSE 암호화 요청 헤더` | `x-amz-server-side-encryption`
`S3 버킷` 민감 정보 저장. 모든 데이터 `암호화`. 구현하는 방법은? | `x-amz-server-side-encryption` 헤더가 포함되지 `않는` 객체 업로드 방지하는 정책 설정
`100 GB`를 KMS 암호화하는 방법은? | `Plain Text Key`와 `Data Key`의 암호화된 복사본을 반환하는 `GenerateDataKey API` 호출 생성. `Plain Text Key`를 사용해서 데이터 암호화
S3에 업로드 하기 전에 파일이 `암호화되었는지 확인` | `GenerateDateKey API`를 사용한 다음 해당 데이터 키를 사용해서 `Lambda 함수` 코드의 파일을 암호화
KMS 암호화 S3. 어플은 `고객 마스터 키(CMK)`에 액세스. 어플에 `액세스 권한` 부여하는 단계 | 어플이 있는 EC2에 연결된 `IAM EC2 역할`의 키에 대한 액세스 권한 부여. IAM 정책이 `키에 대한 액세스 권한`을 부여할 수 있도록 키 정책 작성.
데이터 저장 전 각 파일에 대한 `고유 키를 사용`해서 `암호화`해야 한다. | KMS `GenerateDataKey API`를 사용해서 `데이터 키를 가져오고` 암호화한다. 암호화 된 데이터 키와 데이터를 저장
S3 데이터 `암호화` 저장. 누가 Master Key에 `접근할 수 있는지 제어` 필요. Master Key를 쉽게 생성, 교체, 비활성이 가능한 방식 | `KMS`
모든 데이터는 전송 중에 `암호화`해서 S3에 저장. 모든 트래픽이 암호화되었는지 `확인하는 방법` | `SecureTransport`가 `false`인 트래픽을 거부하는 버킷 정책 생성
KMS를 사용해서 `클라이언트 측 암호화 수행 단계` | `GenerateDataKey API`를 호출해서 `데이터 암호화 키`의 `일반 텍스트 버전`을 검색해서 데이터를 암호화
S3 암호화. 매년 키 교체 필요 | `자동 키 교체`와 함께 `KMS` 사용
어플은 `EBS`를 사용해서 데이터 저장. `암호화` 되도록 개발하려면? | 데이터 저장에 `암호화된 EBS 볼륨`을 사용하도록 `EC2 인스턴스 집합을 구성`
`EBS`에 저장된 `데이터 보호` 방법 | EBS 볼륨 위에 `암호화된 파일 시스템`을 사용
S3에 전송 중인 데이터 `암호화` | `KMS 암호화`하고 클라이언트 측 암호화 설정, `SSL 연결`을 통한 데이터 전송
암호화 SDK를 사용할 떄 암호화에 사용된 `데이터 암호화 키를 언제 추적`합니까? | SDK는 `데이터 암호화 키`를 `암호화`하고 반환된 `암호문의 일부로 암호화하여 저장`합니다.
S3 호스팅 웹사이트. 모든 `요청`을 `추적하고 로깅` 및 보관. `비용 효율적` 솔루션 | S3 서버 `액세스 로깅 활성화`. `수명 주기`를 90일로 설정. 90일 이내에 `S3 Glacier`로 데이터 이동
AWS 계정당 사용할 수 있는 `S3 버킷 `수 | 계정당 `100`
S3 `6GB` 파일 업로드. 최대 크기 초과 에러 메시지 | `멀티파트 업로드` API 사용
S3에 `대용량 파일` 업로드 실패 | `멀티파트 업로드` API 사용해서 업로드

## StepFunction
문제 | 답안 
---|---
여러 공급업체에 요청하고 `일주일 걸리는 프로세스` | `Step Function`을 사용해서 `병행 Lambda 함수`를 실행하고 `결과 결합`
`대형 상태 머신을 호출`하는 Lambda. `쉽게 꺠지는` 레거시 사용자 코드. 리팩토링하는 서비스는? | `Step Functions`

## SAM
문제 | 답안 
---|---
SAM을 사용해서 Lambda 어플 구축. `배포하기 위한 실행 순서` | 로컬에서 `SAM Template 빌드`하고 Template를 `S3에 패키징`하고 S3에서 `Template을 배포`
Serverless Restful API를 `반복적이고 일관되게 배포` | `인라인 Swagger 정의`를 사용해서 `SAM Template 배포`, `Swagger 파일을 정의`해서 `Swagger 파일을 참조하는 SAM Template 배포`
SAM CLI 사용해서 배포할 서버리스 어플. 개발자가 `배포 전 해야할 것` | `SAM 패키지`를 사용해서 서버리스 어플리케이션을 `번들`
Serverless 어플리케이션 `자동 배포 스크립트` 개발. `SAM template를 사용하는 방법` | `aws cloudformation` 패키지를 호출해서 `배포 패키지를 생성`<br>`aws cloudformation deploy`를 호출해서 `패키지를 배포`,<br>`sam package`를 호출해서 `배포 패키지를 생성`<br>`sam deploy`를 호출해서 `패키지를 배포`
SAM CLI로 어플을 `재배포` 하기 위한 명령어 조합 | `sam init`, `sam deploy`

## SQS
문제 | 답안 
---|---
SQS 메시지 완료하는데 `5분 소요`. 메세지 성공적 처리하고 `중복 처리 최소화`하면서 메시지 제거하는 방안은? | `가시성 시간 초과`가 `증가한 메세지 검색`, 메시지 `처리`, 대기열에서 `삭제`
SQS 설명 | 메세지는 `한 번 이상` 배달되고 순서는 `불확실`
SQS 사용 어플리케이션. `분당 하나`의 메세지가 대기열에 게시되서 `비용 증가` | SQS 대기열 `폴링 시간 초과`를 늘린다.
SQS 대기열에 메시지 업데이트. 자주 변경되지 않지만 `업데이트 시간 최소화` | 20초마다 `긴 풀링을 사용`하여 메시지 검색
SQS에 유료회원, 무료회원용 업로드. EC2가 SQS를 폴링. 유료회원 먼저 처리해야 함 | `2개의 SQS 대기열`을 만들고 유료 회원을 `먼저 폴링`
SQS 대기열에 `메세지를 검색`하는 경우 다른 사용자가 메시지에 `액세스 할 수 없는 기간의 기본값` | `30초`
SQS 대기열이 기본 `VisibilityTimeout` 값으로 구성되어 있다고 가정할 때 메세지 수신 시 다른 인스턴스가 `이미 처리`되었거나 현재 `처리 중`인 메세지를 검색할 수 없도록 하는 방법 | `ChangeMessageVisibility` API를 사용해서 `VisibilityTimeout`을 늘린 다음 `DeleteMessage` API를 사용해서 메세지를 `삭제`
SQS `지연 대기열`이 수행하는 것 | 메세지는 대기열에 `처음 추가`될 때 구성 가능한 시간 동안 `숨겨진다.`
각 메세지를 처리하는데 15분 이상이 걸려서 `timeout 우려`됨 | SQS 대기열에 메세지를 추가하고 `ASG`에서 EC2 설정해서 `대기열을 폴링`하고 메세지가 도착하면 처리된다.
`1KB에서 최대 1GB` 크기의 SQS 메세지를 사용하는 어플을 설계해야한다. SQS 메시지는 어떻게 `관리`해야하나? | `S3` 및 Java용 `SQS 확장 클라이언트 라이브러리`를 사용
주문 요청이 `과부하`. `비용 효율성`을 유지하면서 `유연성`을 더하는 방법 | SQS 대기열을 구현해서 `FE와 BE 분리`, SQS 대기열에서 가져오도록 `BE 수정`
`트래픽 몰림`. BE에 일시적인 볼륨 급증을 완화하는 `탄력적인 방법`은? | 사진이 S3에 `업로드`되면 SQS 대기열에 `게시`. 대기열을 이벤트 소스로 사용해서 `Lambda 함수를 트리거`. Lambda 함수에서 사진을 `스캔하고 구문 분석`
SQS를 사용해서 `독립 발신자`의 메시지를 관리. 각 발신인은 메세지를 받은 순서대로 처리. SQS 기능은? | 고유한 `MessageGroupId`로 각 발신자를 구성
SQS 이미지 대기열 `폴링을 자주`함. CPU 주기를 소모하고 `빈 응답`이 많음. 빈 응답을 줄이는 방법은? | 이미징 큐 `ReceiveMessageWaitTimeSeconds` 속성을 20초로 설정
SQS 대기열에서 메세지를 가져옴. 모든 메세지는 `암호화`해야함 | SQS 대기열을 생성하고 `KMS에서 SSE`를 사용해서 `대기열을 암호화`
SQS 대기열에서 메세지를 `1개씩만 수신해서 처리`함. 수신 메세지 늘리는 방법 | `ReceiveMessage` API를 호출해서 `MaxNumberOfMessages`를 1보다 큰 값으로 설정
SQS `비동기 처리`했는데 일부 이벤트가 `여러번` 처리됨 | SQS 대기열을 `FIFO`로 변경

## SNS
문제 | 답안 
---|---
SNS 모바일 푸쉬. 개별 장치에 직접 알림을 보내려면 `장치 등록 식별자` 혹은 `토큰`을 SNS에 등록 | `CreatePlatformEndPint` API 함수를 호출해서 `여러 장치 토큰을 등록`한다.
`SNS 전송 유형` | `HTTP`, `SMS`
Kinesis Data Stream을 처리하는 Lambda. `처리된 데이터가 포함된 알림`을 받아야 한다. | 처리된 데이터를 `SNS 주제`에 게시
SNS에서 보내는 `구조화된 알림 메시지 형식` | `MessageId`, `unsubscriberURL`, `Subject`, `Message` 및 `기타 값`을 포함하는 `JSON`
SNS 게시 요청에 대한 `유효한 인수` | `TopicAm`, `Subject`, `Message`

## Lambda
문제 | 답안 
---|---
API G/W & Lambda로 구성. Lambda는 `Session 정보`를 어디에 저장? | `DynamoDB`에 저장
Lambda를 사용할 때 Handler 범위 밖에서 Client를 Instance화 하면 얻는 이점 | `연결 재사용` 활용
DB 연결해서 동작하는 Lambda 코드가 있다. 비용 증가 없이 `성능 개선 방법` | Lambda 함수에 `필요한 모듈만 패키징`, RDS 연결을 `핸들러 함수 외부로 이동`
`CPU 고사용` Lambda 배포. 런타임 최소화하고 싶으면? | `메모리` 할당이 최대로 설정된 함수 배포
`CPU 집약` 데이터 처리 Lambda nodejs 개발. 완료시간 단축 솔루션은? | Lambda 사용 가능 `메모리 증가`
Lambda 함수 오래 걸림. 어떤 `컴퓨팅 리소스`를 증가시켜야할까? | Lambda 함수에 할당 된 `메모리를 증가`
Lambda의 `콜드 스타트`로 8초 이상 소요. 개선방법은? | SDK for Java의 `필요한 모듈만` 포함해서 배포, 할당 `메모리 늘림`
Lambda를 더 많은 `CPU 성능`으로 테스트 | Lambda에 할당 된 `메모리 늘리기`
Lambda `다중 스레드 실행`을 활용해서 성능 개선 | Lambda 함수 `메모리 증가`
`Lambda` 함수로 `Kinesis Data Stream` 처리. Lambda 함수가 Kinesis Stream에서 `중복 레코드 생성`. Lambda 함수가 없는 Stream은 중복 없음. 원인은? | Lambda가 오류를 처리하지 않고 `Lambda 서비스가 재처리를 시도`
Lambda 함수. 두번째 계정의 있는 DynamoDB 테이블 액세스 방법은? | 테이블 액세스할 때 Lambda 에서 `새 역할을 맡음`
Lambda 함수 테스트 해도 CloudWatch Log에 `생성되지 않음`. 원인은? | Lambda 함수 `실행 역할`에 CloudWatch Log에 쓰기 권한이 없음
병렬, 순차적 실행하는 어플을 Lambda로 리팩토링. POST는 G/W에서 처리. `동일한 순서로 호출`하기 위한 Lambda 호출 방식은? | `Step Functions` 상태 머신을 사용해서 Lambda 함수 조정
Lambda가 `초당 여러번 호출`. 호출당 `50MB` 파일 다운로드 | `/tmp 경로`에 파일을 `캐시`
10MB 미만의 파일을 생성하는 Lambda 함수 설계. 임시 파일은 `여러번 액세스되고 수정`됨. 나중에 필요 없는 파일. 임시 파일이 저장되야할 장소 | `/temp 디렉터리`
Lambda는 VPC의 RDS Mysql 읽고 `다른 싸이트에서 데이터` 가져옴 | Lambda 함수 기본 구성에 `VPC Private Subnet 연결` 추가, VPC에 `NAT G/W` 추가
배포 오류일 때 Lambda `이전 버전으로 롤백` | 현재 버전을 가리키는 `별칭을 사용`하도록 어플을 변경. `새 코드 배포`. 별칭 업데이트해서 트래픽 `10%를 새로운 버전`으로 보내고 `오류 시 이전 버전으로 전송`
DynamoDB를 트리거하는 Labmda. 실행역할은 추가됨. Lambda 활성화 했지만 `트리거되지 않음`. | Lambda 함수에 대한 `이벤트 소스 매핑 구성`
Lambda는 정기적으로 교체되는 사용자의 이름과 암호를 사용해서 외부 사이트에 액세스. 안전하게 `보관하는 방법`은 ? | `System Manager Parameter Store`, `KMS`
Lambda 코드를 `S3`에 새로 올렸지만 `이전 버전`이 실행됨 | `업데이트 기능 코드 API를 호출`
하나의 `시작 지점`을 하나의 `계정`으로 `중앙 집중화`하고 이벤트가 발생하면 `모든 Lambda 함수 호출` | 모든 다중 계정 Lambda를 `SNS 주제에 구독`하고 SNS 주제에 대한 페이로드를 사용해서 `SNS Publish API`를 호출
Lambda 함수 `오류 기록` | Lambda 함수 `코드의 로깅 문`을 통해 오류 보고
Lambda 함수를 사용해서 S3 이미지 처리. `썸네일을 저장`해야하는 새로운 기능 필요. 기존 시간에 영향 미치지 않음 | `S3 이벤트 알림`을 생성하고 `새 Lmabda` 만들어서 처리
Lambda 함수 코드의 `로그 검사`는 어디에 저장 | `CloudWatch`
많은 파일을 처리. 파일당 4분 소요. `모든 파일 처리 방법` | `비동기식` Event Lambda 호출을 수행하고 파일을 `병렬 처리`
S3 이미지 저장할 때 `이벤트 알림`으로 Lambda가 이미지 `크기 조정`. Lambda `추가 트래픽 처리 방안` | Lambda는 요청을 `동시에 실행`하도록 확장됨
DynamoDB 테이블 `항목 수명 주기 활동`을 기반으로 Lambda 트리거 | `DynamoDB Stream`을 활성화하고 Stream에서 `동기적`으로 Lambda 함수 `트리거`
10분마다 Lambda 함수 호출. `트리거하는 자동화된 서비리스 방법` | 정기적인 일정에 따라 Lambda 함수를 호출하는 `CloudWatch Event 규칙 생성`
이벤트와 Lambda 간의 `매핑을 달성`하는 방법 | `다른 Lambda 트리거` 사용
stg, test, prod에 Lambda 배포. 각 환경에 고유한 리소스 집합이 있는데 `현재 환경에 리소스 사용`하는 방법 | Lambda 함수에 `환경 변수`를 사용
Lambda 평균 실행 시간 `100초`, `초당 50개` 요청. 배포 전 해야할 작업 | `동시 실행 제한`을 늘리려면 `AWS Support`에 문의. (기본값은 1000 이기 떄문)
Lambda `코드를 수정하지 않고` DB 연결 문자열을 바꿀 수 있는 방법 | 연결 문자열을 `Secrets Manager`에 암호로 저장
Lambda `로컬 테스트 성공`. `콘솔 테스트 실패`. `Unable to import module` | Lambda 콘솔에서 `LB_LIBRARY_PATH` 환경을 생성하고 시스템 라이브러리 경로 값 지정
Lambda `직접 배포 크기 초과` | 배포 패키지를 `S3에 업로드`하고 `--code CLI` 파라미터를 사용해서 S3를 참조
Lambda 발생하는 주요 이벤트를 `기록`하고 이벤트 특정 `함수 호출`과 `연결`하는 `고유 식별자`를 포함해야함. | Lambda `Context 객체`에서 `요청 식별자`를 얻고 `콘솔에 로그를 기록`하도록 어플을 설계
Lambda의 `기본 설정`이고 `시간 초과 예외`가 발생하면 S3 이벤트는? | `2번 재시도 후 폐기`
Lambda 함수 로그에서 `동일한 요청 ID`를 가진 `중복 항목`이 존재 | Lambda `함수 실패 후 재시도`
Lambda 함수는 2개의 DynamoDB에 액세스해야함. `병목 현상`을 식별해서 성능을 개선하려고 함. DynamoDB API `호출 타이밍 검사 방법` | DynamoDB를 Lambda 함수에 `이벤트 소스로 추가`하고 `CloudWatch 지표`로 성능 확인
Lambda 함수 `디버깅` | `실행 역할`에 `CloudWatch Logs`에 쓰기 권한이 있는지 확인, `CloudWatch 지표를 사용`해서 알림을 생성
로컬에서 Lambda 2번 호출 후 실패. `로깅 방법` | 호출 실패를 조사하도록 `CloudTrail` 로깅 구성
Lambda는 `파일을 추가`하기 위해 `CodeCommit Repository`에 체크인 해야함 | SDK로 `CodeCommit 클라이언트`를 `Instance화`하고 `put_file` method를 호출해서 `파일을 저장소에 추가`
`Query 문자열 파라미터`를 Lambda 함수에 대한 `인수`로 변환하는 방법 | `Mapping Template 생성`
Lambda에서 Downstream으로 데이터를 보내기 전에 `암호화`해야 한다. 암호화 수행하기 위한 API? | `KMS GenerateDataKey API`
Lambda `사용자 지정 라이브러리` 사용 방법 | 라이브러리를 로컬에 설치하고 `ZIP 파일을 업로드`
Lambda `외부 라이브러리` 사용 | 코드와 종속 라이브러리를 `zip해서 올린다`
nodejs 웹 사이트 호스팅. `코드 변경이 없는 솔루션` | `Lambda`

## Kinesis Data Stream
문제 | 답안 
---|---
Kinesis Data Stream, `ProvisionedThroyghputExceededException` 발생. 대처방안은? | Data Stream `샤드 수 증가`, Get/PutRecords 호출에 `지수 백오프 구현`
Kinesis에서 `ProvisionedThrouputExceededException` 발생. | `지수 백오프`로 재시도 구현, 요청의 `빈도/크기 줄이기`
주식 어플. Kinesis로 데이터 수집. `수신 데이터 따라갈 수 없음` | `UpdateShardCount`를 사용해서 스트림의 `샤드 수를 증가`
`수백만 개`의 이벤트 실시간 처리. 동시에 `비용 효율적`으로 처리 | `Kinesis Stream` 사용
매시간 `대용량 데이터 수집`. 메세지는 `실시간으로 전달`되어야 함 | `Kinesis Client Library`와 함께 `Kinesis Data Stream`을 사용해서 메세지 수집 및 전송
`Kinesis Data Stream`의 샤드 수 `4개에서 6개` 됨. 데이터 처리를 위한 `EC2 인스턴스 최대 수`는? | `6` (샤드 수와 EC2 인스턴스 수는 같다.)
Kinesis Stream 내 데이터를 저장 `암호화` 방법 | Kinesis Stream `서버 측 암호화 활성화`
Kinesis Stream에서 `레코드를 가져오기 위해 IAM 액세스` 확인하는 방법 | `--dry-run` 인수를 사용해서 `get 작업`, `IAM 정책 시뮬레이터`로 IAM 역할 정책 검증
Kinesis `2500개 레코드` 수집을 위한 `4개`의 샤드. Lambda 함수. `처리하는 순서` | Lambda는 `FIFO` 방법에 따라 샤드에 배치된 `정확한 순서로 각 레코드를 수신`. 샤드 간에 `순서는 보장하지 않음`
Lambda와 Kinesis Data Stream. `반복자 수명 지표가 증가`하고 `실행 시간`이 지속적으로 느림 | Kinesis의 `샤드 수 줄이기`. Lambda `시간 초과 늘리기`.


## RDS
문제 | 답안 
---|---
Mysql RDS. DB `자격 증명`이 안전하게 저장되고 액세스 되는지 확인 방법 | 자격 증명을 `Secrets Manager`에 저장하고 `자동 암호 교체를 활성화`
어플의 기록을 조회하는 RDS. 기록 데이터 `업데이트 많음`. `읽기 성능 저하`됨 | RDS `읽기 전용 복제본`을 만들고 모든 읽기 트래픽을 `복제본으로 전송`
온프로미스 DB를 RDS Mysql로 마이그레이션. `읽기가 많은` 워크로드. `최적의 읽기 성능` | 읽기 쿼리에 `RDS 읽기 전용 복제본`을 사용하기 위한 연결 문자열 추가
Aurora MySQL DB가 될 Lambda 함수로 코드를 마이그레이션. `DB 기능을 인증`하는 방법 | `Secret Manager`에 DB `자격 증명을 저장`하고 필요에 따라 Manager가 `자격 증명 교체`를 처리

## X-Ray
문제 | 답안 
---|---
API G/W를 사용해서 액세스 하는 온프로미스 Linux 환경. API 테스트 단계에서 `X-Ray 추적 활성화`. 온프로미스 서버에서 X-Ray 추적 활성화 방안 | 온프로미스 서버에 `X-Ray 데몬`을 설치하고 실행해서 데이터를 캡처하고 X-Ray 서비스에 전달
X-Ray로 `Lambda 기반 어플을 추적` | `IAM 실행 역할을 사용`하여 Lambda 함수 `권한을 부여하고 추적을 활성화`
Lambda로 작성된 분산 어플리케이션 `성능 문제` 근본 원인을 식별하는 방법 | `X-Ray`를 사용해서 `Segment 및 Error 검사`
X-Ray `사용 시작 작업` | 어플이 있는 서버에 `X-Ray 에이전트 설치`, SDK를 사용해서 `어플리케이션 추적 코드 계측`
X-Ray로 `EC2`에 있는 어플 모니터링하기 위한 절차 | `X-Ray 데몬을 설치`하고 `어플리케이션 코드를 계측`
ECS 환경에서 `X-Ray 활성화 방안` | `X-Ray 데몬을 실행`하는 Docker 이미지 생성, X-Ray용 `어플리케이션 코드에 계측`을 추가, 작업에 대한 `IAM 역할을 구성`하고 사용
X-Ray 구성을 하고 어플리케이션에서 X-Ray로 데이터를 보내는걸 확인. `EC2에 배포하니까 추적이 안됨`. 원인은? | `X-Ray 데몬`이 EC2에 설치되어 있지 않음, `인스턴스 역할`에 `xray:PutTraceSegments`, `xray:PutTelemetryRecords` 권한이 없음.
Java 기반 Lambda 함수. 성능 `병목 현상`을 격리하기 위한 조치 | `X-Ray API`를 사용해서 `코드 내 전략적 위치에 X-Ray 추적 데이터 기록`. `X-Ray Console`을 사용해서 결과 데이터 분석
여러 `추적 데이터를 수집`하고 `시각화`하기 위한 솔루션 | `X-Ray`
MSA 아키텍처에서 `중앙 집중식 계정`에서 어플의 문제를 `분석하고 디버깅`하기 원한다. | 역할 수임이 있는 `X-ray Agent를 사용`해서 `중앙 집중식 계정`에 데이터를 게시
X-Ray에 정보를 제공하도록 코드 변경. 데이터가 많이 생성되서 `필터링하기 위한 인덱싱` 구현 필요 | `세그먼트 문서` 및 `코드에 주석 추가`

## 기본개념
문제 | 답안 
---|---
`LAMP`를 실행할 수 있는 AWS 서비스는? | `EC2`, `Aurora`
`Restful API` 작성. endpoint 요청에 대한 충족 사항 | `API G/W & Lambda`, `S3 & CloudFront`
AWS SDK `기본 리전` | `us-east-1`
EC2 인스턴스의 `IPv4` 주소 찾기 | `169.254.169.254/lastest/metadata/` 를 검색
`단일 인터페이스` 필요 | `API Gateway`
AWS에서 `추가 비용 없이` 포함되는 서비스 | `Auto Scaling`, `CloudFormation`
`AMI(Amazon Machine Image) 목록을 검색`할 때 사용하는 EC2 API | `DescribeImages`
CLI 명령 후 `에러 메세지가 암호화` 되어 있다. | `STS decode-authorization-message` API로 디코딩
`SWF`(Simple Workflow Service)의 설명 | 작업은 `한 번 할당`되고 `절대 중복되지 않는다`. workflow 실행은 `최대 1년동안 지속`. `결정자`와 `작업자`를 사용해서 작업을 완료
CLI에서 `Resource List` 명령 때 `실행시간 초과` | `Pagenation` 사용
`ECS` 컨테이너 시작할 때 `PortMapping`은 어디에서 정의 | `Task definition`
AWS에서 보안에 대한 `고객의 책임` | `IAM 자격 증명의 수명 주기 관리`, `보안 그룹 및 ACL 설정`, `EBS 볼륨의 암호화`, `EC2 운영체제의 패치 관리`
`모놀리식` 아키텍처를 `마이크로서비스` 아키텍처로 변경하고 성능에 영향을 주지 않으면서 `비동기식 통신`할 수 있도록 해야 한다. `비동기식 메시지 전달`이 가능한 서비스는? | `SQS`, `SNS`
`높은 처리량`으로 `데이터 수집`하고 `S3 버킷에 저장`해야함. 적합한 서비스는? | `Kinesis Firehouse`
`Key-Value` 저장소는? | `ElastiCache`, `DynamoDB`, `S3`
SDK가 있는 언어 | `Java`, `C#`, `Ruby`, `Python`, `JavaScript`, `PHP`, and `Objective C (iOS)`
EC2, S3. `트래픽 증가`. `성능 저하` | `CloudFront`를 사용해서 S3에 저장된 이미지 콘텐츠 제공
HTML, Image, Video, Javascript, Serverless | `S3`, `CloudFront`
`CloudFront` 사용해서 웹 어플 구성. 종단 간 모든 `트래픽을 암호화`해야 함 | Origin Protocol Policy를 `HTTPS Only`로 설정, HTTPS 전용 또는 HTTP를 `HTTPS로 Redirection` 설정
`Tomcat 서버`에 빠르게 배포 | `Elastic Beanstalk`
고객은 새로운 `Restful API`를 요청함. 가능한 구성 요소는? | `ELB + EC2`, `API G/W + Lambda`
AWS `Infra를 Code로 관리`하고 `배포`할 수 있으며 `이전 버전`으로 돌릴 수 있어야 한다. | `CloudFormation`, `CodeCommit` 사용
React, 자신이 소유한 파일 저장, 검색 허용. facebook 사용. 개발 및 배포 `가속화`하는 CLI | `Amplify CLI`

## AWS 관련
문제 | 답안 
---|---
EC2의 Public/Private `IP 확인 방법`은? | Local Instance `Metadata Query`
`NAT` 장치가 `Private Subnet`에 바인딩 트래픽 대상이 되도록 라우팅 테이블 수정. Private Subnet에서 인터넷으로 `아웃바운드 실패`. 해결 방법 | NAT 인스턴스에서 `Source/Destination Check` 속성 비활성화
`민감 데이터 보호`. `액세스 추적` 필요. | EC2 `System Manager Parameter Store`에서 IAM으로 Application Access 권한 부여
각 EC2에 어플, DB 동작. 어플이 DB에 접근하기 위한 비밀키는 변경됨 | `SecureString` 데이터 유형과 함께 `System Manager Parameter Store`를 사용해서 비밀키를 저장
BGP 기반 VPN으로 EC2 연결. Subnet A는 액세스, B는 액세스 불가. 트래픽이 B에 도달했는지 확인하려면? | `VPC 흐름 로그 확인`
`Access key`를 AWS에서 관리하는 방법 | 계정 루트 사용자에 대한 모든 Access key를 삭제, `Access key 대신 IAM 역할 사용`
EC2 어플. AWS API 호출하도록 구성 | `필요한 권한`이 있는 EC2 역할을 지정
특정 IAM 사용자 자격 증명 사용하도록 CLI 구성. 명령어 오류 반환됨. `aws dynamodb get-item --table-name ...` | IAM 사용자는 테이블에 대한 `읽기 액세스 권한`이 있는 정책이 필요
ECS-Docker. `15초 동안 사용자 로드를 기반`으로 확장 | `사용자 활동 데이터`에 고해상도 지정. `CludWatch` 지표 `5초`마다 데이터 게시
실수로 `EC2 인스턴스 종료`. 방지하는 대책 | `Resource Access Manager에서 EC2 종료 보호 활성화`
CLI에서 `aws` 명령을 찾을 수 없음 | aws 실행 파일이 `환경 변수`에 없음
AWS 계정을 감사하는 어플리케이션. A 계정에서 실행되서 계정 B,C 서비스에 액세스 필요. 어플리케이션이 각 계정 서비스를 호출하는 방법 | 각 감사 계정에서 `교차 계정 역할`을 구성하고 해당 역할을 맡는 `계정 A에 코드 작성`
EC2에 있는 어플이 RDS로 연결. 사용자 `ID/PWD를 코드에 저장`히기 싫음. `자격 증명 자동 교체` 필요 | `Secrets Manager`를 사용해서 자격 증명을 저장하고 검색
어플에서 사용하는 IAM 자격 증명에는 API 호출에 대한 `다단계 인증`이 필요하다. 다단계 인증 보호 API에 액세스하는데 사용하는 방법 | `GetSessionToken`
Admin 그룹의 한 사용자에게만 `EC2 리소스 삭제 권한`이 있는지 확인하려고 한다. | `인라인 정책 사용`
컨테이너 어플을 `ECS Fargate에 배포`. 세션 데이터로 활동 추적 | NLB에 `Sticky Session 활성화`하고 컨테이너에서 `세션 데이터 관리`
`ECS 기반 Fargate에 배포`. 어플을 초기화하기 위해 컨테이너에 전달해야 하는 `환경변수`가 있다. 어떻게 전달할건가? | `작업 정의 내 환경 매개변수` 아래에 환경 변수를 포함하는 배열을 정의
`MongoDB 마이그레이션` 호스팅을 위한 솔루션 | MongoDB 호환 모드에서 `AWS DocumentDB`를 배포
`CloudFront`로 글로벌 사용자 기반 어플을 새로 배포. 고객들이 새로 배포된걸 볼 수 없음 | `Edge Cache`에서 모든 응용 프로그램 개체를 `무효화`
`ECS 환경`에서 컨테이너 워크로드를 실행. 로그 및 메트릭 수집을 위해 다른 `컨테이너와 공유` 필요 | `하나의 작업 정의`를 만들어서 정의에서 두 컨테이너 `모두 지정`하고 컨테이너 간 `공유 볼륨`을 탑재
ASG 이벤트에 대한 최상의 메트릭은 `동시 사용자 수` 라고 결정했다. 동시 사용자를 기반으로 `자동 크기 조정`을 위해 무엇을 활용해야하나? | 동시 사용자를 위한 사용자 지정 `CloudWatch 지표`
개발자는 2개의 리소스에 `임시 액세스` | `교차 계정 액세스 역할`을 만들고 `sts:AssumeRole` API를 사용해서 `단기 자격 증명`을 얻는다
DynamoDB, Docker 기반 어플. 로컬에서 IAM 액세스 키로 테스트. ECS에 배포했을 때 어떻게 인증? | 어플이 사용할 `ECS 작업 IAM 역할 구성`
EC2 어플리케이션에서 사용하는 IAM 자격 증명이 오용되거나 손상되었는지 확인. `자격 증명을 안전하게 유지`하기 위해 사용해야 하는 것 | `인스턴스 프로필 자격 증명`
IAM 역할은 S3 API 작업에 대한 액세스를 거부. EC2 인스턴스 자격 증명 파일은 `전체 관리 액세스를 허용`하는 IAM 액세스 키와 보안 액세스 키를 지정 | EC2 인스턴스는 `모든` S3 버킷에서 모든 작업을 수행 가능
API 호출에서 일부 액세스 거부 뜸 | `필요한 권한을 추가`해서 연결된 IAM 업데이트
EC2 인스턴스는 AMI에서 시작된다. `지정된 공개 AMI`이 수행할 수 있는 것 | AMI가 저장된 `동일한 AWS Region`에서 `EC2 인스턴스를 시작`하는데 사용
SLA(서비스 수준 계약)을 약속하고 각 SLA를 준수하기 위해 해야할 것 | 각 사용자에 대한 `사용 계획`을 만들고 API에 액세스 할 수 있는 `API 키`를 요청
개발자 로컬 `CLI IAM 권한` 사용 방법 | `aws configure` CLI 명령을 실행하고 `IAM 액세스 키`와 `보안 액세스 키` 제공
IAM 정책 평가 로직에 대한 설명 | 기본적으로 `모든 요청은 거부`됨, `명시적 허용은 기본 거부를 재정의함`.
각 개발자는 로컬 개발 | 각 개발자에 대한 `IAM 사용자`를 만들고 고유한 액세스 키를 제공
Example Corp AWS 계정에 액세스를 허용하는 안전한 방법 | Example Corp 계정에서 `사용자 생성 및 액세스 키` 제공
주말 동안 트래픽 급증, 주중에는 예측 가능한 급증. 항상 조절 오류를 방지하려면? | 일주일 내내 `ASG`로 `Provisioning 된 용량 사용`
EC2 안에 어플리케이션이 S3 버킷에 쓰기 기능 추가 | `EC2 인스턴스 프로파일` 역할에 IAM 정책
EC2에 있는 어플리케이션이 AWS 서비스에 액세스하고 API 호출 | `EC2 프로파일 사용`
EC2 인스턴스를 시작하거나 종료할 때 `BotoServerError: 503 Service Unavailable` 에러 수신 | EC2에 대한 API 요청 수 최적하를 위한 `지수 백오프` 구현
실시간 처리 | `Event Driven`

## 아키텍처 구성
문제 | 답안 
---|---
EC2 10개로 구성. 하나의 Metric 표현 필요. CloudWatch를 사용해서 해결하는 방법은? | 각 응용 프로그램에 대한 `고유한 메트릭 이름을 사용`하고 `사용자 지정 네임스페이스를 생성`
EC2 4개로 구성. 각 고유한 권한. 메모리 예약 기반 컨테이너. 구성방안은? | 각 ECS에 권한을 포함한 4개의 `고유한 IAM 생성` 후 `IAM 역할 참조` 하도록 ECS 작업 정의 구성
30분 걸리는 사기 탐지 솔루션을 어플리케이션에 추가하는 방법은? | 주문을 `SQS 대기열에 추가`하고 `Auto Scaling 그룹 구성`하고 대기열에서 주문을 가져오기 위해 사기 탐지 솔루션이 설치된 `AZ에 EC2 집합 설정`
최종 사용자에게 보낼 수 있는 양 제한하고 경영진은 더 큰 패키지 구매 옵션 제공 | `기본 사용 계획 설정`. `각 단계를 설정`. 더 큰 패키지를 선택하면 적절한 값으로 `사용자 지정 계획을 만들고 사용자와 연결`
VPC 내 배포되는 웹 어플리케이션. 온프로미스 LDAP 서버에 인증을 해야함. 인증 후 로그인한 사용자는 관련된 S3 Key Space에만 액세스할 수 있도록 해야한다. | LDAP에 대한 인증 후 사용자와 연결된 IAM 역할의 이름을 검색하고 IAM 보안 토큰 서비스를 호출해서 해당 IAM 역할을 수임한다. 어플은 임시 자격 증명을 사용해서 S3에 액세스, LDAP에 대한 인증 후 IAM 보안 토큰 서비스를 호출해서 IAM 연동 사용자 자격 증명을 가져오는 자격 증명 브로커를 개발하고 어플은 자격 증명 브로커를 호출해서 S3에 대한 액세스 권한이 있는 IAM 연동 사용자 자격 증명을 가여온다.
온프로미스를 마이그레이션. 사용자가 업로드한거 서버의 로컬 경로에 저장하고 ASG의 모든 인스턴스가 바로 사용 가능해야함 | S3를 사용하고 모든 업로드를 S3에 저장하도록 어플 설계

## 오답노트
14, 18, 21, 25, 34, 44, 47 51, 62, 69, 75, 79 102, 109, 113, 115, 118, 119

문제 | 답안
---|---
사기 탐지 솔루션은 주문을 확인하는데 10~30분 소요. 주문 처리 파이프라인에 사기 탐지 솔루션을 넣는 방법은? | 모든 주문을 SQS에 넣는다. 대기열 지표를 규모 단위로 ASG 그룹을 구성하고 SQS 대기열에서 주문를 가져오기 위해 사기 탐지 솔루이 설치된 여러 AZ에 걸쳐 동적으로 크기가 조정된 EC2 집합을 시작한다. 통과 또는 실패 상태를 업데이트 한다.
봉투 암호화 동작 방식은? | Customer Master Key는 `Data Key를 암/복호화`하는데 사용.<br>Plain Text Key는 `Customer Data를 암호화`하는데 사용.
Elastic Beanstalk 배포할 수 있는 AWS 서비스는? | ASG, ELB, RDS
CloudFormation Template을 사용해서 Lambda 함수 배포 절차는? | `AWS::Lambda::Function` 리소스를 생성한 다음 CloudFormation Template 내부에 `직접 코드를 작성`.<br>함수 코드가 포함된 `.ZIP 파일`을 `S3`에 업로드한 다음 Template의 `AWS::Lambda::Function` 리소스에 파일에 대한 참조를 추가
Lambda가 지원하는 Alexa 스킬을 수정해서 두번째 계정의 DynamoDB 테이블에 액세스해야 한다. 테이블에 액세스할 수 있는 두번째 계정의 역할이 생성됐다. 액세스 하는 방법은? Lambda 함수에서 `새 역할`을 지정합니다.
SQS의 설명은? | 메세지는 `한 번 이상` 배달되며 배달 순서는 `불확실`하다.
API G/W를 사용해서 `동일한 순서로 Lambda를 호출`하는 방법은? | `Step Function 상태 머신`을 사용해서 Lambda 함수 조정
`ElastiCache`가 좋은 사례는? | `읽기`가 많은 어플리케이션의 `대기 시간과 처리량 개선`, `컴퓨팅 집약적인` 어플리케이션의 성능 향상
CloudFormation Template에서 `S3 Bucket을 참조`하는 효율적인 방법은? | `원본` Template의 `출력` Section에서 `Export` 선언을 추가하고 `다른` Template에서 `ImportValue`를 사용한다.
EC2에서 실행되는 어플리케이션을 구성할 때 `API를 안전하게 호출`하도록 EC2를 구성하는 방법은? | 필요한 권한이 있는 `역할을 지정`
Elastic Beanstalk 배포 된 어플리케이션이 새 버전과 이전 버전이 호환되지 않는다. 새 버전 배포 실패 시 `롤백`이 되어야 하고 모든 인스턴스에서 새 버전으로 `전체 전환` 되어야 합니다. 최소한의 `가동 중지 시간`으로 수행하는 방법은? | `새로운` Elastic Beanstalk 환경에 새 버전을 배포하고 `환경 URL을 교체`
`X-Ray`로 `Lambda` 기반 어플리케이션을 추적하는 방법은? | IAM `실행 역할`을 사용해서 Lambda 함수 권한을 부여하고 추적을 활성화
Serverless Resource를 표현하기 위한 `단순화된 구문을 정의`하기 위한 도구는? | `Serverless Application Model`
SQS 대기열이 자주 변경되지 않지만 `메세지가 도착`하면 대시보드가 `빠르게 반영`해야 합니다. 짧은 지연 시간 제공하는 기술은? | 20초마다 `긴 풀링`을 사용해서 대기열 메세지를 검색
Lambda 함수에서 `ClientError: An error occurred (InvalidParameterValueException)`. `압축을 푼 크기가 초과`했습니다. 해결하기 위한 방법은? | 함수를 여러 개의 `더 작은 Lambda 함수`로 나눈다.
Elastic Beanstalk의 EC2 인스턴스에 `특정 명령을 실행`하려고 한다. 이를 위한 기능은? | `.ebextensions`

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
  
  <img width="600" height="484" alt="스크린샷 2023-01-27 10 07 48" src="https://user-images.githubusercontent.com/21374902/215044965-02c2bd74-946e-444a-a375-b77c6d460625.png">
  
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
macOS | Intellij Ultimate | Typescript

### (2) Java와 Nodejs 중 어떤 언어로 개발해야할까?
- `ORM을 사용한 Java`는 GET은 제일 느리고 POST는 가장 빠르다. 
- `ORM을 사용하지 않은 Java`는 GET이 가장 빠르고 POST는 가장 느리다. 
- `Nodejs`는 그 중간값을 가진다.

  <img width="631" alt="image" src="https://user-images.githubusercontent.com/21374902/228151976-ccb406ec-0861-4f82-85d9-baedeee6dde5.png">
  
  - Reference : https://varteq.com/java-vs-nodejs-on-aws-lambda-benchmark-survey/
- Java는 `Cold Start` 문제를 갖고 있다. `Cold Start`란 함수가 처음 실행될 때 함수 코드와 런타임 환경을 초기화하는 과정이다. 코드가 배포되면 새로운 컨테이너가 생성되고 최초 실행 시 초기화 과정인 `Cold Start` 시간이 필요한 것이다. 이후에 호출될 때는 초기화 시간이 필요하지 않지만 Lambda는 일정 시간 동안 호출되지 않으면 삭제되기 때문에 언제 다시 `Cold Start` 시간이 필요할지 모른다. 이를 해결하기 위해서 Lambda에 할당하는 메모리 크기를 늘려서 초기화 시간을 단축하거나 Previsioned Concurrency를 사용하는 방법이 있다.
  - `Cold Start` 문제를 해결하기 위해 AWS는 `SnapStart`라는 기능을 개발했다.
  - Lambda의 수명주기는 아래와 같다.
    - init
      - 함수의 runtime을 bootstrap하고 정적 코드를 실행하는 과정
      - 대부분의 경우 밀리초 안으로 완료되지만 SpringBoot, Quarkus, Micronual과 같은 Framework와 함께 Java Runtime 중 하나를 사용하는 Lambda 함수의 init 시간은 10초 이상이 소요될 수 있다.
      - 종속성 삽입, 함수 코드 컴파일, Class Path 구성 요소 스캔 등에 시간이 소요되기 떄문이다.
      - 다른 이유로는 정적 코드에서 일부 기계 학습 모델을 다운로드하거나 일부 참조 데이터를 미리 계산하거나 다른 AWS 서비스에 대한 네트워크 연결을 설정할 때에도 시간이 오래 소요될 수 있다. 
    - invoke
    - shutdown
  - SnapStart 동작 방식
    - 특정 Lambda에서 SnapStart를 활성화하고 새 버전을 게시하면 최적화된 프로세스가 실행된다.
    - init 단계를 수행하고 메모리 및 디스크 상태의 변경 불가능한 암호화된 스냅샷을 가져와서 다시 사용할 수 있도록 캐싱한다.
    - 이후에 함수가 호출되면 상태에 따라 캐시에서 청크 단위로 검색되어 실행 환경을 채우는데 사용된다.
    - 새로운 환경을 만드는데 init 단계가 필요하지 않게 되고 최적화를 통해 호출 시간이 단축된다.
  - SnapStart 필요 조건
    - Java 11 이상
    - 미국 동부(오하이오, 버지니아 북부), 미국 서부(오레곤), 아시아 태평양(싱가포르, 시드니, 도쿄) 및 유럽(프랑크푸르트, 아일랜드, 스톡홀름) 리전에서 사용 가능
  - SnapStart 장점

    |장점|설명|
    |---|---|
    |스냅 복원력 강화|Lambda SnapStart는 초기화된 단일 스냅샷을 재사용하여 여러 실행 환경을 재개함으로써 애플리케이션 속도를 높입니다. 이는 코드에 몇 가지 흥미로운 영향을 미칩니다.|
    |고유성|napStart를 사용하는 경우 고유성을 유지하려면 초기화 중에 생성되었던 모든 고유 컨텐츠를 초기화 후에 생성해야 합니다. 사용자(또는 사용자가 참조하는 라이브러리)가 유사 난수 생성기를 사용하는 경우 Init 단계에서 얻은 시드를 기반으로 해서는 안됩니다. SnapStart와 함께 사용할 때 임의성을 보장하기 위해 OpenSSL의 Rand_bytes를 업데이트했고java.security.SecureRandom이 이미 스냅 복원력이 있음을 확인했습니다. Amazon Linux의 /dev/random 및 /dev/urandom도 스냅 복원력이 뛰어납니다.|
    |네트워크 연결|코드가 Init 단계에서 네트워크 서비스에 대한 장기 연결을 만들어 Invoke 단계에서 사용하는 경우 필요 시 연결을 다시 설정할 수 있는지 확인하세요. 이를 위해 AWS SDK가 이미 업데이트되었습니다.|
    |임시 데이터|이는 사실상 위 항목의 보다 일반적인 형태입니다. Init 단계에서 코드가 참조 정보를 다운로드하거나 계산하는 경우 캐싱 기간 동안 참조 정보가 오래되지 않았는지 빠르게 확인하는 것이 좋습니다.|
    |캐싱|캐싱된 스냅샷은 14일 동안 사용하지 않으면 제거됩니다. 업데이트되거나 패치된 런타임에 따라 스냅샷이 달라지는 경우 Lambda는 자동으로 캐시를 새로 고칩니다.|
    |요금|Lambda SnapStart를 사용하는 데는 추가 요금이 부과되지 않습니다.|
    |기능 호환성|더 큰 임시 스토리지, Elastic File Systems, Provisioned Concurrency 또는 Graviton2에서는 Lambda SnapStart를 사용할 수 없습니다. 일반적으로 범용 Lambda 함수에는 SnapStart를 사용하고 지연 시간에 매우 민감한 함수의 하위 집합에는 Provisioned Concurrency를 사용하는 것이 좋습니다.|
    |Firecracker|이 기능은 Firecracker 스냅샷 생성을 사용합니다.|

  - Reference : https://aws.amazon.com/ko/blogs/korea/new-accelerate-your-lambda-functions-with-lambda-snapstart/

### (3) 공통 : AWS SAM CLI 설치
- `brew tap aws/tap`
- `brew install aws-sam-cli`
- `sam --version`
- `brew update aws-sam-cli`

<details>
<summary><font size="5"><b> 1️⃣ Java 11 </b></font></summary>

### (1) Intellij에 AWS Toolkit 설치
- Preferences -> Plugins -> Martketplace -> AWS Toolkit 설치

### (2) Serverless 프로젝트 생성
- File -> New -> Project -> AWS -> AWS Serverless Application
- `Validation of sam failed: Not installed.` 에러가 뜰 경우, SAM CLI executable에 `which sam` 해서 나온 경로 입력

### (3) Build & Test with SAM
- ⭐️ Docker를 Rancher로 돌리는 경우엔 sam 사용이 불가합니다. Docker Desktop을 설치하거나 AWS Lambda Console에 올려서 테스트 해야 합니다.
- Intellij
    - Run Configuration에서 input, aws profile 등 설정하고 실행
- Command
    - SAM 구동 후 테스트 : `sam local start-api` -> 원하는 URL 호출
    - 직접 호출하는 방법 : `sam local invoke "HelloWorldFunction" -e events/event.json`

### (4) 함수 개발
- #### ⭐ 중요 포인트
    - ##### 함수의 응답은 String 타입이 아닌 POJO 형식의 Class를 반환해야 한다. 응답을 String 타입의 JSON 형태로 하면 __"{\\"name\\":\\"beaver\\"}"__ 처럼 큰 따움표가 붙는다. POJO 형식의 Class를 반환하면 Lambda에서 알아서 Serialize를 해준다.
    - ##### 함수는 반드시 RequestHandler Class를 상속 받아야 하고 Input/Output Parameter의 형식을 지정할 수 있다.
    - ##### Input으로 사용하는 event.json과 같은 형식은 AWS에 Sample이 많이 있으므로 참조해서 사용한다.

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

### (5) Lmabda 테스트
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

</details>

<details>
<summary><font size="5"><b> 2️⃣ Typescript </b></font></summary>

### (1) 원하는 경로에 SAM 프로젝트 설치
- `sam init`
- `1 - AWS Quick Start Templates`
- `1 - Hello World Example`
- Use the most popular runtime and package type? (Python and zip) [y/N]: `N`
- `14 - nodejs14.x`
- `1 - Zip`
- `2 - Hello World Example TypeScript`
- Would you like to enable X-Ray tracing on the function(s) in your application?  [y/N]: `N`
- Would you like to enable monitoring using CloudWatch Application Insights? [y/N]: `N`
- Project name [sam-app]: `demo`

### (2) Architectures 변경
- Mac을 사용하고 있다면 `template.yaml`을 열어서 Architectures를 `arm64`으로 변경

### (3) 필요한 Module 및 Build
- `demo/hello-world$ yarn install`
- `demo$ sam build`

### (4) 함수 테스트
- `demo$ sam local invoke HelloWorldFunction --event events/event.json`

</details>


# 2. Lambda with DynamoDB 
<details>
<summary><font size="5"><b> 2️⃣ Java 11 </b></font></summary>

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

</details>

<details>
<summary><font size="5"><b> 2️⃣ Typescript </b></font></summary>
</details>


# 3. Reference
- https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/examples-dynamodb-items.html
- https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStarted.html
- https://docs.aws.amazon.com/ko_kr/amazondynamodb/latest/developerguide/ScanJavaDocumentAPI.html