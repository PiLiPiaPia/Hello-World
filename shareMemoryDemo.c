#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#define PERM S_IRUSR|S_IWUSR 
#define MAX_SEQUENCE_SIZE 100
typedef struct{
	unsigned int size;
	unsigned int fibSequence[MAX_SEQUENCE_SIZE];
}sharedSequence;

int main(int argc, char **argv)
{
    key_t shmid;
    char   *p_addr, *c_addr;
    sharedSequence* sharedMemory;
    pid_t pid;
    if(argc != 1) {
        fprintf(stderr, "Usage:%s\n\a", argv[0]);
        exit(1);
    }
    int seqSize = atoi(argv[0]);
    if(seqSize < 0 || seqSize > MAX_SEQUENCE_SIZE || seqSize == 0){
    	fprintf(stderr, "invalid sequence size\n");
    	exit(1);
    }
    if( (shmid = shmget(IPC_PRIVATE, sizeof(sharedSequence), PERM)) == -1 )   { 
        fprintf(stderr, "Create Share Memory Error:%s\n\a", strerror(errno));
        exit(1);
    }
    sharedMemory = shmat(shmid, 0, 0);
    sharedMemory->size = 0;
 	pid = fork();
    if(pid > 0) {
        wait(NULL);
        for(int i = 0;i < sharedMemory->size;++i){
        	printf("%u\n",sharedMemory->fibSequence[i]);
        }
        exit(0);
    }
    else if (pid == 0){
        sleep(1);
        sharedMemory->fibSequence[0] = 1;
        for(int i = 1;i < seqSize;++i){
        	if(i == 1){
        		sharedMemory->fibSequence[i] = 1;
        	}	
        	else{
        		sharedMemory->fibSequence[i] = sharedMemory->fibSequence[i-1] + sharedMemory->fibSequence[i-2];
        	}
        }
        sharedMemory->size = seqSize;
        exit(0);
    }
}

