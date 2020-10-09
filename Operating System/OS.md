

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

