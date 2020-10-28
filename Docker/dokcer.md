# Docker & Kubenetes

[toc]

## Docker

**Docker Client:** Issuing commands

**Dokcer Server:** Responsible for creating images, running containers

**Image:** A snapshot of file system and a start command.

**Dockerfile**: Config file that used to create image.

- `docker build -f Dockerfile.dev .`(default filename is **Dockerfile**)In this case, we use `Dockerfile.dev` as our build file.

```dockerfile
FROM node:alpine
WORKDIR '/app'
COPY package.json .
RUN npm install
COPY . .
RUN npm run build

FROM nginx
EXPOSE 80
COPY --from=0 /app/build /usr/share/nginx/html
```

**Alpine:** Compact and small version image.

### Docker-Compose

 A separate CLI used to start up multiple docker containers at the same time and automates some of the long-winded arguments we were passing to 'docker run'.Docker compose can simplify the docker commands by create a file called `docker-compose.yml`.Below are some attributes for `.yml` file.

1. `version`: version of docker compose
2.  `services`: specify type and name of the containers

```yaml
version: '3'
services:
    web: # this is container name
        stdin_open: true
        build:
            context: .
            dockerfile: Dockerfile.dev
        ports: 
            - "3000:3000"
        volumes:
            - /app/node_modules
            - .:/app
        command: ["npm", "run", "test"] #overwrite default command 
```

### Component of Docker File

1. Specify a base image.
2. Run some commands to install additional programs.
3. Specify a command to run on container startup.

### Docker Restart Policies

`"no"`:Never attempt to restart(normal no is a keyword in yml file).

`always` Restart for any reason.

`on-failure`Only restart with error code.

`unless-stopped` Always restart unless we forcibly stop it.

### Dockerfile Keyword

1. `FROM` select image
2. `RUN` run the commands inside the container
3. `CMD` command to set the start up command fro container
4. `COPY <FROM> <TO>` copy file from local to container
5. `WORKDIR <path>` set working directory

### Process of Build Dockefile

1. Get base image from local or remotely
2. Create a temporary container from the base image and run the **commands from component step 2** on that container and create **new image** (will be cached at somewhere for future use). If the commands order of component step 2 is different, the cache will no longer works.
3. Create a new temporary container from the **previous new image**, the **command from component step 3** will be store start up command. Create the final image.

**Limiataion:** Host on Linux machine only, when they get install on windows or Mac, a linux Virtual Machine required to run docker.

By running command `docker run hello-world`, docker will

1. Check image named `hello-world` locally(look into **image cache**). If it is not existed check with docker hub(Images repo that holded by docker) and pull the image to local machine.
2. With the image, loaded up into memory and create a container and run the container.

### Common Commands

1. `docker Run` = `docker create` + `docker start`, create and run the container.
   - Corresponding `docker-compose up` for docker-compose
   - `docker-compose up --build` will include `docker build .` in with `docker run image`
2. `docker build dockerId/nameOfContainer:latest .` Build image use Dockerfile that in current directory.
3. **Overwrite the default start command** by specify command after the image name. 
4. `docker ps`  list of running containers, `docker ps --all` all containers were created.
5. `docker system prune` delete all stopped containers and some other stuff.
6. `docker logs ` show the logs.
7. `docker stop` send a **SIGTERM** to container and shut it down.
   - `docker compose down` shut down all the linked containers
8. `docker kill` send a **SIGKILL** to container and force it to shut down.
9. `docker exec -it <container id> <command>` pass the command into containner and execute it inside the container. 
   1. `docker exec -it <container id> sh` give back shell.
10. `docker commit -c <command>  <container id> ` create image from container

### Common Flags

`-a` show output from container.

`-i` attach current input into container input.

`-t` beautify output text.

`-it` allow us to provide input to the container.

`-c`  allow set the default command.   

`-p <FROM>:<TO>`  port mapping.

`-d` Run container in background and print container ID.

`-f`Specify file name that is going to be used to build out the image.

`-v` Set up a volume.

### Docker Volume

Start a reference to local machine disk instead of snapsot of data.

`docker run -p 3000:3000 -v /app/node_modules -v $(pwd):/app <image_id>`

`-v $(pwd):/app` Map current working directory into the '/app' folder in container.

`-v /app/node_modules` Put a bookmark on the node_modules folder.

### Multi Phase

```dockerfile
FROM node:alpine
WORKDIR '/app'
COPY package.json .
RUN npm install
COPY . .
RUN npm run build 

FROM nginx
EXPOSE 80
COPY --from=0 /app/build /usr/share/nginx/html
```

## Kubernete

A system to deploy containerized apps with master-node architecture.

**Connect to docker CLI in the minikube**:

```bash
eval $(minikube docker-env) # limited to current terminal window
```

**Combine two or more config files into one config file**:

