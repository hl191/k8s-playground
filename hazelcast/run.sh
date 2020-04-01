# kubectl delete service hazelcast-service
# kubectl delete service hazelcast-management-service
# kubectl delete deployment hazelcast-management-center

kubectl apply -f https://raw.githubusercontent.com/hazelcast/hazelcast-code-samples/master/hazelcast-integration/kubernetes/rbac.yaml
kubectl apply -f https://raw.githubusercontent.com/hazelcast/hazelcast-code-samples/master/hazelcast-integration/kubernetes/config.yaml
kubectl apply -f https://raw.githubusercontent.com/hazelcast/hazelcast-code-samples/master/hazelcast-integration/kubernetes/hazelcast.yaml

kubectl apply -f k8s
# http://localhost:8888/hazelcast-mancenter

# https://gist.github.com/anonymous/ca3d46c5185f8def24d16e0877f7486b