//if KEYBOARD == 0
//  SCREEN = 0
//else
//  SCREEN = -1


//color boolean
@blackOrWhite
M=0

//sreen counter
@screenCounter
M=0

//last key pressed
@lastKey
M=0


(CHECKKEY)
//reset screenCounter
    @SCREEN
    D=A
    
    @screenCounter
    M=D

//check if key pressed
    @KBD
    D=M

    @SETBLACK
    D;JNE

    @SETWHITE
    D;JMP

(SETBLACK)
    @blackOrWhite
    M=-1
    
    @lastKey
    M=D

    @PAINT
    0;JMP

(SETWHITE)
    @blackOrWhite
    M=0

    @lastKey
    M=D

    @PAINT
    0;JMP

(PAINT)
    //check if key still same
    @KBD
    D=M

    @lastKey
    D=D-M

    @CHECKKEY
    D;JNE

    //check if end of screen has been reached
    @8192
    D=A
    
    @SCREEN
    D=D+A

    @screenCounter
    D=M-D

    @CHECKKEY
    D;JGE

    //else continue painting screen
    @blackOrWhite
    D=M
    
    @screenCounter
    A=M
    M=D

    @screenCounter
    M=M+1

    @PAINT
    0;JMP
