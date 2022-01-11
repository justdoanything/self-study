DevOps
===
DevOps : Development + Operations
5가지 철학 : 문화, 자동화, 측정, 공유, 축적
soft skill : 문제인식, 선택과 집중, 결정, 업의 속성, 사용자
technical skill : 프로그래밍, 운영체제, 서버관리, 오픈소스, 클라우드

## IaC
- IaC (Infrastructure as Code)
→ Infra를 이루는 서버, 미들웨어, 서비스 등 Infra 구성요소들을 코드를 통해 구축하는 것
- Terraform
  - 구성요소 : provider, resource, state, output, module, remote
  - 명령어 : init, plan, apply, import, state, destroy
- AWS EC2 (Amazon Elastic Compute Cloud)
  - 크기 조정이 가능한 컴퓨텅 용량을 클라우드에서 제공하는 웹 서비스
  - EC2의 Public IP/22로 SSH 연결
  - VPC 안에 EC2가 있다면 Security Group을 확인할 것.
  - AMI : 주로 OS를 뜻함
  - chmod 600 {pem key} : key 파일의 권한이 다 오픈되어 있으면 에러 뱉기 때문에
  - ssh -i {pem key} {id}@{EC2 public IP}
  - open port 확인 : netstat -lntp
- Zsh 및 Oh-my-zsh 설치
  - EC2 Instance에 접속해서 Zsh 설치
    sudo yum install zsh
   - chsh 명령어를 사용하기 위한 util 설치
     - sudo yum install util-linux-user.x86_64
   - 기본 Shell 프로그램을 zsh로 설정
     - chsh
     - /bin/zsh
  - Oh-my-zsh 설치
- AWS Credentials
  - IAM 설정 : Terraform도 AWS API를 호출하는 형태이기 때문에 설정을 해줘야 한다.
  - ECS2 내에서 aws configure 명령어 실행
  - AWS Console에 있는 Access ID/KEY 설정
- Terraform 작동원리
  - Local 코드 : 현재 개발자가 작성/수정하는 코드
  - AWS 실제 인프라 : AWS에 배포되있는 인프라
  - Backend에 저장된 상태 : 가장 최근에 배포한 테라폼 코드
  - `AWS 실제 인프라`와 `Backend에 저장된 상태`는 100% 일치해야 함
  - `provider.tf` 작성
    terraform init
    `s3.tf` (Resource) 작성
    terraform plan # build랑 비슷한 느낌
    terraform apply # 실제 적용. deploy랑 비슷한 느낌
    terraform import # AWS 인프라에 배포된 리소스를 가져옴. pull이랑 비슷한 느낌
- AWS VPC
  → Amazon에서 제공하는 Private 네트워크망
  - VPC (Virtual Private Cloud)
  - Subnet
    - Private Subnet : 연결되 있는 Routing Table의 Outbound가 `NAT Gateway`
    - Public Subnet : 연결되 있는 Routing Table의 Outbound가 `Internet Gateway`
  - Routing Table
  - Internet Gateway
  - NAT Gateway
  - Security Group
  - VPC End-point
- ### AWS VPC 구성 예제
  - terraform init
  - `resource.tf` 작성
    > provider "aws" {
    　region = "ap-northeast-2"
    }
    　
    // (1) VPC 생성
    resource "aws_vpc" "main" {
    　cidr_block = "10.0.0.0/16"  // cidr_block은 필수값
    　tags = {
    　　Name = "terraform-101"
    　}
    }
    　
    // (2) public subnet 생성 //
    resource "aws_subnet" "first_public_subnet" {
    　vpc_id = aws_vpc.main.id  // 위에서 aws_vpc는 main으로 alias
    　cidr_block = "10.0.1.0/24"
    　availability_zone = "ap-northeast-2a"
    　tage = {
    　　Name = "101subnet-public-1"
    　}
    }
    　
    // (2) public subnet 생성 //
    resource "aws_subnet" "second_public_subnet" {
    　vpc_id = aws_vpc.main.id
    　cidr_block = "10.0.2.0/24"
    　availability_zone = "ap-northeast-2b"
    　tags = {
    　　Name = "101subnet-public-2"
    　}
    }
    　
    // (2) private subnet 생성 //
    resource "aws_subnet" "first_private_subnet" {
    　vpc_id = aws_vpc.main.id
    　cidr_block = "10.0.3.0/24"
    　availability_zone = "ap-northeash-2a"
    　tags = {
    　　Name = "101subnet-private-1"
    　}
    }
    　
    // (2) private subnet 생성 //
    resource "aws_subnet" "second_private_subnet" {
    　vpc_id = aws_vpc.main.id
    　cidr_block = "10.0.4.0/24"
    　availability_zone = "ap.northeast-2b"
    　tags = {
    　　Name = "101subnet_private-2"
    　}
    }
  - terraform plan
  - terraform apply
