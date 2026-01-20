
![Kubernetes Logo](https://raw.githubusercontent.com/kubernetes/kubernetes/master/logo/logo.png)
---

# Lab 10: Node Isolation Using Taints in Kubernetes

- Run Kubernetes cluster with 2 nodes
- Taint one node with key-value `node=worker` and effect `NoSchedule`
- Describe nodes to verify taint

---

## Step 1: Start Minikube with 2 nodes

```bash
minikube start --nodes=2
```

![Start](Screenshots/start.png)

# Verify Nodes

```bash
kubectl get nodes
```

![test](Screenshots/test.png)

---

## Step 2: Apply taint to worker node

```bash
kubectl taint nodes minikube node=worker:NoSchedule
```

![taint](Screenshots/taint.png)
![taint](Screenshots/taint1.png)
---

## Step 3: Remove taint from control-plane

```bash
kubectl taint nodes minikube node=worker:NoSchedule-
```

![stop](Screenshots/stop.png)

---

## Summary

* Worker node is isolated
* Pods without tolerance cannot be scheduled on worker node
