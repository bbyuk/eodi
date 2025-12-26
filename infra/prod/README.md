Production Environment Setup

이 문서는 운영(PROD) 환경에서 Docker Compose 기반으로
서비스를 기동하는 방법을 설명합니다.

(App / DB 서로 다른 EC2 인스턴스, EBS 기반 스토리지 분리 구조)

<hr>

### 디렉터리 구조
env 파일은 서버 로컬에서 관리
```
prod/
├─ app/
│  ├─ nginx/
│  └─ docker-compose.yml
├─ db/
│  └─ docker-compose.yml
├─ resource/
│  └─ init-network.sh
└─ README.md

```

### 사전 요구사항
#### 공통
- Linux EC2 인스턴스
- Docker 설치
- Docker Compose V2 설치

#### App EC2
- 80, 443 포트 오픈 (public)
- DB EC2로의 VPC 네트워크 접근 허용

#### DB EC2
- MySQL 포트 App EC2 Security Group에서만 허용


#### 네트워크 구성
```
[App EC2]
nginx <-> node <-> api (docker network)

[DB EC2]
api -> MySQL (VPC Private IP / DNS)
```
<hr>

### 1. Docker 네트워크 생성 [수동]

대상 : App EC2

compose lifecycle과 분리된 infra 자원은 별도 스크립트로 한 번만 생성합니다.

#### 실행 권한 부여 (최초 1회)
```
chmod +x /opt/eodi/compose/resource/init-network.sh
```

#### network 생성
```
~/prod/resource/init-network.sh
```
생성되는 network:
- eodi-frontend-net

이미 존재하지 않는 경우 재생성하지 않습니다.

### 2. 환경변수 설정 [수동]
환경변수는 보안상 레포에 포함하지 않으며,
각 EC2 인스턴스에 `/opt/eodi/env` 경로로 수동 배포합니다.

#### App EC2
```
/opt/eodi/env/
└─ api.env
```

#### DB EC2
```
/opt/eodi/env/
└─ db.env
```

### 3. DB EC2 설정 및 컨테이너 실행

#### EBS 마운트
DB EC2에는 MySQL 데이터용 EBS 볼륨을 연결합니다.
```
# MySQL 데이터
/data/db
```

#### 권한 설정 (최초 1회)
```
sudo chown -R 999:999 /data/db
sudo chmod 750 /data/db
```

#### DB 컨테이너 실행
```
cd /opt/eodi/compose/db
docker-compose up -d
```

### 4. App EC2 설정 및 컨테이너 실행

#### nginx 설정 파일 위치
```
/opt/eodi/compose/app/nginx/nginx.conf
```

#### App 컨테이너 실행
```
cd /opt/eodi/compose/app
docker-compose up -d
```