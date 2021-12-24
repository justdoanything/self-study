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
*️⃣ 참고자료
  https://subicura.com/2017/01/19/docker-guide-for-beginners-1.html
```
```
3️⃣ Windows 10 환경에서 개발환경 세팅하기

```
```
4️⃣ WSL2 와 Docker

```
```
5️⃣ Docker와 Kubernetes

```
```
*️⃣ 참고자료  
https://subicura.com/2017/01/19/docker-guide-for-beginners-1.html
```
