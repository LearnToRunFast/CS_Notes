## Docker

**Docker Client:** Issuing commands

**Dokcer Server:** Responsible for creating images, running containers

**Image:** A snapshot of file system and a start command.

**Dockerfile**: file that used to create image.

**Alpine:** Compact and small version.

**Docker Compose:** A separate CLI used to start up multiple docker containers at the same time and automates some of the long-winded arguments we were passing to 'docker run'.

#### **docker-compose.yml keyword:**

1. `version`: version of docker compose
2.  `services`: specify type and name of the containers

#### Component of docker file:

1. Specify a base image.
2. Run some commands to install additional programs.
3. Specify a command to run on container startup.

#### **Docker restart policies:**

`"no"`:Never attempt to restart(normal no is a keyword in yml file).

`always` Restart for any reason.

`on-failure`Only restart with error code.

`unless-stopped` Always restart unless we forcibly stop it.

#### Dockerfile keyword:

1. `FROM` select image
2. `RUN` run the commands inside the container
3. `CMD` command to set the start up command fro container
4. `COPY <FROM> <TO>` copy file from local to container
5. `WORKDIR <path>` set working directory

#### Process of build dockefile:

1. Get base image from local or remotely
2. Create a temporary container from the base image and run the **commands from component step 2** on that container and create **new image** (will be cached at somewhere for future use). If the commands order of component step 2 is different, the cache will no longer works.
3. Create a new temporary container from the **previous new image**, the **command from component step 3** will be store start up command. Create the final image.

**Limiataion:** Host on Linux machine only, when they get install on windows or Mac, a linux Virtual Machine required to run docker.

By running command `docker run hello-world`, docker will

1. Check image named `hello-world` locally(look into **image cache**). If it is not existed check with docker hub(Images repo that holded by docker) and pull the image to local machine.
2. With the image, loaded up into memory and create a container and run the container.

**Common commands:**

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

#### **Common flags**: 

`-a` show output from container.

`-i` attach current input into container input.

`-t` beautify output text.

`-it` allow us to provide input to the container.

`-c`  allow set the default command.   

`-p <FROM>:<TO>`  port mapping

`-d` Run container in background and print container ID.



