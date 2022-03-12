kubernetes
===

목차
---
- [학습목표](#학습목표)
- [Why Container Orchestration](#Why-Container-Orchestration)
- [Kubernetes](#What-Kubernetes)
- [실습환경 세팅하기](#실습환경-세팅하기)

---

## 학습목표
Docker를 공부했던 내용을 기반으로 K8S의 개념과 기능을 공부한다. 실습 위주로 공부하고 최종적으로 AWS 환경에서 Kubernetes를 설정하고 배포한다.

---

## Why Container Orchestration
- `서버를 문서로 관리`
- `서버 설정을 Code로 관리` → 이미 설치된 프로그램과 충돌이 나고 관련 Code를 따로 배워야하는 진입장벽 존재
- `Virtual Machine 사용` → Cloud 환경에 맞지 않음. 특정 Vendor에 종속적임.
- `Docker`
  - 많은 Container를 하나하나 관리하고 Rollout, Rollback 하기엔 어려움이 있음.
  - 서비스 검색이 어려움.
  - 부하에 따른 Container 수를 관리하거나 소수의 Container가 죽었을 때 자동으로 살리는 기능 등 Container 관리를 자동으로 하고 싶어짐.
- `Container Orchestration` : Kubernetes, Swarm, ...

## What Kubernetes
- #### Containter를 쉽고 빠르게 배포/확장하고 관리를 자동화해주는 Open Source Platform
- #### Kubernetes의 장점
  - Planet Scale
  - Never Outgrow
  - Run Anywhere
  - Open Source
  - Masive Popularity
  - Infinite Expandability
  - De Facto

- #### Docker와 Kubernetes를 활용한 관리
![image](https://user-images.githubusercontent.com/21374902/157634817-812cd265-0ad8-41ae-94f0-d800ec938d0d.png)

- #### Desired State
  - 현재 상태와 원하는 상태를 비교하고 미리 설정해둔 상태로 복원시켜주고 지속적으로 관리해주는 것
![image](https://user-images.githubusercontent.com/21374902/157641975-55f68ae6-923a-489d-acb7-70d012ba535e.png)
  - Scheduler로 통해 일정 주기로 상태를 체크하고 각 Controller를 생성해서 관리할 항목을 나눠서 제어할 수 있다.
![image](https://user-images.githubusercontent.com/21374902/157642820-5578c4e1-8e84-45c6-8fd4-e67b05bbdd02.png)
- #### Kubernetes 구성 요소
  - ###### Master 구성 요소
    - etcd
      - 모든 상태와 데이터를 저장하는 요소
      - 분산 시스템으로 안정성을 높이고 (고가용성) 가볍고 빠르게 동작 (일관성)
      - Key-Value 형태로 데이터를 저장
      - TTL (Time to live), Watch 등 부가 기능 제공
    - API Server
      - 상태를 바꾸거나 조회할 때 유일하게 etcd와 통신하는 모듈
      - Restful API 형태로 동작
      - 권한을 체크하고 권한이 없을 경우 차단
      - 다양한 내부 모듈 중간에서 통신하는 역할
      - 수평으로 확장되도록 디자인
    - Scheduler
      - 새로 생성된 Pod를 감지하고 실행할 Node를 선택
      - Node의 현재 상태와 Pod의 요구 사항을 체크
    - Controller
      - 끊임 없이 상태를 체크하고 정상 상태를 유지
      - 복잡성을 낮추기 위해 하나의 프로세스로 실행되고 목적마다 다양한 Controller가 있을 수 있음.
  - ###### Master 조회 흐름
    - Controller ➡ API Server : 리소스 정보 조회 요청
    - API Server : 리소스 정보 조회 권한 체크 
    - API Server ➡ etcd : etcd 정보 조회
  - ###### Master 기본 흐름
    - etcd ➡ API Server : 원하는 상태로 변경 됐다고 전달
    - API Server ➡ Controller : 원하는 상태로 변경 됐다고 전달
    - Controller : 원하는 상태로 리소스 변경
    - Controller 🔃 API Server : 변경 사항 전달
    - API Server : 리소스 정보 갱신 권한 체크
    - API Server 🔃 etcd : 정보 갱신
  - ###### Node 구성 요소
    - Kubelet
      - 각 노드에서 실행되고 컨테이너를 관리함.
      - Pod를 실행/중지하고 Pod의 상태를 체크
      - CRI (Container Runtime Interface)
    - Proxy
      - 내/외부 통신을 설정하고 네트워크 Proxy와 부하 분산 역할
      - 지금은 성능상의 이유로 별도의 별도의 Proxy 프로그램을 띄우지 않고 Kernel 단에서 iptables/IPVS를 사용해서 동작하도록 함.
      - Proxy는 설정만 관리
    ![image](https://user-images.githubusercontent.com/21374902/157651756-dd4c3d61-d674-4fd6-9dd9-fa616d1caa0c.png)

- #### 하나의 Pod가 생성되는 과정
  ![image](https://user-images.githubusercontent.com/21374902/157654094-02033c94-0d41-4d18-925a-123077f4d51a.png)

- #### Kubernetes Object
  - ###### Pod
    - 가장 작은 배포 단위이며 Pod마다 고유한 IP를 부여 받음
    - 여러개의 Container를 갖을 수 있음
    - 하나의 Pod에 Container + Cache를 넣고 local port로 공유할 수 있음
  - ###### ReplicaSet
    - 설정한 환경에 맞는 Pod의 상태와 수를 관리 
  - ###### Deployment
    - ReplicaSet의 상위 개념으로 ReplicaSet을 이용해 배포하고 Version 업그레이드
  - ###### Workload
    - DAEMON SET : 모든 Node에 반드시 1개씩만 떠있는 Pod (로그, 모니터링)
    - STATEFUL SETS : 순서대로 수행하거나 같은 볼륨을 재활용하고 싶을 때 
    - JOB : 한번 실행하고 죽음
  - ###### Cluster IP
    - Pod는 동적으로 변하기 때문에 `Service`에 `Cluster IP`를 붙여서 사용
    - 하지만 Cluster IP는 내부에서만 접근이 가능하기 때문에 `Node`에 `NodePort`를 만들고 `외부에서 접근할 수 있도록 함`
    - 다른 Node에 할당된 NodePort로 연결해도 `자동으로 지정된 Service로 연결해줌.`
    - 두번째 그림에서 Node1이 죽으면 Service가 정상적으로 되지 않기 때문에 `NodePort 앞단에 Load Balancer를 둠.`
    - 외부엔 `하나의 IP (Load Balancer)를 노출`
      ![image](https://user-images.githubusercontent.com/21374902/157827744-a3aaceb3-61ed-4857-9e6b-d5ba75dec19e.png)
      ![image](https://user-images.githubusercontent.com/21374902/157827906-7e2c903f-bc5a-4fc3-a185-ab2117f758b5.png)
      ![image](https://user-images.githubusercontent.com/21374902/157829327-7b90c7ed-0b6f-4b7e-8c55-0aa23f6fe26c.png)
  - ###### Ingress
    - Domain 또는 경로별로 라우팅 해줌
      ![image](https://user-images.githubusercontent.com/21374902/157829810-1af8eeba-3202-4425-b7c3-0edd55aa5e7d.png)
  - ###### 일반적인 구성
    ![image](https://user-images.githubusercontent.com/21374902/157829970-ac03a92d-fe0d-40ef-8acd-80da6e846867.png)
---

## 실습환경 세팅하기
_Kubernetes Adminstrator_ 교육을 들었을 땐 AWS Cloud9에서 1개의 Master, 2개의 Worker 환경을 별도로 제공받아서 실습했었습니다. 하지만 Local 환경에선 n개의 환경을 각각 구축하기 까다롭기 때문에 `minikube`을 사용해서 구성하도록 하겠습니다.

Kubernetes Cluster를 실행하려면 최소한 scheduler, controller, api-server, etcd, kubelet, kube-proxy를 설치해야 하고 필요에 따라 dns, ingress controller, storage class등을 설치해야 합니다. 실습에선 `minukube`로 대체합니다.

- ### minukube & kubectl 설치
  - Kubernetes를 운영환경에 설치하기 위해선 최소 3대의 Master와 Container 배포를 위한 n개의 Node 서버가 필요하지만 실습(개발환경)에선 minikube를 사용
  - 개발환경은 1개의 Node만 사용하기 때문에 Node가 여러개 일 떄 Scheduling하는 테스트가 어렵고 Load Balancer와 Persistent Local Storage를 가상으로 만들어야 합니다
  - #### ~~Windows 10에 설치~~  
    - ~~Hyper-V 활성화~~
      - ~~Check : `DISM /Online /Enable-Feature /All /FeatureName:Microsoft-Hyper-V`~~
      - ~~On : `bcdedit /set hypervisorlaunchtype off`~~
      - ~~Off : `bcdedit /set hypervisorlaunchtype auto`~~
    - ~~minikube 설치 : [minikube-installer.exe](https://github.com/kubernetes/minikube/releases/latest/download/minikube-installer.exe)~~
    - ~~💥memory 할당 문제로 `minikube start --driver=hyperv`가 안될 경우, 가상 메모리 설정 필요~~
      - ~~제어판 > 시스템 및 보안 > 시스템 > 고급 시스템 설정~~
      - ~~고급 탭 > '성능' 영역에 '설정(S)' > 고급 탭 > '가상 메모리' 영역에 '변경(C)'~~
      - ~~'모든 드라이브에 대한 페이징 파일 크기 자동 관리(A)' 체크 해제 > '사용자 지정 크기(C)' 선택 > 처음 크기 : 4096, 최대 크기 : 8192 > 설정 > 확인 > 재부팅~~ 
        ![image](https://user-images.githubusercontent.com/21374902/157142064-ccdc512f-d2d5-4c29-8ece-1414734761a2.png)

  - #### 💥 Docker Desktop을 사용할 수 없기 때문에 WSL2 환경에 세팅
    - 참고 : [Docker Desktop 없이 Docker 사용하기](https://github.com/justdoanything/self-study/blob/main/self-study/Docker.md#2%EF%B8%8F%E2%83%A30%EF%B8%8F%E2%83%A3-Docker-Desktop-%EC%97%86%EC%9D%B4-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0-(Windows10))  
    - minikube 설치 및 실행
      - `curl –LO https://storage.googleapis.com/minikube/releases/latest/minikube_latest_amd64.deb`
      - `sudo dpkg –i minikube_latest_amd64.deb`
      - `minikube start --driver=docker`
      - `minikube kubectl`
    - kubectl 설치
      - `curl -LO "https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl"`
      - `chmod +x ./kubectl`
      - `sudo mv ./kubectl /usr/local/bin/kubectl`
    - minikube 명령어
      ```sh
      # 버전확인
      minikube version

      # 가상머신 시작 (반드시 관리자권한으로 실행)
      minikube start --driver=hyperv
      
      # driver 에러가 발생한다면 virtual box를 사용
      minikube start --driver=virtualbox
      
      # 특정 k8s 버전 실행
      minikube start --kubernetes-version=v1.20.0

      # 상태확인
      minikube status

      # 정지
      minikube stop

      # 삭제
      minikube delete

      # ssh 접속
      minikube ssh

      # ip 확인
      minikube ip

      # 두번째 가상머신 시작
      minikube start -p hellowlrd # helloworld 라는 이름의 profile로 생성

      # profile 목록 확인
      minikube profile list

      # 현재 사용중인 profile 확인
      minikube profile

      # 다른 profile로 변경
      minikube profile hellowlrd # helloworld로 변경
      minikube profile minikube # minikube로 변경

      # 가상머신 제거
      minikube delete # 현재 사용중인 profile의 가상머신 제거
      ```
  
  - 정상 구동 화면\
    ![image](https://user-images.githubusercontent.com/21374902/157167987-36ab1b4e-bad0-4355-ac44-5faedd0b30d6.png)
- 무작정 따라해보기 - wordpress 실행하기
  - wordpress-k8s.yml 작성
    ![image](https://user-images.githubusercontent.com/21374902/157173397-bcf2a579-9f5b-48a6-bbce-de732ae857a2.png)
    ```yml
    apiVersion: apps/v1
    kind: Deployment
    metadata:
      name: wordpress-mysql
      labels:
        app: wordpress
    spec:
      selector:
        matchLabels:
          app: wordpress
          tier: mysql
      template:
        metadata:
          labels:
            app: wordpress
            tier: mysql
        spec:
          containers:
            - image: mariadb:10.7
              name: mysql
              env:
                - name: MYSQL_ROOT_PASSWORD
                  value: password
              ports:
                - containerPort: 3306
                  name: mysql

      ---
      apiVersion: v1
      kind: Service
      metadata:
        name: wordpress-mysql
        labels:
          app: wordpress
      spec:
        ports:
          - port: 3306
        selector:
          app: wordpress
          tier: mysql

      ---
      apiVersion: apps/v1
      kind: Deployment
      metadata:
        name: wordpress
        labels:
          app: wordpress
      spec:
        selector:
          matchLabels:
            app: wordpress
            tier: frontend
        template:
          metadata:
            labels:
              app: wordpress
              tier: frontend
          spec:
            containers:
              - image: wordpress:5.5.3-apache
                name: wordpress
                env:
                  - name: WORDPRESS_DB_HOST
                    value: wordpress-mysql
                  - name: WORDPRESS_DB_PASSWORD
                    value: password
                ports:
                  - containerPort: 80
                    name: wordpress

      ---
      apiVersion: v1
      kind: Service
      metadata:
        name: wordpress
        labels:
          app: wordpress
      spec:
        type: NodePort
        ports:
          - port: 80
        selector:
          app: wordpress
          tier: frontend
      ```
    
  - docker-compose.yml 버전 참고
    ![image](https://user-images.githubusercontent.com/21374902/157173260-bbbe2ee7-3b5d-4033-89b0-0d9458a7818b.png)
    ```yml
    version: "3"

    services:
      wordpress:
        image: wordpress:5.5.3-apache
        environment:
          WORDPRESS_DB_HOST: mysql
          WORDPRESS_DB_PASSWORD: password
        ports:
          - "30000:80"

      mysql:
        image: mariadb:10.7
        environment:
          MYSQL_ROOT_PASSWORD: password
    ```
    
  - wordpress-k8s.yml 실행 : `kubectl apply -f wordpress-k8s.yml`
  - Terminal을 추가로 열어서 Monitoring 실행 : `watch -n 0.5 kubectl get all`
    - Status = Running 확인
      ![image](https://user-images.githubusercontent.com/21374902/157172750-93658332-9176-4cee-8d1b-f18652f16e35.png)
    - 실행한 wordpress 확인
      - `minikube ip`로 IP 확인
      - `kubectl get all`에서 service/wordpress의 PORT 확인 : 31564
      - ### 💥 Browser로 접속하면 접근 불가
        - WSL 내부에서 올렸기 때문에 PC Browser에서 바로 접근 불가
        - WSL의 방화벽을 뚫거나 Port Forwarding을 해야하는데 방법이 어려워서 Ubuntu GUI를 사용해 WSL에서 WEb Browser를 열고 테스트
        - 
  - wordpress 리소스 제거 : `kubectl delete -f wordpress-k8s.yml`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                



---   

강사 : 장원석
강의이름 : Kubernetes Adminstrator

https://github.com/wsjang619/k8s_course

watch -n 0.5 kubectl get all

---   

인프런





---   
- Reference
  - [subicura 블로그](https://subicura.com/k8s)
  - [Inflearn - 쿠버네티스-입문](https://www.inflearn.com/course/%EC%BF%A0%EB%B2%84%EB%84%A4%ED%8B%B0%EC%8A%A4-%EC%9E%85%EB%AC%B8)
  - [github - k8s_course](https://github.com/wsjang619/k8s_course)