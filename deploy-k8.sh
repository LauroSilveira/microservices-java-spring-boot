# deploy.sh
#!/bin/bash
kubectl apply \
  -f secrets.yaml \
  -f configmap.yaml \
  -f volumes.yaml \
  -f services.yaml \
  -f mysql.yaml \
  -f deployment-apps.yaml \
  -f loadbalancer.yaml