# Cleanup
kubectl delete deployments,services goods-service

# Build
mvn clean package
docker build -t me/goods-service:v1.0 .

# Deploy
kubectl apply -f k8s