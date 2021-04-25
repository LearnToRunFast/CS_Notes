## Virtual Memory

In order to manage memory more efficiently and with fewer errors, modern systems provide an abstraction of main memory known as *virtual memory (VM)*. With one clean mechanism, virtual memory provides three important capabilities: 

1. It uses main memory efficiently by treating it as a cache for an address space stored on disk, keeping only the active areas in main memory and transferring data back and forth between disk and memory as needed.
2. It simplifies memory management by providing each process with a uniform address space. 
3. It protects the address space of each process from corruption by other processes.

### Physical and Virtual Addressing

Figure 9.1 shows an example of physical addressing in the context of a load instruction that reads the 4-byte word starting at physical address 4. When the CPU executes the load instruction, it generates an effective physical address and passes it to main memory over the memory bus. The main memory fetches the 4-byte word starting at physical address 4 and returns it to the CPU, which stores it in a register.

![image-20210404115056940](Asserts/image-20210404115056940.png)

The modern processors use a form of addressing known as *virtual addressing*, as shown in Figure 9.2. With virtual addressing, the CPU accesses main memory by generating a *virtual address (VA)*, which is converted to the appropriate physical address before being sent to main memory. The task of converting a virtual address to a physical one is known as *address translation*. Like exception handling, address translation requires close cooperation between the CPU hardware and the operating system. Dedicated hardware on the CPU chip called the *memory management unit (MMU)* translates virtual addresses on the fly, using a lookup table stored in main memory whose contents are managed by the operating system.

![image-20210404115229643](Asserts/image-20210404115229643.png)

### Address Spaces

In a system with virtual memory, the CPU generates virtual addresses from an address space of N = $2^n$ addresses called the *virtual address space*. The size of an address space is characterized by the number of bits that are needed to represent the largest address. A virtual address space with N = $2^n$ addresses is called an n-bit address space. Modern systems typically support either 32-bit or 64-bit virtual address spaces.

### Page

#### Page Tables

The data on disk (the lower level) is partitioned into blocks that serve as the transfer units between the disk and the main memory (the upper level). Virtual memory systems handle this by partitioning the virtual memory into fixed-size blocks called *virtual pages (VPs)*. Each virtual page is P = $2^p$ bytes in size. Similarly, physical memory is partitioned into *physical pages (PPs)*, also P bytes in size. (Physical pages are also referred to as *page frames*.)

The address translation hardware  in the MMU reads the page table each time it converts a virtual address to a physical address using a data structure stored in physical memory known as a *page table* that maps virtual pages to physical pages. The operating system is responsible for maintaining the contents of the page table and transferring pages back and forth between disk and DRAM.

Figure 9.4 shows the basic organization of a page table. A page table is an array of *page table entries (PTEs)*. Each page in the virtual address space has a PTE at a fixed offset in the page table. For our purposes, we will assume that each PTE consists of a *valid bit* and an n-bit address field. The valid bit indicates whether the virtual page is currently cached in DRAM. If the valid bit is set, the address field indicates the start of the corresponding physical page in DRAM where the virtual page is cached. If the valid bit is not set, then a null address indicates that the virtual page has not yet been allocated. Otherwise, the address points to the start of the virtual page on disk.

![image-20210404124257173](Asserts/image-20210404124257173.png)

#### Page Hits and Page Faults

If the valid bit is set for specify read instruction, the address translation hardware knows the physical memory address n the PTE and uses it to find the content. This is consider as a **Page hit**.

If the valid bit is not set for specify read instruction, the address translation hardware triggers a page fault exception. The page fault exception invokes a page fault exception handler in the kernel, which load the physical location into page table. This process called **Page Fault**. 

#### Demand Paging

The activity of transferring a page between disk and memory is known as *swapping* or *paging*. Pages are *swapped in* (*paged in*) from disk to DRAM, and *swapped out* (*paged out*) from DRAM to disk. The strategy of waiting until the last moment to swap in a page, when a miss occurs, is known as *demand paging*.

#### Locality

In practice, virtual memory works well, mainly because of *locality*. If the working set size exceeds the size of physical memory, then the program can produce an unfortunate situation known as *thrashing*, where pages are swapped in and out continuously. Although virtual memory is usually efficient, if a program’s performance slows to a crawl, the wise programmer will consider the possibility that it is thrashing.

### Memory Management

Operating systems provide a separate page table, and thus a separate virtual address space, for each process. Figure 9.9 shows the basic idea.

![image-20210404130218720](Asserts/image-20210404130218720.png)

VM simplifies linking and loading, the sharing of code and data, and allocating memory to applications.

- *Simplifying linking.* A separate address space allows each process to use the same basic format for its memory image, regardless of where the code and data actually reside in physical memory. For 64-bit address spaces, the code segment *always* starts at virtual address `0x400000`. The data segment follows the code segment after a suitable alignment gap. The stack occupies the highest portion of the user process address space and grows downward. Such uniformity greatly simplifies the design and implementation of linkers, allowing them to produce fully linked executables that are independent of the ultimate location of the code and data in physical memory.
- *Simplifying loading.* Virtual memory also makes it easy to load executable and shared object files into memory. To load the .text and .data sections of an object file into a newly created process, the Linux loader allocates virtual pages for the code and data segments, marks them as invalid (i.e., not cached), and points their page table entries to the appropriate locations in the object file. The interesting point is that the loader never actually copies any data from disk into memory. The data are paged in automatically and on demand by the virtual memory system the first time each page is referenced, either by the CPU when it fetches an instruction or by an executing instruction when it references a memory location.
- *Simplifying sharing.* In some instances it is desirable for processes to share code and data. For example, every process must call the same operating system kernel code, and every C program makes calls to routines in the standard C library such as printf. Rather than including separate copies of the kernel and standard C library in each process, the operating system can arrange for multiple processes to share a single copy of this code by mapping the appropriate virtual pages in different processes to the same physical pages.

- *Simplifying memory allocation.* Virtual memory provides a simple mechanism for allocating additional memory to user processes. When a program running in a user process requests additional heap space, the operating system allocates an appropriate number, say, k, of contiguous virtual memory pages, and maps them to k arbitrary physical pages located anywhere in physical memory. Because of the way page tables work, there is no need for the operating system to locate k contiguous pages of physical memory. The pages can be scattered randomly in physical memory.

### Memory Protection

The operating system to control access to the memory system to prevent any unauthorized or unauthenticated users or programs to access the memory regions that they are not supposed to access. Figure 9.10 shows the general idea. There are 3 extra bits:

1. The SUP bit indicates whether processes must be running in kernel (supervisor) mode to access the page. Processes running in kernel mode can access any page, but processes running in user mode are only allowed to access pages for which SUP is 0.
2. The READ and WRITE bits control read and write access to the page.

If an instruction violates these permissions, then the CPU triggers a general protection fault that transfers control to an exception handler in the kernel, which sends a `SIGSEGV` signal to the offending process. Linux shells typically report this exception as a “segmentation fault.”

![image-20210404150754679](Asserts/image-20210404150754679.png)

### Address Translation

Figure 9.12 shows how the MMU uses the page table to perform this mapping. A control register in the CPU, the *page table base register (PTBR)* points to the current page table. 

The n-bit virtual address has two components: 

- a p-bit *virtual page offset (VPO)*
- an (n − p)-bit *virtual page number (VPN)*. 

The MMU uses the VPN to select the appropriate PTE. For example, VPN 0 selects PTE 0, VPN 1 selects PTE 1, and so on. The corresponding physical address is the concatenation of the *physical page number (PPN)* from the page table entry and the VPO from the virtual address. Notice that since the physical and virtual pages are both P bytes, the *physical page offset (PPO)* is identical to the VPO.

![image-20210404151425638](Asserts/image-20210404151425638.png)

Figure 9.13(a) shows the steps that the CPU hardware performs when there is a page hit.

1. The processor generates a virtual address and sends it to the MMU. 
2. The MMU generates the PTE address and requests it from the cache/main memory.
3. The cache/main memory returns the PTE to the MMU.
4. The MMU constructs the physical address and sends it to the cache/main memory.
5. The cache/main memory returns the requested data word to the processor.

Unlike a page hit, which is handled entirely by hardware, handling a page fault requires cooperation between hardware and the operating system kernel (Figure 9.13(b)).

1. The processor generates a virtual address and sends it to the MMU. 
2. The MMU generates the PTE address and requests it from the cache/main memory.
3. The cache/main memory returns the PTE to the MMU.
4. The valid bit in the PTE is zero, so the MMU triggers an exception, which transfers control in the CPU to a page fault exception handler in the operating system kernel.
5. The fault handler identifies a victim page in physical memory, and if that page has been modified, pages it out to disk.
6. The fault handler pages in the new page and updates the PTE in memory.
7. The fault handler returns to the original process, causing the faulting instruction to be restarted. The CPU resends the offending virtual address to the MMU. Because the virtual page is now cached in physical memory, there is a hit, and after the MMU performs the steps in Figure 9.13(a), the main memory returns the requested word to the processor.

![image-20210404151902591](Asserts/image-20210404151902591.png)

#### Speeding Up Address Translation with a TLB

Every time the CPU generates a virtual address, the MMU must refer to a PTE in order to translate the virtual address into a physical address. In the worst case, this requires an additional fetch from memory, at a cost of tens to hundreds of cycles. If the PTE happens to be cached in L1, then the cost goes down to a handful of cycles. However, many systems try to eliminate even this cost by including a small cache of PTEs in the MMU called a *translation lookaside buffer (TLB)*.

A TLB is a small, virtually addressed cache where each line holds a block consisting of a single PTE. A TLB usually has a high degree of associativity. As shown in Figure 9.15, the index and tag fields that are used for set selection and line matching are extracted from the virtual page number in the virtual address. If the TLB has T = $2^t$ sets, then the *TLB index (TLBI)* consists of the t least significant bits of the VPN, and the *TLB tag (TLBT)* consists of the remaining bits in the VPN.

![image-20210404154726434](Asserts/image-20210404154726434.png)

Figure 9.16(a) shows the steps involved when there is a TLB hit (the usual case). The key point here is that all of the address translation steps are performed inside the on-chip MMU and thus are fast.

1. The CPU generates a virtual address.
2. The MMU fetches the appropriate PTE from the TLB.
3. The MMU translates the virtual address to a physical address and sends it to the cache/main memory.
4. The cache/main memory returns the requested data word to the CPU.

When there is a TLB miss, then the MMU must fetch the PTE from the L1 cache, as shown in Figure 9.16(b). The newly fetched PTE is stored in the TLB, possibly overwriting an existing entry.

![image-20210404155432197](Asserts/image-20210404155432197.png)

#### Multi-Level Page Tables

With systems with 64-bit address spaces, a single page table will be to big to hold all the PTEs. It is not only hard to place it on memory but also will causing a slow look up compare to small page table.

The common approach for compacting the page table is to use a hierarchy of page tables instead. Consider a 32-bit virtual address space partitioned into 4 KB pages, with page table entries that are 4 bytes each. Suppose also that at this point in time the virtual address space has the following form: The first 2 K pages of memory are allocated for code and data, the next 6 K pages are unallocated, the next 1,023 pages are also unallocated, and the next page is allocated for the user stack. Figure 9.17 shows how we might construct a two-level page table hierarchy for this virtual address space.

![image-20210404160637989](Asserts/image-20210404160637989.png)

This scheme reduces memory requirements in two ways. 

- First, if a PTE in the level 1 table is null, then the corresponding level 2 page table does not even have to exist. This represents a significant potential savings, since most of the 4 GB virtual address space for a typical program is unallocated. 
- Second, only the level 1 table needs to be in main memory at all times. The level 2 page tables can be created and paged in and out by the VM system as they are needed, which reduces pressure on main memory. Only the most heavily used level 2 page tables need to be cached in main memory.

Figure 9.18 summarizes address translation with a k-level page table hierarchy. The virtual address is partitioned into k VPNs and a VPO. Each VPN i, 1 ≤ i ≤ k, is an index into a page table at level i. Each PTE in a level j table, 1 ≤ j ≤ k − 1, points to the base of some page table at level j + 1. Each PTE in a level k table contains either the PPN of some physical page or the address of a disk block. To construct the physical address, the MMU must access k PTEs before it can determine the PPN. As with a single-level hierarchy, the PPO is identical to the VPO.

![image-20210404160948862](Asserts/image-20210404160948862.png)

### End-to-End Address Translation

To keep things manageable, we make the following assumptions:

- The memory is byte addressable.
- Memory accesses are to *1-byte words* (not 4-byte words).
- Virtual addresses are 14 bits wide (n = 14).
- Physical addresses are 12 bits wide (m = 12).
- The page size is 64 bytes (P = 64).
- The TLB is 4-way set associative with 16 total entries.
- The L1 d-cache is physically addressed and direct mapped, with a 4-byte line size and 16 total sets.

Figure 9.19 shows the formats of the virtual and physical addresses. Since each page is $2^6 = 64$ bytes, the low-order 6 bits of the virtual and physical addresses serve as the VPO and PPO, respectively. The high-order 8 bits of the virtual address serve as the VPN. The high-order 6 bits of the physical address serve as the PPN.

![image-20210404161432171](Asserts/image-20210404161432171.png)

Figure 9.20 shows a snapshot of our little memory system.

![image-20210404162833242](Asserts/image-20210404162833242.png)

When the CPU executes a load instruction that reads the byte at address `0x03d4`. (Recall that our hypothetical CPU reads 1-byte words rather than 4-byte words.) 

![image-20210404162914260](Asserts/image-20210404162914260.png)

The MMU extracts the VPN (0x0F) from the virtual address and checks with the TLB to see if it has cached a copy of PTE 0x0F from some previous memory reference. The TLB extracts the TLB index (0x03) and the TLB tag (0x3) from the VPN, hits on a valid match in the second entry of set 0x3, and returns the cached PPN (`0x0D`) to the MMU.

If the TLB had missed, then the MMU would need to fetch the PTE from main memory. However, in this case, we got lucky and had a TLB hit. The MMU now has everything it needs to form the physical address. It does this by concatenating the PPN (0x0D) from the PTE with the VPO (0x14) from the virtual address, which forms the physical address (0x354).

Next, the MMU sends the physical address to the cache, which extracts the cache offset CO (0x0), the cache set index CI (0x5), and the cache tag CT (0x0D) from the physical address.

![image-20210404163115634](Asserts/image-20210404163115634.png)

Since the tag in set 0x5 matches CT, the cache detects a hit, reads out the data byte (0x36) at offset CO, and returns it to the MMU, which then passes it back to the CPU.

If the TLB misses, then the MMU must fetch the PPN from a PTE in the page table. If the resulting PTE is invalid, then there is a page fault and the kernel must page in the appropriate page and rerun the load instruction. Another possibility is that the PTE is valid, but the necessary memory block misses in the cache.

### Case Study: The Intel Core i7/Linux Memory System

![image-20210404163646433](Asserts/image-20210404163646433.png)

