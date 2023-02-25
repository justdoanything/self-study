### --API Gateway
API G/W API 특정 리소스 접근이 가능하도록 `유일한 사용자 구성`. `토큰 자동 만료/새로고침`이 가능하도록 하려면? | Cognito 사용자풀 사용, Authorizer 구성, 자격 증명 또는 Access token 사용
API G/W, Lambda, S3 호스팅웹에서 CORS 오류 | API G/W에서 메서드에 대한 CORS 활성화
DynamoDB, Lambda, API G/W 구성. 요청 대기 시간 길어짐. 식별할 수 있는 방법 | API G/W와 Lambda에 X-Ray 추적을 활성화하고 사용자 요청 추적 및 분석
API G/W 사용해서 WebSocket API 구축. API로 전송되는 Payload는 생성, 업데이트, 제거의 값을 가진다. 값에 따라 다른 경로와 통합해야 한다. | 경로 선택 표현식 값을 $request.body.action으로 설정
API G/W는 통과했지만 Lambda로 도달 안됨. 두번째 계정의 Lambda는 최대 동시성 실행 | 두번째 Lambda 함수의 동시 실행 제한 구성
API G/W, Lambda, GET 메소드 허용 | Lambda 함수가 있는 API G/W, API G/W에 노출된 GET 메소드
API G/W endpoint 과부하가 걸려서 트래픽을 줄여야 한다. | ElastiCache에서 API 캐싱을 활성화
새 페이지는 CreateApiKey를 사용해서 새 API 키를 생성하고 사용한다. 기존 사용자는 정상인데 새로운 사용자는 403 Forbedden 오류 수신 | 새로 생성된 API 키를 올바른 사용 계획과 연결하려면 createUsagePlanKey 메서드를 호출
API G/W, v1, v2 버전 배포. v1은 6개월 사용 허용 | v2용 새로운 url 만들어서 배포
DynamoDB 한도를 높일 수 있는 항목 | 계정당 테이블 수, 프로비저닝된 처리량 단위 수
DynamoDB 대량 트래픽 | Accelerator를 사용해서 데이터를 캐시
테스트 목적으로 다른 버전의 API 호출 | 각 버전에 맞는 다른 URL을 배포
Restful API 작성. endpoint 요청에 대한 충족 사항 | API G/W & Lambda, S3 & CloudFront
API G/W에 활성화된 캐시를 무효화하는 옵션을 요구했고 조치할 수 있는 방법 | 고객에게 Cache-control:max-age=0 이라는 HTTP 헤더를 전달하도록 요청
API G/W, Lambda 어플리케이션. API 호출하면 Method completed with status: 502 | API 호출에 대한 Lambda 응답 형식 변경
API G/W로 액세스 하는 어플리케이션. 타사 SAML ID 공금자의 인증을 받아야 한다. 인증이 완료되면 AWS 자원에 액세스 가능 | SAML 자격 증명 공급자가 있는 Cognito 자격 증명 풀을 인증 공급자 중 하나로 사용
특정 AWS 계정의 사용자에 대한 API 액세스를 제한하는 방법 | API G/W 리소스 정책
API G/W, Restful에 대한 인증 구현. 호출을 인증하려면 Client ID, User ID가 있는 HTTP 헤더가 포함되어야 한다. 자격 증명은 DynamoDB 인증 데이터와 비교. API G/W에 구현하려면 해야할 작업은? | DynamoDB 인증 테이블을 참조하는 Lambda 권한 부여자 구현
EC2 인스턴스를 시작하거나 종료할 때 BotoServerError: 503 Service Unavailable 에러 수신 | EC2에 대한 API 요청 수 최적하를 위한 지수 백오프 구현
API G/W를 업데이트 하지 않고 Code Push를 효율적으로 수행하는 방법 | Lambda에서 별칭 및 버전 생성
XML 기반 SOAP Interface. API G/W를 사용해서 외부에 노출 필요 | API G/W Restful API 생성하고 Mapping Template를 사용해서 들어오는 JSON을 SOAP Interface에 유효한 XML Message로 변환

### --Elastic Beanstalk
Elastic Beanstalk `배포가 오래 걸리는 문제를 해결`하는 방법은? | CodeCommit Repository 생성, 개발자가 커밋 허용, Elastic Beanstalk에 직접 배포
`Elastic Beanstalk`가 배포할 수 있는 AWS 서비스는? | `ASG`, `ELB`, `RDS`
Elastic Beanstalk 지원 플랫폼 | tomcat, .NET
응용 프로그램을 2개의 구성요소로 나누고 독립적으로 확장. Elastic Beanstalk 사용해서 배포. 어떻게 해야할까? | 각 구성 요소를 별도의 Elastic Beanstalk 환경에 배포
Elastic Beanstalk 배포. ELB가 있는 WEB 계층, RDS 계층 어플리케이션. RDS 인스턴스 분리하는 방법 | RDS 없는 새로운 Elasic Beanstalk 환경 다시 생성
Elastic Beanstalk 배포. 새 버전은 이전 버전과 호환 안됨. 배포 실패 시 이전 버전으로 롤백. 새 업데이트는 일괄 전환 수행 | 새 Elastic Beanstalk 환경에 새 버전을 배포하고 환경 URL을 교체
Elastic Beanstalk로 Python 배포. 소스 번들 생성 시 요구사항 | 최상위 디렉토리를 포함하지 않아야 하고, 단일 .zip, .war 파일로 생성되어야 한다.
Elastic Beanstalk의 EC2 인스턴스 특정 명령 세트 실행하려고 한다. Beanstalk의 기능은? | .ebextensions
Elastic Beanstalk 어플을 여러 리전에 배포. 각 리전에 서로 다른 AMI 필요. 리전에 대해 올바른 AMI을 지정할 CloudFormation Template Key는 ? | Mappings
Docker 어플리케이션으로 작업. 다운타임 없이 자동으로 변경 및 업데이트 | Elastic Beanstalk를 사용하고 일괄 업데이트 정책 선택
트래픽이 많은 동적 웹. 개발을 제외한 모든 것을 AWS로 마이그레이션 | Elastic Beanstalk 환경에서 웹 사이트 코드를 배포. ASG로 Instance 수 확장
Elastic Beanstalk를 사용해서 버전 수를 25개로 제한. 소스 번들은 S3 소스 버킷에서 삭제됨. 어플 버전 수명 주기 설정에서 무엇을 해야하나? | 연령별 어플리케이션 버전 제한 설정을 0으로 설정
Elastic Beanstalk 환경을 새 어플 버전으로 업데이트하는 솔루션 | 어플 코드를 .zip으로 패키징하고 Management 콘솔에서 패키징 된 어플을 업로드한 다음 배포, 어플 코드를 .zip으로 패키징하고 Management 콘솔에서 새 어플 버전을 생성한 다음 CLI 사용해서 환경을 재구축
Beanstalk에서 지원하는 플랫폼 | Apach tomcat, .NET
Beanstalk에서 다중 컨테이너 Docker 인스턴스를 구성하기위한 필요한 것 | ECS 작업 정의
Elastic Beanstalk 전체 용량을 유지하면서 배포하는 방법 | Rolling with additional batch
Elastic Beanstalk CLB -> ALB로 마이그레이션. Management 콘솔에서 해야할 작업 | LB 유형을 제외하고 동일한 구성으로 새 환경 생성, 기존 환경과 동일한 버전의 어플리케이션 배포, swap-environment-cnames 작업 실행
healthcheckurl.config는 Elastic Beanstalk 구성 파일을 어플리케이션 소스 번들의 어디에 배치? | .ebextensions 폴더에
healthcheckurl.yaml 에러 | healthcheckurl.config로 변경해야함
Elastic Beanstalk 새 버전의 어플 배포. 개발자 수행 작업 | Elastic Beanstalk Console에서 새 어플리케이션 버전 업로드 및 배포

Beanstalk 배포. 배포는 최소한의 영향. 최대한 빠른 롤백 전략 | Immutable
Beanstalk 배포. 중단 최소화. 어플 액세스 로그 보관. 배포 정책은? | Rolling
Elastic Beanstalk에서 실행할 Linux 어플리케이션. 비용을 최소솨하면서 업데이트 중 전체 용량을 유지해야함. 배포 정책은? | Roliing with additional batch
Beanstalk 전체 용량 유지하고 실패한 배포의 영향을 최소화 하는 정책 | Rolling with an additional batch
Elastic Beanstalk 롤아웃을 완료하기 전 특정 기간 변경 사항을 평가해야 한다. | Immutable


