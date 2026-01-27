# Lab 17: Pod Resource Management with CPU and Memory Requests and Limits

![Kubernetes Logo](https://raw.githubusercontent.com/kubernetes/kubernetes/master/logo/logo.png)

---

## bjective

Update Node.js Deployment with resource requests and limits
Verify applied resources using kubectl describe
Monitor real-time usage with kubectl top


---

## Step 1:Enable metrics-server

```bash
minikube addons enable metrics-server
kubectl get deployment metrics-server -n kube-system
```

---

## Step 2:Deploy with resource limits

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nodejs-app
  namespace: ivolve
spec:
  replicas: 2
  selector:
    matchLabels:
      app: nodejs-app
  template:
    metadata:
      labels:
        app: nodejs-app
    spec:
      tolerations:
      - key: "node"
        operator: "Equal"
        value: "worker"
        effect: "NoSchedule"
      initContainers:
      - name: db-setup
        image: mysql:5.7
        command: ['sh', '-c']
        env:
        - name: DB_HOST
          valueFrom:
            configMapKeyRef:
              name: nodejs-config
              key: DB_HOST
        - name: DB_USER
          valueFrom:
            configMapKeyRef:
              name: mysql-config
              key: DB_USER
        - name: DB_PASSWORD
          valueFrom:
            secretKeyRef:
              name: mysql-secret
              key: DB_PASSWORD
        - name: MYSQL_ROOT_PASSWORD
          valueFrom:
            secretKeyRef:
              name: mysql-secret
              key: MYSQL_ROOT_PASSWORD
        resources:
          requests:
            cpu: "100m"
            memory: "128Mi"
          limits:
            cpu: "200m"
            memory: "256Mi"
      containers:
      - name: nodejs
        image: shimaaesmat/nodejs:lab9
        ports:
        - containerPort: 3000
        envFrom:
        - configMapRef:
            name: nodejs-config
        env:
        - name: DB_USER
          valueFrom:
            configMapKeyRef:
              name: mysql-config
              key: DB_USER
        - name: DB_PASSWORD
          valueFrom:
            secretKeyRef:
              name: mysql-secret
              key: DB_PASSWORD
        resources:
          requests:
            cpu: "1"
            memory: "1Gi"
          limits:
            cpu: "2"
            memory: "2Gi"
        volumeMounts:
        - name: app-storage
          mountPath: /app/data
      volumes:
      - name: app-storage
        persistentVolumeClaim:
          claimName: app-logs-pvc
```

```bash
kubectl delete deployment nodejs-app -n ivolve
kubectl apply -f nodejs-deployment-with-resources.yaml
```

---

## Step 3:Verify resources with kubectl describe

```bash
kubectl describe pod $POD -n ivolve
```

```
ontainers:
  nodejs:
    Image:      shimaaesmat/nodejs:lab9
    Port:       3000/TCP
    Host Port:  0/TCP
    Limits:
      cpu:     2
      memory:  2Gi
    Requests:
      cpu:     1
      memory:  1Gi
    Environment Variables 
```

---

## Step 4:Monitor with kubectl top
```bash
kubectl top pod
```

Expected output:

```bash
NAME             CPU(cores)   MEMORY(bytes)
nodejs-app       50m          256Mi
```

---
