# Lab  13: Persistent Storage Setup for Application Logging

![Kubernetes Logo](https://raw.githubusercontent.com/kubernetes/kubernetes/master/logo/logo.png)

---

## bjective

Define a PersistentVolume (PV) with hostPath storage
Define a PersistentVolumeClaim (PVC) to request storage
Configure persistent storage for application logging

---

## Step 1: Create the hostPath directory on nodes

```bash
minikube ssh -- "sudo mkdir -p /mnt/app-logs && sudo chmod 777 /mnt/app-logs"
```

---

## Step 2: Verify directory creation

```bash
minikube ssh -- ls -ld /mnt/app-logs
```

### Output

```bash
shimaa@Ubuntu24:~/iVolve-Tasks/Kubernetes/day6/lab13$ minikube ssh -- ls -ld /mnt/app-logs
drwxrwxrwx 2 root root 4096 Jan 26 23:05 /mnt/app-logs
```

---

## Step 3: Create the PersistentVolume

```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
name: app-logs-pv
spec:
capacity:
storage: 1Gi
accessModes:
- ReadWriteMany
persistentVolumeReclaimPolicy: Retain
hostPath:
path: /mnt/app-logs
type: DirectoryOrCreate
```

```bash
kubectl apply -f pv-app-logs.yaml
kubectl get pv
```

---

## Step 4:Create the PersistentVolumeClaim

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
name: app-logs-pvc
namespace: ivolve
spec:
accessModes:
- ReadWriteMany
resources:
requests:
storage: 1Gi
volumeName: app-logs-pv
storageClassName: ""
```

```bash
kubectl apply -f pvc-app-logs.yaml
kubectl get pvc -n ivolve
```

---

## Step 5: Verify Binding

```bash
kubectl get pv,pvc -n ivolve
```

### Output 

```bash
shimaa@Ubuntu24:~/iVolve-Tasks/Kubernetes/day6/lab13$ kubectl get pv,pvc -n ivolve
NAME                                                        CAPACITY   ACCESS MODES   RECLAIM POLICY   STATUS      CLAIM                           STORAGECLASS   VOLUMEATTRIBUTESCLASS   REASON   AGE
persistentvolume/app-logs-pv                                1Gi        RWX            Retain           Bound       ivolve/app-logs-pvc                            <unset>                          24m
```
