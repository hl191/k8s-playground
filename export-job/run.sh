# Cleanup
#kubectl delete deployments,services export-job

# Build
mvn clean install
docker build -t me/export-job:v1.0 .
# docker run me/export-job:v1.0

# Deploy
kubectl create -f k8s

# kubectl delete job $(kubectl get job -o=jsonpath='{.items[?(@.status.succeeded==1)].metadata.name}')