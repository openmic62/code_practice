;************************************************************************
;                                                                       *
;   Filename:      delay10.asm                                          *
;   Date:          26/1/12                                              *
;   File Version:  1.3                                                  *
;                                                                       *
;   Author:        David Meiklejohn                                     *
;   Company:       Gooligum Electronics                                 *
;                                                                       *
;************************************************************************
;                                                                       *
;   Architecture:  Baseline PIC                                         *
;   Processor:     any                                                  *
;                                                                       *
;************************************************************************
;                                                                       *
;   Files required: none                                                *
;                                                                       *
;************************************************************************
;                                                                       *
;   Description:    Variable Delay : N x 10 ms (10 ms - 2.55 s)         *
;                                                                       *
;       N passed as parameter in W reg                                  *
;       exact delay = W x 10.015 ms                                     *
;                                                                       *
;   Returns: W = 0                                                      *
;   Assumes: 4 MHz clock                                                *
;                                                                       *
;************************************************************************

	#include <p12F509.inc>        ; processor specific variable definitions
	
	GLOBAL   delay10xW_R

;***** VARIABL DEFINITIONS *****************************************************
	UDATA
dc1     res 1                     ; loop counter - inner
dc2     res 1                     ; loop counger - middle
dc3     res 1                     ; loop counger - outer
	
;***** SUBROUTINES *************************************************************
        CODE

;***** Variable delay: 10 mS to 2.55 S
delay10xW_R
	banksel dc3
	movwf   dc3               ; delay = 1+Wx(3+10009+3)-1+4 = W x 10.15 mS
dly2	movlw   .13               ; repeat inner loop 13 times
	movwf   dc2               ; -> 13x(767+3)-1 = 10009 cycles
	clrf    dc1               ; inner loop = 256x3-1 = 767 cycles
dly1	decfsz  dc1,f
	goto    dly1
	decfsz  dc2,f             ; end middle loop
	goto    dly1
	decfsz  dc3,f             ; end outer loop
	goto    dly2
	
	retlw   0
	
	END
