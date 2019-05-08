// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.

// Pseado
//  i = R0
//  n = R1
//  sum = 0
//  while (i > 0) {
//    sum += n
//    i--
//  }

//get value in R0 -> i
@R0
  D=M
@i
  M=D

//get value in R1 -> n
@R1
  D=M
@n
  M=D

//set sum = 0
@R2
  M=0

(LOOP)
//check if n == 0
@i
  D=M
@END
  D;JEQ

//continue loop
//add R1 to sum
@n
  D=M
@R2
  M=D+M
@i
  M=M-1
@LOOP
  0; JMP

//end with infinite loop
(END)
@END
  0; JMP









