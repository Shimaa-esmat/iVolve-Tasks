#!/usr/bin/env groovy

def call(Map config = [:]) {
    stage('Deploy on Kubernetes') {
        def namespace = config.namespace ?: 'default'
        def deploymentFile = config.deploymentFile ?: 'k8s/deployment.yaml'
        def kubeconfig = config.kubeconfigId ?: 'kubeconfig-credentials'
        
        echo "Deploying application to Kubernetes namespace: ${namespace}"
        
        withCredentials([file(credentialsId: kubeconfig, variable: 'KUBECONFIG')]) {
            sh """
                # Apply Kubernetes manifests
                kubectl apply -f ${deploymentFile} -n ${namespace} --kubeconfig=\${KUBECONFIG}
                
                # Wait for deployment to be ready
                kubectl rollout status deployment/demo-app -n ${namespace} --kubeconfig=\${KUBECONFIG} --timeout=5m
                
                # Show deployment status
                kubectl get pods -n ${namespace} --kubeconfig=\${KUBECONFIG}
            """
        }
        
        echo "Deployment to Kubernetes completed successfully"
    }
}
