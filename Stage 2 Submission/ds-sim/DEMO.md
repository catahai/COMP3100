
# Demo for Stage 2 Assignment

`sudo apt update`
`sudo apt upgrade`
Requires git

Installing git:
`sudo apt-get install git`

## Clone this github repo COMP3100
installing the ds-sim files (if necessary. It also requires git commands)
cd COMP3100 
git clone https://github.com/distsys-MQ/ds-sim/git
new directory will be COMP3100/ds-sim

## Ensure that the files are executable, the `ds-client` and `ds-server`


`chmod -x ds-client`
`chmod -x ds-server`



## How to run the simulation

1. Head to the pre-compiled folder under:

`$cd COMP3100/ds-sim/src/pre-compiled

if using M1 Mac, the pre-compiled folder will need to be ran under the aarch64 folder due to ARM machines running it differently.

`$ cd COMP3100/ds-sim/src/pre-compiled/aarch64

2. Run server `$ ds-server -c ds-sample-config.xml -n -v brief`
-n: creates a new line character ('\n') at the end of each messages made by server and client
-v: (verbose) meaning (verbose mode0, which will output some details about the full runtime of the server
brief: meaning it will show us basic outputs during the full runtime of the server

2. Compile the java program `Client.java`

First ensure that java is on the ubuntu machine through java -version
if not, the follow command is needed to install java

`sudo apt install default-jre default-jdk`

Now we can compile the program using (ensuring that the java program is in the right folder, pre-compiled or aarch64
javac Client.java
which will generate a 
or run it through
the shell script provided by the examinor

## If simulation is not working 
 - ensure all packages are installed properly
 - restart OS after applying `sudo apt update` or `sudo apt upgrade`

## Two terminals should have
running the server through ip: 127.0.0.1 and port: 50000 
`$ ds-server -c ds-config01.xml -n -v brief`

`$ java Client.java

## Otherwise to run the S2TestScript we can write it under through python3
./s2_test.py "java Client" -n -r results/ref_results.json

## Installing Python3 

`$sudo apt install python3 python3-tk python3-pip`



