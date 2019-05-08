// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.


// Pseodo code
//  LOOP:
//  keyboard = KBD
//  if (keyboard) {
//    i = 0
//    while (i < screen.size) {
//      @SCREEN + i = -1;
//      i++;
//    }
//  }
//  @LOOP
//  0; JMP


//set variable for last entered key
@lastkey
  M = 0

(MAINLOOP)
//get keyboard value
@KBD
  D=M
//find if key changed
@lastkey
  D=D-M
@KEYCHANGE
  D;JNE
@MAINLOOP
  D;JEQ


(KEYCHANGE)
//get last key
@KBD
  D=M
//set last key
@lastkey
  M=D
//if last key != 0
@PAINTSCREEN
  D;JNE
//else lastkey == 0
@CLEARSCREEN
  D;JEQ


(PAINTSCREEN)
//set current and stop address
@SCREEN
  D=A
@current
  M=D
@8192
  D=D+A
@stop
  M=D


    (PAINTLOOP)
    //check if current >= stop - return to mainloop - painting done
    @current
      D=M
    @stop
      D=D-M
    @MAINLOOP
      D;JGE

    //paint current pixels and increase current
    @current
      A=M
      M=-1
    @current
      M=M+1
    @PAINTLOOP
      0;JMP


(CLEARSCREEN)
//set current and stop address
@SCREEN
  D=A
@current
  M=D
@8192
  D=D+A
@stop
  M=D


    (CLEARLOOP)
    //check if current >= stop - return to mainloop - painting done
    @current
      D=M
    @stop
      D=D-M
    @MAINLOOP
      D;JGE

    //paint current pixels and increase current
    @current
      A=M
      M=0
    @current
      M=M+1
    @CLEARLOOP
      0;JMP