Figure 9.21 gives the highlights of the Core i7 memory system which support a 48-bit (256 TB) virtual address space and a 52-bit (4 PB) physical address space, along with a compatibility mode that supports 32-bit (4 GB) virtual and physical address spaces. The *processor package* (chip) includes 

- four cores
  - Each core contains a hierarchy of TLBs
    - a hierarchy of data and instruction caches
    - a set of fast point-to-point links, based on the QuickPath technology, for communicating directly with the other cores and the external I/O bridge. 
  - The TLBs are virtually addressed, and 4-way set associative. 
  - The L1, L2, and L3 caches are physically addressed, with a block size of 64 bytes. 
    - L1 and L2 are 8-way set associative
    - L3 is 16-way set associative. 
  - The page size can be configured at start-up time as either 4 KB or 4 MB. Linux uses 4 KB pages.
- a large L3 cache shared by all of the cores
- a DDR3 memory controller shared by all of the cores

#### Core i7 Address Translation

![image-20210404164202809](Asserts/image-20210404164202809.png)

Figure 9.22 summarizes the entire Core i7 address translation process, from the time the CPU generates a virtual address until a data word arrives from memory. The Core i7 uses a four-level page table hierarchy. Each process has its own private page table hierarchy. 

When a Linux process is running, the page tables associated with allocated pages are all memory-resident, although the Core i7 architecture allows these page tables to be swapped in and out. The *CR3* control register contains the physical address of the beginning of the level 1 (L1) page table. The value of CR3 is part of each process context, and is restored during each context switch.

![image-20210404164415710](Asserts/image-20210404164415710.png)

Figure 9.23 shows the format of an entry in a level 1, level 2, or level 3 page table. When P = 1 (which is always the case with Linux), the address field contains a 40-bit physical page number (PPN) that points to the beginning of the appropriate page table. Notice that this imposes a 4 KB alignment requirement on page tables.

![image-20210404164612003](Asserts/image-20210404164612003.png)

Figure 9.24 shows the format of an entry in a level 4 page table. When P = 1, the address field contains a 40-bit PPN that points to the base of some page in physical memory. Again, this imposes a 4 KB alignment requirement on physical pages.

> _**Note**_: The *XD* (execute disable) bit, which was introduced in 64-bit systems, can be used to disable instruction fetches from individual memory pages. This is an important new feature that allows the operating system kernel to reduce the risk of buffer overflow attacks by restricting execution to the read-only code segment.

As the MMU translates each virtual address, it also updates two other bits that can be used by the kernel’s page fault handler. 

- The MMU sets the A bit, which is known as a *reference bit*, each time a page is accessed. The kernel can use the reference bit to implement its page replacement algorithm. 
- The MMU sets the D bit, or *dirty bit*, each time the page is written to. A page that has been modified is sometimes called a *dirty page*. The dirty bit tells the kernel whether or not it must write back a victim page before it copies in a replacement page. 

The kernel can call a special kernel-mode instruction to clear the reference or dirty bits.

![image-20210404165059175](Asserts/image-20210404165059175.png)

Figure 9.25 shows how the Core i7 MMU uses the four levels of page tables to translate a virtual address to a physical address. The 36-bit VPN is partitioned into four 9-bit chunks, each of which is used as an offset into a page table. The CR3 register contains the physical address of the L1 page table. VPN 1 provides an offset to an L1 PTE, which contains the base address of the L2 page table. VPN 2 provides an offset to an L2 PTE, and so on.

#### Linux Virtual Memory System

![image-20210404170052374](Asserts/image-20210404170052374.png)

Linux maintains a separate virtual address space for each process of the form shown in Figure 9.26. The kernel virtual memory contains the code and data structures in the kernel. Some regions of the kernel virtual memory are mapped to physical pages that are shared by all processes. For example, each process shares the kernel’s code and global data structures. Linux also maps a set of contiguous virtual pages (equal in size to the total amount of DRAM in the system) to the corresponding set of contiguous physical pages. This provides the kernel with a convenient way to access any specific location in physical memory—for example, when it needs to access page tables or to perform memory-mapped I/O operations on devices that are mapped to particular physical memory locations.

Other regions of kernel virtual memory contain data that differ for each process. Examples include page tables, the stack that the kernel uses when it is executing code in the context of the process, and various data structures that keep track of the current organization of the virtual address.

#### Linux Virtual Memory Areas

Linux organizes the virtual memory as a collection of *areas* (also called *segments*). For example, the code segment, data segment, heap, shared library segment, and user stack are all distinct areas. Each existing virtual page is contained in some area, and any virtual page that is not part of some area does not exist and cannot be referenced by the process. The notion of an area is important because it allows the virtual address space to have gaps. The kernel does not keep track of virtual pages that do not exist, and such pages do not consume any additional resources in memory, on disk, or in the kernel itself.

![image-20210404171141176](Asserts/image-20210404171141176.png)

Figure 9.27 highlights the kernel data structures that keep track of the virtual memory areas in a process. The kernel maintains a distinct task structure (`task_ struct` in the source code) for each process in the system. The elements of the task structure either contain or point to all of the information that the kernel needs to run the process (e.g., the PID, pointer to the user stack, name of the executable object file, and program counter).

One of the entries in the task structure points to an `mm_struct` that characterizes the current state of the virtual memory. The two fields of interest to us are 

- `pgd`, which points to the base of the level 1 table (the page global directory)
- `mmap`, which points to a list of `vm_area_structs` (area structs), each of which characterizes an area of the current virtual address space. When the kernel runs this process, it stores `pgd` in the CR3 control register.

For our purposes, the area struct for a particular area contains the following fields:

- `fvm_start`: Points to the beginning of the area.

- `vm_end`: Points to the end of the area.

- `vm_prot`: Describes the read/write permissions for all of the pages contained in the area.

- `vm_flags`: Describes (among other things) whether the pages in the area are shared with other processes or private to this process.

- `vm_next`: Points to the next area struct in the list.

#### Linux Page Fault Exception Handling

![image-20210404172451727](Asserts/image-20210404172451727.png)

Suppose the MMU triggers a page fault while trying to translate some virtual address A. The exception results in a transfer of control to the kernel’s page fault handler, which then performs the following steps:

1. `Check the validity of the address A`. The fault handler searches the list of area structs, comparing A with the `vm_start` and `vm_end` in each area struct. If the instruction is not legal, then the fault handler triggers a segmentation fault, which terminates the process. This situation is labeled “1” in Figure 9.28. 
   - Because a process can create an arbitrary number of new virtual memory areas (using the mmap function), a sequential search of the list of area structs might be very costly. So in practice, Linux superimposes a tree on the list, using some fields that we have not shown, and performs the search on this tree.
2. `Check permission of the instruction`. If the attempted access is not legal, then the fault handler triggers a protection exception, which terminates the process. This situation is labeled “2” in Figure 9.28.
3. At this point, the kernel knows that the page fault resulted from a legal operation on a legal virtual address. It handles the fault by selecting a victim page, swapping out the victim page if it is dirty, swapping in the new page, and updating the page table. When the page fault handler returns, the CPU restarts the faulting instruction, which sends A to the MMU again. This time, the MMU translates A normally, without generating a page fault.

### Memory Mapping

Linux initializes the contents of a virtual memory area by associating it with an *object* on disk, a process known as *memory mapping*. Areas can be mapped to one of two types of objects:

- *Regular file in the Linux file system:* An area can be mapped to a contiguous section of a regular disk file, such as an executable object file. The file section is divided into page-size pieces, with each piece containing the initial contents of a virtual page. Because of demand paging, none of these virtual pages is actually swapped into physical memory until the CPU first *touches* the page (i.e., issues a virtual address that falls within that page’s region of the address space). If the area is larger than the file section, then the area is padded with zeros.
- *Anonymous file:* An area can also be mapped to an anonymous file, created by the kernel, that contains all binary zeros. The first time the CPU touches a virtual page in such an area, the kernel finds an appropriate victim page in physical memory, swaps out the victim page if it is dirty, overwrites the victim page with binary zeros, and updates the page table to mark the page as resident. Notice that no data are actually transferred between disk and memory. For this reason, pages in areas that are mapped to anonymous files are sometimes called *demand-zero pages*.

In either case, once a virtual page is initialized, it is swapped back and forth between a special *swap file* maintained by the kernel. The swap file is also known as the *swap space* or the *swap area*. An important point to realize is that at any point in time, the swap space bounds the total amount of virtual pages that can be allocated by the currently running processes.

#### Shared Objects

Memory mapping provides us with a clean mechanism for controlling how objects are shared by multiple processes. 

An object can be mapped into an area of virtual memory as either a *shared object* or a *private object*. A virtual memory area for a shared object is called a *shared area*. Similarly for a *private area*.

- Any writes to shared object will be visible to any processes that mapped the shared object into their virtual memory, the changes are also reflected in the original object on disk.
- On the other hand, changes made to an area mapped to a private object are not visible to other processes, and any writes that the process makes to the area are *not* reflected back to the object on disk. 

![image-20210404183004285](Asserts/image-20210404183004285.png)

Private objects are mapped into virtual memory using a clever technique known as *copy-on-write*. A private object begins life in exactly the same way as a shared object, with only one copy of the private object stored in physical memory  show in Figure 9.30(a). By deferring the copying of the pages in private objects until the last possible moment, copy-on-write makes the most efficient use of scarce physical memory.

For each process that maps the private object, the page table entries for the corresponding private area are flagged as read-only, and the area struct is flagged as *private copy-on-write*. If a process attempts to write to some page in the private area, the write triggers a protection fault.

When the fault handler notices that the protection exception was caused by the process trying to write to a page in a private copy-on-write area, it creates a new copy of the page in physical memory, updates the page table entry to point to the new copy, and then restores write permissions to the page, as shown in Figure 9.30(b). When the fault handler returns, the CPU re-executes the write, which now proceeds normally on the newly created page.

#### Fork

When the fork function is called by the *current process*, the kernel creates various data structures for the *new process* and assigns it a unique PID. To create the virtual memory for the new process, it creates exact copies of the current process’s `mm_struct`, `area structs`, and page tables. It flags each page in both processes as read-only, and flags each area struct in both processes as private copy-on-write.

When the fork returns in the new process, the new process now has an exact copy of the virtual memory as it existed when the fork was called. When either of the processes performs any subsequent writes, the copy-on-write mechanism creates new pages, thus preserving the abstraction of a private address space for each process.

#### Execve

Suppose that the program running in the current process makes the following call:

```c
execve("a.out", NULL, NULL);
```

![image-20210404212556076](Asserts/image-20210404212556076.png)

Loading and running `a.out` requires the following steps:

1. *Delete existing user areas.* Delete the existing area structs in the user portion of the current process’s virtual address.

2. *Map private areas.* Create new area structs for the code, data, bss, and stack areas of the new program. All of these new areas are private copy-on-write. The code and data areas are mapped to the `.text` and `.data` sections of the a.out file. The bss area is demand-zero, mapped to an anonymous file whose size is contained in a.out. The stack and heap area are also demand-zero, initially of zero length. Figure 9.31 summarizes the different mappings of the private areas.
3. *Map shared areas.* If the a.out program was linked with shared objects, such as the standard C library libc.so, then these objects are dynamically linked into the program, and then mapped into the shared region of the user’s virtual address space.
4. *Set the program counter (PC).* The last thing that `execve` does is to set the program counter in the current process’s context to point to the entry point in the code area.

#### User-Level Memory Mapping with the mmap Function

Linux processes can use the `mmap` function to create new areas of virtual memory and to map objects into these areas.

```c
#include <unistd.h>
#include <sys/mman.h>
void  *mmap(void *start, size_t length, int prot, int flags,
            int fd, off_t offset);
```

![image-20210404213246657](Asserts/image-20210404213246657.png)

The `mmap` function asks the kernel to create a new virtual memory area, preferably one that starts at address `start`, and to map a contiguous chunk of the object specified by file descriptor `fd` to the new area. The contiguous object chunk has a size of `length` bytes and starts at an offset of `offset` bytes from the beginning of the file. The start address is merely a hint, and is usually specified as NULL. For our purposes, we will always assume a NULL start address. Figure 9.32 depicts the meaning of these arguments.

The `prot` argument contains bits that describe the access permissions of the newly mapped virtual memory area (i.e., the vm_prot bits in the corresponding area struct).

- `PROT_EXEC`. Pages in the area consist of instructions that may be executed by the CPU.
- `PROT_READ`. Pages in the area may be read. 
- `PROT_WRITE`. Pages in the area may be written. 
- `PROT_NONE`. Pages in the area cannot be accessed.

The `flags` argument consists of bits that describe the type of the mapped object.

- If the `MAP_ANON` flag bit is set, then the backing store is an anonymous object and the corresponding virtual pages are demand-zero.
- `MAP_PRIVATE` indicates a private copy-on-write object
- `MAP_SHARED` indicates a shared object. For example,

For example, asks the kernel to create a new read-only, private, demand-zero area of virtual memory containing size bytes. If the call is successful, then `bufp` contains the address of the new area.

```c
bufp = Mmap(NULL, size, PROT_READ, MAP_PRIVATE|MAP_ANON, 0, 0);
```

The `munmap` function deletes regions of virtual memory:

```c
#include <unistd.h>
#include <sys/mman.h>
int munmap(void *start, size_t length);
```

### Dynamic Memory Allocation

While it is certainly possible to use the low-level `mmap` and `munmap` functions to create and delete areas of virtual memory, C programmers typically find it more convenient and more portable to use a *dynamic memory allocator* when they need to acquire additional virtual memory at run time.

![image-20210405110612071](Asserts/image-20210405110612071.png)

A dynamic memory allocator maintains an area of a process’s virtual memory known as the *heap* (Figure 9.33). Details vary from system to system, but without loss of generality, we will assume that the heap is an area of demand-zero memory that begins immediately after the uninitialized data area and grows upward (toward higher addresses). For each process, the kernel maintains a variable `brk` (pronounced “break”) that points to the top of the heap.

An allocator maintains the heap as a collection of various-size *blocks*. Each block is a contiguous chunk of virtual memory that is either *allocated* or *free*. An allocated block has been explicitly reserved for use by the application. A free block is available to be allocated. A free block remains free until it is explicitly allocated by the application. An allocated block remains allocated until it is freed, either explicitly by the application or implicitly by the memory allocator itself.

Allocators come in two basic styles. Both styles require the application to explicitly allocate blocks. They differ about which entity is responsible for freeing allocated blocks.

- *Explicit allocators* require the application to explicitly free any allocated blocks. For example, the C standard library provides an explicit allocator called the malloc package. C programs allocate a block by calling the `malloc` function, and free a block by calling the `free` function. The new and delete calls in C++ are comparable.
- *Implicit allocators* require the allocator to detect when an allocated block is no longer being used by the program and then free the block. Implicit allocators are also known as *garbage collectors*, and the process of automatically freeing unused allocated blocks is known as *garbage collection*. For example, higher-level languages such as Lisp, ML, and Java rely on garbage collection to free allocated blocks.

