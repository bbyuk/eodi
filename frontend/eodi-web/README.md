## 어디살까 Frontend (Next.js)

이 프로젝트의 프론트엔드는 Next.js 기반 SSR 애플리케이션입니다.

로컬 개발 환경에서는 Node.js로 직접 실행하며, 운영/배포 환경을 위해 Dockerfile을 통해 컨테이너 빌드가 가능하도록 구성되어 있습니다.

<hr>

### Stack
- Framework: Next.js
- Runtime: Node.js (v22.21.1)
- Language: JavaScript
- Styling: Tailwind
- Package Manager: npm

<hr>

### 로컬 개발 환경 구성
```
# Node.js 버전 확인
node -v
# v22.21.1

# 의존성 설치
npm install

# 개발 서버 실행
npm run dev
```

로컬 접속 URL : http://localhost:3000

Next.js 개발 모드로 실행되며 HMR, 개발용 에러 오버레이, 개발 전용 설정이 활성화 됩니다.

<hr>

### Docker build & Run
```
# 빌드
docker build -t eodi-web .

# 실행
docker run -p 3000:3000 eodi-web
```