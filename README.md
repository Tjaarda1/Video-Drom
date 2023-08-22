
# Video-Drom Application README

Welcome to the README for the Video-Drom application. This document provides comprehensive instructions on how to compile Docker images, upload them, deploy Kubernetes resources, and an in-depth understanding of the application's design and purpose.

## Table of Contents

- [Docker Images](#docker-images)
- [Kubernetes Resources](#kubernetes-resources)
- [Design of the Application](#design-of-the-application)
- [What is Video-Drom?](#what-is-video-drom)
- [Summary of Features](#summary-of-features)

## Docker Images

To compile and push Docker images for the application, follow these steps:

### Backend

```bash
cd src/backend/
sudo docker build -t alexdecb/video-drom-back:latest .
sudo docker push alexdecb/video-drom-back:latest
```

### Frontend

```bash
cd src/frontend
sudo docker build -t alexdecb/video-drom-front:latest .
sudo docker push alexdecb/video-drom-front:latest
```

### MPD-Generator

```bash
cd src/mpd-generator
sudo docker build -t alexdecb/mpd-generator:latest .
sudo docker push alexdecb/mpd-generator:latest
```

### Video-Server

No action required.

## Kubernetes Resources

Setup volumes, NFS server, and deploy services:

### Volumes

1. Set up NFS server:
   ```bash
   kubectl create -f ./kubernetes-resources/volumes/video-files/nfs-server.yaml
   ```

2. Create MariaDB volume:
   ```bash
   kubectl create -f ./kubernetes-resources/volumes/mariadb/
   ```

### Deployments

1. Deploy Database:
   ```bash
   kubectl create -f ./kubernetes-resources/deployments/maria-database/
   ```

2. Deploy MPD-Generator:
   ```bash
   kubectl create -f ./kubernetes-resources/deployments/mpd-generator/
   ```

3. Deploy Backend:
   - Configure `./kubernetes-resources/deployments/backend/backend-deploy.yaml` with DB_URL.
   ```bash
   kubectl create -f ./kubernetes-resources/deployments/backend
   ```

4. Deploy Video-Server:
   ```bash
   kubectl create -f ./kubernetes-resources/deployments/video-server
   ```

5. Deploy Frontend:
   - Modify `app-urls-cm.yaml` with video-server and backend URLs.
   ```bash
   kubectl create -f ./kubernetes-resources/deployments/

## Design of the Application

Video-Drom is an application designed for users to store and access personal videos via the web, utilizing cutting-edge video streaming technologies. The microservices architecture includes Frontend, Backend, Video-Server, MPD Generator, and Database. Communication patterns, security, and the use of an API Gateway are outlined in detail.

### Application Flow

Viewing Content: Diagram illustrating how users access and view content.

Uploading Content: Diagram depicting the process of uploading videos.

## What is Video-Drom?

Video-Drom is an application developed for a thesis project, offering users a platform to store and access their personal videos through web interfaces. The app employs Dynamic Adaptive Streaming over HTTP (DASH) technology for on-demand video delivery and utilizes Kubernetes for deployment. A deeper study of the app is done in the respective memoir.


## Summary of Features

- Upload and store personal videos.
- Access videos from any web-enabled device.
- Leverage DASH technology for adaptive video streaming.
- Microservices architecture on Kubernetes.
- Components: Frontend, Backend, Video-Server, MPD Generator, and Database.

For detailed instructions and a comprehensive understanding of the application's architecture, refer to the respective sections above.

For inquiries or assistance, feel free to contact me!