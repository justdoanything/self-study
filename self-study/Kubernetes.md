kubernetes
===

목차
---

---

## 1. 학습목표
Docker를 공부했던 내용을 기반으로 K8S의 개념과 기능을 공부한다. 실습 위주로 공부하고 최종적으로 AWS 환경에서 Kubernetes를 설정하고 배포한다.

---

## 2. 실습환경 세팅하기
_Kubernetes Adminstrator_ 교육을 들었을 땐 AWS Cloud9에서 1개의 Master, 2개의 Worker 환경을 별도로 제공받아서 실습했었습니다. 하지만 Local 환경에선 n개의 환경을 각각 구축하기 까다롭기 때문에 `minikube`을 사용해서 구성하도록 하겠습니다.

Kubernetes Cluster를 실행하려면 최소한 scheduler, controller, api-server, etcd, kubelet, kube-proxy를 설치해야 하고 필요에 따라 dns, ingress controller, storage class등을 설치해야 합니다. 실습에선 `minukube`로 대체합니다.

- minukube & kubectl 설치(windows10)
  - ### 💥 Docker Desktop을 사용할 수 없기 때문에 WSL2 환경에 세팅
    - 참고 : [Docker Desktop 없이 Docker 사용하기](https://github.com/justdoanything/self-study/blob/main/self-study/Docker.md#2%EF%B8%8F%E2%83%A30%EF%B8%8F%E2%83%A3-Docker-Desktop-%EC%97%86%EC%9D%B4-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0-(Windows10))
    - ~~Hyper-V 활성화~~
      - ~~Check : `DISM /Online /Enable-Feature /All /FeatureName:Microsoft-Hyper-V`~~
      - ~~On : `bcdedit /set hypervisorlaunchtype off`~~
      - ~~Off : `bcdedit /set hypervisorlaunchtype auto`~~
    - ~~minikube 설치 : [minikube-installer.exe](https://github.com/kubernetes/minikube/releases/latest/download/minikube-installer.exe)~~
    - ~~💥memory 할당 문제로 `minikube start --driver=hyperv`가 안될 경우, 가상 메모리 설정 필요~~
      - ~~제어판 > 시스템 및 보안 > 시스템 > 고급 시스템 설정~~
      - ~~고급 탭 > '성능' 영역에 '설정(S)' > 고급 탭 > '가상 메모리' 영역에 '변경(C)'~~
      - ~~'모든 드라이브에 대한 페이징 파일 크기 자동 관리(A)' 체크 해제 > '사용자 지정 크기(C)' 선택 > 처음 크기 : 4096, 최대 크기 : 8192 > 설정 > 확인 > 재부팅~~ \
      ![image](https://user-images.githubusercontent.com/21374902/157142064-ccdc512f-d2d5-4c29-8ece-1414734761a2.png)
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




---

강사 : 장원석
강의이름 : Kubernetes Adminstrator

https://github.com/wsjang619/k8s_course

watch -n 0.5 kubectl get all




- Reference
  - https://subicura.com/k8s
  - https://www.inflearn.com/course/%EC%BF%A0%EB%B2%84%EB%84%A4%ED%8B%B0%EC%8A%A4-%EC%9E%85%EB%AC%B8
  - https://github.com/wsjang619/k8s_course