FROM node:21-alpine

WORKDIR /web_app

COPY package.json yarn.lock ./

RUN yarn install --silent

COPY . .

EXPOSE 3000
CMD yarn run dev --port 3000 --strictPort --host 0.0.0.0