### --CodePipeline, CodeCommit, CodeBuild, CodeDeploy
CodeCommit Repository에 대한 `HTTPS 복제 URL` 사전 구성 방법은? | AWS 자격 증명 프로필 사용하고 Git 자격 증명 도우미 설정. 도우미가 Repository로 경로를 보낼 수 있도록 구성
소스 변경될 때 배포. 배포 트리거 방식 | 소스 코드를 S3에 저장하고 파일이 변경되면 시작하는 CodePipeline 구성, 소스 코드를 CodeCommit Repository에 저장하고 커밋될 때 시작하는 CodePipeline 구성
Github Repository -> CodeCommit HTTS를 통해서 마이그레이션 | IAM에서 생성된 Git 자격 증명 세트
전달 파이프라인은 CodeCommit Repository의 Master Branch에 대한 변경이 트리거.<br>CodeBuild를 사용해서 프로세스 테스트 및 빌드 단계 구현<br>CodeDeploy로 배포<br>파이프라인은 정상. CodeDeploy가 배포를 안함. 가능한 원인은? | CodeCommit Repository의 Master Branch에서 변경 사항이 적용되지 않음, 파이프라인 초기 단계 중 하나가 실패해서 파이프라인 종료
서비스는 변경 세트를 피어 투 피어로 교환해서 여러 분산 Repository로 동기화하는지 확인해야 한다. 네트워크가 없어도 작업 가능해야 한다. | CodeCommit
CodeDeploy를 통해 재배포 하기 위한 파일 | appspec.yml
CodeDeploy 사용해서 외부 MySQL과 연결하는 어플리케이션 배포 자동화. API Key, DB Pwd에 안전하게 액세스 하기 원함 | SSM Parameter Store를 사용해서 EC2 인스턴스 IAM 역할을 사용하여 암호를 저장하고 프로그래밍 방식으로 액세스
CodePipeline에서 단위 테스트를 작성하고 파이프라인 일부로 실행하려면 | 단위 테스트 실행 단계를 포함하도록 CodeBuild 사양 업데이트
Stg, QA, Prod 환경이 있다. Stg 배포 후 QA, Prod에 배포하려고 한다. | CodeDeploy를 사용해서 여러 배포 그룹을 생성
운영 배포 전 코드를 검토하고 승인이 필요함 | 파이프라인에 승인 작업을 추가하고 승인이 필요할 때 SNS 주제에 게시해서 승인 작업을 기다린다.
CodeDeploy로 EC2에 배포. 특정 배포 파일에 파일 권한 변경. 어떤 수명 주기 이벤트를 사용해야 하나 | AfterInstall
EC2 인스턴스와 온프로미스에서 실행되는 가상 서버 모두에 S/W Package 배포를 자동화 | CodeDeploy 사용
stg, test, prod 환경에 배포. 관리할 리소스 수를 최소화하면서 배포하는 방법 | 여러 별칭이 있는 하나의 Lambda 함수를 사용해서 여러 단계의 API G/W 하나를 생성
stg,test,prod 환경에서 API G/W는 237GB 캐시를 사용중. 효율적인 배포 전략은? | 필요할 때만 개발 및 테스트 환경용 캐시를 활성화
여러 파일에 대한 일괄 변경 지원, 병렬 분기, 버전 추적 | CodeCommit
CodeDeploy로 배포할 때 내부 배포에 대한 후크 실행 순서 | 프로그램 중지 -> 설치 전 -> 설치 후 -> 프로그램 시작
CodePipeline을 구현하고 각 단계의 작업 상태를 Lambda로 알림을 보내려고 함. Lambda 함수를 이벤트 소스와 연결하는 단계 | CodePipeline을 이벤트 소스로 사용하는 CloudWatch Events 규칙을 생성
CodeBuild는 코드 빌드 -> 도커 이미지 생성 -> ECR에 푸쉬 -> 이미지에 태그 지정. 개발자가 CLI를 구성한 경우 도커 이미지를 가져오는 방법 | aws ecr get-login의 출력을 실행한 후 docker pull repository uri:tag 실행
CodeBuild 프로젝트를 실행할 때 환경 변수 길이가 결합된 문자 최대 길이 초과 | System Manager Parameter Store를 사용해서 환경 변수를 저장
코드가 Prod에 배포 되기 전 승인이 필요 | CodePipeline 단계에서 승인 작업 사용
CodeDeploy를 작동하려면 appspec.yml 파일은 어디에 배치? | 어플리케이션 소스 코드의 루트 디렉토리
로컬에 어플의 빌드,번들,패키징. EC2에 배포해야함 | 번들을 S3에 업로드 하고 CodeDeploy를 사용해서 배포를 수행할 때 S3 위치 지정
Code Repository에서 Commit을 위한 Pipeline에서 단위 테스트를 Trigger하고 Pipeline의 실패 이벤트에 대해 알림 수신 | CodeCommit에 Code를 저장하고 CodePipeline을 생성해서 단위 테스트를 자동화하고 CloudWatch를 사용해서 실패 이벤트 알림을 Trigger
온프로미스에서 실행되는 EC2 인스턴스 및 가상 서버에 어플리케이션 패키지 배포를 자동화 | CodeDeploy
여러 개발자와 코드 공유, 여러 버전 및 일괄 변경 추적과 함께 장기간 저장 필요. | CodeCommit


### --Cognito
사용자 자격 증명 절대 노출 금지 | Cognito 사용자풀 구성하고 Cognito API로 사용자 인증하고 권한 부여
인증 없는 제한된 액세스 게스트 허용 | 인증되지 않은 액세스가 활성화 된 Cognito|
FE어플은 Congnito 사용자 풀 사용. 인증 흐름 처리. SDK 사용해서 DynamoDB 어플 통합. 비밀키 노출하지 않고 API 안전하게 호출 | Cognito 자격 증명 풀을 구성하고 JWT을 임시 자격 증명으로 교환
등록된 사용자와 게스트가 있을 때 두 유형 모두에게 액세스를 제공하려면 ? | Cognito를 사용해서 인증된 역할과 인증되지 않은 역할을 사용해서 액세스 제공, IAM을 사용해서 사용자 유형에 따라 STS(Security Token Service) 작업을 사용해서 다른 역할을 맡도록 하고 위임한 역할에 대한 엑세스 제공
로그인 프로토콜을 MFA로 고도화 하려고 한다. | MFA가 포함된 Cognito
Cognito 사용자 풀 사용. 회사 로고가 있는 로그인 페이지 만들고 싶다. | Cognito에서 호스팅 사용자 인터페이스를 생성하고 회사 로고로 사용자 지정
회사의 모든 직원 정보가 SAML 직원 디렉터리에만 남아 있어야 한다. 직원에게 승인된 액세스를 제공해서 자신의 어플에만 액세스 하도록 해야한다. | Cognito 자격 증명 풀을 사용하고 SAML 공급자와 연동해서 IAM 조건 키를 사용해서 직원에게 액세스 권한 부여
사용자 자신이 비밀번호를 재설정하도록 허용하면서 사용자 인증 및 권한을 관리할 수 있는 것 | Cognito 사용자 풀, 자격 증명 풀
Cognito에서 사용자를 인증하고 DynamoDB 레코드를 생성하는 흐름 | Cognito 사용자 풀에서 토큰을 인증하고 받는다. Cognito 자격증명 풀을 사용해서 토큰을 AWS 자격증명으로 바꾼다. AWS 자격증명으로 DynamoDB에 액세스한다.
Facebook 등 OpenID 자격 증명 공급자를 기반으로 인증이 필요. 사용자 지정 권한 부여 모델을 기반으로 액세스 허용 | Cognito 사용자 풀과 사용자 지정 권한 부여자를 사용해서 JWT 기반으로 사용자를 인증하고 권한을 부여
다단계 인증이 필요한 모바일 앱 | Congnito 사용자 풀을 생성하고 사용자 생성, 사용자 풀에 대한 다단계 인증 활성화
모바일 게임. 데이터는 로컬 저장. 여러 장치에서 사용하니까 데이터 동기화 필요 | Cognito로 데이터 동기화
ALB. CloudFront. 소셜 미디어로 로그인 | Cognito 인증 공급자 중 하나로 사용하도록 CloudFront 구성
비로그인 게스트가 Cognito 자원 사이트에 액세스해서 S3 파일을 다운로드 허용 | 새 자격 증명 풀을 생성하고 인증된 자격 증명에 대한 액세스를 활성화하고 S3 액세스 권한 부여
특정 ID에 액세스하는 모든 장치에 푸쉬하려면 프로필 데이터에 업데이트가 필요 | Cognito Sync
Cognito 모든 장치 업데이트 자동으로 알림 기능 | 적절한 IAM 열할과 푸시 동기화 기능


### --CloudFormation
CloudFormation Template로 Lambda 배포 절차는? | Template에서 AWS::Lambda::Function `리소스 생성` 후 CloudFormation Template `내부에 코드 작성`, 코드 .zip `S3에 업로드` 후 AWS::Lambda::Function 리소스에 `참조 추가`
ASG, EC2. 테스트를 위한 다수의 단기 Instance. CloudFormation Template 사용해서 관리자가 시작. 테스터에게 테스트 환경만 허용. 광범위 권한 부여하지 않음. | 환경 템플릿에서 Service Catalog 제품 생성. 기존 역할이 있는 제품에 시작 제약 조건 추가. 테스트 사용자에게 Service Catalog API만 사용할 수 있는 권한 부여. Service Catalog 콘솔에서 템플릿을 시작하도록 교육.
CloudFormation Template에 Serverless Model에 의해 정의된 객체를 포함하려면 문서 루트에 포함해야 하는 섹션 | Transform
Serverless에서 배포할 때 롤백할 수 있는 기능이 필요함. 자동화 방안은? | CloudFormation Template Serverless Application Model을 준수하는 구문을 사용해서 Lambda 함수 리소스를 정의
CLI 명령어 aws cloudformation deploy를 사용해서 배포할 수 있도록 template을 준비하려면? | aws cloudformation package를 사용해서 소스 코드를 S3 버킷에 업로드하고 수정된 CloudFormation Template를 생성한다.
AWS::ElasticLoadBalancing::LoadBalancer 리소스 이름이 "ElasticLoad Balancer"인 CloudFormation에서 생성된 로드 밸런싱된 웹 사이트의 URL을 반환하는 코드 | "Fn::Join" : ["". [ "http://", {"Fn::GetAtr" : [ "ElasticLoadBalancer","DNSName"]}]]
CloudFormation Stack의 Resource 중 하나를 생성할 수 없으면? | 기존에 생성된 Resource를 삭제하고 Stack 생성을 종료
Lambda, DynamoDB, API G/W. 배포 준비 끝. 롤백 기능 필요. 자동화 방안 | CloudFormation Template의 Serverless Application Model을 준수하는 구문을 사용해서 Lambda Resource를 정의
CloudFormation Template으로 배포. 스택 중 하나 업데이트. 실행 중인 리소스에 미치는 영향 파악 | 변경 세트 조사
CloudFormation Template 배포. DB의 이름 변경. DeletionPolicy 속성이 기본값에서 변경이 안됨. | 새 데이터베이스를 생성하고 이전 데이터베이스를 삭제한다.

