

[toc]

## Virtualizaiton of CPU

### Access restriction

1. **User mode:** applications do not have full access to hardware resources.
2. **Kernel mode:** the OS has access to the full resources of the machine.

##### Trap table:

Kernel will set up trap table at boot time which the OS will tell the hardware what code to run when certain exceptional event occur(remember address of syscall handler).

#### Limited direct execution protocol

To execute a system call, a program must execute a special **trap** instruction. This instruction simultaneously jumps into the kernel and raises the privilege level to kernel mode; once in the kernel, the system can now perform whatever privileged operations are needed (if allowed), and thus do the required work for the calling process. When finished, the OS calls a special **return-from-trap** instruction, which, as you might expect, returns into the calling user program while simultaneously reducing the privilege level back to user mode. A per-process **kernel stack** was created to keep the PC, flags and a few other registers.

1. The kernel initializes the tap table and the CPU remembers its location for subsequent use.
2. The kernel sets up a few thing(allocating a node on the process list, allocating memory ...) before using a return -from-trap instruction to start the execution of the process.



