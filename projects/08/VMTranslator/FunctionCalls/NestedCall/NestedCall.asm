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
//Pushing constant 4000
@4000
D=A
@SP
A=M
M=D
@SP
M=M+1
//Popping to pointer 0
@SP
M=M-1
A=M
D=M
@THIS
M=D
//Pushing constant 5000
@5000
D=A
@SP
A=M
M=D
@SP
M=M+1
//Popping to pointer 1
@SP
M=M-1
A=M
D=M
@THAT
M=D
//Calling function Sys.main
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
@SP
D=M
@LCL
M=D
@Sys.main
0;JMP
(RETURNID_1)
//Popping to temp 1
@SP
M=M-1
A=M
D=M
@6
M=D
//Adding label LOOP
(LOOP)
//Juming to LOOP
@LOOP
0;JMP
//Function Sys.main
(Sys.main)
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
//Pushing constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//Popping to LCL 2
@SP
M=M-1
A=M
D=M
@LCL
A=M
A=A+1
A=A+1
M=D
//Pushing from LCL 2
@LCL
A=M
A=A+1
A=A+1
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
//Popping to LCL 3
@SP
M=M-1
A=M
D=M
@LCL
A=M
A=A+1
A=A+1
A=A+1
M=D
//Pushing from LCL 3
@LCL
A=M
A=A+1
A=A+1
A=A+1
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
//Popping to LCL 4
@SP
M=M-1
A=M
D=M
@LCL
A=M
A=A+1
A=A+1
A=A+1
A=A+1
M=D
//Pushing from LCL 4
@LCL
A=M
A=A+1
A=A+1
A=A+1
A=A+1
D=M
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 4001
@4001
D=A
@SP
A=M
M=D
@SP
M=M+1
//Popping to pointer 0
@SP
M=M-1
A=M
D=M
@THIS
M=D
//Pushing constant 5001
@5001
D=A
@SP
A=M
M=D
@SP
M=M+1
//Popping to pointer 1
@SP
M=M-1
A=M
D=M
@THAT
M=D
//Pushing constant 200
@200
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
//Pushing constant 40
@40
D=A
@SP
A=M
M=D
@SP
M=M+1
//Popping to LCL 2
@SP
M=M-1
A=M
D=M
@LCL
A=M
A=A+1
A=A+1
M=D
//Pushing constant 6
@6
D=A
@SP
A=M
M=D
@SP
M=M+1
//Popping to LCL 3
@SP
M=M-1
A=M
D=M
@LCL
A=M
A=A+1
A=A+1
A=A+1
M=D
//Pushing constant 123
@123
D=A
@SP
A=M
M=D
@SP
M=M+1
//Calling function Sys.add12
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
@SP
D=M
@LCL
M=D
@Sys.add12
0;JMP
(RETURNID_2)
//Popping to temp 0
@SP
M=M-1
A=M
D=M
@5
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
//Pushing from LCL 2
@LCL
A=M
A=A+1
A=A+1
D=M
@SP
A=M
M=D
@SP
M=M+1
//Pushing from LCL 3
@LCL
A=M
A=A+1
A=A+1
A=A+1
D=M
@SP
A=M
M=D
@SP
M=M+1
//Pushing from LCL 4
@LCL
A=M
A=A+1
A=A+1
A=A+1
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
//Adding
@SP
M=M-1
A=M
D=M
A=A-1
M=D+M
//Adding
@SP
M=M-1
A=M
D=M
A=A-1
M=D+M
//Adding
@SP
M=M-1
A=M
D=M
A=A-1
M=D+M
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
//Function Sys.add12
(Sys.add12)
//Pushing constant 4002
@4002
D=A
@SP
A=M
M=D
@SP
M=M+1
//Popping to pointer 0
@SP
M=M-1
A=M
D=M
@THIS
M=D
//Pushing constant 5002
@5002
D=A
@SP
A=M
M=D
@SP
M=M+1
//Popping to pointer 1
@SP
M=M-1
A=M
D=M
@THAT
M=D
//Pushing from ARG 0
@ARG
A=M
D=M
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 12
@12
D=A
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
