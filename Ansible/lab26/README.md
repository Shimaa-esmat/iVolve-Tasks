# Lab 26: Initial Ansible Configuration and Ad-Hoc Execution

## Objective

Set up Ansible on the control node, enable passwordless SSH access to the managed node, create an inventory, and execute ad-hoc commands.

---

## Step 1: Install Ansible on Control Node


```bash
sudo apt update
sudo apt install -y ansible
ansible --version
```

![version](/Screenshots/ansiblev.png)

---

## Step 2: Generate SSH Key on Control Node

```bash
ssh-keygen -t rsa -b 4096 -C "ansible-control-key"
ls -la ~/.ssh/
```

![key](/Screenshots/key.png)
![verifykey](/Screenshots/verifykey.png)

---

## Step 3: Create Ansible Configuration File and Inventory File

```bash
cat ansible.cfg 
[defaults]
inventory = ./inventory
remote_user = shimaa
host_key_checking = False
deprecation_warnings = False
interpreter_python = auto_silent

[privilege_escalation]
become = False  
become_method = sudo
become_user = root
become_ask_pass = False
```

```bash
shimaa@Ubuntu24:~/iVolve-Tasks/Ansible/lab26$ cat inventory 
[managed_nodes]
node1 ansible_host=192.168.113.137 ansible_user=shimaa
```

![AnsibleConnectivity](/Screenshots/AnsibleConnectivity.png)

---

## Step 4:Execute Ad Hoc Commands


```bash
ansible all -m shell -a "df -h"
ansible all -m shell -a "df -h /"
```

![diskspace](/Screenshots/diskspace.png)