#### The malloc and free Functions

The `malloc` function returns a pointer to a block of memory of at least size bytes that is suitably aligned for any kind of data object that might be contained in the block. If `malloc` encounters a problem (e.g., the program requests a block of memory that is larger than the available virtual memory), then it returns NULL and sets `errno`. `Malloc` does not initialize the memory it returns. Applications that want initialized dynamic memory can use `calloc`, a thin wrapper around the `malloc` function that initializes the allocated memory to zero. Applications that want to change the size of a previously allocated block can use the `realloc` function.

```c
#include <stdlib.h>
void *malloc(size_t size);
```

Dynamic memory allocators such as `malloc` can allocate or deallocate heap memory explicitly by using the `mmap` and `munmap` functions, or they can use the `sbrk` function. The `sbrk` function grows or shrinks the heap by adding `incr` to the kernel’s `brk` pointer. If successful, it returns the old value of `brk`, otherwise it returns −1 and sets `errno` to *ENOMEM*. If `incr` is zero, then `sbrk` returns the current value of `brk`. Calling `sbrk` with a negative incr is legal but tricky because the return value (the old value of brk) points to `abs(incr)` bytes past the new top of the heap.

```c
#include <unistd.h>
void *sbrk(intptr_t incr);
```

Programs free allocated heap blocks by calling the `free` function. The `ptr` argument must point to the beginning of an allocated block that was obtained from `malloc`, `calloc`, or `realloc`. If not, then the behavior of `free` is undefined. Even worse, since it returns nothing, free gives no indication to the application that something is wrong. This can produce some baffling run-time errors.

#### Allocator Requirements and Goals

Explicit allocators must operate within some rather stringent constraints:

- *Handling arbitrary request sequences.* The allocator cannot make any assumptions about the ordering of allocate and free requests. 

- *Making immediate responses to requests.* The allocator must respond immediately to allocate requests. Thus, the allocator is not allowed to reorder or buffer requests in order to improve performance.
- *Using only the heap.* In order for the allocator to be scalable, any nonscalar data structures used by the allocator must be stored in the heap itself.
- *Aligning blocks (alignment requirement).* The allocator must align blocks in such a way that they can hold any type of data object.
- *Not modifying allocated blocks.* Allocators can only manipulate or change free blocks. In particular, they are not allowed to modify or move blocks once they are allocated. Thus, techniques such as compaction of allocated blocks are not permitted.

Working within these constraints, the author of an allocator attempts to meet the often conflicting performance goals of maximizing throughput and memory utilization.

- *Goal 1: Maximizing throughput.*  For example, if an allocator completes 500 allocate requests and 500 free requests in 1 second, then its throughput is 1,000 operations per second. In general, we can maximize throughput by minimizing the average time to satisfy allocate and free requests.
- *Goal 2: Maximizing memory utilization.* Good programmers know that virtual memory is a finite resource that must be used efficiently. 

#### Fragmentation

The primary cause of poor heap utilization is a phenomenon known as *fragmentation*, which occurs when otherwise unused memory is not available to satisfy allocate requests. There are two forms of fragmentation: *internal fragmentation* and *external fragmentation*.

- *Internal fragmentation* occurs when an allocated block is larger than the payload. The smallest unit in heap is block size, if the requested size is not multiple of block size, the free spaces left is internal fragmentation.
- *External fragmentation* occurs when there *is* enough aggregate free memory to satisfy an allocate request, but no single free block is large enough to handle the request. 

#### Implementation

A practical allocator that strikes a better balance between throughput and utilization must consider the following issues:

- *Free block organization.* How do we keep track of free blocks?
- *Placement.* How do we choose an appropriate free block in which to place a newly allocated block?
- *Splitting.* After we place a newly allocated block in some free block, what do we do with the remainder of the free block?
- *Coalescing.* What do we do with a block that has just been freed?

#### Implicit Free Lists

![image-20210405121805699](Asserts/image-20210405121805699.png)

Any practical allocator needs some data structure that allows it to distinguish block boundaries and to distinguish between allocated and free blocks. Most allocators embed this information in the blocks themselves. One simple approach is shown in Figure 9.35.

#### Explicit Free Lists

The implicit free list provides us with a simple way to introduce some basic allocator concepts. However, because block allocation time is linear in the total number of heap blocks, the implicit free list is not appropriate for a general purpose allocator (although it might be fine for a special-purpose allocator where the number of heap blocks is known beforehand to be small).

![image-20210405133827538](Asserts/image-20210405133827538.png)

A better approach is to organize the free blocks into some form of explicit data structure. Since by definition the body of a free block is not needed by the program, the pointers that implement the data structure can be stored within the bodies of the free blocks. For example, the heap can be organized as a doubly linked free list by including a `pred` (predecessor) and `succ` (successor) pointer in each free block, as shown in Figure 9.48.

#### Segregated Free Lists

A popular approach for reducing the allocation time, known generally as *segregated storage*, is to maintain multiple free lists, where each list holds blocks that are roughly the same size. The general idea is to partition the set of all possible block sizes into equivalence classes called *size classes*. 

The allocator maintains an array of free lists, with one free list per size class, ordered by increasing size. When the allocator needs a block of size n, it searches the appropriate free list. If it cannot find a block that fits, it searches the next list, and so on.

##### Simple Segregated Storage

With simple segregated storage, the free list for each size class contains same-size blocks, each the size of the largest element of the size class. For example, if some size class is defined as {17–32}, then the free list for that class consists entirely of blocks of size 32.

To allocate a block of some given size, we check the appropriate free list. If the list is not empty, we simply allocate the first block in its entirety. Free blocks are never split to satisfy allocation requests. If the list is empty, the allocator requests a fixed-size chunk of additional memory from the operating system (typically a multiple of the page size), divides the chunk into equal-size blocks, and links the blocks together to form the new free list. To free a block, the allocator simply inserts the block at the front of the appropriate free list.

There are a number of advantages to this simple scheme. 

- Allocating and freeing blocks are both fast constant-time operations. 
- The combination of the same-size blocks in each chunk, no splitting, and no coalescing means that there is very little per-block memory overhead. 
- Since each chunk has only same-size blocks, the size of an allocated block can be inferred from its address.
- Since there is no coalescing, allocated blocks do not need an allocated/free flag in the header. Thus, allocated blocks require no headers, and since there is no coalescing, they do not require any footers either. 
- Since allocate and free operations insert and delete blocks at the beginning of the free list, the list need only be singly linked instead of doubly linked. 
- The only required field in any block is a one-word `succ` pointer in each free block, and thus the minimum block size is only one word.

A significant disadvantage is that simple segregated storage is 

- susceptible to internal and external fragmentation. Internal fragmentation is possible because free blocks are never split. Worse, certain reference patterns can cause extreme external fragmentation because free blocks are never coalesced.

##### Segregated Fits

The allocator maintains an array of free lists. Each free list is associated with a size class and is organized as some kind of explicit or implicit list. Each list contains potentially different-size blocks whose sizes are members of the size class. There are many variants of segregated fits allocators. Here we describe a simple version.

To allocate a block, we determine the size class of the request and do a first-fit search of the appropriate free list for a block that fits. If we find one, then we (optionally) split it and insert the fragment in the appropriate free list. If we cannot find a block that fits, then we search the free list for the next larger size class. We repeat until we find a block that fits. If none of the free lists yields a block that fits, then we request additional heap memory from the operating system, allocate the block out of this new heap memory, and place the remainder in the appropriate size class. To free a block, we coalesce and place the result on the appropriate free list.

The segregated fits approach is a popular choice with production-quality allocators such as the GNU `malloc` package provided in the C standard library because it is both fast and memory efficient. Search times are reduced because searches are limited to particular parts of the heap instead of the entire heap. Memory utilization can improve because of the interesting fact that a simple first-fit search of a segregated free list approximates a best-fit search of the entire heap.

##### Buddy Systems

A *buddy system* is a special case of segregated fits where each size class is a power of 2. The basic idea is that, given a heap of $2^m$ words, we maintain a separate free list for each block size $2^k$, where 0 ≤ k ≤ m. Requested block sizes are rounded up to the nearest power of 2. Originally, there is one free block of size $2^m$ words.

To allocate a block of size $2^k$, we find the first available block of size $2^j$, such that k ≤ j ≤ m. If j = k, then we are done. Otherwise, we recursively split the block in half until j = k. As we perform this splitting, each remaining half (known as a *buddy*) is placed on the appropriate free list. To free a block of size $2^k$, we continue coalescing with the free buddies. When we encounter an allocated buddy, we stop the coalescing.

A key fact about buddy systems is that, given the address and size of a block, it is easy to compute the address of its buddy; the addresses of a block and its buddy differ in exactly one bit position. For example, a block of size 32 bytes with address `xxx . . . x00000` has its buddy at address `xxx . . . x10000`

The major advantage of a buddy system allocator is its fast searching and coalescing. 

The major disadvantage is that the power-of-2 requirement on the block size can cause significant internal fragmentation. For this reason, buddy system allocators are not appropriate for general-purpose workloads. However, for certain application-specific workloads, where the block sizes are known in advance to be powers of 2, buddy system allocators have a certain appeal.

### Garbage Collection

A *garbage collector* is a dynamic storage allocator that automatically frees allocated blocks that are no longer needed by the program. Such blocks are known as *garbage*. The process of automatically reclaiming heap storage is known as *garbage collection*. In a system that supports garbage collection, applications explicitly allocate heap blocks but never explicitly free them. In the context of a C program, the application calls malloc but never calls free. Instead, the garbage collector periodically identifies the garbage blocks and makes the appropriate calls to free to place those blocks back on the free list.

#### Garbage Collector Basics

![image-20210406091241538](Asserts/image-20210406091241538.png)

A garbage collector views memory as a directed *reachability graph* of the form shown in Figure 9.49. The nodes of the graph are partitioned into a set of *root nodes* and a set of *heap nodes*. Each heap node corresponds to an allocated block in the heap. A directed edge p → q means that some location in block p points to some location in block q. Root nodes correspond to locations not in the heap that contain pointers into the heap. These locations can be registers, variables on the stack, or global variables in the read/write data area of virtual memory. The unreachable nodes correspond to garbage that can never be used again by the application. A node p is *reachable* if there exists a directed path from any root node to p. The role of a garbage collector is to maintain some representation of the reachability graph and periodically reclaim the unreachable nodes by freeing them and returning them to the free list.

#### Mark&Sweep Garbage Collectors

A Mark&Sweep garbage collector consists of a *mark phase*, which marks all reachable and allocated descendants of the root nodes, followed by a *sweep phase*, which frees each unmarked allocated block. Typically, one of the spare low-order bits in the block header is used to indicate whether a block is marked or not.

### Common Memory-Related Bugs in C Programs

#### Dereferencing Bad Pointers

```c
// you should pass in address of val instead of the content
scanf("%d", &val)
// dont do this
scanf("%d", val)
```

#### Reading Uninitialized Memory

```c
/* Return y = Ax */
int *matvec(int **A, int *x, int n) {
  int i, j;
  int *y = (int *)Malloc(n * sizeof(int));
  for(i=0;i<n;i++) {
        // you should initialize y[i] before use it
    y[i] = 0;
    for (j = 0; j < n; j++)
      y[i] += A[i][j] * x[j];
  }

  return y;
}

```

#### Stack Buffer Overflows

```c
void bufoverflow() {
  char buf[64];
   /* Here is the stack buffer overflow bug 
   	the gets function copies an arbitrary-length string to the buffer.
   */
  gets(buf);
  // use fget instead
  fgets(buf);
  return;
}
```

#### Assuming That Pointers and the Objects They Point to Are the Same Size

```c
/* Create an nxm array */

int **makeArray1(int n, int m) {
  int i;
  // should be sizeof(int *) here
  int **A = (int **)Malloc(n * sizeof(int));
  for (i = 0; i < n; i++)
    A[i] = (int *)Malloc(m * sizeof(int));
  return A;
}

```

#### Making Off-by-One Errors

```c
/* Create an nxm array */
int **makeArray2(int n, int m) {
  int i;
  int **A = (int **)Malloc(n * sizeof(int *));
  // should be < n here
  for (i = 0; i <= n; i++)
    A[i] = (int *)Malloc(m * sizeof(int));
  return A;
}

```

#### Referencing a Pointer Instead of the Object It Points To

```c
int *binheapDelete(int **binheap, int *size) {
  int *packet = binheap[0];
  binheap[0] = binheap[*size - 1];
  *size--; /* This should be (*size)-- */
  heapify(binheap, *size, 0);
  return(packet);
}
```

#### Misunderstanding Pointer Arithmetic

Arithmetic operations on pointers are performed in units that are the size of the objects they point to, which are not necessarily bytes. 

```c
int *search(int *p, int val) {
  while (*p && *p != val)
    p += sizeof(int); /* Should be p++ */
  return p;
}
```

#### Referencing Nonexistent Variables

When the function return, the address of val is invalid.

```c
int *stackref () {
  int val;
  return &val; 
}
```

#### Referencing Data in Free Heap Blocks

```c
int *heapref(int n, int m) {
  int i;
  int *x, *y;
  x = (int *)Malloc(n * sizeof(int));
  // Other calls to malloc and free go here free(x);
  y= (int *)Malloc(m * sizeof(int)); 
  for (i=0;i<m;i++)
    y[i] = x[i]++; /* Oops! x[i] is a word in a free block */
  return y;
}
```

#### Introducing Memory Leaks

```c
void leak(int n)
{
  int *x = (int *)Malloc(n * sizeof(int)); 
  // need to free the x before returning
  return; /* x is garbage at this point */ 
}
```

## System-Level I/O

*Input/output (I/O)* is the process of copying data between main memory and external devices such as disk drives, terminals, and networks. An input operation copies data from an I/O device to main memory, and an output operation copies data from memory to a device.

A Linux *file* is a sequence of m bytes. All I/O devices, such as networks, disks, and terminals, are modeled as files, and all input and output is performed by reading and writing the appropriate files. This elegant mapping of devices to files allows the Linux kernel to export a simple, low-level application interface, known as *Unix I/O*, that enables all input and output to be performed in a uniform and consistent way:

- *Opening files.* An application ask the kernel to *open* the corresponding file. The kernel returns a small nonnegative integer, called a *descriptor*, that identifies the file in all subsequent operations on the file. The kernel keeps track of all information about the open file. The application only keeps track of the descriptor.

  Each process created by a Linux shell begins life with three open files:

  - *standard input* (descriptor 0)
  - *standard output* (descriptor 1)
  - *standard error* (descriptor 2). 

  The header file `<unistd.h>` defines constants `STDIN_ FILENO`, `STDOUT_FILENO`, and `STDERR_FILENO`, which can be used instead of the explicit descriptor values.

- *Changing the current file position.* The kernel maintains a *file position* k, initially 0, for each open file. The file position is a byte offset from the beginning of a file. An application can set the current file position k explicitly by performing a `seek` operation.

