목차
---
0. [목표](#0️⃣-목표)
1. [물리 머신 vs 가상 머신 vs 도커 컨테이너](#물리-머신-vs-가상-머신-vs-도커-컨테이너)
2. [Docker](#2️⃣-Docker)
3. [Docker Image](#3️⃣-Docker-Image)
4. [Docker Layer](#4️⃣-Docker-Layer)
5. [Dockerfile](#5️⃣-Dockerfile)
6. [Windows 10에 개발환경 세팅 (With WSL)](#6️⃣-Windows-10에-개발환경-세팅-(With-WSL))
7. [무작정 Docker 따라하기](#7️⃣-무작정-Docker-따라하기)
8. [Container Update](#8️⃣-Container-Update)
9. [Docker Compose](#9️⃣-Docker-Compose)
10. [Docker Image 생성](#1️⃣0️⃣-Docker-Image-생성)
11. [Dockerfile 명령어](#1️⃣1️⃣-Dockerfile-명령어)
12. [Docker Build Log 분석](#1️⃣2️⃣-Docker-Build-Log-분석)
13. [Dockerfile Build](#1️⃣3️⃣-Dockerfile-Build)
14. [Docker와 Kubernetes](#1️⃣4️⃣-Docker와-Kubernetes)
15. [Docker Registry](#1️⃣5️⃣-Docker-Registry)
16. [Docker Hub](#1️⃣6️⃣-Docker-Hub)
17. [Docker Deploy](#1️⃣7️⃣-Docker-Deploy)
18. [gitlab-ci.yml 예제](#1️⃣8️⃣-gitlab-ci.yml-예제)
19. [Gitlab에 maven build 및 docker build 로그 분석](#1️⃣9️⃣-Gitlab에-maven-build-및-docker-build-로그-분석)
20. [Docker Desktop 없이 사용하기 (Windows10)](#2️⃣0️⃣Docker-Desktop-없이-사용하기-(Windows10))

＊ [참고자료](#*️⃣-참고자료)

---
0️⃣ 목표
===
- Docker에 대한 기본 이해
- ssh_tunneling 프로그램을 docker를 활용해 구동/배포/관리가 되도록 개발
- Gitlab - AWS - docker로 구동하는 배포 시스템을 이해 및 구현
---
1️⃣ 물리 머신 vs 가상 머신 vs 도커 컨테이너
===
![image](https://user-images.githubusercontent.com/21374902/147321427-6f4f1bf6-e1b0-450e-bf6b-43fef4cde521.png)



---
2️⃣ Docker  
===
- 전가상화, 반가상화는 추가적인 OS 설치는 불가피하기 때문에 성능문제가 존재
- 이를 개선하기 위해 '프로세스' 격리 방식이 등장
- 리눅스 환경에선 리눅스 컨테이너가 프로세스 격리시키기 때문에 가볍게 빠르게 동작하고 자원손실도 거의 없습니다.
- 실행중인 컨테이너에 접속해서 명령어를 실행하고 패키지 설치, 여러개의 프로세스를 백그라운드로 실행할 수 있습니다.
- CPU, 메모리 사용량을 제한할 수 있고 특정 포트나 디렉토리를 외부와 연결할 수 있습니다.
![image](https://user-images.githubusercontent.com/21374902/147167642-1dad5620-3b02-4e83-854d-3595e7feee64.png)



---
3️⃣ Docker Image
===
- `Docker Image`는 컨테이너 실행에 필요한 파일과 설정값을 갖고 있고 변하지 않습니다.
- `Docker Container`는 이미지를 실행한 상태라고 볼 수 있고 추가되거나 변하는 값은 `Container`에 저장합니다.
- 한 개의 `Image`로 한 개의 `Server`에서 여러개의 `Container`를 생성해서 실행할 수 있습니다.
- `Docker Image`는 실항할 때 필요한 모든 요소들을 갖고있기 때문에 통채로 관리하면 `Image`의 용량이 너무 커지는 문제가 있었는데 이를 `Docker Layer` 개념으로 해결했습니다.

![image](https://user-images.githubusercontent.com/21374902/147167708-010adcfc-cda2-4399-a69a-807ba6d2a690.png)



---
4️⃣ Docker Layer
===
- Docker Image는 여러개의 읽기 전용 레이어로 구성이 되고 파일이 추가되거나 수정되면 새로운 레이어가 생성됩니다. (참고 : [Docker Build Log 분석](#1️⃣2️⃣-Docker-Build-Log-분석))
- 예를들어 Ubuntu 이미지가 [A+B+C]의 집합이라면 Ubuntu 기반으로 만든 nginx 이미지는 [A+B+C+nginx]가 되고 이 이미지를 기반으로 webapp를 만들면 [A+B+C+nginx+source] 레이어로 구성이 됩니다.
- 여기에서 [source]를 수정하면 새로운 [source2] 레이어만 다운받으면 되기 때문에 효율적입니다.
→ 이미 만들어 놓은 [A+B+C+nginx] 레이어에 새로운 [source2] 만 더해서 Image를 만들기 때문에 [A+B+C]와 [nginx]를 합치는 작업을 반복하지 않습니다. (Like Caching)\
(참고 : [Docker Build](#1️⃣3️⃣-Dockerfile-Build))
- Container를 생성할 때도 Layer 방식을 사용하는데 기존 Image Layer 위에 Write/Read Layer를 추가해서 Container가 실행중에 생성하는 파일은 Write/Read Layer에 저장되므로 여러개의 Container를 생성해도 최소한의 용량만 사용합니다.
- 가상화 방식의 경우엔 큰 이미지를 여러개의 서버에 배포하는데 이를 간단하게 해결한게 Docker Layer 입니다.
![image](https://user-images.githubusercontent.com/21374902/147167762-342c1f71-014f-435a-bef5-360d4ab4ca89.png)



---
5️⃣ Dockerfile
===
- Docker Image는 URL 방식으로 관리하며 Tag를 붙일 수 있습니다.
- Tag 기능을 잘 이용하면 테스트나 롤백도 쉽게 가능합니다.
- Docker Image를 만드는 규칙을 정해놓고 `docker build` 명령어를 통해서 image를 쉽게 생성할 수 있습니다. (참고 : [Docker Image 생성](#1️⃣0️⃣-Docker-Image-생성))
![image](https://user-images.githubusercontent.com/21374902/147322683-26ab298f-a6fd-4ca6-b2f9-994faf71c75a.png)
![image](https://user-images.githubusercontent.com/21374902/147327131-76c2efb7-e930-4f4d-b319-c796052766c7.png)



---
6️⃣ Windows 10에 개발환경 세팅 (With WSL)
===
- Hyper-V 활성화
  - 제어판 > 프로그램 및 기능 > Windows 기능 켜기/끄기 > 'Hyper-V 체크'
- WSL (Windows Services for Linux) 활성화
  - `dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart` 명령어 실행
- WSL Kernel update package 설치 
- Ubuntu 설치
  - https://docs.microsoft.com/ko-kr/windows/wsl/install-manual
- Docker Desktop for Windows10 설치
  - General > Use the WSL2 based engine 체크
  - Resources > WSL INTEGRATION > Enable integration with my default WSL distro 체크
- WSL2 (Ubuntu) 환경에서 개발도구 설치
  - Ubuntu Update
    - `sudo apt update`
    - `sudo apt upgrade -y`
    - `sudo apt autoremove -y`
  - Git
    - `sudo apt install git -y`
  - AWS CLI
    - `sudo apt install python -y`
    - `sudo apt install python3 -y`
    - `sudo apt install python3-pip -y`
  - SAM CLI
    - `pip3 install aws-sam-cli --user --trusted-host pypi.org --trusted-host files.pythonhosted.org`
  - Node.js
    - `wget https://nodejs.org/dist/latest-v12.x/node-v12.22.9-linux-x64.tar.gz -P ~/tools/`
    - `tar xvf ~/tools/node-v12.22.9-linux-x64.tar.gz -C ~/tools/`
    - `echo "export PATH=\"\$HOME/tools/node-v12.22.9-linux-x64/bin:\$PATH\"" >> ~/.profile`
    - `source ~/.profile`
    - `npm install -g yarn`\
    ![image](https://user-images.githubusercontent.com/21374902/147616035-5bb71b64-74e2-490c-bbc6-bb44fbc06ddd.png)



---
7️⃣ 무작정 Docker 따라하기
===
- Docker에 올릴 대상 프로그램 : https://github.com/justdoanything/ssh_tunneling (Completed)
- Docker를 실행하기 위해선 kernel은 3.10.x 이상, Ubuntu는 14.04 이상을 사용해야 합니다.
- Docker for Windows를 설치해도 Docker는 Linux 기반 Container 이기 때문에 실제론 가상머신에 설치가 됩니다.
- 특정 Port나 Directory를 연결하려면 Docker Container를 가상머신에 연결하고 다시 Windows에 연결해하는 작업이 필요한대 이런 부분을 자연스럽게 처리해줍니다.
- docker version 명령어를 실행하면 Client와 Server 2개가 나오는데 이는 Docker가 하나의 실행파일 이지만 Client/Server 역할을 동시에 합니다.
- docker client가 docker server로 명령을 전송하고 결과를 받아 터미널에 출력합니다.
![image](https://user-images.githubusercontent.com/21374902/147620567-f0f179fd-d97f-4adc-8694-0f7a8ef1a753.png)

- `docker run [OPTIONS] IMAGE[:TAG|@DIGEST] [COMMAND] [ARG...]`
  값 | 의미
  ---|:---
  -d     | detached mode 흔히 말하는 백그라운드 모드
  -p     | 호스트와 컨테이너의 포트를 연결 (포워딩)
  -v     | 호스트와 컨테이너의 디렉토리를 연결 (마운트)
  -e     | 컨테이너 내에서 사용할 환경변수 설정
  --name | 컨테이너 이름 설정
  -rm    | 프로세스 종료시 컨테이너 자동 제거
  -it    | -i와 -t를 동시에 사용한 것으로 터미널 입력을 위한 옵션
  -link  | 컨테이너 연결 [컨테이너명:별칭]
  -w     | Container에 작업 경로를 변경
- Ubuntu Container 실행 예제
  - `docker run ubuntu:16.04`
    - ubuntu 이미지가 없으면 자동으로 다운받고 실행하고 다른 명령어를 보내지 않았기 때문에 Container가 생성됐다가 바로 삭제됨
  - `docker run --rm -it ubuntu:16.04 /bin/bash`
- Redis Container 실행 예제
  - `docker run -d -p 1234:6379 redis`
    - _redis는 메모리 기반의 다양한 기능을 가진 스토리지로 6379 포트로 통신_
    - _-d 옵션이 없으면 foreground로 실행되서 아무키도 입력할 수 없는 상태가 됨
      (docker는 1개로 떠있기 때문에 Ubuntu 내에서 1234 포트로 붙는 것과 Windows10 Terminal에서 1234포트로 붙는거 모두 동일한 redis를 사용한다._
- Mysql Container 실행 예제
  - `docker run -d -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=true --name mysql mysql:5.7`
  - `mysql -h127.0.0.1 -uroot`
- WordPress Container 예제
  - `docker run -d -p 8080:80 --link mysql:mysql -e WORDPRESS_DB_HOST=mysql -e WORDPRESS_DB_NAME=wp -e WORDPRESS_DB_USER=wp -e WORDPRESS_DB_PASSWORD=wp wordpress`
- Tensorflow Container 예제
  - `docker run -d -p 8888:8888 -p 6006:6006 teamlab/pydata-tensorflow:0.1`
- Container 관련 명령어
  값 | 의미
  ---|:---
  docker start {container}  | Docker Container 실행
  docker stop {container}   | Docker Container 중단
  docker rm {container}     | Docker Container 제거
  docker rename {old} {new} | Docker Container 이름변경
  docker ps -a              | Docker Container 전체 목록
  docker images             | Docker Image 목록
  docker search {image}     | Docker Hub에서 Image 검색
  docker pull {image}       | Docker Hub에서 Image 내려받기
  docker rmi {image}        | Docker Image 제거
  docker logs {container}   | Docker Container 로그보기
  docker exec {container}   | Docker Container 명령어 실행
  - `docker exec -it mysql /bin/bash` : 실행 중인 mysql container에 /bin/bash 명령어 수행
  - `docker rm -v $(docker ps -a -q -f status=exited)` : exited 상태의 container 모두 삭제
  - `docker logs --tail 10 {container}` : Container Log 10줄만보기
  - `docker logs -f {container}` : Container Log 실시간 출력




---
8️⃣ Container Update
===
- Docker Containter를 업데이트 하기 위해선 새버전의 Image를 다운 받고(pull) 기존 Container를 중지(stop) 후 삭제(rm)한 후 새로운 Image를 기반으로 다시 실행(run)해야 합니다.
  - 이렇게 할경우 Container 내 데이터가 모두 삭제되는 문제가 발생
    (mysql 이라면 database 내 데이터 전부 등)
- 이를 해결하기 위해선 삭제되어선 안되는 데이터들을 AWS S3와 같은 클라우드 서비스를 이용하거나 Data Volumes을 Container에 추가해서 사용하는 방법이 있습니다.
- run 명령어 중에 -v 옵션을 주면 Host의 Directory를 Mount해서 사용할 수 있습니다.
  - `docker run -d -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=true --name mysql -v /my/own/data/datadir:/var/lib/mysql mysql:5.7`
![image](https://user-images.githubusercontent.com/21374902/147638958-a81d9bf3-8645-4b4c-b5f7-39575f9e0623.png)



---
9️⃣ Docker Compose
===
- Docker의 복잡한 설정을 간편하게 하기 위해서 yml방식의 설정파일을 이용한 Docker Compose를 사용합니다.
- 여러 개의 Docker Container를 정의하고 한번에 실행/중지 시킬 때 사용한다.
- YAML 파일에 Service에 대한 환경설정을 모두 정의해놓고 Single 명령어를 통해 실행한다.
- Docker Compose을 사용하는 일반적인 순서
  - Application 환경은 `Dockerfile`에 작성해서 어디서든지 실행할 수 있도록 합니다.
  - Application을 구성하는 Service는 `docker-compose.yml`에 작성해서 각각 독립된 환경(Container)에서 실행할 수 있도록 합니다.
  - `docker-compose up` 명령어를 통해 `docker-compose.yml`에 정의한대로 Container를 생성 및 실행합니다.
- `docker-compose up` 명령어로 서비스들을 한번에 실행하고 `docker-compose down`으로 한번에 내릴 수 있습니다.

```shell
### 기존 docker 명령어
docker run -dp 3000:3000 \
  -w /app -v ${PWD}:/app \
  --network todo-app \
  -e MYSQL_HOST=mysql \
  -e MYSQL_USER=root \
  -e MYSQL_PASSWORD=secret \
  -e MYSQL_DB=todos \
  node:12-alpine \
  sh -c "yarn install && yarn run dev"

docker run -d \
  --network todo-app --network-alias mysql \
  -v todo-mysql-data:/var/lib/mysql \
  -e MYSQL_ROOT_PASSWORD=secret \
  -e MYSQL_DATABASE=todos \
  mysql:5.7

```

```yml
### 변환한 docker-compose.yml 파일
version: "3.8"

services:
  app:
    image: node:12-alpine
    command: sh -c "yarn install && yarn run dev"
    ports:
      - 3000:3000
    working_dir: /app
    volumes:
      - ./:/app
    environment:
      MYSQL_HOST: mysql
      MYSQL_USER: root
      MYSQL_PASSWORD: secret
      MYSQL_DB: todos

  mysql:
    image: mysql:5.7
    volumes:
      - todo-mysql-data:/var/lib/mysql
    environment: 
      MYSQL_ROOT_PASSWORD: secret
      MYSQL_DATABASE: todos

volumes:
  todo-mysql-data:
```
  - `docker-compose up` 명령어를 수행하면 아래와 같이 단계별로 진행
    - Set Network To Upload Services
    - Link Volumns
    - Pull Images
    - Build Images
    - Run Services
  - `-p` : 프로젝트명을 부여해서 하나의 Application을 격리된 여러개의 환경에서 서비스를 할 수 있습니다.
    - `docker-compose -p first_app up`
    - `docker-compose -p second_app up`
  - docker-compose.yml 파일에서 공통된 환경변수를 사용하기 위해선 `.env` 파일에 설정합니다.
    ```sh
    $ cat .env     # 환경변수 파일
    TAG=v1.5

    $ cat docker-compose.yml   # compose 파일
    version: '3'
    services:
      web:
        image: "webapp:${TAG}"$
    ```
  - `--env-file` 옵션으로 환경별로 변수를 만들고 세팅할 수 있습니다.
  `docker-compose --env-file ./config/.env.dev up`

---
1️⃣0️⃣ Docker Image 생성
===
- Sinatra 웹 어플리케이션 예제
  - ruby 폴더를 생성하고 아래 파일을 작성
  ![image](https://user-images.githubusercontent.com/21374902/147998926-91891017-44e7-4dd8-a488-4afcc18b2587.png)
  - ruby 실행
    `docker run --rm -p 4567:4567 -v $PWD:/usr/src/app -w /usr/src/app ruby bash -c "bundle install && bundle exec ruby app.rb -o 0.0.0.0"`
  - 위 명령어처럼 한번에 실행하면 SSL 에러와 Server handler not found 에러가 발생
    아래와 같이 명령어를 순차적으로 실행
    (기존에 Gemfile.lock 파일이 있으면 삭제 후 진행)\
    `sudo apt install ruby-bundler`\
    `bundle install`\
    `bundle exec ruby app.rb`
  ```
  💥 Trouble Shooting
    1. Gemfile에 source 부분을 https 로 작성하면 SSL Exception 발생
      → rubygems.org는 Fastly 라는 CDN provider를 사용하는데 Fastly에서 TLS 1.2 으로 업데이트 하면서 인증이 필요하게됨.
      → 해당 에러도 Container 내에서 명령어가 실행될 때 발생하는 에러로 아래와 비슷한 http/https 관련 에러일거라고 파악.

    2. source 부분을 http로 수정 후 Container로 ruby를 실행하면 Server handler not fund 에러 발생
      (1) docker run을 할 때 바로 bash 명령어를 실행하지 않고 직접 들어가서 명령어를 하나씩 실행해봄.
        docker run --rm -p 4567:4567 -v $PWD:/usr/src/app -w /usr/src/app --name ruby -it ruby /bin/bash
          bundle install (성공)
          bundle exec ruby app.rb -o 0.0.0.0
        → Server handler (thin,puma,reel,HTTP,webrick) not found. (RuntimeError) 에러 발생
          Gemfile에 rubygems.org로 접근할 때 에러 발생.
      (2) Container 내에서 gem install thin, puma, reel, http, webrick 명령어 수행 후 ruby 다시 실행
        → 같은 에러 발생
      (3) Container 내에서 web protocol 자원을 사용하지 못하는 것으로 생각함
    3. 마땅한 해결책은 찾지 못했고 나중에 시간이 되면 다시 찾아볼 예정.
      이후엔 내가 만든 imaage, container로 web 통신을 할 예정이니까 그때 다시 시도해보기로 함.
  ```
![image](https://user-images.githubusercontent.com/21374902/148023517-60aac2f4-491b-42d8-8c30-08b2ea858993.png)

- Docker Image를 만들기 위해선 Dockerfile 이라는 이미지 빌드용 DSL(Domain Specific Language) 파일을 사용
  - 예제 작업 순서 : ubuntu 설치 → ruby 설치 → 소스 복사 → Gem 패키지 설치 → Sinatra 서버 실행
  - Shell Script 방식
    - Ubuntu 실행 후 아래 명령어를 수행
      ```shell
      # 1. ubuntu 설치 (패키지 업데이트)
      apt-get update

      # 2. ruby 설치
      apt-get install ruby
      gem install bundler

      # 3. 소스 복사
      mkdir -p /usr/src/app
      scp Gemfile app.rb root@ubuntu:/usr/src/app

      # 4. Gem 패키지 설치
      bundle install

      # 5. Sinatra 서버 실행
      bundle exec ruby app.rb
      ```
  - Dockerfile
    - Dockerfile 작성
      ```dockerfile
      # 1. ubuntu 설치 (패키지 업데이트 + 만든사람 표시)
      FROM ubuntu:16.04
      MAINTAINER yongwoo@lgcns.com
      RUN apt-get update

      # 2. ruby 설치
      RUN apt-get -y install ruby
      RUN gem install bundler

      # 3. 소스 복사
      COPY . /usr/src/app  # Gemfile, app.rb가 있는 경로에서 Container에 /usr/src/app 로 복사

      # 4. Gem 패키지 설치 (실행 디렉토리 설정)
      WORKDIR /usr/src/app  # 위에서 설정한 Container 경로와 같아야함
      RUN bundle install

      # 5. Sinatra 서버 실행 (Listen 포트 설정)
      EXPOSE 4567
      CMD bundle exec ruby app.rb -o 0.0.0.0
      ```  
    - Dockerfile 기반으로 image 생성\
    `docker build -t app .`
    - Docker Image 실행\
      `docker run -d -p 8080:4567 app`
    - ruby의 base image를 사용하면 훨씬 간략하게 작성할 수 있습니다.
      ```dockerfile
      FROM ruby:2.3
      MAINTAINER subicura@subicura.com
      ```
![image](https://user-images.githubusercontent.com/21374902/148030522-87816648-ab0a-4586-88c0-8cfd9f5d36f9.png)



---
1️⃣1️⃣ Dockerfile 명령어
===
명령어 | 의미
:---|:---|
FROM        | (필수) base image 지정. 다양한 base image는 Docker hub에서 확인 가능
MAINTAINER  | 관리하는 사람의 이름 또는 이메일 정보를 기입
COPY        | file, directory를 이미지로 복사. directory가 없으면 자동으로 생성.
ADD         | OPY 보다 능동적으로 파일 처리 가능. src에 file 대신 URL을 넣을 수 있고 압축파일을 넣으면 자동으로 압축을 해제해서 복사.
RUN         | 명령어를 그대로 실행. /bin/bash -c 뒤에 명령어를 실행하는 방식.
CMD         | Docker Container가 실행되었을 때 실행되는 명령어. Build 할때는 실행되지 않으며 여러개의 CMD가 존재하면 맨마지막 CMD만 실행
WORKDIR     | RUN, CMD, ADD, COPY 등이 실행된 기본 Directory를 지정. 각 명령어에 대해서 계속 실행되기 때문에 "RUN cd /path"를 실행해도 다음 명령어에선 기본 Directory에서 실행됨.
EXPOSE      | Docker Container가 실행되었을 때 요청을 기다리고 있는 포트(Listen Port)를 지정.
VOLUME      | Container 외부에 file system을 mount 할 때 사용. 필수는 아니지만 설정해주는 것이 좋음.
ENV         | Container에서 사용할 환경변수를 지정. -e 옵션을 사용하면 기존값을 Overriding하여 사용함.
- RUN, CMD, ENTRYPOINT 의 차이점
  - RUN
    - 새롭게 생성된 Layer 위에서 실행
    - Dockerfile로부터 Docker Image를 Build 할 때 수행
    - 주로 환경에 Package 등을 설치할 때 사용
  - CMD
    - Image로부터 Container를 생성했을 때 최초로 수행
    - Build 할때는 실행되지 않으며 여러개의 CMD가 존재하면 맨마지막 CMD만 실행
  - ENTRYPOINT
    - docker run이나 Container를 start할 때 Container가 수행되고 최초로 실행할 명령어를 지정

  💥 CMD는 docker run 일 때만 수행되고 ENTRYPOINT는 Container가 시작할때마다 수행된다.\
  💥 Container 실행 후 반복적으로 수행해야하는 명령어가 있다면 별도의 shell 파일을 만든 후 `ENTRYPOINT ["sh", "entrypoint.sh"]` 로 하도록 Dockfile 작성




---
1️⃣2️⃣ Docker Build Log 분석
===
- 임시 컨테이너 생성 → 명령어 수행 → 이미지로 저장 → 임시 컨테이너 삭제 → 새로 만든 이미지 기반으로 임시 컨테이너 생성 → 명렁어 수행 → 이미지 저장 → 임시 컨테이너 삭제 → ... (반복)
- Sending build context to Docker daemon  5.12 kB\
  `→ Docker는 Client/Server로 구성되어 있기 때문에 Client에서 Server(Demon)으로 파일을 전송`\
  Step 1/10 : FROM ubuntu:16.04\
  `→ Dockerfile에서 첫번째 명령어를 실행합니다.`\
  ---> f49eec89601e\
  `→ 명령어 실행 결과를 image로 저장합니다. (ubuntu image의 ID가 표시)`\
  Step 2/10 : MAINTAINER subicura@subicura.com\
  `→ 두번째 명령어 실행`\
  ---> Running in f4de0c750abb\
  `→ 이전에 생성된 image [f49eec89601e] 기반으로 생성한 Container [f4de0c750abb]에서 명령어를 실행`\
  ---> 4a400609ff73\
  `→ 명령어 수행 결과를 또다른 새로운 image로 저장 [4a400609ff73]`\
  Removing intermediate container f4de0c750abb\
  `→ 임시 Container [f4de0c750abb] 삭제`\
  Step 3/10 : RUN apt-get -y update\
  `→ 세번째 명령어를 실행`\
  ...\
  Successfully built 20369cef9829\
  `→ 최종적으로 성공한 image ID를 출력`



---
1️⃣3️⃣ Dockerfile Build
===
- 명령어를 실행할 때마다 image layer를 저장하고 다시 빌드할 때 Dockerfile이 변경되지 않았으면 기존에 저장한 image를 캐시처럼 그대로 사용합니다.
- Dockerfile을 한줄씩 실행할 때 변경되는 부분이 있으면 캐시가 깨지게되고 변경된 부분 이후는 같은 명령어라도 캐시를 사용하지 않고 다 새로 동작합니다.
- 따라서 자주 변경될 것 같은 명령어는 아래로 빼고 install 등 시간이 오래 걸리는 명령어는 위로 올려서 캐시를 활용하는 것이 빌드 시간을 줄일 수 있는 방법입니다.
- `-qq`, `--no-doc`, `--no-ri` 옵션을 부여해서 불필요한 로그를 출력하지 않게 하거나 문서를 생성하지 않게 할 수 있습니다.
- Docker Layer의 개수가 제한되어 있을 수도 있기 때문에 너무 많은 명령어는 좋지 않습니다.
- 아래 사진을 보면 같은 동작을 하지만 훨씬 간략하게 짤 수 있습니다.
## before
![image](https://user-images.githubusercontent.com/21374902/148367332-a66c3502-09ce-4909-878a-af5208135d4a.png)
## after
![image](https://user-images.githubusercontent.com/21374902/148367264-973aa642-75e0-4e6f-9e2d-db2cb7727812.png)



---
1️⃣4️⃣ Docker와 Kubernetes
===
- `Docker` : 한 환경에서 Process 단위로 구분하여 실행\
- `Kubernetes` : Container Orchestration Tool\
  (Orchestration Tool : Kubernetes, Docker Swarm, ECS, Normad, ...)
- Docker는 `기술적인 개념이자 도구`이고\
  Kubernetes는 `Docker를 관리하는 도구`라고 볼 수 있다.
- Image를 만들고 Container를 올리는 것은 `Docker`\
  만들어진 Container를 관리하는 것은 `Kubernetes`
- Kubernetes는 다수의 Container를 실행 및 관리하며 Service 단위로 관리할 수 있게 해준다.
  1. Self-Healing : Container가 죽으면 자동으로 재시작
  2. Load balancing : 새로운 Container를 만들고 죽이며 부하에 대한 Control
  3. Fault tolerance-FT Service : 무중단 서비스
  4. Vendor Lock In Solution : 구동하는 Cloud 환경이나 여러 호환성에 대해서 독립적으로 동작



---
1️⃣5️⃣ Docker Registry
===
- Build한 Image를 서버에 배포하기 위해 직접 파일을 복사하는 대신 Docker Registry 라는 이미지 저장소를 사용합니다.
- 명령어를 통해 이미지를 Registry에 Push 하면 다른 서버에서 Pull 받아서 사용하는 구조.
- Docker Registry는 오픈소스 무료 설치형이고 설치형이 싫다면 Docker Hub를 이용하면 됩니다.  
![image](https://user-images.githubusercontent.com/21374902/148635190-8f470d88-f61b-484d-88a1-52a736cf2007.png)



---
1️⃣6️⃣ Docker Hub
===
- Docerk Hub에는 기본적으로 제공하는 ubuntu, centos 등의 base image와 ruby, java 등 공식 image, 그리고 일반 사용자들이 만든 image까지 모두 저장되어 있습니다.
- Docker Hub 사용방법
  - `docker login`
    - 인증정보는 ~/.docker/config.json 에 저장
  - `docker tag app subicura/sinatra-app:1`
    - tag 명령어 : docker tag SOURCE_IMAGE[:TAG] TARGET_IMAGE[:TAG]
    - 이미지 이름 구성 : [Registry URL]/[사용자 ID]/[이미지명]:[tag]
    - Registry URL은 기본적으로 Docker Hub를 바라보고 있고 사용자 ID를 지정하지 않으면 기본값은 'library' 입니다.   
  - `docker push subicura/sinatra-app:1`
- Docker Image를 Private 하게 관리하려면 Docker Cloud를 사용하거나 Registry 서버를 따로 구축해야 합니다.
- Docker Registry를 만드는 방법
  - `docker run -d -v $PWD/registry:/var/lib/registry -p 5000:5000 distribution/registry:2.6.0`
  - `docker tag app localhost:5000/subicura/sinatra-app:1`
  - `docker push localhost:5000/subicura/sinatra-app:1`
  - `tree registry`
- Docker Registry는 일반적으로 HTTP를 사용하기 때문에 보안 이슈가 있어서 내부 서버를 제외하곤 HTTP 사용을 금지하고 있으며 이를 무시하려면 Docker Engine을 실행할 때 특정 옵션을 줘야 합니다.
- Docker Hub 사용방법
  - 이미지 검색하기 : `docker search {image name}`
  - 이미지 내려받기 : `docker pull {image name}`
  - 이미지 올리기
    - `docker login`
    - `docker tag SOURCE_IMAGE[:TAG] USER_NAME/TARGET_IMAGE[:TAG]`
    - `docker push USER_NAME/TARGET_IMAGE[:TAG]`



---
1️⃣7️⃣ Docker Deploy
===
- Container 방식으로 배포
  - 언어, 프레임워크와 상관없이 동일한 방식으로 배포할 수 있다.
  - 서버에 접속해서 Container를 실행할 줄 알면 된다.
  - 서버에 최신 image를 올려둔 후 이전 Container를 중지 후 삭제하고 최신 image로 Container를 실행시켜주면 됩니다.
- 하지만 위 방법은 무중단 배포를 의미하는 것은 아니기 때문에 무중단 배포를 위해선 아래 자료를 참고 합니다.\
https://subicura.com/2016/06/07/zero-downtime-docker-deployment.html



---
1️⃣8️⃣ gitlab-ci.yml 예제
===
- gitlab-docker-aws 환경에 DEV, STG, PROD 라는 3개의 환경을 세팅하여 사용할 때 사용했던 gitlab-ci.yml 파일 예제
- ECR : Amazon Elastic Container Registry
  - ECR에 Repository, 정책, 토큰, 이미지 등을 미리 설정해두고 그 설정을 불러서 동작하도록 설정
- ECR을 이용해서 EC2에 새로운 ECS를 만드는 순서
  - `Ready Docker image` → `Create ECR repository` → `Connect EC2` → `Pull Docker image` → `Create new ECS with docker image` → `Create service`
- Maven 생명주기 : `validation` → `compile` → `test` → `package` → `intergration-test` → `verify` → `install` → `deploy`
  - maven compile : complie 후 target 폴더에 .class 파일 생성
  - maven test : JUnit 테스트 코드 실행
  - maven package : .jar 생성 파일 생성
  - maven build : maven 기반 project build
    ```yml
    ### docker image 기반으로 동작하도록 설정
        image: docker:latest
        
    ### 환경 변수 등 아래 명령어에서 공통으로 사용하는 값 세팅
        variables:
          DEV_ECR: {ECR Repository 주소}
          STG_ECR: {ECR Repository 주소}
          PROD_ECR: {ECR Repository 주소}
          MAVEN_OPTS: -Dmaven.repo.local=${CI_PROJECT_DIR}/.mr

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
          # - /^dev.*$/
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
            - "RESULT=\"$(curl -s -o /dev/null -w \"%{http_code}\" --request POST -H \"access_token: ${ACCESS_TOKEN}\" \"${CICD_SERVICE_URL}/${serverGroup URL}/deploy?commit=$NEW_IMAGE_TAG\")\""
            - echo ${RESULT}
    ### STG, PROD 환경별 작성    
    ###  환경별 값은 거의 동일하고 except 부분만 달라진다.
        STG-docker-build:
        STG-deploy: ...
        PROD-docker-build: ...
        PROD-deploy: ...
    ```



---
1️⃣9️⃣ Gitlab에 maven build 및 docker build 로그 분석
===
- ###### 주어진 환경 : 프로젝트 내에서 정의한 파이프라인과 CI/CD 환경은 별도로 정의되어 있었고 구동 환경은 AWS + Docker + Gitlab + SpringBoot 입니다.
- ###### Gitlab 내에서 maven build + docker build를 수행하고 별도의 CI/CD 환경에서 Deploy를 수행함.
- ###### gitlab.ci.yml 파일은 [1️⃣8️⃣ gitlab-ci.yml 예제](#1️⃣8️⃣-gitlab-ci.yml-예제)
- ###### Dockerfile은 아래 참고
  ```docker
  ### 기존에 사용하던 App은 Spring 이라서 tomcat을 사용
  FROM tomcat:8.5.35-jre8
  VOLUME /tmp

  ENV TZ Asia/Seoul
  RUN apt-get update -y && apt-get install unzip net-tools watch vim -y
  ADD entrypoint.sh /user/test/app
  RUN chmod +x /user/test/app/entrypoint.sh

  ADD target/*.jar /user/test/app/web/test.jar
  RUN chmod +x /user/test/app/web/test.jar

  EXPOSE 8080
  CMD ["sh", "entrypoint.sh"]  
  ### java -jar /user/test/app/web/test.jar
  ```
- ###### Maven Build
  - 이미 설정되어 있는 gitlab-runner를 통한 docker 배포
  - script는 .gitlab.ci.yml에 이미 정의해 놓은 명령어를 실행
  - docker를 준비하고 maven image를 먼저 실행 
  → docker 환경 준비 
  → git 자원 가져오기 
  → 특정 경로(/builds/..)에 git repo init 
  → 해당 branch에서 마지막 commit id로 checkout 
  → s3에서 cache(zip 형태) 가져오기 
  → 이미 설정해 놓은 step_script 실행
  _(.gitlab.ci.yml 파일을 보면 build.script: "mvn install"으로 되있음)_
  → docker에서 maven install 실행 
  → 성공한 내용 cache(zip 형태)에 저장 
  → cache 생성 
  → 다시 s3에 cache 업로드 
  → 성공한 artifact(jar파일)을 업로드 
  _(.gitlab.ci.yml 파일에 artifacts.paths.target/*.jar로 선언)_
  → pom.xml에 정의한 것처럼 target/*.jar 파일을 업로드 
  → 사용했던 자원들 Clean
  - 로그 요약
    ```log
    Running with gitlab-runner 14.6.0 ({mixedValue1}) on Gitlab Runner {gitlab id(mixed value)}
    Preparing the "docker+machine" executor
    Using Docker executor with image maven:xxx
    Using docker image sha256:xxx for maven:xxx with digest maven@sha256: xxx
    Preparing environment
    Running on runner-{gitlab id(mixed value)}-...
    Getting source from git repository
    Fetching changes with git depth set to 50...
    Initialized empty Git repository in /builds/{git project name}/.git/
    Created fresh repository.
    Checking out {commit id} as master...
    Skipping Git submodules setup
    Restoring cache
    Checking cache for default...
    Downloading cache.zip from {s3 address}
    Successfully extracted cache
    ############## .gitlab.ci.yml 파일에 build.script 실행 ##############
    Executing "step_script" stage of the job script
    Using docker image sha256:xxx for maven:xxx with digest maven@sha256: xxx
    $ mvn install
    [INFO] Scanning for projects...
    ...
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    Saving cache for successful job
    Creating cache default...
    .m2/: found xxx matching files and directories    
    Uploading cache.zip to {s3 address}}
    Created cache
    ############## .gitlab.ci.yml 파일에 artifacts.paths.target/*.jar ##############
    Uploading artifacts for successful job
    Uploading artifacts...
    target/*.jar: found 1 matching files and directories 
    Uploading artifacts as "archive" to coordinator... ok id={numberous id value} responseStatus=201 Created token={mixed token value}
    Cleaning up project directory and file based variables
    Job succeeded
    ```
- ###### Docker Build
  - 수행하는 동작은 위에 있는 `Maven Build`와 비슷함
  docker 환경 준비 - git repo init - commit id로 checkout - download cache from s3 - script 실행
  - 차이점은 "step_script"를 실행할 때, 
    - Maven build : .gitlab-ci.yml 파일에 `build.script` 명령어인 `maven install`을 수행
    - Docker build는 `{branch}-docker-build` 하위에 있는 `before_script`, `script` 하위 명령어를 순차적으로 실행
  - 로그 요약
    ```log
    Running with gitlab-runner 14.6.0 ({mixedValue1}) on Gitlab Runner {gitlab id(mixed value)}
    Preparing the "docker+machine" executor
    Using Docker executor with image docker:latest ...
    Pulling docker image docker:latest ...
    Using docker image sha256:xxx for docker:latest with digest docker@sha256:xxx ...
    Preparing environment
    Running on runner-{gitlab id(mixed value)}-... via runner-{gitlab id(mixed value)}-runner-... ...
    Getting source from Git repository
    Fetching changes with git depth set to 50...
    Reinitialized existing Git repository in /builds/{git project name}/.git/
    Checking out {commit id} as master...
    Removing .m2/
    Removing target/
    Skipping Git submodules setup
    Restoring cache
    Checking cache for default...
    Downloading cache.zip from {s3 address}
    Successfully extracted cache
    Downloading artifacts
    Downloading artifacts for build (xxx)...
    Downloading artifacts from coordinator... ok id=xxx responseStatus=200 OK token={token value}
    Executing "step_script" stage of the job script
    Using docker image sha256:xxx for docker:latest with digest docker@sha256:xxx ...
    ############## .gitlab.ci.yml 파일에 before_script 실행 ##############
    $ NEW_PROD_IMAGE_NAME=${PROD_ECR}:$(echo ${CI_COMMIT_REF_NAME} | sed "s/[^[[:alnum:]]//g")-${CI_COMMIT_SHA}
    ############## 여기 이하는 .gitlab.ci.yml 파일에 script 부분 순차적으로 실행 ##############
    $ apk add --no-cache curl jq python3 py3-pip
    {processing ...}
    OK: 83 MiB in 65 packages
    $ pip3 install awscli
    {processing ...}
    Successfully installed PyYAML-5.4.1 awscli-1.22.40 botocore-1.23.40 colorama-0.4.3 docutils-0.15.2 jmespath-0.10.0 pyasn1-0.4.8 python-dateutil-2.8.2 rsa-4.7.2 s3transfer-0.5.0
    $ $(aws ecr get-login --no-include-email --region {aws region})
    {processing ...}
    Login Succeeded
    $ docker build -t $NEW_PROD_IMAGE_NAME .
    Step 1/10 : FROM tomcat:8.5.35-jre8
    {build Dockerfile ...}
    Successfully built xxx
    Successfully tagged {aws ecr}.{aws region}.{aws domain + project name + branch name}-{commit id}
    $ docker push $NEW_PROD_IMAGE_NAME
    {push docker image}
    The push refers to repository [{aws ecr}.{aws region}.{aws domain + project name + branch name}]
    {branch name}-{commit id}: digest: sha256:xxx size: 4097
    $ docker rmi $NEW_PROD_IMAGE_NAME
    {remove docker image}
    Saving cache for successful job
    Creating cache default...
    .m2/: found 5189 matching files and directories    
    Archive is up to date!                             
    Created cache
    Cleaning up project directory and file based variables
    Job succeeded
    ```
---



2️⃣0️⃣Docker Desktop 없이 사용하기 (Windows10)
===
1. ###### Docker Desktop 삭제
2. ###### WSL에 docker 설치
    - #### 공통
      ```sh
      # docker-compose 설치
      $ sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
      $ sudo chmod +x /usr/local/bin/docker-compose
      ```
    - ### docker.io
      ```sh
      # docker.io 설치
      $ sudo apt update
      $ sudo apt upgrade -y
      $ sudo apt install -y docker.io
      
      # Docker 그룹에 사용자 추가
      $ sudo usermod -aG docker $USER
      ```
    - ### docker-ce
      ```sh
      # 기존 Docker 삭제
      $ sudo apt remove -y docker docker-engine docker.io containerd runc
      $ sudo apt purge -y docker-ce docker-ce-cli containerd.io
      $ sudo rm -fr /var/lib/containerd/
      
      # 필요한 패키지 설치
      $ sudo apt install -y apt-transport-https ca-certificates curl gnupg lsb-release
      $ sudo apt update
      
      # Docker 공식 GPG 키
      $ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
      
      # Docker stable repo 사용
      $ sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
      
      # Docker 설치
      $ sudo apt install -y docker-ce docker-ce-cli containerd.io
      $ sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
      $ sudo chmod +x /usr/local/bin/docker-compose

      # Docker 그룹에 사용자 추가
      $ sudo usermod -aG docker $USER
      ```
3. ###### docker 실행
    ```sh
    # Docker daemon 실행
    $ sudo dockerd
    ```
4. ###### Windows Terminal 에서 Docker 실행
    ```sh
    # wsl {command}
    PS D:\> wsl docker ps -al
    ```
5. ###### Reference
    - docker.io : https://www.bearpooh.com/92
    - docker-ce : https://vntgcorp.github.io/DockerWithoutDesktop/

---
*️⃣ 참고자료
===
- Docker Docs : [Docker Docs](https://docs.docker.com/get-started/overview/)
- Gitlab Docs : [Gitlab Docs](https://docs.gitlab.com/ee/ci/yaml/index.html#stages)
- Docker Part : [Logosubicura's blog](https://subicura.com/2017/01/19/docker-guide-for-beginners-1.html)
- gitlab-ci.yml Part : [otrodevym's tistory](https://otrodevym.tistory.com/entry/Gitlab-CICD-gitlab-ciyml-%EC%82%AC%EC%9A%A9-%EB%B0%A9%EB%B2%95)
- docker-compose Part : https://meetup.toast.com/posts/277



