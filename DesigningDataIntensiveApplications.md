

# Designing Data Intensive Applications

we focus on three concerns that are important in most software systems:

## Reliability

The system should continue to work correctly even in the face of adversity (hardware or software faults, and even human error). 

- The application performs the function that the user expected.
- It can tolerate the user making mistakes or using the software in unexpected ways.
- Its performance is good enough for the required use case, under the expected load and data volume.
- The system prevents any unauthorized access and abuse.

The things that can go wrong are called faults, and systems that anticipate faults and can cope with them are called **fault-tolerant** or **resilient**. 

> **_NOTE:_** A fault is not the same as a failure. A fault is usually defined as one component of the system deviating from its spec, whereas a failure is when the system as a whole stops providing the required service to the user. It is impossible to reduce the probability of a fault to zero; therefore it is usually best to design fault-tolerance mechanisms that prevent faults from causing failures. 

Although we generally prefer tolerating faults over preventing faults, there are cases where prevention is better. This is the case with security matters, for example: if an attacker has compromised a system and gained access to sensitive data, that event cannot be undone.

### Hardware Faults

#### Backup Machine

Normal way is to have backup machine to be able to restore to desired state quickly.

#### Software Fault-Tolenrance

As data volumes and applications's computing demands have increaded, more applications have begun using larger numbers of machines, which proportionally increases the rate of hardware faults. 

Using software fault-tolerance techniques in preference or in addition to hardware redundancy. Such systems also have operational advantages: a single-server system requires planned downtime if you need to reboot the machine, whereas a system that can tolerate machine failure can be patched one node at a time, without downtime of the entire system.

### Software Faults

A systematic error within the system is harder to anticipate, and because they are correlated across nodes, they tend to cause many more system failures than uncorrelated hardware faults.

#### Prevention

- carefully thinking about assumptions and interactions in the system
- thorough testing
- process isolation
- allowing processes to crash and restart
- measuring, monitoring, and analyzing system behavior in production
- If a system is expected to provide some guarantee, it can constantly check itself while it is running and raise an alert if a discrepancy is found

### Human Errors

- Design systems in a way that minimizes opportunities for error. For example, well-designed abstractions, APIs.
- Decouple the places where people make the most mistakes from the places where they can cause failures. In particular, provide fully featured non-production sandbox environments where people can explore and experiment safely, using real data, without affecting real users.
- Test thoroughly at all levels, from unit tests to whole system integration tests and manual tests. Automated testing is widely used, well understood, and especially valuable for covering corner cases that rarely arise in normal operation.
- Allow quick and easy recovery from human errors, to minimize the impact in the case of a failure.
- Set up detailed and clear monitoring, such as performance metrics and error rates.
- Implement good management practices and training.

## Scalability

**Scalability** is the term we use to describe a system’s ability to cope with increased load. 

## Maintainability

Over time, many different people will work on the system (engineering and operations, both maintaining current behavior and adapting the system to new use cases), and they should all be able to work on it productively. 

