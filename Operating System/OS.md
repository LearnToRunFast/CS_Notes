

[toc]

## Virtualizaiton of CPU

### Key CPU virtualization terms:

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

### Work flow for limited direct exectuion

1. The OS boots by initializing **trap table** and the hardware will remember addresses of syscall handler and timer handler.
2. The OS start interrupt timer and handware will start timer interrupt CPU in X ms.
3. During the running of process A, timer interrrupt happened. It saves register values of A to it's kernal stack and move to kernel mode, jump to trap handler.
4. The OS will handle the trap by calling switch(). It will save register values of A to process structure of A, restore correpoding values from process structure of B and switch to kernel stack of B. Finally **return-from-trap** into B.
5. The hardware will restore register valus of B from kernel stack of B and move to user mode and jump to B's PC then process B will start running.

### Scheduling

**Turnaround time:**The turnaround time of a job is defined as the time at which the job completes minus the time at which the job arrived in the system.

**Response time:** The time from when the job arrives in a system to the first time it is scheduled.

1. **First In, First Out(FIFO):**Early arrive process will get executed first.
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