- *Reading and writing files.* A *read* operation copies n bytes (n > 0) from a file to memory, starting at the current file position k and then incrementing k by n. 

  Given a file with a size of m bytes, performing a read operation when k ≥ m triggers a condition known as *end-of-file (EOF)*, which can be detected by the application. There is no explicit “EOF character” at the end of a file.

  Similarly, a *write* operation copies n bytes (n > 0) from memory to a file, starting at the current file position k and then updating k.

- *Closing files.* When an application has finished accessing a file, it asks the kernel to *close* the file. The kernel responds by freeing the data structures it created when the file was opened and restoring the descriptor to a pool of available descriptors. When a process terminates for any reason, the kernel closes all open files and frees their memory resources.

### Files

Each Linux file has a *type* that indicates its role in the system:

- A *regular file* contains arbitrary data. Application programs often distinguish between *text files*, which are regular files that contain only ASCII or Unicode characters, and *binary files*, which are everything else. To the kernel there is no difference between text and binary files.

  A Linux text file consists of a sequence of *text lines*, where each line is a sequence of characters  by a *newline* character (‘\n’). The newline character is the same as the ASCII line feed character (LF) and has a numeric value of `0x0a`.

- A *directory* is a file consisting of an array of *links*, where each link maps a *filename* to a file, which may be another directory. Each directory contains at least two entries: 

  - `.` (dot) is a link to the directory itself
  - `..` (dot-dot) is a link to the *parent directory* in the directory hierarchy. 

- A *socket* is a file that is used to communicate with another process across a network.

Other file types include *named pipes*, *symbolic links*, and *character* and *block devices*.

The Linux kernel organizes all files in a single *directory hierarchy* anchored by the *root directory* named `/` (slash). Each file in the system is a direct or indirect descendant of the root directory. Figure 10.1 shows a portion of the directory hierarchy on our Linux system.

![image-20210406141420307](Asserts/image-20210406141420307.png)

Locations in the directory hierarchy are specified by *pathnames*. A pathname is a string consisting of an optional slash followed by a sequence of filenames separated by slashes. Pathnames have two forms:

- An *absolute pathname* starts with a slash and denotes a path from the root node. For example, in Figure 10.1, the absolute pathname for `hello.c` is `/home/droh/hello.c`.
- A *relative pathname* starts with a filename and denotes a path from the current working directory. For example, in Figure 10.1, if `/home/droh` is the current working directory, then the relative pathname for `hello.c` is `./hello.c`.

### Opening and Closing Files

```c
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
// Returns: new file descriptor if OK, −1 on error
int open(char *filename, int flags, mode_t mode);
```

A process opens an existing file or creates a new file by calling the `open` function. The `open` function converts a filename to a file descriptor and returns the descriptor number. The descriptor returned is always the smallest descriptor that is not currently open in the process. The flags argument indicates how the process intends to access the file, we can join them by `O_WRONLY|O_APPEND`.

- `O_RDONLY`. Reading only 
- `O_WRONLY`. Writing only 
- `O_RDWR`. Reading and writing
- `O_CREAT`. If the file doesn’t exist, then create a *truncated* (empty) version of it.
- `O_TRUNC`. If the file already exists, then truncate it.
- `O_APPEND`. Before each write operation, set the file position to the end of the file.

![image-20210406154222589](Asserts/image-20210406154222589.png)

The mode argument specifies the access permission bits of new files. The symbolic names for these bits are shown in Figure 10.2. Each process has a `umask` that is set by calling the `umask` function. When a process creates a new file by calling the `open` function with some `mode` argument, then the access permission bits of the file are set to `mode & ~umask`. 

Finally, a process closes an open file by calling the `close` function. Closing a descriptor that is already closed is an error.

```c
#include <unistd.h>
// Returns: 0 if OK, −1 on error
int close(int fd);
```

### Reading and Writing Files

```c
#include <unistd.h>
// Returns: number of bytes read if OK, 0 on EOF, −1 on error
ssize_t read(int fd, void *buf, size_t n);
//Returns: number of bytes written if OK, −1 on error
ssize_t write(int fd, const void *buf, size_t n);

```

Applications can explicitly modify the current file position by calling the `lseek` function. In some situations, read and write transfer fewer bytes than the application requests. Such *short counts* do *not* indicate an error. They occur for a number of reasons:

- *Encountering EOF on reads.* 
- *Reading text lines from a terminal.*
- *Reading and writing network sockets.* 

### Robust Reading and Writing with the Rio Package

Rio provides two different kinds of functions:

- *Unbuffered input and output functions.* 

- *Buffered input functions.* These functions allow you to efficiently read text lines and binary data from a file whose contents are cached in an application-level buffer, similar to the one provided for standard I/O functions such as printf. 

#### Rio Unbuffered Input and Output Functions

![image-20210406163431460](Asserts/image-20210406163431460.png)

#### Rio Buffered Input Functions

![image-20210406184044560](Asserts/image-20210406184044560.png)

![image-20210406184107588](Asserts/image-20210406184107588.png)

![image-20210406184118027](Asserts/image-20210406184118027.png)

### Reading File Metadata

An application can retrieve information about a file (sometimes called the file’s

*metadata*) by calling the stat and fstat functions.

![image-20210406184333305](Asserts/image-20210406184333305.png)

The `stat` function takes as input a filename and fills in the members of a stat structure shown in Figure 10.9. The `fstat` function is similar, but it takes a file descriptor instead of a filename. 

```c
#include <unistd.h>
#include <sys/stat.h>
int stat(const char *filename, struct stat *buf);
int fstat(int fd, struct stat *buf);
```

### Reading Directory Contents

![image-20210406184626489](Asserts/image-20210406184626489.png)

### Sharing Files

The kernel represents open files using three related data structures:

- *Descriptor table.* Each process has its own separate *descriptor table* whose entries are indexed by the process’s open file descriptors. Each open descriptor entry points to an entry in the *file table.*

- *File table.* The set of open files is represented by a file table that is shared by all processes. Each file table entry consists of (for our purposes) the current file position, a *reference count* of the number of descriptor entries that currently point to it, and a pointer to an entry in the *v-node table*. Closing a descriptor decrements the reference count in the associated file table entry. The kernel will not delete the file table entry until its reference count is zero.
- *v-node table.* Like the file table, the v-node table is shared by all processes. Each entry contains most of the information in the stat structure, including the `st_mode` and `st_size` members.

![image-20210406184920559](Asserts/image-20210406184920559.png)

Figure 10.12 shows an example where descriptors 1 and 4 reference two different files through distinct open file table entries. This is the typical situation, where files are not shared and where each descriptor corresponds to a distinct file.

![image-20210406185124576](Asserts/image-20210406185124576.png)

Multiple descriptors can also reference the same file through different file table entries, as shown in Figure 10.13. This might happen, for example, if you were to call the open function twice with the same filename. The key idea is that each descriptor has its own distinct file position, so different reads on different descriptors can fetch data from different locations in the file.

![image-20210406185256307](Asserts/image-20210406185256307.png)

Suppose that before a call to `fork`, the parent process has the open files shown in Figure 10.12. Then Figure 10.14 shows the situation after the call to `fork`.

The child gets its own duplicate copy of the parent’s descriptor table. Parent and child share the same set of open file tables and thus share the same file position. An important consequence is that the parent and child must both close their descriptors before the kernel will delete the corresponding file table entry.

### I/O Redirection

The `dup2` function copies descriptor table entry `oldfd` to descriptor table entry `newfd`, overwriting the previous contents of descriptor table entry `newfd`. If `newfd` was already open, then `dup2` closes `newfd` before it copies `oldfd`.

```c
#include <unistd.h>
// Returns: nonnegative descriptor if OK, −1 on error
int dup2(int oldfd, int newfd);
```

### Standard I/O

The C language defines a set of higher-level input and output functions, called the *standard I/O library*, that provides programmers with a higher-level alternative to Unix I/O. The library (libc) provides functions for opening and closing files (fopen and fclose), reading and writing bytes (fread and fwrite), reading and writing strings (fgets and fputs), and sophisticated formatted I/O (scanf and printf).

The standard I/O library models an open file as a *stream*. To the programmer, a stream is a pointer to a structure of type `FILE`. Every ANSI C program begins with three open streams, stdin, stdout, and stderr, which correspond to standard input, standard output, and standard error, respectively:

```c
#include <stdio.h>
extern FILE *stdin;
extern FILE *stdout;
extern FILE *stderr;
/* Standard input (descriptor 0) */
/* Standard output (descriptor 1) */
/* Standard error (descriptor 2) */
```

## Network Programming

Every network application is based on the *client-server model*. With this model, an application consists of a *server* process and one or more *client* processes. A server manages some *resource*, and it provides some *service* for its clients by manipulating that resource. For example, a Web server manages a set of disk files that it retrieves and executes on behalf of clients. An FTP server manages a set of disk files that it stores and retrieves for clients. Similarly, an email server manages a spool file that it reads and updates for clients.

![image-20210412120731402](Asserts/image-20210412120731402.png)

The fundamental operation in the client-server model is the *transaction* (Figure 11.1). A client-server transaction consists of four steps:

1. When a client needs service, it initiates a transaction by sending a *request* to the server. For example, when a Web browser needs a file, it sends a request to a Web server.
2. The server receives the request, interprets it, and manipulates its resources in the appropriate way. For example, when a Web server receives a request from a browser, it reads a disk file.
3. The server sends a *response* to the client and then waits for the next request. For example, a Web server sends the file back to a client.
4. The client receives the response and manipulates it. For example, after a Web browser receives a page from the server, it displays it on the screen.

### Networks

To a host, a network is just another I/O device that serves as a source and sink for data, as shown in Figure 11.2.

![image-20210412121517310](Asserts/image-20210412121517310.png)

An adapter plugged into an expansion slot on the I/O bus provides the physical interface to the network. Data received from the network are copied from the adapter across the I/O and memory buses into memory, typically by a DMA transfer. Similarly, data can also be copied from memory to the network.

![image-20210412162550439](Asserts/image-20210412162550439.png)

An *Ethernet segment* consists of some wires (usually twisted pairs of wires) and a small box called a *hub*, as shown in Figure 11.3. Each Ethernet adapter has a globally unique 48-bit address that is stored in a nonvolatile memory on the adapter. A host can send a chunk of bits called a *frame* to any other host on the segment. Each frame includes some fixed number of *header* bits that identify the source and destination of the frame and the frame length, followed by a *payload* of data bits. Every host adapter sees the frame, but only the destination host actually reads it.

![image-20210412164050239](Asserts/image-20210412164050239.png)

Figure 11.6 shows an example internet with a pair of LANs and WANs connected by three routers.

![image-20210412164414729](Asserts/image-20210412164414729.png)

Figure 11.7 shows an example of how hosts and routers use the internet protocol to transfer data across incompatible LANs. The example internet consists of two LANs connected by a router. A client running on host A, which is attached to LAN1, sends a sequence of data bytes to a server running on host B, which is attached to LAN2. There are eight basic steps:

1. The client on host A invokes a system call that copies the data from the client’s virtual address space into a kernel buffer.

2. The protocol software on host A creates a LAN1 frame by appending an internet header and a LAN1 frame header to the data. The internet header is addressed to internet host B. The LAN1 frame header is addressed to the router. It then passes the frame to the adapter. Notice that the payload of the LAN1 frame is an internet packet, whose payload is the actual user data. This kind of *encapsulation* is one of the fundamental insights of internetworking.

3. The LAN1 adapter copies the frame to the network.

4. When the frame reaches the router, the router’s LAN1 adapter reads it from

   the wire and passes it to the protocol software.

5. The router fetches the destination internet address from the internet packet header and uses this as an index into a routing table to determine where to forward the packet, which in this case is LAN2. The router then strips off the old LAN1 frame header, prepends a new LAN2 frame header addressed to host B, and passes the resulting frame to the adapter.

6. The router’s LAN2 adapter copies the frame to the network.

7. When the frame reaches host B, its adapter reads the frame from the wire and

   passes it to the protocol software.

8. Finally, the protocol software on host B strips off the packet header and frame header. The protocol software will eventually copy the resulting data into the server’s virtual address space when the server invokes a system call that reads the data.

### The Global IP Internet

![image-20210412174055504](Asserts/image-20210412174055504.png)

Figure 11.8 shows the basic hardware and software organization of an Internet client-server application.

Each Internet host runs software that implements the *TCP/IP* protocol (*Transmission Control Protocol/Internet Protocol*), which is supported by almost every modern computer system. Internet clients and servers communicate using a mix of *sockets interface* functions and Unix I/O functions. The sockets functions are typically implemented as system calls that trap into the kernel and call various kernel-mode functions in TCP/IP.

TCP/IP is actually a family of protocols, each of which contributes different capabilities. 

- IP provides the basic naming scheme and a delivery mechanism that can send packets, known as *datagrams*, from one Internet host to any other host. The IP mechanism is unreliable in the sense that it makes no effort to recover if datagrams are lost or duplicated in the network. 
- UDP (Unreliable Datagram Protocol) extends IP slightly, so that datagrams can be transferred from process to process, rather than host to host. 
- TCP is a complex protocol that builds on IP to provide reliable full duplex (bidirectional) *connections* between processes.

### IP Addresses

An IP address is an unsigned 32-bit integer. Network programs store IP addresses in the *IP address structure* shown in Figure 11.9.

![image-20210412174858621](Asserts/image-20210412174858621.png)

Because Internet hosts can have different host byte orders, TCP/IP defines a uniform *network byte order* (big-endian byte order) for any integer data item, such as an IP address, that is carried across the network in a packet header. Addresses in IP address structures are always stored in (big-endian) network byte order, even if the host byte order is little-endian. Unix provides the following functions for converting between network and host byte order.

```c
#include <arpa/inet.h>
// Returns: value in network byte order
uint32_t htonl(uint32_t hostlong);
uint16_t htons(uint16_t hostshort);

// Returns: value in host byte order
uint32_t ntohl(uint32_t netlong);
uint16_t ntohs(unit16_t netshort);
```

Application programs can convert back and forth between IP addresses and dotted-decimal strings using the functions `inet_pton` and `inet_ntop`.

```c
#include <arpa/inet.h>
// Returns: 1 if OK, 0 if src is invalid dotted decimal, −1 on error
int inet_pton(AF_INET, const char *src, void *dst);

// Returns: pointer to a dotted-decimal string if OK, NULL on error
const char *inet_ntop(AF_INET, const void *src, char *dst,
                      socklen_t size);
```

### Socket

A *socket* is an end point of a connection. Each socket has a corresponding *socket address* that consists of an Internet address and a 16-bit integer *port* and is denoted by the notation `address:port`.

The port in the client’s socket address is assigned automatically by the kernel when the client makes a connection request and is known as an *ephemeral port*. However, the port in the server’s socket address is typically some *well-known port* that is permanently associated with the service. For example, Web servers typically use port 80. The mapping between well-known names and well-known ports is contained in a file called `/etc/services`.

