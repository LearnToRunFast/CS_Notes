# Virtualizaiton of CPU

## Key CPU virtualization terms:

- The CPU should support at least two modes of execution: a restricted **user mode** and a privileged (non-restricted) kernel mode.
  - **User mode:** The applications do not have full access to hardware resources.
  - **Kernel mode:** The OS has access to the full resources of the machine.
- Typical user applications run in user mode, and use a system call
  to trap into the kernel to request operating system services.
- The trap instruction saves register state carefully, changes the hardware status to kernel mode, and jumps into the OS to a pre-specified destination: the **trap table**.
  - **Trap table:** A *trap table* is what is conventionally used by the *system call handler* to invoke the requested operating service routine. It protected by the kernel, so does not enable execution at an arbitrary address. It *maps* a requested service (typically a small number) to a function that provides that service.
- When the OS finishes servicing a system call, it returns to the user program via another special **return-from-trap** instruction, which reduces privilege and returns control to the instruction after the trap that jumped into the OS.
- The trap tables must be set up by the OS at boot time, and make sure that they cannot be readily modified by user programs. All of this is part of the limited direct execution protocol which runs programs efficiently but without loss of OS control.
- Once a program is running, the OS must use hardware mechanisms to ensure the user program does not run forever, namely the **timer interrupt**. This approach is a **non-cooperative** approach to CPU scheduling.
  - **Cooperative:** Program volunteering give up CPU so that the OS can devide to run some other task.
  - **Non-cooperative:** A timer device can be programmed to raise an interrupt periodically; when the interrupt is raised, the currently running process is halted, and a pre-configured interrupt handler in the OS runs. The OS has regained control of the CPU.
- **context switch:**Sometimes the OS, during a timer interrupt or system call, might wish to switch from running the current process to a different one, a low-level technique known as a **context switch** .

## Work flow for limited direct exectuion

1. The OS boots by initializing **trap table** and the hardware will remember addresses of syscall handler and timer handler.
2. The OS start interrupt timer and handware will start timer interrupt CPU in X ms.
3. During the running of process A, timer interrrupt happened. It saves register values of A to it's kernal stack and move to kernel mode, jump to trap handler.
4. The OS will handle the trap by calling switch(). It will save register values of A to process structure of A, restore correpoding values from process structure of B and switch to kernel stack of B. Finally **return-from-trap** into B.
5. The hardware will restore register valus of B from kernel stack of B and move to user mode and jump to B's PC then process B will start running.

## Scheduling

**Turnaround time**:The turnaround time of a job is defined as the time at which the job completes minus the time at which the job arrived in the system.

**Response time:** The time from when the job arrives in a system to the first time it is scheduled.

1. **First In, First Out(FIFO)**:Early arrive process will get executed first.
   - It suffers from **convoy effect**, where a number of relatively-short potential consumers of a resource get queued behind a heavy weight resource comsumer.
2. **Shortest Job First(SJF, non-preemptive)**: Shortest process get executed first.But this algorithm still does not solve the problem when heavy process run first, as it is **non-preemptive** the short process which arrive late will still suffer from **convoy effect**.
3. **Shortest Time-to-Completion First (STCF, preemptive)**:Preemptive version of **SJF**. The **STCF** scheduler determines which of the remaining jobs (including the new job) has the least time left, and schedules that one.
4. **Round Robin:**Instead of running jobs to completion, **RR** runs a job for a time slice (sometimes called a scheduling quantum) and then switches to the next job in the run queue. It repeatedly does so until the jobs are finished. RR is sometimes called time-slicing. 
   - Note that the length of a time slice must be a multiple of the timer-interrupt period; thus if the timer interrupts every 10 milliseconds, the time slice could be 10, 20, or any other multiple of 10 ms.

**SJF** or **STCS** has less turnaround time but suffer from bad response time compare to **RR**. **RR** has a great response time but has worst turnaround time. Either good at turnaround time or response time, but not both. Such **trade-off** is common in systems.

5. **Multi-Level Feefback Queue(MLFQ)**
   - Rule 1: If Priority(A) > Priority(B), A runs (B doesn’t).
   - Rule 2: If Priority(A) = Priority(B), A & B run in round-robin fash-
     ion using the time slice (quantum length) of the given queue.
   - Rule 3: When a job enters the system, it is placed at the highest
     priority (the topmost queue).
   - Rule 4: Once a job uses up its time allotment at a given level (re-
     gardless of how many times it has given up the CPU), its priority is
     reduced (i.e., it moves down one queue).
   - Rule 5: After some time period S, move all the jobs in the system
     to the topmost queue.

### **Proportional-share scheduler:(fair-share scheduler)**

- **Lottery scheduling**

  - programs have tickets, scheduler will draw the ticket and decide which program should run
  - **Ticket Mechanisms**
    - **Ticket currency**
      - Programs can have currency to their sub-jobs, system can converts currency to global ticket
    - **Ticket transfer**
      - Tickets can be transfer to other program. Eg, In client/server setting, client can pass tickets to server after query and thus try to maximise the performace of the server while handling the client's request. And the server will transfers the ticket back to the client after finished.
    - **Ticket inflation**
      - Only applid for trust processes, a process can temporarily raise or lower the number of tickets it owns.

- **Stride scheduling**

  - Processes get a stride value which is inverse propotional to their tickets.All processes will have a global value of 0,the process who has lower global value runs and increase it's global value by it's stride

- **The Linux Completely Fair Scheduler(CFS)**

  - Highly efficient and scalable.

    - CFS aims to spend very little time making scheduling decisions, through both its inherent design and its clever use of data structures well-suited to the task.
    - It fairly divide a CPU evenly among all competing processes. It does so through a simple counting-based technique known as **virtual runtime** (**vruntime**).As each process runs, it accumulates vruntime.CFS will pick the process with the lowest vruntime to run next.
    - **Sched latency**(usually 48ms), CFS uses this value to determine how long one process should run before considering a switch. The time slice will be **shce latency** / n where n is number of processes.
    - To prevent too small time slice, **min granularity** was introduced, which is usually set to a value like 6 ms.CFS will never set the time slice of a process to less than this value, ensuring that not too much time is spent in scheduling overhead.

  - **Weighting**

    - CFS also enables controls over process priority, enabling users or admin- istrators to give some processes a higher share of the CPU through UNIX mechanism known as **nice** level of a process. The nice parameter is from -20 to +19 with a deafult 0.Positive nice values imply lower priority and negative values imply higher priority.It follows below formula below
      $$
      time\_slice_k = \frac{weight_k}{\sum_{i=0}^{n - 1}weight_i}\times sched\_latency
      $$
      The **vruntime** formula
      $$
      vruntime_i = vruntime_i + \frac{weight_0}{weight_i}\times runtime_i
      $$

  - **Red-Black Tree**: By keeping processes in a Red-Black tree to find the next job to run as quickly as possible.

    - CFS does not keep all process in this structure; rather, only running (or runnable) processes are kept therein.If a process goes to sleep, it will be removed from the tree and kept track of elsewhere.
    - Processes are ordered in the tree by **vruntime**, and most operations (such as insertion and deletion) are logarithmic in time, i.e., O(log n)

  - **Dealing with I/O and sleeping processes**: The process may monopolize the CPU for the duration of it's sleep time while it catches up, effectively starving A.

    - CFS handles this case by altering the vruntime of a job when it wakes up. Specifically, CFS sets the **vruntime** of that job to the minimum value found in the tree. In this way, CFS avoids starvation, but not without a cost: jobs that sleep for short periods of time frequently do not ever get their fair share of the CPU.

### Multiprocessor Scheduling