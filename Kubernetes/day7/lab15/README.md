# Lab 15: Node.js Application Deployment with ClusterIP service

![Kubernetes Logo](https://raw.githubusercontent.com/kubernetes/kubernetes/master/logo/logo.png)

---

## Step 1: Apply Persistent Volume (PV)

```bash
kubectl apply -f pv.yaml
```

---

## Step 2: Apply Persistent Volume Claim (PVC)

```bash
kubectl apply -f pvc.yaml
```

---

## Step 3: Apply ConfigMap

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: nodejs-config
data:
  DB_HOST: mysql-headless
  DB_USER: root
  DB_PASSWORD: password123456
  PORT: "3000"
```

```bash
kubectl apply -f configmap.yaml
```

## Step 4: Apply Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nodejs-app
spec:
  replicas: 2
  selector:
    matchLabels:
      app: nodejs
  template:
    metadata:
      labels:
        app: nodejs
    spec:
      tolerations:
        - key: node
          operator: Equal
          value: worker
          effect: NoSchedule
      containers:
        - name: nodejs
          image: shimaaesmat/nodejs:lab9
          ports:
            - containerPort: 3000
          envFrom:
            - configMapRef:
                name: nodejs-config
          volumeMounts:
            - name: nodejs-logs
              mountPath: /app/logs
      volumes:
        - name: nodejs-logs
          persistentVolumeClaim:
            claimName: nodejs-pvc
```

```bash
kubectl apply -f deployment.yaml
```

---

## Step 5: Apply ClusterIP Service

```yaml
apiVersion: v1
kind: Service
metadata:
  name: nodejs-service
spec:
  selector:
    app: nodejs
  ports:
    - protocol: TCP
      port: 80
      targetPort: 3000
  type: ClusterIP
```

```bash
kubectl apply -f service.yaml
```

---

## Verify Pods and Service

 ```bash
 kubectl get pods
kubectl get svc
kubectl get pv,pvc
 ```

---
