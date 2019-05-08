// R0 * R1 -> R2

//  i = counter
//  sum = total
//  while i < R1
//      sum += R0

@i
M=0

@R2
M=0

(LOOP)
    //check if i == R1 ? END
    @i
    D=M

    @R1
    D=M-D

    @END
    D;JEQ

    //add R0 value to sum
    @i
    M=M+1

    @R0
    D=M

    @R2
    M=D+M

    @LOOP
    0;JMP


(END)
    @END
    0;JMP  