#### The Sockets Interface

![image-20210412184542846](Asserts/image-20210412184542846.png)

The *sockets interface* is a set of functions that are used in conjunction with the Unix I/O functions to build network applications. It has been implemented on most modern systems, including all Unix variants as well as Windows and Macintosh systems. Figure 11.12 gives an overview of the sockets interface in the context of a typical client-server transaction. 

![image-20210412185500618](Asserts/image-20210412185500618.png)

Internet socket addresses are stored in 16-byte structures having the type `sockaddr_in`, shown in Figure 11.13. For Internet applications, the sin_family field is AF_INET, the sin_port field is a 16-bit port number, and the sin_addr field contains a 32-bit IP address. The IP address and port number are always stored in network (big-endian) byte order.

#### The socket Function

Clients and servers use the socket function to create a *socket descriptor*.

```c
#include <sys/types.h>
#include <sys/socket.h>
// Returns: nonnegative descriptor if OK, −1 on error
int socket(int domain, int type, int protocol);
```

If we wanted the socket to be the end point for a connection, then we could call socket with the following hardcoded arguments:

```c
    clientfd = Socket(AF_INET, SOCK_STREAM, 0);
```

where 

- `AF_INET` indicates that we are using 32-bit IP addresses
- `SOCK_STREAM` indicates that the socket will be an end point for a connection. 

However, the best practice is to use the `getaddrinfo` function (Section 11.4.7) to generate these parameters automatically, so that the code is protocol-independent. 

The `clientfd` descriptor returned by socket is only partially opened and cannot yet be used for reading and writing. 

#### The Connect Function

A client establishes a connection with a server by calling the `connect` function. The `connect` function attempts to establish an Internet connection with the server at socket address `addr`, where `addrlen` is `sizeof(sockaddr_in)`. The connect function blocks until either the connection is successfully established or an error occurs. If successful, the `clientfd` descriptor is now ready for reading and writing.

```c
#include <sys/socket.h>
// Returns: 0 if OK, −1 on error
int connect(int clientfd, const struct sockaddr *addr,
            socklen_t addrlen);
```

#### The bind Function

The `bind` function asks the kernel to associate the server’s socket address in `addr` with the socket descriptor `sockfd`. The `addrlen` argument is `sizeof(sockaddr_ in)`. As with socket and connect, the best practice is to use getaddrinfo to supply the arguments to bind.

```c
#include <sys/socket.h>
// Returns: 0 if OK, −1 on error
int bind(int sockfd, const struct sockaddr *addr,
```

#### The listen Function

A server calls the `listen` function to tell the kernel that the descriptor will be used by a server instead of a client. The `listen` function converts `sockfd` from an active socket to a *listening socket* that can accept connection requests from clients. The backlog argument is a hint about the number of outstanding connection requests that the kernel should queue up before it starts to refuse requests. We will typically set it to a large value, such as 1,024.

```c
#include <sys/socket.h>
Returns: 0 if OK, −1 on error
int listen(int sockfd, int backlog);
```

#### The accept Function

Servers wait for connection requests from clients by calling the `accept` function. The `accept` function waits for a connection request from a client to arrive on the listening descriptor `listenfd`, then fills in the client’s socket address in addr, and returns a *connected descriptor* that can be used to communicate with the client using Unix I/O functions.

```c
#include <sys/socket.h>
// Returns: nonnegative connected descriptor if OK, −1 on error
int accept(int listenfd, struct sockaddr *addr, int *addrlen);
```

The `listening` descriptor serves as an end point for client connection requests. It is typically created once and exists for the lifetime of the server. The `connected` descriptor is the end point of the connection that is established between the client and the server. It is created each time the server accepts a connection request and exists only as long as it takes the server to service a client.

![image-20210412211442842](Asserts/image-20210412211442842.png)

Figure 11.14 outlines the roles of the listening and connected descriptors. 

#### Host and Service Conversion

Linux provides some powerful functions, called `getaddrinfo` and `getnameinfo`, for converting back and forth between binary socket address structures and the string representations of hostnames, host addresses, service names, and port numbers. When used in conjunction with the sockets interface, they allow us to write network programs that are independent of any particular version of the IP protocol.

##### The getaddrinfo Function

![image-20210412212356718](Asserts/image-20210412212356718.png)

The `getaddrinfo` function converts string representations of hostnames, host addresses, service names, and port numbers into socket address structures. Given `host` and `service` (the two components of a socket address), `getaddrinfo` returns a result that points to a linked list of `addrinfo` structures, each of which points to a socket address structure that corresponds to `host` and `service` (Figure 11.15).

```c
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
int getaddrinfo(const char *host, const char *service,
                const struct addrinfo *hints,
                struct addrinfo **result);
// Returns: 0 if OK, nonzero error code on error
void freeaddrinfo(struct addrinfo *result);
//   Returns: error message
const char *gai_strerror(int errcode);
```

After a client calls `getaddrinfo`, it walks this list, trying each socket address in turn until the calls to `socket` and `connect` succeed and the connection is established. Similarly, a server tries each socket address on the list until the calls to `socket` and `bind` succeed and the descriptor is bound to a valid socket address. To avoid memory leaks, the application must eventually free the list by calling `freeaddrinfo`. If `getaddrinfo` returns a nonzero error code, the application can call `gai_strerror` to convert the code to a message string.

##### The getnameinfo Function

The `getnameinfo` function is the inverse of `getaddrinfo`. It converts a socket address structure to the corresponding host and service name strings.

```c
#include <sys/socket.h>
#include <netdb.h>
// Returns: 0 if OK, nonzero error code on error
int getnameinfo(const struct sockaddr *sa, socklen_t salen,
                char *host, size_t hostlen,
                char *service, size_t servlen, int flags);

```

### Web Servers

#### Web Content

![image-20210413235603431](Asserts/image-20210413235603431.png)

To Web clients and servers, *content* is a sequence of bytes with an associated *MIME (multipurpose internet mail extensions)* type. Figure 11.23 shows some common MIME types.

Web servers provide content to clients in two different ways:

- Fetch a disk file and return its contents to the client. The disk file is known as *static content* and the process of returning the file to the client is known as *serving static content*.
- Run an executable file and return its output to the client. The output produced by the executable at run time is known as *dynamic content*, and the process of running the program and returning its output to the client is known as *serving dynamic content*.

## Concurrent Programming

Modern operating systems provide three basic approaches for building concurrent programs:

- *Processes.* With this approach, each logical control flow is a process that is scheduled and maintained by the kernel. Since processes have separate virtual address spaces, flows that want to communicate with each other must use some kind of explicit *interprocess communication (IPC)* mechanism.
- *I/O multiplexing.*This is a form of concurrent programming where applications explicitly schedule their own logical flows in the context of a single process. Logical flows are modeled as state machines that the main program explicitly transitions from state to state as a result of data arriving on file descriptors. Since the program is a single process, all flows share the same address space.
- *Threads.* Threads are logical flows that run in the context of a single process and are scheduled by the kernel. You can think of threads as a hybrid of the other two approaches, scheduled by the kernel like process flows and sharing the same virtual address space like I/O multiplexing flows.

### Concurrent Programming with Processes

The simplest way to build a concurrent program is with processes, using familiar functions such as `fork`, `exec`, and `waitpid`. For example, a natural approach for building a concurrent server is to accept client connection requests in the parent and then create a new child process to service each new client.

![image-20210415115034612](Asserts/image-20210415115034612.png)

Suppose we have two clients and a server that is listening for connection requests on a listening descriptor 3. Now suppose that the server accepts a connection request from client 1 and returns a connected descriptor 4, as shown in Figure 12.1. After accepting the connection request, the server forks a child, which gets a complete copy of the server’s descriptor table. The child closes its copy of listening descriptor 3, and the parent closes its copy of connected descriptor 4, since they are no longer needed. This gives us the situation shown in Figure 12.2, where the child process is busy servicing the client.

![image-20210415115200333](Asserts/image-20210415115200333.png)

> _**Note**_: Since the connected descriptors in the parent and child each point to the same file table entry, it is crucial for the parent to close its copy of the connected descriptor. Otherwise, the file table entry for connected descriptor 4 will never be released, and the resulting memory leak will eventually consume the available memory and crash the system.

#### A Concurrent Server Based on Processes

```c
/* 
 * echoserverp.c - A concurrent echo server based on processes
 */
/* $begin echoserverpmain */
#include "csapp.h"
void echo(int connfd);

void sigchld_handler(int sig) //line:conc:echoserverp:handlerstart
{
    while (waitpid(-1, 0, WNOHANG) > 0)
	;
    return;
} //line:conc:echoserverp:handlerend

int main(int argc, char **argv) 
{
    int listenfd, connfd, port;
    socklen_t clientlen=sizeof(struct sockaddr_in);
    struct sockaddr_in clientaddr;

    if (argc != 2) {
	fprintf(stderr, "usage: %s <port>\n", argv[0]);
	exit(0);
    }
    port = atoi(argv[1]);

    Signal(SIGCHLD, sigchld_handler);
    listenfd = Open_listenfd(port);
    while (1) {
	connfd = Accept(listenfd, (SA *) &clientaddr, &clientlen);
	if (Fork() == 0) { 
	    Close(listenfd); /* Child closes its listening socket */
	    echo(connfd);    /* Child services client */ //line:conc:echoserverp:echofun
	    Close(connfd);   /* Child closes connection with client */ //line:conc:echoserverp:childclose
	    exit(0);         /* Child exits */
	}
	Close(connfd); /* Parent closes connected socket (important!) */ //line:conc:echoserverp:parentclose
    }
}
/* $end echoserverpmain */
```

Above code is for a concurrent echo server based on processes. There are several important points to make about this server:

- Servers typically run for long periods of time, so we must include a `SIGCHLD` handler that reaps zombie children. Since `SIGCHLD` signals are blocked while the `SIGCHLD` handler is executing, and since Linux signals are not queued, the `SIGCHLD` handler must be prepared to reap multiple zombie children(in a while loop).
- The parent and the child must close their respective copies of `connfd`to avoid a memory leak.
- Finally, because of the reference count in the socket’s file table entry, the connection to the client will not be terminated until both the parent’s and child’s copies of `connfd` are closed.

#### Pros and Cons of Processes

Processes have a clean model for sharing state information between parents and children: file tables are shared and user address spaces are not. Having separate address spaces for processes is both an advantage and a disadvantage. 

- It is impossible for one process to accidentally overwrite the virtual memory of another process, which eliminates a lot of confusing failures—an obvious advantage.
- On the other hand, separate address spaces make it more difficult for processes to share state information. To share information, they must use explicit IPC (interprocess communications) mechanisms. 

Another disadvantage of process-based designs is that they tend to be slower because the overhead for process control and IPC is high.

### Concurrent Programming with I/O Multiplexing

The basic idea is to use the `select` function to ask the kernel to suspend the process, returning control to the application only after one or more I/O events have occurred, as in the following examples:

- Return when any descriptor in the set {0, 4} is ready for reading.
- Return when any descriptor in the set {1, 2, 7} is ready for writing.
- Time out if 152.13 seconds have elapsed waiting for an I/O event to occur.

`Select` is a complicated function with many different usage scenarios. We will only discuss the first scenario: waiting for a set of descriptors to be ready for reading. 

```c
#include <sys/select.h>
// Returns: nonzero count of ready descriptors, −1 on error
int select(int n, fd_set *fdset, NULL, NULL, NULL);

// Macros for manipulating descriptor sets
FD_ZERO(fd_set *fdset);   /* Clear all bits in fdset */
FD_CLR(int fd, fd_set *fdset); /* Clear bit fd in fdset */
FD_SET(int fd, fd_set *fdset);		/* Turn on bit fd in fdset */
FD_ISSET(int fd, fd_set *fdset);  /* Is bit fd in fdset on? */
```

The `select` function manipulates sets of type `fd_set`, which are known as *descriptor sets*. Logically, we think of a descriptor set as a bit vector (introduced in Section 2.1) of size n. Each bit corresponds to a descriptor.  A descriptor k is a member of the descriptor set if and only if $b_k = 1$. 

You are only allowed to do three things with descriptor sets: 

1. allocate them
2. assign one variable of this type to another
3. modify and inspect them using the `FD_ZERO`, `FD_SET`, `FD_CLR`, and `FD_ISSET` macros.

For our purposes, the `select` function takes two inputs: 

- a descriptor set (fdset) called the *read set*
- the cardinality (n) of the read set (actually the maximum cardinality of any descriptor set). 

The `select` function blocks until at least one descriptor in the read set is ready for reading. A descriptor k is *ready for reading* if and only if a request to read 1 byte from that descriptor would not block. As a side effect, `select` modifies the `fd_set` pointed to by argument `fdset` to indicate a subset of the read set called the *ready set*, consisting of the descriptors in the read set that are ready for reading. The value returned by the function indicates the cardinality of the ready set. Note that because of the side effect, we must update the read set every time `select` is called.

```c
/*  
 * open_listenfd - Open and return a listening socket on port. This
 *     function is reentrant and protocol-independent.
 *
 *     On error, returns: 
 *       -2 for getaddrinfo error
 *       -1 with errno set for other errors.
 */
/* $begin open_listenfd */
int open_listenfd(char *port) 
{
  struct addrinfo hints, *listp, *p;
  int listenfd, rc, optval=1;

  /* Get a list of potential server addresses */
  memset(&hints, 0, sizeof(struct addrinfo));
  hints.ai_socktype = SOCK_STREAM;             /* Accept connections */
  hints.ai_flags = AI_PASSIVE | AI_ADDRCONFIG; /* ... on any IP address */
  hints.ai_flags |= AI_NUMERICSERV;            /* ... using port number */
  if ((rc = getaddrinfo(NULL, port, &hints, &listp)) != 0) {
    fprintf(stderr, "getaddrinfo failed (port %s): %s\n", port, gai_strerror(rc));
    return -2;
  }

  /* Walk the list for one that we can bind to */
  for (p = listp; p; p = p->ai_next) {
    /* Create a socket descriptor */
    if ((listenfd = socket(p->ai_family, p->ai_socktype, p->ai_protocol)) < 0) 
      continue;  /* Socket failed, try the next */

    /* Eliminates "Address already in use" error from bind */
    setsockopt(listenfd, SOL_SOCKET, SO_REUSEADDR,    //line:netp:csapp:setsockopt
               (const void *)&optval , sizeof(int));

    /* Bind the descriptor to the address */
    if (bind(listenfd, p->ai_addr, p->ai_addrlen) == 0)
      break; /* Success */
    if (close(listenfd) < 0) { /* Bind failed, try the next */
      fprintf(stderr, "open_listenfd close failed: %s\n", strerror(errno));
      return -1;
    }
  }


  /* Clean up */
  freeaddrinfo(listp);
  if (!p) /* No address worked */
    return -1;

  /* Make it a listening socket ready to accept connection requests */
  if (listen(listenfd, LISTENQ) < 0) {
    close(listenfd);
    return -1;
  }
  return listenfd;
}
/* $end open_listenfd */
```