### --CloudWatch
Step Function 작업 상태를 CloudWatch로 오류 표시. 원래 입려과 오류 모두 보존하는 방법 | Catch문에서 ResultPath를 사용해서 원래 입력에 오류 표시
CloudWatch Logs에 예외를 계산을 위한 메트릭 필터 생성. 결과가 반영되지 않음. 원인은? | 필터를 만든 이후에 발생하는 데이터에만 지표를 생성
타사의 API 사용. 실패가 임계치면 알림. | CloudWatch에 사용자 지정 지표를 게시하고 SNS 사용
CloudWatch API 호출 400 에러 | 지수 백오프로 통화 재시도
로그 때문에 메모리 가득참. 로그 중앙 집중화 필요 | CloudWatch를 설치해서 로그를 CloudWatch로 보내고 전송된 로그는 인스턴스에서 삭제한다.
EC2에서 실행중인 어플의 로그 데이터를 액세스 하고 싶다. | EC2에서 CloudWatch Logs Agent를 설치
Lambda 함수가 설정된 시간 제한 미만으로 완료되도 API G/W에서 시간초과가 발생. CloudWatch의 API G/W 지표 중 도움이 되는 것 | IntegrationLatency, Latency
개발과 운영 로깅 추가 | 코드에서 다른 로깅 로직을 구현하는 다른 Lambda 함수를 가리키고 CloudWatch Lgos에 액세스
EC2에서 실행되는 어플. 중앙 집권 로그 | CloudWatch Logs
EC2 인스턴스에 CloudWatch 어플 지표를 저장하려고 한다 | CloudWatch PutMetricData API 호출을 사용해서 사용자 지정 지표를 제출한다. API 호출을 활성화하기 위한 IAM 역할로 EC2 인스턴스를 시작한다.
콜백 트래픽이 많음. 콜백이 지속적으로 수신되는지 확인하고 싶고 데이터를 10일 동안 유지. 콜백 수의 임계치가 초과하면 경고 받고 싶음 | 콜백 데이터를 CloudWatch에 사용자 지정 지표로 푸쉬하고 CloudWatch 알림 매커니즘을 사용해서 시스템 관리자에게 알림
온프로미스 어플도 중앙 집권적으로 CloudWatch에 보고 싶음 | CloudWatch Agent를 온프로미스 서버에 설치하고 IAM 사용자 자격 증명을 사용하도록 Agent를 구성
CloudWatch Logs의 Log Group에 중요한 로그 데이터를 게시. KMS 고객 마스터 키를 사용해서 로그 데이터를 암호화 해야함 | CLI Associate-kms-key 명령을 사용해서 ARN 지정


### --DynamoDB
DynamoDB. 개별 사용자로 구성. `다른 사용자 데이터 액세스 불가`하도록 하려면? | `기본 키 값을 기반`으로 항목 액세스 제한
DynamoDB 특징은? | `낙관적 동시성 제어` 사용, `일관성을 위해 조건부 쓰기` 사용
기존 SNS 계정 사용해서 게임 로그인, 데이터는 DynamoDB 저장, DynamoDB API 안전한 접근 방식 필요. `DynamoDB API 요청에 서명`하는 방법은? | `Web ID 연합` 사용, `임시 보안 자격 증명`을 요청에 서명
`Provisioning 된 처리량 효율성`을 위한 DynamoDB Hash Key Schema의 예시는? | Application의 `User ID`
DynamoDB에서 `ProvisionedThroughputExceededException` 발생. CloudWatch에 처리량 초과하지 않음. 원인은? | 특정 Hash Key에 대한 용량을 초과
DynamoDB에 실시간 동적 업데이트. 덮어쓰기 방지 옵션은? | 조건부 쓰기
EC2 어플이 DynamoDB 쓰기 권한. 보안키는 사용하지 않음 | EC2에 IAM 추가. DynamoDB에 쓰기 권한 IAM 역할 생성
DynamoDB 많은 읽기 용량 소비. 속성이 매우 큼. 어플은 모든 속성이 필요하지 않음. | 최소한의 프로젝션 속성 집합으로 글로벌 보조 인덱스를 만든다.
EC2 실패할 경우 세션이 손실되지 않도록 해야 한다. | DynamoDB 사용해서 확장 가능한 세션 처리를 수행
분라되있는 DB에서 다른 DB에서 거의 실시간으로 업데이트를 가져오는 방법 | DynamoDB Stream을 사용해서 다른 DB의 모든 변경 사항을 전달
설문조사 결과를 DynamoDB에 저장. 데이터 처리 후 레코드를 S3에 저장. 각 설문 조사의 데이터를 보관할 수 있는 방식 | 테이블 DynamoDB Stream을 활성화하고 Stream을 Lambda 트리거로 작성. Stream 레코드가 수정되면 S3에 저장
여러 EC2에 어플이 있고 DynamoDB에 데이터 저장. 읽기가 강력하게 일관성이 있는지 확인 | GetItem을 호출할 때 ConsistentRead를 true로 설정
아이템 거래. 두 사용자 레코드를 단일 트랜잭션으로 업데이트하거나 롤백해야 한다. 이런 기능을 위한 DB 옵션 | 트랜잭션 블록 내에서 수행되는 작업이 있는 MYSQL, Transact* 작업을 사용하여 읽기 쓰기를 수행하는 DynamoDB
DynamoDB 글로벌 보조 인덱스 항목을 찾으려고 한다. 가장 적은 수의 읽기 용량 단위를 사용하기 위해 호출하는 API | 최종 일관성 읽기를 사용한 쿼리 작업
DynamoDB 테이블에 세션 정보 캐싱. 오래된 항목을 자동으로 삭제하는 방법 | 만료 시간이 있는 속성을 추가해서 해당 속성 기반의 TTL 기능 활성화
DynamoDB에 요금 데이터 저장. 수시로 변경. 고객한테 변경이 반영되지 않음. 원인은? | 아이템 가격 변경 시 캐시가 무효화되지 않는다.
DynamoDB 테이블은 주문 날짜를 기준으로 분할. 트래픽 증가. 쓰기 제한되고 소비된 처리량은 provisioning된 처리량보다 닞다. | 파티션 키 값에 난수 접미사를 추가한다.
DynamoDB는 GSI를 사용해서 읽기 쿼리 지원. 쓰기 활동이 많을 때 작업이 제한됨. 쓰기 용량 단위는 넉넉함. 제한되는 이유 | GSI 쓰기 용량 단위가 부족
DynamoDB에서 대규모 스캔 작업을 사용할 때 테이블에 프로비저닝된 처리량에 대한 스캔 영향을 최소화 하기 위한 기술 | 스캔에 대해 더 작은 페이지 크기를 설정
DynamoDB 요청의 95%가 반복 읽기. NoSQL 계층을 확장해서 캐싱하기 위한 전략 | DynamoDB Accelerator
DynamoDB에 쓰는 어플이 있다. 사용량이 많아져서 ConditionalCheckFailedException 발생. 여러 클라이언트가 동일 레코드에 쓰기 작업 개선 | jiter를 사용해서 오류 재시도 및 지수 백오프 구현
단일 API 호출로 DynamoDB 테이블에서 여러 항목을 검색할 수 있는 작업 | BatchGetItem
DynamoDB 한시간에 한번씩 분석하고 더이상 필요하지 않음 | 테이블 삭제 및 시간당 새 테이블 생성
DynamoDB 테이블에서 데이터를 읽을 때 선택한 일관성 모델이 프로비저닝된 처리량에 미치는 영향 | 강력하게 일관된 읽기는 최종 일관된 읽기보다 더 많은 처리량을 사용한다.
DynamoDB API. ThrouttlingException 오류 발생. SDK와 호환되지 않는 언어로 코딩 | 어플리케이션 로직에 지수 백오프 추가
NoSQL을 DynamoDB로 마이그레이션. 빈번한 쿼리 최적화, 읽기 지연 시간 감소, 테이블의 특정 주요 속성에 대한 빈번한 쿼리에 대한 계획 | 자주 쿼리되는 키에 글로벌 보조 인덱스를 만들고 색인에 필요한 속성을 추가
DynamoDB 테이블 스캔 실행 시간을 최소화. 워크로드는 강력하게 일관된 읽기 용량 단위의 평균 절반 | 속도를 제한하면서 병렬 스캔 사용
Lambda & DynamoDB. 항목을 검색, 속성 업데이트, 항목 생성, 기본 키에 액세스. 필요한 IAM 권한은? | UpdateItem, GetItem, PutItem
DynamoDB. 읽기/쓰기 작업에 대한 응답 시간 줄이고 싶음 | DynamoDB Accelerator
파티션 키=user_id, 정렬 키=sport_name. sport_name에 대한 점수를 기반으로 최고 성과자를 표시하는 리더보드. 효율적인 추출 방법 | 파티션 키가 sprot_name이고 정렬 키가 score인 글로벌 보조 인덱스를 만들어서 결과를 가져온다.
DynamoDB 대용량의 작업이 일시적으로만 필요하다면 | 테이블을 생성하고 삭제한다.
DynamoDB에서 Client 요청에 문제가 있으면? | 4xx HTTP 응답
DynamoDB API 호출이 가장 적은 레코드를 재처리하기 위한 방법 | 성공적으로 처라된 상목을 삭제하고 새로운 BatchWriteItem 작업을 다시 실행
DynamoDB에 제품 정보가 들어있는데 추가로 이미지를 포함해야 한다. | S3에 이미지를 저장하고 "제품" 테이블 항목에 S3 URL Pointer를 추가
DynamoDB 테이블에 있는 여러 항목에 대한 조정된 전체 변경을 수행 | TransactWriteItem 작업을 사용해서 변경 사항을 그룹화하고 테이블 항목을 업데이트
DynamoDB 읽기 요청 성능 향상 | ElastiCache의 Creational Cluster의 데이터를 Caching 하도록 어플리케이션을 구성, 테이블의 읽기 용량 늘리기
폴링 응용 프로그램. 풀 결과를 DynamoDB에 저장. 풀 데이터 제거 후 S3에 저장. | DynamoDB Stream을 활성화해서 Stream을 Lambda에 대한 Trigger로 구성. Stream 레코드가 수정되면 S3에 저장.

