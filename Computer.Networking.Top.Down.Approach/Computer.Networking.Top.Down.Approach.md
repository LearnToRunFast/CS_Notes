# Computer network

## What is Internet

Smart devices are refer as **hosts** or **end systems**.End systems are connected together by a network of **communication links** and **packet switches**. 

Different links can transmit data at different rates, with the **transmission rate** of a link measured in bits/second.

When one end system has data to send to another end system, the sending end system segments the data and adds header bytes to each segment. The resulting packages of information, known as **packets**.

Packet switches come in many shapes and flavors, but the two most prominent types in today’s Internet are **routers** and **link-layer switches**.Link-layer switches are typically used in access networks, while routers are typically used in the network core.

End systems access the Internet through **Internet Service Providers (ISPs)**.

End systems, packet switches, and other pieces of the Internet run **protocols** that control the sending and receiving of information within the Internet. The **Transmission Control Protocol (TCP)** and the **Internet Protocol (IP)** are two of the most important protocols in the Internet. 

- The IP protocol specifies the format of the packets that are sent and received among routers and end systems. 
- The Internet’s principal protocols are collectively known as **TCP/IP**.

*A* **protocol** defines the format and the order of messages exchanged between two or more communicating entities, as well as the actions taken on the transmission and/or receipt of a message or other event.

Two most prevalent types of broadband residential access are **digital subscriber line (DSL)** and cable. 

As shown in Figure 1.5, each customer’s DSL modem uses the existing telephone line to exchange data with a digital subscriber line access multiplexer (DSLAM) located in the telco’s local central office (CO). The home’s DSL modem takes digital data and translates it to high frequency tones for transmission over telephone wires to the CO; the analog signals from many such houses are translated back into digital format at the DSLAM.

![image-20210208195300749](Asserts/Computer.Networking.Top.Down.Approach/image-20210208195300749.png)

The residential telephone line carries both data and traditional telephone signals simultaneously, which are encoded at different frequencies:

- A high-speed downstream channel, in the 50 kHz to 1 MHz band
- A medium-speed upstream channel, in the 4 kHz to 50 kHz band
- An ordinary two-way telephone channel, in the 0 to 4 kHz band

On the customer side, a splitter separates the data and telephone signals arriving to the home and forwards the data signal to the DSL modem. 

On the telco side, in the CO, the DSLAM separates the data and phone signals and sends the data into the Internet. Hundreds or even thousands of households connect to a single DSLAM.

The DSL standards define multiple transmission rates, including 12 Mbps down- stream and 1.8 Mbps upstream, and 55 Mbps downstream and 15 Mbps upstream.The maximum rate is also limited by the distance between the home and the CO, the gauge of the twisted-pair line and the degree of electrical interference.If the residence is not located within 5 to 10 miles of the CO, the residence must resort to an alternative form of Internet access.

While DSL makes use of the telco’s existing local telephone infrastructure, **cable Internet access** makes use of the cable television company’s existing cable television infrastructure. A residence obtains cable Internet access from the same company that provides its cable television. As illustrated in Figure 1.6, fiber optics connect the cable head end to neighborhood-level junctions, from which traditional coaxial cable is then used to reach individual houses and apartments. Each neighborhood junction typically supports 500 to 5,000 homes. Because both fiber and coaxial cable are employed in this system, it is often referred to as hybrid fiber coax (HFC).

![image-20210208195224422](Asserts/Computer.Networking.Top.Down.Approach/image-20210208195224422.png)

Cable Internet access requires special modems, called cable modems. As with a DSL modem, the cable modem is typically an external device and connects to the home PC through an Ethernet port. At the cable head end, the cable modem termination system (CMTS) serves a similar function as the DSL network’s DSLAM—turning the analog signal sent from the cable modems in many downstream homes back into digital format. Cable modems divide the HFC network into two channels, a downstream and an upstream channel. As with DSL, access is typically asymmetric, with the downstream channel typically allocated a higher transmission rate than the upstream channel. The DOCSIS 2.0 standard defines downstream rates up to 42.8 Mbps and upstream rates of up to 30.7 Mbps. As in the case of DSL networks, the maximum achievable rate may not be realized due to lower contracted data rates or media impairments.

