# Base image
FROM node:16 as build-stage

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./
COPY vite.config.js ./
COPY ./public/app_urls.json ./public/
# Install dependencies
RUN npm install

# Copy the entire project to the working directory
COPY . .

# Build the Vue.js app
RUN npm run build

# Production stage
FROM nginx:1.21

# Copy the built Vue.js app from the build stage
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Start NGINX
CMD ["nginx", "-g", "daemon off;"]