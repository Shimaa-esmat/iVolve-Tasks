# Lab 11: Namespace Management and Resource Quota Enforcement

![Kubernetes Logo](https://raw.githubusercontent.com/kubernetes/kubernetes/master/logo/logo.png)

---

## bjective

Create a namespace called ivolve
Apply resource quota to limit pods to 2 within the namespace

---

## Step 1: Create the namespace

```bash
kubectl apply -f ns-ivolve.yaml
```

---

## Step 2: Apply the resource quota

```bash
kubectl apply -f resource-quota.yaml
```

---

## Step 3: Verify

```bash
kubectl get namespaces

kubectl get resourcequota -n ivolve
kubectl describe resourcequota ivolve-quota -n ivolve
```
