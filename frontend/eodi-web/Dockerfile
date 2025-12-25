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

# standalone output
COPY --from=builder /app/public ./public
COPY --from=builder /app/.next/standalone ./
COPY --from=builder /app/.next/static ./.next/static

USER nextjs
EXPOSE 3000

CMD ["node", "server.js"]
