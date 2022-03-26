kubernetes
===

목차
---
- [학습목표](#학습목표)
- [Why Container Orchestration](#Why-Container-Orchestration)
- [Kubernetes](#What-Kubernetes)
- [Kubernetes 구성 요소](#Kubernetes-구성-요소)
- [Kubernetes Object](Kubernetes-Object)
- [실습환경 세팅하기](#실습환경-세팅하기)
- [무작정 따라해보기](#무작정-따라해보기)
- [Kubernetes 명령어](#Kubernetes-명령어)
- [Pod 배포하기](#Pod-배포하기)
- [Container 상태 모니터링](#Container-상태-모니터링)
- [다중 Container 자원 공유](#다중-Container-자원-공유)
- [ReplicaSet](#ReplicaSet)
- [Deployment](#Deployment)
- [Service](#Service)
- [Ingress](#Ingress)
- [Volume](#Volume)
- [ConfigMap](#ConfigMap)
- [Secret](#Secret)
- [DaemonSet](#DaemonSet)
- [Job](#Job)
- [CronJob](#Cronjob)
- [Persistent Volume](#Persistent-Volume)
- [StatefulSet](#StatefulSet)
- [ResourceQuota](#ResourceQuota)
- [LimitRange](#LimitRange)
- [기타 명령어](#기타-명령어)


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
- `Container Orchestration` : Kubernetes, Swarm, ...\
  _(Orchestration : Computer System, Application, Service의 자동화된 설정, 관리, 조정 하는 것)_



---



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



---



## Kubernetes 구성 요소
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



---



## Kubernetes Object
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
  - ###### Namespace
    - 동일한 물리 Cluster에서 가상 Cluster를 나눠 지원하는 Object
    - Namespace가 다르면 같음 이름의 Object가 존재할 수 있다.
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
    <details>
      <summary> 📑 Windows10 (Hyper-V)</summary>
      
      - Hyper-V 활성화
        - Check : `DISM /Online /Enable-Feature /All /FeatureName:Microsoft-Hyper-V`
        - On : `bcdedit /set hypervisorlaunchtype off`
        - Off : `bcdedit /set hypervisorlaunchtype auto`
      - 설치
        - minikube 설치 : [minikube-installer.exe](https://github.com/kubernetes/minikube/releases/latest/download/minikube-installer.exe)
        - Kubernetes 설치 : `curl -LO https://storage.googleapis.com/kubernetes-release/release/v1.20.0/bin/windows/amd64/kubectl.exe`
      - 실행
        - docker 기반 실행 : `minikube start --driver=docker`
        - hyperv 기반 실행 : `minikube start --driver=hyperv`
        - 💥memory 할당 문제로 안될 경우, 가상 메모리 설정 필요
          - 제어판 > 시스템 및 보안 > 시스템 > 고급 시스템 설정
          - 고급 탭 > '성능' 영역에 '설정(S)' > 고급 탭 > '가상 메모리' 영역에 '변경(C)'
          - '모든 드라이브에 대한 페이징 파일 크기 자동 관리(A)' 체크 해제 > '사용자 지정 크기(C)' 선택 > 처음 크기 : 4096, 최대 크기 : 8192 > 설정 > 확인 > 재부팅 \
          ![image](https://user-images.githubusercontent.com/21374902/157142064-ccdc512f-d2d5-4c29-8ece-1414734761a2.png)
    </details>

    <details>
      <summary> 📑 Windows10 (WSL2)</summary>
      
      #### 💥 Docker Desktop을 사용할 수 없기 때문에 WSL2 환경에 세팅
      
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
      - 실행
        - `minikube start --driver=docker`

    </details>

    <details>
      <summary> 📑 MacOS </summary>
      
      - 설치
        - minikube : \
        `brew install minikube` \
        OR `curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-darwin-amd64 && chmod +x minikube`
        - kubernetes : \
        `brew install kubectl` \
        OR `curl -LO https://storage.googleapis.com/kubernetes-release/release/v1.20.0/bin/darwin/amd64/kubectl && chmod +x kubectl`
      - 실행
        - `minikube start --driver=docker`
    </details>

    <details>
      <summary> 📑 minikube 명령어</summary>

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
    </details>

  - 정상 구동 화면\
    ![image](https://user-images.githubusercontent.com/21374902/157167987-36ab1b4e-bad0-4355-ac44-5faedd0b30d6.png)



---



## 무작정 따라해보기
  - kubernetes 버전
    ![image](https://user-images.githubusercontent.com/21374902/157173397-bcf2a579-9f5b-48a6-bbce-de732ae857a2.png)
    <details>
      <summary>📑 wordpress 실습</summary>
    
      ```yml
      # wordpress.yml
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
    
      - wordpress-k8s.yml 실행 : `kubectl apply -f wordpress-k8s.yml`
      - Terminal을 추가로 열어서 Monitoring 실행 : `watch -n 0.5 kubectl get all`
        - MacOS는 watch 설치 필요 : `brew install watch`
        - Status = Running 확인
          ![image](https://user-images.githubusercontent.com/21374902/158041664-739224aa-744d-43b1-a7d7-091b501bb821.png)
      - 실행한 wordpress 확인
        - `minikube ip`로 IP 확인
        - `kubectl get all`에서 service/wordpress의 PORT 확인
        - `Docker`로 사용중이면 `minikube service wordpress`
      - 💥 minikube service wordpress 접속 불가 현상
        - wordpress의 NodePort로 접근이 안되고 minikube service 명령어로도 접근이 안됨
        - Docker Desktop 으로 해결
          - minikube에 있는 리소스 제거 : `kubectl delete -f wordpress-k8s.yml`
          - minikube 중단 : `minikube stop`
          - Docker Desktop에서 kubernetes 활성화 : 환경설정 ▶️ Kubernetes ▶️ Enable Kubernetes ▶️ Apply & Restart
          - Docker Desktop 자원 설정 : `kubectl config use-context docker-desktop`
          - wordpress 시작 : `kubectl apply -f wordpress.yml`
          - wordpress의 NodePort로 접근 : localhost:32499
      - wordpress 리소스 제거 : `kubectl delete -f wordpress-k8s.yml`
    </details>
    
    
  - 참고 : docker-compose.yml 버전
    ![image](https://user-images.githubusercontent.com/21374902/157173260-bbbe2ee7-3b5d-4033-89b0-0d9458a7818b.png)
    
    <details>
      <summary> 📑docker-compose 버전</summary>
      
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
    </details>



  ---



## Kubernetes 명령어
  - ###### kubectl apply -f {file or url}
    - 파일 또는 URL까지 사용해서 배포할 수 있다.
    - `kubectl apply -f https://subicura.com/k8s/code/guide/index/wordpress-k8s.yml`\
    ![image](https://user-images.githubusercontent.com/21374902/158041249-d03d52e1-25a8-4a43-ab25-2a7bad41df46.png)
  - ###### kubectl get {type}
    - Resource를 확인할 수 있다.
    - `kubectl get po, svc`\
      ![image](https://user-images.githubusercontent.com/21374902/158041617-2957a616-407f-44fa-8cbb-db25d56ca862.png)
    - `kubectl get all`\
      ![image](https://user-images.githubusercontent.com/21374902/158041664-739224aa-744d-43b1-a7d7-091b501bb821.png)
    - `kubectl get pod -o wide`\
      `kubectl get pod -o yaml`\
      `kubectl get pod -o json`\
      `kubectl get pod --show-labels`
  - ###### kubectl describe {type} {name}
    - Resource의 상세한 상태를 볼 수 있다.
    - `kubectl describe pod wordpress-74757b6ff-s6k2h`
  - ###### kubectl delete {type} {name}
    - Resource를 제거할 수 있다.
    - `kubectl delete pod wordpress-74757b6ff-s6k2h`
      - Pod를 삭제해도 ReplicaSet에 따라 자동으로 재생성 된다.
  - ###### kubectl logs {pod name}
    - Container의 로그를 확인할 수 있다.
    - `kubectl logs wordpress-mysql-5447bfc5b-zqg94`
    - `kubectl logs wordpress-74757b6ff-wbkl7`
  - ###### kubectl exec {option} {pod name} -- {command}
    - Container에 명령어를 전달할 수 있다.
    - `kubectl exec -it wordpress-mysql-5447bfc5b-zqg94 -- bash`
    - Pod 안에 여러개의 Container가 있으면 `-c` 옵션으로 Container를 선택해줘야 한다.
      - `kubectl exec -it wordpress -c db -- sh`
  - ###### kubectl config current-context
    - kubectl은 여러 개의 Kubernetes Cluster Context로 설정하고 필요에 따라 선택할 수 있다. 
    - `kubectl config current-context`
    - `kubectl config use-context minikube`
  - ###### kubectl api-resources
    - 전체 오브젝트 종류 확인
  - ###### kubectl explain pod
    - 특정 오브젝트 설명 보기



---



## Pod 배포하기
- ### Pod 배포 - 명령어
  - Pod는 Kubernetes에서 관리하는 가장 작은 배포 단위이며 1개의 Pod 안에 여러개의 Container를 있을 수 있습니다.
  - Docker Hub에 있는 image로 Pod 실행해보기\
  `kubectl run task_daemon --image yongwoo1992/repeatedly_multi_task:1.0 kubectl get po kubectl exec -it task_daemon -- bash sh run.sh status`
  - Pod가 배포되는 과정
    - `Scheduler` 🔃 `API Server` : 할당되지 않은 Pod가 있는지 체크
    - `Kubelet` 🔃 `API Server` : Node에 할당된 Pod가 있는지 체크 
    - `kubectl run` 수행
    - `Scheduler` ➡ `minikube(node)` : Pod를 Node에 할당 (실습 환경은 단일 node - minikube)
    - `Kubelet` ➡ `Container` : 할당 된 Pod를 확인하고 Container 생성
    - `Kubelet` ➡ `API Server` : Pod의 상태를 전달
- ### Pod 배포 - Yaml
  - YAML 파일을 작성하고 `apply` 명령어로 Pod를 배포할 수 있습니다.
  - `kubectl apply -f yongwoo_daemon.yml`
    ```yml
    # yongwoo_daemon.yml
    apiVersion: v1
    kind: Pod
    metadata:
      name: yongwoo-daemon
      labels:
        app: yongwoo
    spec:
      containers:
      - name: yongwoo-daemon
        image: yongwoo1992/repeatedly_multi_task:1.0
    ```
  - 상태 확인
    - Pod 정보 확인 : `watch -n 0.5 kubectl get all`
    - Pod 내 Container 접속
      - `kubectl exec -it yongwoo-daemon -- bash`
      - `sh run.sh status`
    - Pod 삭제 : `kubectl delete -f yongwoo_daemon.yml`



---



## Container 상태 모니터링
  - Container가 생성된 직후에는 서비스할 수 없는 상태이다.
  - Container가 준비되고 그 안에 있는 Application이 Running 되어야 서비스할 수 있는 상태라고 할 수 있다.
    ![image](https://user-images.githubusercontent.com/21374902/158043731-9c1cb4d3-500c-41f6-bed7-73e663253c58.png)
  - 일반적으로 Container의 상태를 지속적으로 체크하고 이상이 있으면 자동으로 재시작해주는 옵션을 사용한다.
    - #### livenessProbe : Container의 상태가 정상이 아니면 `재시작`
      - Container의 상태를 체크하는 방법은 여러가지가 있다. : `httpGet`, `exec`, `tcpSocket`, `grpc`
        <details>
          <summary> 📑 livenessProbe 실습 - httpGet 사용</summary>

          ```yml
          # livenessProbe.yml
          apiVersion: v1
          kind: Pod
          metadata:
            name: sample-rp
            labels:
              app: sample
          spec:
            containers:
            - name: app
              image: ghcr.io/subicura/echo:v1
              livenessProbe:
                httpGet:
                  path: /not/exist
                  port: 8080
                initialDelaySeconds: 5 # 5초 이후에 상태 확인
                timeoutSeconds: 2 # 요청에 대한 timeout 시간 >설정 (Default : 1)
                periodSeconds: 5 # 5초마다 Pod 확인 (Default : 10)
                failureThreshold: 1 # 1번 실패하면 재시작 (Default : 3)
          ```
          - 상태 확인
            - `/not/exist`는 존재하지 않기 때문에 Pod는 계속 에러 응답을 보낸다.
            - `Running` 상태와 `CrashLoopBackOff` 상태를 반복하면서 `RESTARTS` 가 늘어나는 것을 확인 할 수 있다.

        </details>
      
    - #### readinessProbe : Container의 상태가 정상이 아니면 `요청 제외`
      - Container의 상태가 이상해도 재시작하지 않고 요청만 제외시킨다.
        <details>
          <summary> 📑 readinessProbe 실습</summary>
          
          ```yml
          # readinessProbe.yml
          apiVersion: v1
          kind: Pod
          metadata:
            name: sample-rp
            labels:
              app: sample
          spec:
            containers:
            - name: app
              image: ghcr.io/subicura/echo:v1
              readinessProbe:
                httpGet:
                  path: /not/exist
                  port: 8080
                initialDelaySeconds: 5
                timeoutSeconds: 2
                periodSeconds: 5
                failureThreshold: 1
          ```
          - 상태 확인
            - `readinessProbe`는 재시작을 하지 않기 때문에 `READY` 상태가 `0/1`로 남아있는 것을 볼 수 있다.
        </details>
        
    - #### livenessProbe + readinessProbe
      - 일반적으론 2가지 옵션을 같이 사용한다.
        <details>
          <summary> 📑 livenessProbe + readinessProbe 실습 예제</summary>

          ```yml
          # liveAndRead.yml
          apiVersion: v1
          kind: Pod
          metadata:
            name: sample-lp-rp
            labels:
              app: sample
          spec:
            containers:
            - name: app
              image: ghcr.io/subicura/echo:v1
              livenessProbe:
                httpGet:
                  path: /
                  port: 3000
              readinessProbe:
                httpGet:
                  path: /
                  port: 3000
          ```
          - 상태 확인
            - 3000 port는 정상적으로 응답하기 때문에 `READY = 1/1`, `STATUS=Running` 을 확인할 수 있다.
            - 둘 다 /not/exist, 8080 으로 변경하면 `READY = 0/1`, `STATUS=Running` 면서 `RESTARTS`는 계속 증가한다.
            - livenessProbe 만 불가능 상태로 바꾸면 `READY = 1/1`, `STATUS=Running` 면서 `RESTARTS`는 계속 증가한다.
        </details>
        



---



## 다중 Container 자원 공유
  - 하나의 Pod에 여러개의 Container가 있을 때 Container끼리 자원이나 네트워크를 공유할 수 있다.
    <details>
      <summary> 📑 localhost를 공유하는 예제</summary>

      ```yml
      # multi-containers.yml
      apiVersion: v1
      kind: Pod
      metadata:
        name: counter
        labels:
          app: counter
      spec:
        containers:
        - name: app
          image: ghcr.io/subicura/counter:latest
          env:
          - name: REDIS_HOST
            value: "localhost"
        - name: db
          image: redis
      ```
      - 상태 확인
        - app의 로그 확인 : `kubectl logs counter app`
        - db의 로그 확인 : `kubectl logs counter db`
        - db에 접속해서 redis 동작 확인
          - `kubectl exec -it counter -c app -- sh`  # -c 옵션으로 container 지정
          - `curl localhost:3000`
          - `telnet localhost 6379`
          - `dbsize`
          - `keys *`
          - `get count`
          - `quit`
    </details>



---



## ReplicaSet
  ![image](https://user-images.githubusercontent.com/21374902/158137366-53b85b39-7ac7-4259-80fc-e82c410c8d02.png)
  - ReplicaSet은 label을 체크해서 원하는 수의 Pod가 충족되지 않으면 새로운 Pod을 자동으로 생성한다.
  - Pod만 구성했을 때와 ReplicaSet을 같이 구성했을 때
    <details>
      <summary> 📑 ReplicaSet 예제</summary>

      ```yml
      apiVersion: apps/v1
      kind: ReplicaSet
      metadata:
        name: rs
      spec:
        replicas: 1  # Pod 개수
        selector:    # label 체크 조건
          matchLabels:
            app: echo
            tier: app
        template:     # 생성할 Pod 정보 
          metadata:
            labels:
              app: echo
              tier: app
          spec:
            containers:
            - name: echo
              image: ghcr.io/subicura/echo:v1
      ```
      - 상태 확인
        - ReplicaSet은 label 기준으로 체크 한다. (spec.selector)
        - 생성 된 Pod의 label 확인 : `kubectl get pod --show-labels`
        - label 제거 : `kubectl label pod {pod name} app-`\
          ▶️ Pod가 1개 새로 생성되는 것을 확인 할 수 있음.
        - label 추가 : `kubectl label pod {pod name} app=echo`\
          ▶️ Pod 중 1개가 삭제 됨.
          
      
    </details>
  - ReplicaSet이 동작하는 방식
    - `Scheduler` 🔃 `API Server` : 할당되지 않은 Pod가 있는지 체크
    - `ReplicaSet Controller` 🔃 `API Server` : 조건 기준으로 체크
    - `ReplicaSet Controller` ➡ `API Server` : Pod 생성 및 제거


---



## Deployment
  - Deployment를 사용하면 Pod를 새로운 버전으로 업데이트하고 관리할 수 있다.
  - ReplicaSet을 이용하여 Pod을 업데이트하고 이력을 관리하여 Rollback 하거나 특정 버전 revision으로 돌아갈 수 있다.
  - Deployment 방식은 크게 2가지 이다.
    - Recreate : 현재 운영중인 Pod를 모두 삭제하고 새로운 버전의 Pod를 생성한다. Downtime이 발생할 수 있기 때문에 권장하지 않는다.
    - RollingUpdate(default) : 새로운 버전의 n개의 Pod를 생성하고 기존 버전의 Pod를 삭제한다.
      <details>
        <summary> 📑 Deployment 예제</summary>

        ```yml
        apiVersion: apps/v1
        kind: Deployment
        metadata:
          name: dp
        spec:
          replicas: 4
          selector:
            matchLabels:
              app: echo
              tier: app
          template:
            metadata:
              labels:
                app: echo
                tier: app
            spec:
              containers:
              - name: echo
                image: ghcr.io/subicura/echo:v2
                # image: ghcr.io/subicura/echo:v1
        ```
        - 상태 확인
          - 생성 된 자원 확인
        - Version 변경 : spec.template.spec.containers.image의 v1 → v2로 변경 후
          - `kubectl apply -f echo-deployment.yml`
          - Pod가 8개 되는게 아니고 기존 4개는 사라지고 새로운 버전의 4개가 생성되는 것을 볼 수 있다.
          - replicaSet은 새롭게 1개가 생성되고 기존 replicaSet은 0/0/0 상태를 갖는다.
      </details>
    
  - 배포되는 과정
    - 기존에 v1 기준으로 ReplicaSet이 있고 그 안에 Pod는 4개 존재
    - v2를 배포하면 v2를 위한 ReplicaSet이 생성됨
    - ReplicaSet(v1)에 있는 Pod가 1개씩 줄면서 ReplicaSet(v2)는 1개씩 늘어남.
    - 상제 정보 확인  : `kubectl describe deploy {deployment name}`
  - Deployment가 동작하는 방식
    - `Scheduler` 🔃 `API Server` : 할당되지 않은 Pod가 있는지 체크
    - `Deployment Controller` 🔃 `API Server` : 조건 기준으로 체크
    - `ReplicaSet Controller` 🔃 `API Server` : 조건 기준으로 체크
    - `Deployment Controller` ➡ `API Server` : 조건에 맞는 ReplicaSet 생성
    - `ReplicaSet Controller` ➡ `API Server` : Pod 생성 및 제거
  - Deployment 버전 관리
    - History 확인 : `kubectl rollout history deploy {deployment name}`
    - 특정 버전에 대한 History 확인 : `kubectl rollout history deploy {deployment name} --revision=1`
    - 이전 버전으로 rollback : `kubectl rollout undo deploy {deployment name}`
    - 특정 버전으로 rollback : `kubectl rollout undo deploy /{deployment name} --to-revision=2`
  - 배포 후 조정
    - `Deployment`로 생성 된 Pod의 수 조정 : `kubectl scale deployment {deployment name} --replicas=3`
    - `ReplicaSet`으로 생성 된 Pod 수 조정 : `kubectl scale rs echo-replica --replicas=3`
  - RollingUpdate의 maxSurge, maxUnavailable
    - maxSurge, maxUnavailable 옵션을 주면 한번에 실행하는 Pod의 수를 조정할 수 있다. 
      <details>
        <summary> 📑 RollingUpdate 예제 </summary>
        
        ```yml
        apiVersion: apps/v1
        kind: Deployment
        metadata:
          name: echo-deploy-st
        spec:
          replicas: 4
          selector:
            matchLabels:
              app: echo
              tier: app
          minReadySeconds: 5
          strategy:
            type: RollingUpdate
            rollingUpdate:  # 한 번에 작업하는 Pod의 개수 설정
              maxSurge: 3
              maxUnavailable: 3
          template:
            metadata:
              labels:
                app: echo
                tier: app
            spec:
              containers:
                - name: echo
                  image: ghcr.io/subicura/echo:v1
                  livenessProbe:
                    httpGet:
                      path: /
                      port: 3000
        ```
        </details>

    - 처음에 실행했던 Deployment : 1개씩 up/down
      ```
      Normal  ScalingReplicaSet  30s   deployment-controller  Scaled up replica set dp-77cd7699f4 to 4
      Normal  ScalingReplicaSet  13s   deployment-controller  Scaled up replica set dp-76dcd9f4f9 to 1
      Normal  ScalingReplicaSet  12s   deployment-controller  Scaled down replica set dp-77cd7699f4 to 3
      Normal  ScalingReplicaSet  12s   deployment-controller  Scaled up replica set dp-76dcd9f4f9 to 2
      Normal  ScalingReplicaSet  11s   deployment-controller  Scaled down replica set dp-77cd7699f4 to 2
      Normal  ScalingReplicaSet  11s   deployment-controller  Scaled up replica set dp-76dcd9f4f9 to 3
      Normal  ScalingReplicaSet  11s   deployment-controller  Scaled down replica set dp-77cd7699f4 to 1
      Normal  ScalingReplicaSet  11s   deployment-controller  Scaled up replica set dp-76dcd9f4f9 to 4
      Normal  ScalingReplicaSet  9s    deployment-controller  Scaled down replica set dp-77cd7699f4 to 0
      ```

    - maxSurge 옵션을 주고 실행했던 Deployment : 3개씩 up/down
      ```log
      Normal  ScalingReplicaSet  85s   deployment-controller  Scaled up replica set dp-roll-dbd946f9c to 4
      Normal  ScalingReplicaSet  28s   deployment-controller  Scaled up replica set dp-roll-bf855bcd8 to 3
      Normal  ScalingReplicaSet  28s   deployment-controller  Scaled down replica set dp-roll-dbd946f9c to 1
      Normal  ScalingReplicaSet  28s   deployment-controller  Scaled up replica set dp-roll-bf855bcd8 to 4
      Normal  ScalingReplicaSet  19s   deployment-controller  Scaled down replica set dp-roll-dbd946f9c to 0
      ```  
      



---



## Service
  - Pod는 생성되고 사라지고를 반복하기 때문에 Pod에 직접 통신하는 것은 힘들다.
    ![image](https://user-images.githubusercontent.com/21374902/158757760-fcc420ee-e151-4b31-9368-178d5b354466.png)
  - [[Kubernetes Object의 Cluster IP]](#Cluster-IP) 에서 볼 수 있듯이 Service의 Cluster IP는 내부에서만 접근이 가능하고 NodePort로 접근을 해도 Main NodePort가 죽으면 서비스가 일시적으로 동작하지 않을 수 있다.
  - Service 이름을 내부 Domain Server에 등록해서 Pod 간에 Service 이름으로 통신할 수 있다.

  - ### Service 생성 흐름
    - `Scheduler` 🔃 `API Server` : 할당되지 않은 Pod가 있는지 체크
    - `Endpoint Controller` 🔃 `API Server` : Service와 Pod를 감시하면서 조건에 맞는 Pod의 IP 수집
    - `Endpoint Controller` ➡ `API Server` : 수집한 IP로 Endpoint 생성
    - `Kube-Proxy` ➡ `API Server` : 변화를 감지하고 Node의 iptables를 설정
    - `CoreDNS` ➡ `API Server` : Service를 감시하고 Service의 이름과 IP를 CoreDNS에 추가
      - `iptables` : Kernel 레벨의 네트워크 도구
      - `CoreDNS` : `kube-dns`로 생성되며 빠르고 편리하게 사용할 수 있는 Cluster 내부용 Domain Name Server
      - `iptables` 설정으로 여러 IP에 트래픽을 전달하고 `CoreDNS`를 이용해서 IP 대신 Domain을 사용할 수 있다.
  - ### Endpoint
    - Endpoint Address 정보엔 redis Pod의 IP를 확인할 수 있습니다.
    - `kubectl get ep`
    - `kubectl describe ep redis`
  
  - ### Service 의 종류
    - ClusterIP : Service가 기본적으로 갖고있는 ClusterIP를 사용
    - NodePort : 모든 Node에 Port를 할당해서 접근
    - LoadBalancer : Load Balance Plugin을 설치해서 접근
  - ### Service (ClusterIP)
    <details>
      <summary> 📑 redis를 Service로 노출하는 예제</summary>

      ```yml
      # counter-redis-svc.yml 
      apiVersion: apps/v1
      kind: Deployment
      metadata:
        name: redis
      spec:
        selector: #label selector : 특정 label을 찾아서 해당하는 Object만 관리
          matchLabels:
            app: counter
            tier: db
        template:
          metadata:
            labels:
              app: counter
              tier: db
          spec:
            containers:
              - name: redis
                image: redis
                ports:
                  - containerPort: 6379
                    protocol: TCP

      ---
      apiVersion: v1
      kind: Service
      metadata:
        name: redis
      spec:
        ports:
          - port: 6379
            protocol: TCP
        selector:
          app: counter
          tier: db
      ```
      - 상태 확인
      - Service의 selector는 Deployment에서 정의한 label을 사용해서 해당 Pod의 6379 포트로 연결하도록 설정한다.
      - 같은 Cluster에서 생성된 Pod라면 `redis`라는 domain으로 접근할 수 있습니다.
    </details>

    <details>
      <summary> 📑 redis에 접근할 Deployment로 생성 예제 </summary>

      ```yml
      # counter-app.yml 
      apiVersion: apps/v1
      kind: Deployment
      metadata:
        name: counter
      spec:
        selector:
          matchLabels:
            app: counter
            tier: app
        template:
          metadata:
            labels:
              app: counter
              tier: app
          spec:
            containers:
              - name: counter
                image: ghcr.io/subicura/counter:latest
                env:
                  - name: REDIS_HOST
                    value: "redis"  # Service 이름
                  - name: REDIS_PORT
                    value: "6379"
      ```
      - counter에 접근한 후 redis에 접근할 수 있습니다.\
        - `kubectl exec -it counter -- sh`
        - `telnet redis 6379`
        - `dbsize`
        - `KEYS *`
        - `GET count`
    </details>
  - ### Service (NodePort)
    - Cluster IP는 Cluster 내부에서만 접근할 수 있기 때문에 외부(Node)에서 접근할 수 있도록 NodePort를 사용한다.
      <details>
        <summary> 📑 NodePort 예제</summary>

        ```yml
        # counter-nodeport.yml 
        apiVersion: v1
        kind: Service
        metadata:
          name: counter-np
        spec:
          type: NodePort
          ports:
            - port: 3000
              protocol: TCP
              nodePort: 31000
          selector:
            app: counter
            tier: app
        ```
        - 상태 확인
        - minikube ip의 31000 port로 접근하면 counter로 접근할 수 있다.
      </details>
  - ### Service (LoadBalancer)
    - NodePort는 Main Node가 사라지면 자동으로 다른 Node를 통해 접근이 불가능하다는 점이다.
    - 살아있는 Node를 구분하기 위해서 모든 Node를 바라보는 `Load Balaner`가 필요하고 요청은 Load Balancer를 통해서 살아있는 NodePort로 연결된다.
      <details>
        <summary> 📑 LoadBalancer 예제</summary>

        ```yml
        # counter-lb.yml 
        apiVersion: v1
        kind: Service
        metadata:
          name: counter-lb
        spec:
          type: LoadBalancer
          ports:
            - port: 30000
              targetPort: 3000
              protocol: TCP
          selector:
            app: counter
            tier: app
        ```
        - 상태 확인

        - EXTERNAL-IP가 pending인 이유
          - Local 환경에선 특정 노드(실습환경에선 minikube 단일 노드)를 가리키는 Load Balancer가 외부에 필요한데 그게 없기 때문에 EXTERNAL-IP가 지정되지 않는다.
          - minikube에 가상 Load Balancer 만들기
            - `minikube addons enable metallb` : 가상 환경에서 Load Balancer를 만들어주고 minikube에 떠있는 현재 노드를 설정
            - minikube의 ip를 ConfigMap으로 지정
              - `mikikube addons configure metallb`\
                `-- Enter Load Balancer Start IP` : # minikube ip 결과값 입력\
                `-- Enter Load Balancer End IP` : # minikube ip 결과값 입력
              - yml 사용
                ```yml
                # metallb-cm.yml 
                apiVersion: v1
                kind: ConfigMap
                metadata:
                  namespace: metallb-system
                  name: config
                data:
                  config: |
                    address-pools:
                    - name: default
                      protocol: layer2
                      addresses:
                      - 192.168.64.4/32 # minikube ip
                ```
        - minikube ip:30000 접근
        - Docker 사용중이면 `minikube service counter-lb`
      
      </details>
      



---



## Ingress
- 참고사항 : FQDN
  - Pod와 Service에 DNS Record를 생성한다.
  - Service : {service name}.{namespace}.svc.cluster.local
  - Pod : {Pod IP}.{namespace}.pod.cluster.local
- 하나의 Cluster에서 여러개의 Service를 운영할 때 여러개의 Domain과 Service를 매칭해서 사용할 수 있다.
  ![image](https://user-images.githubusercontent.com/21374902/158762956-958b3fcf-3569-4642-992c-fbfeee150344.png)
- htto(80), https(443) Port로 여러 개의 Service를 연결해야할 때 Ingress를 사용한다.
- ### Ingress 활성화
  - Ingress는 다른 Object와 달리 별도의 Controller를 설치해야 한다. Controller의 종류는 많지만 실습에선 nginx ingress controller를 사용한다.\
  (그 외엔 haproxy, traefik, alb 등이 있다.)
  - `minikube addons enable ingress`
  - `kubectl -n ingress-nginx get pod`
  - `curl -I http://minikube ip/healthz`
  - Docker 사용중이라면 `minikube service ingress-nginx-controller -n ingress-nginx --url` 명령어로 접속 주소 확인
- ### 2개의 다른 버전인 echo Web Application 배포
  - `spec.rules.host`는 minikube ip로 변경
  - Docker 사용중이면 `spec.rules.host`에 127.0.0.1 사용 : v1.echo.127.0.0.1.sslip.io
    <details>
      <summary> 📑 v1 배포 예제</summary>

      ```yml
      # echo-v1.yml 
      apiVersion: networking.k8s.io/v1
      kind: Ingress
      metadata:
        name: echo-v1
      spec:
        rules:
          - host: v1.echo.192.168.64.5.sslip.io # minikube ip 사용
            http:
              paths:
                - path: /
                  pathType: Prefix
                  backend:
                    service:
                      name: echo-v1
                      port:
                        number: 3000

      ---
      apiVersion: apps/v1
      kind: Deployment
      metadata:
        name: echo-v1
      spec:
        replicas: 3
        selector:
          matchLabels:
            app: echo
            tier: app
            version: v1
        template:
          metadata:
            labels:
              app: echo
              tier: app
              version: v1
          spec:
            containers:
              - name: echo
                image: ghcr.io/subicura/echo:v1
                livenessProbe:
                  httpGet:
                    path: /
                    port: 3000

      ---
      apiVersion: v1
      kind: Service
      metadata:
        name: echo-v1
      spec:
        ports:
          - port: 3000
            protocol: TCP
        selector:
          app: echo
          tier: app
          version: v1
      ```
    </details>
    <details>
      <summary> 📑 v2 배포 예제</summary>

      ```yml
      # echo-v2.yml 
      apiVersion: networking.k8s.io/v1
      kind: Ingress
      metadata:
        name: echo-v2
      spec:
        rules:
          - host: v2.echo.192.168.64.5.sslip.io  # minikube ip 사용
            http:
              paths:
                - path: /
                  pathType: Prefix
                  backend:
                    service:
                      name: echo-v2
                      port:
                        number: 3000

      ---
      apiVersion: apps/v1
      kind: Deployment
      metadata:
        name: echo-v2
      spec:
        replicas: 3
        selector:
          matchLabels:
            app: echo
            tier: app
            version: v2
        template:
          metadata:
            labels:
              app: echo
              tier: app
              version: v2
          spec:
            containers:
              - name: echo
                image: ghcr.io/subicura/echo:v2
                livenessProbe:
                  httpGet:
                    path: /
                    port: 3000

      ---
      apiVersion: v1
      kind: Service
      metadata:
        name: echo-v2
      spec:
        ports:
          - port: 3000
            protocol: TCP
        selector:
          app: echo
          tier: app
          version: v2
      ```

    </details>
    
  - 상태 확인
    - `kubectl apply -f echo-v1.yml, echo-v2.yml`
    - `kubectl get ing`
    - 접속 테스트
      - Docker는 v1.echo.127.0.0.1.sslip.io:PORT로 테스트한다.\
        PORT는 ingress-nginx-controller 서비스의 첫번째 항목
- ### Ingress 생성 흐름
  - `Ingress Controller` 🔃 `API Server` : Ingress 변화가 있는지 확인
  - `Endpoint Controller` ➡ `Nginx, HAProxy, ...` : 변경 된 내용을 Nginx에 설정하고 프로세스 재시작



---



## Volume
- Volume를 따로 지정해주지 않으면 데아터는 모두 Container 내부에 저장되고 Pod가 제거되면 데이터는 모두 사라진다.
- Mysql과 같은 Database는 데이터가 유실되지 않도록 해야하기 때문에 반드시 별도의 저장소에 데이터를 저장해야 한다.
- ElasticBlockStore(AWS), AzureDisk(Azure), GcePersistentDisk(GCP)를 사용해야 하지만 실습 환경에선 Local을 이용한다.
- ### Sidecar : Container에서 생성되는 Log 파일을 별도로 수집하는 방식
  <details>
    <summary> 📑 Sidecar 예제</summary>

    ```yml
    # empty-dir.yml 
    apiVersion: v1
    kind: Pod
    metadata:
      name: sidecar
    spec:
      containers:
        - name: app
          image: busybox
          args:
            - /bin/sh
            - -c
            - >
              while true;
              do
                echo "$(date)\n" >> /var/log/example.log;
                sleep 1;
              done
          volumeMounts:
            - name: varlog
              mountPath: /var/log
        - name: sidecar
          image: busybox
          args: [/bin/sh, -c, "tail -f /var/log/example.log"]
          volumeMounts:
            - name: varlog
              mountPath: /var/log
      volumes:
        - name: varlog
          emptyDir: {}
    ```

    - 상태확인
      - `kubectl apply -f empty-dir.yml`
      - `kubectl logs -f sidecar -c sidecar`

  </details>

![image](https://user-images.githubusercontent.com/21374902/159108809-98178a99-249b-4c2b-9e6b-853b19d83c4d.png)

- ### Hostpath : Host(외부)의 directory를 Container directory에 연결하는 방식
  <details>
    <summary> 📑 Hostpath 예제</summary>

    ```yml
    # hostpath.yml  
    apiVersion: v1
    kind: Pod
    metadata:
      name: host-log
    spec:
      containers:
        - name: log
          image: busybox
          args: ["/bin/sh", "-c", "sleep infinity"]
          volumeMounts:
            - name: varlog
              mountPath: /host/var/log
      volumes:
        - name: varlog
          hostPath:
            path: /var/log
    ```

    - 상태 확인
      - `kubectl apply -f hostpath.yml`
      - `kubectl exec -it host-log -- sh`
      - `ls -al /host/var/log`
  
  </details>

![image](https://user-images.githubusercontent.com/21374902/159108963-78340da1-3555-43f0-b878-70a697d8d184.png)



---



## ConfigMap
- Container에서 사용하는 Configuration 파일은 image를 build 할 때 복사할 수 있지만 ConfigMap을 사용하면 Container를 실행할 때 외부 파일을 연결할 수 있다.
  <details>
    <summary> 📑 ConfigMap 생성 예제 </summary>

    ```yml
    # config-file.yml 
    global:
      scrape_interval: 15s

    scrape_configs:
      - job_name: prometheus
        metrics_path: /prometheus/metrics
        static_configs:
          - targets:
              - localhost:9090
    ```
    - ConfigMap 생성 : `kubectl create cm my-config --from-file=config-file.yml`
    - ConfigMap 조회 : `kubectl get cm`
    - ConfigMap 상세조회 : `kubectl describe cm/my-config`
    
  </details>

  <details>
    <summary> 📑 ConfigMap 연결 </summary>

    ```yml
    # alpine.yml 
    apiVersion: v1
    kind: Pod
    metadata:
      name: alpine
    spec:
      containers:
        - name: alpine
          image: alpine
          command: ["sleep"]
          args: ["100000"]
          volumeMounts:
            - name: config-vol
              mountPath: /etc/config
      volumes:
        - name: config-vol
          configMap:
            name: my-config
    ```
    - `kubectl apply -f alpine.yml`
    - 접속 후 설정 확인
      - `kubectl exec -it alpine -- ls /etc/config`
      - `kubectl exec -it alpine -- cat /etc/config/config-file.yml`

  </details>

- ### ENV 형식
  - env 형식을 그대로 사용할 수 있다.
    <details>
      <summary>📑 ENV 형식 예제</summary>
      
      ```yml
      # config-env.yml 
      hello=world
      haha=hoho
      ```
      - env 포멧으로 생성 : `kubectl create cm env-config --from-env-file=config-env.yml`
      - env-config 조회 : `kubectl describe cm/env-config`

    </details>


- ### YAML 형식
  <details>
    <summary>📑 YAML 형식 예제</summary>
      
    ```yml
    # config-map.yml 
    apiVersion: v1
    kind: ConfigMap
    metadata:
      name: my-config
    data:
      hello: world
      kuber: netes
      multiline: |-
        first
        second
        third
    ```
    - 기존 configmap 삭제 : `kubectl delete cm/my-config`
    - ConfigMap 생성 : `kubectl apply -f config-map.yml`
    - alpine 적용 : `kubectl apply -f alpine.yml`
    - 적용내용 확인 : `kubectl exec -it alpine -- cat /etc/config/multilineonfig`
  </details>

- ### ConfigMap을 환경변수로 사용
  <details>
    <summary>📑 ConfigMap을 환경변수로 사용하는 예제</summary>
      
    ```yml
    # alpine-env.yml 
    apiVersion: v1
    kind: Pod
    metadata:
      name: alpine-env
    spec:
      containers:
        - name: alpine
          image: alpine
          command: ["sleep"]
          args: ["100000"]
          env:
            - name: hello
              valueFrom:
                configMapKeyRef:
                  name: my-config
                  key: hello
    ```
    - 적용 : `kubectl apply -f alpine-env.yml`
    - env 확인 : `kubectl exec -it alpine-env -- env`
  </details>
---



## Secret
- ConfigMap과 유사한 특징을 갖지만 보안을 더 강화하여 데이터를 base64 암호화해서 저장한다.
- ### Secret 생성
  - username.txt 작성 : admin
  - userpassword.txt 작성 : 1q2w3e4r
  - secret 생성 : `kubectl create secret generic db-user-pass --from-file=./username.txt --from-file=./password.txt`
  - secret 상세 조회 : `kubectl describe secret/db-user-pass`
  - -o yaml로 상세 조회 : `kubectl get secret/db-user-pass -o yaml`
  - 저장된 데이터 base64 decode : `echo 'MXEydzNlNHI=' | base64 --decode`
- ### Secret을 환경변수로 연결
  <details>
    <summary>📑 Secret을 환경변수로 연결하는 예제</summary>
      
    ```yml
    # alpine-env.yml 
    apiVersion: v1
    kind: Pod
    metadata:
      name: alpine-env
    spec:
      containers:
        - name: alpine
          image: alpine
          command: ["sleep"]
          args: ["100000"]
          env:
            - name: DB_USERNAME
              valueFrom:
                secretKeyRef:
                  name: db-user-pass
                  key: username.txt
            - name: DB_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: db-user-pass
                  key: password.txt
    ```
    - 적용 : `kubectl apply -f alpine-env.yml`
    - env 확인 : `kubectl exec -it alpine-env -- env`
  </details>

---

## DaemonSet
- Node의 지정한 개수의 Pod를 유지시켜 준다.
- DaemonSet vs. ReplicaSet
  - ReplicaSet은 Node와 상관없이 Pod의 개수를 유지시켜 준다. 즉, Pod를 새로 생성할 때 각 Node의 자원상태를 보고 가능한 Node에 생성한다.
  - DaemonSet은 1개의 Node에 몇개의 Pod를 생성할지 정한다.
- DaemonSet 예제
  <details>
    <summary> 📑 DaemonSet 예제1</summary>

    ```yml
    apiVersion: apps/v1
    kind: DaemonSet
    metadata:
      name: daemonset1
    spec:
      selector:
        matchLabels:
          type: app
      template:
        metadata:
          labels:
            type: app
        spec:
          containers:
            - name: container
              image: nginx
              ports:
              - containerPort: 80

    ``` 
  </details>

  <details>
    <summary> 📑 DaemonSet 예제2</summary>

    ```yml
    apiVersion: apps/v1
    kind: DaemonSet
    metadata:
      name: daemonset1
    spec:
      selector:
        matchLabels:
          type: app
      template:
        metadata:
          labels:
            type: app
        spec:
          nodeSelector:
            os: centos
          containers:
            - name: container
              image: nginx
              ports:
              - containerPort: 80
    ``` 
  </details>



---


## Job
- 실행되고 종료되어야 하는 Pod를 관리할 때 사용
- Job 예제
  <details>
    <summary> 📑 Job 예제</summary>

    ```yml
    apiVersion: apps/v1
    kind: Job
    metadata:
      name: job1
    spec:
      completions: 4  # 몇개의 Pod에 실행할지
      parallelism: 2  # 동시에 몇개의 Pod에서 실행할지
      activeDeadlineSeconds: 20 # 몇초동안만 살려둘지
      template:
        spec:
          containers:
          - name : job1
            image: perl
            command: ["perl", "-Mbignum=bpi", "-wle", "print bpi(2000)"]
          restartPolicy: Never
      backoffLimit: 3 # 재시도 횟수
    ``` 
  </details>



---


## CronJob
- Job을 주기적으로 생성하는 역할을 한다.
- ConcurrencyPolicy : 실행전략
  - Allow (default) : 동시에 실행되는 Job 허용한다.
  - Forbid : 동시에 실행되는 것을 허용하지 않는다. 이전 Job이 아직 완료되지 않은 경우, 새로운 Job의 실행을 건너 뛴다.
  - Replace : 이전 Job이 아직 완료되지 않은 경우, 이전 Job을 중단하고 새로운 Job을 실행한다.
- CronJob 예제
  <details>
    <summary> 📑 CronJob 예제</summary>

    ```yml
    apiVersion: apps/v1
    kind: CronJob
    metadata:
      name: hello
    spec:
      schedule: "*/1 * * * *"
      concurrencyPolicy: Replace
      jobTemplate:
        spec:
          template:
            spec:
              containers:
              - name: hello
                image: busybox
                imagePullPolicy: IfNotPresent
                command:
                - /bin/sh
                - -c
                - date; echo Hello from the kubernetes cluster
    ``` 
  </details>


---


## Persistent Volume
- Persistent Volume (PV) : 관리자에 의해 생성된 볼륨
- Persistent Volume Claim (PVC) : 사용자가 볼륨을 사용하기 위해 PV에 요청하게 된다.
- PV/PVC의 Lifecycle
  - Provisioning
    - 정적, 동적인 PV를 생성하는 단계이다.
    - Static Provisioning : 설정 파일 등을 통해 특정 용량을 가진 PV를 미리 생성해두고 요청이 있을 때 미리 생성한 PV를 할당하여 사용
    - Dynamic Provisioning : 사용자가 요청할 때 PV를 생성해서 할당하고 사용자는 원하는 만큼 용량을 설정해서 생성할 수 있다.
  - Binding
    - PV를 PVC에 연결시키는 단계이다.
    - PVC는 사용자가 요청하는 볼륨을 PV에 요청하고 PV는 그에 맞는 볼륨이 있으면 할당한다.
    - 만약 PVC가 요청하는 볼륨이 PV에 없다면 해당 요청은 무한정 남아있게 되고, PVC가 요청하는 볼륨이 PV에 생성되면 그 요청은 받아들여져 할당한다.
    - PVC와 PV는 ClaimRef를 사용하는 1:1 관계 이며 바인딩이 정상적으로 완료될 경우 bound 상태가 된다.
  - Using
    - Pod는 PVC를 볼륨으로 사용 합니다. Cluster는 PVC를 확인하여 Binding 된 PV를 찾고 해당 볼륨을 Pod에서 사용할 수 있도록 해준다.
    - 만약 Pod가 사용중인 PVC를 삭제하려고 하면 Storage Object in Use Protection에 의해 삭제되지 않는다. 만약 삭제 요청을 하였다면 Pod가 PVC를 사용하지 않을때까지 삭제 요청은 연기된다.
  - Reclamiming
    - PV는 기존에 사용했던 PVC가 아니더라도 다른 PVC로 재활용이 가능하다.
    - 사용이 종료된 PVC를 삭제할 때, 사용했던 PV의 데이터를 어떻게 처리할지에 대한 설정한다.
      - Retain : PV의 데이터를 그대로 보존 합니다.
      - Recycle : 재사용하게될 경우 기존의 PV 데이터들을 모두 삭제 후 재사용 합니다.
      - Delete : 사용이 종료되면 해당 볼륨을 삭제 합니다.
 - PV/PVC 사용 예제
   <details>
    <summary> 📑 PV 생성 예제</summary>

    ```yml
    apiVersion: v1 
      kind: PersistentVolume 
      metadata: 
        name: dev-pv 
      spec: 
        capacity: 
          storage: 2Gi 
        volumeMode: Filesystem 
        accessModes: 
        - ReadWriteOnce 
        storageClassName: manual 
        persistentVolumeReclaimPolicy: Delete 
        hostPath: 
          path: /tmp/log_backup
    ```

   </details>

   <details>
    <summary> 📑 PVC 생성 예제</summary>

    ```yml
    apiVersion: v1 
    kind: PersistentVolumeClaim 
    metadata: 
      name: dev-pvc 
    spec: 
      accessModes: 
      - ReadWriteOnce 
      volumeMode: Filesystem 
      resources: 
        requests: 
          storage: 2Gi 
      storageClassName: manual
    ```

   </details>

   <details>
    <summary> 📑 PVC를 사용할 Deployment 예제</summary>

    ```yml
    apiVersion: apps/v1 
    kind: Deployment 
    metadata: 
      name: test-deployment 
      labels: 
        app: test-deployment 
    spec: 
      replicas: 1 
      selector: 
        matchLabels: 
          app: test-deployment 
      template: 
        metadata: 
          labels: 
            app: test-deployment 
        spec: 
          containers: 
          - name: test-deployment 
            image: nginx 
            ports: 
            - containerPort: 8080 
            volumeMounts: 
            - mountPath: "/var/log/test.log" 
              name: dev-volume 
          volumes: 
          - name: dev-volume 
            persistentVolumeClaim: 
              claimName: dev-pvc
    ```

   </details>



---

## StatefulSet
- Application의 상태를 저장하고 관리하는데 사용된다. Deployment와 ReplicaSet과 다르게 각 Pod의 고유성을 보장한다. Pod마다 고유한 식별자가 존재해서 고유한 데이터를 보관한다.
- StatefulSet의 특징
  - Pod 이름에 식별자 부여
    - StatefulSet에 의해 생성되는 Pod는 명시적으로 순서에 해당하는 식별자가 Pod 이름에 부여된다. 
    - 예를 들어 mynginx라는 이름의 Pod를 정의하면 StatefulSet에 의해 생성되는 Pod는 mynginx-0, mynginx-1, mynginx-2...와 같이 이름이 부여된다.
  - Pod 생성 순서화
    - 모든 Pod를 동시에 병렬적으로 생성하는 Deployment와는 다르게 StatefulSet은 정해진 순서대로 Pod를 하나씩 생성한다. 
    - 예를 들어 master 노드가 생성되어 실행되고 난 후 slave 노드가 생성되어야 하는 경우 StatefulSet을 통해 Pod의 생성 순서를 정의하면 된다.
  - 개별 Pod에 대한 PVC 관리
    - StatefulSet은 PV를 요청하기 위한 PVC를 템플릿 형태로 정의한다. 따라서 Pod마다 각각 PVC와 PV를 생성하여 관리할 수 있다.
- Deployment, ReplicaSet 비교
  - Deployment, ReplicaSet은 주로 Stateless한 Application을 관리할 때 사용한다. Pod가 생성되는 순서를 지정할 수 없기 때문에 PVC를 이용해 mount할 때 지정 된 Pod를 찾을 수 없다.
  - StateufulSet은 Stateful한 Application을 관리할 때 사용한다.
    ![image](https://user-images.githubusercontent.com/21374902/159690051-5f4a8562-a34f-4a2c-af78-8afb49a44913.png)
    ![image](https://user-images.githubusercontent.com/21374902/159690256-a70e5234-a8d7-4571-95c9-1a1ec8354852.png)
- Stateless vs. Stateful
  - #### Stateless 
    - Process와 Application이 격리된 것으로 간주한다. 과거 Transaction에 대한 정보가 참조되거나 저장되지 않기 때문이다. 각 Transaction은 모두 처음부터 시작된다. CDN, Web, Print Server와 같이 단기 요청을 처리하는 것이다. `검색`하는 것처럼 개별적인 Transaction으로 동작하고 중간에 중단되면 새롭게 시작하면 된다.
    - Apache, Nginx, 검색
  - #### Stateful
    - 이전 Transaction의 Context에 따라 수행되기 때문에 현재 Transaction이 과거 Transaction의 영향을 받는다. 과거 정보를 저장하기 때문에 중간에 중단되어도 그 이전 지점부터 다시 시작할 수 있다. 
    - MariaDB, MongoDB, Banking, Email

![image](https://user-images.githubusercontent.com/21374902/159689716-6ad3570a-74d9-4c2a-b0a4-0fb77946d08f.png)


---



## ResourceQuota
- Namespace에 생성할 수 있고 최대 리소스 제한을 설정할 수 있다.
- ResourceQuota 예제
  <details>
    <summary> 📑 ResourceQuota 예제</summary>

    ```yml
    apiVersion: v1
    kind: ResourceQuota
    metadata:
      name: rq
      namespace: namespace1
    spec:
      hard:
        requests.memory: 5Gi
        limits.memory: 10Gi
    ```
   </details>
   
   <details>
    <summary> 📑 ResourceQuota 예제</summary>

    ```yml
    apiVersion: v1
    kind: ResourceQuota
    metadata:
      name: pod
    spect:
      containers:
        - name: container
          image: nginx
          resources:
            requests:
              memory: 2Gi
            limits:
              memory: 4Gi
    ```
   </details>



---


## LimitRange
- Namespace에 생성할 수 있고 최대 Pod의 리소스를 제한할 수 있다.
- LimitRange 예제
  <details>
    <summary> 📑 LimitRange 예제</summary>

    ```yml
    apiVersion: v1
    kind: LimitRange
    metadata:
      name: lr
    spec:
      limits:
        min:
          memory: 2Gi
        max:
          memory: 5Gi
        maxLimitRequestRatio:
          memory: 2
        defaultRequest:
          memory: 2Gi
        default:
          memory: 3Gi
    ```
   </details>
   
   <details>
    <summary> 📑 LimitRange 예제</summary>

    ```yml
    apiVersion: v1
    kind: LimitRange
    metadata:
      name: pod
    spec:
      containers:
        - name: container
          image: nginx
          resources:
            requests:
              memory: 2Gi
            limits:
              memory: 3Gi
    ```
   </details>


---


## 기타 명령어
- `kubectl get rs -w`
- `watch -n 0.5 kubectl get all`
- `kubectl top pod` : pod의 CPU, Memory 정보를 확인


---   
- Reference
  - [subicura 블로그 - k8s](https://subicura.com/k8s)
  - [Inflearn - 쿠버네티스 입문](https://www.inflearn.com/course/%EC%BF%A0%EB%B2%84%EB%84%A4%ED%8B%B0%EC%8A%A4-%EC%9E%85%EB%AC%B8)
  - [Kubernetes Adminstrator - 장원석](https://github.com/wsjang619/k8s_course)
  - [Kubernetes Documents](https://kubernetes.io/ko/docs)
  - https://nirsa.tistory.com/157