- ### NAT G/W와 Internet G/W
![image](https://user-images.githubusercontent.com/21374902/148865682-993fd4d1-cb92-4216-8090-d08270804de6.png)
>_퍼블릭 서브넷의 인스턴스는 인터넷에 바로 아웃바운드 트래픽을 전송할 수 있는 반면, 프라이빗 서브넷의 인스턴스는 그렇게 할 수 없습니다. 반면, 프라이빗 서브넷의 인스턴스는 퍼블릭 서브넷에 있는 NAT(Network Address Translation) 게이트웨이를 사용하여 인터넷에 액세스할 수 있습니다. 소프트웨어 업데이트 시 NAT 게이트웨이를 사용하여 데이터베이스 서버를 인터넷에 연결할 수 있지만, 인터넷에서 데이터베이스 서버 연결을 설정할 수 없습니다. written by AWS Docs (https://docs.aws.amazon.com/ko_kr/vpc/latest/userguide/VPC_Scenario2.html)_
  - ###### - `resource.tf`에 추가 작성
      >// (3) Private Subnet을 위한 eip 생성 //
      resource "aws_eip" "eip_first" {  // eip : Elastic IP (고정 IP)
      　vpc = true
      　lifecycle {
      　　create_before_destroy = true
      　}
      }
      　
      // (3) Private Subnet을 위한 eip 생성 //
      resource "aws_eip" "eip_second" {  // eip : Elastic IP (고정 IP)
      　vpc = true
      　lifecycle {
      　　create_before_destroy = true
      　}
      }
      　
      // (4) NAT Gateway 생성하고 eip와 Public subnet를 연결 //
      resource "aws_nat_gateway" "nat_gateway_first_public" {
      　allocation_id = aws_eip.eip_first.id
      　subnet_id = aws_subnet.first_public_subnet.id
      　tags = {
      　　Name = "nat-gw-first"
      　}
      }
      　
      // (4) NAT Gateway 생성하고 eip와 Public subnet를 연결 //
      resource "aws_nat_gateway" "nat_gateway_second_public" {
      　allocation_id = aws_eip.eip_second.id
      　subnet_id = aws_subnet.second_public_subnet.id
      　tags = {
      　　Name = "nat-gw-second"
      　}
      }
    - terraform plan
    - terraform apply
- ### Route Table
  - `resource.tf`에 추가 작성
    > // (5) Public 전용 Route Table 생성 //
    resource "aws_route_table" "route_table_public" {
    　vpc_id = aws_vpc.main.id
    　tags = {
    　　Name = "route_table_public"
    　}
    }
    　
    // (6) Route Table과 Public Subnet 연결
    resource "aws_route_table_association" "route_ass_public_first" {
    　subnet_id = aws_subnet.first_public_subnet.id
    　route_table_id = aws_route_table.route_table_public.id
    }
    　
    // (6) Route Table과 Public Subnet 연결
    resource "aws_route_table_association" "route_ass_second_first" {
    　subnet_id      = aws_subnet.first_second_subnet.id
    　route_table_id = aws_route_table.route_table_public.id
    }
    　
    　
    　
    　
    // (7) Private 전용 Route Table 생성 //
    resource "aws_route_table" "route_table_private_first" {
    　vpc_id = aws_vpc.main.id
    　tags = {
    　　Name = "route_table_private_first"
    　}
    }
    　
    // (7) Private 전용 Route Table 생성 //
    resource "aws_route_table" "route_table_private_second" {
    　vpc_id = aws_vpc.main.id
    　tags = {
    　　Name = "route_table_private_second"
    　}
    }
    　
    // (8) Route Table에 Private Subnet 연결
    resource "aws_route_table_association" "route_ass_private_first" {
    　subnet_id = aws_subnet.first_private_subnet.id
    　route_table_id = aws_route_table.route_table_private_first.id
    }
    　
    // (8) Route Table에 Private Subnet 연결
    resource "aws_route_table_association" "route_ass_private_second" {
    　subnet_id = aws_subnet.second_private_subnet.id
    　route_table_id = aws_route_table.route_table_private_second.id
    }
    　
    // (9) Route Table과 NAT Gateway 연결
    resource "aws_route" "private_nat_first" {
    　route_table_id = aws_route_table.route_table_private_first.id
    　destination_cidr_block = "0.0.0.0/0"
    　nat_gateway_id = aws_nat_gateway.nat_gateway_first_public.id
    }
    　
    // (9) Route Table과 NAT Gateway 연결
    resource "aws_route" "private_nat_second" {
    　route_table_id = aws_route_table.route_table_private_second.id
    　destination_cidr_block = "0.0.0.0/0"
    　nat_gateway_id = aws_nat_gateway.nat_gateway_second_public.id
    }
    　
    // (10) Gateway 생성 //
    resource "aws_internet_gateway" "igw" {
    　vpc_id = aws_vpc.main.id
    　tags = {
    　　Name = "internet-gw"
    　}
    }

- ### 요약
![image](https://user-images.githubusercontent.com/21374902/148888522-8d90efb9-5f42-4683-8c9f-65c8f0a6f646.png)
```
1. VPC 생성
2. Subnet 생성 (Public 전용, Private 전용)
3. eip 생성
4. NAT Gateway 생성 및 eip, Public Subnet 연결
5. Public 전용 Route Table 생성
6. Public Subnet과 Route Table 연결
7. Private 전용 Route Table 생성
8. Private Subnet이랑 Private Route Table 연결
9. Private Route Table이랑 NAT Gateway 연결
10. Internet Gateway 생성
```

- Reference
  - DevOps : Infrastructure as Code with 테라폼(Terraform) and AWS 초급, 입문편 by [Inflearn](https://www.inflearn.com/)
  - [Terraform & AWS 101](https://terraform101.inflearn.devopsart.dev/)
---