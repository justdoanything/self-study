```
0️⃣ 목표
Docker에 대한 기본 이해
ssh_tunneling 프로그램을 docker를 활용해 구동/배포/관리가 되도록 개발
Gitlab - AWS - docker로 구동하는 배포 시스템을 이해 및 구현
```
```
1️⃣ 물리 머신 vs 가상 머신 vs 도커 컨테이너
```
![image](https://user-images.githubusercontent.com/21374902/147321427-6f4f1bf6-e1b0-450e-bf6b-43fef4cde521.png)
```
2️⃣ Docker  
  . 전가상화, 반가상화는 추가적인 OS 설치는 불가피하기 때문에 성능문제가 존재
  . 이를 개선하기 위해 '프로세스' 격리 방식이 등장
  . 리눅스 환경에선 리눅스 컨테이너가 프로세스 격리시키기 때문에 가볍게 빠르게 동작하고 자원손실도 거의 없습니다.
  . 실행중인 컨테이너에 접속해서 명령어를 실행하고 패키지 설치, 여러개의 프로세스를 백그라운드로 실행할 수 있습니다.
  . CPU, 메모리 사용량을 제한할 수 있고 특정 포트나 디렉토리를 외부와 연결할 수 있습니다.
```
![image](https://user-images.githubusercontent.com/21374902/147167642-1dad5620-3b02-4e83-854d-3595e7feee64.png)
```
3️⃣ Docker Image
  . [이미지]는 컨테이너 실행에 필요한 파일과 설정값을 갖고 있고 변하지 않습니다.
    [컨테이너]는 이미지를 실행한 상태라고 볼 수 있고 추가되거나 변하는 값은 [컨테이너]에 저장합니다.
  . 한 개의 [이미지]로 한 개의 [서버]에서 여러개의 [컨테이너]를 생성해서 실행할 수 있습니다.
  . Docker Image는 실항할 때 필요한 모든 요소들을 갖고있기 때문에 통채로 관리하면 Image의 용량이 너무 커지는 문제가 있었는데 이를 Docker Layer 개념으로 해결했습니다.
```
  ![image](https://user-images.githubusercontent.com/21374902/147167708-010adcfc-cda2-4399-a69a-807ba6d2a690.png)
```
4️⃣ Docker Layer
  . Docker Image는 여러개의 읽기 전용 레이어로 구성이 되고 파일이 추가되거나 수정되면 새로운 레이어가 생성.
  . 예를들어 Ubuntu 이미지가 [A+B+C]의 집합이라면 Ubuntu 기반으로 만든 nginx 이미지는 [A+B+C+nginx]가 되고 이 이미지를 기반으로 webapp를 만들면 [A+B+C+nginx+source] 레이어로 구성이 됩니다.
  . 여기에서 [source]를 수정하면 새로운 [source2] 레이어만 다운받으면 되기 때문에 효율적입니다.
  . Container를 생성할 때도 Layer 방식을 사용하는데 기존 Image Layer 위에 Write/Read Layer를 추가해서 Container가 실행중에 생성하는 파일은 Write/Read Layer에 저장되므로 여러개의 Container를 생성해도 최소한의 용량만 사용합니다.
  . 가상화 방식의 경우엔 큰 이미지를 여러개의 서버에 배포하는데 이를 간단하게 해결한게 Docker Layer 입니다.
```
  ![image](https://user-images.githubusercontent.com/21374902/147167762-342c1f71-014f-435a-bef5-360d4ab4ca89.png)
```
5️⃣ Dockerfile
  . Docker Image는 URL 방식으로 관리하며 Tag를 붙일 수 있습니다.
  . Tag 기능을 잘 이용하면 테스트나 롤백도 쉽게 가능합니다.
```

![image](https://user-images.githubusercontent.com/21374902/147322683-26ab298f-a6fd-4ca6-b2f9-994faf71c75a.png)
![image](https://user-images.githubusercontent.com/21374902/147327131-76c2efb7-e930-4f4d-b319-c796052766c7.png)


```
6️⃣ Windows 10에 개발환경 세팅 (With WSL)
  . Hyper-V 활성화
    > 제어판 > 프로그램 및 기능 > Windows 기능 켜기/끄기 > 'Hyper-V 체크'
  . WSL (Windows Services for Linux) 활성화
    > dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart 
  . WSL Kernel update package 설치 
  . Ubuntu 설치
    > https://docs.microsoft.com/ko-kr/windows/wsl/install-manual
  . Docker Desktop for Windows10 설치
    > General > Use the WSL2 based engine 체크
    > Resources > WSL INTEGRATION > Enable integration with my default WSL distro 체크
  . WSL2 (Ubuntu) 환경에서 개발도구 설치
    > Ubuntu Update
      - sudo apt update
      - sudo apt upgrade -y
      - sudo apt autoremove -y
    > Git
      - sudo apt install git -y
    > AWS CLI
      - sudo apt install python -y
      - sudo apt install python3 -y
      - sudo apt install python3-pip -y
    > SAM CLI
      - pip3 install aws-sam-cli --user --trusted-host pypi.org --trusted-host files.pythonhosted.org
    > Node.js
      - wget https://nodejs.org/dist/latest-v12.x/node-<버전>-linux-x64.tar.gz - P ~/tools/
      - tar xvf ~/tools/node-<버전>-linux-x64.tar.gz -C ~/tools/
      - echo "export PATH=\"\$HOME/tools/node-<버전>-linux-x64/bin:\$PATH\"" >> ~/.profile
      - source ~/.profile
      - npm install -g yarn
```
![image](https://user-images.githubusercontent.com/21374902/147616035-5bb71b64-74e2-490c-bbc6-bb44fbc06ddd.png)
```
7️⃣ 무작정 Docker 따라하기
  . https://github.com/justdoanything/ssh_tunneling
  . Docker를 실행하기 위해선 kernel은 3.10.x 이상, Ubuntu는 14.04 이상을 사용해야 합니다.
  . Docker for Windows를 설치해도 Docker는 Linux 기반 Container 이기 때문에 실제론 가상머신에 설치가 됩니다.
  . 특정 Port나 Directory를 연결하려면 Docker Container를 가상머신에 연결하고 다시 Windows에 연결해하는 작업이 필요한대 이런 부분을 자연스럽게 처리해줍니다.
  . docker version 명령어를 실행하면 Client와 Server 2개가 나오는데 이는 Docker가 하나의 실행파일 이지만 Client/Server 역할을 동시에 합니다.
  . docker client가 docker server로 명령을 전송하고 결과를 받아 터미널에 출력합니다.
```
![image](https://user-images.githubusercontent.com/21374902/147620567-f0f179fd-d97f-4adc-8694-0f7a8ef1a753.png)
```
  . docker run [OPTIONS] IMAGE[:TAG|@DIGEST] [COMMAND] [ARG...]
    -d      detached mode 흔히 말하는 백그라운드 모드
    -p	    호스트와 컨테이너의 포트를 연결 (포워딩)
    -v	    호스트와 컨테이너의 디렉토리를 연결 (마운트)
    -e	    컨테이너 내에서 사용할 환경변수 설정
    –name   컨테이너 이름 설정
    –rm	    프로세스 종료시 컨테이너 자동 제거
    -it	    -i와 -t를 동시에 사용한 것으로 터미널 입력을 위한 옵션
    –link   컨테이너 연결 [컨테이너명:별칭]
  - Ubuntu Container 예제
    docker run ubuntu:16.04
      → ubuntu 이미지가 없으면 자동으로 다운받고 실행하고 다른 명령어를 보내지 않았기 때문에 Container가 생성됐다가 바로 삭제됨
    docker run --rm -it ubuntu:16.04 /bin/bash
  - Redis Container 예제
    ＊ redis는 메모리 기반의 다양한 기능을 가진 스토리지로 6379 포트로 통신
    → docker run -d -p 1234:6379 redis
      (-d 옵션이 없으면 foreground로 실행되서 아무키도 입력할 수 없는 상태가 됨)
      (docker는 1개로 떠있기 때문에 Ubuntu 내에서 1234 포트로 붙는 것과 Windows10 Terminal에서 1234포트로 붙는거 모두 동일한 redis를 사용한다.)
  - Mysql Container 예제
    → docker run -d -p 3306:3306 \
      -e MYSQL_ALLOW_EMPTY_PASSWORD=true \
      --name mysql \
      mysql:5.7
    → mysql -h127.0.0.1 -uroot
  - WordPress Container 예제
    ＊ WordPress는 database가 필요하기 때문에 --link 옵션으로 mysql container에 연결해줍니다.
    → docker run -d -p 8080:80 \
      --link mysql:mysql \
      -e WORDPRESS_DB_HOST=mysql \
      -e WORDPRESS_DB_NAME=wp \
      -e WORDPRESS_DB_USER=wp \
      -e WORDPRESS_DB_PASSWORD=wp \
      wordpress
  - Tensorflow Container 예제
    → docker run -d -p 8888:8888 -p 6006:6006 teamlab/pydata-tensorflow:0.1

  . Container 명령어
    1. docker start {name}
    2. docker stop {name}
    3. docker rm {name}
    4. docker rename {old} {new}
    5. docker ps -a
    6. docker images
    7. docker pull {image}
    8. docker rmi {image}
    9. docker logs {container}
       docker logs --tail 10 {container}
       docker logs -f {container}
    10. docker exec {container}
       docker exec -it mysql /bin/bash
       docker exec -it mysql mysql -uroot  
       (docker run은 컨테이너를 실행하지만 exec는 실행중인 컨테이너에 명령어를 던진다.)
```
```
8️⃣ Container Update
  . Docker Containter를 업데이트 하기 위해선 새버전의 Image를 다운 받고(pull) 기존 Container를 중지(stop) 후 삭제(rm)한 후 새로운 Image를 기반으로 다시 실행(run)해야 합니다.
  → 이렇게 할경우 Container 내 데이터가 모두 삭제되는 문제가 발생
    (mysql 이라면 database 내 데이터 전부 등)
  . 이를 해결하기 위해선 삭제되어선 안되는 데이터들을 AWS S3와 같은 클라우드 서비스를 이용하거나 Data Volumes을 Container에 추가해서 사용하는 방법이 있습니다.
  . run 명령어 중에 -v 옵션을 주면 Host의 Directory를 Mount해서 사용할 수 있습니다.
    docker run -d -p 3306:3306 \
      -e MYSQL_ALLOW_EMPTY_PASSWORD=true \
      --name mysql \
      -v /my/own/data/datadir:/var/lib/mysql \
      mysql:5.7
```
![image](https://user-images.githubusercontent.com/21374902/147638958-a81d9bf3-8645-4b4c-b5f7-39575f9e0623.png)
```yml
9️⃣ DockerCompose
### Docker의 복잡한 설정을 간편하게 하기 위해서 yml방식의 설정파일을 이용한 Docker Compose를 사용합니다.
### docker-compose.yml 작성 후 docker-compose up 명령어 실행
### 예제코드    
    version: '2'
    services:
      db:
        image: mysql:5.7
        volumes:
          - db_data:/var/lib/mysql
        restart: always
        environment:
          MYSQL_ROOT_PASSWORD: wordpress
          MYSQL_DATABASE: wordpress
          MYSQL_USER: wordpress
          MYSQL_PASSWORD: wordpress
      wordpress:
        depends_on:
          - db
        image: wordpress:latest
        volumes:
          - wp_data:/var/www/html
        ports:
          - "8000:80"
        restart: always
        environment:
          WORDPRESS_DB_HOST: db:3306
          WORDPRESS_DB_PASSWORD: wordpress
    volumes:
        db_data:
        wp_data:
  
### docker-compose 더 자세히 알아보기
추후 작성 필요
```
```yml
1️⃣0️⃣ gitlab-ci.yml 예제
### gitlab-docker-aws 환경에 DEV, STG, PROD 라는 3개의 환경을 세팅하여 사용할 때 사용했던 gitlab-ci.yml
    
### docker image 기반으로 동작하도록 설정
    image: docker:latest
    
### 환경 변수 등 아래 명령어에서 공통으로 사용하는 값 세팅
    variables:
      DEV_ECR: {ECR Repository 주소}
      STG_ECR: {ECR Repository 주소}
      PROD_ECR: {ECR Repository 주소}
      MAVEN_OPTS: -Dmaven.repo.local=${CI_PROJECT_DIR}/.mr
### ECR : Amazon Elastic Container Registry
###       ECR에 Repository, 정책, 토큰, 이미지 등을 미리 설정해두고 그 설정을 불러서 동작하도록 설정
### ECR을 이용해서 EC2에 새로운 ECS를 만드는 순서
### Ready Docker image → Create ECR repository → Connect EC2 → Pull Docker image → Create new ECS with docker image → Create service

### 
    cache:
      paths:
        - .m2/   
### 파이프라인 단계의 이름과 순서
### Job이 실행되는 단계를 의미하며 동일한 stage 안에 있는 JOB들은 병렬적으로 수행
    stages: 
#     - test
      - build
      - package
#     - deploy   # build 후 자동으로 deploy까지 할 때 사용
    
### stages에 있는 build가 수행될 때 참조하는 스크립트
    build:
      image: maven:3-jdk-8
      stage: build
      only:
        - triggers
      script: "mvn install"
      artifacts:
        paths:
          - target/*.jar
### DEV 환경에 docker build 될 때 참조하는 스크립트
    DEV-docker-build:
      stage: package
      only:
        - triggers
      except:
        - /^stage.*$/
        - /^master.*$/
      before_script:
        - NEW_IMAGE_NAME = ${environment}:$(echo ${CI_COMMIT_REF_NAME} | sed "s/[^[[:alnum]]//g")-${CI_COMMIT_SHA}])
      script:
        - apk add --no-cache curl jq python3 py3-pip
        - pip3 install awscli
        - $(aws ecr get-login --no-include-email --region {aws region})
        - docker build -t $NEW_IMAGE_NAME .
        - docker push $NEW_IMAGE_NAME
        - docker rmi $NEW_IMAGE_NAME

### stages에 deploy를 수행할 때 참조하는 스크립트
    DEV-deploy:
      image: sppark/curl-jq:v1
      stage: deploy
      only:
        - triggers
      except:
        # only dev
        - /^stage.*$/
        - /^master.*$/
      before_script:
        - NEW_IMAGE_TAG=$(echo ${CI_COMMIT_REF_NAME} | sed "s/[^[[:alnum:]]//g")-${CI_COMMIT_SHA}
      script:
        - "RESULT=\"$(curl -s -o /dev/null -w \"%{http_code}\" --request POST -H \"access_token: ${ACCESS_TOKEN}\" \"${CICD_SERVICE_URL}/app/19/serverGroup/43/deploy?commit=$NEW_IMAGE_TAG\")\""
        - echo ${RESULT}
# STG, PROD 환경별 작성    
  환경별 값은 거의 동일하고 except 부분만 달라진다.
    STG-docker-build:
    STG-deploy: ...
    PROD-docker-build: ...
    PROD-deploy: ...
    
```
```
1️⃣2️⃣ 
```
```
*️⃣ 참고자료
```
- Docker Docs : [Docker Docs](https://docs.docker.com/get-started/overview/)
- Gitlab Docs : [Gitlab Docs](https://docs.gitlab.com/ee/ci/yaml/index.html#stages)
- Docker Part : [Logosubicura's blog](https://subicura.com/2017/01/19/docker-guide-for-beginners-1.html)
- gitlab-ci.yml Part : [otrodevym's tistory](https://otrodevym.tistory.com/entry/Gitlab-CICD-gitlab-ciyml-%EC%82%AC%EC%9A%A9-%EB%B0%A9%EB%B2%95)


```
5️⃣ Docker와 Kubernetes

```

