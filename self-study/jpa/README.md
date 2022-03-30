# postgres 설치 및 접속
  - docker run -p 5432:5432 -e POSTGRES_PASSWORD=yongwoo -e POSTGRES_USER=yongwoo -e POSTGRES_DB=springdata --name postgres_boot -d postgres
  - docker exec -it postgres_boot bash
  - psql -U yongwoo springdata

