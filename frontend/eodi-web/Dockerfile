################################
# 1. deps
################################
FROM node:22.21.1-alpine AS deps
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm ci


################################
# 2. builder
################################
FROM node:22.21.1-alpine AS builder
WORKDIR /app

COPY --from=deps /app/node_modules ./node_modules
COPY . .

ENV NODE_ENV=production
RUN npm run build


################################
# 3. runner
################################
FROM node:22.21.1-alpine AS runner
WORKDIR /app

ENV NODE_ENV=production
ENV PORT=3000

# non-root user
RUN addgroup -g 1001 nodejs \
 && adduser -u 1001 -G nodejs -s /bin/sh -D nextjs

# 실행에 필요한 최소 파일만 복사
COPY --from=builder /app/package.json ./package.json
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/public ./public

USER nextjs
EXPOSE 3000

# next start 실행
CMD ["npm", "run", "start"]