Code below how we might use `select` to implement an iterative echo server that also accepts user commands on the standard input. 

```c
/* $begin select */
#include "csapp.h"
void echo(int connfd);
void command(void);

int main(int argc, char **argv) 
{
  int listenfd, connfd;
  socklen_t clientlen;
  struct sockaddr_storage clientaddr;
  fd_set read_set, ready_set;

  if (argc != 2) {
    fprintf(stderr, "usage: %s <port>\n", argv[0]);
    exit(0);
  }
  listenfd = Open_listenfd(argv[1]);  //line:conc:select:openlistenfd

  FD_ZERO(&read_set);              /* Clear read set */ //line:conc:select:clearreadset
  FD_SET(STDIN_FILENO, &read_set); /* Add stdin to read set */ //line:conc:select:addstdin
  FD_SET(listenfd, &read_set);     /* Add listenfd to read set */ //line:conc:select:addlistenfd

  while (1) {
    ready_set = read_set;
    Select(listenfd+1, &ready_set, NULL, NULL, NULL); //line:conc:select:select
    if (FD_ISSET(STDIN_FILENO, &ready_set)) //line:conc:select:stdinready
      command(); /* Read command line from stdin */
    if (FD_ISSET(listenfd, &ready_set)) { //line:conc:select:listenfdready
      clientlen = sizeof(struct sockaddr_storage); 
      connfd = Accept(listenfd, (SA *)&clientaddr, &clientlen);
      echo(connfd); /* Echo client input until EOF */
      Close(connfd);
    }
  }
}

void command(void) {
  char buf[MAXLINE];
  if (!Fgets(buf, MAXLINE, stdin))
    exit(0); /* EOF */
  printf("%s", buf); /* Process the input command */
}
/* $end select */
```

We begin by using the `open_listenfd` function to open a listening descriptor

```c
listenfd = Open_listenfd(argv[1]); 
```

and then using `FD_ZERO` to create an empty read set and define the read set to consist of descriptor 0 (standard input) and descriptor 3 (the listening descriptor), respectively:

```c
    FD_ZERO(&read_set);              /* Clear read set */ //line:conc:select:clearreadset
    FD_SET(STDIN_FILENO, &read_set); /* Add stdin to read set */ //line:conc:select:addstdin
    FD_SET(listenfd, &read_set);     /* Add listenfd to read set */ 
```

![image-20210415163428250](Asserts/image-20210415163428250.png)



At this point, we begin the typical server loop. But instead of waiting for a connection request by calling the `accept` function, we call the `select` function, which blocks until either the listening descriptor or standard input is ready for reading. 

```c
Select(listenfd+1, &ready_set, NULL, NULL, NULL); 
```

For example, here is the value of `ready_set` that `select` would return if the user hit the enter key, thus causing the standard input descriptor to become ready for reading:

![image-20210415173727657](Asserts/image-20210415173727657.png)

Once `select` returns, we use the `FD_ISSET` macro to determine which descriptors are ready for reading. If standard input is ready, we call the command function, which reads, parses, and responds to the command before returning to the main routine. 

```c
if (FD_ISSET(STDIN_FILENO, &ready_set)) //line:conc:select:stdinready
  command(); /* Read command line from stdin */
```

If the listening descriptor is ready we call `accept` to get a connected descriptor 

and then call the `echo` function, which echoes each line from the client until the client closes its end of the connection.

```c
/*
 * echo - read and echo text lines until client closes connection
 */
/* $begin echo */
#include "csapp.h"

void echo(int connfd) 
{
    size_t n; 
    char buf[MAXLINE]; 
    rio_t rio;

    Rio_readinitb(&rio, connfd);
    while((n = Rio_readlineb(&rio, buf, MAXLINE)) != 0) { //line:netp:echo:eof
	printf("server received %d bytes\n", (int)n);
	Rio_writen(connfd, buf, n);
    }
}
/* $end echo */
```

```c
if (FD_ISSET(listenfd, &ready_set)) { //line:conc:select:listenfdready
  clientlen = sizeof(struct sockaddr_storage); 
  connfd = Accept(listenfd, (SA *)&clientaddr, &clientlen);
  echo(connfd); /* Echo client input until EOF */
  Close(connfd);
}
```

While this program is a good example of using select, it still leaves something to be desired. The problem is that once it connects to a client, it continues echoing input lines until the client closes its end of the connection. Thus, if you type a command to standard input, you will not get a response until the server is finished with the client. A better approach would be to multiplex at a finer granularity, echoing (at most) one text line each time through the server loop.

#### A Concurrent Event-Driven Server Based on I/O Multiplexing

I/O multiplexing can be used as the basis for concurrent *event-driven* programs, where flows make progress as a result of certain events. The general idea is to model logical flows as state machines. 

![image-20210415175251761](Asserts/image-20210415175251761.png)

For each new client k, a concurrent server based on I/O multiplexing creates a new state machine $s_k$ and associates it with connected descriptor $d_k$. As shown in Figure 12.7, each state machine $s_k$ has one state (“waiting for descriptor $d_k$ to be ready for reading”), one input event (“descriptor $d_k$ is ready for reading”), and one transition (“read a text line from descriptor $d_k$”).

The server uses the I/O multiplexing, courtesy of the `select` function, to detect the occurrence of input events. As each connected descriptor becomes ready for reading, the server executes the transition for the corresponding state machine—in this case, reading and echoing a text line from the descriptor.

```c
/* 
 * echoservers.c - A concurrent echo server based on select
 */
/* $begin echoserversmain */
#include "csapp.h"

typedef struct { /* represents a pool of connected descriptors */ //line:conc:echoservers:beginpool
  int maxfd;        /* largest descriptor in read_set */   
  fd_set read_set;  /* set of all active descriptors */
  fd_set ready_set; /* subset of descriptors ready for reading  */
  int nready;       /* number of ready descriptors from select */   
  int maxi;         /* highwater index into client array */
  int clientfd[FD_SETSIZE];    /* set of active descriptors */
  rio_t clientrio[FD_SETSIZE]; /* set of active read buffers */
} pool; //line:conc:echoservers:endpool
/* $end echoserversmain */

void init_pool(int listenfd, pool *p);
void add_client(int connfd, pool *p);
void check_clients(pool *p);
/* $begin echoserversmain */

int byte_cnt = 0; /* counts total bytes received by server */

int main(int argc, char **argv)
{
  int listenfd, connfd, port; 
  socklen_t clientlen = sizeof(struct sockaddr_in);
  struct sockaddr_in clientaddr;
  static pool pool; 

  if (argc != 2) {
    fprintf(stderr, "usage: %s <port>\n", argv[0]);
    exit(0);
  }
  port = atoi(argv[1]);

  listenfd = Open_listenfd(port);
  init_pool(listenfd, &pool); //line:conc:echoservers:initpool
  while (1) {
    /* Wait for listening/connected descriptor(s) to become ready */
    pool.ready_set = pool.read_set;
    pool.nready = Select(pool.maxfd+1, &pool.ready_set, NULL, NULL, NULL);

    /* If listening descriptor ready, add new client to pool */
    if (FD_ISSET(listenfd, &pool.ready_set)) { //line:conc:echoservers:listenfdready
      connfd = Accept(listenfd, (SA *)&clientaddr, &clientlen); //line:conc:echoservers:accept
      add_client(connfd, &pool); //line:conc:echoservers:addclient
    }

    /* Echo a text line from each ready connected descriptor */ 
    check_clients(&pool); //line:conc:echoservers:checkclients
  }
}
/* $end echoserversmain */
```

Code above shows a concurrent event-driven server based on I/O multiplexing. The set of active clients is maintained in a pool structure. 

```c
typedef struct { /* represents a pool of connected descriptors */ //line:conc:echoservers:beginpool
  int maxfd;        /* largest descriptor in read_set */   
  fd_set read_set;  /* set of all active descriptors */
  fd_set ready_set; /* subset of descriptors ready for reading  */
  int nready;       /* number of ready descriptors from select */   
  int maxi;         /* highwater index into client array */
  int clientfd[FD_SETSIZE];    /* set of active descriptors */
  rio_t clientrio[FD_SETSIZE]; /* set of active read buffers */
} pool; //line:conc:echoservers:endpool
```

After initializing the pool by calling `init_pool`, 

```c
  init_pool(listenfd, &pool); //line:conc:echoservers:initpool
```

the server enters an infinite loop. 

```c
while (1) {
  /* Wait for listening/connected descriptor(s) to become ready */
  pool.ready_set = pool.read_set;
  pool.nready = Select(pool.maxfd+1, &pool.ready_set, NULL, NULL, NULL);

  /* If listening descriptor ready, add new client to pool */
  if (FD_ISSET(listenfd, &pool.ready_set)) { //line:conc:echoservers:listenfdready
    connfd = Accept(listenfd, (SA *)&clientaddr, &clientlen); //line:conc:echoservers:accept
    add_client(connfd, &pool); //line:conc:echoservers:addclient
  }

  /* Echo a text line from each ready connected descriptor */ 
  check_clients(&pool); //line:conc:echoservers:checkclients
}
```

During each iteration of this loop, the server calls the select function to detect two different kinds of input events: 

1. a connection request arriving from a new client
2. a connected descriptor for an existing client being ready for reading. 

When a connection request arrives, the server opens the connection and calls the `add_client` function to add the client to the pool. 

```c
/* If listening descriptor ready, add new client to pool */
if (FD_ISSET(listenfd, &pool.ready_set)) { //line:conc:echoservers:listenfdready
  connfd = Accept(listenfd, (SA *)&clientaddr, &clientlen); //line:conc:echoservers:accept
  add_client(connfd, &pool); //line:conc:echoservers:addclient
}
```

Finally, the server calls the `check_clients` function to echo a single text line from each ready connected descriptor.

```c
/* Echo a text line from each ready connected descriptor */ 
check_clients(&pool); //line:conc:echoservers:checkclients
```

The `init_pool` function initializes the client pool. The `clientfd` array represents a set of connected descriptors, with the integer −1 denoting an available slot. Initially, the set of connected descriptors is empty, and the listening descriptor is the only descriptor in the `select` read set.

```c
/* $begin init_pool */
void init_pool(int listenfd, pool *p) 
{
  /* Initially, there are no connected descriptors */
  int i;
  p->maxi = -1;                   //line:conc:echoservers:beginempty
  for (i=0; i< FD_SETSIZE; i++)  
    p->clientfd[i] = -1;        //line:conc:echoservers:endempty

  /* Initially, listenfd is only member of select read set */
  p->maxfd = listenfd;            //line:conc:echoservers:begininit
  FD_ZERO(&p->read_set);
  FD_SET(listenfd, &p->read_set); //line:conc:echoservers:endinit
}
/* $end init_pool */
```

The `add_client` function adds a new client to the pool of active clients. After finding an empty slot in the `clientfd` array, the server adds the connected descriptor to the array and initializes a corresponding Rio read buffer so that we can call `rio_readlineb` on the descriptor. We then add the connected descriptor to the select read set, and we update some global properties of the pool. The `maxfd` variable keeps track of the largest file descriptor for select. The maxi variable keeps track of the largest index into the `clientfd` array so that the `check_clients` function does not have to search the entire array.

```c
/* $begin add_client */
void add_client(int connfd, pool *p) 
{
  int i;
  p->nready--;
  for (i = 0; i < FD_SETSIZE; i++)  /* Find an available slot */
    if (p->clientfd[i] < 0) { 
      /* Add connected descriptor to the pool */
      p->clientfd[i] = connfd;                 //line:conc:echoservers:beginaddclient
      Rio_readinitb(&p->clientrio[i], connfd); //line:conc:echoservers:endaddclient

      /* Add the descriptor to descriptor set */
      FD_SET(connfd, &p->read_set); //line:conc:echoservers:addconnfd

      /* Update max descriptor and pool highwater mark */
      if (connfd > p->maxfd) //line:conc:echoservers:beginmaxfd
        p->maxfd = connfd; //line:conc:echoservers:endmaxfd
      if (i > p->maxi)       //line:conc:echoservers:beginmaxi
        p->maxi = i;       //line:conc:echoservers:endmaxi
      break;
    }
  if (i == FD_SETSIZE) /* Couldn't find an empty slot */
    app_error("add_client error: Too many clients");
}
/* $end add_client */
```

The `check_clients` function echoes a text line from each ready connected descriptor. If we are successful in reading a text line from the descriptor, then we echo that line back to the client. Notice we are maintaining a cumulative count of total bytes received from all clients. If we detect EOF because the client has closed its end of the connection, then we close our end of the connection and remove the descriptor from the pool.

```c
void check_clients(pool *p) 
{
  int i, connfd, n;
  char buf[MAXLINE]; 
  rio_t rio;

  for (i = 0; (i <= p->maxi) && (p->nready > 0); i++) {
    connfd = p->clientfd[i];
    rio = p->clientrio[i];

    /* If the descriptor is ready, echo a text line from it */
    if ((connfd > 0) && (FD_ISSET(connfd, &p->ready_set))) { 
      p->nready--;
      if ((n = Rio_readlineb(&rio, buf, MAXLINE)) != 0) {
        byte_cnt += n; //line:conc:echoservers:beginecho
        printf("Server received %d (%d total) bytes on fd %d\n", 
               n, byte_cnt, connfd);
        Rio_writen(connfd, buf, n); //line:conc:echoservers:endecho
      }

      /* EOF detected, remove descriptor from pool */
      else { 
        Close(connfd); //line:conc:echoservers:closeconnfd
        FD_CLR(connfd, &p->read_set); //line:conc:echoservers:beginremove
        p->clientfd[i] = -1;          //line:conc:echoservers:endremove
      }
    }
  }
}
/* $end check_clients */
```

In terms of the finite state model in Figure 12.7, the `select` function detects input events, and the `add_client` function creates a new logical flow (state machine). The `check_clients` function performs state transitions by echoing input lines, and it also deletes the state machine when the client has finished sending text lines.

#### Pros and Cons of I/O Multiplexing

One advantage is that event-driven designs give programmers more control over the behavior of their programs than process-based designs. For example, we can imagine writing an event-driven concurrent server that gives preferred service to some clients, which would be difficult for a concurrent server based on processes.

Another advantage is that an event-driven server based on I/O multiplexing runs in the context of a single process, and thus every logical flow has access to the entire address space of the process. This makes it easy to share data between flows. A related advantage of running as a single process is that you can debug your concurrent server as you would any sequential program, using a familiar debugging tool such as gdb. Finally, event-driven designs are often significantly more efficient than process-based designs because they do not require a process context switch to schedule a new flow.