A distributed multiple access protocol is needed to coordinate transmissions and avoid collisions.

**Fiber to the home (FTTH)** provide an optical fiber path from the CO directly to the home. 

There are several competing technologies for optical distribution from the CO to the homes. The simplest optical distribution network is called direct fiber, with one fiber leaving the CO for each home. More commonly, each fiber leaving the central office is actually shared by many homes; it is not until the fiber gets relatively close to the homes that it is split into individual customer-specific fibers. There are two competing optical-distribution network architectures that perform this splitting: active optical networks (AONs) and passive optical networks (PONs). AON is essentially switched Ethernet, which is discussed in Chapter 6.

Figure 1.7 shows FTTH using the PON distribution architecture. Each home has an optical network terminator (ONT), which is connected by dedicated optical fiber to a neighborhood splitter. The splitter combines a number of homes (typically less than 100) onto a single, shared optical fiber, which connects to an optical line terminator (OLT) in the telco’s CO. The OLT, providing conversion between optical and electrical signals, connects to the Internet via a telco router. In the home, users connect a home router (typically a wireless router) to the ONT and access the Internet via this home router. 

In the PON architecture, all packets sent from OLT to the splitter are replicated at the splitter (similar to a cable head end).

![image-20210208200220707](Asserts/Computer.Networking.Top.Down.Approach/image-20210208200220707.png)

In locations where DSL, cable, and FTTH are not available (e.g., in some rural settings), a satellite link can be used to connect a residence to the Internet at speeds of more than 1 Mbps. Dial-up access over traditional phone lines is based on the same model as DSL—a home modem connects over a phone line to a modem in the ISP. Compared with DSL and other broadband access networks, dial-up access is excruciatingly slow at 56 kbps.

For each transmitter-receiver pair, the bit is sent by propagating electromagnetic waves or optical pulses across a **physical medium**. Physical media fall into two categories: **guided media** and **unguided media**.

- guided media, the waves are guided along a solid medium, such as a fiber optic cable, a twisted-pair copper wire, or a coaxial cable.
- unguided media, the waves propagate in the atmosphere and in outer space, such as in a wireless LAN or a digital satellite channel.

### The Network Core

#### Packet Switching

To send a message from a source end system to a destination end system, the source breaks long messages into smaller chunks of data known as **packets**. Between source and destination, each packet travels through communication links and **packet switches** (for which there are two predominant types, **routers** and **link-layer switches**). Packets are transmitted over each communication link at a rate equal to the *full* transmission rate of the link. So, if a source end system or a packet switch is sending a packet of *L* bits over a link with transmission rate *R* bits/sec, then the time T to transmit the packet is
$$
T =L / R
$$

##### Store-and-Forward Transmission

Most packet switches use **store-and-forward transmission** at the inputs to the links. Store-and-forward transmission means that the packet switch must receive the entire packet before it can begin to transmit the first bit of the packet onto the outbound link. 

![image-20210219124149074](Asserts/Computer.Networking.Top.Down.Approach/image-20210219124149074.png)

