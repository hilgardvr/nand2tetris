//Pushing constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//Equals?
@SP
M=M-1
A=M
D=M
A=A-1
D=D-M
@DOEQEQUALS0
D;JEQ
@DOEQNOTEQ0
D;JNE
(DOEQNOTEQ0)
@SP
A=M-1
M=0
@DOEQCONT0
0;JMP
(DOEQEQUALS0)
@SP
A=M-1
M=-1
(DOEQCONT0)
//Pushing constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 16
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
//Equals?
@SP
M=M-1
A=M
D=M
A=A-1
D=D-M
@DOEQEQUALS1
D;JEQ
@DOEQNOTEQ1
D;JNE
(DOEQNOTEQ1)
@SP
A=M-1
M=0
@DOEQCONT1
0;JMP
(DOEQEQUALS1)
@SP
A=M-1
M=-1
(DOEQCONT1)
//Pushing constant 16
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//Equals?
@SP
M=M-1
A=M
D=M
A=A-1
D=D-M
@DOEQEQUALS2
D;JEQ
@DOEQNOTEQ2
D;JNE
(DOEQNOTEQ2)
@SP
A=M-1
M=0
@DOEQCONT2
0;JMP
(DOEQEQUALS2)
@SP
A=M-1
M=-1
(DOEQCONT2)
//Pushing constant 892
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//Less?
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@DOLTLT0
D;JLT
@DOLTNOTLT0
D;JGE
(DOLTLT0)
@SP
A=M-1
M=-1
@DOLTCONT0
0;JMP
(DOLTNOTLT0)
@SP
A=M-1
M=0
(DOLTCONT0)
//Pushing constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 892
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
//Less?
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@DOLTLT1
D;JLT
@DOLTNOTLT1
D;JGE
(DOLTLT1)
@SP
A=M-1
M=-1
@DOLTCONT1
0;JMP
(DOLTNOTLT1)
@SP
A=M-1
M=0
(DOLTCONT1)
//Pushing constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//Less?
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@DOLTLT2
D;JLT
@DOLTNOTLT2
D;JGE
(DOLTLT2)
@SP
A=M-1
M=-1
@DOLTCONT2
0;JMP
(DOLTNOTLT2)
@SP
A=M-1
M=0
(DOLTCONT2)
//Pushing constant 32767
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//Greater?
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@DOGTGT0
D;JGT
@DOGTNOTGT0
D;JLE
(DOGTGT0)
@SP
A=M-1
M=-1
@DOGTCONT0
0;JMP
(DOGTNOTGT0)
@SP
A=M-1
M=0
(DOGTCONT0)
//Pushing constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 32767
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
//Greater?
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@DOGTGT1
D;JGT
@DOGTNOTGT1
D;JLE
(DOGTGT1)
@SP
A=M-1
M=-1
@DOGTCONT1
0;JMP
(DOGTNOTGT1)
@SP
A=M-1
M=0
(DOGTCONT1)
//Pushing constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//Greater?
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@DOGTGT2
D;JGT
@DOGTNOTGT2
D;JLE
(DOGTGT2)
@SP
A=M-1
M=-1
@DOGTCONT2
0;JMP
(DOGTNOTGT2)
@SP
A=M-1
M=0
(DOGTCONT2)
//Pushing constant 57
@57
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 31
@31
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pushing constant 53
@53
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
//Pushing constant 112
@112
D=A
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
//Negging
@SP
A=M-1
M=-M
//Anding
@SP
M=M-1
A=M
D=M
A=A-1
M=D&M
//Pushing constant 82
@82
D=A
@SP
A=M
M=D
@SP
M=M+1
//Oring
@SP
M=M-1
A=M
D=M
A=A-1
M=D|M
//Notting
@SP
A=M
A=A-1
M=!M
