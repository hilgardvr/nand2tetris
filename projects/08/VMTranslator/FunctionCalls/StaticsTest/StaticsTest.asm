//Bootstrap code
@256
D=A
@SP
M=D
@300
D=A
@LCL
M=D
@400
D=A
@ARG
M=D
//Calling function Sys.init
@RETURNID_0
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
D=M
@ARG
M=D
M=M-1
M=M-1
M=M-1
M=M-1
M=M-1
@SP
D=M
@LCL
M=D
@Sys.init
0;JMP
(RETURNID_0)
//Function Sys.init
(Sys.init)
//Pushing constant 6
@6
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 8
@8
D=A
@SP
A=M
M=D
@SP
M=M+1
//Calling function Class1.set
@RETURNID_1
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
D=M
@ARG
M=D
M=M-1
M=M-1
M=M-1
M=M-1
M=M-1
M=M-1
M=M-1
@SP
D=M
@LCL
M=D
@Class1.set
0;JMP
(RETURNID_1)
//Popping to temp 0
@SP
M=M-1
A=M
D=M
@5
M=D
//Pushing constant 23
@23
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 15
@15
D=A
@SP
A=M
M=D
@SP
M=M+1
//Calling function Class2.set
@RETURNID_2
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
D=M
@ARG
M=D
M=M-1
M=M-1
M=M-1
M=M-1
M=M-1
M=M-1
M=M-1
@SP
D=M
@LCL
M=D
@Class2.set
0;JMP
(RETURNID_2)
//Popping to temp 0
@SP
M=M-1
A=M
D=M
@5
M=D
//Calling function Class1.get
@RETURNID_3
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
D=M
@ARG
M=D
M=M-1
M=M-1
M=M-1
M=M-1
M=M-1
@SP
D=M
@LCL
M=D
@Class1.get
0;JMP
(RETURNID_3)
//Calling function Class2.get
@RETURNID_4
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
D=M
@ARG
M=D
M=M-1
M=M-1
M=M-1
M=M-1
M=M-1
@SP
D=M
@LCL
M=D
@Class2.get
0;JMP
(RETURNID_4)
//Adding label WHILE
(WHILE)
//Juming to WHILE
@WHILE
0;JMP
//Function Class2.set
(Class2.set)
//Pushing from ARG 0
@ARG
A=M
D=M
@SP
A=M
M=D
@SP
M=M+1
//Popping to static 0
@SP
M=M-1
A=M
D=M
@Class2.vm.0
M=D
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
//Popping to static 1
@SP
M=M-1
A=M
D=M
@Class2.vm.1
M=D
//Pushing constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
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
//Function Class2.get
(Class2.get)
//Pushing from static 0
@Class2.vm.0
D=M
@SP
A=M
M=D
@SP
M=M+1
//Pushing from static 1
@Class2.vm.1
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
//Function Class1.set
(Class1.set)
//Pushing from ARG 0
@ARG
A=M
D=M
@SP
A=M
M=D
@SP
M=M+1
//Popping to static 0
@SP
M=M-1
A=M
D=M
@Class1.vm.0
M=D
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
//Popping to static 1
@SP
M=M-1
A=M
D=M
@Class1.vm.1
M=D
//Pushing constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
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
//Function Class1.get
(Class1.get)
//Pushing from static 0
@Class1.vm.0
D=M
@SP
A=M
M=D
@SP
M=M+1
//Pushing from static 1
@Class1.vm.1
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
