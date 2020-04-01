# Cleanup
kill $(ps aux | grep 'kubectl' | awk '{print $1}')
kubectl delete deployments,services goods-service

# Build
mvn clean package
docker build -t me/goods-service:v1.0 .

# Deploy
kubectl apply -f k8s
kubectl port-forward service/goods-service 8080:8080