5개 쓰기 용량 단위가 있는 DynamoDB 테이블에 트랜잭션을 쓰고 있음. 읽기 처리량 높은 옵션은? | 4KB 크기 항목을 읽는 5개 읽기 용량 단위의 최종 일관된 읽기
DynamoDB 저장 되는 항목의 크기는 7KB, 읽기는 강력한 일관성 필요. 읽기 속도는 초당 3개 항목. 쓰기는 초당 10 항목. DynamoDB 크기는 | 6 읽기 용량 단위, 70 쓰기 용량 단위
DynamoDB 초당 90개 읽기 항목. 각 항목은 3KB. 필요한 읽기 용량 단위 프로비저닝 | 45
DynamoDB 강력한 일관된 읽기 초당 100개 항목을 읽어야 하고 각 항목의 크기는 5KB. 프로비저닝된 읽기 처리량을 어떤 값으로 설정해야 하나? | 200 읽기 용량 단위
600개의 온도 게이지를 모니터링해서 1분마다 온도 샘플을 수집하고 DynamoDB에 저장. 각 샘플에는 1K 데이터 쓰기가 포함. 테이블에 필요한 쓰기 처리량 | 10 쓰기 용량


### --ElastiCache & Load Balancer
ELB + EC2, 세션 데이터 작성하는 위치는? | ElasticCache에 데이터 쓰기
ElastiCache 좋은 사례 | 읽기가 많은 어플의 워크로드 대기 시간과 처리량 개선, 컴퓨팅 집약적인 어플의 성능 향상
ElastiCache for Redis 사용. 로드 증가. 장애 시 복원력이 필요 | ElastiCache를 수직적으로 확장
ELB 뒤에 여러개의 서버. 웹 서버의 메모리에 세션 데이터 저장. 세션 데이터 손실 방지. 다운타임 최소화 | Redis용 ElastiCache 클러스터
읽기 쿼리에 영향을 최소화 하면서 트래픽 급증을 대응하는 방법은? | ElastiCache를 사용해서 데이터 캐시
온프로미스 세션 공유하는 어플을 마이그레이션. 내결함성, 확장성, 무중단 필요. 세션 상태 저장 옵션은? | ElastiCache에 세션 상태 저장
ElastiCache를 사용해서 캐쉬 계층 구현. 반응형 어플리케이션이라 가격에 대한 업데이트는 강력한 일관성 필요 | 먼저 백엔드에 쓰고 캐시 무효화
RDS 앞에 캐싱 계층. 서비스 실패 시 재생성하는데 비용이 많이 듬 | 클러스터 모드에서 ElastiCache Redis 구현
읽기 전용 레코드가 대용량 트래픽 | Redis용 ElastiCache를 배포하고 어플리케이션에 대한 데이터를 캐시
인메모리 저장소를 사용해서 누적된 게임 결과를 저장하는 어플. 개별 결과는 DB 저장. AWS로 마이그레이션. 일관된 결과를 위해 누적 게임 결과는 어디에 저장? | ElastiCache
ELB로 분산처리 어플 작성. 기존 로그인 사용자들이 다시 로그인해야하는 문제 발생. 방지 대책은? | ElastiCache에 세션 상태 저장
NAT 장치가 Private Subnet에 바인딩 트래픽 대상이 되도록 라우팅 테이블 수정. Private Subnet에서 인터넷으로 아웃바운드 실패. 해결 방법 | NAT 인스턴스에서 Source/Destination Check 속성 비활성화
세션 데이터를 외부화 하는 방법 | ElastiCache Memcached 클러스터를 생성한 다음 어플리케이션 수준에서 세션 처리를 구현하여 세션 데이터 스토리지용 클러스터 활용
반복 읽기 요청이 많다. 반복 읽기 쿼리를 위한 인메모리 저장소 | ElastiCache
ALB 로그 파일에 Client Public IP Address를 캡쳐하기 위한 방법 | X-Forwarded-For Header를 로그 구성에 추가

Client IP 기준으로 처리. ALB 뒤에 배치되면서 같은 IP로 들어옴 | X-Forwarded-For 헤더를 검사하도록 어플 코드 변경
ELB를 사용하고 EC2에 CPU 제약이 있다. EC2의 CPU 로드를 늘리지 않으면서 웹 사이트를 보호하는 전략 | ELB에서 SSL 구성, SSL 종료로 ELB 구성
ALB에 등록된 Lambda 함수에 다중 값 헤더를 보내려고 한다. | ALB에서 다중 값 헤더를 활성화 해야함
CLI로 ALB에 Lambda 함수 등록. Client에서 ALB 통해 Lambda 호출 실패. 이유는? | Lambda 함수 호출 권한이 없음




