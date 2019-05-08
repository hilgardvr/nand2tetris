//Pushing constant 111
@111
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 333
@333
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 888
@888
D=A
@SP
A=M
M=D
@SP
M=M+1
//Popping to static 8
@SP
M=M-1
A=M
D=M
@24
M=D
//Popping to static 3
@SP
M=M-1
A=M
D=M
@19
M=D
//Popping to static 1
@SP
M=M-1
A=M
D=M
@17
M=D
//Pushing from static 3
@19
D=M
@SP
A=M
M=D
@SP
M=M+1
//Pushing from static 1
@17
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
//Pushing from static 8
@24
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
