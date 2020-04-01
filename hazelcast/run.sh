kubectl delete statefulset.apps/hazelcast
kubectl delete service hazelcast-service
kubectl delete service hazelcast-management-service
kubectl delete deployment hazelcast-management-center

kubectl apply -f k8s
# http://localhost:8080/hazelcast-mancenter