A significant disadvantage of event-driven designs is coding complexity. Our event-driven concurrent echo server requires three times more code than the process-based server. Unfortunately, the complexity increases as the granularity of the concurrency decreases. By *granularity*, we mean the number of instructions that each logical flow executes per time slice. For instance, in our example concurrent server, the granularity of concurrency is the number of instructions required to read an entire text line. As long as some logical flow is busy reading a text line, no other logical flow can make progress. This is fine for our example, but it makes our event-driven server vulnerable to a malicious client that sends only a partial text line and then halts. Modifying an event-driven server to handle partial text lines is a nontrivial task, but it is handled cleanly and automatically by a process-based design. Another significant disadvantage of event-based designs is that they cannot fully utilize multi-core processors.

Despite the disadvantages, modern high-performance servers such as Node.js, nginx, and Tornado use event-driven programming based on I/O multiplexing, mainly because of the significant performance advantage compared to processes and threads.

### Concurrent Programming with Threads

A *thread* is a logical flow that runs in the context of a process. Modern systems also allow us to write programs that have multiple threads running concurrently in a single process. The threads are scheduled automatically by the kernel. Each thread has its own *thread context*, including a unique integer *thread ID (TID),* stack, stack pointer, program counter, general-purpose registers, and condition codes. All threads running in a process share the entire virtual address space of that process.

Logical flows based on threads combine qualities of flows based on processes and I/O multiplexing. Like processes, threads are scheduled automatically by the kernel and are known to the kernel by an integer ID. Like flows based on I/O multiplexing, multiple threads run in the context of a single process, and thus they share the entire contents of the process virtual address space, including its code, data, heap, shared libraries, and open files.

#### Thread Execution Model

The execution model for multiple threads is similar in some ways to the execution model for multiple processes. Consider the example in Figure 12.12. Each process begins life as a single thread called the *main thread*. At some point, the main thread creates a *peer thread*, and from this point in time the two threads run concurrently. Eventually, control passes to the peer thread via a context switch, either because the main thread executes a slow system call such as read or sleep or because it is interrupted by the system’s interval timer. The peer thread executes for a while before control passes back to the main thread, and so on.

![image-20210415183412844](Asserts/image-20210415183412844.png)

Thread execution differs from processes in some important ways. 

- Because a thread context is much smaller than a process context, a thread context switch is faster than a process context switch. 
- Thread is not organized in a rigid parent-child hierarchy. The threads associated with a process form a *pool* of peers, independent of which threads were created by which other threads. The main thread is distinguished from other threads only in the sense that it is always the first thread to run in the process. The main impact of this notion of a pool of peers is that a thread can kill any of its peers or wait for any of its peers to terminate. Further, each peer can read and write the same shared data.

#### Posix Threads

Posix threads (Pthreads) is a standard interface for manipulating threads from C programs. Below shows a simple Pthreads program. The main thread creates a new peer thread by calling the `pthread_create` function. When the call to `pthread_create` returns, the main thread and the newly created peer thread are running concurrently, and `tid` contains the ID of the new thread. The main thread waits for the peer thread to terminate with the call to `pthread_join` . Finally, the main thread calls exit, which terminates all threads (in this case, just the main thread) currently running in the process.

```c
/* 
 * hello.c - Pthreads "hello, world" program 
 */
/* $begin hello */
#include "csapp.h"
void *thread(void *vargp);                    //line:conc:hello:prototype

int main()                                    //line:conc:hello:main
{
  pthread_t tid;                            //line:conc:hello:tid
  Pthread_create(&tid, NULL, thread, NULL); //line:conc:hello:create
  Pthread_join(tid, NULL);                  //line:conc:hello:join
  exit(0);                                  //line:conc:hello:exit
}

void *thread(void *vargp) /* thread routine */  //line:conc:hello:beginthread
{
  printf("Hello, world!\n");                 
  return NULL;                               //line:conc:hello:return
}                                              //line:conc:hello:endthread
/* $end hello */
```

##### Creating Threads

Threads create other threads by calling the `pthread_create` function. The `pthread_create` function creates a new thread and runs the *thread routine* `f` in the context of the new thread and with an input argument of `arg`. The `attr` argument can be used to change the default attributes of the newly created thread. When `pthread_create` returns, argument `tid` contains the ID of the newly created thread. The new thread can determine its own thread ID by calling the `pthread_self` function.

```c
#include <pthread.h>
typedef void *(func)(void *);
// Returns: 0 if OK, nonzero on error
int pthread_create(pthread_t *tid, pthread_attr_t *attr,
                   func *f, void *arg);
// Returns: thread ID of caller
pthread_t pthread_self(void);
```

##### Terminating Threads

A thread terminates in one of the following ways:

- The thread terminates *implicitly* when its top-level thread routine returns.

- The thread terminates *explicitly* by calling the `pthread_exit` function. If the main thread calls `pthread_exit`, it waits for all other peer threads to terminate and then terminates the main thread and the entire process with a return value of `thread_return`.
- Some peer thread calls the Linux exit function, which terminates the process and all threads associated with the process.
- Another peer thread terminates the current thread by calling the `pthread_cancel` function with the ID of the current thread.

```c
#include <pthread.h>
void pthread_exit(void *thread_return);
// Returns: 0 if OK, nonzero on error
int pthread_cancel(pthread_t tid);
```

##### Reaping Terminated Threads

Threads wait for other threads to terminate by calling the `pthread_join` function. The `pthread_join` function blocks until thread `tid` terminates, assigns the generic (void *) pointer returned by the thread routine to the location pointed to by `thread_return`, and then *reaps* any memory resources held by the terminated thread. Notice that, unlike the Linux wait function, the `pthread_join` function can only wait for a specific thread to terminate. There is no way to instruct `pthread_join` to wait for an *arbitrary* thread to terminate. This can complicate our code by forcing us to use other, less intuitive mechanisms to detect process termination. 

```c
#include <pthread.h>
// Returns: 0 if OK, nonzero on error
int pthread_join(pthread_t tid, void **thread_return);
```

##### Detaching Threads

At any point in time, a thread is *joinable* or *detached*. A joinable thread can be reaped and killed by other threads. Its memory resources (such as the stack) are not freed until it is reaped by another thread. In contrast, a detached thread cannot be reaped or killed by other threads. Its memory resources are freed automatically by the system when it terminates.

By default, threads are created joinable. In order to avoid memory leaks, each joinable thread should be either explicitly reaped by another thread or detached by a call to the `pthread_detach` function. The `pthread_detach` function detaches the joinable thread `tid`. Threads can detach themselves by calling `pthread_detach` with an argument of `pthread_ self()`.

Although some of our examples will use joinable threads, there are good reasons to use detached threads in real programs. For example, a high-performance Web server might create a new peer thread each time it receives a connection request from a Web browser. Since each connection is handled independently by a separate thread, it is unnecessary—and indeed undesirable—for the server to explicitly wait for each peer thread to terminate. In this case, each peer thread should detach itself before it begins processing the request so that its memory resources can be reclaimed after it terminates.

```c
#include <pthread.h>
// Returns: 0 if OK, nonzero on error
int pthread_detach(pthread_t tid);
```

##### Initializing Threads

The `pthread_once` function allows you to initialize the state associated with a thread routine. The `once_control` variable is a global or static variable that is always initialized to `PTHREAD_ONCE_INIT`. The first time you call `pthread_once` with an argument of `once_control`, it invokes `init_routine`, which is a function with no input arguments that returns nothing. Subsequent calls to `pthread_once` with the same `once_control` variable do nothing. The `pthread_once` function is useful whenever you need to dynamically initialize global variables that are shared by multiple threads. 

```c
#include <pthread.h>
pthread_once_t once_control = PTHREAD_ONCE_INIT;
// Always returns 0
int pthread_once(pthread_once_t *once_control,
                 void (*init_routine)(void));
```

##### A Concurrent Server Based on Threads

```c
/* 
 * echoservert.c - A concurrent echo server using threads
 */
/* $begin echoservertmain */
#include "csapp.h"

void echo(int connfd);
void *thread(void *vargp);

int main(int argc, char **argv) 
{
  int listenfd, *connfdp, port;
  socklen_t clientlen=sizeof(struct sockaddr_in);
  struct sockaddr_in clientaddr;
  pthread_t tid; 

  if (argc != 2) {
    fprintf(stderr, "usage: %s <port>\n", argv[0]);
    exit(0);
  }
  port = atoi(argv[1]);

  listenfd = Open_listenfd(port);
  while (1) {
    connfdp = Malloc(sizeof(int)); //line:conc:echoservert:beginmalloc
    *connfdp = Accept(listenfd, (SA *) &clientaddr, &clientlen); //line:conc:echoservert:endmalloc
    Pthread_create(&tid, NULL, thread, connfdp);
  }
}

/* thread routine */
void *thread(void *vargp) 
{  
  int connfd = *((int *)vargp);
  Pthread_detach(pthread_self()); //line:conc:echoservert:detach
  Free(vargp);                    //line:conc:echoservert:free
  echo(connfd);
  Close(connfd);
  return NULL;
}
/* $end echoservertmain */
```

### Shared Variables in Threaded Programs

#### Threads Memory Model

A pool of concurrent threads runs in the context of a process. Each thread has its own separate *thread context*, which includes a thread ID, stack, stack pointer, program counter, condition codes, and general-purpose register values. Each thread shares the rest of the process context with the other threads. This includes the entire user virtual address space, which consists of read-only text (code), read/write data, the heap, and any shared library code and data areas. The threads also share the same set of open files.

#### Mapping Variables to Memory

Variables in threaded C programs are mapped to virtual memory according to their storage classes:

- *Global variables*. A *global variable* is any variable declared outside of a function. At run time, the read/write area of virtual memory contains exactly one instance of each global variable that can be referenced by any thread.
- *Local automatic variables*. A *local automatic variable* is one that is declared inside a function without the static attribute. At run time, each thread’s stack contains its own instances of any local automatic variables. This is true even if multiple threads execute the same thread routine.
- *Local static variables*. A *local static variable* is one that is declared inside a function with the static attribute. As with global variables, the read/write area of virtual memory contains exactly one instance of each local static variable declared in a program. For example, even though each peer thread create a static variable, at run time there is only one instance residing in the read/write area of virtual memory. Each peer thread reads and writes this instance.

### Synchronizing Threads with Semaphores

#### Semaphores

A semaphore, s, is a global variable with a nonnegative integer value that can only be manipulated by two special operations, called P and V :

- P(s): If s is nonzero, then P decrements s and returns immediately. If s is zero, then suspend the thread until s becomes nonzero and the thread is restarted by a V operation. After restarting, the P operation decrements s and returns control to the caller.

- V (s): The V operation increments s by 1. If there are any threads blocked at a P operation waiting for s to become nonzero, then the V operation restarts exactly one of these threads, which then completes its P operation by decrementing s.

The definitions of P and V ensure that a running program can never enter a state where a properly initialized semaphore has a negative value. This property, known as the *semaphore invariant*.

The Posix standard defines a variety of functions for manipulating semaphores.

```c
#include <semaphore.h>
// Returns: 0 if OK, −1 on error  
int sem_init(sem_t *sem, 0, unsigned int value);
int sem_wait(sem_t *s);   /* P(s) */
int sem_post(sem_t *s);   /* V(s) */
```

The `sem_init` function initializes semaphore `sem` to `value`. Each semaphore must be initialized before it can be used. Programs perform P and V operations by calling the `sem_wait` and `sem_post` functions, respectively. For conciseness, we prefer to use the following equivalent P and V wrapper functions instead:

```c
#include "csapp.h"
// Returns: nothing
void P(sem_t *s);   /* Wrapper function for sem_wait */
void V(sem_t *s);   /* Wrapper function for sem_post */
```

#### Using Semaphores for Mutual Exclusion

A semaphore that is used in this way to protect shared variables is called a *binary semaphore* because its value is always 0 or 1. Binary semaphores whose purpose is to provide mutual exclusion are often called *mutexes*. Performing a P operation on a mutex is called *locking* the mutex. Similarly, performing the V operation is called *unlocking* the mutex. A thread that has locked but not yet unlocked a mutex is said to be *holding* the mutex. A semaphore that is used as a counter for a set of available resources is called a *counting semaphore*.

```c
volatile long cnt = 0; /* Counter */
sem_t mutex;           /* Semaphore that protects counter */

Sem_init(&mutex, 0, 1); /* mutex = 1 */


for (i = 0; i < niters; i++) {
  P(&mutex);
  cnt++;
  V(&mutex); 
}

```

#### Using Semaphores to Schedule Shared Resources

Another important use of semaphores, besides providing mutual exclusion, is to schedule accesses to shared resources. In this scenario, a thread uses a semaphore operation to notify another thread that some condition in the program state has become true. Two classical and useful examples are the *producer-consumer* and *readers-writers* problems.

##### Producer-Consumer Problem

![image-20210416120339896](Asserts/image-20210416120339896.png)

The *producer-consumer* problem is shown in Figure 12.23. A producer and consumer thread share a *bounded buffer* with n *slots*. The producer thread repeatedly produces new *items* and inserts them in the buffer. The consumer thread repeatwhedly removes items from the buffer and then consumes (uses) them. Variants with multiple producers and consumers are also possible.

Since inserting and removing items involves updating shared variables, we must guarantee mutually exclusive access to the buffer. But guaranteeing mutual exclusion is not sufficient. We also need to schedule accesses to the buffer. If the buffer is full (there are no empty slots), then the producer must wait until a slot becomes available. Similarly, if the buffer is empty (there are no available items), then the consumer must wait until an item becomes available.

Producer-consumer interactions occur frequently in real systems. For example, in a multimedia system, the producer might encode video frames while the consumer decodes and renders them on the screen. The purpose of the buffer is to reduce jitter in the video stream caused by data-dependent differences in the encoding and decoding times for individual frames. The buffer provides a reservoir of slots to the producer and a reservoir of encoded frames to the consumer. Another common example is the design of graphical user interfaces. The producer detects mouse and keyboard events and inserts them in the buffer. The consumer removes the events from the buffer in some priority-based manner and paints the screen.

![image-20210416124250656](Asserts/image-20210416124250656.png)

We will develop a simple package, called `Sbuf`, for building producer-consumer programs. `Sbuf` manipulates bounded buffers of type `sbuf_t` (Figure 12.24). Items are stored in a dynamically allocated integer array (buf) with n items. The `front` and `rear` indices keep track of the first and last items in the array. Three semaphores synchronize access to the buffer. The `mutex` semaphore provides mutually exclusive buffer access. Semaphores `slots` and `items` are counting semaphores that count the number of empty slots and available items, respectively.

