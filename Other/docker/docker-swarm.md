docker run swarm --help

create
list
manage
join

0a02aa4de77026394875f553a5716047

swarm manage -H 0.0.0.0:3376 --tlsverify --tlscacert=/certs/ca.pem --tlscert=/certs/server.pem --tlskey=/certs/server-key.pem token://0a02aa4de77026394875f553a5716047


2888

docker run -d swarm join --addr=192.168.99.100:2376 token://0a02aa4de77026394875f553a5716047

192.168.99.100


docker run -d swarm join --addr=192.168.99.101:2376 token://0a02aa4de77026394875f553a5716047



