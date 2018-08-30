FROM java:8

ADD ./jChain.jar jchain.jar

EXPOSE 7499 
EXPOSE 7498

CMD java -jar jchain.jar -rpc
