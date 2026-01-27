# Lab 18:Control Pod-to-Pod Traffic using NetworkPolicy

![Kubernetes Logo](https://raw.githubusercontent.com/kubernetes/kubernetes/master/logo/logo.png)

---

## bjective

- Allow access to the MySQL Pod only from Node.js application pods
- Block all other pods from accessing MySQL
- Restrict access to TCP port 3306 only

---

## Step 1:Verify Namespace and Resources

### Check if the namespace exists

```bash
kubectl get namespace
```

### Verify MySQL Pod

```bash
kubectl get pods -n ivolve -l app=mysql
```

### Verify Node.js Pod

```bash
kubectl get pods -n ivolve -l app=nodejs
```

### Verify MySQL Service

```bash
kubectl get svc -n ivolve
```

---

## Step 2:Create the NetworkPolicy

```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: allow-app-to-mysql
  namespace: ivolve
spec:
  podSelector:
    matchLabels:
      app: mysql
  policyTypes:
    - Ingress
  ingress:
    - from:
        - podSelector:
            matchLabels:
              app: nodejs
      ports:
        - protocol: TCP
          port: 3306

```

```bash
kubectl apply -f networkpolicy.yaml
kubectl get networkpolicy -n ivolve
```

---

## Step 3:Test Connectivity

```bash
kubectl exec -it deploy/nodejs-app -n ivolve -- nc -zv mysql-headless 3306
```

```bash
mysql-headless (10.244.0.3:3306) open
```

Test from an Unauthorized Pod

```bash
kubectl run testpod --rm -it --image=busybox -n ivolve -- nc -zv mysql-headless 3306
```

```bash
pod "test" deleted from ivolve namespace
error: timed out waiting for the condition
```

---
