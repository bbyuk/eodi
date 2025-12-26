# infra

이 디렉터리는 **로컬(Local) / 개발(Dev) / 운영(Prod)** 환경에서  
서비스 실행에 필요한 **인프라 구성 요소를 일관되게 관리**하기 위한 디렉터리입니다.

애플리케이션 코드와 인프라 설정을 분리하여 다음을 목표로 합니다.

- 환경 간 차이를 최소화한 실행 환경 제공
- Docker 기반 재현 가능한 인프라 구성
- 데이터베이스 스키마 및 초기화 로직의 명확한 관리
- 로컬 → 개발 → 운영 환경으로의 자연스러운 확장

---

```text
infra
├── common
│   ├── env
│   │   ├── app.env              # 애플리케이션 공통 환경 변수
│   │   └── db.env               # DB 공통 환경 변수
│   │
│   └── mysql
│       ├── init-scripts
│       │   └── init.sql         # MySQL 초기 DB / 계정 / 권한 설정
│       └── schema               # 도메인별 스키마 정의 (DDL)
│           ├── address
│           ├── deal
│           ├── integration
│           ├── legal_dong
│           └── spring_batch
│
├── local
│   ├── nginx
│   │   └── nginx.conf           # 로컬 환경용 Nginx 설정
│   ├── docker-compose.yml       # 로컬 인프라 구성
│   └── README.md
│
├── dev
│   ├── nginx
│   │   └── nginx.conf           # 개발 환경용 Nginx 설정
│   ├── docker-compose.yml       # 개발 인프라 구성
│   └── README.md
│
├── prod
│   ├── nginx
│   │   └── nginx.conf           # 운영 환경용 Nginx 설정
│   ├── docker-compose.yml       # 운영 인프라 구성
│   └── README.md
│
└── README.md
