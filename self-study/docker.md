```
1️⃣ 물리 머신 vs. 가상 머신 vs. 도커 컨테이너
![image](https://user-images.githubusercontent.com/21374902/147167642-1dad5620-3b02-4e83-854d-3595e7feee64.png)

```
```
2️⃣ What is Docker  
  . 전가상화, 반가상화는 추가적인 OS 설치는 불가피하기 때문에 성능문제가 존재
  . 이를 개선하기 위해 '프로세스' 격리 방식이 등장
  . 리눅스 환경에선 리눅스 컨테이너가 프로세스 격리시키기 때문에 가볍게 빠르게  
  . 동작하고 자원손실도 거의 없음
  . 실행중인 컨테이너에 접속해서 명령어를 실행하고 패키지 설치, 여러개의 프로세스를 백그라운드로 실행할 수 있음 (장점)
  . CPU, 메모리 사용량을 제한할 수 있고 특정 포트나 디렉토리를 외부와 연결할 수 있다.
  . [이미지]는 컨테이너 실행에 필요한 파일과 설정값을 갖고 있고 변하지 않는다.
  ![image](https://user-images.githubusercontent.com/21374902/147167708-010adcfc-cda2-4399-a69a-807ba6d2a690.png)
    [컨테이너]는 이미지를 실행한 상태라고 볼 수 있고 추가되거나 변하는 값은 [컨테이너]에 저장한다.
  . 한 개의 [이미지]로 한 개의 [서버]에서 여러개의 [컨테이너]를 생성해서 실행할 수 있다.
  . Docker Layer
  ![image](https://user-images.githubusercontent.com/21374902/147167762-342c1f71-014f-435a-bef5-360d4ab4ca89.png)

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
