#!/bin/bash

# SSL 인증서 초기 설정 스크립트
# 사용법: ./init-ssl.sh your-domain.com your-email@domain.com

DOMAIN=${1:-your-domain.com}
EMAIL=${2:-your-email@domain.com}

echo "🔐 SSL 인증서 초기 설정을 시작합니다..."
echo "도메인: $DOMAIN"
echo "이메일: $EMAIL"

# 기존 인증서 디렉토리 생성
sudo mkdir -p ./ssl_certs/live/$DOMAIN

# 임시 자가서명 인증서 생성 (첫 nginx 시작을 위해)
sudo openssl req -x509 -nodes -days 1 -newkey rsa:2048 \
    -keyout ./ssl_certs/live/$DOMAIN/privkey.pem \
    -out ./ssl_certs/live/$DOMAIN/fullchain.pem \
    -subj "/C=KR/ST=Seoul/L=Seoul/O=Organization/CN=$DOMAIN"

echo "📦 Docker 컨테이너를 시작합니다..."
sudo docker-compose up -d nginx

echo "⏳ Nginx 시작을 기다립니다..."
sleep 10

echo "🎫 Let's Encrypt 인증서를 발급받습니다..."
sudo docker-compose run --rm certbot certbot certonly \
    --webroot \
    --webroot-path=/var/www/certbot \
    --email $EMAIL \
    --agree-tos \
    --no-eff-email \
    -d $DOMAIN \
    -d www.$DOMAIN

echo "🔄 Nginx를 재시작합니다..."
sudo docker-compose exec nginx nginx -s reload

echo "✅ SSL 인증서 설정이 완료되었습니다!"
echo "🔍 인증서 확인: https://$DOMAIN"

# 인증서 자동 갱신을 위한 cron job 추가 안내
echo ""
echo "📅 인증서 자동 갱신을 위해 다음 cron job을 추가하세요:"
echo "0 12 * * * cd /home/ubuntu/app && sudo docker-compose run --rm certbot certbot renew && sudo docker-compose exec nginx nginx -s reload"