### --S3 & Encryption
S3 반복 호출해서 `Limit Exceeded` 발생. 해결방법은? | 어플리케이션에서 `지수 백오프 구현`
S3 웹 호스팅. CORS 에러 발생 | CORS 구성을 생성해서 교차 출처 요청을 허용하는 cdfonts 버킷 구성
S3에서 가장 저렴한 비용으로 다운로드 액세스 안전하게 제어하는 방법은? | S3 Presigned URL와 함께 CloudFront 사용
S3에 저장하기 전 암호화. 암호화 키는 보안팀이 관리 | KMS 암호화 사용. 고객 마스터 키를 사용해서 클라이언트 측 암호화 구현
S3 서버측 암호화를 위헤 제공하는 블록 암호 유형은? | `Advanced Encryption Standard`
S3 보안 인프라 관리 싫음. 암호화 키는 제어하고 싶음. | SSE-KMS 사용
Envelope 암호화 KMS 작동 방법은? | `Master Key`는 `암복호화`용, `Text Data Key`는 `고객 데이터 암호화`용
S3 버킷 민감 정보 저장. 모든 데이터 암호화. 구현하는 방법은? | x-amz-server-side-encryption 헤더가 포함되지 않는 객체 업로드 방지하는 정책 설정
100 GB를 KMS 암호화하는 방법은? | Plain Text Key와 Data Key의 암호화된 복사본을 반환하는 GenerateDataKey API 호출 생성. Plain Text Key를 사용해서 데이터 암호화
S3 데이터 처리 어플리케이션. 하루 10번, 1분 소요. 배포하고 호출하는 방법은? | Lambda로 배포하고 S3 이벤트 알림과 같이 호출
이미징 서비스를 EC2로 마이그레이션. 이미지는 Private S3 버킷에서 가져옴. 해야할 설정은? | S3 버킷에 대한 읽기 전용 권한이 있는 EC2 서비스 역할을 생성하고 연결
S3 버킷에 프로필 사진 저장. 로그인할 때마다 표시. 공개적으로 액세스 불가 | 사진의 S3 key를 DynamoDB에 저장. 함수를 사용해서 Presigned URL 생성 후 반환
보안 문서 Private S3에 저장. 요청 된 사용자 15분 동안만 다운로드 가능 | 만료 시간이 15분인 Presigned S3 URL 생성
KMS 암호화를 활성화하고 성능이 느려짐. 원인은? | KMS API 호출 제한이 원하는 성능을 달성하는데 필요한 것보다 적다.
어플의 키는 온프로미스 데이터 센터에서 관리. 암호화는 S3에서 처리. | 고객이 제공한 키로 서버 측 암호화 사용
S3에 대용량 파일 저장하고 메타 데이터를 제공해서 사용자가 선택해서 다운로드. 메타데이터를 인덱싱 하고 밀리초 내에 검색 기능 제공 | DynamoDB 를 사용해서 검색 기능 제공
The specified bucket does not exist 에러 발생. 원인 분석 시작지는? | CloudTrail에서 DeleteBucket 이벤트 확인
데이터 파일은 로컬로 캐싱하고 공유 이미지를 로컬 디스크에 기록. 마이그레이션할 때 수평적 확장을 허용하기 위한 것 | 공유 이미지를 제공하기 위해 S3를 사용하도록 어플을 수정하고 캐시 데이터를 로컬 디스크에 쓴다.
S3로 사진 공유 웹사이트 운영하는데 다른 사이트에서 도용함 | S3의 공개 읽기 액세스를 제거하고 날짜가 있는 서명 URL 사용
S3에 업로드 하기 전에 파일이 암호화되었는지 확인 | GenerateDateKey API를 사용한 다음 해당 데이터 키를 사용해서 Lambda 함수 코드의 파일을 암호화
KMS 암호화 S3. 어플은 고객 마스터 키(CMK)에 액세스. 어플에 액세스 권한 부여하는 단계 | 어플이 있는 EC2에 연결된 IAM EC2 역할의 키에 대한 액세스 권한 부여. IAM 정책이 키에 대한 액세스 권한을 부여할 수 있도록 키 정책 작성.
데이터 저장 전 각 파일에 대한 고유 키를 사용해서 암호화해야 한다. | KMS GenerateDataKey API를 사용해서 데이터 키를 가져오고 암호화한다. 암호화 된 데이터 키와 데이터를 저장
자체 마스터 키를 사용해서 AWS 서비스 활용 | KMS를 사용한 SSE
S3의 us-standard region에 객체를 저장하고 성공 확인. 객체를 읽으려고 했는데 실패 | us-standard는 최종 일관성을 사용하고 bucket에서 객체를 읽기 위해선 시간이 필요하다.
S3의 putObject 권한이 있는 IAM 사용자 생성. KMS 관리형 키로 암호화. IAM 사용자의 키로 putObject 했을 때 Access denied | kms:GenerateDataKey 작업을 허용하도록 IAM 사용자 정책 업데이트
EBS 지원 Intance와 Instance Store 지원 Instance의 주요 차이 | EBS 지원 Instance를 중지하고 다시 시작할 수 있다.
S3 데이터 암호화 저장. 누가 Master Key에 접근할 수 있는지 제어 필요. Master Key를 쉽게 생성, 교체, 비활성이 가능한 방식 | KMS
S3 KMS. AWSKMS; Status Code: 400; Error Code: ThrottlingException 발생 | AWS Support에 KMS 속도 제한 증가를 요청 문의. 응용 프로그램 코드에 지수 백오프를 사용해서 오류 재시도 수행.
유료 회원에게만 콘텐츠 제공. 현재는 모든 객체 비공개. 유료 회원에게만 다운로드를 제공하는 방법 | 유료 가입자가 다운로드 요청을 할 때 Pre-signed URL 생성
S3 데이터 액세스 제한 기능 | S3 버킷 정책 설정, 버킷이나 객체에 S3 ACL 설정
S3 버킷은 초당 300개 이상의 GET 요청 처리 | CloudFront를 S3와 통합, S3 키 이름 접두사를 무작위로 지정
모든 데이터는 전송 중에 암호화해서 S3에 저장. 모든 트래픽이 암호화되었는지 확인하는 방법 | SecureTransport가 false인 트래픽을 거부하는 버킷 정책 생성
KMS를 사용해서 클라이언트 측 암호화 수행 단계 | GenerateDataKey API를 호출해서 데이터 암호화 키의 일반 텍스트 버전을 검색해서 데이터를 암호화
S3 버킷 읽기 성능 향상 | 20개 이상의 접두사를 생성. 접두사로 파일을 배치. 접두사를 병렬로 읽기
어플은 EBS를 사용해서 데이터 저장. 암호화 되도록 개발하려면? | 데이터 저장에 암호화된 EBS 볼륨을 사용하도록 EC2 인스턴스 집합을 구성
버킷의 로깅이 활성화 되어 있고 개발자는 문서를 이동한 후 작업을 중단함. 버킷의 용량이 50GB. 원인은? | 동일한 버킷에 로그인하면 로그가 기하급수적으로 증가
S3 암호화. 매년 키 교체 필요 | 자동 키 교체와 함께 KMS 사용
S3에 전송 중인 데이터 암호화 | KMS 암호화하고 클라이언트 측 암호화 설정, SSL 연결을 통한 데이터 전송
S3 Bucket에 정보를 반환하는 API가 필요하고 Lambda와 API G/W로 개발. MSA 어플이 S3 버킷에 필요한 액세스 권한을 갖도록 하려면? | S3 버킷에 액세스 할 수있는 권한이 있는 IAM 역할을 생성하고 이를 Lambda의 실행 역할로 할당
많은 트래픽. 수천 개의 인스턴스. 시간당 로그파일 저장. S3에서 최적의 성능을 제공하는 명명 체계 | HH-DD-MM-YYYY-log_instanceID
암호화 SDK를 사용할 떄 암호화에 사용된 데이터 암호화 키를 언제 추적합니까? | SDK는 데이터 암호화 키를 암호화하고 반환된 암호문의 일부로 암호화하여 저장합니다.
S3 이미지 저장할 때 이벤트 알림으로 Lambda가 이미지 크기 조정. Lambda 추가 트래픽 처리 방안 | Lambda는 요청을 동시에 실행하도록 확장됨
S3에 복사하는 Lambda 작성함. 두번째 버킷에 복사되지 않고 평균 500초정도 걸림. 원인은? | Lambda 함수 최대 실행 시간이 300초
S3 버킷에 액세스 하기 위한 역할을 CLI로 생성. create-role 명령어. EC2 서비스가 역할을 맡도록 하기 위한 정책 | 신뢰 정책을 추가해야함
EBS에 저장된 데이터 보호 방법 | EBS 볼륨 위에 암호화된 파일 시스템을 사용
S3에 호스팅하고 있는 웹. 초기 파일 변경하려면? | S3에 새로운 html 업로드 후 색인 문서 속성을 새로운 html로 변경
10명 팀원에게 고유한 폴더 경로 S3 권한 부여. 10개의 권한을 생성하지 않고 일반화하는 방법은? | IAM 정책 변수 사용
S3 암호화. 마스터 키 사용 이력 추적 가능해야함 | SSE-KMS
초당 수천개의 PUT 요청을 커버하는 S3 경로는? | 파일 이름에 타임스탬프를 접두사로 붙인다. (과거엔 무작위 해쉬 문자열)
S3 상태 대시보드. S3 메타 데이터는 DynamoDB에 저장. 최적의 비용 설계 | Lambda가 지원하는 S3 이벤트 알림을 사용해서 메타데이터를 DynamoDB에 유지. 대시보드는 DynamoDB를 폴링해서 변경 사항 반영
S3 호스팅 웹사이트. 모든 요청을 추적하고 로깅 및 보관. 비용 효율적 솔루션 | S3 서버 액세스 로깅 활성화. 수명 주기를 90일로 설정. 90일 이내에 S3 Glacier로 데이터 이동
객체 업로드 시 SSE 암호화 요청 헤더 | x-amz-server-side-encryption
S3 버킷 파일 추가되면 DynamoDB 레코드 삽입 | DynamoDB에 삽입하는 Lambda를 호출하는 S3 이벤트 구성
AWS 계정당 사용할 수 있는 S3 버킷 수 | 계정당 100
코드 버킷에서 자산 버킷에 접근할 때 모든 사용자에게 403 오류 | 자산 버킷의 정책을 수정해서 모든 보안 주체에 대한 액세스 허용
S3 웹 호스팅. 다른 bucket의 이미지 다운로드 실패 | S3에서 CORS 활성화
S3에서 400 에러 | S3 허용 용량 초과
1MB 미만 파일 S3 버킷에 업로드 시간 너무 오래 걸림 | 객체 키에 임의의 접두사 추가

S3 6GB 파일 업로드. 최대 크기 초과 에러 메시지 | 멀티파트 업로드 API 사용
S3에 대용량 파일 업로드 실패 | 멀티파트 업로드 API 사용해서 업로드





### --StepFunction
여러 공급업체에 요청하고 일주일 걸리는 프로세스 | Step Function을 사용해서 병행 Lambda 함수를 실행하고 결과 결합
대형 상태 머신을 호출하는 Lambda. 쉽게 꺠지는 레거시 사용자 코드. 리팩토링하는 서비스는? | Step Functions

### --SAM
SAM을 사용해서 Lambda 어플 구축. 배포하기 위한 실행 순서 | 로컬에서 SAM Template 빌드하고 Template를 S3에 패키징하고 S3에서 Template을 배포
Serverless Restful API를 반복적이고 일관되게 배포 | 인라인 Swagger 정의를 사용해서 SAM Template 배포, Swagger 파일을 정의해서 Swagger 파일을 참조하는 SAM Template 배포
SAM CLI 사용해서 배포할 서버리스 어플. 개발자가 배포 전 해야할 것 | SAM 패키지를 사용해서 서버리스 어플리케이션을 번들
Serverless 어플리케이션 자동 배포 스크립트 개발. SAM template를 사용하는 방법 | aws cloudformation 패키지를 호출해서 배포 패키지를 생성하고 aws cloudformation deploy를 호출해서 패키지를 배포. sam package를 호출해서 배포 패키지를 생성하고 sam deploy를 호출해서 패키지를 배포
SAM CLI로 어플을 재배포 하기 위한 명령어 조합 | sam init, sam deploy