At time *L*/*R* seconds, since the router has just received the entire packet, it can begin to transmit the packet onto the outbound link towards the destination; at time $2\times L/R$, the router has transmitted the entire packet, and the entire packet has been received by the destination. Thus, the total delay is $2\times L/R$. If the switch instead forwarded bits as soon as they arrive.

Let’s now consider the general case of sending one packet from source to destination over a path consisting of *N* links each of rate *R* (thus, there are *N*-1 routers between source and destination). Applying the same logic as above, we see that the end-to-end delay is:
$$
d_{end\_to\_end} = N\frac{L}{R}
$$

##### Queuing Delays and Packet Loss

The packet switch has an **output buffer** (also called an **output queue**), which stores packets that the router is about to send into that link. The packages need to wait inside the queue if the forward link is busy. We call this waiting time as **queuing delays**.

Since the amount of buffer space is finite, an arriving packets faced the buffer is full, so **packet loss** will occur.

##### Forwarding Tables and Routing Protocols

In the Internet, every end system has an address called an IP address.When a source end system wants to send a packet to a destination end system, the source includes the destination’s IP address in the packet’s header. This address has a hierarchical structure. Each router has a **forwarding table** that maps destination addresses (or portions of the destination addresses) to that router’s outbound links. When a packet arrives at a router, the router examines the address and searches its forwarding table, using this destination address, to find the appropriate outbound link. The router then directs the packet to this outbound link. The Internet has a number of special **routing protocols** that are used to automatically set the forwarding tables. 

#### Circuit Switching

There are two fundamental approaches to moving data through a network of links and switches: **circuit switching** and **packet switching**.

In circuit-switched networks, the resources needed along a path to provide for communication between the end systems are *reserved* for the duration of the communication session between the end systems. Traditional telephone networks are examples of circuit-switched networks.

##### Multiplexing in Circuit-Switched Networks

A circuit in a link is implemented with either **frequency-division multiplexing (FDM)** or **time-division multiplexing (TDM)**.

With FDM, the frequency spectrum of a link is divided up among the connections established across the link. 

For a TDM link, time is divided into frames of fixed duration, and each frame is divided into a fixed number of time slots. When the network establishes a connection across a link, the network dedicates one time slot in every frame to this connection.

![image-20210219130305353](Asserts/Computer.Networking.Top.Down.Approach/image-20210219130305353.png)

##### Packet Switching Versus Circuit Switching

**Packet Switching** will utilise the link fully when some of users are not using the link. **Circuit Switching** may has benefit not affecting other users when some of the users heavily use the link.

#### Network of Networks

The access ISPs interconnect with a *single global transit ISP*. The global transit ISPs  interconnect among themselves. We said that access ISPs and global transit ISPs are in different tier to form multi-tier hierarchy.To build a network that more closely resembles today’s Internet, we must add points of presence (PoPs), multi-homing, peering, and Internet exchange points (IXPs) to the hierarchical Network Structure.

A **PoP** is simply a group of one or more routers (at the same location) in the provider’s network where customer ISPs can connect into the provider ISP. It exists in all levels of the hierarchy, except for the bottom (access ISP) level. Any ISP (except for tier-1 ISPs) may choose to **multi-home**, that is, to connect to two or more provider ISPs.A pair of nearby ISPs at the same level of the hierarchy can **peer**: they can directly connect their networks together so that all the traffic between them passes over the direct connection rather than through upstream intermediaries. An **Internet Exchange Point (IXP)**, which is a meeting point where multiple ISPs can peer together.

On top of the current Network Structure, we can add **content-provider networks** to form the final piece.

![image-20210219134108826](Asserts/Computer.Networking.Top.Down.Approach/image-20210219134108826.png)

### Delay, Loss, and Throughput in Packet-Switched Networks

#### Overview of Delay in Packet-Switched Networks

The most important of these delays are the **nodal processing delay**, **queuing delay**, **transmission delay**, and **propagation delay**; together, these delays accumulate to give a **total nodal delay**. 

![image-20210219232948304](Asserts/Computer.Networking.Top.Down.Approach/image-20210219232948304.png)

**Processing Delay**

The time required to examine the packet’s header and determine where to direct the packet is part of the **processing delay**. The processing delay can also include other factors, such as the time needed to check for bit-level errors in the packet that occurred in transmitting the packet’s bits from the upstream node to router A.

**Queuing Delay**

At the queue, the packet experiences a queuing delay as it waits to be transmitted onto the link. The length of the queuing delay of a specific packet will depend on the number of earlier-arriving packets that are queued and waiting for transmission onto the link. If the queue is empty and no other packet is currently being transmitted, then our packet’s queuing delay will be zero.

**Transmission Delay**

Assuming that packets are transmitted in a first-come-first-served manner, as is common in packet-switched networks, our packet can be transmitted only after all the packets that have arrived before it have been transmitted. 

Denote the length of the packet by L bits, and denote the transmission rate of the link from router A to router B by R bits/sec. For example, for a 10 Mbps Ethernet link, the rate is R = 10 Mbps; for a 100 Mbps Ethernet link, the rate is R = 100 Mbps. The transmission delay is L/R.

**Propagation Delay**

Once a bit is pushed into the link, it needs to propagate to router B. The time required to propagate from the beginning of the link to router B is the **propagation delay.** The bit propagates at the propagation speed of the link. The propagation speed depends on the physical medium of the link (that is, fiber optics, twisted-pair copper wire, and so on) and is in the range of
$$
2\times 10^8 m/s \ to \ \ 3\times 10^8 m/s
$$
which is equal to, or a little less than, the speed of light. **The propagation delay is the distance between two routers divided by the propagation speed.** That is, the propagation delay is d/s, where d is the distance between router A and router B and s is the propagation speed of the link. Once the last bit of the packet propagates to node B, it and all the preceding bits of the packet are stored in router B. The whole process then continues with router B now performing the forwarding.

**Comparing Transmission and Propagation Delay**

The transmission delay is the amount of time required for the router to push out the packet; it is a function of the packet’s length and the transmission rate of the link, but has nothing to do with the distance between the two routers. The propagation delay, on the other hand, is the time it takes a bit to propagate from one router to the next; it is a function of the distance between the two routers, but has nothing to do with the packet’s length or the transmission rate of the link.

If we let $d_{proc}$,  $d_{queue}$,  $d_{trans}$,  and $d_{prop}$ denote the processing, queuing, transmission, and propagation delays, then the total nodal delay is given by
$$
d_{nodal} = d_{proc} +d_{queue} + d_{trans} + d_{prop}
$$

#### Queuing Delay and Packet Loss

##### Queuing Delay

The most complicated and interesting component of nodal delay is the queuing delay, $d_{queue}$.The queuing delay can vary from packet to packet. For example, if 10 packets arrive at an empty queue at the same time, the first packet transmitted will suffer no queuing delay, while the last packet transmitted will suffer a relatively large queuing delay. Let `a` denote the average rate at which packets arrive at the queue (`a` is in units of packets/sec). Recall that `R` is the transmission rate; that is, it is the rate (in bits/sec) at which bits are pushed out of the queue. Also suppose, for simplicity, that all packets consist of `L` bits. Then the average rate at which bits arrive at the queue is `La bits/sec`. Finally, assume that the queue is very big, so that it can hold essentially an infinite number of bits. The ratio `La/R`, called the **traffic intensity**, often plays an important role in estimating the extent of the queuing delay. If La/R > 1, then the average rate at which bits arrive at the queue exceeds the rate at which the bits can be transmitted from the queue. In this unfortunate situation, the queue will tend to increase without bound and the queuing delay will approach infinity! Therefore, one of the golden rules in traffic engineering is: **Design your system so that the traffic intensity is no greater than 1**.

##### Packet Loss

In reality a queue preceding a link has finite capacity. A packet can arrive to find a full queue. With no place to store such a packet, a router will drop that packet.The fraction of lost packets increases as the traffic intensity increases.

#### End to End Delay

Nodal delay is only the delay at a single router. Let’s now consider the total delay from source to destination. To get a handle on this concept, suppose there are *N* - 1 routers between the source host and the destination host. Let’s also suppose for the moment that the network is uncongested (so that queuing delays are negligible), the processing delay at each router and at the source host is $d_{proc}$, the transmission rate out of each router and out of the source host is *R* bits/sec, and the propagation on each link is $d_{prop}$. The nodal delays accumulate and give an end-to-end delay:
$$
d_{end\_to\_end} = N(d_{proc} + d_{trans} + d_{prop})
$$
where $d_{trans} = L/R$, where *L* is the packet size. 

#### Throughput in Computer Networks

To define throughput, consider transferring a large file from Host A to Host B across a computer network. This transfer might be, for example, a large video clip from one peer to another in a P2P file sharing system. The **instantaneous throughput** at any instant of time is the rate (in bits/ sec) at which Host B is receiving the file. If the file consists of *F* bits and the transfer takes *T* seconds for Host B to receive all *F* bits, then the **average throughput** of the file transfer is *F/T* bits/sec.

Figure 1.19(a) shows two end systems, a server and a client, connected by two communication links and a router.Let $R_s$ denote the rate of the link between the server and the router; and $R_c$ denote the rate of the link between the router and the client. The throughput is min{*R**c*, *R**s*}, that is, it is the transmission rate of the **bottleneck link**. The throughput by transfer a large file of Fbits from server to client is $F/min(R_s, R_c)$

![image-20210220094405468](Asserts/Computer.Networking.Top.Down.Approach/image-20210220094405468.png)

### Protocol Layers and Their Service Models

#### Layered Architecture

A layered architecture allows us to discuss a well-defined, specific part of a large and complex system. This simplification itself is of considerable value by providing modularity, making it much easier to change the implementation of the service provided by the layer. For large and complex systems that are constantly being updated, the ability to change the implementation of a service without affecting other components of the system is an important advantage of layering.

##### Protocol Layering

The network hardware and software that implement the protocols in **layers**. Each protocol belongs to one of the layers. A protocol layer can be implemented in software, in hardware, or in a combination of the two. Application-layer protocols—such as HTTP and SMTP—are almost always implemented in software in the end systems; so are transport-layer protocols. Because the physical layer and data link layers are responsible for handling communication over a specific link, they are typically implemented in a network interface card (for example, Ethernet or WiFi interface cards) associated with a given link.The network layer is often a mixed implementation of hardware and software. 

**Some drawbacks**

- One potential drawback of layering is that one layer may duplicate lower-layer functionality. For example, many protocol stacks provide error recovery on both a per-link basis and an end-to-end basis.
- functionality at one layer may need information (for example, a timestamp value) that is present only in another layer; this violates the goal of separation of layers.

When taken together, the protocols of the various layers are called the **protocol stack**. The Internet protocol stack consists of five layers: the physical, link, network, transport, and application layers, as shown in Figure 1.23(a).

![image-20210220100458965](Asserts/Computer.Networking.Top.Down.Approach/image-20210220100458965.png)

**Application Layer**

The application layer is where network applications and their application-layer protocols reside. The Internet’s application layer includes many protocols, such as:

- the HTTP protocol (which provides for Web document request and transfer)
- SMTP (which provides for the transfer of e-mail messages)
- the domain name system (DNS) which translate the domain name into IP
- FTP (which provides for the transfer of files between two end systems)

An application-layer protocol is distributed over multiple end systems, with the application in one end system using the protocol to exchange packets of information with the application in another end system. We’ll refer to this packet of information at the application layer as a **message**.

**Transport Layer**

The Internet’s transport layer transports application-layer messages between application endpoints. In the Internet there are two transport protocols, TCP and UDP, either of which can transport application-layer messages. We’ll refer to a transport-layer packet as a **segment**.

- TCP provides a connection-oriented service to its applications. This service includes guaranteed delivery of application-layer messages to the destination and flow control (that is, sender/receiver speed matching). TCP also breaks long messages into shorter segments and provides a congestion-control mechanism, so that a source throttles its transmission rate when the network is congested. 

- The UDP protocol provides a connectionless service to its applications. This is a no-frills service that provides no reliability, no flow control, and no congestion control.

**Network Layer**

The Internet’s network layer is responsible for moving network-layer packets known as **datagrams** from one host to another. The Internet transport-layer protocol (TCP or UDP) in a source host passes a transport-layer segment and a destination address to the network layer. The network layer then provides the service of delivering the segment to the transport layer in the destination host.

The Internet’s network layer includes: 

- **IP protocol**, which defines the fields in the datagram as well as how the end systems and routers act on these fields. There is only one IP protocol, and all Internet components that have a network layer must run the IP protocol. 
- **Routing protocols** that determine the routes that datagrams take between sources and destinations. The Internet has many routing protocols. 

Although the network layer contains both the IP protocol and numerous routing protocols, it is often simply referred to as the IP layer, reflecting the fact that IP is the glue that binds the Internet together.

**Link Layer**

The Internet’s network layer routes a datagram through a series of routers between the source and destination. To move a packet from one node (host or router) to the next node in the route, the network layer relies on the services of the link layer. In particular, at each node, the network layer passes the datagram down to the link layer, which delivers the datagram to the next node along the route. At this next node, the link layer passes the datagram up to the network layer.

The services provided by the link layer depend on the specific link-layer protocol that is employed over the link. For example, some link-layer protocols provide reliable delivery, from transmitting node, over one link, to receiving node.  Examples of link layer protocols include Ethernet, WiFi, and the cable access network’s DOCSIS protocol. As datagrams typically need to traverse several links to travel from source to destination, a datagram may be handled by different link-layer protocols at different links along its route. For example, a datagram may be handled by Ethernet on one link and by Point-to-Point Protocol (PPP) on the next link. The network layer will receive a different service from each of the different link-layer protocols. We’ll refer to the link layer packets as **frames**.

> _**Note**_: The reliable delivery service is different from the reliable delivery service of TCP, which provides reliable delivery from one end system to another.

**Physical Layer**

While the job of the link layer is to move entire frames from one network element to an adjacent network element, the job of the physical layer is to move the *individual bits* within the frame from one node to the next. The protocols in this layer are again link dependent and further depend on the actual transmission medium of the link (for example, twisted-pair copper wire, single-mode fiber optics). For example, Ethernet has many physical-layer protocols: one for twisted-pair copper wire, another for coaxial cable, another for fiber, and so on. In each case, a bit is moved across the link in a different way.

**The OSI Model**

The seven layers of the OSI reference model, shown in Figure 1.23(b), are: application layer, presentation layer, session layer, transport layer, network layer, data link layer, and physical layer. The functionality of five of these layers is roughly the same as their similarly named Internet counterparts. Thus, let’s consider the two additional layers present in the OSI reference model—the presentation layer and the session layer. 

**Presentation Layer**

The role of the presentation layer is to provide services that allow communicating applications to interpret the meaning of data exchanged. These services include:

- data compression
- data encryption
- data description (which frees the applications from having to worry about the internal format in which data are represented/store —formats that may differ from one computer to another). 

**Session Layer**

The session layer provides for delimiting and synchronization of data exchange, including the means to build a checkpointing and recovery scheme.

#### Encapsulation

Figure 1.24 shows the physical path that data takes down a sending end system’s protocol stack, up and down the protocol stacks of an intervening link-layer switch and router, and then up the protocol stack at the receiving end system.The link-layer switches implement layers 1 and 2; routers implement layers 1 through 3. This means, for example, that Internet routers are capable of implementing the IP protocol (a layer 3 protocol), while link-layer switches are not. So link-layer switches do not recognize IP addresses, they are capable of recognizing layer 2 addresses, such as Ethernet addresses.

![image-20210220103428969](Asserts/Computer.Networking.Top.Down.Approach/image-20210220103428969.png)

Figure 1.24 also illustrates the important concept of **encapsulation**. At the send- ing host, an **application-layer message** (M in Figure 1.24) is passed to the transport layer. In the simplest case, the transport layer takes the message and appends addi- tional information (so-called transport-layer header information, H*t* in Figure 1.24) that will be used by the receiver-side transport layer. The application-layer message and the transport-layer header information together constitute the **transport-layer segment**. The transport-layer segment thus encapsulates the application-layer mes- sage. The added information might include information allowing the receiver-side transport layer to deliver the message up to the appropriate application, and error- detection bits that allow the receiver to determine whether bits in the message have been changed in route. The transport layer then passes the segment to the network layer, which adds network-layer header information (H*n* in Figure 1.24) such as source and destination end system addresses, creating a **network-layer datagram**. The datagram is then passed to the link layer, which (of course!) will add its own link-layer header information and create a **link-layer frame**. Thus, we see that at each layer, a packet has two types of fields: header fields and a **payload field**. The payload is typically a packet from the layer above.

The process of encapsulation can be more complex than that described above. For example, a large message may be divided into multiple transport-layer segments (which might themselves each be divided into multiple network-layer datagrams). At the receiving end, such a segment must then be reconstructed from its constituent datagrams.

#### Networks Under Attack

##### Malware

The host device can infected by **malware** which are bad programs that designed to cause damage or deal information to the host device. 

The compromised host may also be enrolled in a network of thousands of similarly compromised devices, collectively known as a **botnet**, which the bad guys control and leverage for spam e-mail distribution or distributed denial-of-service attacks (DDos) against targeted hosts. 

Much of the malware out there today is **self-replicating**: once it infects one host, from that host it seeks entry into other hosts over the Internet, and from the newly infected hosts, it seeks entry into yet more hosts. 

Malware can spread in the form of a virus or a worm. 

- **Viruses** are malware that require some form of user interaction to infect the user’s device. The classic example is an e-mail attachment containing malicious executable code. If a user receives and opens such an attachment, the user inadvertently runs the malware on the device. Typically, such e-mail viruses are self-replicating: once executed, the virus may send an identical message with an identical malicious attachment to, for example, every recipient in the user’s address book.
- **Worms** are malware that can enter a device without any explicit user interaction. For example, a user may be running a vulnerable network application to which an attacker can send malware.In some cases, without any user intervention, the application may accept the malware from the Internet and run it, creating a worm. The worm in the newly infected device then scans the Internet, searching for other hosts running the same vulnerable network application. When it finds other vulnerable hosts, it sends a copy of itself to those hosts.

Another broad class of security threats are known as **denial-of-service (DoS) attacks**. A DoS attack renders a network, host, or other piece of infrastructure unusable by legitimate users. Web servers, e-mail servers, DNS servers, and institutional networks can all be subject to DoS attacks. Most Internet DoS attacks fall into one of three categories:

- **Vulnerability attack**: This involves sending a few well-crafted messages to a vulnerable application or operating system running on a targeted host. If the right sequence of packets is sent to a vulnerable application or operating system, the service can stop or, worse, the host can crash.
- **Bandwidth flooding**: The attacker sends a deluge of packets to the targeted host—so many packets that the target’s access link becomes clogged, preventing legitimate packets from reaching the server.
- **Connection flooding**: The attacker establishes a large number of half-open or fully open TCP connections at the target host. The host can become so bogged down with these bogus connections that it stops accepting legitimate connections.

Let’s now explore the bandwidth-flooding attack in more detail. In a **distributed DoS (DDoS)** attack, illustrated in Figure 1.25, the attacker controls multiple sources and has each source blast traffic at the target.

![image-20210220110054088](Asserts/Computer.Networking.Top.Down.Approach/image-20210220110054088.png)

##### Sniffing Packets

Many users today access the Internet via wireless devices, such as WiFi-connected laptops or handheld devices with cellular Internet connections. Placing a passive receiver in the vicinity of the wireless transmitter, that receiver can obtain a copy of every packet that is transmitted! These packets can contain all kinds of sensitive information, including passwords, social security numbers, trade secrets, and private personal messages. A passive receiver that records a copy of every packet that flies by is called a **packet sniffer**.

Sniffers can be deployed in wired environments as well. In wired broadcast environments, as in many Ethernet LANs, a packet sniffer can obtain copies of broadcast packets sent over the LAN. Furthermore, a bad guy who gains access to an institution’s access router or access link to the Internet may be able to plant a sniffer that makes a copy of every packet going to/from the organization. Sniffed packets can then be analyzed offline for sensitive information.

Because packet sniffers are passive—that is, they do not inject packets into the channel—they are difficult to detect. 

##### Masquerade

The ability to inject packets into the Internet with a false source address is known as **IP spoofing**, and is but one of many ways in which one user can masquerade as another user.

To solve this problem, we will need *end-point authentication,* that is, a mechanism that will allow us to determine with certainty if a message originates from where we think it does. 

## Application Layer

