# delete.sh
#!/bin/bash
cd ~/GitHub/microservices-java-spring-boot/k8s && kubectl delete \
  -f secrets.yaml \
  -f configmap.yaml \
  -f volumes.yaml \
  -f mysql.yaml \
  -f services.yaml \
  -f loadbalancer.yaml \
  -f deployment-apps.yaml