//Function SimpleFunction.test
(SimpleFunction.test)
//Pushing constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//Popping to LCL 0
@SP
M=M-1
A=M
D=M
@LCL
A=M
M=D
//Pushing from LCL 0
@LCL
A=M
D=M
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//Popping to LCL 1
@SP
M=M-1
A=M
D=M
@LCL
A=M
A=A+1
M=D
//Pushing from LCL 1
@LCL
A=M
A=A+1
D=M
@SP
A=M
M=D
@SP
M=M+1
//Pushing from LCL 0
@LCL
A=M
D=M
@SP
A=M
M=D
@SP
M=M+1
//Pushing from LCL 1
@LCL
A=M
A=A+1
D=M
@SP
A=M
M=D
@SP
M=M+1
//Adding
@SP
M=M-1
A=M
D=M
A=A-1
M=D+M
//Notting
@SP
A=M
A=A-1
M=!M
//Pushing from ARG 0
@ARG
A=M
D=M
@SP
A=M
M=D
@SP
M=M+1
//Adding
@SP
M=M-1
A=M
D=M
A=A-1
M=D+M
//Pushing from ARG 1
@ARG
A=M
A=A+1
D=M
@SP
A=M
M=D
@SP
M=M+1
//Subbing
@SP
M=M-1
A=M
D=M
A=A-1
M=M-D
//Returning from function
//Saving return value
@LCL
D=M
@R15
M=D
@5
D=A
@R15
A=M-D
D=M
@R15
M=D
//Pushing return value to ARG
@SP
A=M-1
D=M
@ARG
A=M
M=D
//Resetting stackpointer
D=A+1
@SP
M=D
//Resetting memory after return
//Resetting THAT
@LCL
M=M-1
A=M
D=M
@THAT
M=D
//Resetting THIS
@LCL
M=M-1
A=M
D=M
@THIS
M=D
//Resetting ARG
@LCL
M=M-1
A=M
D=M
@ARG
M=D
//Resetting local
@LCL
M=M-1
A=M
D=M
@LCL
M=D
//Jumping to saved return location
@R15
A=M
0;JMP