```c
/* $begin sbufc */
#include "csapp.h"
#include "sbuf.h"

/* Create an empty, bounded, shared FIFO buffer with n slots */
/* $begin sbuf_init */
void sbuf_init(sbuf_t *sp, int n)
{
  sp->buf = Calloc(n, sizeof(int)); 
  sp->n = n;                       /* Buffer holds max of n items */
  sp->front = sp->rear = 0;        /* Empty buffer iff front == rear */
  Sem_init(&sp->mutex, 0, 1);      /* Binary semaphore for locking */
  Sem_init(&sp->slots, 0, n);      /* Initially, buf has n empty slots */
  Sem_init(&sp->items, 0, 0);      /* Initially, buf has zero data items */
}
/* $end sbuf_init */

/* Clean up buffer sp */
/* $begin sbuf_deinit */
void sbuf_deinit(sbuf_t *sp)
{
  Free(sp->buf);
}
/* $end sbuf_deinit */

/* Insert item onto the rear of shared buffer sp */
/* $begin sbuf_insert */
void sbuf_insert(sbuf_t *sp, int item)
{
  P(&sp->slots);                          /* Wait for available slot */
  P(&sp->mutex);                          /* Lock the buffer */
  sp->buf[(++sp->rear)%(sp->n)] = item;   /* Insert the item */
  V(&sp->mutex);                          /* Unlock the buffer */
  V(&sp->items);                          /* Announce available item */
}
/* $end sbuf_insert */

/* Remove and return the first item from buffer sp */
/* $begin sbuf_remove */
int sbuf_remove(sbuf_t *sp)
{
  int item;
  P(&sp->items);                          /* Wait for available item */
  P(&sp->mutex);                          /* Lock the buffer */
  item = sp->buf[(++sp->front)%(sp->n)];  /* Remove the item */
  V(&sp->mutex);                          /* Unlock the buffer */
  V(&sp->slots);                          /* Announce available slot */
  return item;
}
/* $end sbuf_remove */
/* $end sbufc */
```

Code above shows the implementation of the `Sbuf` package. The `sbuf_init` function allocates heap memory for the buffer, sets `front` and `rear` to indicate an empty buffer, and assigns initial values to the three semaphores. This function is called once, before calls to any of the other three functions. The `sbuf_deinit` function frees the buffer storage when the application is through using it. The `sbuf_insert` function waits for an available slot, locks the mutex, adds the item, unlocks the mutex, and then announces the availability of a new item. The `sbuf_remove` function is symmetric. After waiting for an available buffer item, it locks the mutex, removes the item from the front of the buffer, unlocks the mutex, and then signals the availability of a new slot.

##### Readers-Writers Problem

The *readers-writers problem* is a generalization of the mutual exclusion problem. A collection of concurrent threads is accessing a shared object such as a data structure in main memory or a database on disk. Some threads only read the object, while others modify it. Threads that modify the object are called *writers*. Threads that only read it are called *readers*. Writers must have exclusive access to the object, but readers may share the object with an unlimited number of other readers. In general, there are an unbounded number of concurrent readers and writers.

Readers-writers interactions occur frequently in real systems. For example, in an online airline reservation system, an unlimited number of customers are allowed to concurrently inspect the seat assignments, but a customer who is booking a seat must have exclusive access to the database. As another example, in a multi-threaded caching Web proxy, an unlimited number of threads can fetch existing pages from the shared page cache, but any thread that writes a new page to the cache must have exclusive access.

The readers-writers problem has several variations, each based on the priorities of readers and writers. 

- The *first readers-writers problem*, which avors readers, requires that no reader be kept waiting unless a writer has already been granted permission to use the object. In other words, no reader should wait simply because a writer is waiting. 
- The *second readers-writers problem*, which favors writers, requires that once a writer is ready to write, it performs its write as soon as possible. Unlike the first problem, a reader that arrives after a writer must wait, even if the writer is also waiting.

![image-20210416130409506](Asserts/image-20210416130409506.png)

Figure 12.26 shows a solution to the first readers-writers problem. Like the solutions to many synchronization problems, it is subtle and deceptively simple. The w semaphore controls access to the critical sections that access the shared object. The mutex semaphore protects access to the shared readcnt variable, which counts the number of readers currently in the critical section. A writer locks the w mutex each time it enters the critical section and unlocks it each time it leaves. This guarantees that there is at most one writer in the critical section at any point in time. On the other hand, only the first reader to enter the critical section locks w, and only the last reader to leave the critical section unlocks it. The w mutex is ignored by readers who enter and leave while other readers are present. This means that as long as a single reader holds the w mutex, an unbounded number of readers can enter the critical section unimpeded.

A correct solution to either of the readers-writers problems can result in *starvation*, where a thread blocks indefinitely and fails to make progress. For example, in the solution in Figure 12.26, a writer could wait indefinitely while a stream of readers arrived.

#### A Concurrent Server Based on Prethreading

![image-20210416131533268](Asserts/image-20210416131533268.png)

We created a new thread for each new client in the concurrent server previously. A disadvantage of this approach is that we incur the nontrivial cost of creating a new thread for each new client. A server based on prethreading tries to reduce this overhead by using the producer-consumer model shown in Figure 12.27. The server consists of a main thread and a set of worker threads. The main thread repeatedly accepts connection requests from clients and places the resulting connected descriptors in a bounded buffer. Each worker thread repeatedly removes a descriptor from the buffer, services the client, and then waits for the next descriptor.

Code bolow shows how we would use the `Sbuf` package to implement a prethreaded concurrent echo server. After initializing buffer `sbuf`, the main thread creates the set of worker threads. Then it enters the infinite server loop, accepting connection requests and inserting the resulting connected descriptors in `sbuf`. Each worker thread has a very simple behavior. It waits until it is able to remove a connected descriptor from the buffer and then calls the `echo_cnt` function to echo client input.

```c
/* 
 * echoservert_pre.c - A prethreaded concurrent echo server
 */
/* $begin echoservertpremain */
#include "csapp.h"
#include "sbuf.h"
#define NTHREADS  4
#define SBUFSIZE  16

void echo_cnt(int connfd);
void *thread(void *vargp);

sbuf_t sbuf; /* shared buffer of connected descriptors */

int main(int argc, char **argv) 
{
  int i, listenfd, connfd, port;
  socklen_t clientlen=sizeof(struct sockaddr_in);
  struct sockaddr_in clientaddr;
  pthread_t tid; 

  if (argc != 2) {
    fprintf(stderr, "usage: %s <port>\n", argv[0]);
    exit(0);
  }
  port = atoi(argv[1]);
  sbuf_init(&sbuf, SBUFSIZE); //line:conc:pre:initsbuf
  listenfd = Open_listenfd(port);

  for (i = 0; i < NTHREADS; i++)  /* Create worker threads */ //line:conc:pre:begincreate
    Pthread_create(&tid, NULL, thread, NULL);               //line:conc:pre:endcreate

  while (1) { 
    connfd = Accept(listenfd, (SA *) &clientaddr, &clientlen);
    sbuf_insert(&sbuf, connfd); /* Insert connfd in buffer */
  }
}

void *thread(void *vargp) 
{  
  Pthread_detach(pthread_self()); 
  while (1) { 
    int connfd = sbuf_remove(&sbuf); /* Remove connfd from buffer */ //line:conc:pre:removeconnfd
    echo_cnt(connfd);                /* Service client */
    Close(connfd);
  }
}
/* $end echoservertpremain */
```

The `echo_cnt` function records the cumulative number of bytes received from all clients in a global variable called `byte_cnt`. This is interesting code to study because it shows you a general technique for initializing packages that are called from thread routines. We need to initialize the `byte_cnt` counter and the `mutex` semaphore. One approach, which we used for the `Sbuf` and `Rio` packages, is to require the main thread to explicitly call an initialization function. Another approach, shown here, uses the `pthread_once` function to call the initialization function the first time some thread calls the `echo_cnt` function. The advantage of this approach is that it makes the package easier to use. The disadvantage is that every call to `echo_cnt` makes a call to `pthread_once`, which most times does nothing useful.

Once the package is initialized, the `echo_cnt` function initializes the Rio buffered I/O package and then echoes each text line that is received from the client. Notice that the accesses to the shared `byte_cnt` variable are protected by P and V operations.

```c

#include "csapp.h" 
static int byte_cnt; /* Byte counter */
static sem_t mutex; /* and the mutex that protects it */

static void init_echo_cnt(void) 7{
  Sem_init(&mutex, 0, 1);
  byte_cnt = 0;
}

void echo_cnt(int connfd)
{
  int n;
  char buf[MAXLINE];
  rio_t rio;
  static pthread_once_t once = PTHREAD_ONCE_INIT;

  Pthread_once(&once, init_echo_cnt);
  Rio_readinitb(&rio, connfd);
  while((n = Rio_readlineb(&rio, buf, MAXLINE)) != 0) {
    P(&mutex);
    byte_cnt += n;
    printf("server received %d (%d total) bytes on fd %d\n",
           n, byte_cnt, connfd);
    V(&mutex);
    Rio_writen(connfd, buf, n);
  }
}
```

> _**Note**_: I/O multiplexing is not the only way to write an event-driven program. For example, you might have noticed that the concurrent prethreaded server that we just developed is really an event-driven server with simple state machines for the main and worker threads. The main thread has two states (“waiting for connection request” and “waiting for available buffer slot”), two I/O events (“connection request arrives” and “buffer slot becomes available”), and two transitions (“accept connection request” and “insert buffer item”). Similarly, each worker thread has one state (“waiting for available buffer item”), one I/O event (“buffer item becomes available”), and one transition (“remove buffer item”).

### Using Threads for Parallelism

> _**Note**_: Synchronization overhead is expensive and should be avoided if possible. If it cannot be avoided, the overhead should be amortized by as much useful computation as possible. Because of the overhead of context switching multiple threads on the same core is large, parallel programs are often written so that each core runs exactly one thread.

The *speedup* of a parallel program is typically defined as
$$
S_p = \frac{T_1}{T_p}
$$
where p is the number of processor cores and $T_k$ is the running time on k cores. This formulation is sometimes referred to as *strong scaling*. When $T_1$ is the execution time of a sequential version of the program, then $S_p$ is called the *absolute speedup*. Absolute speedup is a truer measure of the benefits of parallelism than relative speedup. Parallel programs often suffer from synchronization overheads, even when they run on one processor, and these overheads can artificially inflate the relative speedup numbers because they increase the size of the numerator. On the other hand, absolute speedup is more difficult to measure than relative speedup because measuring absolute speedup requires two different versions of the program. For complex parallel codes, creating a separate sequential version might not be feasible, either because the code is too complex or because the source code is not available.

A related measure, known as *efficiency*, is defined as 
$$
E_p = \frac{S_p}{p} = \frac{T_1}{pT_p}
$$
and is typically reported as a percentage in the range (0, 100]. Efficiency is a measure of the overhead due to parallelization. Programs with high efficiency are spending more time doing useful work and less time synchronizing and communicating than programs with low efficiency.

![image-20210416182210649](Asserts/image-20210416182210649.png)

Figure 12.36 shows the different speedup and efficiency measures for our example parallel sum program. Efficiencies over 90 percent such as these are very good, but do not be fooled. We were able to achieve high efficiency because our problem was trivially easy to parallelize. In practice, this is not usually the case. Parallel programming has been an active area of research for decades. With the advent of commodity multi-core machines whose core count is doubling every few years, parallel programming continues to be a deep, difficult, and active area of research.

There is another view of speedup, known as *weak scaling*, which increases the problem size along with the number of processors, such that the amount of work performed on each processor is held constant as the number of processors increases. With this formulation, speedup and efficiency are expressed in terms of the total amount of work accomplished per unit time. For example, if we can double the number of processors and do twice the amount of work per hour, then we are enjoying linear speedup and 100 percent efficiency.

Weak scaling is often a truer measure than strong scaling because it more accurately reflects our desire to use bigger machines to do more work. This is particularly true for scientific codes, where the problem size can be easily increased and where bigger problem sizes translate directly to better predictions of nature. However, there exist applications whose sizes are not so easily increased, and for these applications strong scaling is more appropriate. For example, the amount of work performed by real-time signal-processing applications is often determined by the properties of the physical sensors that are generating the signals. Changing the total amount of work requires using different physical sensors, which might not be feasible or necessary. For these applications, we typically want to use parallelism to accomplish a fixed amount of work as quickly as possible.

### Other Concurrency Issues

#### Thread Safety

When we program with threads, we must be careful to write functions that have a property called thread safety. A function is said to be *thread-safe* if and only if it will always produce correct results when called repeatedly from multiple concurrent threads. If a function is not thread-safe, then we say it is *thread-unsafe*.

We can identify four (nondisjoint) classes of thread-unsafe functions:

- Class 1: *Functions that do not protect shared variables.* This class of thread-unsafe functions is relatively easy to make thread-safe: protect the shared variables with synchronization operations such as P and V . An advantage is that it does not require any changes in the calling program. A disadvantage is that the synchronization operations slow down the function.

- Class2: *Functions that keep state across multiple invocations.* The result of the current invocation depends on an intermediate result from the previous iteration. When we call `rand` repeatedly from a single thread after seeding it with a call to srand, we can expect a repeatable sequence of numbers. However, this assumption no longer holds if multiple threads are calling rand.

  The only way to make a function such as `rand` thread-safe is to rewrite it so that it does not use any static data, relying instead on the caller to pass the state information in arguments. The disadvantage is that the programmer is now forced to change the code in the calling routine as well.

  ```c
  unsigned next_seed = 1;
  /* rand - return pseudorandom integer in the range 0..32767 */
  unsigned rand(void)
  {
    next_seed = next_seed*1103515245 + 12543;
    return (unsigned)(next_seed>>16) % 32768;
  }
  /* srand - set the initial seed for rand() */
  void srand(unsigned new_seed)
  {
    next_seed = new_seed;
  }
  ```

- Class 3: *Functions that return a pointer to a static variable.* Some functions compute a result in a static variable and then return a pointer to that variable. If we call such functions from concurrent threads, the results being used by one thread are silently overwritten by another thread. 

  There are two ways to deal with this class of thread-unsafe functions. 

  - One option is to rewrite the function so that the caller passes the address of the variable in which to store the results. This eliminates all shared data, but it requires the programmer to have access to the function source code.
  - Another option is to use the *lock-and-copy* technique. The basic idea is to associate a mutex with the thread-unsafe function. At each call site, lock the mutex, call the thread-unsafe function, copy the result returned by the function to a private memory location, and then unlock the mutex. To minimize changes to the caller, you should define a thread-safe wrapper function that performs the lock-and-copy and then replace all calls to the thread-unsafe function with calls to the wrapper.

- Class4: *Functions that call thread-unsafe functions.* If a function f calls a thread-unsafe function g, is f thread-unsafe? It depends. If g is a class 2 function that relies on state across multiple invocations, then f is also thread-unsafe and there is no recourse short of rewriting g. However, if g is a class 1 or class 3 function, then f can still be thread-safe if you protect the call site and any resulting shared data with a mutex.

#### Reentrancy

There is an important class of thread-safe functions, known as *reentrant functions*, that are characterized by the property that they do not reference *any* shared data when they are called by multiple threads. Although the terms *thread-safe* and *reentrant* are sometimes used (incorrectly) as synonyms, there is a clear technical distinction that is worth preserving. Figure 12.39 shows the set relationships be- tween reentrant, thread-safe, and thread-unsafe functions. The set of all functions is partitioned into the disjoint sets of thread-safe and thread-unsafe functions. The set of reentrant functions is a proper subset of the thread-safe functions.