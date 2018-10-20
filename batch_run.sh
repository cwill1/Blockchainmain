#!/bin/bash
javac Peer.java
javac Message.java
javac Client.java

java -cp . Peer start process 0
java -cp . Peer start process 1
java -cp . Peer start process 2

echo hello world





