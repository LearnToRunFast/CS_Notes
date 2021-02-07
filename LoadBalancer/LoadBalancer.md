[toc]

# Load Balancer

Load balancer is located on layer 4 or layer 7.

## Type of Load Balancer

Hardware base

Software base

## Routing Algorithm

### Round Bobin

Requests are sequentially sent to available servers and back to first server.

#### Cons

The servers need to have same specs, otherwise not fully utilised the resources of high spec server.

### Weighted Round Robin

Requests are sent to highest weighted server first and sent to second weighted server when the first server is in full load.

### Least Connections

Requests are sent to the servers with fewest active connections

### Least Response Time

Requests are sent to the servers with fewest active connections aand the lowest average response time(by multiplication).

### IP Hashing

Ip of the client is used to find out a server to send the request to.