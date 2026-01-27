# Lab 16: Kubernetes Init Container for Pre Deployment Database Setup

![Kubernetes Logo](https://raw.githubusercontent.com/kubernetes/kubernetes/master/logo/logo.png)

---

## bjective

Modify an existing Node.js Deployment to include an init container that prepares the MySQL database before the application starts. The init container will create the ivolve database and a user with all privileges on that database.


---

## Step 1:Create ConfigMap for DB Connection

```bash
kubectl apply -f db-config.yaml
kubectl get configmap db-config -n ivolve
```

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: db-config
  namespace: ivolve
data:
  MYSQL_HOST: "mysql"
  MYSQL_PORT: "3306"
  MYSQL_ROOT_USER: "root"
  MYSQL_ROOT_PASSWORD: "rootpassword"
  MYSQL_DB: "ivolve"
  MYSQL_USER: "ivolve_user"
  MYSQL_PASSWORD: "ivolve_pass"
```

---

## Step 2:Create Node.js Deployment

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
      app: nodejs
  template:
    metadata:
      labels:
        app: nodejs
    spec:
      initContainers:
        - name: init-mysql
          image: mysql:5.7
          command:
            - sh
            - -c
            - |
              mysql -h $MYSQL_HOST -P $MYSQL_PORT -u $MYSQL_ROOT_USER -p$MYSQL_ROOT_PASSWORD -e "
              CREATE DATABASE IF NOT EXISTS $MYSQL_DB;
              CREATE USER IF NOT EXISTS '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD';
              GRANT ALL PRIVILEGES ON $MYSQL_DB.* TO '$MYSQL_USER'@'%';
              FLUSH PRIVILEGES;"
          envFrom:
            - configMapRef:
                name: db-config
      containers:
        - name: nodejs
          image: shimaaesmat/nodejs:lab9
          ports:
            - containerPort: 3000
          envFrom:
            - configMapRef:
                name: nodejs-config
          volumeMounts:
            - name: app-logs
              mountPath: /app/logs
      volumes:
        - name: app-logs
          persistentVolumeClaim:
            claimName: app-logs-pvc
      tolerations:
        - key: "node"
          operator: "Equal"
          value: "worker"
          effect: "NoSchedule"

```

```bash
kubectl apply -f deployment.yaml
kubectl get pods -n ivolve -w
```

---

## Step 3:Verify Init Container Execution

```bash
kubectl get pods -n ivolve
kubectl describe pod nodejs-app-<pod-id> -n ivolve
```

---

## Step 4:Connect to MySQL to Verify DB and User

```bash
kubectl exec -it mysql-0 -n ivolve -- mysql -u root -p
```

Inside MySQL

```bash
SHOW DATABASES;
SELECT user, host FROM mysql.user;
SHOW GRANTS FOR 'ivolve_user'@'%';
```

---