### --SQS
SQS 메시지 완료하는데 5분 소요. 메세지 성공적 처리하고 중복 처리 최소화하면서 메시지 제거하는 방안은? | `가시성 시간 초과`가 `증가한 메세지 검색`, 메시지 `처리`, 대기열에서 `삭제`
SQS 설명 | 메세지는 한 번 이상 배달되고 순서는 불확실
SQS 사용 어플리케이션. 분당 하나의 메세지가 대기열에 게시되서 비용 증가 | SQS 대기열 폴링 시간 초과를 늘린다.
SQS 대기열에 메시지 업데이트. 자주 변경되지 않지만 업데이트 시간 최소화 | 20초마다 긴 풀링을 사용하여 메시지 검색
SQS에 유료회원, 무료회원용 업로드. EC2가 SQS를 폴링. 유료회원 먼저 처리해야 함 | 2개의 SQS 대기열을 만들고 유료 회원을 먼저 폴링
SQS 대기열에 메세지를 검색하는 경우 다른 사용자가 메시지에 액세스 할 수 없는 기간의 기본값 | 30초
SQS 대기열이 기본 VisibilityTimeout 값으로 구성되어 있다고 가정할 때 메세지 수신 시 다른 인스턴스가 이미 처리되었거나 현재 처리 중인 메세지를 검색할 수 없도록 하는 방법 | ChangeMessageVisibility API를 사용해서 VisibilityTimeout을 늘린 다음 DeleteMessage API를 사용해서 메세지를 삭제
SQS 지연 대기열이 수행하는 것 | 메세지는 대기열에 처음 추가될 때 구성 가능한 시간 동안 숨겨진다.
각 메세지를 처리하는데 15분 이상이 걸려서 timeout 우려됨 | SQS 대기열에 메세지를 추가하고 ASG에서 EC2 설정해서 대기열을 폴링하고 메세지가 도착하면 처리된다.
1KB에서 최대 1GB 크기의 SQS 메세지를 사용하는 어플을 설계해야한다. SQS 메시지는 어떻게 관리해야하나? | S3 및 Java용 SQS 확장 클라이언트 라이브러리를 사용
주문 요청이 과부하. 비용 효율성을 유지하면서 유연성을 더하는 방법 | SQS 대기열을 구현해서 FE와 BE 분리, SQS 대기열에서 가져오도록 BE 수정
트래픽 몰림. BE에 일시적인 볼륨 급증을 완화하는 탄력적인 방법은? | 사진이 S3에 업로드되면 SQS 대기열에 게시. 대기열을 이벤트 소스로 사용해서 Lambda 함수를 트리거. Lambda 함수에서 사진을 스캔하고 구문 분석
SQS를 사용해서 독립 발신자의 메시지를 관리. 각 발신인은 메세지를 받은 순서대로 처리. SQS 기능은? | 고유한 MessageGroupId로 각 발신자를 구성
SQS 이미지 대기열 폴링을 자주함. CPU 주기를 소모하고 빈 응답이 많음. 빈 응답을 줄이는 방법은? | 이미징 큐 ReceiveMessageWaitTimeSeconds 속성을 20초로 설정
SQS 대기열에서 메세지를 가져옴. 모든 메세지는 암호화해야함 | SQS 대기열을 생성하고 KMS에서 SSE를 사용해서 대기열을 암호화
SQS 대기열에서 메세지를 1개씩만 수신해서 처리함. 수신 메세지 늘리는 방법 | ReceiveMessage API를 호출해서 MaxNumberOfMessages를 1보다 큰 값으로 설정
SQS 비동기 처리했는데 일부 이벤트가 여러번 처리됨 | SQS 대기열을 FIFO로 변경

### --SNS
SNS 모바일 푸쉬. 개별 장치에 직접 알림을 보내려면 장치 등록 식별자 혹은 토큰을 SNS에 등록 | CreatePlatformEndPint API 함수를 호출해서 여러 장치 토큰을 등록한다.
SNS 전송 유형 | HTTP, SMS
Kinesis Data Stream을 처리하는 Lambda. 처리된 데이터가 포함된 알림을 받아야 한다. | 처리된 데이터를 SNS 주제에 게시
SNS에서 보내는 구조화된 알림 메시지 형식 | MessageId, unsubscriberURL, Subject, Message 및 기타 값을 포함하는 JSON
SNS 게시 요청에 대한 유효한 인수 | TopicAm, Subject, Message


### --Lambda
API G/W & Lambda로 구성. Lambda는 Session 정보를 어디에 저장? | DynamoDB에 저장
Lambda를 사용할 때 Handler 범위 밖에서 Client를 Instance화 하면 얻는 이점 | `연결 재사용` 활용
`CPU 고사용` Lambda 배포. 런타임 최소화하고 싶으면? | `메모리` 할당이 최대로 설정된 함수 배포
CPU 집약 데이터 처리 Lambda nodejs 개발. 완료시간 단축 솔루션은? | Lambda 사용 가능 메모리 증가
Lambda 함수 오래 걸림. 어떤 컴퓨팅 리소스를 증가시켜야할까? | Lambda 함수에 할당 된 메모리를 증가
`Lambda` 함수로 `Kinesis Data Stream` 처리. Lambda 함수가 Kinesis Stream에서 중복 레코드 생성. Lambda 함수가 없는 Stream은 중복 없음. 원인은? | Lambda가 오류를 처리하지 않고 Lambda 서비스가 재처리를 시도
Lambda 함수. 두번째 계정의 있는 DynamoDB 테이블 액세스 방법은? | 테이블 액세스할 때 Lambda 에서 새 역할을 맡음
Lambda 함수 테스트 해도 CloudWatch Log에 생성되지 않음. 원인은? | Lambda 함수 실행 역할에 CloudWatch Log에 쓰기 권한이 없음
병렬, 순차적 실행하는 어플을 Lambda로 리팩토링. POST는 G/W에서 처리. 동일한 순서로 호출하기 위한 Lambda 호출 방식은? | Step Functions 상태 머신을 사용해서 Lambda 함수 조정
Lambda가 초당 여러번 호출. 호출당 50MB 파일 다운로드 | /tmp 경로에 파일을 캐시
Lambda는 VPC의 RDS Mysql 읽고 다른 싸이트에서 데이터 가져옴 | Lambda 함수 기본 구성에 VPC Private Subnet 연결 추가, VPC에 NAT G/W 추가
배포 오류일 때 Lambda 이전 버전으로 롤백 | 현재 버전을 가리키는 별칭을 사용하도록 어플을 변경. 새 코드 배포. 별칭 업데이트해서 트래픽 10%를 새로운 버전으로 보내고 오류 시 이전 버전으로 전송
DynamoDB를 트리거하는 Labmda. 실행역할은 추가됨. Lambda 활성화 했지만 트리거되지 않음. | Lambda 함수에 대한 이벤트 소스 매핑 구성
Lambda는 정기적으로 교체되는 사용자의 이름과 암호를 사용해서 외부 사이트에 액세스. 안전하게 보관하는 방법은 ? | System Manager Parameter Store, KMS
Lambda 코드를 S3에 새로 올렸지만 이전 버전이 실행됨 | 업데이트 기능 코드 API를 호출
DB 연결해서 동작하는 Lambda 코드가 있다. 비용 증가 없이 성능 개선 방법 | Lambda 함수에 필요한 모듈만 패키징, RDS 연결을 핸들러 함수 외부로 이동
하나의 시작 지점을 하나의 계정으로 중앙 집중화하고 이벤트가 발생하면 모든 Lambda 함수 호출 | 모든 다중 계정 Lambda를 SNS 주제에 구독하고 SNS 주제에 대한 페이로드를 사용해서 SNS Publish API를 호출
Lambda 함수 오류 기록 | Lambda 함수 코드의 로깅 문을 통해 오류 보고
Lambda 함수를 사용해서 S3 이미지 처리. 썸네일을 저장해야하는 새로운 기능 필요. 기존 시간에 영향 미치지 않음 | S3 이벤트 알림을 생성하고 새 Lmabda 만들어서 처리
10MB 미만의 파일을 생성하는 Lambda 함수 설계. 임시 파일은 여러번 액세스되고 수정됨. 나중에 필요 없는 파일. 임시 파일이 저장되야할 장소 | /temp 디렉터리
Lambda 함수 코드의 로그 검사는 어디에 저장 | CloudWatch
많은 파일을 처리. 파일당 4분 소요. 모든 파일 처리 방법 | 비동기식 Event Lambda 호출을 수행하고 파일을 병렬 처리
DynamoDB 테이블 항목 수명 주기 활동을 기반으로 Lambda 트리거 | DynamoDB Stream을 활성화하고 Stream에서 동기적으로 Lambda 함수 트리거
Lambda로 정적 텍스트와 이미지가 모두 포함된 뉴스레터를 다수 사용자에게 보내야함. 뉴스레터에 하이퍼링크될 이미지를 저장할 수 있는 빠르고 확장성 있는 저장소 필요 | S3 Bucket과 S3 Transfer Acceleration을 사용해서 이미지 다운로드 속도 향상
10분마다 Lambda 함수 호출. 트리거하는 자동화된 서비리스 방법 | 정기적인 일정에 따라 Lambda 함수를 호출하는 CloudWatch Event 규칙 생성
이벤트와 Lambda 간의 매핑을 달성하는 방법 | 다른 Lambda 트리거 사용
stg, test, prod에 Lambda 배포. 각 환경에 고유한 리소스 집함이 있는데 현재 환경에 리소스 사용하는 방법 | Lambda 함수에 환경 변수를 사용
Lambda 평균 실행 시간 100초, 초당 50개 요청. 배포 전 해야할 작업 | 동시 실행 제한을 늘리려면 AWS Support에 문의. 기본값은 1000 이기 떄문.
Lambda 코드를 수정하지 않고 DB 연결 문자열을 바꿀 수 있는 방법 | 연결 문자열을 Secrets Manager에 암호로 저장
Lambda 로컬 테스트 성공. 콘솔 테스트 실패. Unable to import module | Lambda 콘솔에서 LB_LIBRARY_PATH 환경을 생성하고 시스템 라이브러리 경로 값 지정
Lambda 직접 배포 크기 초과 | 배포 패키지를 S3에 업로드하고 -code CLI 파라미터를 사용해서 S3를 참조
Lambda의 콜드 스타트로 8초 이상 소요. 개선방법은? | SDK for Java의 필요한 모듈만 포함해서 배포, 할당 메모리 늘림
Lambda 발생하는 주요 이벤트를 기록하고 이벤트 특정 함수 호출과 연결하는 고유 식별자를 포함해야함. | Lambda Context 객체에서 요청 식별자를 얻고 콘솔에 로그를 기록하도록 어플을 설계
Lambda의 기본 설정이고 시간 초과 예외가 발생하면 S3 이벤트는? | 2번 재시도 후 폐기
Lambda는 파일을 추가하기 위해 CodeCommit Repository에 체크인 해야함 | SDK로 CodeCommit Client를 Instance화하고 put_file method를 호출해서 파일을 저장소에 추가
쿼리 문자열 파라미터를 Lambda 함수에 대한 인수로 변환하는 방법 | Mapping Template 생성
Lambda에서 Downstream으로 데이터를 보내기 전에 암호화해야 한다. 암호화 수행하기 위한 API? | KMS GenerateDataKey API
Lambda를 더 많은 CPU 성능으로 테스트 | Lambda에 할당 된 메모리 늘리기
Lambda 함수 로그에서 동일한 요청 ID를 가진 중복 항목이 존재 | Lambda 함수 실패 후 재시도
Lambda 함수는 2개의 DynamoDB에 액세스해야함. 병목 현상을 식별해서 성능을 개선하려고 함. DynamoDB API 호출 타이밍 검사 방법 | DynamoDB를 Lambda 함수에 이벤트 소스로 추가하고 CloudWatch 지표로 성능 확인
Lambda 함수 디버깅 | 실행 역할에 CloudWatch Logs에 쓰기 권한이 있는지 확인, CloudWatch 지표를 사용해서 알림을 생성
Lambda 사용자 지정 라이브러리 사용 방법 | 라이브러리를 로컬에 설치하고 ZIP 파일을 업로드
Lambda 외부 라이브러리 사용 | 코드와 종속 라이브러리를 zip해서 올린다
nodejs 웹 사이트 호스팅. 코드 변경이 없는 솔루션 | Lambda
Lambda 다중 스레드 실행을 활용해서 서능 개선 | Lambda 함수 메모리 증가
로컬에서 Lambda 2번 호출 후 실패. 로깅 방법 | 호출 실패를 조사하도록 CloudTrail 로깅 구성


