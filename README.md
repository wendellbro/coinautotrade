# Wallchain - Official C++ implementation of the Wallchain protocol
Wallchain is a blockchain based secure decentralized anonymous private network incentivized by bandwidth mining.
https://www.wallchain.net

# License
The wallchain binary is licensed under the
[GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html), also included
in our repository in the `COPYING` file.

# Build Instruction
We're using docker to uniform the build environment. 
There're two steps to build the project.
 
# Docker build environment

## Windows
- install docker
```
download url ：https://download.docker.com/win/stable/Docker%20for%20Windows%20Installer.exe
```
- start docker container
```
bin/start-docker.bat
```
## Mac OS X
- install docker
```
brew install docker docker-compose docker-machine xhyve docker-machine-driver-xhyve
```
- This driver requires superuser privileges to access the hypervisor. To enable, execute
```
sudo chown root:wheel /usr/local/opt/docker-machine-driver-xhyve/bin/docker-machine-driver-xhyve
sudo chmod u+s /usr/local/opt/docker-machine-driver-xhyve/bin/docker-machine-driver-xhyve
```
- install virtualbox
```
brew cask install virtualbox
```
- create docker vm
```
docker-machine create -d virtualbox --virtualbox-memory "4096" --virtualbox-cpu-count "2" docker-vm
```
- start docker vm
```
docker-machine start docker-vm
```
- stop docker vm
```
docker-machine stop docker-vm
```
- list vms
```
docker-machine ls
```
- start docker container
```
bin/start-docker.sh
```
## Linux
- install docker
```
sudo apt install docker.io
```
or
```
see details https://docs.docker.com/install/linux/docker-ce/ubuntu/#install-docker-ce-1
```
- solve the trouble that docker needs root privileges
```
sudo groupadd docker
sudo usermod -a -G docker wallchain
gnome-session-quit
```
- start docker container
```
bin/start-docker.sh
```
*** Build dht
When it's in the docker, and the root path of the Wallchain is "SOME_PATH". 
Run:
```
cd SOME_PATH
cd dht
./makeDht.sh
```
There will be a long time to download boost_1_57_0.tar.bz2 during the building. 
If having the boost_1_57_0.tar.bz2 locally, putting it in advance in the build path can reduce the building time.
The build path is `SOME_PATH/build_dht`(It might not be created yet).

If some error messages come up during the building, just ignore them.
If seeing the last messge is 
> [100%] Built target AllRouting 
then the building is successful!

If the project has been built once, you can just run make AllXXX to build a sub-project again.
For example: 
To build common, rpc and routing step by step: 
```
make AllCommon
make AllRpc
make AllRouting
```
# Wallchain code style:
1. Wallchain code style is obeyed [Google C++ Style Guide](https://google.github.io/styleguide/cppguide.html).
2. About code comment, there is a template at `dht/docs/code-comment-template.h`.
3. IMPORTANT: 
   Before making a commit, it must always run `bin/cpplintwallchain.sh` to check the sources format.
   If there is a format error, it should be fixed before commiting.
