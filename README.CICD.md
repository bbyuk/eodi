## GitHub Actions CI/CD 파이프라인 구성

이 문서는 아래 아키텍처 다이어그램을 기준으로
eodi 프로젝트의 CI/CD 파이프라인 구성 및 과정을 기술하며,
GitHub Actions와 AWS 서비스를 활용해
Docker Compose 기반 운영 환경을 자동 배포하는 방법에 대해 작성한다.

## TODO - draw.io 이미지 첨부

---

## 0. 개요

---
- VCS: GitHub
- CI/CD:
  - Source Code: GitHub Actions + AWS SSM Run Command
  - Infra definition: GitHub Actions + git fetch
- Image Registry: Amazon ECR (Private)
- Runtime: App EC2 (Docker Compose)
  - nginx
  - frontend (Node.js + Next.js Application)
  - backend (JDK 17 + Spring Boot Application)

※ SSH 접근 없이 배포 수행

---

## 1. AWS 콘솔 작업

---

### 1.1. ECR 생성

**AWS Console -> Amazon ECR -> 프라이빗 레지스트리 -> 리포지토리**

1. 리포지토리 생성
  - eodi-api
  - eodi-web
2. 이미지 태그 변경 가능성: Mutable
3. 암호화 설정: AES-256

**Lifecycle Policy**
리포지토리 선택 -> Lifecycle policy -> 수명 주기 정책 규칙 -> 작업 -> JSON 편집

- untagged image 3일 후 삭제
- 최근 5개 이미지만 유지

```
{
  "rules": [
    {
      "rulePriority": 1,
      "description": "Delete untagged images after 3 days",
      "selection": {
        "tagStatus": "untagged",
        "countType": "sinceImagePushed",
        "countUnit": "days",
        "countNumber": 3
      },
      "action": {
        "type": "expire"
      }
    },
    {
      "rulePriority": 2,
      "description": "Keep only last 5 images",
      "selection": {
        "tagStatus": "any",
        "countType": "imageCountMoreThan",
        "countNumber": 5
      },
      "action": {
        "type": "expire"
      }
    }
  ]
}
```


### 1.2. IAM Role 생성

#### 1.2.1. GitHub Actions용 IAM Role

**AWS Console -> IAM -> 역할 -> 역할 생성**

- 신뢰할 수 있는 엔터티 선택
  - 신뢰할 수 있는 엔터티 유형: 사용자 지정 신뢰
  - 사용자 지정 신뢰 정책:

```
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Principal": {
                "Federated": "arn:aws:iam::<ACCOUNT_ID>:oidc-provider/token.actions.githubusercontent.com"
            },
            "Action": "sts:AssumeRoleWithWebIdentity",
            "Condition": {
                "StringEquals": {
                    "token.actions.githubusercontent.com:aud": "sts.amazonaws.com"
                },
                "StringLike": {
                    "token.actions.githubusercontent.com:sub": [
                        "repo:<ORG_OR_USER>/<REPO>:ref:refs/heads/main",
                        "repo:<ORG_OR_USER>/<REPO_INFRA>:ref:refs/heads/main"
                    ]
                }
            }
        }
    ]
}
```

**생성한 IAM Role 선택 -> 권한 -> 권한 추가 -> 인라인 정책 생성 -> JSON**

> NOTE: 아래 정책은 설명을 단순화한 예시이며,
> 운영 환경에서는 ECR 리포지토리 ARN 및 SSM 인스턴스/문서 ARN으로 Resource 범위를 제한하는 것을 권장


```
{
	"Version": "2012-10-17",
	"Statement": [
		{
			"Sid": "AllowSSMSendCommand",
			"Effect": "Allow",
			"Action": [
				"ssm:SendCommand",
				"ssm:ListCommandInvocations",
				"ssm:GetCommandInvocation"
			],
			"Resource": "*"
		},
		{
			"Sid": "AllowECRPushAndRead",
			"Effect": "Allow",
			"Action": [
				"ecr:GetAuthorizationToken",
				"ecr:BatchCheckLayerAvailability",
				"ecr:InitiateLayerUpload",
				"ecr:UploadLayerPart",
				"ecr:CompleteLayerUpload",
				"ecr:PutImage",
				"ecr:BatchGetImage",
				"ecr:GetDownloadUrlForLayer"
			],
			"Resource": "*"
		}
	]
}
```

#### 1.2.2. App EC2용 IAM Role

**AWS Console -> IAM -> 역할 -> 역할 생성**

- 신뢰할 수 있는 엔터티 선택
  - 신뢰할 수 있는 엔터티 유형: AWS 서비스
  - 사용 사례
    - 서비스 또는 사용 사례: EC2
- 권한 추가
  - AmazonEC2ContainerRegistryReadOnly - (ECR pull)
  - AmazonSSMManagedInstanceCore - (SSM)


### 1.3. IAM Role 연결

**AWS Console -> EC2 -> 인스턴스 -> App EC2 선택 -> 작업 -> 보안 -> IAM 역할 수정**
1.2.2에서 생성한 App EC2용 IAM Role을 연결

---

## 2. App EC2 환경 구성

---

**사전 구성**

- App EC2 서버 구성 참고해 docker / docker compose / git 설치
- Base Image를 AMI Bake 사용

### 2.1. eodi-infra repository 배포

- docker compose yml이나 init 쉘 스크립트 등 인프라 정의 파일로 구성된 eodi-infra repository 배포 workflow 실행
- eodi-infra/Deploy prod Infra Definitions workflow 실행
  - 이미 .git repo가 존재할 경우 git fetch
  - /opt/eodi/infra 디렉터리가 없거나 .git이 없다면 git clone

```
배포 후 디렉터리 tree

/opt/eodi/
├─ env/       -> 서버 로컬 자산으로 관리
│  ├─ api.env
│  ├─ web.env
├─ infra/
│  └─ prod/
│     └─ app/
│        └─ compose/
│           ├─ docker-compose.base.yml
│           ├─ docker-compose.api.yml
│           ├─ docker-compose.web.yml
│           └─ docker-compose.nginx.yml

```

---

## 3. GitHub Repository Secrets 등록

---

**대상 Repository -> Settings -> Security -> Secrets and variables -> Actions -> New repository secret**

secret name과 value 입력 후 저장

```
현재 등록한 AWS 관련 repository secrets

AWS_REGION
AWS_IAM_ROLE
AWS_ECR_REGISTRY
```

---

## 4. GitHub Actions Workflow 작성

eodi repository 책임

- 소스 코드 빌드 - ECR push
- deploy via ssm with docker compose

eodi-infra repository 책임

- docker compose yml 배포
- nginx docker compose up