### -- Kinesis Data Stream
Kinesis Data Stream, `ProvisionedThroyghputExceededException` 발생. 대처방안은? | Data Stream `샤드 수 증가`, Get/PutRecords 호출에 `지수 백오프 구현`
주식 어플. Kinesis로 데이터 수집. 수신 데이터 따라갈 수 없음 | UpdateShardCount를 사용해서 스트림의 샤드 수를 증가
수백만 개의 이벤트 실시간 처리. 동시에 비용 효율적으로 처리 | Kinesis Stream 사용
매시간 대용량 데이터 수집. 메세지는 실시간으로 전달되어야 함 | Kinesis Client Library와 함께 Kinesis Data Stream을 사용해서 메세지 수집 및 전송
Kinesis Data Stream의 샤드 수 4개에서 6개 됨. 데이터 처리를 위한 EC2 인스턴스 최대 수는? | 6
Kinesis Stream 내 데이터를 저장 암호화 방법 | Kinesis Stream 서버 측 암호화 활성화
Kinesis Stream에서 레코드를 가져오기 위해 IAM 액세스 확인하는 방법 | --dry-run 인수를 사용해서 get 작업, IAM 정책 시뮬레이터로 IAM 역할 정책 검증
Kinesis에서 ProvisionedThrouputExceededException 발생. | 지수 백오프로 재시도 구현, 요청의 빈도/크기 줄이기
Kinesis 2500개 레코드 수집을 위한 4개의 샤드. Lambda 함수. 처리하는 순서 | Lambda는 FIFO 방법에 따라 샤드에 배치된 정확한 순서로 각 레코드를 수신. 샤드 간에 순서는 보장하지 않음
Lambda와 Kinesis Data Stream. 반복자 수명 지표가 증가하고 실행 시간이 지속적으로 느림 | Kinesis의 샤드 수 줄이기. Lambda 시간 초과 늘리기.



### --RDS
Mysql RDS. DB 자격 증명이 안전하게 저장되고 액세스 되는지 확인 방법 | 자격 증명을 Secrets Manager에 저장하고 자동 암호 교체를 활성화
어플의 기록을 조회하는 RDS. 기록 데이터 업데이트 많음. 읽기 성능 저하됨 | RDS 읽기 전용 복제본을 만들고 모든 읽기 트래픽을 복제본으로 전송
Aurora MySQL DB가 될 Lambda 함수로 코드를 마이그레이션. DB 기능을 인증하는 방법 | Secret Manager에 DB 자격 증명을 저장하고 필요에 따라 Manager가 자격 증명 교체를 처리



### --X-Ray
API G/W를 사용해서 액세스 하는 온프로미스 Linux 환경. API 테스트 단계에서 X-Ray 추적 활성화. 온프로미스 서버에서 X-Ray 추적 활성화 방안 | 온프로미스 서버에 X-Ray 데몬을 설치하고 실행해서 데이터를 캡처하고 X-Ray 서비스에 전달
X-Ray로 Lambda 기반 어플을 추적 | IAM 실행 역할을 사용하여 Lambda 함수 권한을 부여하고 추적을 활성화
X-Ray 사용 시작 작업 | 어플이 있는 서버에 X-Ray 에이전트 설치, SDK를 사용해서 어플 추적 코드 계측
ECS 환경에서 X-Ray 활성화 방안 | X-Ray 데몬을 실행하는 Docker 이미지 생성, X-Ray용 어플리케이션 코드에 계측을 추가, 작업에 대한 IAM 역할을 구성하고 사용
Lambda로 작성된 분산 어플리케이션 성능 문제 근본 원인을 식별하는 방법 | X-Ray를 사용해서 Segment 및 Error 검사
Java 기반 Lambda 함수. 성능 병목 현상을 격리하기 위한 조치 | X-Ray API를 사용해서 코드 내 전략적 위치에 X-Ray 추적 데이터 기록. X-Ray Console을 사용해서 결과 데이터 분석
X-Ray로 EC2에 있는 어플 모니터링하기 위한 절차 | X-Ray 데몬을 설치하고 어플리케이션 코드를 계측
여러 추적 데이터를 수집하고 시각화하기 위한 솔루션 | AWS X-Ray
MSA 아키텍처에서 중앙 집중식 계정에서 어플의 문제를 분석하고 디버깅하기 원한다. | 역할 수임이 있는 X-ray Agent를 사용해서 중앙 집중식 계정에 데이터를 게시
X-Ray 구성을 하고 어플리케이션에서 X-Ray로 데이터를 보내는걸 확인. EC2에 배포하니까 추적이 안됨. 원인은? | X-Ray 데몬이 EC2에 설치되어 있지 않음, 인스턴스 역할에 xray:PutTraceSegments, xray:PutTelemetryRecords 권한이 없음.
X-Ray에 정보를 제공하도록 코드 변경. 데이터가 많이 생성되서 필터링하기 위한 인덱싱 구현 필요 | 세그먼트 문서 및 코드에 주석 추가

### --성능개선
EC2 `정적 콘텐츠` 때문에 높은 지연 시간 발생. 해결방안은?| 정적 컨텐츠를 캐시할 `CloudFront 배포`, `S3에 정적 컨텐츠 저장`
성능 지연 이유는 모든 페이지에서 DB 프로필 조회가 발생. 캐시 필요하다. 해결방법은? | `ElastiCache Cluster` 생성. `cache-aside caching` 전략 사용
온프로미스 상태 저장 웹 서버를 마이그레이션. 더 큰 탄력성 원함. | DynamoDB에 세션 상태 데이터 자장. ASG이 있는 ELB 사용
온프로미스 DB를 RDS Mysql로 마이그레이션. 읽기가 많은 워크로드. 최적의 읽기 성능 | 읽기 쿼리에 RDS 읽기 전용 복제본을 사용하기 위한 연결 문자열 추가



