## Local Environment Setup

이 문서는 로컬 개발 환경을 Docker Compose로 기동하는 방법을 설명합니다.

(Web - App - DB 분리, 네트워크 분리 구조)

<hr>

### 디렉터리 구조
```
local/
├─ app/
│  ├─ nginx/
│  ├─ app.env
│  ├─ web.env
│  └─ docker-compose.yml
├─ db/
│  ├─ .env
│  └─ docker-compose.yml
├─ network/
│  └─ init-mac.sh
└─ README.md
```

### 사전 요구사항
- Docker Desktop 설치 및 실행
- Docker Compose v2 설치

### 네트워크 구성
```
frontend-net : web/nodejs ↔ app
backend-net  : app ↔ db
```
<hr>

### 1. Docker 네트워크 생성
네트워크는 compose lifecycle과 분리된 infra 자원이므로 별도 스크립트로 한 번만 생성합니다.

#### 실행 권한 부여 (최초 1회)
```
# Mac 사용자
chmod +x ~/local/network/init-mac.sh
```

#### 네트워크 생성
```
~/local/network/init-mac.sh
```

생성되는 네트워크:

- eodi-frontend-net
- eodi-backend-net

이미 존재하는 경우 재생성하지 않습니다.

### 2. DB 컨테이너 실행
DB 서비스는 eodi-backend-net 네트워크에만 연결됩니다.
```
# 백그라운드 db 컨테이너 실행
~/local/db/docker-compose up -d
```

### 3. App + Web(nginx) + Web(node.js) 컨테이너 기동
App은 eodi-frontend-net / eodi-backend-net 네트워크 모두 연결되며, Web(nginx), Web(node.js)은 eodi-frontend-net 네트워크에만 연결됩니다.
```
# 백그라운드 app 컨테이너 실행
~/local/app/docker-compose up -d
```