Use `---` to separate two object of configs in a single config file.

### Strucutre

For kubernetes, every container will be hosted in **Deployment** and A **Node** will contains more than one **Deployments**.

1. Every identity container comes with one **Deployment** and one **ClusterIP**(only if deployment needs to be access by other objects) which both are created by config file(in yaml format).
2. Every container are able to communicate with each other with **ClusterIP** but outside traffics are not able to access them.
3. A **Ingress Service** (created by config file) will be created to act like a proxy server which routes all the traffics to corresponding **ClusterIP**.

### Development

Use minikube to manage

- **Local Kubernetes Dev process**
  - Install **Kubectl**(CLI for interacting with our master)
    - run `brew install kubectl`.
  - Install a Virtual machine driver(VMware, virtual box etc), used to make a VM as single node.
    - download any VM.
  - Install **minukube**,runs a single node (kubernete node) using VM
    - run `brew install minikube`.
    - start **minikube** by run `minikube start`.

- **minikuke(dev only)**: Create kubernetes cluster on local machine and manage virtual machine itself.
- **Kubectl(both dev and production)**: Manage containers in the node.

### Config File

The file will be in **yaml** format. Config file is used to create an object.

```yaml
# pod
apiVersion: v1
kind: Pod #type of object
metadata:
    name: client-pod # name of obj
    lalels:
        component: web #coupled with service
spec:
    containers:
        - name: client #ref purpose
          image: jiang1993/docker-client
          ports:
            - containerPort: 3000 #expose port 3000 of container to outside world 
            
#Service,  this should be in another yaml file
apiVersion: v1
kind: Service
metadata:
    name: client-node-port # match with config file name
spec:
    type: NodePort # sub type of service
    ports:
        - port: 3050 # port for another pod/container to access
          targetPort: 3000 # open port for current pod
          nodePort: 31515 #(30000-32767) port for outside world
    selector: # labele-selector system with key-value pair component: web.
        component: web
        
#ClusterIP Service,  this should be in another yaml file
apiVersion: v1
kind: Service
metadata:
    name: client-cluster-ip-service # match with config file name
spec:
    type: ClusterIP # sub type of service
    ports:
        - port: 3000 # port for another pod/container to access
          targetPort: 3000 # open port for current pod
    selector: # labele-selector system with key-value pair component: web.
        component: web
```

