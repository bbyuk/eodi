# Phase 1 – Single EC2 (Web / App / DB 통합)

## 개요

Phase 1은 **단일 EC2 인스턴스에서 Web / Application / Database를 모두 운영하는 초기 MVP 단계**이다.  
운영 복잡도를 최소화하고, 로컬 개발 환경과 운영 환경을 최대한 동일하게 유지하는 것을 목표로 한다.

- 대상: MVP / 초기 사용자 검증 단계
- 인프라 비용 최소화
- 빠른 배포 및 롤백 가능
- 이후 Phase 2, Phase 3로 확장 가능한 구조

---

## 구성 요소

이 Phase에서는 다음 컨테이너들이 하나의 `docker-compose`로 관리된다.

- **Web**: Nginx 기반 외부 트래픽 수신 및 App 프록시
- **Application**: Spring Boot 기반 API 서버
- **DB**: MySQL 기반 운영 DB (Docker Volume 기반 영속화)

---

## 설계 의도

### 1. 로컬 ↔ 운영 환경 동일성
환경 차이로 인한 버그를 최소화한다.
- 동일한 Docker 이미지
- 동일한 compose 파일
- 동일한 DB 버전

```
# 로컬 & 운영
docker-compose up -d
```

---

### 2. 비용 효율성

- RDS, ElastiCache 등 Managed Service 미사용
- 단일 EC2 인스턴스 비용만 발생
- MVP 단계에서 과도한 인프라 비용 방지

---

### 3. 운영 단순성

- SSH 접속 포인트 1개
- 로그, 설정, 백업 위치 명확
- 장애 포인트 최소화

---
### 4. docker-compose 파일

```
infra/phase/1/docker-compose.yml
```

- 주요 특징:
  - MySQL 데이터는 Docker Volume으로 영속화
  - DB 포트는 외부로 노출하지 않음 
  - 컨테이너별 메모리 제한 설정