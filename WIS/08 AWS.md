목차
===
- AWS Certified
  - [AWS Certified Architecture Associate](#aws-certified-architecture-associate) 
    - [기출 문제 풀이](#기출-문제-풀이)
  - [AWS Certified Developer Associate](#aws-certified-developer-associate)
- [Quick Dictionary](#quick-dictionary)
  - [Storage Service](#storage-service)
  - [Global Infra](#global-infra)
  - [ELB](#elastic-load-balancing)
- [ECS와 EC2](#aws-ecs-and-ec2)
- [Cognito](#cognito)
- [Amazon Connect](#amazon-connect)
- [Amazon Lambda](#amazon-lambda)


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
# 기출 문제 풀이
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



#### Quick Dictionary
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

---
AWS Certified Developer Associate
===
### 📖 AWS Certified Developer Associate를 공부하면서 정리한 내용입니다.
### 참고자료 : Udemy 강의
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
|                                          | NS(Main Domain)에 CNAME을 줄 수 없기 때문에 Alias를 사용해서 ELB를 연결할 수 있다.  | 

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
### Amazon Connect : https://aws.amazon.com/ko/connect
## 1. 서비스 개요
- #### 간단히 말하면 고객센터를 구축해주는 Amazon의 몇 안되는 완성형 서비스이다. 간단한 [데모 영상](https://www.youtube.com/watch?v=wnmXSqHlgyM)을 보고 따라해봤을 때 인스턴스를 구축하고 기본 세팅을 하는데 10분이 채 걸리지 않았다.

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
- ### Instance 구축
  - [Amazon Connect](https://docs.aws.amazon.com/connect/index.html) 접속 → Amazon Connect 시작하기 → Create Instance

    <img width="600" alt="스크린샷 2023-01-27 10 07 48" src="https://user-images.githubusercontent.com/21374902/215044965-02c2bd74-946e-444a-a375-b77c6d460625.png">
    
    <img width="600" alt="스크린샷 2023-01-27 10 09 43" src="https://user-images.githubusercontent.com/21374902/215044969-3af96cb8-674e-4685-9c81-d9b14ec53324.png">
    
    <img width="600" alt="스크린샷 2023-01-27 10 10 14" src="https://user-images.githubusercontent.com/21374902/215044972-7fb6cb56-52b6-4f24-a862-4c103ea95d9a.png">
    
    <img width="600" alt="스크린샷 2023-01-27 10 11 33" src="https://user-images.githubusercontent.com/21374902/215044975-76f16971-fd85-43ec-bfc8-3db894e62ee2.png">
    
    <img width="600" alt="스크린샷 2023-01-27 10 18 24" src="https://user-images.githubusercontent.com/21374902/215044982-8b4788bb-7b9b-4427-a38b-be23cb586669.png">

- ### Amazon Connect Dashboard

  <img width="300" alt="스크린샷 2023-01-27 10 20 03" src="https://user-images.githubusercontent.com/21374902/215044987-a8852510-79ea-4a52-9789-9b7fee63a9b4.png">
  
  <img width="600" alt="스크린샷 2023-01-27 10 20 44" src="https://user-images.githubusercontent.com/21374902/215044989-d8e86809-4942-4d45-b99e-0db871bc1235.png">

  - #### 통신 채널 탐색
    
    <img width="600" alt="스크린샷 2023-01-27 10 23 21" src="https://user-images.githubusercontent.com/21374902/215044996-45720ff9-4915-4b1d-9744-d42ebfddef41.png">

    <img width="600" alt="스크린샷 2023-01-27 10 27 40" src="https://user-images.githubusercontent.com/21374902/215045000-671f32c8-e96e-4aeb-88cd-d99f1469ee14.png">

  - #### Chat widget
  - #### Prompt
  - #### Contact Flow
    - ##### [Lambda 설정](https://docs.aws.amazon.com/ko_kr/connect/latest/adminguide/connect-lambda-functions.html)
      - ###### Lambda 작성하기
        - Amazon Connect Console → 원하는 Instance의 alias 선택 → AWS Lambda 영역에서 Lambda 작성 또는 만들어둔 Lambda 선택
      - ###### Contact Flow에 Lambda 추가하기
        - Amazon Connect Admin → Routing → Contanct Flows → 원하는 흐름 선택 → INTEGRATE → Invoke AWS Lambda Function 추가 → 설정한 Lambda 함수 설정 → Input Parameter 설정
          
          <img width="500" alt="스크린샷 2023-01-31 16 41 20" src="https://user-images.githubusercontent.com/21374902/215784157-af7f3f5c-e111-4e8e-a05b-3e89084024af.png">
      - ###### Lambda 응답 데이터 활용하기
        1. Lambda 응답을 직접 참조
          
            <img width="500" alt="스크린샷 2023-01-31 16 39 07" src="https://user-images.githubusercontent.com/21374902/215784146-f924f1e5-4a54-4d30-bc0c-6d7fbc1fc6f4.png">

            <img width="500" alt="스크린샷 2023-01-31 16 39 32" src="https://user-images.githubusercontent.com/21374902/215784926-429b755e-6da6-4efd-8924-8cc6ef34bbca.png">

        2. Lambda 응답을 연락처 속성으로 저장
          - Amazon Connect Admin → SET → Set contact attributes → Add another attribute → 아래 사진처럼 설정해서 사용 가능
          
              <img width="500" alt="스크린샷 2023-01-31 16 41 37" src="https://user-images.githubusercontent.com/21374902/215784162-32c4dd3b-6d88-4d11-9331-c092bb271d7e.png">

              <img width="500" alt="스크린샷 2023-01-31 16 41 58" src="https://user-images.githubusercontent.com/21374902/215785100-32f6fab8-336a-442e-8c44-7441461dc401.png">

---

Amazon Lambda
===
### Amazon Lambda를 로컬 환경에서 개발하고 테스트할 수 있다.
OS | IDE | Language
---|---|---
macOS | Intellij Ultimate | Java 11

### 1. AWS SAM CLI 설치
- `brew tap aws/tap`
- `brew install aws-sam-cli`
- `sam --version`
- `brew update aws-sam-cli`

### 2. Intelli에 AWS Toolkit 설치
- Preferences -> Plugins -> Martketplace -> AWS Toolkit 설치

### 3. Serverless 프로젝트 생성
- File -> New -> Project -> AWS -> AWS Serverless Application
- `Validation of sam failed: Not installed.` 에러가 뜰 경우, SAM CLI executable에 `which sam` 해서 나온 경로 입력

### 4. Build & Test with SAM
- SAM 구동 후 테스트 : `sam local start-api` -> 원하는 URL 호출
- 직접 호출하는 방법 : `sam local invoke "HelloWorldFunction" -e events/event.json`

### 6. Reference
  - https://docs.aws.amazon.com/ko_kr/serverless-application-model/latest/developerguide/serverless-getting-started-hello-world.html