- **Objects**
  - **StatefulSet**:
  - **ReplicaController**:
  - **Pod**: Run a container
  - **Service**: Setup networking
    - **NodePort**: **dev only**, Exposes a container to the outside world.
    - **ClusterIP**: Only objects within the cluster are able to access the clusterIP pointing at.
    - **LoadBalancer**: Legacy way to getting network traffic into a cluster.
    - **Ingress**: Exposes a set of services to the outside world(replace LoadBalancer).There are two types of Nginx Ingress.
      - [Ingress-nginx](https://github.com/kubernetes/ingress-nginx)  which a community led project
      - [kubernetes-ingress](github.com/nginxinc/kubernetes-ingress) which lead by company nginx
- **apiVersion**
  - **v1**: contains **Pod**, **Event** and other object types
  - **apps/v1**: Different set of types like **StatefulSet**, **ContollerRevision**

### Cubectl Command

- `apply` Create an object or change configuration of the object in the cluster 
  - `kubectl apply -f <filename>`: Feed a config file to kubectl where`-f` specify a file that has the config changes.
  - `kubectl apply -f <foldername>`: Will Feed all the config file inside the folder to kubectl.
- `get` Retrieve information about a running object
  - `kubectl get pods`: Get status for all pods.
- `delete` Delete a running object.
  - `kubectl delete -f <config file>` Remove the running object which associate with the given config file.
- `set` Change a property.
  - `kubectl  set image <object_type>/<object_name> <container_name>=<new image>` Change the deployment image.
- `logs` Check logs.
  - `kubectl logs <name of the object>` check logs for the specify object. 

### Depolyment

A Deployment provides **declarative updates** for Pods and ReplicaSets.

```yaml
# config file for deployment
apiVersion: apps/v1
kind: Deployment
metadata:
	name: client-deployment # match with config file name
spec:
	replicas: 1
	selector: # deployment will ask master to create the pod and use this to ref it
		matchLabels: 
			component: web
	template: # specify config for pod below
  	metadata:
  		labels:
  			component: web
    spec:
        containers:
        	- name: client
						image: stephengrider/multi-client
            ports:
            	- containerPort: 3000
```

### Update Pod with Latest Image

As kubernetes does not provide a easy way to automatecally update the new image with asscoiated pods, we need to do some extra works to make it happen. Below are some work arounds:

1. Manually delete pods to get the deployment to recreate the mwith the latest version(Bad).
2. Tag build images with a real version number and specify that version in the config file.
   - Update config file causing a update to the branch, it will trigger the whole deployment again.
3. Use an **imperative command** to update the imgae version that the deployment should use.
   - Tag the image with a version number and push to docker hub.
   - Run a **kubectl** command forcing the deployment to use the new image version.
     - `kubectl set image <object_type>/<object_name> <container_name>=<new image>`

### Persistent Volume Claim

A advertisement(claim) for peyersistent volume that can be provided to **pods**, but it may not be ready yet. It can be dynamically provisioned if necessary.

**Problem**: The postgres database was created in a container and is located inside the virtual machine(Deployment object), any crash happened to the container will potentially destroy all the data.

**Solution**: Provide persistent volume which is outside of virtual machine to the database.

```yaml
# on db deployment
# create volumes under template
template:
	spec:
		volumes:
			- name: postgres-storage
				persistentVolumeClaim:
					claimName: database-persistent-volume-claim
		container:
			- name: postgres
				image: postgres
				ports:
					- containerPort:5432
				volumeMounts:
					- name: postgres-storage
						mountPath: /var/lib/postgresql/data # default path for postgres
						subPath: postgres # data will store in subpath under mountPath
						
# on PersistentVolumeClaim
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
	name: database-persistent-volume-claim
spec:
	accessModes:
		- ReadWriteOnce #can be used by a single node , ReadWriteMany refers to many nodes
	resources:
		requests:
			storage: 2Gi
	# storageClassName   may need to be set in cloud env
```

### Secrets Object

Any sensitive information that need to be stored secretly in the kubernetes. Secrets object provide such function but instead of using config to create secrets object, we need to use **imperative** method to create the object so that the content is not visible to others. The command provides below:

`kubectl create secret generic  <secret_name> --from-literal key=value` where

1. `create` Imperative command to create a new object.
2. `secret` Type of object that we are going to create.
3. `generic` Type of secret. othet types like`docker-registry` is refer to authentication with some custome docker registry and '`tls` is for http set-up.
4. `--from-literal`Indicates we are going to add the secret information into this command.
5. `key=value` Key value pair for the secret.

```yaml
# Using for secret object in the env section, 
# instead of using name and value pair
env:
	- name:
		value:
# we use
env:
	- name: PASSWORD
		valueFrom:
			secretKeyRef:
				name: #name of the secret
				key: # key of the secret 
```

**Notes**: To change the default password of postgres, specify the `POSTGRES_PASSWORD`  variable in the environment (`env` in the config file).

### Ingress

With **Ingress config file**, it will create an **Ingress controller** to make an load balancer service with additional deployment object(**nginx pod**) to handle the traffic. Addtional to that, it will create another pair of **ClusterIp Service** and Depolyment(**default-backend pod**) to handle health check in order to make sure everything is working correctly.

```yaml
# ingress services
apiVersion: networking.k8s.io/v1beta1
# UPDATE THE API
kind: Ingress
metadata:
  name: ingress-service
  annotations:
    kubernetes.io/ingress.class: nginx # ingress controller base on nginx project
    nginx.ingress.kubernetes.io/use-regex: 'true'
    nginx.ingress.kubernetes.io/rewrite-target: /$1 #rewrite /api/ to /
spec:
  rules:
    - http:
        paths:
          - path: /?(.*)
            backend:
              serviceName: client-cluster-ip-service
              servicePort: 3000
          - path: /api/?(.*)
            backend:
              serviceName: server-cluster-ip-service
              servicePort: 5000
```

### Production

1. Create github repo
2. Tie repo to Travis CI
   1. Go to [Travis CI](travis-ci.rog), sync it with github first and enable the project 
   2. Use ruby docker to install travis and use travis to encrypt login credentials.
   3. push the encrypted file into GitHub. 
3. Create Google Cloud project
4. Add deployment scripts to the repo

Example of `.tavis.ymal`

```yaml
sudo: required
services:
  - docker
env:
  global:
    - SHA=$(git rev-parse HEAD)  #create env variable
    - CLOUDSDK_CORE_DISABLE_PROMPTS=1 # disable gcloud prompt
before_install:
  - openssl aes-256-cbc -K encrypted0c35eebf403ckey−ivencrypted_0c35eebf403c_iv -in service-account.json.enc -out service-account.json -d
  - curl https://sdk.cloud.google.com | bash > /dev/null;
  - source $HOME/google-cloud-sdk/path.bash.inc
  - gcloud components update kubectl
  - gcloud auth activate-service-account --key-file service-account.json
  - gcloud config set project skilful-berm-214822
  - gcloud config set compute/zone us-central1-a
  - gcloud container clusters get-credentials multi-cluster
  - echo "DOCKER_PASSWORD"|dockerlogin−u"DOCKER_USERNAME" --password-stdin
  - docker build -t stephengrider/react-test -f ./client/Dockerfile.dev ./client

script:
  - docker run -e CI=true stephengrider/react-test npm test

deploy:
  provider: script
  script: bash ./deploy.sh
  on:
    branch: master
```

Example of `deploy.sh`

```bash
docker build -t stephengrider/multi-client:latest -t stephengrider/multi-client:$SHA -f ./client/Dockerfile ./client
docker build -t stephengrider/multi-server:latest -t stephengrider/multi-server:$SHA -f ./server/Dockerfile ./server
docker build -t stephengrider/multi-worker:latest -t stephengrider/multi-worker:$SHA -f ./worker/Dockerfile ./worker

docker push stephengrider/multi-client:latest
docker push stephengrider/multi-server:latest
docker push stephengrider/multi-worker:latest

docker push stephengrider/multi-client:$SHA
docker push stephengrider/multi-server:$SHA
docker push stephengrider/multi-worker:$SHA

kubectl apply -f k8s
# update image if necessary
kubectl set image deployments/server-deployment server=stephengrider/multi-server:$SHA
kubectl set image deployments/client-deployment client=stephengrider/multi-client:$SHA
kubectl set image deployments/worker-deployment worker=stephengrider/multi-worker:$SHA
```

#### Install Ingress

Use **Helm** to install Nginx Ingress controller.

```yaml
# ingress config
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: ingress-service
  annotations:
    kubernetes.io/ingress.class: nginx
    nginx.ingress.kubernetes.io/rewrite-target: /$1
    certmanager.k8s.io/cluster-issuer: 'letsencrypt-prod'
    nginx.ingress.kubernetes.io/ssl-redirect: 'true'
spec:
  tls:
    - hosts:
        - k8s-multi.com
        - www.k8s-multi.com
      secretName: k8s-multi-com #secret name to get certificate
  rules:
    - host: k8s-multi.com
      http:
        paths:
          - path: /?(.*)
            backend:
              serviceName: client-cluster-ip-service
              servicePort: 3000
          - path: /api/?(.*)
            backend:
              serviceName: server-cluster-ip-service
              servicePort: 5000
    - host: www.k8s-multi.com
      http:
        paths:
          - path: /?(.*)
            backend:
              serviceName: client-cluster-ip-service
              servicePort: 3000
          - path: /api/?(.*)
            backend:
              serviceName: server-cluster-ip-service
              servicePort: 5000
```

#### HTTP Setup

**Issuer** is an object that will tell **cer manager** where to get the certificate from

```yaml
apiVersion: cert-manager.io/v1alpha2
kind: ClusterIssuer
metadata:
  name: letsencrypt-prod
spec:
  acme:
    server: https://acme-v02.api.letsencrypt.org/directory
    email: "youremail@email.com"
    privateKeySecretRef:
      name: letsencrypt-prod
    solvers:
      - http01:
          ingress:
            class: nginx
```

**Certificate** is an object that describing details about the certificate that should be obtained and create one secret will holds the certificate.

```yaml
apiVersion: certmanager.k8s.io/v1alpha2
kind: Certificate
metadata:
  name: k8s-multi-com-tls
spec:
  secretName: k8s-multi-com # look up secret for this name
  issuerRef:
    name: letsencrypt-prod
    kind: ClusterIssuer
  commonName: k8s-multi.com
  dnsNames:
    - k8s-multi.com
    - www.k8s-multi.com
  acme:
    config:
      - http01:
          ingressClass: nginx
        domains:
          - k8s-multi.com
          - www.k8s-multi.com
```

**Cert Manager** is created by **Helm** to set up infra to respond to HTTP challenge, get certificate and stores it in **secret**

### Skaffold For Local Development

skafold watches the kubernetes application changes. Once it detects the changes, it will take certain action base on different modes.

1. Rebuild client imahe from scratch(**rebuild image**), update K8S(kubernetes application).
2. Inject updated files into the client pod, rely on react app to automatically update itself.
   1. In this mode, make sure the client pod can automatelly update itself.

```yaml
apiVersion: skaffold/v1beta2
kind: Config
build:
  local:
    push: false # no push to hub
  artifacts:
    - image: stephengrider/multi-client
      context: client
      docker:
        dockerfile: Dockerfile.dev
      sync: # take update file and inject the changeI
        '**/*.js': .
        '**/*.css': .
        '**/*.html': .
    - image: stephengrider/multi-server
      context: server
      docker:
        dockerfile: Dockerfile.dev
      sync:
        '**/*.js': .
    - image: stephengrider/multi-worker
      context: worker
      docker:
        dockerfile: Dockerfile.dev
      sync:
        '**/*.js': .
deploy:
  kubectl:
    manifests:
      - k8s/client-deployment.yaml
      - k8s/server-deployment.yaml
      - k8s/worker-deployment.yaml
      - k8s/server-cluster-ip-service.yaml
      - k8s/client-cluster-ip-service.yaml
```