# Video-Drom


Docker images:

backend: 

dockerfile in src/backend/
sudo docker build -t alexdecb/video-drom-back:latest .
sudo docker push alexdecb/video-drom-back:latest 

frontend:
dockerfile in src/frontend
sudo docker build -t alexdecb/video-drom-front:latest .
sudo docker push alexdecb/video-drom-front:latest

video-server:
nothing

mpd-generator:

dockerfile in src/mpd-generator
sudo docker build -t alexdecb/mpd-generator:latest . 
(3 gb, tarda)
sudo docker push alexdecb/mpd-generator:latest

## Kubernetes resources:

volumes:
mariadb and video-files

set up nfs server:
kubectl create -f ./kubernetes-resources/volumes/video-files/nfs-server.yaml

create the mariadb volume:
kubectl create -f ./kubernetes-resources/volumes/mariadb/

DEPLOYMENTS

Database
kubectl create -f ./kubernetes-resources/deployments/maria-database/


MPD-Generator
kubectl create -f ./kubernetes-resources/deployments/mpd-generator/
Luego entrar y crear interfaz l2sm:

Backend
Check the mariadb service ip with kubectl get services
Copy paste it to 
./kubernetes-resources/deployments/backend/backend-deploy.yaml in spec.containers.env {DB_URL}
And deploy:

kubectl create -f ./kubernetes-resources/deployments/backend


Video-Server

kubectl create -f ./kubernetes-resources/deployments/video-server

Frontend
Modify the app-urls-cm.yaml file adding the video-server and backend external urls
kubectl create -f ./kubernetes-resources/deployments/

