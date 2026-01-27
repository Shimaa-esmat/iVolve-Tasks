# Lab  12: Managing Configuration and Sensitive Data with ConfigMaps and Secrets

![Kubernetes Logo](https://raw.githubusercontent.com/kubernetes/kubernetes/master/logo/logo.png)

---

## bjective

Define a ConfigMap for non-sensitive MySQL configuration
Define a Secret for sensitive MySQL credentials using base64 encoding

---

## Step 1: Ensure the ivolve namespace exists

```bash
kubectl get namespace ivolve
```

---

## Step 2: Create ConfigMap

```bash
kubectl apply -f mysql-config.yaml
```

---

## Step 3: Create Secret

```bash
kubectl apply -f mysql-secret.yaml
```

---

## Step 4: Verify ConfigMap

```bash
kubectl get configmap -n ivolve
kubectl describe configmap mysql-config -n ivolve
```

---

## Step 5: Verify Secret

```bash
kubectl get secret -n ivolve
kubectl describe secret mysql-secret -n ivolve
```

## Step 6: CView Secret data (base64 encoded)

```bash
kubectl get secret mysql-secret -n ivolve -o yaml
```

## Notes

Secrets are base64 encoded, not encrypted