### --AWS 관련
AWS CLI 사용, `Serverless 시작 단계` | `CloudFormation` Package 사용 후 배포
EC2의 Public/Private `IP 확인 방법`은? | Local Instance `Metadata Query`
민감 데이터 보호. 액세스 추적 필요. | EC2 System Manager Parameter Store에서 IAM으로 Application Access 권한 부여
BGP 기반 VPN으로 EC2 연결. Subnet A는 액세스, B는 액세스 불가. 트래픽이 B에 도달했는지 확인하려면? | VPC 흐름 로그 확인
Access key를 AWS에서 관리하는 방법 | 계정 루트 사용자에 대한 모든 Access key를 삭제, Access key 대신 IAM 역할 사용
EC2 어플. AWS API 호출하도록 구성 | 필요한 권한이 있는 EC2 역할을 지정
특정 IAM 사용자 자격 증명 사용하도록 CLI 구성. 명령어 오류 반환됨. `aws dynamodb get-item --table-name ...` | IAM 사용자는 테이블에 대한 읽기 액세스 권한이 있는 정책이 필요
ECS-Docker. 15초 동안 사용자 로드를 기반으로 확장  |  사용자 활동 데이터에 고해상도 지정. CludWatch 지표 5초마다 데이터 게시
실수로 EC2 인스턴스 종료. 방지하는 대책 | Resource Access Manager에서 EC2 종료 보호 활성화
CLI에서 aws 명령을 찾을 수 없음 | aws 실행 파일이 환경 변수에 없음
각 EC2에 어플, DB 동작. 어플이 DB에 접근하기 위한 비밀키는 변경됨 | SecureString 데이터 유형과 함께 System Manager Parameter Store를 사용해서 비밀키를 저장
AWS 계정을 감사하는 어플리케이션. A 계정에서 실행되서 계정 B,C 서비스에 액세스 필요. 어플리케이션이 각 계정 서비스를 호출하는 방법 | 각 감사 계정에서 교차 계정 역할을 구성하고 해당 역할을 맡는 계정 A에 코드 작성
EC2에 있는 어플이 RDS로 연결. 사용자 ID/PWD를 코드에 저장히기 싫음. 자격 증명 자동 교체 필요 | Secrets Manager를 사용해서 자격 증명을 저장하고 검색
어플에서 사용하는 IAM 자격 증명에는 API 호출에 대한 다단계 인증이 필요하다. 다단계 인증 보호 API에 액세스하는데 사용하는 방법 | GetSessionToken
Admin 그룹의 한 사용자에게만 EC2 리소스 삭제 권한이 있는지 확인하려고 한다. | 인라인 정책 사용
컨테이너 어플을 ECS Fargate에 배포. 세션 데이터로 활동 추적 | NLB에 Sticky Session 활성화하고 컨테이너에서 세션 데이터 관리
ECS 기반 Fargate에 배포. 어플을 초기화하기 위해 컨테이너에 전달해야 하는 환경변수가 있다. 어떻게 전달할건가? | 작업 정의 내 환경 매개변수 아래에 환경 변수를 포함하는 배열을 정의
MongoDB 마이그레이션 호스팅을 위한 솔루션 | MongoDB 호환 모드에서 AWS DocumentDB를 배포
CloudFront로 글로벌 사용자 기반 어플을 새로 배포. 고객들이 새로 배포된걸 볼 수 없음 | Edge Cache에서 모든 응용 프로그램 개체를 무효화
ECS 환경에서 컨테이너 워크로드를 실행. 로그 및 메트릭 수집을 위해 다른 컨테이너와 공유 필요 | 하나의 작업 정의를 만들어서 정의에서 두 컨테이너 모두 지정하고 컨테이너 간 공유 볼륨을 탑재
ASG 이벤트에 대한 최상의 메트릭은 동시 사용자 수 라고 결정했다. 동시 사용자를 기반으로 자동 크기 조정을 위해 무엇을 활용해야하나? | 동시 사용자를 위한 사용자 지정 CloudWatch 지표
개발자는 2개의 리소스에 임시 액세스 | 교차 계정 액세스 역할을 만들고 sts:AssumeRole API를 사용해서 단기 자격 증명을 얻는다
DynamoDB, Docker 기반 어플. 로컬에서 IAM 액세스 키로 테스트. ECS에 배포했을 때 어떻게 인증? | 어플이 사용할 ECS 작업 IAM 역할 구성
EC2 어플리케이션에서 사용하는 IAM 자격 증명이 오용되거나 손상되었는지 확인. 자격 증명을 안전하게 유지하기 위해 사용해야 하는 것 | 인스턴스 프로필 자격 증명
IAM 역할은 S3 API 작업에 대한 액세스를 거부. EC2 인스턴스 자격 증명 파일은 전체 관리 액세스를 허용하는 IAM 액세스 키와 보안 액세시 키를 지정 | EC2 인스턴스는 모든 S3 버킷에서 모든 작업을 수행 가능
API 호출에서 일부 액세스 거부 뜸 | 필요한 권한을 추가해서 연결된 IAM 업데이트
EC2 인스턴스는 AMI에서 시작된다. 지정된 공개 AMI이 수행할 수 있는 것 | AMI가 저장된 동일한 AWS Region에서 EC2 인스턴스를 시작하는데 사용
SLA(서비스 수준 계약)을 약속하고 각 SLA를 준수하기 위해 해야할 것 | 각 사용자에 대한 사용 계획을 만들고 API에 액세스 할 수 있는 API 키를 요청
개발자 로컬 CLI IAM 권한 사용 방법 | aws configure CLI 명령을 실행하고 IAM 액세스 키와 보안 액세스 키 제공
IAM 정책 평가 로직에 대한 설명 | 기본적으로 모든 요청은 거부됨, 명시적 허용은 기본 거부를 재정의함.
각 개발자는 로컬 개발 | 각 개발자에 대한 IAM 사용자를 만들고 고유한 액세스 키를 제공
Example Corp AWS 계정에 액세스를 허용하는 안전한 방법 | Example Corp 계정에서 사용자 생성 및 액세스 키 제공
주말 동안 트래픽 급증, 주중에는 예측 가능한 급증. 항상 조절 오류를 방지하려면? | 일주일 내내 ASG로 Provisioning 된 용량 사용
EC2 안에 어플이 S3 버킷에 쓰기 기능 추가 | EC2 인스턴스 프로파일 역할에 IAM 정책
EC2에 있는 어플리케이션이 AWS 서비스에 액세스하고 API 호출 | EC2 프로파일 사용
실시간 처리 | Event Driven

### --기본개념
`LAMP`를 실행할 수 있는 AWS 서비스는? | `EC2`, `Aurora`
AWS SDK 기본 리전 | us-east-1
EC2 인스턴스의 IPv4 주소 찾기 | 169.254.169.254/lastest/metadata/ 를 검색
단일 인터페이스 필요 | API Gateway
AWS에서 추가 비용 없이 포함되는 서비스 | Auto Scaling, CloudFormation
AMI(Amazon Machine Image) 목록을 검색할 때 사용하는 EC2 API | DescribeImages
CLI 명령 후 에러 메세지가 암호화 되어 있다. | STS decode-authorization-message API로 디코딩
SWF의 설명 | 작업은 한 번 할당되고 절대 중복되지 않는다. workflow 실행은 최대 1년동안 지속. 결정자와 작업자를 사용해서 작업을 완료
CLI에서 Resource List 명령 때 실행시간 초과 | Pagenation 사용
ECS 컨테이너 시작할 때 PortMapping은 어디에서 정의 | Task definition
AWS에서 보안에 대한 고객의 책임 | IAM 자격 증명의 수명 주기 관리, 보안 그룹 및 ACL 설정, EBS 볼륨의 암호화, EC2 운영체제의 패치 관리
모놀리식 아키텍처를 마이크로서비스 아키텍처로 변경하고 성능에 영향을 주지 않으면서 비동기식 통신할 수 있도록 해야 한다. 비동기식 메시지 전달이 가능한 서비스는? | SQS, SNS
높은 처리량으로 데이터 수집하고 S3 버킷에 저장해야함. 적합한 서비스는? | Kinesis Firehouse
HTML, Image, Video, Javascript, Serverless | S3, CloudFront
SDK가 있는 언어 | Java, C#, Ruby, Python, JavaScript, PHP, and Objective C (iOS)
EC2, S3. 트래픽 증가 성능 저하 | CloudFront를 사용해서 S3에 저장된 이미지 콘텐츠 제공
Key-Value 저장소는? | ElastiCache, DynamoDB, S3
CloudFront 사용해서 웹 어플 구성. 종단 간 모든 트래픽을 암호화해야 함 | Origin Protocol Policy를 HTTPS Only로 설정, HTTPS 전용 또는 HTTP를 HTTPS로 Redirection 설정
Tomcat 서버에 빠르게 배포 | Elastic Beanstalk
고객은 새로운 Restful API를 요청함. 가능한 구성 요소는? | ELB + EC2, API G/W + Lambda
AWS Infra를 Code로 관리하고 배포할 수 있으며 이전 버전으로 돌릴 수 있어야 한다. | CloudFormation, CodeCommit 사용
React, 자신이 소유한 파일 저장, 검색 허용. facebook 사용. 개발 및 배포 가속화하는 CLI | Amplify CLI

### --아키텍처 구성
EC2 10개로 구성. 하나의 Metric 표현 필요. CloudWatch를 사용해서 해결하는 방법은? | 각 응용 프로그램에 대한 `고유한 메트릭 이름을 사용`하고 `사용자 지정 네임스페이스를 생성`
EC2 4개로 구성. 각 고유한 권한. 메모리 예약 기반 컨테이너. 구성방안은? | 각 ECS에 권한을 포함한 4개의 `고유한 IAM 생성` 후 `IAM 역할 참조` 하도록 ECS 작업 정의 구성
30분 걸리는 사기 탐지 솔루션을 어플리케이션에 추가하는 방법은? | 주문을 `SQS 대기열에 추가`하고 `Auto Scaling 그룹 구성`하고 대기열에서 주문을 가져오기 위해 사기 탐지 솔루션이 설치된 `AZ에 EC2 집합 설정`
최종 사용자에게 보낼 수 있는 양 제한하고 경영진은 더 큰 패키지 구매 옵션 제공 | `기본 사용 계획 설정`. `각 단계를 설정`. 더 큰 패키지를 선택하면 적절한 값으로 `사용자 지정 계획을 만들고 사용자와 연결`
VPC 내 배포되는 웹 어플리케이션. 온프로미스 LDAP 서버에 인증을 해야함. 인증 후 로그인한 사용자는 관련된 S3 Key Space에만 액세스할 수 있도록 해야한다. | LDAP에 대한 인증 후 사용자와 연결된 IAM 역할의 이름을 검색하고 IAM 보안 토큰 서비스를 호출해서 해당 IAM 역할을 수임한다. 어플은 임시 자격 증명을 사용해서 S3에 액세스, LDAP에 대한 인증 후 IAM 보안 토큰 서비스를 호출해서 IAM 연동 사용자 자격 증명을 가져오는 자격 증명 브로커를 개발하고 어플은 자격 증명 브로커를 호출해서 S3에 대한 액세스 권한이 있는 IAM 연동 사용자 자격 증명을 가여온다.
온프로미스를 마이그레이션. 사용자가 업로드한거 서버의 로컬 경로에 저장하고 ASG의 모든 인스턴스가 바로 사용 가능해야함 | S3를 사용하고 모든 업로드를 S3에 저장하도록 어플 설계



