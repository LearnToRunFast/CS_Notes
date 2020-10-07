## Docker

**Docker Client:** Issuing commands

**Dokcer Server:** Responsible for creating images, running containers

**Image:** A snapshot of file system and a start command.

**Limiataion:** Host on Linux machine only, when they get install on windows or Mac, a linux Virtual Machine required to run docker.

By running command `docker run hello-world`, docker will

1. Check image named `hello-world` locally(look into **image cache**). If it is not existed check with docker hub(Images repo that holded by docker) and pull the image to local machine.
2. With the image, loaded up into memory and create a container and run the container.

**Common commands:**

1. `docker Run` = `docker create` + `docker start`, create and run the container.
2. **Overwrite the default start command** by specify command after the image name. 

3. `docker ps`  list of running containers, `docker ps --all` all containers were created.
4. `docker system prune` delete all stopped containers and some other stuff.
5. `docker logs ` show the logs.
6. `docker stop` send a **SIGTERM** to container and shut it down.
7. `docker kill` send a **SIGKILL** to container and force it to shut down.
8. `docker exec -it <container id> <command>` pass the command into containner and execute it inside the container. 
   1. `docker exec -it <container id> sh` give back shell.

**Common flags**: 

`-a` show output from container.

`-i` attach current input into container input.

`-t` beautify output text.

`-it` allow us to provide input to the container.



